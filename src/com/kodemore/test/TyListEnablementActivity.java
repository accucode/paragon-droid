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

import com.kodemore.utility.Kmu;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmOneLineView;

/**
 * Provide a simple example controling which items in a listView are enabled.
 * Only the even rows (starting at 0) are enabled.
 */
public class TyListEnablementActivity
    extends KmActivity
{
    //##################################################
    //# create
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
        KmListView<Integer> root;
        root = newListView();
        root.addItems(Kmu.getIntegers(20));
        return root;
    }

    private KmListView<Integer> newListView()
    {
        return new KmListView<Integer>(ui())
        {
            @Override
            protected View getView(Integer e, int index, View oldView)
            {
                KmOneLineView view;
                view = KmOneLineView.createOrCast(ui(), oldView);
                view.setText(e);
                return view;
            }

            @Override
            public boolean areAllItemsEnabled()
            {
                return false;
            }

            @Override
            public boolean isEnabled(int index)
            {
                return Kmu.isEven(index);
            }

            @Override
            protected void onItemClick(Integer item)
            {
                alert("Clicked: " + item);
            }
        };
    }

}
