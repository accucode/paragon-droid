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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import android.content.Context;
import android.net.Uri;

import com.kodemore.collection.KmList;
import com.kodemore.time.KmTimestamp;
import com.kodemore.utility.KmBridge;
import com.kodemore.utility.Kmu;

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
public abstract class KmFilePath
{
    //##################################################
    //# constants
    //##################################################

    protected static final String SLASH = "/";

    //##################################################
    //# variables
    //##################################################

    /**
     * The nominal path.  The real path used to access the
     * file system may be different for subclasses.
     * See getRealPath();
     */
    private String                _path;

    //##################################################
    //# constructor
    //##################################################

    public KmFilePath()
    {
        setPath(SLASH);
    }

    public KmFilePath(String path)
    {
        setPath(path);
    }

    public KmFilePath(File file)
    {
        setPath(file.getPath());
    }

    //##################################################
    //# accessing 
    //##################################################

    public final void setPath(String e)
    {
        _path = normalize(e);
    }

    public final boolean setDateLastModified(KmTimestamp timeStamp)
    {
        if ( getRealFile() == null )
            return false;

        if ( !getRealFile().exists() )
            return false;

        if ( timeStamp == null )
            return false;

        return getRealFile().setLastModified(timeStamp.getMsSince1970());
    }

    public final String getPath()
    {
        return _path;
    }

    public final boolean hasPath(String e)
    {
        return Kmu.isEqual(getPath(), e);
    }

    public final KmFilePath getParent()
    {
        String path = getPath();

        if ( path.length() == 0 )
            return null;

        int i = path.lastIndexOf(SLASH);
        if ( i < 0 )
            return newFile("");

        return newFile(path.substring(0, i));
    }

    public final boolean hasParent()
    {
        return getParent() != null;
    }

    public final boolean isRoot()
    {
        return !hasParent();
    }

    public final KmFilePath getChild(String child)
    {
        return newFile(join(getPath(), child));
    }

    /**
     * Return the "name" of this path.  Loosely, name is considered to
     * be only the portion after the last path separator (/).
     * 
     * E.g.: 
     *      path: /root/someFolder/file.txt
     *      name: file.txt
     * 
     */
    public final String getName()
    {
        if ( isRoot() )
            return getPath();

        String s;
        s = getPath();
        s = Kmu.removePrefix(s, getParentPath());
        s = Kmu.removePrefix(s, SLASH);
        return s;
    }

    public final String getParentPath()
    {
        return hasParent()
            ? getParent().getPath()
            : null;
    }

    public final KmTimestamp getLastModified()
    {
        if ( exists() )
        {
            long milliseconds = getRealFile().lastModified();

            if ( milliseconds > 0 )
                return KmTimestamp.createFromMsSince1970(milliseconds);
        }
        return null;
    }

    public abstract KmList<KmFilePath> getChildren();

    public KmList<KmFilePath> getChildFolders()
    {
        KmList<KmFilePath> v = new KmList<KmFilePath>();

        for ( KmFilePath e : getChildren() )
            if ( e.isFolder() )
                v.add(e);
        return v;
    }

    public KmList<KmFilePath> getChildFiles()
    {
        KmList<KmFilePath> v = new KmList<KmFilePath>();

        for ( KmFilePath e : getChildren() )
            if ( e.isFile() )
                v.add(e);

        return v;
    }

    protected abstract KmFilePath newFile(String path);

    public abstract boolean isFile();

    public abstract boolean isFolder();

    /**
     * The size of the file in bytes.
     */
    public abstract int getSize();

    public boolean isInternal()
    {
        return false;
    }

    public boolean isExternal()
    {
        return false;
    }

    public boolean isAsset()
    {
        return false;
    }

    //##################################################
    //# stream
    //##################################################

    public abstract InputStream openInputStream();

    public abstract OutputStream openOutputStream();

    public abstract OutputStream openAppendStream();

    public OutputStream openOutputStream(boolean append)
    {
        return append
            ? openAppendStream()
            : openOutputStream();
    }

