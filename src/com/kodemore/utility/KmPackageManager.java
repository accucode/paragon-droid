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

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.ContactsContract.Contacts;

import com.kodemore.collection.KmList;

public class KmPackageManager
{
    //##################################################
    //# constructor
    //##################################################

    public KmPackageManager()
    {
        // none
    }

    //##################################################
    //# search
    //##################################################

    public KmList<KmResolveInfo> findAll()
    {
        Intent e = new Intent();
        return queryIntentActivities(e);
    }

    public KmList<KmResolveInfo> findThirdPartyMain()
    {
        KmList<KmResolveInfo> v = findMain();
        removeSystemInfos(v);
        return v;
    }

    public KmList<KmResolveInfo> findThirdPartyMainLauchers()
    {
        KmList<KmResolveInfo> v = findMainLauchers();
        removeSystemInfos(v);

        return v;
    }

    public KmList<KmResolveInfo> findMain()
    {
        Intent e;
        e = new Intent();
        e.setAction(Intent.ACTION_MAIN);
        return queryIntentActivities(e);
    }

    public KmList<KmResolveInfo> findMainLauchers()
    {
        Intent e;
        e = new Intent();
        e.setAction(Intent.ACTION_MAIN);
        e.addCategory(Intent.CATEGORY_LAUNCHER);
        return queryIntentActivities(e);
    }

    public KmList<KmResolveInfo> findPackage(String pkg)
    {
        Intent e;
        e = new Intent();
        e.setPackage(pkg);

        return queryIntentActivities(e);
    }

    public KmList<KmResolveInfo> findIntent(Intent e)
    {
        return queryIntentActivities(e);
    }

    public boolean supports(Intent e)
    {
        return findIntent(e).isNotEmpty();
    }

    public boolean supportsPackage(String pkg)
    {
        return findPackage(pkg).isNotEmpty();
    }

    public KmList<KmResolveInfo> findTest()
    {
        Intent e;
        e = new Intent();
        e.setAction(Intent.ACTION_GET_CONTENT);
        e.setData(Contacts.CONTENT_URI);

        return queryIntentActivities(e);
    }

    /**
     * Find the activities in my own package.
     * Activities must declare an intent in the 
     * androidManifest to be found.
     */
    public KmList<KmResolveInfo> findMyActivities()
    {
        String pkg;
        pkg = getContext().getPackageName();

        Intent i;
        i = new Intent();
        i.setPackage(pkg);

        return queryIntentActivities(i);
    }

    //##################################################
    //# support
    //##################################################

    public PackageManager getInner()
    {
        return getContext().getPackageManager();
    }

    private KmList<KmResolveInfo> queryIntentActivities(Intent intent)
    {
        int flags = 0;
        return queryIntentActivities(intent, flags);
    }

    private KmList<KmResolveInfo> queryIntentActivities(Intent intent, int flags)
    {
        List<ResolveInfo> infos;
        infos = getInner().queryIntentActivities(intent, flags);

        if ( infos == null )
            return new KmList<KmResolveInfo>();

        return wrapResolveInfos(infos);
    }

    private KmList<KmResolveInfo> wrapResolveInfos(List<ResolveInfo> infos)
    {
        if ( infos == null )
            return null;

        KmList<KmResolveInfo> v = new KmList<KmResolveInfo>();
        for ( ResolveInfo e : infos )
            v.add(new KmResolveInfo(this, e));

        return v;
    }

    private void removeSystemInfos(KmList<KmResolveInfo> v)
    {
        Iterator<KmResolveInfo> i = v.iterator();
        while ( i.hasNext() )
            if ( i.next().isSystemApplication() )
                i.remove();
    }

    private Context getContext()
    {
        return KmBridge.getInstance().getApplicationContext();
    }

}
