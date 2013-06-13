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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import android.content.Context;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;

/**
 * Private files are mutable, but not visible to other
 * applications unless the device is jailbroken.  Private
 * files are not stored on the sd card.  Private files
 * do not support folders.
 *
 * http://developer.android.com/guide/topics/data/data-storage.html
 */
public class KmInternalFilePath
    extends KmFilePath
{
    //##################################################
    //# constructor
    //##################################################

    public KmInternalFilePath()
    {
        super();
    }

    public KmInternalFilePath(String name)
    {
        super(name);
    }

    //##################################################
    //# accessing
    //##################################################

    @Override
    public String getRealPath()
    {
        return getPath();
    }

    @Override
    public KmList<KmFilePath> getChildren()
    {
        if ( !isRoot() )
            return new KmList<KmFilePath>();

        KmList<KmFilePath> v = new KmList<KmFilePath>();

        String[] children = getApplicationContext().fileList();
        for ( String c : children )
            v.add(newFile(c));

        return v;
    }

    @Override
    public boolean isFile()
    {
        return !isFolder();
    }

    @Override
    public boolean isFolder()
    {
        return isRoot();
    }

    @Override
    public boolean isInternal()
    {
        return true;
    }

    //##################################################
    //# io
    //##################################################

    @Override
    public boolean exists()
    {
        String s = getRealPath();
        String[] arr = getApplicationContext().fileList();

        for ( String e : arr )
            if ( e.equals(s) )
                return true;

        return false;
    }

    @Override
    public int getSize()
    {
        return readSize(openInputStream());
    }

    @Override
    public boolean delete()
    {
        return getApplicationContext().deleteFile(getRealPath());
    }

    @Override
    public boolean createFolder()
    {
        // No Action.
        // The private storage does not support subfolders.
        return false;
    }

    @Override
    public boolean renameTo(KmFilePath path)
    {
        if ( path.isInternal() )
            return getRealFile().renameTo(path.getRealFile());

        return false;
    }

    //##################################################
    //# streams
    //##################################################

    @Override
    public InputStream openInputStream()
    {
        try
        {
            return getApplicationContext().openFileInput(getRealPath());
        }
        catch ( FileNotFoundException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    @Override
    public OutputStream openOutputStream()
    {
        try
        {
            return getApplicationContext().openFileOutput(getRealPath(), Context.MODE_PRIVATE);
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    @Override
    public OutputStream openAppendStream()
    {
        try
        {
            return getApplicationContext().openFileOutput(getRealPath(), Context.MODE_APPEND);
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    //##################################################
    //# reader/writer
    //##################################################

    @Override
    public Reader openFileReader()
    {
        try
        {
            FileInputStream fi = getApplicationContext().openFileInput(getRealPath());
            return new FileReader(fi.getFD());
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    @Override
    public Writer openFileWriter()
    {
        try
        {
            FileOutputStream o;
            o = getApplicationContext().openFileOutput(getRealPath(), Context.MODE_PRIVATE);
            return new FileWriter(o.getFD());
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    @Override
    public Writer openAppendWriter()
    {
        try
        {
            FileOutputStream o;
            o = getApplicationContext().openFileOutput(getRealPath(), Context.MODE_APPEND);
            return new FileWriter(o.getFD());
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    //##################################################
    //# support
    //##################################################

    @Override
    protected KmFilePath newFile(String path)
    {
        return new KmInternalFilePath(path);
    }

}
