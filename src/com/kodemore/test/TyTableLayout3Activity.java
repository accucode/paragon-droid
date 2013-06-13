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
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

import com.kodemore.view.KmActivity;
import com.kodemore.view.KmButton;
import com.kodemore.view.KmTableLayout;
import com.kodemore.view.KmTableRow;

/**
 * Stretch the rows and columns in a table to fill the view.
 */
public class TyTableLayout3Activity
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
        KmTableLayout table;
        table = new KmTableLayout(ui());
        table.setRowWeightFull();
        table.setStretchAllColumns();
        table.setRowItemHeightMatchParent();

        // This works but the columns stretch relative to their contents.
        // In this case we want the columns to all have matching widths.
        // So we initially force all row items to a width of zero, then
        // allow them to grow to fit.
        table.setRowItemWidth(0);

        KmTableRow row;
        row = table.addRow();
        addButtonTo(row, "1");
        addButtonTo(row, "2");
        addButtonTo(row, "3");

        row = table.addRow();
        addButtonTo(row, "4");
        addButtonTo(row, "5");
        addButtonTo(row, "6");

        row = table.addRow();
        addButtonTo(row, "7");
        addButtonTo(row, "8");
        addButtonTo(row, "9");

        row = table.addRow();
        addButtonTo(row, "Enter");
        addGoButtonTo(row, "GO BUTTON!");

        return table;
    }

    public void addButtonTo(KmTableRow row, String value)
    {
        KmButton button;
        button = row.addButton(value, newToastAction(value));
        button.setTextSize(72);
    }

    private void addGoButtonTo(KmTableRow row, String value)
    {
        KmButton button;
        button = ui().newButton(value, newToastAction(value));
        button.setTextSize(72);

        TableRow.LayoutParams params = new LayoutParams();
        params.span = 2;
        button.setLayoutParams(params);

        row.addView(button);
    }
}
