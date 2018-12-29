package com.io7m.lwjgl.examples.tests;

import org.junit.jupiter.api.Test;
import org.lwjgl.assimp.Assimp;
import org.lwjgl.bgfx.BGFXInit;
import org.lwjgl.cuda.CU;
import org.lwjgl.egl.EGL;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nuklear.Nuklear;
import org.lwjgl.odbc.SQL;
import org.lwjgl.openal.AL;
import org.lwjgl.opencl.CL;
import org.lwjgl.opengl.GL;
import org.lwjgl.ovr.OVR;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.jawt.JAWT;
import org.lwjgl.system.rpmalloc.RPmalloc;
import org.lwjgl.util.libdivide.LibDivide;
import org.lwjgl.util.lmdb.LMDB;
import org.lwjgl.util.lz4.LZ4;
import org.lwjgl.util.meow.Meow;
import org.lwjgl.util.nfd.NativeFileDialog;
import org.lwjgl.util.opus.Opus;
import org.lwjgl.util.par.ParShapes;
import org.lwjgl.util.remotery.Remotery;
import org.lwjgl.util.simd.SSE;
import org.lwjgl.util.tinyexr.TinyEXR;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.lwjgl.util.tootle.Tootle;
import org.lwjgl.util.vma.Vma;
import org.lwjgl.util.xxhash.XXHash;
import org.lwjgl.util.yoga.Yoga;
import org.lwjgl.util.zstd.Zstd;
import org.lwjgl.vulkan.VK;

import java.lang.module.ModuleFinder;
import java.nio.file.Path;
import java.util.logging.Logger;

public final class TestModuleDescriptors
{
  private static final Logger LOG =
    Logger.getLogger(TestModuleDescriptors.class.getCanonicalName());

  @Test
  public void testAssimp()
    throws Exception
  {
    checkModuleName("org.lwjgl.assimp", Assimp.class);
  }

  @Test
  public void testBgfx()
    throws Exception
  {
    checkModuleName("org.lwjgl.bgfx", BGFXInit.class);
  }

  @Test
  public void testCuda()
    throws Exception
  {
    checkModuleName("org.lwjgl.cuda", CU.class);
  }

  @Test
  public void testEGL()
    throws Exception
  {
    checkModuleName("org.lwjgl.egl", EGL.class);
  }

  @Test
  public void testGLFW()
    throws Exception
  {
    checkModuleName("org.lwjgl.glfw", GLFW.class);
  }

  @Test
  public void testJAWT()
    throws Exception
  {
    checkModuleName("org.lwjgl.jawt", JAWT.class);
  }

  @Test
  public void testLibdivide()
    throws Exception
  {
    checkModuleName("org.lwjgl.libdivide", LibDivide.class);
  }

  @Test
  public void testLMDB()
    throws Exception
  {
    checkModuleName("org.lwjgl.lmdb", LMDB.class);
  }

  @Test
  public void testLz4()
    throws Exception
  {
    checkModuleName("org.lwjgl.lz4", LZ4.class);
  }

  @Test
  public void testMeow()
    throws Exception
  {
    checkModuleName("org.lwjgl.meow", Meow.class);
  }

  @Test
  public void testNanoVG()
    throws Exception
  {
    checkModuleName("org.lwjgl.nanovg", NanoVG.class);
  }

  @Test
  public void testNfd()
    throws Exception
  {
    checkModuleName("org.lwjgl.nfd", NativeFileDialog.class);
  }

  @Test
  public void testNuklear()
    throws Exception
  {
    checkModuleName("org.lwjgl.nuklear", Nuklear.class);
  }

  @Test
  public void testODBC()
    throws Exception
  {
    checkModuleName("org.lwjgl.odbc", SQL.class);
  }

  @Test
  public void testOpenAL()
    throws Exception
  {
    checkModuleName("org.lwjgl.openal", AL.class);
  }

  @Test
  public void testOpenCL()
    throws Exception
  {
    checkModuleName("org.lwjgl.opencl", CL.class);
  }

  @Test
  public void testOpenGL()
    throws Exception
  {
    checkModuleName("org.lwjgl.opengl", GL.class);
  }

  @Test
  public void testOpus()
    throws Exception
  {
    checkModuleName("org.lwjgl.opus", Opus.class);
  }

  @Test
  public void testOVR()
    throws Exception
  {
    checkModuleName("org.lwjgl.ovr", OVR.class);
  }

  @Test
  public void testPar()
    throws Exception
  {
    checkModuleName("org.lwjgl.par", ParShapes.class);
  }

  @Test
  public void testRemotery()
    throws Exception
  {
    checkModuleName("org.lwjgl.remotery", Remotery.class);
  }

  @Test
  public void testRpmalloc()
    throws Exception
  {
    checkModuleName("org.lwjgl.rpmalloc", RPmalloc.class);
  }

  @Test
  public void testSSE()
    throws Exception
  {
    checkModuleName("org.lwjgl.sse", SSE.class);
  }

  @Test
  public void testStb()
    throws Exception
  {
    checkModuleName("org.lwjgl.stb", STBImage.class);
  }

  @Test
  public void testTinyEXR()
    throws Exception
  {
    checkModuleName("org.lwjgl.tinyexr", TinyEXR.class);
  }

  @Test
  public void testTinyFD()
    throws Exception
  {
    checkModuleName("org.lwjgl.tinyfd", TinyFileDialogs.class);
  }

  @Test
  public void testTootle()
    throws Exception
  {
    checkModuleName("org.lwjgl.tootle", Tootle.class);
  }

  @Test
  public void testVMA()
    throws Exception
  {
    checkModuleName("org.lwjgl.vma", Vma.class);
  }

  @Test
  public void testVulkan()
    throws Exception
  {
    checkModuleName("org.lwjgl.vulkan", VK.class);
  }

  @Test
  public void testXXHash()
    throws Exception
  {
    checkModuleName("org.lwjgl.xxhash", XXHash.class);
  }

  @Test
  public void testYoga()
    throws Exception
  {
    checkModuleName("org.lwjgl.yoga", Yoga.class);
  }

  @Test
  public void testZstandard()
    throws Exception
  {
    checkModuleName("org.lwjgl.zstd", Zstd.class);
  }

  private static void checkModuleName(
    final String name,
    final Class<?> clazz)
    throws Exception
  {
    final var source = clazz.getProtectionDomain().getCodeSource().getLocation();
    LOG.info("class: " + clazz + " source " + source);

    if (source == null) {
      throw new IllegalStateException("No source for: " + source);
    }

    final var finder = ModuleFinder.of(Path.of(source.toURI()));
    finder.findAll().forEach(ref -> LOG.info("found: " + ref.descriptor().name()));

    final var found = finder.find(name);
    if (found.isEmpty()) {
      throw new IllegalStateException("Module " + name + " not found");
    }

    final var module = found.get();
    LOG.info("class: " + clazz + " module " + module.descriptor().name());

    try (var reader = module.open()) {
      final var data = reader.read(formatClassName(clazz));
      if (data.isEmpty()) {
        throw new IllegalStateException("Class " + clazz + " not found");
      }
      reader.release(data.get());
    }
  }

  private static String formatClassName(
    final Class<?> clazz)
  {
    return clazz.getCanonicalName().replace('.', '/') + ".class";
  }
}
