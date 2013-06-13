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

import java.util.List;

import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmListDialogManager;
import com.kodemore.view.KmTextView;

/**
 * I use a popup list dialog, and preserve it across
 * restarts (e.g.: when you reorient the screen).
 * 
 * Automatically manage the dialog if the app restarts.
 */
public class TyListDialog2Activity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmListDialogManager<TyAnimal> _dialog;
    private KmTextView                    _selected;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _dialog = new KmListDialogManager<TyAnimal>()
        {
            @Override
            protected String getDisplayName(TyAnimal e)
            {
                return e.getDisplayString();
            }

            @Override
            protected List<TyAnimal> getItems()
            {
                return getList();
            }
        };
        _dialog.register(ui());

        _dialog.setTitle("Pick an Animal");
        _dialog.setOnItemSelectedListener(newDialogAction());
        _dialog.addCancelButton();

        _selected = ui().newTextView();
    }

    private KmList<TyAnimal> getList()
    {
        KmList<TyAnimal> v;
        v = TyAnimal.tools.getRandomAnimals();

        return v;
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("List", newButtonAction());
        root.addView(_selected);
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
                _dialog.show();
            }
        };
    }

    private KmAction newDialogAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                TyAnimal e = _dialog.getSelectedItem();

                _selected.setText(e.getDisplayString());
            }
        };
    }

}
