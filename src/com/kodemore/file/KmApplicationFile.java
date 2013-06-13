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

import android.content.Context;
import android.os.Environment;

import com.kodemore.utility.Kmu;


/**
 * Files that are located on the (primary) external storage.
 * The application files are located under a sub directory
 * that includes the package name.  E.g.:
 *     /sdcard/Android/data/com.kodemore.android.stub/files/
 * E.g.: the SD Card.
 */
public class KmApplicationFile
    extends KmNativeFile
{
    //##################################################
    //# constructor
    //##################################################//

    public KmApplicationFile(Context context)
    {
        super(context);
        checkApplicationFolder();
    }

    public KmApplicationFile(Context context, String path)
    {
        super(context, path);
        checkApplicationFolder();
    }

    //##################################################
    //# accessing
    //##################################################//

    @Override
    public String getRealPath()
    {
        return Kmu.joinPath(getApplicationFolder(), getPath());
    }

    //##################################################
    //# override
    //##################################################//

    @Override
    protected String getRootFolder()
    {
        return getApplicationFolder();
    }

    @Override
    protected KmFile newPath(String path)
    {
        return new KmApplicationFile(getContext(), path);
    }

    //##################################################
    //# support
    //##################################################//

    private void checkApplicationFolder()
    {
        Kmu.createFolder(getApplicationFolder());
    }

    private String getApplicationFolder()
    {
        // Per android documentation Mar 2011.
        // http://developer.android.com/guide/topics/data/data-storage.html
        return Kmu.joinPath(
            Environment.getExternalStorageDirectory().getPath(),
            "Android/data",
            getContext().getPackageName(),
            "files");
    }
}
