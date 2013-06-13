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

public class TyListSections2Activity
    extends KmActivity
{
    /**
     * 
     * 
     * (steve) collapsable list sections
     * well this is sweet however the list doesn't save its state
     * i suspect it has something to do with the list getting random animals all the time.
     * 
     * (wyatt)
     * Cache the list in a static variable, and retest.
     * Let me know if you need a hand.
     * 
     * reveiw_wyatt (steve) I tried to set a static vairable not sure if i did it right 
     */

    //##################################################
    //# variables
    //##################################################

    private KmListView<Object>    _resultsListView;
    private KmList<Object>        _categoryList;
    private KmList<TyAnimal>      _subCategoryList;
    private static KmList<Object> _resumeList;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        createLists();

        _resultsListView = newListView();
        _resultsListView.setItems(_categoryList);
        _resultsListView.setFastScrollEnabled();
        _resultsListView.setAutoSave();
    }

    //==================================================
    //= init :: list
    //==================================================

    private void createLists()
    {
        KmList<TyAnimal> animalList;
        animalList = new KmList<TyAnimal>();

        KmList<Object> colorList;
        colorList = new KmList<Object>();

        String color = null;

        for ( TyAnimal animal : getAnimals() )
        {
            if ( animal.getColor().equals(color) )
            {
                animalList.add(animal);
                color = animal.getColor();
            }

            if ( !animal.getColor().equals(color) )
            {
                colorList.add(new TyCollapsableLineObject(animal.getColor()));
                animalList.add(animal);

                color = animal.getColor();
            }
        }
        _categoryList = colorList;
        _subCategoryList = animalList;
        _resumeList = colorList;
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
                KmOneLineView view;
                view = KmOneLineView.createOrCast(ui(), oldView);

                if ( item instanceof TyCollapsableLineObject )
                {
                    TyCollapsableLineObject e = (TyCollapsableLineObject)item;

                    view.setTextBold(e.getLabel());
                    view.setBackgroundColor(Color.WHITE);
                    view.setTextColor(Color.BLUE);
                    view.setPadding(20);
                    return view;
                }

                if ( item instanceof TyAnimal )
                {
                    TyAnimal e = (TyAnimal)item;
                    view.setText(e.getDisplayString());
                    view.setBackgroundColor(Color.BLACK);
                    view.setTextColor(Color.LTGRAY);
                    view.setPadding(0);
                    return view;
                }

                return view;
            }

            @Override
            protected void onItemClick(Object e)
            {
                if ( e instanceof TyAnimal )
                {
                    TyAnimal animal = (TyAnimal)e;
                    toast(animal.getDisplayString());
                }

                if ( e instanceof TyCollapsableLineObject )
                {
                    TyCollapsableLineObject collapsable = (TyCollapsableLineObject)e;

                    if ( !collapsable.isActive() )
                    {
                        addItemSubCategoryLines(e);
                        return;
                    }
                    if ( collapsable.isActive() )
                    {
                        removeItemSubCategoryLines(e);
                        return;
                    }
                }
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

        if ( e instanceof TyCollapsableLineObject )
        {
            String s = ((TyCollapsableLineObject)e).getLabel();
            if ( s == null )
                return "";

            return s;
        }
        return "";
    }

    private void addItemSubCategoryLines(Object categoryItem)
    {
        int i = _resultsListView.getAdapter().getPosition(categoryItem);

        TyCollapsableLineObject collapsableLine;
        collapsableLine = (TyCollapsableLineObject)categoryItem;
        collapsableLine.setActive(true);

        String color = collapsableLine.getLabel();
        KmList<TyAnimal> v = getAnimalsByColor(color);

        _resultsListView.addItemsAt(v, i + 1);
    }

    private void removeItemSubCategoryLines(Object item)
    {
        TyCollapsableLineObject collapsableLine;
        collapsableLine = (TyCollapsableLineObject)item;
        collapsableLine.setActive(false);

        String color = collapsableLine.getLabel();

        for ( Object e : getAnimalsByColor(color) )

            if ( e instanceof TyAnimal )
            {
                TyAnimal animal = (TyAnimal)e;

                if ( animal.hasColor(color) )
                    _resultsListView.removeItem(e);
            }
    }

    private KmList<TyAnimal> getAnimalsByColor(String color)
    {
        KmList<TyAnimal> list;
        list = new KmList<TyAnimal>();

        for ( TyAnimal e : _subCategoryList )
            if ( color.equals(e.getColor()) )
                list.add(e);

        list.sortOn(TyAnimal.tools.getColorComparator());
        return list;
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addSpace();
        root.addView(_resultsListView);
        root.addSpace();
        return root;
    }

    //##################################################
    //# lifecycle
    //##################################################//

    @Override
    protected void onPause()
    {
        _resumeList = _resultsListView.getItems();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        if ( isRestart() )
            _resultsListView.setItems(_resumeList);

        super.onResume();
    }
}
