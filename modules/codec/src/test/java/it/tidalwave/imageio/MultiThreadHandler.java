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
package it.tidalwave.imageio;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.io.IOException;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class MultiThreadHandler extends Handler
  {
    private static final Map<ThreadGroup, String> TEST_NAME_MAP = new HashMap<ThreadGroup, String>();
    
    private final Map<String, Handler> handlerMap = new HashMap<String, Handler>();

    protected String directory = "target/logs";

    public static void setTestName (final @Nonnull String name)
      {
        TEST_NAME_MAP.put(Thread.currentThread().getThreadGroup(), name);
      }

    public void setDirectory (final String directory)
      {
        this.directory = directory;
      }

    public String getDirectory()
      {
        return directory;
      }

    @Override
    public void publish (final @Nonnull LogRecord logRecord)
      {
        getHandler().publish(logRecord);
      }

    @Override
    public void flush()
      {
        getHandler().flush();
      }

    @Override
    public void close() 
      throws SecurityException
      {
        getHandler().close();
      }

    @Nonnull
    private synchronized Handler getHandler()
      {
        final String id = TEST_NAME_MAP.get(Thread.currentThread().getThreadGroup());
        Handler handler = handlerMap.get(id);

        if (handler == null)
          {
            try
              {
                final String fileName = id.replace(':', '_').replace('/', '_').replace('[', '_').replace(']', '_').replace(' ', '_');
                handler = new FileHandler(String.format("%s/log-%s.log", directory, fileName));
                handler.setFormatter(getFormatter());
                handlerMap.put(id, handler);
              }
            catch (IOException e)
              {
                e.printStackTrace();
              }
            catch (SecurityException e)
              {
                e.printStackTrace();
              }
          }

        return handler;
      }
  }