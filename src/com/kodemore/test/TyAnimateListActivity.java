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
import com.kodemore.view.KmListView;
import com.kodemore.view.KmTwoLineView;

/**
 * A list with multiple elements per row.
 * Demonstrates using domain objects in a list.
 * Demonstrates using the TwoLineView for list items.
 * Demonstrates overriding getSectionNameFor to display a rolodex. 
 */
public class TyAnimateListActivity
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
    //##################################################//

    @Override
    protected View createLayout()
    {
        KmList<TyAnimal> items;
        items = TyAnimal.tools.getRandomAnimals();
        items.sortOn(TyAnimal.tools.getTypeComparator());

        KmListView<TyAnimal> list;
        list = newList();
        list.addItems(items);
        list.setFastScrollEnabled();
        return list;
    }

    //##################################################
    //# list
    //##################################################//

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
}
