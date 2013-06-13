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

import java.io.Serializable;

import com.kodemore.utility.Kmu;

/**
 * I provide a simple model for testing.
 */
public class TyAnimal
    implements Serializable
{
    //##################################################
    //# static 
    //##################################################

    public static final TyAnimalTools tools = new TyAnimalTools();

    //##################################################
    //# variables
    //##################################################

    /**
     * The unique identifier.
     * Our focus is on testing models with non-integer ids.
     */
    private String                    _uid;

    /**
     * bear, fox, ...
     */
    private String                    _type;

    /**
     * red, blue, ...
     */
    private String                    _color;

    //##################################################
    //# constructor
    //##################################################

    public TyAnimal()
    {
        _uid = Kmu.newUid();
    }

    //##################################################
    //# uid
    //##################################################

    public String getUid()
    {
        return _uid;
    }

    public void setUid(String e)
    {
        _uid = e;
    }

    public boolean hasUid(String e)
    {
        return _uid.equals(e);
    }

    //##################################################
    //# type
    //##################################################

    public String getType()
    {
        return _type;
    }

    public void setType(String e)
    {
        _type = e;
    }

    public boolean hasType()
    {
        return Kmu.hasValue(_type);
    }

    public boolean hasType(String e)
    {
        return Kmu.isEqual(_type, e);
    }

    public boolean hasTypeSubstringIgnoreCase(CharSequence s)
    {
        return Kmu.hasSubstringIgnoreCase(getType(), s);
    }

    //##################################################
    //# color
    //##################################################

    public String getColor()
    {
        return _color;
    }

    public void setColor(String e)
    {
        _color = e;
    }

    public boolean hasColor()
    {
        return Kmu.hasValue(_color);
    }

    public boolean hasColor(String e)
    {
        return Kmu.isEqual(_color, e);
    }

    //##################################################
    //# equals
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        if ( e instanceof TyAnimal )
            return ((TyAnimal)e).hasUid(_uid);

        return false;
    }

    @Override
    public int hashCode()
    {
        return _uid.hashCode();
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return Kmu.format("Animal-" + getUid());
    }

    public String getDisplayString()
    {
        return Kmu.join(getColor(), getType(), " ");
    }

    public boolean hasDisplayStringSubstring(String e)
    {
        String display = getDisplayString().toLowerCase();
        e = e.toLowerCase();

        return display.contains(e);
    }
}
