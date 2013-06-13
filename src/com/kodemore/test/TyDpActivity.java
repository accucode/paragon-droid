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

import android.util.TypedValue;
import android.view.View;

import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmImageView;
import com.kodemore.view.RR;

/**
 * An activity that changes between two different layouts 
 * depending on orientation.  
 */
public class TyDpActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private int _dp;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _dp = 100;
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        root.setItemWidth(convertDpToPx(_dp));
        root.setItemHeight(convertDpToPx(_dp));

        KmImageView iv = new KmImageView(ui());
        iv.setImage(RR.drawable.managerIcon);

        root.addView(iv);

        return root;
    }

    //##################################################
    //# utility
    //##################################################

    private int convertDpToPx(int dp)
    {
        float px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            getDisplay().getMetrics());

        return (int)px;
    }
}
