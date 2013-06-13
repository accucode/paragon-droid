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
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kodemore.collection.KmList;
import com.kodemore.drawable.KmDrawableBuilder;
import com.kodemore.utility.KmRunnable;

/** 
 * There is an issue attaching listViews to hosted tabs in 
 * tab activities. If you attach a listview to a tab as a 
 * view, it will not be able to save it's state and reports :
 *  
 *      W/PhoneWindow(22688): Previously focused view reported id 1000 during save, 
 *      but can't be found during restore.
 * 
 * The same warning occurs when you have a listview inside an activity
 * where the activity is hosted by a tabbed activity. However, the
 * listview inside the activity WILL save its state (such as position
 * in the list, etc.).
 *  
 * It appears saving state is broken in some way and we have
 * been unable to find any reason why this is occurring. The
 * only fix found currently is to manually save the state of
 * listviews using onSaveInstanceState and onRestoreInstanceState
 * however implementing a fix everywhere has been problematic 
 * because the "View" class doesn't have these methods public.
 */
public class KmListView<E>
    extends ListView
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;

    //##################################################
    //# constructor
    //##################################################

    public KmListView(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;

        setAdapter(newAdapter());
        setOnItemClickListener(newItemClickListener());
        setOnCreateContextMenuListener(newContextMenuListener());

        setBackgroundDrawable(getDefaultBackground());
    }

    //##################################################
    //# theme
    //##################################################

    protected Drawable getDefaultBackground()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(2);
        b.addPaint(Color.LTGRAY, 1).setCornerRadius(10);
        b.addPaint(Color.WHITE, 1).setCornerRadius(9);
        b.addPad(9);
        return b.toDrawable();
    }

    //##################################################
    //# core
    //##################################################

    public KmUiManager ui()
    {
        return _ui;
    }

    public KmListHelper getHelper()
    {
        return new KmListHelper(this);
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
        setId(ui().nextId());
    }

    public boolean hasId()
    {
        return getId() != NO_ID;
    }

    //##################################################
    //# adapters (defaults)
    //##################################################

    private KmArrayAdapter<E> newAdapter()
    {
        return new KmArrayAdapter<E>(ui())
        {
            @Override
            protected View getView(E item, int index, View oldView)
            {
                return KmListView.this.getView(item, index, oldView);
            }

            @Override
            protected CharSequence getSectionNameFor(E item)
            {
                return KmListView.this.getSectionNameFor(item);
            }

            @Override
            public boolean areAllItemsEnabled()
            {
                return KmListView.this.areAllItemsEnabled();
            }

            @Override
            public boolean isEnabled(int index)
            {
                return KmListView.this.isEnabled(index);
            }
        };
    }

    private KmOnItemClickListener<E> newItemClickListener()
    {
        return new KmOnItemClickListener<E>()
        {
            @Override
            protected void handle(E item)
            {
                KmListView.this.onItemClick(item);
            }
        };
    }

    private KmOnCreateContextMenuListener<E> newContextMenuListener()
    {
        return new KmOnCreateContextMenuListener<E>()
        {
            @Override
            public void create(KmContextMenuHelper menu, E item)
            {
                KmListView.this.onCreateContextMenu(menu, item);
            }
        };
    }

    //##################################################
    //# adapter
    //##################################################

    public void setAdapter(ArrayAdapter<E> e)
    {
        super.setAdapter(e);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayAdapter<E> getAdapter()
    {
        return (ArrayAdapter<E>)super.getAdapter();
    }

    //##################################################
    //# items 
    //##################################################

    public E getItemAt(int i)
    {
        return getAdapter().getItem(i);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E getSelectedItem()
    {
        return (E)super.getSelectedItem();
    }

    public KmList<E> getItems()
    {
        KmList<E> v = new KmList<E>();

        int n = getItemCount();
        for ( int i = 0; i < n; i++ )
            v.add(getItemAt(i));

        return v;
    }

    public void setItems(List<E> v)
    {
        clearItems();
        addItems(v);
    }

    public int getItemCount()
    {
        return getAdapter().getCount();
    }

    /**
     * Informs the list that the underlying items may have
     * changed and that the view should be updated.
     */
    public void notifyDataSetChanged()
    {
        getAdapter().notifyDataSetChanged();
    }

    //==================================================
    //= items :: add
    //==================================================

    public void addNull()
    {
        addItem(null);
    }

    public void addItem(E e)
    {
        getAdapter().add(e);
    }

    public void addItemAt(E e, int i)
    {
        getAdapter().insert(e, i);
    }

    public void addItemAtStart(E e)
    {
        addItemAt(e, 0);
    }

    public void addItems(List<E> v)
    {
        if ( v == null )
            return;

        for ( E e : v )
            addItem(e);
    }

    public void addItemsAt(List<? extends E> v, int i)
    {
        if ( v == null )
            return;

        for ( E e : v )
        {
            addItemAt(e, i);
            i++;
        }
    }

    private void setItemsAndScrollTo(List<E> v, Integer i)
    {
        setItems(v);

        if ( i != null )
            setSelection(i);
    }

    public void smoothScrollToStart()
    {
        smoothScrollToPosition(0);
    }

    //==================================================
    //= items :: remove
    //==================================================

    public void removeItem(E e)
    {
        getAdapter().remove(e);
    }

    public void removeLastItem()
    {
        removeItemAt(getSize() - 1);
    }

    public void removeItemAt(int i)
    {
        removeItem(getItemAt(i));
    }

    public void clearItems()
    {
        getAdapter().clear();
    }

    //##################################################
    //# size
    //##################################################

    public int getSize()
    {
        return getAdapter().getCount();
    }

    public boolean isEmpty()
    {
        return getCount() == 0;
    }

    public boolean isNotEmpty()
    {
        return !isEmpty();
    }

    //##################################################
    //# enabled
    //##################################################

    public void enable()
    {
        setEnabled(true);
    }

    public void disable()
    {
        setEnabled(false);
    }

    //##################################################
    //# overrides
    //##################################################

    protected View getView(E item, int index, View oldView)
    {
        return ui().newOneLineView(item);
    }

    protected void onItemClick(E item)
    {
        // optional
    }

    protected void onCreateContextMenu(KmContextMenuHelper menu, E item)
    {
        // optional
    }

    /**
     * Override with a non-null return value to enable rolodex
     * style thumb dragging.  For more detail see
     * KmArrayAdapter.getSectionNameFor.
     */
    protected CharSequence getSectionNameFor(E item)
    {
        return null;
    }

    //##################################################
    //# item enablement
    //##################################################

    /**
     * Used in conjunction with the default array adapter to allow
     * clients to easily override item enablement without creating
     * a custom adapter.
     */
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    /**
     * Used in conjunction with the default array adapter to allow
     * clients to easily override item enablement without creating
     * a custom adapter.
     */
    public boolean isEnabled(int index)
    {
        return true;
    }

    //##################################################
    //# convenience
    //##################################################

    /**
     * Must be called AFTER items are updated if are using
     * sections (rolodex) functionality.
     */
    public void setFastScrollEnabled()
    {
        setFastScrollEnabled(true);
    }

    public void refreshSections()
    {
        if ( !isFastScrollEnabled() )
            return;

        setFastScrollEnabled(false);
        setFastScrollEnabled(true);
    }

    public void setSmoothScrollbarEnabled()
    {
        setSmoothScrollbarEnabled(true);
    }

    public void setSmoothScrollbarDisabled()
    {
        setSmoothScrollbarEnabled(false);
    }

    //##################################################
    //# async
    //##################################################

    public void setItemsAsyncSafe(final List<E> v)
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

    public void setItemsAndScrollToAsyncSafe(final List<E> v, final Integer i)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setItemsAndScrollTo(v, i);
            }
        }.runOnUiThread(ui());
    }

    public void setSelectionAsyncSafe(final int i)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setSelection(i);
            }
        }.runOnUiThread(ui());
    }

    public void clearItemsAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                clearItems();
            }
        }.runOnUiThread(ui());
    }

}
