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

package com.io7m.lwjgl.examples.zstd;

import org.lwjgl.util.zstd.Zstd;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import java.util.logging.Logger;

/**
 * An example Zstandard component.
 */

@Component(immediate = true)
public final class ZstandardComponent
{
  private static final Logger LOG =
    Logger.getLogger(ZstandardComponent.class.getCanonicalName());

  /**
   * Construct a component.
   */

  public ZstandardComponent()
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
      final var context = Zstd.ZSTD_createCCtx();
      Zstd.ZSTD_freeCCtx(context);
    } finally {
      LOG.info("onActivate: done");
    }
  }
}
