package com.io7m.lwjgl.examples.tests;

import org.apache.felix.framework.FrameworkFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;

import java.io.IOException;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class TestAllComponents
{
  private static final Logger LOG =
    Logger.getLogger(TestAllComponents.class.getCanonicalName());

  /**
   * Check that no package export conflicts were added whilst producing OSGi
   * bundles.
   *
   * @throws IOException On errors
   */

  @Test
  public void testConflicts()
    throws IOException
  {
    final var modules = new ArrayList<ModuleDescriptor>();
    final var jars = findJars();
    for (final var jar : jars) {
      final var moduleRef =
        ModuleFinder.of(Paths.get(jar))
          .findAll()
          .iterator()
          .next();

      final var moduleDescriptor = moduleRef.descriptor();
      if (moduleDescriptor.name().startsWith("com.io7m")) {
        continue;
      }
      modules.add(moduleDescriptor);
    }

    Assertions.assertFalse(modules.isEmpty());

    final var packageToModules = new HashMap<String, String>();
    boolean failed = false;
    for (final var descriptor : modules) {
      for (final var export : descriptor.exports()) {
        final var packageName = export.source();
        final var moduleName = descriptor.name();
        LOG.info(String.format(
          "module %s exports %s",
          moduleName,
          packageName));
        if (packageToModules.containsKey(packageName)) {
          LOG.severe(String.format(
            "package %s exported by modules: %s and %s",
            packageName,
            moduleName,
            packageToModules.get(packageName))
          );
          failed = true;
        } else {
          packageToModules.put(packageName, moduleName);
        }
      }
    }
    Assertions.assertFalse(failed);
  }

  /**
   * Check that all components can be resolved and executed.
   *
   * @throws Exception On errors
   */

  @Test
  public void testResolveAllComponents()
    throws Exception
  {
    final var tmp =
      Files.createTempDirectory("lwjgl-osgi-tests")
        .toAbsolutePath();

    final var frameworks = new FrameworkFactory();
    final Map<String, String> config = new HashMap<>();
    config.put(Constants.FRAMEWORK_STORAGE, tmp.toString());
    config.put(Constants.FRAMEWORK_STORAGE_CLEAN, "true");

    final var framework = frameworks.newFramework(config);
    framework.start();

    try {
      final var context = framework.getBundleContext();
      final var jars = findJars();
      final var bundles = new ArrayList<Bundle>(jars.size());
      for (final var jar : jars) {
        LOG.info("install: " + jar);
        bundles.add(context.installBundle(jar.toString()));
      }

      framework.start();

      final var func =
        findBundle(bundles, "org.osgi.util.function");
      func.start();

      final var promise =
        findBundle(bundles, "org.osgi.util.promise");
      promise.start();

      final var scr =
        findBundle(bundles, "org.apache.felix.scr");
      scr.start();

      for (final var bundle : bundles) {
        LOG.info("start: " + bundle.getSymbolicName());
        try {
          bundle.start();
        } catch (final BundleException e) {
          if (isOVR(bundle) && !isWindows()) {
            LOG.info("OVR cannot resolve on non-Windows platforms, ignoring!");
            continue;
          }
          throw e;
        }
      }
    } finally {
      framework.stop();
    }
  }

  private static Bundle findBundle(
    final List<Bundle> bundles,
    final String name)
  {
    return bundles.stream()
      .filter(b -> {
        final var symbolic = b.getSymbolicName();
        if (symbolic != null) {
          return symbolic.contains(name);
        } else {
          return false;
        }
      })
      .findFirst()
      .orElseThrow();
  }

  private static boolean isWindows()
  {
    return System.getProperty("os.name").toLowerCase().startsWith("windows");
  }

  private static boolean isOVR(
    final Bundle bundle)
  {
    final var name = bundle.getSymbolicName();
    return name.contains("com.io7m.lwjgl.examples.ovr")
      || name.contains("org.lwjgl.ovr");
  }

  private static List<URI> findJars()
    throws IOException
  {
    try (var stream = TestAllComponents.class.getResourceAsStream(
      "/com/io7m/lwjgl/examples/tests/jars.txt")) {
      final var text = new String(
        stream.readAllBytes(),
        StandardCharsets.UTF_8);
      return List.of(text.split(":"))
        .stream()
        .map(file -> Paths.get(file).toUri())
        .collect(Collectors.toList());
    }
  }
}
