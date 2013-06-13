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

package com.kodemore.test;

import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.collection.KmOrderedMap;
import com.kodemore.collection.KmUntypedTuple;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmTwoLineListView;

/**
 * Query for other intents (e.g.: activities) already
 * installed on this device.  In particular, this shows
 * how to find the "label" (aka "name") of the application.
 */
public class TyResolveInfoActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    /**
     * The package substring we will search for. 
     */
    private static final String PACKAGE_SUBSTRING = "kodemore";

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        // none
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmTwoLineListView list;
        list = new KmTwoLineListView(ui());
        list.addItems(getItems());
        return list;
    }

    //##################################################
    //# utility
    //##################################################

    private KmList<KmUntypedTuple> getItems()
    {
        ResolveInfo info = getInfo();
        ActivityInfo activityInfo = info.activityInfo;
        ApplicationInfo appInfo = activityInfo.applicationInfo;

        KmOrderedMap<Object,Object> m;
        m = new KmOrderedMap<Object,Object>();
        m.put("Label", info.loadLabel(getPackageManager()));
        m.put("Activity Name", activityInfo.name);
        m.put("Activity Package Name", activityInfo.packageName);
        m.put("Activity Process Name", activityInfo.processName);
        m.put("Activity Target", activityInfo.targetActivity);
        m.put("Activity Non Localized Label", activityInfo.nonLocalizedLabel);
        m.put("App Class Name", appInfo.className);
        m.put("App Name", appInfo.name);
        m.put("App Package Name", appInfo.packageName);
        m.put("App Process Name", appInfo.processName);

        return m.toUntypedList();
    }

    public ResolveInfo getInfo()
    {
        Intent intent;
        intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager mgr;
        mgr = getContext().getPackageManager();

        List<ResolveInfo> infos;
        infos = mgr.queryIntentActivities(intent, 0);

        for ( ResolveInfo info : infos )
        {
            String pkg = info.activityInfo.packageName;
            if ( pkg.contains(PACKAGE_SUBSTRING) )
                return info;
        }

        // if we don't find a match, just use the first activity found.
        return infos.get(0);
    }
}
