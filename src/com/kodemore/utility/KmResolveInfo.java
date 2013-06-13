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

import java.util.Comparator;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

public class KmResolveInfo
{
    //##################################################
    //# variables
    //##################################################

    private KmPackageManager _packageManager;
    private ResolveInfo      _inner;

    //##################################################
    //# constructor
    //##################################################

    public KmResolveInfo(KmPackageManager pm, ResolveInfo info)
    {
        _packageManager = pm;
        _inner = info;
    }

    //##################################################
    //# application
    //##################################################

    public String getApplicationName()
    {
        return getApplicationInfo().name;
    }

    public String getApplicationPackage()
    {
        return getApplicationInfo().packageName;
    }

    public String getApplicationLabel()
    {
        PackageManager pm = getPackageManager().getInner();
        CharSequence s = getApplicationInfo().loadLabel(pm);
        return Kmu.toStringSafe(s);
    }

    public String getApplicationDescription()
    {
        PackageManager pm = getPackageManager().getInner();
        CharSequence s = getApplicationInfo().loadDescription(pm);
        return Kmu.toStringSafe(s);
    }

    public Drawable getApplicationIcon()
    {
        PackageManager pm = getPackageManager().getInner();
        return getApplicationInfo().loadIcon(pm);
    }

    public String getApplicationVersionName()
    {
        return getPackageInfo().versionName;
    }

    public int getApplicationVersionCode()
    {
        return getPackageInfo().versionCode;
    }

    /**
     * Is this application part of the original install
     * that is part of a hard reset.
     */
    public boolean isSystemApplication()
    {
        return hasApplicationFlag(ApplicationInfo.FLAG_SYSTEM);
    }

    //##################################################
    //# activity
    //##################################################

    public boolean isActivity()
    {
        return getInner().activityInfo != null;
    }

    public String getActivityName()
    {
        return getInner().activityInfo.name;
    }

    public String getActivityPackage()
    {
        return getInner().activityInfo.packageName;
    }

    public Intent toIntent()
    {
        Intent e;
        e = new Intent();
        e.setClassName(getActivityPackage(), getActivityName());
        return e;
    }

    //##################################################
    //# service
    //##################################################

    public boolean isService()
    {
        return getInner().serviceInfo != null;
    }

    //##################################################
    //# support
    //##################################################

    private ResolveInfo getInner()
    {
        return _inner;
    }

    private KmPackageManager getPackageManager()
    {
        return _packageManager;
    }

    private ApplicationInfo getApplicationInfo()
    {
        return isActivity()
            ? getInner().activityInfo.applicationInfo
            : getInner().serviceInfo.applicationInfo;
    }

    private boolean hasApplicationFlag(int i)
    {
        int flags = getApplicationInfo().flags;
        return (flags & i) == i;
    }

    private PackageInfo getPackageInfo()
    {
        try
        {
            String pkg = getApplicationPackage();
            int flags = 0;
            PackageManager pm = getPackageManager().getInner();
            return pm.getPackageInfo(pkg, flags);
        }
        catch ( NameNotFoundException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    //##################################################
    //# comparators
    //##################################################

    public static Comparator<KmResolveInfo> getApplicationLabelComparator()
    {
        return new Comparator<KmResolveInfo>()
        {
            @Override
            public int compare(KmResolveInfo info1, KmResolveInfo info2)
            {
                String a = info1.getApplicationLabel();
                String b = info2.getApplicationLabel();

                return a.compareToIgnoreCase(b);
            }
        };
    }

}
