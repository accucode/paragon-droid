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

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

/**
 * I provide a more convenient alternative to OnCreateContextMenuListener.
 * I currently assume menu is being created for a ListView.
 */
public abstract class KmOnCreateContextMenuListener<K>
    implements OnCreateContextMenuListener
{
    /**
     * Handle the housekeeping required by the traditional
     * listener interface.  Specific configuration of the 
     * menu is delgated to the create(...) method.
     */
    @Override
    public final void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo)
    {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;

        int index = info.position;
        K item = getItemFor(view, index);

        KmContextMenuHelper h;
        h = new KmContextMenuHelper(menu);

        create(h, item);
    }

    @SuppressWarnings("unchecked")
    private K getItemFor(View view, int index)
    {
        ListView listView = (ListView)view;
        return (K)listView.getItemAtPosition(index);
    }

    public abstract void create(KmContextMenuHelper h, K item);
}
