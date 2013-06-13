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
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmOneLineView;
import com.kodemore.view.KmRowLayout;

/**
 * I demonstrate how to refresh the list's item display, without reloading
 * the list.  Advantages:
 * 
 *      1) We avoid the often significant overhead of reloading the entire list.
 *      2) The list maintains its other state, such as scroll positions.
 *
 * Summary:
 * 
 *      listView.invalidate
 *          This does NOT work.
 *          The individual items within the list are NOT updated.
 *          
 *      listView.invalidateViews
 *          This DOES work.
 *          It appears that this is usually our best option.
 *      
 *      listView.notifyDataSetChanged
 *          This DOES work.
 *          This is likely a more heavy handled approach that invalidateViews.
 *      
 * Which approach is best?
 *      At this point we don't yet have a good answer.  For the cases found thus far,
 *      invalidateViews and notifyDataSetChanged both work.  In theory, we should probably
 *      use invalidateViews when the underlying data remains the same, but the views need
 *      to be updated.  And we should use notifyDataSetChanged when the underlying data
 *      (in the adapter) has changed.
 */
public class TyListRefreshActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private int                 _repeat;
    private KmListView<Integer> _listView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _repeat = 1;
        _listView = newListView();
        _listView.addItems(Kmu.getIntegers(20));
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
                view.setText(format(e));
                return view;
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addViewFullWeight(_listView);

        KmRowLayout row;
        row = root.addEvenRow();
        row.addButton("One", newOneAction());
        row.addButton("Two", newTwoAction());
        row.addButton("Three", newThreeAction());

        row = root.addEvenRow();
        row.addButton("Invalidate List", newInvalidateAction());
        row.addButton("Invalidate Views", newInvalidateViewsAction());
        row.addButton("Notify Changed", newNotifyDataSetChangedAction());

        return root;
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newOneAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleOne();
            }
        };
    }

    private KmAction newTwoAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleTwo();
            }
        };
    }

    private KmAction newThreeAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleThree();
            }
        };
    }

    private KmAction newInvalidateAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleInvalidate();
            }
        };
    }

    private KmAction newInvalidateViewsAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleInvalidateViews();
            }
        };
    }

    private KmAction newNotifyDataSetChangedAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleNotifyDataSetChanged();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleOne()
    {
        _repeat = 1;
    }

    private void handleTwo()
    {
        _repeat = 2;
    }

    private void handleThree()
    {
        _repeat = 3;
    }

    private void handleInvalidate()
    {
        _listView.invalidate();
    }

    private void handleInvalidateViews()
    {
        _listView.invalidateViews();
    }

    private void handleNotifyDataSetChanged()
    {
        _listView.notifyDataSetChanged();
    }

    //##################################################
    //# utility
    //##################################################

    private String format(Integer e)
    {
        return Kmu.repeat(e + " ", _repeat).trim();
    }

}
