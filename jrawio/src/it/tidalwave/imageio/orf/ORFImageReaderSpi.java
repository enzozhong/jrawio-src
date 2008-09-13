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
 * $Id: ORFImageReaderSpi.java 156 2008-09-13 18:39:08Z fabriziogiudici $
 *
 ******************************************************************************/
package it.tidalwave.imageio.orf;

import javax.annotation.Nonnull;
import java.util.Locale;
import it.tidalwave.imageio.util.Logger;
import java.io.IOException;
import javax.imageio.ImageReader;
import it.tidalwave.imageio.io.RAWImageInputStream;
import it.tidalwave.imageio.raw.RAWImageReaderSpiSupport;
import it.tidalwave.imageio.tiff.IFD;
import it.tidalwave.imageio.tiff.TIFFImageReaderSupport;
import it.tidalwave.imageio.pef.PEFImageReader;

/*******************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id: ORFImageReaderSpi.java 156 2008-09-13 18:39:08Z fabriziogiudici $
 *
 ******************************************************************************/
public class ORFImageReaderSpi extends RAWImageReaderSpiSupport
  {
    private final static String CLASS = ORFImageReaderSpi.class.getName();
    private final static Logger logger = Logger.getLogger(CLASS);
    
    /***************************************************************************
     * 
     *
     **************************************************************************/
    public ORFImageReaderSpi()
      {
        super("ORF", "orf", "image/orf", PEFImageReader.class);
      }

    /***************************************************************************
     *
     * {@inheritDoc}
     * 
     **************************************************************************/
    public String getDescription (final Locale locale)
      {
        return "Standard ORF Image Reader";
      }

    /***************************************************************************
     *
     * {@inheritDoc}
     * 
     **************************************************************************/
    @Nonnull
    public ImageReader createReaderInstance (final Object extension) 
      throws IOException
      {
        return new ORFImageReader(this, extension);
      }

    /***************************************************************************
     *
     * {@inheritDoc}
     * 
     **************************************************************************/
    public boolean canDecodeInput (@Nonnull final RAWImageInputStream iis) 
      throws IOException
      {
        iis.seek(0);
        long ifdOffset = TIFFImageReaderSupport.processHeader(iis, null);
        final IFD primaryIFD = new IFD();
        primaryIFD.load(iis, ifdOffset);
        
        if (primaryIFD.isDNGVersionAvailable())
          { 
            return false;    
          }
        
        final String make = primaryIFD.getMake();
        final String model = primaryIFD.getModel();

        if ((make == null) || !make.toUpperCase().startsWith("OLYMPUS"))
          {
            logger.fine("ORFImageReaderSpi giving up on: '%s' / '%s'", make, model);
            return false;
          }

        return true;
      }
  }
