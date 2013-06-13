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

import android.app.Activity;
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.filter.KmFilterManager;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmActivityListView;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTextField;

/**
 * I am used to display a list of all other tests.
 */
public class TyMainActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField _filterField;

    //##################################################
    //# config
    //##################################################

    @Override
    protected String getDefaultTitle()
    {
        return "Tests";
    }

    @Override
    protected boolean showsTitleBar()
    {
        return true;
    }

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _filterField = ui().newTextField();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmActivityListView list;
        list = new KmActivityListView(ui());
        list.addItems(TyRegistry.getSortedTests());
        list.setFastScrollEnabled();
        list.setAutoSave();

        list.setFastScrollEnabled(false);
        list.setFastScrollEnabled(true);

        KmFilterManager<Class<? extends Activity>> mgr;
        mgr = newFilterManager();
        mgr.setArrayAdapter(list.getAdapter());
        mgr.watch(_filterField);

        KmColumnLayout root;
        root = ui().newColumn();
        root.addView(_filterField);
        root.addView(list);
        return root;
    }

    private KmFilterManager<Class<? extends Activity>> newFilterManager()
    {
        return new KmFilterManager<Class<? extends Activity>>(ui())
        {
            @Override
            public KmList<Class<? extends Activity>> getAllItems()
            {
                return TyRegistry.getSortedTests();
            }

            @Override
            public List<Class<? extends Activity>> getFilteredItems(
                List<Class<? extends Activity>> all)
            {
                return filter(all);
            }
        };
    }

    private List<Class<? extends Activity>> filter(List<Class<? extends Activity>> all)
    {
        if ( !_filterField.hasValue() )
            return all;

        String filter = _filterField.getValue().toLowerCase();

        KmList<Class<? extends Activity>> v = new KmList<Class<? extends Activity>>();
        for ( Class<? extends Activity> e : all )
        {
            String name = Kmu.formatActivity(e).toLowerCase();
            if ( name.contains(filter) )
                v.add(e);
        }
        return v;
    }
}
