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

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kodemore.utility.Kmu;

public class KmJsonList
    implements Iterable<Object>
{
    //##################################################
    //# instance creation
    //##################################################

    public static KmJsonList parse(String json)
    {
        try
        {
            JSONArray e = new JSONArray(json);
            return new KmJsonList(e);
        }
        catch ( JSONException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    //##################################################
    //# variables
    //##################################################

    private JSONArray _inner;

    //##################################################
    //# constructor
    //##################################################

    public KmJsonList()
    {
        _inner = new JSONArray();
    }

    public KmJsonList(JSONArray e)
    {
        _inner = e;
    }

    //##################################################
    //# accessing
    //##################################################

    public int size()
    {
        return getInner().length();
    }

    @Override
    public Iterator<Object> iterator()
    {
        return toObjectList().iterator();
    }

    public KmList<Object> toObjectList()
    {
        KmList<Object> v = new KmList<Object>();

        int n = getInner().length();
        for ( int i = 0; i < n; i++ )
            v.add(_get(i));

        return v;
    }

    //##################################################
    //# nulls
    //##################################################

    public boolean isNull(int index)
    {
        return getInner().isNull(index);
    }

    public void setNull(int index)
    {
        _set(index, null);
    }

    //##################################################
    //# string
    //##################################################

    public String getString(int index)
    {
        return (String)_get(index);
    }

    public void setString(int index, String value)
    {
        _set(index, value);
    }

    public void addString(String value)
    {
        _add(value);
    }

    //##################################################
    //# boolean
    //##################################################

    public Boolean getBoolean(int index)
    {
        return (Boolean)_get(index);
    }

    public void setBoolean(int index, Boolean value)
    {
        _set(index, value);
    }

    public void addBoolean(Boolean value)
    {
        _add(value);
    }

    //##################################################
    //# integer
    //##################################################

    public Integer getInteger(int index)
    {
        return (Integer)_get(index);
    }

    public void setInteger(int index, Integer value)
    {
        _set(index, value);
    }

    public void addInteger(Integer value)
    {
        _add(value);
    }

    //##################################################
    //# longs
    //##################################################

    public Long getLong(int index)
    {
        return (Long)_get(index);
    }

    public void setLong(int index, Long value)
    {
        _set(index, value);
    }

    public void addLong(Long value)
    {
        _add(value);
    }

    //##################################################
    //# doubles
    //##################################################

    public Double getDouble(int index)
    {
        return (Double)_get(index);
    }

    public void setDouble(int index, Double value)
    {
        _set(index, value);
    }

    public void addDouble(Double value)
    {
        _add(value);
    }

    //##################################################
    //# map
    //##################################################

    public KmJsonMap getMap(int index)
    {
        JSONObject e = (JSONObject)_get(index);

        return e == null
            ? null
            : new KmJsonMap(e);
    }

    public void setMap(int index, KmJsonMap value)
    {
        if ( value == null )
            _set(index, null);
        else
            _set(index, value.getInner());
    }

    public void addMap(KmJsonMap value)
    {
        if ( value == null )
            _add(null);
        else
            _add(value.getInner());
    }

    public KmJsonMap addMap()
    {
        KmJsonMap e = new KmJsonMap();
        addMap(e);
        return e;
    }

    public KmList<KmJsonMap> getMaps()
    {
        KmList<KmJsonMap> v = new KmList<KmJsonMap>();

        for ( Object e : this )
            if ( e instanceof JSONObject )
                v.add(new KmJsonMap((JSONObject)e));

        return v;
    }

    //##################################################
    //# list
    //##################################################

    public KmJsonList getList(int index)
    {
        JSONArray e = (JSONArray)_get(index);

        return e == null
            ? null
            : new KmJsonList(e);
    }

    public void setList(int index, KmJsonList value)
    {
        if ( value == null )
            _set(index, null);
        else
            _set(index, value.getInner());
    }

    public void addList(KmJsonList value)
    {
        if ( value == null )
            _add(null);
        else
            _add(value.getInner());
    }

    //##################################################
    //# support
    //##################################################

    public JSONArray getInner()
    {
        return _inner;
    }

    private Object _get(int index)
    {
        try
        {
            return isNull(index)
                ? null
                : getInner().get(index);
        }
        catch ( JSONException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    private void _set(int index, Object value)
    {
        try
        {
            _inner.put(index, value);
        }
        catch ( JSONException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    private void _add(Object value)
    {
        getInner().put(value);
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
}
