/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.bundles.org.lwjgl.example;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * LWJGL example.
 */

@Component(immediate = true)
public final class LWJGLExample
{
  private long context;
  private Thread thread;

  public LWJGLExample()
  {

  }

  private void log(
    final String message)
  {
    System.err.printf(
      "[%s][%s]: %s\n",
      LWJGLExample.class.getName(),
      Thread.currentThread().getName(),
      message);
  }

  private void logException(
    final Exception e)
  {
    System.err.printf(
      "[%s][%s]: ERROR: %s\n",
      LWJGLExample.class.getName(),
      Thread.currentThread().getName(),
      e.getMessage());
    e.printStackTrace();
  }

  @Activate
  private void activate()
  {
    this.thread = new Thread(this::doExample);
    this.thread.setName("renderer");
    this.thread.start();
  }

  private void doExample()
  {
    try {
      this.log("activate");

      GLFWErrorCallback.createPrint(System.err).set();

      if (!GLFW.glfwInit()) {
        throw new IllegalStateException("Unable to initialize GLFW");
      }

      this.log("creating window");
      GLFW.glfwDefaultWindowHints();
      GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
      GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
      GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
      GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
      GLFW.glfwWindowHint(
        GLFW.GLFW_OPENGL_PROFILE,
        GLFW.GLFW_OPENGL_CORE_PROFILE);

      this.context =
        GLFW.glfwCreateWindow(
          640,
          480,
          "LWJGL3",
          MemoryUtil.NULL,
          MemoryUtil.NULL);
      GLFW.glfwMakeContextCurrent(this.context);
      GL.createCapabilities();

      this.log("clearing window");
      GL11.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

      this.log("shutting down");
      GLFW.glfwMakeContextCurrent(MemoryUtil.NULL);
      GLFW.glfwDestroyWindow(this.context);
      GLFW.glfwTerminate();

    } catch (final Exception e) {
      this.logException(e);
    }
  }

  @Deactivate
  private void deactivate()
  {
    this.log("deactivate");
    this.thread = null;
  }
}
