/*
 * Copyright © 2018 Mark Raynsford <code@io7m.com> http://io7m.com
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

package com.io7m.lwjgl.examples.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import java.util.logging.Logger;

/**
 * An example OpenGL component.
 */

@Component(immediate = true)
public final class OpenGLComponent
{
  private static final Logger LOG =
    Logger.getLogger(OpenGLComponent.class.getCanonicalName());

  /**
   * Construct a component.
   */

  public OpenGLComponent()
  {

  }

  /**
   * Called by the OSGi runtime when the component is activated.
   */

  @Activate
  public void onActivate()
  {
    LOG.info("onActivate");

    try {
      LOG.info("initializing GLFW");
      GLFW.glfwInit();

      LOG.info("creating window");
      final var window =
        GLFW.glfwCreateWindow(640, 480, "Hello World", 0L, 0L);

      if (window == 0L) {
        LOG.info("creating window failed, terminating");
        GLFW.glfwTerminate();
        return;
      }

      LOG.info("making context current");
      GLFW.glfwMakeContextCurrent(window);

      LOG.info("creating OpenGL capabilities");
      GL.createCapabilities();

      LOG.info("clearing window");
      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

      LOG.info("swapping buffers");
      GLFW.glfwSwapBuffers(window);

      LOG.info("polling events");
      GLFW.glfwPollEvents();

      LOG.info("destroying window");
      GLFW.glfwDestroyWindow(window);

      LOG.info("terminating");
      GLFW.glfwTerminate();
    } finally {
      LOG.info("onActivate: done");
    }
  }
}
