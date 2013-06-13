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

package com.kodemore.view;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kodemore.utility.KmRunnable;
import com.kodemore.utility.Kmu;

/**
 * I implement a simple spinner view backed by an ArrayAdaptor.
 * The intent is to make simple spinners, really, really simple.
 */
public class KmSpinner<K>
    extends Spinner
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;

    private boolean     _showsNull;
    private String      _nullLabel;

    //##################################################
    //# constructor
    //##################################################

    public KmSpinner(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;
        _showsNull = false;

        setAdapter(newAdapter());
    }

    //##################################################
    //# core
    //##################################################

    public KmUiManager ui()
    {
        return _ui;
    }

    public KmSpinnerHelper getHelper()
    {
        return new KmSpinnerHelper(this, ui());
    }

    public void setAutoSave()
    {
        setId();
        setSaveEnabled(true);
    }

    //##################################################
    //# id
    //##################################################

    public void setId()
    {
        if ( !hasId() )
            setId(ui().nextId());
    }

    private boolean hasId()
    {
        return getId() != NO_ID;
    }

    //##################################################
    //# adapter
    //##################################################

    @SuppressWarnings("unchecked")
    @Override
    public ArrayAdapter<K> getAdapter()
    {
        return (ArrayAdapter<K>)super.getAdapter();
    }

    //##################################################
    //# selection
    //##################################################

    @SuppressWarnings("unchecked")
    @Override
    public K getSelectedItem()
    {
        return (K)super.getSelectedItem();
    }

    public void setSelectedItem(K e)
    {
        int i = getIndexOf(e);

        if ( i < 0 )
        {
            clearSelection();
            return;
        }

        setSelection(i);
    }

    public boolean hasSelectedItem()
    {
        return getSelectedItem() != null;
    }

    public boolean hasSelectedItem(K e)
    {
        K item = getSelectedItem();
        return item == null
            ? e == null
            : item.equals(e);
    }

    public void clearSelection()
    {
        setSelection(0);
        setSelected(false);
    }

    public void selectFirst()
    {
        setSelection(0);
    }

    public K getCurrentSelection()
    {
        return getSelectedItem();
    }

    //##################################################
    //# index
    //##################################################

    public boolean isEmpty()
    {
        return getCount() == 0;
    }

    public int getIndexOf(K e)
    {
        int n = getAdapter().getCount();
        for ( int i = 0; i < n; i++ )
            if ( Kmu.isEqual(getItemAt(i), e) )
                return i;

        return -1;
    }

    //##################################################
    //# items
    //##################################################

    public K getItemAt(int index)
    {
        return getAdapter().getItem(index);
    }

    public void setItems(List<K> v)
    {
        clearItems();
        addItems(v);
    }

    public void addItem(K e)
    {
        if ( nullAdded() && isEmpty() )
            getAdapter().add(null);

        getAdapter().add(e);
    }

    public void addItemAt(K e, int i)
    {
        if ( isNullItem(i) )
            i = 1;

        getAdapter().insert(e, i);
    }

    public void addItems(List<K> v)
    {
        ArrayAdapter<K> a = getAdapter();

        if ( nullAdded() && isEmpty() )
            a.add(null);

        for ( K e : v )
            a.add(e);
    }

    public void removeItem(K e)
    {
        getAdapter().remove(e);
    }

    public void removeFirstItem()
    {
        if ( !isEmpty() && nullAdded() )
        {
            removeItemAt(1);
            return;
        }

        if ( !isEmpty() )
            removeItemAt(0);
    }

    public void removeLastItem()
    {
        if ( !isEmpty() )
            removeItemAt(getCount() - 1);
    }

    public void removeItemAt(int index)
    {
        if ( isNullItem(index) )
            return;

        removeItem(getItemAt(index));
    }

    public void clearItems()
    {
        getAdapter().clear();
    }

    //##################################################
    //# add null
    //##################################################

    /**
     * (aaron) here are the methods to add a null value to
     * the top of the list.  The adapter will automatically handle the
     * appropriate display string for this item.  I tried to cover all
     * cases  above so this will always be the first in the list.
     */

    public void showNull()
    {
        showNull("<null>");
    }

    public void showNullNone()
    {
        showNull("<none>");
    }

    /**
     *  This needs to be set before items are added to the spinner
     */
    public void showNullSelect()
    {
        showNull("<Select One>");
    }

    public void showNull(String s)
    {
        _nullLabel = s;

        if ( _showsNull )
            return;

        _showsNull = true;
    }

    public boolean nullAdded()
    {
        return _showsNull;
    }

    public String getNullLabel()
    {
        return _nullLabel;
    }

    public boolean isNullItem(int index)
    {
        return nullAdded() && index == 0;
    }

    //##################################################
    //# adapter
    //##################################################

    /**
     * (aaron) I made some changes here that will 
     * allow for easier subclassing like in MySpinner.
     */
    protected KmArrayAdapter<K> newAdapter()
    {
        return new KmArrayAdapter<K>(ui())
        {
            @Override
            protected View getView(K item, int index, View oldView)
            {
                return getLineView(item, index);
            }

            @Override
            public View getDropDownView(K item, int index, View oldView)
            {
                return getDropDownLineView(item, index);
            }
        };
    }

    private View getLineView(K item, int index)
    {
        String text;

        if ( isNullItem(index) )
            text = getNullLabel();
        else
            text = formatItem(item);

        KmOneLineView view;
        view = new KmOneLineView(ui());
        view.setText(text);
        view.setTextColor(Color.DKGRAY);

        styleView(item, view);

        return view;
    }

    private View getDropDownLineView(K item, int index)
    {
        String text;

        if ( isNullItem(index) )
            text = getNullLabel();
        else
            text = formatItem(item);

        KmOneLineView view;
        view = new KmOneLineView(ui());
        view.setText(text);
        view.setTextColor(Color.LTGRAY);
        view.setBackgroundColor(Color.DKGRAY);

        boolean isSelected = Kmu.isEqual(getCurrentSelection(), item);
        boolean notNullLabel = !isNullItem(index);

        if ( isSelected && notNullLabel )
            styleSelected(view);

        styleView(item, view);

        return view;
    }

    /**
     * For an element in the list for display.
     * This may return null.
     */
    private String formatItem(K item)
    {
        String s = getDisplayName(item);

        if ( Kmu.hasValue(s) )
            return s;

        return Kmu.toDisplay(item);
    }

    //##################################################
    //# styling
    //##################################################

    protected void styleSelected(KmOneLineView view)
    {
        KmTextViewHelper h = view.getTextView().getHelper();
        h.setBold();
        h.setTextColor(Color.YELLOW);
    }

    public void styleView(K item, KmOneLineView view)
    {
        if ( item == null )
            return;

        if ( isSpecialValue(item) )
            styleSpecialItem(view);
    }

    private void styleSpecialItem(KmOneLineView view)
    {
        KmTextViewHelper h;
        h = view.getTextView().getHelper();
        h.setItalic();
        h.setTextColor(Color.CYAN);
    }

    //##################################################
    //# abstract
    //##################################################

    /**
     * If the spinner has a null item added, care should be taken
     * to make this method null safe.
     */
    protected boolean isSpecialValue(K e)
    {
        return false;
    }

    protected String getDisplayName(K e)
    {
        return null;
    }

    //##################################################
    //# async
    //##################################################

    public void setItemsAsyncSafe(final List<K> v)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setItems(v);
            }
        }.runOnUiThread(ui());
    }

    public void setSelectedItemAsyncSafe(final K e)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setSelectedItem(e);
            }
        }.runOnUiThread(ui());
    }
}
