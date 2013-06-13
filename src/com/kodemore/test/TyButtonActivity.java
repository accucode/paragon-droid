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

import com.kodemore.utility.KmConstantsIF;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmButton;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTableLayout;
import com.kodemore.view.KmTableRow;

public class TyButtonActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmButton _autoSaveButton;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _autoSaveButton = ui().newButton("auto");
        _autoSaveButton.setAutoSave();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        addCommonButtons(root);
        addAutoSaveButton(root);
        addButtonTable(root);

        return root.inScrollView();
    }

    //==================================================
    //= layout :: common buttons
    //==================================================

    private void addCommonButtons(KmColumnLayout root)
    {
        root.addText("Common...");

        KmRowLayout row;
        row = root.addEvenRow();
        row.addView(createSolidButton());
        row.addView(createTwoToneButton());
        row.addView(createGradientButton());

        row = root.addEvenRow();
        row.addView(createWarningButton());
        row.addView(createErrorButton());
    }

    private KmButton createSolidButton()
    {
        KmButton e;
        e = ui().newButton();
        e.setStyleSolid();
        e.setText("solid");
        e.setOnClickToast("solid");
        return e;
    }

    private KmButton createTwoToneButton()
    {
        KmButton e;
        e = ui().newButton();
        e.setStyleTwoTone();
        e.setText("two");
        e.setOnClickToast("two");
        return e;
    }

    private KmButton createGradientButton()
    {
        KmButton e;
        e = ui().newButton();
        e.setStyleGradient();
        e.setText("gradient");
        e.setOnClickToast("gradient");
        return e;
    }

    private KmButton createWarningButton()
    {
        KmButton e;
        e = ui().newButton();
        e.beWarning();
        e.setOnClickToast("warning");
        return e;
    }

    private KmButton createErrorButton()
    {
        KmButton e;
        e = ui().newButton();
        e.beError();
        e.setOnClickToast("error");
        return e;
    }

    //==================================================
    //= layout :: auto save
    //==================================================

    private void addAutoSaveButton(KmColumnLayout root)
    {
        root.addSpace();
        root.addText("AutoSave...");

        KmRowLayout row;
        row = root.addEvenRow();
        row.addButton("aaa", newSetTextAction("aaa"));
        row.addButton("bbb", newSetTextAction("bbb"));
        row.addView(_autoSaveButton);
    }

    //==================================================
    //= layout :: button table
    //==================================================

    private void addButtonTable(KmColumnLayout root)
    {
        root.addSpace();
        root.addText("Table...");
        root.addViewFullWeight(createButtonTable());
    }

    private KmTableLayout createButtonTable()
    {
        KmTableLayout table;
        table = new KmTableLayout(ui());
        table.setStretchAllColumns();
        table.setRowWeightFull();
        table.setRowItemWidth(0);

        KmTableRow row;
        row = table.addRow();
        row.setItemHeightMatchParent();
        addButtonTo(row, "1");
        addButtonTo(row, "2");
        addButtonTo(row, "3");

        row = table.addRow();
        row.setItemHeightMatchParent();
        addButtonTo(row, "4");
        addButtonTo(row, "5");
        addButtonTo(row, "6");

        row = table.addRow();
        row.setItemHeightMatchParent();
        addButtonTo(row, "7");
        addButtonTo(row, "8");
        addButtonTo(row, "9");

        return table;
    }

    private void addButtonTo(KmTableRow row, String msg)
    {
        KmButton e;
        e = row.addButton(msg);
        e.setOnClickToast(msg);
        e.setTextSizeAsPercentOfScreen(KmConstantsIF.BUTTON_PAD_TEXT_SIZE);
        e.setStyleGradient();
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newSetTextAction(final String s)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSetText(s);
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleSetText(String s)
    {
        _autoSaveButton.setText(s);
    }
}
