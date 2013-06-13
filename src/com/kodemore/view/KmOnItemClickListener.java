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

import android.view.View;
import android.widget.AdapterView;

import com.kodemore.utility.Kmu;

public abstract class KmOnItemClickListener<K>
    implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    //##################################################
    //# interfaces
    //##################################################

    @Override
    public final void onItemClick(AdapterView<?> parent, View view, int index, long rowId)
    {
        try
        {
            _handle(parent, index);
        }
        catch ( Exception ex )
        {
            Kmu.handleUnhandledException(ex);
        }
    }

    @Override
    public final boolean onItemLongClick(AdapterView<?> parent, View view, int index, long rowId)
    {
        try
        {
            _handle(parent, index);
            return true;
        }
        catch ( Exception ex )
        {
            Kmu.handleUnhandledException(ex);
            return true;
        }
    }

    private void _handle(AdapterView<?> parent, int index)
    {
        @SuppressWarnings("unchecked")
        K item = (K)parent.getItemAtPosition(index);

        handle(item);
    }

    //##################################################
    //# override
    //##################################################

    protected abstract void handle(K item);
}
