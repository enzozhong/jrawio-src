/***********************************************************************************************************************
 *
 * jrawio - a Java(TM) Image I/O SPI Provider for Camera Raw files
 * Copyright (C) 2003 - 2016 by Tidalwave s.a.s.
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************
 *
 * WWW: http://jrawio.rawdarkroom.org
 * SCM: https://kenai.com/hg/jrawio~src
 *
 **********************************************************************************************************************/
package it.tidalwave.imageio.raf;

import java.awt.Dimension;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import it.tidalwave.imageio.io.RAWImageInputStream;
import it.tidalwave.imageio.raw.Directory;
import it.tidalwave.imageio.raw.HeaderProcessor;
import it.tidalwave.imageio.tiff.TIFFMetadataSupport;
import it.tidalwave.imageio.tiff.IFD;
import it.tidalwave.imageio.tiff.ThumbnailLoader;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class RAFMetadata extends TIFFMetadataSupport
  {
    private final static long serialVersionUID = 1795868418676854749L;

    /*******************************************************************************************************************
     *
     ******************************************************************************************************************/
    public RAFMetadata (@Nonnull final Directory primaryIFD,
                        @Nonnull final RAWImageInputStream iis, 
                        @Nonnull final HeaderProcessor headerProcessor)
      {
        super(primaryIFD, iis, headerProcessor);
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override
    protected void postInit (@Nonnull final RAWImageInputStream iis)
      {
        final FujiRawData fujiRawData = ((RAFHeaderProcessor)headerProcessor).getFujiRawData();
        final IFD exif = getExifIFD();
        // Testing with ExifProbe reveals a JPEG_SOI, a JPEG_APP1 and then a TIFF - I suppose it's a TIFF embedded
        // in a JPEG. ImageIO can't read it - we are skipping the JPEG stuff (12 bytes) and directly pointing
        // to the TIFF.
        thumbnailLoaders.add(new ThumbnailLoader(iis,
                                    fujiRawData.getJPEGImageOffset() + 12,
                                    fujiRawData.getJPEGImageLength(),
                                    exif.getPixelXDimension(),
                                    exif.getPixelYDimension()));
      }
 
    /*******************************************************************************************************************
     * 
     * @return
     * 
     ******************************************************************************************************************/
    @CheckForNull
    public FujiMakerNote getFujiMakerNote()
      {
        return (FujiMakerNote)getMakerNote();
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override
    protected boolean isRasterIFD (@Nonnull final IFD ifd)
      {
        return ifd.isPhotometricInterpretationAvailable();
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override
    protected boolean isThumbnailIFD (@Nonnull final IFD ifd)
      {
        return ifd.isJPEGInterchangeFormatAvailable();
      }

    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public FujiRawData getFujiRawData()
      {
        return ((RAFHeaderProcessor)headerProcessor).getFujiRawData();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override
    @Nonnull
    protected Dimension getImageSize()
      {
        final FujiTable1 fujiTable1 = getFujiRawData().getFujiTable1();
        return new Dimension(fujiTable1.getRotatedWidth(), fujiTable1.getRotatedHeight());
      }
  }
