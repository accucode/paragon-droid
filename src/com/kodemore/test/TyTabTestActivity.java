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
import com.kodemore.view.KmListView;
import com.kodemore.view.KmTab;
import com.kodemore.view.KmTabActivity;
import com.kodemore.view.KmTabHost;
import com.kodemore.view.KmTwoLineView;
import com.kodemore.view.RR;

/**
 * This is a test to demonstrate the issue : 
 * 
 * W/PhoneWindow(16856): Previously focused view reported id 2002 during save, 
 * but can't be found during restore.
 * 
 * Either tab will have the same issue. The first one will have an issue on a 
 * textfield, the second will have it on the list.
 * 
 * I tried changing the order of the layouts and the same issue just manifests 
 * with different views.
 * 
 * If you change the order of "init" it won't affect which view this issue 
 * manifests with.
 */
public class TyTabTestActivity
    extends KmTabActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmListView<TyAnimal> _list;

    //##################################################
    //# layout
    //##################################################

    private void init()
    {
        KmList<TyAnimal> items;
        items = TyAnimal.tools.getRandomAnimals();
        items.sortOn(TyAnimal.tools.getTypeComparator());

        _list = newList();
        _list.addItems(items);
        _list.setFastScrollEnabled();
        _list.setAutoSave();
    }

    @Override
    protected void installTabsOn(KmTabHost host)
    {
        init();

        KmTab tab;
        tab = host.addTab();
        tab.setIndicator("List as Activity", RR.drawable.homeIcon);
        tab.setContent(TyTabTestHostedActivity.class);
        tab.setup();

        tab = host.addTab();
        tab.setIndicator("List as View", RR.drawable.viewIcon);
        tab.setContent(_list);

        tab.setup();
    }

    //##################################################
    //# list
    //##################################################

    private KmListView<TyAnimal> newList()
    {
        return new KmListView<TyAnimal>(ui())
        {
            @Override
            protected View getView(TyAnimal e, int index, View oldView)
            {
                String type = e.getType();
                String color = e.getColor();

                KmTwoLineView view;
                view = KmTwoLineView.createOrCast(ui(), oldView);
                view.setLine1(type);
                view.setLine2(color);
                return view;
            }

            @Override
            protected void onItemClick(TyAnimal e)
            {
                toast(e.getDisplayString());
            }

            @Override
            protected CharSequence getSectionNameFor(TyAnimal e)
            {
                return e.getType();
            }
        };
    }

    //##################################################
    //# override
    //##################################################

    /**
     * this method gives us a handle to do stuff 
     * each time the tab is changed from one activity
     * to another.
     * THIS IS NOT REQUIRED TO MAKE A TABBED ACTIVITY WORK!
     */
    @Override
    protected void handleOnTabChanged()
    {
        super.handleOnTabChanged();

        // do stuff...
    }
}
