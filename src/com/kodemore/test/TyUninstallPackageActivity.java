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

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.utility.KmPackageManager;
import com.kodemore.utility.KmResolveInfo;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * This requires API 14 or above
 * 
 * I uninstall an apk.
 */
public class TyUninstallPackageActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    private static final String    APP_NAME = "Crasher";

    //##################################################
    //# variables
    //##################################################

    private KmSimpleIntentCallback _callback;
    private String                 _packageName;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _callback = newCallback();
        _callback.register(ui());

        _packageName = findPackageName();
    }

    private KmSimpleIntentCallback newCallback()
    {
        return new KmSimpleIntentCallback()
        {
            @Override
            public void handleOk(Intent data)
            {
                toast("Uninstall Successful");
            }

            @Override
            public Intent getRequest()
            {
                String path = "package:" + _packageName;
                Uri uri = Uri.parse(path);

                Intent i = new Intent();
                i.setAction(Intent.ACTION_UNINSTALL_PACKAGE);
                i.setData(uri);
                i.putExtra(Intent.EXTRA_RETURN_RESULT, true);
                return i;
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addLabel(_packageName);
        root.addButton("Uninstall App", newInstallAppAction());
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newInstallAppAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleInstallApp();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleInstallApp()
    {
        if ( _packageName == null )
        {
            toast("App Doesn't Exist");
            return;
        }

        _callback.run();
    }

    //##################################################
    //# utility
    //##################################################

    private String findPackageName()
    {
        KmPackageManager pm = new KmPackageManager();
        KmList<KmResolveInfo> v;

        v = pm.findThirdPartyMainLauchers();

        for ( KmResolveInfo e : v )
            if ( APP_NAME.equals(e.getApplicationLabel()) )
                return e.getActivityPackage();

        return null;
    }
}
