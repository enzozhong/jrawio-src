/***********************************************************************************************************************
 *
 * jrawio - a Java(TM) Image I/O SPI Provider for Camera Raw files
 * ===============================================================
 *
 * Copyright (C) 2003-2009 by Tidalwave s.a.s. (http://www.tidalwave.it)
 * http://jrawio.tidalwave.it
 *
 ***********************************************************************************************************************
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
 ***********************************************************************************************************************
 *
 * $Id$
 *
 **********************************************************************************************************************/
package it.tidalwave.imageio.orf;

import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import it.tidalwave.imageio.ImageReaderTestSupport;
import org.junit.Test;
import static org.junit.Assert.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class ORFImageReaderTest extends ImageReaderTestSupport
  {
    @Test
    public void testMIMEType()
      {
        assertMIMETypes("orf", "image/x-olympus-orf");
      }

    @Test(timeout=60000)
    // JIRA issues JRW-151, JRW-154, JRW-155, JRW-159
    public void testJRW151_JRW154_JRW155_JRW159() 
      throws Exception 
      {
        final String path = "https://imaging.dev.java.net/nonav/TestSets/others/josephandre/Olympus/E510/ORF/_2090037.ORF";
        final ImageReader ir = getImageReader(path);
        assertEquals(1, ir.getNumImages(false));
        assertEquals(1, ir.getNumThumbnails(0));
        assertImage(ir, 3720, 2800);
        assertThumbnail(ir, 0, 1600, 1200);
        final BufferedImage image = assertLoadImage(ir, 3720, 2800, 3, 16);
        assertLoadThumbnail(ir, 0, 1600, 1200);
        
        assertRaster(image, path, "f823981ab27195c2db002ee03a65af84");
        
        final ORFMetadata metadata = (ORFMetadata)ir.getImageMetadata(0);
        assertNotNull(metadata);
        final OlympusMakerNote makerNote = metadata.getOlympusMakerNote();
        assertNotNull(makerNote);
        assertEquals(8, makerNote.getTags().size());
        
        final CameraSettings cameraSettings = makerNote.getOlympusCameraSettings();
        assertNotNull(cameraSettings);
        assertEquals(44, cameraSettings.getTags().size());

        final Equipment equipment = makerNote.getOlympusEquipment();
        assertNotNull(equipment);
        assertEquals(23, equipment.getTags().size());
        
        final FocusInfo focusInfo = makerNote.getOlympusFocusInfo();
        assertNotNull(focusInfo);
        assertEquals(59, focusInfo.getTags().size());
        
        final ImageProcessing imageProcessing = makerNote.getOlympusImageProcessing();
        assertNotNull(imageProcessing);
        assertEquals(142, imageProcessing.getTags().size());
        
        final RawDevelopment rawDevelopment = makerNote.getOlympusRawDevelopment();
        assertNotNull(rawDevelopment);
        assertEquals(14, rawDevelopment.getTags().size());
        
        close(ir);
      }
    
    @Test(timeout=60000)
    // JIRA issues JRW-160
    public void testJRW160() 
      throws Exception 
      {
        final String path = "https://imaging.dev.java.net/nonav/TestSets/others/victoriagracia/Olympus/E500/ORF/V7020205.ORF";
        final ImageReader ir = getImageReader(path);
        assertEquals(1, ir.getNumImages(false));
        assertEquals(2, ir.getNumThumbnails(0));
        assertImage(ir, 3360, 2504);
        assertThumbnail(ir, 0, 160, 120);
        assertThumbnail(ir, 1, 0, 0); //  FIXME: broken
        final BufferedImage image = assertLoadImage(ir, 3360, 2504, 3, 16);
        assertLoadThumbnail(ir, 0, 160, 120);
//        assertLoadThumbnail(ir, 1, 0, 0); //  FIXME: broken
       
        assertRaster(image, path, "4dfff3c25f1fdc8940ace2d079a09c6f");
        
        final ORFMetadata metadata = (ORFMetadata)ir.getImageMetadata(0);
        assertNotNull(metadata);
        final OlympusMakerNote makerNote = metadata.getOlympusMakerNote();
        assertNotNull(makerNote);
        assertEquals(27, makerNote.getTags().size());
        
        final CameraSettings cameraSettings = makerNote.getOlympusCameraSettings();
        assertNotNull(cameraSettings);
        assertEquals(40, cameraSettings.getTags().size());

        final Equipment equipment = makerNote.getOlympusEquipment();
        assertNotNull(equipment);
        assertEquals(23, equipment.getTags().size());
        
        final FocusInfo focusInfo = makerNote.getOlympusFocusInfo();
        assertNotNull(focusInfo);
        assertEquals(52, focusInfo.getTags().size());
        
        final ImageProcessing imageProcessing = makerNote.getOlympusImageProcessing();
        assertNotNull(imageProcessing);
        assertEquals(109, imageProcessing.getTags().size());
        
        final RawDevelopment rawDevelopment = makerNote.getOlympusRawDevelopment();
        assertNotNull(rawDevelopment);
        assertEquals(14, rawDevelopment.getTags().size());
        
        close(ir);
      }
  }
