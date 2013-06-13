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

import com.kodemore.types.KmQuantity;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmQuantityField;
import com.kodemore.view.KmRowLayout;

/**
 * Test the Quantity field.
 */
public class TyQuantityFieldActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmQuantityField _quantityField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _quantityField = ui().newQuantityField();
        _quantityField.setAutoSave();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addLabel("Quantity");
        root.addView(_quantityField);

        KmRowLayout row;
        row = root.addEvenRow();
        row.addButton("Set Null", newSetNullAction());
        row.addButton("Set Value", newSetValueAction());
        row.addButton("Get Value", newGetValueAction());
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newSetValueAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSetValue();
            }
        };
    }

    private KmAction newSetNullAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSetNull();
            }
        };
    }

    private KmAction newGetValueAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleGetValue();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleSetValue()
    {
        KmQuantity qty = new KmQuantity(5.4);
        _quantityField.setValue(qty);
    }

    private void handleSetNull()
    {
        _quantityField.setValue(null);
    }

    private void handleGetValue()
    {
        alert("Value", _quantityField.getValue() + "");
    }

}
