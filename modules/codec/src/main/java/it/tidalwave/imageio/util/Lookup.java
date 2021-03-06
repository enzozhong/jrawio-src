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
package it.tidalwave.imageio.util;

import javax.annotation.Nonnull;
import java.io.Serializable;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public abstract class Lookup implements Serializable
  {
    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    public static class NotFoundException extends Exception
      {
        public NotFoundException (final @Nonnull Class<?> type)
          {
            super("Parameter type not found: " + type);
          }
      }

    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    public static Lookup fixed (final @Nonnull Object ... contents)
      {
        return new DefaultLookup(contents);
      }

    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    protected Lookup()
      {
      }

    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public <T> T lookup (final @Nonnull Class<T> type, final @Nonnull T defaultValue)
      {
        //TODO: enforce defaultValue not null
        try
          {
            return lookup(type);
          }
        catch (NotFoundException e)
          {
            return defaultValue;
          }
      }

    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public abstract <T> T lookup (final @Nonnull Class<T> type)
      throws NotFoundException;

    /*******************************************************************************************************************
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public abstract String toContentString();
  }
