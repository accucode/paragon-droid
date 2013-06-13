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

package com.kodemore.test.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class TyIntegerServiceConnection
    implements ServiceConnection
{
    //##################################################
    //# variables
    //##################################################

    private TyIntegerServiceBinder _binder;

    //##################################################
    //# connection
    //##################################################

    @Override
    public void onServiceConnected(ComponentName name, IBinder iBinder)
    {
        _binder = (TyIntegerServiceBinder)iBinder;
    }

    @Override
    public void onServiceDisconnected(ComponentName name)
    {
        _binder = null;
    }

    //##################################################
    //# accessing
    //##################################################

    public TyIntegerServiceBinder getBinder()
    {
        return _binder;
    }

    public boolean isConnected()
    {
        return getBinder() != null;
    }
}
