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

package com.kodemore.preference.wrapper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.kodemore.time.KmDate;
import com.kodemore.time.KmTimestamp;
import com.kodemore.utility.KmBridge;
import com.kodemore.utility.Kmu;

/**
 * I provide convenient access to SharedPreferences.
 * 
 * There are several reasons to use this rather than 
 * using the SharedPreferences directly.
 * 
 * 1) Null Values.
 * SharedPreferences does not all you to save null values.
 * This is per contract - not a bug.  However, this is 
 * frequently inconvenient; requiring that you create 
 * artificial values or more complex coding workarounds.
 * This wrapper will politely return null if you attempt
 * to fetch a key that is not defined.  Also, you can set
 * a value to null, which is implemented by removing that 
 * key from the underlying storage.
 * 
 * 2) Default Values.
 * Related to Null Values, the standard SharedPreferences
 * requires that you always specify a formal (non-null?)
 * default value any time you fetch a value. This is often
 * awkward or pointless.  While this wrapper supports the
 * pattern getValue(key, def), you can also just get the
 * value without a default.  If the key is not present the
 * method will politely return null.
 * 
 * 3) Non-standard Types 
 * This wrapper provides a logical and consolidated place
 * to define methods for load/storing data types not 
 * supported by the SharedPreferences model.  
 * E.g.: I implement methods to get/setDouble values in
 * addition to the Floats supported by the native data store.
 * 
 * 4) Auto-commit
 * By default, all preference changes are committed immediately 
 * and automatically.  You can disable this by calling 
 * setAutoCommit(false), after which changes will only be 
 * persisted if you call commit().
 * 
 * 5) Private
 * I use android's _private_ preferences model by default.  
 * In fact, there is currently no hook to override this, though
 * that can be easily added when needed.  In general we want 
 * access to groups of preferences to be managed via explicitly
 * defined names and NOT linked to implicitly to class names.
 * This approach avoids accidentally breaking the contract when
 * a class is renamed or reorganized into a different package.  
 * 
 * 6) All of the property access methods are intentionally 
 * protected.  If you just need direct access to properties
 * using string literal keys, you can use the KmSimplePreferences
 * subclass which makes these methods public.  If you are 
 * using lots of preferences in a more complex application
 * then you should probably create an application specific 
 * subclass that wraps access and insulates the various 
 * clients from the low level details.
 */
public abstract class KmAbstractPreferenceWrapper
{
    //##################################################
    //# variables
    //##################################################

    private String  _name;
    private Editor  _editor;
    private boolean _autoCommit;

    //##################################################
    //# constructor
    //##################################################

    public KmAbstractPreferenceWrapper(String name)
    {
        _name = name;
        _editor = null;
        _autoCommit = true;
    }

    //##################################################
    //# accessing
    //##################################################

    protected String getName()
    {
        return _name;
    }

    protected SharedPreferences getInner()
    {
        return getApplicationContext().getSharedPreferences(getName(), Context.MODE_PRIVATE);
    }

    protected void clear()
    {
        getEditor().clear();
        checkAutoCommit();
    }

    public void clear(String key)
    {
        getEditor().remove(key);
        checkAutoCommit();
    }

    //##################################################
    //# commit
    //##################################################

    public boolean getAutoCommit()
    {
        return _autoCommit;
    }

    public void setAutoCommit(boolean e)
    {
        _autoCommit = e;
    }

    public void disableAutoCommit()
    {
        setAutoCommit(false);
    }

    public void checkAutoCommit()
    {
        if ( _autoCommit )
            commit();
    }

    public void commit()
    {
        if ( _editor != null )
        {
            _editor.commit();
            _editor = null;
        }
    }

    private Editor getEditor()
    {
        if ( _editor == null )
            _editor = getInner().edit();

        return _editor;
    }

    //##################################################
    //# null
    //##################################################

    public boolean isNull(String key)
    {
        return _getValue(key) == null;
    }

    public boolean hasKey(String key)
    {
        return getInner().contains(key);
    }

    //##################################################
    //# string
    //##################################################

