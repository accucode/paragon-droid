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
import com.kodemore.types.KmAccount;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmTwoLineView;

/**
 * This activity lists all user accounts that are on a
 * particular android device. Note it will display
 * nothing if no accounts exist. 
 * 
 * PERMISSIONS in AndroidManifest.xml
 *     <uses-permission android:name="android.permission.GET_ACCOUNTS" />
 */
public class TyUserListActivity
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
        KmList<KmAccount> v;
        v = KmAccount.tools.getAccounts();

        KmListView<KmAccount> list;
        list = newListView();
        list.addItems(v);
        list.setFastScrollEnabled();
        return list;
    }

    private KmListView<KmAccount> newListView()
    {
        return new KmListView<KmAccount>(ui())
        {
            @Override
            protected View getView(KmAccount e, int index, View oldView)
            {
                String type = e.getType();
                String name = e.getName();

                KmTwoLineView view;
                view = KmTwoLineView.createOrCast(ui(), oldView);
                view.setLine1(type);
                view.setLine2(name);
                return view;
            }

            @Override
            protected void onItemClick(KmAccount item)
            {
                toast(item.getDisplayString());
            }

            @Override
            protected CharSequence getSectionNameFor(KmAccount e)
            {
                return e.getName();
            }
        };
    }
}
