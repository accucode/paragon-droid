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
import android.os.Environment;
import android.view.View;

import com.kodemore.file.KmExternalFilePath;
import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * This requires API 14 or above
 * 
 * I install an apk.
 */
public class TyInstallPackageActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    private static final String    APP_FILE_NAME = "crasher.apk";
    private static final String    FILE_PATH     = Kmu.joinPath(
                                                     Environment.getExternalStorageDirectory().getPath(),
                                                     Environment.DIRECTORY_DOWNLOADS,
                                                     APP_FILE_NAME);

    //##################################################
    //# variables
    //##################################################

    private KmSimpleIntentCallback _callback;
    private KmExternalFilePath     _file;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _file = new KmExternalFilePath(FILE_PATH);
        _callback = newCallback();
        _callback.register(ui());
    }

    private KmSimpleIntentCallback newCallback()
    {
        return new KmSimpleIntentCallback()
        {
            @Override
            public void handleOk(Intent data)
            {
                toast("Install Successful");
            }

            @Override
            public Intent getRequest()
            {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_INSTALL_PACKAGE);
                i.setData(_file.toUri());
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
        root.addLabel("File Path: " + _file.getPath());
        root.addButton("Install App", newInstallAppAction());
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

        if ( !_file.exists() )
        {
            toast("File doesn't exist");
            return;
        }

        _callback.run();
    }
}
