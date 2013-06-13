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

import java.util.Collection;

import kankan.wheel.widget.WheelView;
import android.view.View;

import com.kodemore.collection.KmList;

public class KmWheelView<T>
    extends WheelView
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;

    //##################################################
    //# constructor
    //##################################################

    public KmWheelView(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;

        setViewAdapter(newAdapter());
    }

    //##################################################
    //# core
    //##################################################

    private KmUiManager ui()
    {
        return _ui;
    }

    public KmViewHelper getHelper()
    {
        return new KmViewHelper(this);
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

    public boolean hasId()
    {
        return getId() != NO_ID;
    }

    //##################################################
    //# items
    //##################################################

    public KmList<T> getItems()
    {
        return getViewAdapter().getItems();
    }

    public void addItem(T e)
    {
        getViewAdapter().addItem(e);
    }

    public void addAllItems(Collection<T> v)
    {
        getViewAdapter().addAllItems(v);
    }

    public T getItemAt(int i)
    {
        return getViewAdapter().getItemAt(i);
    }

    public void clearItems()
    {
        getViewAdapter().clearItems();
    }

    //##################################################
    //# adapter
    //##################################################

    @Override
    @SuppressWarnings("unchecked")
    public KmWheelAdapter<T> getViewAdapter()
    {
        return (KmWheelAdapter<T>)super.getViewAdapter();
    }

    public void setAdapter(KmWheelAdapter<T> e)
    {
        setViewAdapter(e);
    }

    private KmWheelAdapter<T> newAdapter()
    {
        return new KmWheelAdapter<T>(ui())
        {
            @Override
            protected View getView(T item, int index)
            {
                return KmWheelView.this.getView(item, index);
            }
        };
    }

    //##################################################
    //# visibility
    //##################################################

    public void show()
    {
        setVisibility(VISIBLE);
    }

    public void hide()
    {
        setVisibility(INVISIBLE);
    }

    public void gone()
    {
        setVisibility(GONE);
    }

    //##################################################
    //# view
    //##################################################

    protected View getView(T item, int index)
    {
        return ui().newOneLineView(item);
    }

}