    protected String getString(String key)
    {
        return getString(key, null);
    }

    protected String getString(String key, String def)
    {
        Object e = _getValue(key);
        return e instanceof String
            ? (String)e
            : def;
    }

    protected void setString(String key, String value)
    {

        if ( Kmu.isEmpty(value) )
            getEditor().remove(key);
        else
            getEditor().putString(key, value);

        checkAutoCommit();
    }

    //##################################################
    //# integer
    //##################################################

    protected Integer getInteger(String key)
    {
        return getInteger(key, null);
    }

    protected Integer getInteger(String key, Integer def)
    {
        Object e = _getValue(key);
        return e instanceof Integer
            ? (Integer)e
            : def;
    }

    protected void setInteger(String key, Integer value)
    {
        if ( value == null )
            getEditor().remove(key);
        else
            getEditor().putInt(key, value);

        checkAutoCommit();
    }

    //##################################################
    //# long
    //##################################################

    protected Long getLong(String key)
    {
        return getLong(key, null);
    }

    protected Long getLong(String key, Long def)
    {
        Object e = _getValue(key);
        return e instanceof Long
            ? (Long)e
            : def;
    }

    protected void setLong(String key, Long value)
    {
        if ( value == null )
            getEditor().remove(key);
        else
            getEditor().putLong(key, value);

        checkAutoCommit();
    }

    //##################################################
    //# float
    //##################################################

    protected Float getFloat(String key)
    {
        return getFloat(key, null);
    }

    protected Float getFloat(String key, Float def)
    {
        Object e = _getValue(key);
        return e instanceof Float
            ? (Float)e
            : def;
    }

    protected void setFloat(String key, Float value)
    {
        if ( value == null )
            getEditor().remove(key);
        else
            getEditor().putFloat(key, value);

        checkAutoCommit();
    }

    //##################################################
    //# double
    //##################################################

    protected Double getDouble(String key)
    {
        return getDouble(key, null);
    }

    protected Double getDouble(String key, Double def)
    {
        return Kmu.toDouble(getFloat(key, Kmu.toFloat(def)));
    }

    protected void setDouble(String key, Double value)
    {
        setFloat(key, Kmu.toFloat(value));
    }

    //##################################################
    //# boolean 
    //##################################################

    protected Boolean getBoolean(String key)
    {
        return getBoolean(key, null);
    }

    protected Boolean getBoolean(String key, Boolean def)
    {
        Object e = _getValue(key);

        return e instanceof Boolean
            ? (Boolean)e
            : def;
    }

    protected void setBoolean(String key, Boolean value)
    {
        if ( value == null )
            getEditor().remove(key);
        else
            getEditor().putBoolean(key, value);

        checkAutoCommit();
    }

    //##################################################
    //# date
    //##################################################

    protected KmDate getDate(String key)
    {
        return getDate(key, null);
    }

    protected KmDate getDate(String key, KmDate def)
    {
        Integer i = getInteger(key);

        return i == null
            ? def
            : KmDate.createOrdinal(i);
    }

    protected void setDate(String key, KmDate value)
    {
        if ( value == null )
            getEditor().remove(key);
        else
            getEditor().putInt(key, value.getOrdinal());

        checkAutoCommit();
    }

    //##################################################
    //# timestamp
    //##################################################

    protected KmTimestamp getTimestamp(String key)
    {
        return getTimestamp(key, null);
    }

    protected KmTimestamp getTimestamp(String key, KmTimestamp def)
    {
        Long e = getLong(key);

        return e == null
            ? def
            : KmTimestamp.createFromMsSince1970(e);
    }

    protected void setTimestamp(String key, KmTimestamp value)
    {
        if ( value == null )
            getEditor().remove(key);
        else
            getEditor().putLong(key, value.getMsSince1970());

        checkAutoCommit();
    }

    //##################################################
    //# utility
    //##################################################

    private Context getApplicationContext()
    {
        return KmBridge.getInstance().getApplicationContext();
    }

    private Object _getValue(String key)
    {
        return getInner().getAll().get(key);
    }

}
