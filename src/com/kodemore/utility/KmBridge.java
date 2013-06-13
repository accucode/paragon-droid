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

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

/**
 * I provide access to certain information that must be
 * provided by the local application.  The application 
 * should install a speciallized subclass.
 */
public abstract class KmBridge
{
    //##################################################
    //# static
    //##################################################

    private static KmBridge _instance;

    public static void install(KmBridge e)
    {
        _instance = e;
    }

    public static KmBridge getInstance()
    {
        if ( _instance == null )
            KmLog.error("You must install + " + KmBridge.class.getSimpleName());

        return _instance;
    }

    //##################################################
    //# accessing
    //##################################################

    public abstract void handleException(Exception ex);

    public abstract Context getApplicationContext();

    public abstract BluetoothAdapter getBluetoothAdapter();

    public abstract String getActiveSqlPackageName();

    public abstract String getSharedApplicationFolder();
}
