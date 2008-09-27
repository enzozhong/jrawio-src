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
 * $Id: DemosaicOperation.java 189 2008-09-27 22:50:55Z fabriziogiudici $
 *
 ******************************************************************************/
package it.tidalwave.imageio.rawprocessor.raw;

import it.tidalwave.imageio.util.Logger;
import it.tidalwave.imageio.rawprocessor.RAWImage;
import it.tidalwave.imageio.rawprocessor.Curve;
import it.tidalwave.imageio.rawprocessor.OperationSupport;
import it.tidalwave.imageio.rawprocessor.demosaic.DemosaicFilterProcessor;

/*******************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id: DemosaicOperation.java 189 2008-09-27 22:50:55Z fabriziogiudici $
 *
 ******************************************************************************/
public class DemosaicOperation extends OperationSupport
  {
    private final static Logger logger = getLogger(DemosaicOperation.class);

    protected String algorithm;

    protected DemosaicFilterProcessor processor;

    private int[] cfaPattern;

    private String cfaPatternAsString;

    /*******************************************************************************
     *
     * @inheritDoc
     *
     ******************************************************************************/
    public void process (RAWImage image) throws Exception
      {
        logger.fine("process()");
        cfaPattern = image.getCFAPattern();
        String cfaPatternAsString = image.getCFAPatternAsString();
        algorithm = chooseDemosaicAlgorithm(cfaPatternAsString);
        double redCoeff = image.getRedCoefficient();
        double greenCoeff = image.getGreenCoefficient();
        double blueCoeff = image.getBlueCoefficient();
        double blackLevel = image.getBlackLevel();
        Curve curve = image.getCurve();
        processor = new DemosaicFilterProcessor(cfaPatternAsString, algorithm, redCoeff, greenCoeff, blueCoeff, blackLevel, curve);
        logger.finer("Bayer pattern: %s", cfaPatternAsString);
        processor.process(image.getImage());
      }    
    
    /*******************************************************************************
     * 
     * Chooses the demosaic algorithm. Can be overridden if a particular RAW format
     * processor wants to make it own selection.
     * 
     * @return
     * 
     *******************************************************************************/
    protected String chooseDemosaicAlgorithm (String cfaPatternAsString)
      {
        if (!cfaPatternAsString.equals("GRBG")) // FIXME: PixelGrouping does not work yet with other patterns
          {
            return "Bilinear";
          }

        return "PixelGrouping";
      }
  }
