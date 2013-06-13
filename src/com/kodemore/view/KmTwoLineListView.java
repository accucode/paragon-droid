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

import android.view.View;

import com.kodemore.collection.KmUntypedTuple;
import com.kodemore.utility.KmRunnable;

public class KmTwoLineListView
    extends KmListView<KmUntypedTuple>
{
    //##################################################
    //# constructor
    //##################################################

    public KmTwoLineListView(KmUiManager ui)
    {
        super(ui);
    }

    //##################################################
    //# convenience
    //##################################################

    public void addItem(Object key, Object value)
    {
        KmUntypedTuple e;
        e = new KmUntypedTuple();
        e.setKey(key);
        e.setValue(value);
        addItem(e);
    }

    public void addItems(Collection<KmUntypedTuple> v)
    {
        for ( KmUntypedTuple e : v )
            addItem(e);
    }

    //##################################################
    //# overrides
    //##################################################

    @Override
    protected View getView(KmUntypedTuple item, int index, View oldView)
    {
        if ( item == null )
            return ui().newOneLineView("");

        KmTwoLineView view;
        view = KmTwoLineView.createOrCast(ui(), oldView);
        view.setLine1(item.getKey());
        view.setLine2(item.getValue());
        return view;
    }

    //##################################################
    //# async
    //##################################################

    public void addItemAsyncSafe(final Object key, final Object value)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                addItem(key, value);
            }
        }.runOnUiThread(ui());
    }
}
