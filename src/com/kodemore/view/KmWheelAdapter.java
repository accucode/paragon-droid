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

import kankan.wheel.widget.adapters.AbstractWheelAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.kodemore.collection.KmList;

public class KmWheelAdapter<T>
    extends AbstractWheelAdapter
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;
    private KmList<T>   _items;

    //##################################################
    //# constructor
    //##################################################

    public KmWheelAdapter(KmUiManager ui)
    {
        _ui = ui;
        _items = new KmList<T>();
    }

    //##################################################
    //# core
    //##################################################

    protected KmUiManager ui()
    {
        return _ui;
    }

    //##################################################
    //# accessing
    //##################################################

    public KmList<T> getItems()
    {
        return _items;
    }

    public void addItem(T e)
    {
        _items.add(e);
    }

    public void addAllItems(Collection<T> v)
    {
        for ( T e : v )
            addItem(e);
    }

    public T getItemAt(int i)
    {
        return _items.get(i);
    }

    public void clearItems()
    {
        _items.clear();
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public int getItemsCount()
    {
        return _items.size();
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent)
    {
        T item = getItemAt(index);
        return getView(item, index);
    }

    //##################################################
    //# view
    //##################################################

    protected View getView(T item, int index)
    {
        return ui().newOneLineView(item);
    }

}
