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

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmButton;
import com.kodemore.view.KmCheckListView;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmContextMenuHelper;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextView;
import com.kodemore.view.KmUiManager;

/**
 * this demostrates a simple check box list view showing how
 * the click can be intercepted by the content in the line.
 */
public class TyCheckList2Activity
    extends KmActivity
{
    //##################################################
    //# variables 
    //##################################################

    private KmList<Integer>          _list;

    private KmCheckListView<Integer> _ListView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _list = Kmu.range(1, 10);

        _ListView = new KmCheckListView<Integer>(ui())
        {
            @Override
            protected View getContentView(Integer item)
            {
                LineView view = new LineView(ui(), item, newInlineButtonAction(item));
                view.setOnClickListener(newInlineClickAction(item));

                return view;
            }

            @Override
            protected void onItemClick(Integer item)
            {
                // none
            }

            @Override
            protected void onCreateContextMenu(KmContextMenuHelper menu, Integer item)
            {
                toast("Long Clicked: " + item);
            }
        };
        _ListView.setItems(_list);
        _ListView.setAutoSave();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        KmRowLayout row;
        row = root.addEvenRow();
        row.addButton("Toast Selected", newButtonAction());
        row.addButton("Check All", newCheckAllAction());
        row.addButton("Un-Check All", newUnCheckAllAction());

        root.addViewFullWeight(_ListView);

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

    private KmAction newInlineButtonAction(final Integer item)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleInlineButton(item);
            }
        };
    }

    private KmAction newInlineClickAction(final Integer item)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleLineClick(item);
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleButton()
    {
        KmList<Integer> list = _ListView.getEnabledCheckedItems();

        KmStringBuilder out;
        out = new KmStringBuilder();
        out.println("The following are checked");
        out.println();

        for ( Integer e : list )
            out.println(e.toString());

        toast(out.toString());
    }

    private void handleCheckAll()
    {
        _ListView.checkAll();
    }

    private void handleUnCheckAll()
    {
        _ListView.unCheckAll();
    }

    private void handleInlineButton(Integer item)
    {
        toast("Button pressed on line " + item);
    }

    private void handleLineClick(Integer item)
    {
        toast("Inner View Clicked on line " + item);
    }

    //##################################################
    //# view class
    //##################################################

    private class LineView
        extends KmRowLayout
    {
        public LineView(KmUiManager ui, Integer i, KmAction e)
        {
            super(ui);

            setItemWeightFull();

            KmTextView text = addText("This is line " + i.toString());
            text.setGravity(Gravity.CENTER_VERTICAL);

            setItemWeightNone();

            KmButton b = addButton("Button", e);
            b.setFocusable(false);

            setBackgroundColor(Color.CYAN);
        }
    }
}
