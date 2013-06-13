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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;

/**
 * I am a file stored on the shared external storage of
 * the device, typically on the sd card.
 *
 * http://developer.android.com/guide/topics/data/data-storage.html
 */
public abstract class KmAbstractExternalFilePath
    extends KmFilePath
{
    //##################################################
    //# constructor
    //##################################################

    public KmAbstractExternalFilePath()
    {
        super();
    }

    public KmAbstractExternalFilePath(String path)
    {
        super(path);
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public String getRealPath()
    {
        return Kmu.joinPath(getRootFolder(), getPath());
    }

    protected abstract String getRootFolder();

    //##################################################
    //# abstract accessing
    //##################################################

    @Override
    public KmList<KmFilePath> getChildren()
    {
        if ( !isFolder() )
            return new KmList<KmFilePath>();

        KmList<KmFilePath> v = new KmList<KmFilePath>();

        String[] children = getRealFile().list();
        if ( children == null )
            return null;

        for ( String child : children )
            v.add(getChild(child));

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

    @Override
    public int getSize()
    {
        return (int)getRealFile().length();
    }

    @Override
    public boolean isExternal()
    {
        return true;
    }

    //##################################################
    //# io
    //##################################################

    @Override
    public boolean isAvailable()
    {
        return Kmu.isExternalStorageAvailable();
    }

    public boolean isAvailableReadOnly()
    {
        return Kmu.isExternalStorageAvailableReadOnly();
    }

    @Override
    public boolean exists()
    {
        return getRealFile().exists();
    }

    @Override
    public boolean delete()
    {
        return getRealFile().delete();
    }

    @Override
    public boolean createFolder()
    {
        String path = getRealPath();
        return Kmu.createFolder(path);
    }

    @Override
    public boolean renameTo(KmFilePath path)
    {
        if ( path.isExternal() )
            return getRealFile().renameTo(path.getRealFile());

        return false;
    }

    //##################################################
    //# stream
    //##################################################

    @Override
    public InputStream openInputStream()
    {
        try
        {
            return new FileInputStream(getRealFile());
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    @Override
    public OutputStream openOutputStream()
    {
        try
        {
            return new FileOutputStream(getRealFile(), false);
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
            return new FileOutputStream(getRealFile(), true);
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    //##################################################
    //# writer/reader
    //##################################################

    @Override
    public Reader openFileReader()
    {
        try
        {
            return new FileReader(getRealFile());
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
            return new FileWriter(getRealFile(), false);
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
            return new FileWriter(getRealFile(), true);
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

}
