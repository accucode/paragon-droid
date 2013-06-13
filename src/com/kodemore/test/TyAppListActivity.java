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

import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.utility.KmPackageManager;
import com.kodemore.utility.KmResolveInfo;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmTextField;

/**
 * Get the list of apps currently installed on the device. 
 */
public class TyAppListActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField               _filterField;
    private KmListView<KmResolveInfo> _listView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _filterField = ui().newTextField();
        _filterField.addTextChangedAction(newTextChangedAction());
        _filterField.setAutoSave();

        _listView = newList();
        _listView.setAutoSave();
        _listView.setFastScrollEnabled();

        refreshList();
    }

    //==================================================
    //= init :: list
    //==================================================

    private KmListView<KmResolveInfo> newList()
    {
        return new KmListView<KmResolveInfo>(ui())
        {
            @Override
            protected View getView(KmResolveInfo item, int index, View oldView)
            {
                TyResolveInfoView view;
                view = TyResolveInfoView.createOrCast(ui(), oldView);
                view.apply(item);
                return view;
            }

            @Override
            protected void onItemClick(KmResolveInfo e)
            {
                startActivity(e.toIntent());
            }

            @Override
            protected CharSequence getSectionNameFor(KmResolveInfo e)
            {
                return e.getActivityName();
            }
        };
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
        root.addView(_listView);
        return root;
    }

    private KmAction newTextChangedAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                refreshList();
            }
        };
    }

    //##################################################
    //# utility (refresh)
    //##################################################

    private void refreshList()
    {
        KmPackageManager pm = new KmPackageManager();

        KmList<KmResolveInfo> v;
        v = pm.findThirdPartyMainLauchers();
        v = filterItems(v);
        v.sortOn(KmResolveInfo.getApplicationLabelComparator());

        _listView.setItems(v);
    }

    //##################################################
    //# filter
    //##################################################

    private KmList<KmResolveInfo> filterItems(KmList<KmResolveInfo> all)
    {
        KmList<KmResolveInfo> v = new KmList<KmResolveInfo>();

        if ( !_filterField.hasValue() )
        {
            v.addAll(all);
            return v;
        }

        String filter = _filterField.getValue();

        for ( KmResolveInfo e : all )
            if ( matches(e, filter) )
                v.add(e);

        return v;
    }

    private boolean matches(KmResolveInfo e, String filter)
    {
        if ( matches(e.getApplicationName(), filter) )
            return true;

        if ( matches(e.getApplicationLabel(), filter) )
            return true;

        if ( matches(e.getApplicationPackage(), filter) )
            return true;

        if ( matches(e.getActivityName(), filter) )
            return true;

        if ( matches(e.getActivityPackage(), filter) )
            return true;

        return false;
    }

    private boolean matches(String value, String filter)
    {
        return Kmu.isEmpty(value)
            ? false
            : value.toLowerCase().contains(filter.toLowerCase());
    }
}
