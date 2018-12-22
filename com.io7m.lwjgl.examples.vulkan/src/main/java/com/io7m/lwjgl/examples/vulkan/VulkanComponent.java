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

package com.io7m.lwjgl.examples.vulkan;

import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import java.util.logging.Logger;

/**
 * An example Vulkan component.
 */

@Component(immediate = true)
public final class VulkanComponent
{
  private static final Logger LOG =
    Logger.getLogger(VulkanComponent.class.getCanonicalName());

  /**
   * Construct a component.
   */

  public VulkanComponent()
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
      final var create_info = VkInstanceCreateInfo.calloc();
      final var ptr = PointerBuffer.allocateDirect(1);
      VK10.vkCreateInstance(create_info, null, ptr);
    } finally {
      LOG.info("onActivate: done");
    }
  }
}
