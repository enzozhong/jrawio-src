/*******************************************************************************
 *
 * jrawio - a Java(TM) ImageIO API Spi Provider for RAW files
 * ==========================================================
 *
 * Copyright (C) 2003-2008 by Fabrizio Giudici
 * Project home page: http://jrawio.tidalwave.it
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *
 *******************************************************************************
 *
 * $Id: PEFRasterReader.java 57 2008-08-21 20:00:46Z fabriziogiudici $
 *
 ******************************************************************************/
package it.tidalwave.imageio.raf;

import it.tidalwave.imageio.io.RAWImageInputStream;
import it.tidalwave.imageio.raw.RAWImageReaderSupport;
import it.tidalwave.imageio.raw.RasterReader;
import java.awt.image.WritableRaster;
import java.io.IOException;

/*******************************************************************************
 *
 * This class implements the PEF (Pentax raw Format) raster loading.
 * 
 * @author  Fabrizio Giudici
 * @version $Id: PEFRasterReader.java 57 2008-08-21 20:00:46Z fabriziogiudici $
 *
 ******************************************************************************/
public class RAFRasterReader extends RasterReader
  {
    @Override
    protected boolean isCompressedRaster()
      {
        return false;
      }

    @Override
    protected void loadUncompressedRaster (final RAWImageInputStream iis,
                                           final WritableRaster raster,
                                           final RAWImageReaderSupport ir)
      throws IOException
      {
        iis.seek(this.rasterOffset);
      }
   }