package com.io7m.lwjgl.examples.tests;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.FrameworkFactory;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
      final var text = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
      return List.of(text.split(":"))
        .stream()
        .map(file -> Paths.get(file).toUri())
        .collect(Collectors.toList());
    }
  }
}
