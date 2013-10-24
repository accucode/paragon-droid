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


/**
 * See superclass.
 * 
 * I simply override the otherwise protected property
 * access methods.
 */
public class KmSimplePreferenceWrapper
    extends KmAbstractPreferenceWrapper
{
    //##################################################
    //# constructor
    //##################################################

    public KmSimplePreferenceWrapper(String name)
    {
        super(name);
    }

    //##################################################
    //# string
    //##################################################

    @Override
    public String getString(String key)
    {
        return super.getString(key);
    }

    @Override
    public String getString(String key, String def)
    {
        return super.getString(key, def);
    }

    @Override
    public void setString(String key, String value)
    {
        super.setString(key, value);
    }

    //##################################################
    //# integer
    //##################################################

    @Override
    public Integer getInteger(String key)
    {
        return super.getInteger(key, null);
    }

    @Override
    public Integer getInteger(String key, Integer def)
    {
        return super.getInteger(key, def);
    }

    @Override
    public void setInteger(String key, Integer value)
    {
        super.setInteger(key, value);
    }

    //##################################################
    //# float
    //##################################################

    @Override
    public Float getFloat(String key)
    {
        return super.getFloat(key);
    }

    @Override
    public Float getFloat(String key, Float def)
    {
        return super.getFloat(key, def);
    }

    @Override
    public void setFloat(String key, Float value)
    {
        super.setFloat(key, value);
    }

    //##################################################
    //# double
    //##################################################

    @Override
    public Double getDouble(String key)
    {
        return super.getDouble(key);
    }

    @Override
    public Double getDouble(String key, Double def)
    {
        return super.getDouble(key, def);
    }

    @Override
    public void setDouble(String key, Double value)
    {
        super.setDouble(key, value);
    }

    //##################################################
    //# boolean
    //##################################################

    @Override
    public Boolean getBoolean(String key)
    {
        return super.getBoolean(key);
    }

    @Override
    public Boolean getBoolean(String key, Boolean def)
    {
        return super.getBoolean(key, def);
    }

    @Override
    public void setBoolean(String key, Boolean value)
    {
        super.setBoolean(key, value);
    }

}
