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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.content.Context;
import android.net.Uri;

import com.kodemore.collection.KmList;


/**
 * Generic access to files.
 * 
 * Note: if you want to access the external file system (sd card)
 * then you must declare the permission in the AndroidManifest.xml
 * file.  
 * 
 *      <manifest...>
 *          <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *          <application...>
 */
public abstract class KmFile
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

    public KmFile(Context context)
    {
        this(context, "/");
    }

    public KmFile(Context context, String path)
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

    public String getRealPath()
    {
        return getPath();
    }

    public File getRealFile()
    {
        return new File(getRealPath());
    }

    public abstract KmFile getParent();

    public abstract KmList<KmFile> getChildren();

    protected abstract KmFile newPath(String path);

    public abstract boolean isFile();

    public abstract boolean isFolder();

    public Uri toUri()
    {
        return Uri.fromFile(getRealFile());
    }

    //##################################################
    //# io
    //##################################################//

    /**
     * Determine if the media is available and writeable.
     */
    public boolean isAvailable()
    {
        return true;
    }

    public abstract boolean exists();

    public abstract void delete();

    public abstract boolean createFolder();

    //##################################################
    //# read
    //##################################################//

    public String readString()
    {
        return new String(readBytes());
    }

    public abstract byte[] readBytes();

    //##################################################
    //# write
    //##################################################//

    public void writeString(CharSequence s)
    {
        writeBytes(s.toString().getBytes());
    }

    public void appendString(CharSequence s)
    {
        appendBytes(s.toString().getBytes());
    }

    public abstract void writeBytes(byte[] arr);

    public abstract void appendBytes(byte[] arr);

    //##################################################
    //# support
    //##################################################//

    protected Context getContext()
    {
        return _context;
    }

    protected byte[] readBytes(FileInputStream in) throws IOException
    {
        byte[] buf = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while ( true )
        {
            int n = in.read(buf);
            if ( n < 0 )
                break;

            out.write(buf, 0, n);
        }
        return out.toByteArray();
    }

    //##################################################
    //# display
    //##################################################//

    @Override
    public String toString()
    {
        return getPath();
    }
}
