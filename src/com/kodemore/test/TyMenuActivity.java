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
import com.kodemore.view.KmMenuHelper;

/**
 * I test the menu.  I.e.: the roll-up menu that commonly
 * provides access to things like options and preferences
 * via the devices "menu" button. 
 */
public class TyMenuActivity
    extends KmActivity
{
    //##################################################
    //# config
    //##################################################

    @Override
    public boolean onCreateOptionsMenu(KmMenuHelper h)
    {
        h.addOptions(newToastAction("Options"));
        h.addUpgrade(newToastAction("Upgrade"));
        h.addInfo(newToastAction("KodeMore"));
        h.addEdit(newToastAction("Edit"));
        h.addSync(newToastAction("Sync"));
        h.addHelp(newToastAction("Help"));
        return true;
    }

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
        return ui().newTextView("Try the 'Menu...'");
    }

}
