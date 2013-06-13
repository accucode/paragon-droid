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

import com.kodemore.collection.KmList;
import com.kodemore.utility.KmSamples;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmContextMenuHelper;
import com.kodemore.view.KmStringListView;

/**
 * Sample of a context menu via subclassing.
 * This is cleaner and safer.
 * This avoids the need to blindly cast the adapter.
 */
public class TyListContextMenuActivity
    extends KmActivity
{
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
        KmList<String> items = KmSamples.getAnimalTypes();

        KmStringListView list;
        list = newListView();
        list.setItems(items);
        list.setFastScrollEnabled();
        return list;
    }

    private KmStringListView newListView()
    {
        return new KmStringListView(ui())
        {
            @Override
            protected void onItemClick(String item)
            {
                toast(item);
            }

            @Override
            protected void onCreateContextMenu(KmContextMenuHelper menu, String item)
            {
                menu.setTitle("Actions (%s)", item);
                menu.addToastAction("Save", "Save: " + item);
                menu.addToastAction("Delete", "Delete: " + item);
            }

            @Override
            protected CharSequence getSectionNameFor(String e)
            {
                return e;
            }
        };
    }
}