    //##################################################
    //# reader/writer
    //##################################################

    public abstract Reader openFileReader();

    public abstract Writer openFileWriter();

    public abstract Writer openAppendWriter();

    //##################################################
    //# real path
    //##################################################

    /**
     * The real path.  This may differ from the nominal
     * path depending on the subclass.
     */
    public abstract String getRealPath();

    public File getRealFile()
    {
        return new File(getRealPath());
    }

    public Uri toUri()
    {
        return Uri.fromFile(getRealFile());
    }

    //##################################################
    //# io
    //##################################################

    /**
     * Determine if the media is available and writeable.
     */
    public boolean isAvailable()
    {
        return true;
    }

    public abstract boolean exists();

    /**
     * Delete the associated folder or file.
     * Folders must be empty before they can be deleted.
     * See also deleteAll.
     */
    public abstract boolean delete();

    /**
     * Delete a folders contents recursively, then delete the folder itself.
     * This can also be used for a file.
     * Return true only if the primary path is deleted.
     * This may result in only part of a folders contents being deleted. 
     */
    public boolean deleteAll()
    {
        for ( KmFilePath e : getChildren() )
            if ( !e.deleteAll() )
                return false;

        return delete();
    }

    /**
     * Delete all children within a folder, but not the folder itself.
     * If called on a file, do nothing.
     */
    public boolean deleteAllChildren()
    {
        for ( KmFilePath e : getChildren() )
            if ( !e.deleteAll() )
                return false;

        return true;
    }

    /**
     * Delete all files recursively.  
     * Does not delete folders.
     */
    public boolean deleteAllFiles()
    {
        if ( isFile() )
            return delete();

        for ( KmFilePath e : getChildren() )
            if ( !e.deleteAllFiles() )
                return false;

        return true;
    }

    public abstract boolean createFolder();

    /**
     * Rename the file, return true if successful.
     */
    public boolean renameTo(KmFilePath path)
    {
        return false;
    }

    /**
     * Copy the file, return true if successful.
     */
    public boolean copyTo(KmFilePath path)
    {
        return Kmu.copyBytes(openInputStream(), path.openOutputStream());
    }

    //##################################################
    //# read
    //##################################################

    public final String readString()
    {
        return new String(readBytes());
    }

    public final byte[] readBytes()
    {
        return Kmu.readBytes(openInputStream());
    }

    //##################################################
    //# write
    //##################################################

    public void writeString(CharSequence s)
    {
        writeBytes(s.toString().getBytes());
    }

    public void appendString(CharSequence s)
    {
        appendBytes(s.toString().getBytes());
    }

    public void writeBytes(byte[] arr)
    {
        write(arr, false);
    }

    public void appendBytes(byte[] arr)
    {
        write(arr, true);
    }

    private void write(byte[] arr, boolean append)
    {
        OutputStream out = null;
        try
        {
            out = openOutputStream(append);
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

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return getPath();
    }

    //##################################################
    //# support
    //##################################################

    protected Context getApplicationContext()
    {
        return KmBridge.getInstance().getApplicationContext();
    }

    private String normalize(String s)
    {
        if ( Kmu.isEmpty(s) )
            return "";

        return Kmu.removeAllPrefix(s, SLASH);
    }

    protected int readSize(InputStream in)
    {
        return Kmu.readSize(in);
    }

    protected String join(String a, String b)
    {
        return Kmu.joinPath(a, b);
    }

    protected void fatal(String msg, Object... args)
    {
        Kmu.fatal(msg, args);
    }

    //##################################################
    //# equals
    //##################################################

    /**
     * We consider to file paths to be equal ONLY if they have
     * BOTH the same class and the same path.
     */
    @Override
    public boolean equals(Object e)
    {
        if ( e == null )
            return false;

        if ( !getClass().equals(e.getClass()) )
            return false;

        return ((KmFilePath)e).hasPath(getPath());
    }

    @Override
    public int hashCode()
    {
        return Kmu.toHashCode(getPath());
    }
}
