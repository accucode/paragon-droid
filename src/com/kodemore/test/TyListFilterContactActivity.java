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

import java.util.List;

import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.contact.KmContact;
import com.kodemore.contact.KmContactManager;
import com.kodemore.filter.KmFilterManager;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmOneLineView;
import com.kodemore.view.KmTextField;

/**
 * A list of filtered contacts.
 * 
 * We want to manage filters in the background.  
 * 
 * Also, filters should not be limited to a prefix match, nor 
 * even limited to a single string criteria.  For example, 
 * what if we want to filter on name substring, phone prefix, 
 * and account type.  The built-in filter support provided by
 * android doens't seem to allow for any of this.
 * 
 * Here we examine an alternate pattern for filtering.
 * These are the goals...
 * 
 *      1) Filtering should be simple.  Any complexity for the 
 *         developer should be due to the intrinsic complexity
 *         of their custom filter; not due to complexity of the 
 *         framework.
 *         
 *      2) Filtering should support multiple criteria; and need
 *         not even be directly related to ui elements.  Filters
 *         could just as easily be based on the current date,
 *         preferences, or anything else.
 *         
 *      3) Filtering should work on domain models (e.g.: a contact)
 *         not just simple data types such as strings.
 *         
 *      4) Filtering does need to occur in the background; not
 *         on the main ui thread.  This is important because we
 *         don't want the ui thread to lock up every time the 
 *         filter recalculates.  This is particularly true when
 *         the filter is based on something like a text fields
 *         which may change rapidly as the user types.
 */
public class TyListFilterContactActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField           _filterField;
    private KmListView<KmContact> _list;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _filterField = ui().newTextField();
        _list = newListView();
        _list.setFastScrollEnabled();

        KmFilterManager<KmContact> filter;
        filter = newFilterManager();
        filter.setArrayAdapter(_list.getAdapter());
        filter.watch(_filterField);
    }

    //==================================================
    //= init :: list
    //==================================================

    private KmListView<KmContact> newListView()
    {
        return new KmListView<KmContact>(ui())
        {
            @Override
            protected View getView(KmContact item, int index, View oldView)
            {
                KmOneLineView view;
                view = KmOneLineView.createOrCast(ui(), oldView);
                view.setText(item.getDisplayName());
                return view;
            }

            @Override
            protected void onItemClick(KmContact item)
            {
                toast(item.getDisplayName());
            }

            @Override
            protected CharSequence getSectionNameFor(KmContact e)
            {
                return e.getDisplayName();
            }
        };
    }

    //==================================================
    //= init :: filter
    //==================================================

    private KmList<KmContact> getAll()
    {
        return new KmContactManager(getContext()).getContacts();
    }

    private KmFilterManager<KmContact> newFilterManager()
    {
        return new KmFilterManager<KmContact>(ui())
        {
            @Override
            public List<KmContact> getAllItems()
            {
                return getAll();
            }

            @Override
            public List<KmContact> getFilteredItems(List<KmContact> all)
            {
                return filter(all);
            }
        };
    }

    /**
     * This is factored out of the filterManager to keep the code 
     * clean, and to avoid accidental dependencies on the filter
     * manager.  Notice that you can simply return the "all"
     * parameter if you don't want to apply a filter.
     */
    private List<KmContact> filter(List<KmContact> all)
    {
        String filter;
        filter = _filterField.getValue();

        if ( filter == null )
            return all;

        filter = filter.toLowerCase().trim();
        if ( Kmu.isEmpty(filter) )
            return all;

        KmList<KmContact> v = new KmList<KmContact>();

        for ( KmContact e : all )
            if ( e.hasDisplayNameSubstring(filter) )
                v.add(e);

        return v;
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addView(_filterField);
        root.addView(_list);
        return root;
    }

}
