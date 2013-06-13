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

package com.kodemore.view;

import android.app.Activity;
import android.view.View;

import com.kodemore.utility.Kmu;

/**
 * I implement a simple view specifically for holding
 * a list of activities which are started when clicked.
 */
public class KmActivityListView
    extends KmListView<Class<? extends Activity>>
{
    //##################################################
    //# constructor
    //##################################################

    public KmActivityListView(KmUiManager ui)
    {
        super(ui);
    }

    //##################################################
    //# override
    //##################################################

    @Override
    protected View getView(Class<? extends Activity> item, int index, View oldView)
    {
        KmOneLineView view;
        view = KmOneLineView.createOrCast(ui(), oldView);
        view.setText(format(item));
        return view;
    }

    @Override
    protected void onItemClick(Class<? extends Activity> e)
    {
        Kmu.startActivity(getContext(), e);
    }

    @Override
    protected CharSequence getSectionNameFor(Class<? extends Activity> e)
    {
        return format(e);
    }

    //##################################################
    //# format
    //##################################################

    private String format(Class<? extends Activity> e)
    {
        return Kmu.formatActivity(e);
    }

}
