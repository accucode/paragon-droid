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

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import com.kodemore.collection.KmList;
import com.kodemore.collection.KmMap;
import com.kodemore.utility.Kmu;

/**
 * Assets are raw files bundled with the application during
 * deployment.  The assets are read only, but do support a 
 * hierarchial folder structure.
 *
 * http://developer.android.com/guide/topics/data/data-storage.html
 */
public class KmAssetFilePath
    extends KmFilePath
{
    //##################################################
    //# static
    //##################################################

    private static final String          ANDROID_ASSET = "android_asset";
    /**
     * A map of paths and their status.
     *      true   - the path is a folder.
     *      false  - the path is a file.
     *      null   - the path does not exist.
     *      no key - we have not tested the path yet.
     */
    private static KmMap<String,Boolean> _folders      = new KmMap<String,Boolean>();

    //##################################################
    //# constructor
    //##################################################

    public KmAssetFilePath()
    {
        super();
    }

    public KmAssetFilePath(String path)
    {
        super(path);
    }

    //##################################################
    //# accessing
    //##################################################

    @Override
    public String getRealPath()
    {
        return join(ANDROID_ASSET, getPath());
    }

    @Override
    public KmList<KmFilePath> getChildren()
    {
        try
        {
            KmList<KmFilePath> v = new KmList<KmFilePath>();

            String[] children = getAssets().list(getPath());
            for ( String c : children )
                v.add(getChild(c));

            return v;
        }
        catch ( IOException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    @Override
    public boolean exists()
    {
        return checkFolder() != null;
    }

    @Override
    public boolean isFile()
    {
        return exists() && !checkFolder();
    }

    @Override
    public boolean isFolder()
    {
        return exists() && checkFolder();
    }

    @Override
    public boolean isAsset()
    {
        return true;
    }

    //##################################################
    //# io
    //##################################################

    @Override
    public boolean delete()
    {
        throwReadOnly();
        return false;
    }

    @Override
    public boolean createFolder()
    {
        throwReadOnly();
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
            return getAssets().open(getPath());
        }
        catch ( IOException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    @Override
    public OutputStream openOutputStream()
    {
        throwReadOnly();
        return null;
    }

    @Override
    public OutputStream openAppendStream()
    {
        throwReadOnly();
        return null;
    }

    //##################################################
    //# reader/writer
    //##################################################

    @Override
    public Reader openFileReader()
    {
        try
        {
            AssetFileDescriptor a = getAssets().openFd(getPath());
            return new FileReader(a.getFileDescriptor());
        }
        catch ( IOException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    @Override
    public Writer openFileWriter()
    {
        throwReadOnly();
        return null;
    }

    @Override
    public Writer openAppendWriter()
    {
        throwReadOnly();
        return null;
    }

    //##################################################
    //# size
    //##################################################

    @Override
    public int getSize()
    {
        int n;

        n = getAssetDescriptorLength();
        if ( n > 0 )
            return n;

        n = getAssetStreamLength();
        if ( n > 0 )
            return n;

        return -1;
    }

    private int getAssetDescriptorLength()
    {
        AssetFileDescriptor fd = null;
        try
        {
            fd = getAssets().openFd(getPath());
            return (int)fd.getLength();
        }
        catch ( IOException ex )
        {
            return -1;
        }
        finally
        {
            Kmu.closeSafely(fd);
        }
    }

    private int getAssetStreamLength()
    {
        return readSize(openInputStream());
    }

    //##################################################
    //# check folder
    //##################################################

    public Boolean checkFolder()
    {
        synchronized (KmAssetFilePath.class)
        {
            String path = getPath();

            if ( !_folders.containsKey(path) )
                _folders.put(path, _checkFolder());

            return _folders.get(path);
        }
    }

    private Boolean _checkFolder()
    {
        if ( !_checkExists() )
            return null;

        if ( _checkFile() )
            return false;

        return true;
    }

    private boolean _checkExists()
    {
        try
        {
            if ( isRoot() )
                return true;

            String name = getName();
            String[] v = getAssets().list(getParent().getPath());
            for ( String e : v )
                if ( e.equals(name) )
                    return true;

            return false;
        }
        catch ( IOException ex )
        {
            return false;
        }
    }

    private boolean _checkFile()
    {
        InputStream in = null;
        try
        {
            in = openInputStream();
            return true;
        }
        catch ( Exception ex )
        {
            Kmu.closeSafely(in);
            return false;
        }
    }

    //##################################################
    //# support
    //##################################################

    @Override
    protected KmFilePath newFile(String path)
    {
        return new KmAssetFilePath(path);
    }

    private AssetManager getAssets()
    {
        return getApplicationContext().getAssets();
    }

    private void throwReadOnly()
    {
        fatal("Assets are read only.");
    }

}
