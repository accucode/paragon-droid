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

import android.view.View;

import com.kodemore.view.KmActivity;
import com.kodemore.view.KmTwoLineListView;

/**
 * I list the attributes of android.os.Build.*
 */
public class TyBuildAttributesActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTwoLineListView _listView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _listView = new KmTwoLineListView(ui());
        _listView.addItem("version.codename", android.os.Build.VERSION.CODENAME);
        _listView.addItem("version.incremental", android.os.Build.VERSION.INCREMENTAL);
        _listView.addItem("version.release", android.os.Build.VERSION.RELEASE);
        _listView.addItem("version.sdk", android.os.Build.VERSION.SDK);
        _listView.addItem("version.sdk_int", android.os.Build.VERSION.SDK_INT);
        _listView.addItem("board", android.os.Build.BOARD);
        _listView.addItem("bootloader", android.os.Build.BOOTLOADER);
        _listView.addItem("brand", android.os.Build.BRAND);
        _listView.addItem("cpu_abi", android.os.Build.CPU_ABI);
        _listView.addItem("cpu_abi2", android.os.Build.CPU_ABI2);
        _listView.addItem("device", android.os.Build.DEVICE);
        _listView.addItem("display", android.os.Build.DISPLAY);
        _listView.addItem("fingerprint", android.os.Build.FINGERPRINT);
        _listView.addItem("hardware", android.os.Build.HARDWARE);
        _listView.addItem("host", android.os.Build.HOST);
        _listView.addItem("id", android.os.Build.ID);
        _listView.addItem("manufacturer", android.os.Build.MANUFACTURER);
        _listView.addItem("model", android.os.Build.MODEL);
        _listView.addItem("product", android.os.Build.PRODUCT);
        _listView.addItem("radio", android.os.Build.RADIO);
        _listView.addItem("tags", android.os.Build.TAGS);
        _listView.addItem("time", android.os.Build.TIME);
        _listView.addItem("type", android.os.Build.TYPE);
        _listView.addItem("unknown", android.os.Build.UNKNOWN);
        _listView.addItem("user", android.os.Build.USER);
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        return _listView;
    }

}
