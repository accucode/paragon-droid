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
import java.io.IOException;

import android.content.Context;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;


public class KmPrivateFile
    extends KmFile
{
    //##################################################
    //# constructor
    //##################################################//

    public KmPrivateFile(Context context)
    {
        super(context);
    }

    public KmPrivateFile(Context context, String path)
    {
        super(context, path);
    }

    //##################################################
    //# io
    //##################################################//

    @Override
    public boolean exists()
    {
        String[] arr = getContext().fileList();
        for ( String e : arr )
            if ( e.equals(getRealPath()) )
                return true;

        return false;
    }

    @Override
    public byte[] readBytes()
    {
        FileInputStream in = null;
        try
        {
            in = getContext().openFileInput(getRealPath());
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
        int mode = getContext().MODE_PRIVATE;
        _write(arr, mode);
    }

    @Override
    public void appendBytes(byte[] arr)
    {
        int mode = getContext().MODE_APPEND;
        _write(arr, mode);
    }

    private void _write(byte[] arr, int mode)
    {
        FileOutputStream out = null;
        try
        {
            Context context = getContext();
            out = context.openFileOutput(getRealPath(), mode);
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
        getContext().deleteFile(getRealPath());
    }

    @Override
    public boolean createFolder()
    {
        // No Action.
        // The private storage does not support subfolders.
        return true;
    }

    @Override
    public boolean isFile()
    {
        return true;
    }

    @Override
    public boolean isFolder()
    {
        return false;
    }

    @Override
    public KmFile getParent()
    {
        return null;
    }

    @Override
    public KmList<KmFile> getChildren()
    {
        return null;
    }

    //##################################################
    //# support
    //##################################################//

    @Override
    protected KmFile newPath(String path)
    {
        return new KmPrivateFile(getContext(), path);
    }

}
