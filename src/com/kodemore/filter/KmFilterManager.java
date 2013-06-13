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

package com.kodemore.filter;

import java.util.List;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.kodemore.utility.KmRunnable;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmSpinner;
import com.kodemore.view.KmUiManager;

/**
 * The filter manager handles the generic complexities and 
 * multi-threading.  You must specify a single listAdapter,
 * and typically one or more fields to watch.  Note that you
 * usually don't even need to bind the filterManager to an 
 * instance variable. 
 */
public abstract class KmFilterManager<K>
{
    //##################################################
    //# variables
    //##################################################

    /**
     * Track when the background task is running.
     * This is important so that we don't relauch the 
     * filter task repeatedly in the event that a complex
     * process is still running.
     */
    private boolean         _running;

    /**
     * The default items to use when there is no filter.
     */
    private List<K>         _defaultItems;

    /**
     * Indicates that the default items may have changed
     * and should be recalculated.
     */
    private boolean         _defaultIsDirty;

    /**
     * The filtered items.
     */
    private List<K>         _filteredItems;

    /**
     * Indicates that the filtered items may have changed
     * and should be recalculated.
     */
    private boolean         _filteredIsDirty;

    /**
     * The target array adapter.
     */
    private ArrayAdapter<K> _arrayAdapter;

    private KmUiManager     _ui;

    //##################################################
    //# constructor
    //##################################################

    public KmFilterManager(KmUiManager ui)
    {
        _ui = ui;
        invalidate();
    }

    //##################################################
    //# accessing
    //##################################################

    public ArrayAdapter<K> getArrayAdapter()
    {
        return _arrayAdapter;
    }

    public void setArrayAdapter(ArrayAdapter<K> e)
    {
        _arrayAdapter = e;
    }

    public void setArrayAdapter(KmListView<K> e)
    {
        setArrayAdapter(e.getAdapter());
    }

    private KmUiManager ui()
    {
        return _ui;
    }

    //##################################################
    //# external access
    //##################################################

    /**
     * Causes both the base list, the filter to be reevaluated.
     */
    public synchronized void invalidate()
    {
        _defaultIsDirty = true;
        _filteredIsDirty = true;
        checkRun();
    }

    public void invalidateAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                invalidate();
            }
        }.runOnUiThread(ui());
    }

    /**
     * Assumes that the base list is still valid, but reevaluates
     * the filter.
     */
    public synchronized void invalidateFilteredItems()
    {
        _filteredIsDirty = true;
        checkRun();
    }

    //##################################################
    //# abstract
    //##################################################

    public abstract List<K> getAllItems();

    public abstract List<K> getFilteredItems(List<K> all);

    //##################################################
    //# support
    //##################################################

    private void checkDirty()
    {
        if ( isDirty() )
            checkRun();
    }

    private void checkRun()
    {
        if ( _running )
            return;

        run();

        _running = true;
        _defaultIsDirty = false;
        _filteredIsDirty = false;
    }

    private void run()
    {
        new AsyncTask<Void,Void,Void>()
        {
            final boolean updateDefaultItems  = _defaultIsDirty;
            final boolean updateFilteredItems = _filteredIsDirty;

            @Override
            protected Void doInBackground(Void... params)
            {
                if ( updateDefaultItems )
                    _defaultItems = getAllItems();

                if ( updateFilteredItems )
                    _filteredItems = getFilteredItems(_defaultItems);

                return null;
            }

            @Override
            protected void onPreExecute()
            {
                runPreExecute();
            }

            @Override
            protected void onPostExecute(Void result)
            {
                updateAdapter();
                _running = false;
                checkDirty();
            }
        }.execute();
    }

    private void updateAdapter()
    {
        ArrayAdapter<K> a;
        a = _arrayAdapter;
        a.setNotifyOnChange(false);
        a.clear();

        List<K> v = _filteredItems;
        for ( K e : v )
            a.add(e);

        a.notifyDataSetChanged();
    }

    private boolean isDirty()
    {
        return _defaultIsDirty || _filteredIsDirty;
    }

    protected void runPreExecute()
    {
        // nothing
    }

    //################################################## 
    //# watch
    //##################################################

    public void watch(EditText field)
    {
        field.addTextChangedListener(createTextWatcher());
    }

    public void watch(KmSpinner<String> spinner)
    {
        spinner.setOnItemSelectedListener(createOnItemSelectedListener());
    }

    private TextWatcher createTextWatcher()
    {
        return new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // ignored
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // ignored
            }

            @Override
            public void afterTextChanged(Editable e)
            {
                if ( e != null && !e.equals("") )
                    invalidateFilteredItems();
            }
        };
    }

    private OnItemSelectedListener createOnItemSelectedListener()
    {
        return new OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                invalidateFilteredItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // noop          
            }
        };
    }

}
