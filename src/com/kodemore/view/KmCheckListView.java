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

import android.util.SparseBooleanArray;
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.utility.KmRunnable;

/**
 * This is a simple checkable list.  The line items containt a check box
 * on the left side, and by default allows for multiple selection.  Individual
 * check boxes can be disabled by overriding the method isCheckDisabled.
 */
public abstract class KmCheckListView<K>
    extends KmListView<K>
{
    //##################################################
    //# constructor
    //##################################################

    public KmCheckListView(KmUiManager ui)
    {
        super(ui);

        setChoiceModeMultiple();
        setAdapter(newAdapter());
    }

    //##################################################
    //# adapter
    //##################################################

    private KmArrayAdapter<K> newAdapter()
    {
        return new KmArrayAdapter<K>(ui())
        {
            @Override
            protected View getView(K item, int index, android.view.View oldView)
            {
                KmCheckFrameLineView view;
                view = KmCheckFrameLineView.createOrCast(ui(), oldView);
                view.setContentView(getContentView(item));
                view.enableCheckBox(isCheckEnabled(item));
                return view;
            }
        };
    }

    //##################################################
    //# convenience
    //##################################################

    public KmList<K> getCheckedItems()
    {
        KmList<K> list = new KmList<K>();

        SparseBooleanArray bools = getCheckedItemPositions();

        int n = getItemCount();
        for ( int i = 0; i < n; i++ )
            if ( bools.get(i) )
                list.add(getItemAt(i));

        return list;
    }

    public KmList<K> getEnabledCheckedItems()
    {
        KmList<K> list = new KmList<K>();

        SparseBooleanArray bools = getCheckedItemPositions();

        int n = getItemCount();
        for ( int i = 0; i < n; i++ )
            if ( bools.get(i) )
                if ( !isCheckDisabled(getItemAt(i)) )
                    list.add(getItemAt(i));

        return list;
    }

    public void checkAll()
    {
        int n = getItemCount();
        for ( int i = 0; i < n; i++ )
            setItemChecked(i);
    }

    public void unCheckAll()
    {
        int n = getItemCount();
        for ( int i = 0; i < n; i++ )
            setItemUnChecked(i);
    }

    public boolean allChecked()
    {
        int n = getItemCount();
        for ( int i = 0; i < n; i++ )
            if ( isItemUnChecked(i) )
                return false;

        return true;
    }

    public boolean noneChecked()
    {
        int n = getItemCount();
        for ( int i = 0; i < n; i++ )
            if ( isItemChecked(i) )
                return false;

        return true;
    }

    //##################################################
    //# convenience
    //##################################################

    public void setItemChecked(int i)
    {
        setItemChecked(i, true);
    }

    public void setItemUnChecked(int i)
    {
        setItemChecked(i, false);
    }

    public boolean isItemUnChecked(int i)
    {
        return !isItemChecked(i);
    }

    public void setChoiceModeMultiple()
    {
        setChoiceMode(CHOICE_MODE_MULTIPLE);
    }

    public void setChoiceModeSingle()
    {
        setChoiceMode(CHOICE_MODE_SINGLE);
    }

    //##################################################
    //# utility
    //##################################################

    /**
     * Convenient hook to disable the checkbox ability of items
     * in the CheckListView.  It returns false by default (all
     * items are checkable).  Disabled items will not be returned
     * by getEnabledCheckedItems.
     */
    protected boolean isCheckDisabled(K item)
    {
        return false;
    }

    protected final boolean isCheckEnabled(K item)
    {
        return !isCheckDisabled(item);
    }

    //##################################################
    //# content view
    //##################################################

    protected abstract View getContentView(K item);

    //##################################################
    //# async
    //##################################################

    public void checkAllAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                checkAll();
            }
        }.runOnUiThread(ui());
    }
}
