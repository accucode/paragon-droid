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

package com.kodemore.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;

/**
 * I provide more convenient access to information from the 
 * application's context.
 */
public class KmAppInfo
{
    //##################################################
    //# variables
    //##################################################

    private Context _context;

    //##################################################
    //# options menu
    //##################################################

    public KmAppInfo(Context e)
    {
        _context = e;
    }

    //##################################################
    //# name
    //##################################################

    public String getAppName()
    {
        return "KodeMore Stub";
    }

    //##################################################
    //# package
    //##################################################

    /**
     * The package name of the currently running activity.
     * From the AndroidManifest.xml.
     * This is the formal package name of the activity and can
     * be used to reference the activity in the market.
     */
    public String getPackageName()
    {
        return getContext().getPackageName();
    }

    //##################################################
    //# version
    //##################################################

    /**
     * The consumer facing version number.
     * From the AndroidManifest.xml.
     * While commonly in a format similar to "1.3" there is not 
     * fixed format requirements.  
     */
    public String getVersionName()
    {
        return getPackageInfo().versionName;
    }

    /**
     * The consumer facing version.
     * From the AndroidManifest.xml.
     */
    public Integer getVersionCode()
    {
        return getPackageInfo().versionCode;
    }

    //##################################################
    //# private
    //##################################################

    protected Context getContext()
    {
        return _context;
    }

    protected Resources getResources()
    {
        return getContext().getResources();
    }

    public PackageInfo getPackageInfo()
    {
        try
        {
            return getPackageManager().getPackageInfo(getPackageName(), 0);
        }
        catch ( NameNotFoundException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    public PackageManager getPackageManager()
    {
        return getContext().getPackageManager();
    }

}
