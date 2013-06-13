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
import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmCheckListView;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmContextMenuHelper;
import com.kodemore.view.KmRowLayout;

/**
 * Test class for check list.
 * this demostrates a simple check box list view.
 */
public class TyCheckList1Activity
    extends KmActivity
{
    //##################################################
    //# variables 
    //##################################################

    private KmCheckListView<Integer> _checkListView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _checkListView = newCheckList();
        _checkListView.setItems(Kmu.range(1, 10));
        _checkListView.setAutoSave();
    }

    private KmCheckListView<Integer> newCheckList()
    {
        return new KmCheckListView<Integer>(ui())
        {
            @Override
            protected View getContentView(Integer item)
            {
                return ui().newOneLineView(item);
            }

            @Override
            protected void onCreateContextMenu(KmContextMenuHelper menu, Integer item)
            {
                menu.setTitle("Menu for " + item);
                menu.addAction("Some Action", newToastAction("Action for " + item));
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmRowLayout row;
        row = ui().newEvenRow();
        row.addButton("Toast Selected", newButtonAction());
        row.addButton("Check All", newCheckAllAction());
        row.addButton("Un-Check All", newUnCheckAllAction());

        KmColumnLayout root;
        root = ui().newColumn();
        root.addView(row);
        root.addViewFullWeight(_checkListView);

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newButtonAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleButton();
            }
        };
    }

    private KmAction newCheckAllAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleCheckAll();
            }
        };
    }

    private KmAction newUnCheckAllAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleUnCheckAll();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleButton()
    {
        KmStringBuilder out;
        out = new KmStringBuilder();
        out.println("The following are checked");
        out.println();

        KmList<Integer> list = _checkListView.getEnabledCheckedItems();

        for ( Integer e : list )
            out.println(e);

        toast(out);
    }

    private void handleCheckAll()
    {
        _checkListView.checkAll();
    }

    private void handleUnCheckAll()
    {
        _checkListView.unCheckAll();
    }
}
