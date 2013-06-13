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

package com.kodemore.collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kodemore.utility.Kmu;

public class KmJsonMap
{
    //##################################################
    //# instance creation
    //##################################################

    public static KmJsonMap parse(String json)
    {
        try
        {
            JSONObject e = new JSONObject(json);
            return new KmJsonMap(e);
        }
        catch ( JSONException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    //##################################################
    //# variables 
    //##################################################

    private JSONObject _inner;

    //##################################################
    //# constructor
    //##################################################

    public KmJsonMap()
    {
        _inner = new JSONObject();
    }

    public KmJsonMap(JSONObject e)
    {
        _inner = e;
    }

    //##################################################
    //# basics
    //##################################################

    public String getString(String key)
    {
        return (String)_get(key);
    }

    public void setString(String key, String value)
    {
        _set(key, value);
    }

    public Boolean getBoolean(String key)
    {
        return (Boolean)_get(key);
    }

    public void setBoolean(String key, Boolean value)
    {
        _set(key, value);
    }

    public Integer getInteger(String key)
    {
        return (Integer)_get(key);
    }

    public void setInteger(String key, Integer value)
    {
        _set(key, value);
    }

    public Long getLong(String key)
    {
        return (Long)_get(key);
    }

    public void setLong(String key, Long value)
    {
        _set(key, value);
    }

    public Double getDouble(String key)
    {
        return (Double)_get(key);
    }

    public void setDouble(String key, Double value)
    {
        _set(key, value);
    }

    //##################################################
    //# map
    //##################################################

    public KmJsonMap getMap(String key)
    {
        JSONObject e = (JSONObject)_get(key);

        return e == null
            ? null
            : new KmJsonMap(e);
    }

    public void setMap(String key, KmJsonMap value)
    {
        if ( value == null )
            _set(key, null);
        else
            _set(key, value.getInner());
    }

    //##################################################
    //# list
    //##################################################

    public KmJsonList getList(String key)
    {
        JSONArray e = (JSONArray)_get(key);

        return e == null
            ? null
            : new KmJsonList(e);
    }

    public void setList(String key, KmJsonList value)
    {
        if ( value == null )
            _set(key, null);
        else
            _set(key, value.getInner());
    }

    public KmJsonList setList(String key)
    {
        KmJsonList v = new KmJsonList();
        _set(key, v.getInner());
        return v;
    }

    //##################################################
    //# misc 
    //##################################################

    public boolean has(String key)
    {
        return getInner().has(key);
    }

    public void remove(String key)
    {
        getInner().remove(key);
    }

    public int size()
    {
        return getInner().length();
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public boolean isNotEmpty()
    {
        return !isEmpty();
    }

    //##################################################
    //# support
    //##################################################

    public JSONObject getInner()
    {
        return _inner;
    }

    private Object _get(String key)
    {
        try
        {
            return has(key)
                ? getInner().get(key)
                : null;
        }
        catch ( JSONException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    private void _set(String key, Object value)
    {
        try
        {
            getInner().put(key, value);
        }
        catch ( JSONException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    //##################################################
    //# format
    //##################################################

    /**
     * Return the compact json encoded value.
     */
    @Override
    public String toString()
    {
        return getInner().toString();
    }

    public String toStringPretty()
    {
        try
        {
            return getInner().toString(2);
        }
        catch ( JSONException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }
}
