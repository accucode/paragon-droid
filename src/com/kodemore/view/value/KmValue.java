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

package com.kodemore.view.value;

import android.os.Bundle;
import android.view.View;

import com.kodemore.view.KmUiManager;

public abstract class KmValue<T>
{
    //##################################################
    //# mode
    //##################################################

    /**
     * Different modes of behavior for managing state during android lifecycle. 
     */
    enum Mode
    {
        /**
         * Do nothing.  
         */
        NONE,

        /**
         * Automatically save and restore the value's state using bundles. 
         */
        AUTO_SAVE,

        /**
         * Automatically flush (null) the value each time we save or restore.
         */
        AUTO_FLUSH
    }

    //##################################################
    //# variables
    //##################################################

    /**
     * The ui manager.
     */
    private KmUiManager _ui;

    /**
     * A unique id used to coordinate saved state.
     */
    private int         _id;

    /**
     * The internal value being wrapped.
     */
    private T           _value;

    /**
     * Determine what should be done when the android lifecycle is
     * ready to save or restore state.  The default mode is none.
     */
    private Mode        _mode;

    //##################################################
    //# constructor
    //##################################################

    public KmValue(KmUiManager ui)
    {
        _ui = ui;
        _ui.register(this);

        _id = View.NO_ID;
        _mode = Mode.NONE;
    }

    //##################################################
    //# core
    //##################################################

    private KmUiManager ui()
    {
        return _ui;
    }

    //##################################################
    //# id
    //##################################################

    public int getId()
    {
        return _id;
    }

    public void setId(int e)
    {
        _id = e;
    }

    public void setId()
    {
        if ( !hasId() )
            setId(ui().nextId());
    }

    public boolean hasId()
    {
        return getId() != View.NO_ID;
    }

    //##################################################
    //# mode
    //##################################################

    public void setAutoSave()
    {
        setId();
        _mode = Mode.AUTO_SAVE;
    }

    public void setAutoFlush()
    {
        _mode = Mode.AUTO_FLUSH;
    }

    //##################################################
    //# value
    //##################################################

    public T getValue()
    {
        return _value;
    }

    public void setValue(T e)
    {
        _value = e;
    }

    public boolean hasValue()
    {
        return !isNull();
    }

    public void setNull()
    {
        setValue(null);
    }

    public boolean isNull()
    {
        return getValue() == null;
    }

    public void clear()
    {
        setNull();
    }

    //##################################################
    //# save / restore
    //##################################################

    public void saveState(Bundle state)
    {
        switch ( _mode )
        {
            case AUTO_FLUSH:
                clear();
                break;

            case AUTO_SAVE:
                saveState(state, getKey(), getValue());
                break;

            case NONE:
                break;
        }
    }

    public void restoreState(Bundle state)
    {
        switch ( _mode )
        {
            case AUTO_FLUSH:
                clear();
                break;

            case AUTO_SAVE:
                setValue(restoreState(state, getKey()));
                break;

            case NONE:
                break;
        }
    }

    protected abstract void saveState(Bundle state, String key, T value);

    protected abstract T restoreState(Bundle state, String key);

    private String getKey()
    {
        return getId() + "";
    }

}
