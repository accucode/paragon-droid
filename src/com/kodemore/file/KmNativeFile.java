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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;


/**
 * Files that are located on the (primary) external storage.
 * E.g.: the SD Card.
 */
public class KmNativeFile
    extends KmFile
{
    //##################################################
    //# constructor
    //##################################################//

    public KmNativeFile(Context context)
    {
        super(context);
    }

    public KmNativeFile(Context context, String path)
    {
        super(context, path);
    }

    //##################################################
    //# override
    //##################################################//

    @Override
    public String getRealPath()
    {
        return Kmu.joinPath(getRootFolder(), getPath());
    }

    @Override
    protected KmFile newPath(String path)
    {
        return new KmNativeFile(getContext(), path);
    }

    protected String getRootFolder()
    {
        return "/";
    }

    //##################################################
    //# abstract accessing
    //##################################################//

    @Override
    public KmFile getParent()
    {
        String parent = new File(getPath()).getParent();
        return parent == null
            ? null
            : newPath(parent);
    }

    @Override
    public KmList<KmFile> getChildren()
    {
        if ( !isFolder() )
            return null;

        KmList<KmFile> v = new KmList<KmFile>();

        String[] children = getRealFile().list();
        if ( children == null )
            return null;

        for ( String child : children )
            v.add(newPath(child));

        return v;
    }

    @Override
    public boolean isFile()
    {
        return getRealFile().isFile();
    }

    @Override
    public boolean isFolder()
    {
        return getRealFile().isDirectory();
    }

    //##################################################
    //# io
    //##################################################//

    @Override
    public boolean isAvailable()
    {
        return Kmu.isExternalStorageAvailable();
    }

    @Override
    public boolean exists()
    {
        return getRealFile().exists();
    }

    @Override
    public byte[] readBytes()
    {
        FileInputStream in = null;
        try
        {
            in = new FileInputStream(getRealFile());
            return readBytes(in);
        }
        catch ( IOException ex )
        {
            throw Kmu.toRuntime(ex);
        }
        finally
        {
            Kmu.closeSafely(in);
        }
    }

    @Override
    public void writeBytes(byte[] arr)
    {
        _write(arr, false);
    }

    @Override
    public void appendBytes(byte[] arr)
    {
        _write(arr, true);
    }

    private void _write(byte[] arr, boolean append)
    {
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream(getRealFile(), append);
            out.write(arr);
            out.flush();
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
        finally
        {
            Kmu.closeSafely(out);
        }
    }

    @Override
    public void delete()
    {
        getRealFile().delete();
    }

    @Override
    public boolean createFolder()
    {
        String path = getRealPath();
        return Kmu.createFolder(path);
    }

}
