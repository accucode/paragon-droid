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

import com.kodemore.utility.KmAbstractFilter;

/**
 * I provide a simple model for testing.
 */
public class TyAnimalFilter
    extends KmAbstractFilter<TyAnimal>
{
    //##################################################
    //# variables
    //##################################################

    private String  _type;
    private boolean _usesType;

    private String  _color;
    private boolean _usesColor;

    //##################################################
    //# constructor
    //##################################################

    public TyAnimalFilter()
    {
        // none
    }

    //##################################################
    //# testing
    //##################################################

    @Override
    public boolean includes(TyAnimal e)
    {
        return matchesType(e) && matchesColor(e);
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
        _usesType = true;
    }

    public boolean usesType()
    {
        return _usesType;
    }

    public boolean matchesType(TyAnimal e)
    {
        if ( !usesType() )
            return true;

        return matchesString(e.getType(), getType());
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
        _usesColor = true;
    }

    public boolean usesColor()
    {
        return _usesColor;
    }

    public boolean matchesColor(TyAnimal e)
    {
        if ( !usesColor() )
            return true;

        return matchesString(e.getColor(), getColor());
    }

}
