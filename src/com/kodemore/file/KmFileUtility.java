/*
  Copyright (c) 2005-2012 Wyatt Love

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */

package com.kodemore.file;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;

import com.kodemore.utility.Kmu;


/**
 * I am normally used indirectly by other tools. 
 */
public class KmFileUtility
{
    //##################################################
    //# variables
    //##################################################//

    /**
     * The context used to access the file system.
     */
    private Context _context;

    /**
     * The nominal path.  The real path used to access the
     * file system may be different for subclasses.
     * See getRealPath();
     */
    private String  _path;

    //##################################################
    //# constructor
    //##################################################//

    public KmFileUtility(Context context)
    {
        this(context, "/");
    }

    public KmFileUtility(Context context, String path)
    {
        _context = context;
        _path = path;
    }

    //##################################################
    //# accessing 
    //##################################################//

    public String getPath()
    {
        return _path;
    }

    public void setPath(String e)
    {
        _path = e;
    }

    /**
     * This is the real path used to access the file system.
     * See subclass overrides.
     */
    public String getRealPath()
    {
        return getPath();
    }

    //##################################################
    //# io
    //##################################################//

    public boolean exists()
    {
        String[] arr = _context.fileList();
        for ( String e : arr )
            if ( e.equals(getRealPath()) )
                return true;

        return false;
    }

    public String readString()
    {
        return new String(readBytes());
    }

    public byte[] readBytes()
    {
        byte[] buf = new byte[1024];
        FileInputStream fis = null;
        try
        {
            fis = _context.openFileInput(getRealPath());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ( true )
            {
                int n = fis.read(buf);
                if ( n < 0 )
                    break;

                out.write(buf, 0, n);
            }

            return out.toByteArray();
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
        finally
        {
            try
            {
                if ( fis != null )
                    fis.close();
            }
            catch ( Exception ex )
            {
                throw Kmu.toRuntime(ex);
            }
        }
    }

    public void writeString(CharSequence s)
    {
        writeBytes(s.toString().getBytes());
    }

    public void writeBytes(byte[] arr)
    {
        FileOutputStream out = null;
        try
        {
            out = _context.openFileOutput(getRealPath(), _context.MODE_PRIVATE);
            out.write(arr);
            out.close();
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
        finally
        {
            try
            {
                if ( out != null )
                    out.close();
            }
            catch ( Exception ex )
            {
                throw Kmu.toRuntime(ex);
            }
        }
    }

    public void delete()
    {
        _context.deleteFile(getRealPath());
    }

}
