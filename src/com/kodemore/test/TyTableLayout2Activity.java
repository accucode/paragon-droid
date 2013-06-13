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
import com.kodemore.view.KmButton;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTableLayout;
import com.kodemore.view.KmTableRow;

public class TyTableLayout2Activity
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
        KmColumnLayout root;
        root = ui().newColumn();

        addTable1(root);
        addTable2(root);
        addTable3(root);
        addTable4(root);
        addTable5(root);

        return root.inScrollView();
    }

    private void addTable1(KmColumnLayout root)
    {
        root.addSpace();
        root.addBoldLabel("Defaults");

        KmTableLayout table;
        table = root.addTable();

        KmTableRow row;
        row = table.addRow();
        row.addButton("aaa");
        row.addButton("bbb");
        row.addButton("ccc");

        row = table.addRow();
        row.addButton("xxx");
        row.addButton("yyy");
        row.addButton("zzz");
    }

    private void addTable2(KmColumnLayout root)
    {
        root.addSpace();
        root.addBoldLabel("Stretch all columns");

        KmTableLayout table;
        table = root.addTable();
        table.setStretchAllColumns();

        KmTableRow row;
        row = table.addRow();
        row.addButton("aaa");
        row.addButton("bbb");
        row.addButton("ccc");

        row = table.addRow();
        row.addButton("xxx");
        row.addButton("yyy");
        row.addButton("zzz");
    }

    private void addTable3(KmColumnLayout root)
    {
        root.addSpace();
        root.addBoldLabel("Skip and span");

        KmTableLayout table;
        KmTableRow row;
        KmButton button;

        table = root.addTable();
        table.setStretchAllColumns();

        row = table.addRow();
        row.addButton("aaa");
        row.addButton("bbb");
        row.addButton("ccc");

        row = table.addRow();
        row.addButton("ddd");
        row.skip();
        row.addButton("eee");

        row = table.addRow();
        button = row.addButton("xxx");
        row.getLayoutParamsFor(button).span = 2;

        row.addButton("yyy");
    }

    private void addTable4(KmColumnLayout root)
    {
        root.addSpace();
        root.addBoldLabel("Column width depends on contents");

        KmTableLayout table;
        table = root.addTable();
        table.setStretchAllColumns();

        KmTableRow row;
        row = table.addRow();
        row.addButton("a");
        row.addButton("b");
        row.addButton("ccc ccc ccc ccc ccc");

        row = table.addRow();
        row.addButton("x");
        row.addButton("y");
        row.addButton("zzz zzz zzz zzz zzz");
    }

    private void addTable5(KmColumnLayout root)
    {
        root.addSpace();
        root.addBoldLabel("Force columns to equal widths.");

        KmTableLayout table;
        table = root.addTable();
        table.setStretchAllColumns();
        table.setRowItemWidth(0);

        KmTableRow row;
        row = table.addRow();
        row.addButton("aaa");
        row.addButton("bbb");
        row.addButton("ccc");

        row = table.addRow();
        row.addButton("xxx");
        row.addButton("yyy");
    }
}
