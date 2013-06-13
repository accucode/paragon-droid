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
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmCheckBox;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmSpinner;
import com.kodemore.view.KmTextField;
import com.kodemore.view.KmTextView;
import com.kodemore.view.KmTwoLineView;

/**
 * This is a test to demonstrate the issue : 
 * 
 * W/PhoneWindow(16856): Previously focused view reported id 2002 during save, but can't be found during restore.
 * 
 * If you open this activity from the tab activity "TyTabTestActivity" you will get this error on rotation.
 * If you open this activity by itself you will not get this error on rotation.
 */
public class TyTabTestHostedActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    private static final String TEXT_VIEW = "TEST";

    //##################################################
    //# variables
    //##################################################

    KmListView<TyAnimal>        _list;
    KmTextView                  _text;
    KmCheckBox                  _checkBox;
    KmSpinner<String>           _spinner;
    KmTextField                 _textField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        KmList<TyAnimal> items;
        items = TyAnimal.tools.getRandomAnimals();
        items.sortOn(TyAnimal.tools.getTypeComparator());

        _list = newList();
        _list.addItems(items);
        _list.setFastScrollEnabled();
        _list.setAutoSave();

        _text = ui().newTextView();
        _text.setValue(TEXT_VIEW);
        _text.setAutoSave();

        _textField = ui().newTextField();
        _textField.setAutoSave();

        _checkBox = ui().newCheckBox();
        _checkBox.setAutoSave();

        _spinner = newSpinner();
        _spinner.setItems(getSpinnerOptions());
        _spinner.setPrompt("SELECT ONE");
        _spinner.setAutoSave();
    }

    //==================================================
    //= init :: list
    //==================================================

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

    //==================================================
    //= init :: spinner
    //==================================================

    private KmList<String> getSpinnerOptions()
    {
        KmList<String> items;
        items = new KmList<String>();
        items.add("first");
        items.add("second");
        items.add("third");
        return items;
    }

    private KmSpinner<String> newSpinner()
    {
        return new KmSpinner<String>(ui())
        {
            @Override
            protected boolean isSpecialValue(String e)
            {
                return false;
            }

            @Override
            protected String getDisplayName(String e)
            {
                return e;
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmRowLayout root = ui().newRow();

        root.setItemWidth(0);
        root.setItemWeightFull();

        KmColumnLayout col = root.addColumn();

        col.addView(_text);
        col.addView(_checkBox);
        col.addView(_textField);
        col.addView(_spinner);

        col = root.addColumn();

        col.addView(_list);

        return root;
    }
}
