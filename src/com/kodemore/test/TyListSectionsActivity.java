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
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmOneLineView;

/**
 * A list of sorted animals with seperators placing animals in
 * sections by color. 
 */
public class TyListSectionsActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmList<Object>     _list;
    private KmListView<Object> _listView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _list = newList();

        _listView = newListView();
        _listView.setItems(_list);
        _listView.setFastScrollEnabled();
    }

    //==================================================
    //= init :: list
    //==================================================

    private KmList<Object> newList()
    {
        KmList<Object> v = new KmList<Object>();
        String color = null;

        for ( TyAnimal e : getAnimals() )
        {
            if ( !e.hasColor(color) )
            {
                color = e.getColor();
                v.add(color);
            }
            v.add(e);
        }

        return v;
    }

    private KmList<TyAnimal> getAnimals()
    {
        KmList<TyAnimal> v;
        v = TyAnimal.tools.getRandomAnimals();
        v.sortOn(TyAnimal.tools.getColorComparator());
        return v;
    }

    //==================================================
    //= init :: list view
    //==================================================

    private KmListView<Object> newListView()
    {
        return new KmListView<Object>(ui())
        {
            @Override
            protected View getView(Object item, int index, View oldView)
            {
                if ( item instanceof TyAnimal )
                {
                    KmOneLineView view;
                    view = KmOneLineView.createOrCast(ui(), oldView);
                    view.setText(((TyAnimal)item).getDisplayString());
                    return view;
                }

                if ( item instanceof String )
                {
                    KmOneLineView view;
                    view = KmOneLineView.createOrCast(ui(), oldView);
                    view.setBackgroundColor(Color.WHITE);
                    view.setTextBold(item);
                    view.setTextColor(Color.BLUE);
                    return view;
                }

                return ui().newOneLineView(null);
            }

            @Override
            protected void onItemClick(Object item)
            {
                if ( item instanceof TyAnimal )
                    toast(((TyAnimal)item).getDisplayString());

                if ( item instanceof String )
                    toast("Category: " + item);
            }

            @Override
            protected CharSequence getSectionNameFor(Object e)
            {
                return getRolodexString(e);
            }
        };
    }

    private CharSequence getRolodexString(Object e)
    {
        if ( e instanceof TyAnimal )
        {
            String s = ((TyAnimal)e).getDisplayString();
            if ( s == null )
                return "";

            return s;
        }

        if ( e instanceof String )
        {
            String s = "" + e;

            return s;
        }
        return "";
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addView(_listView);
        return root;
    }
}
