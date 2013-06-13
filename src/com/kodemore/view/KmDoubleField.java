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

package com.kodemore.view;

import android.text.InputType;

import com.kodemore.utility.KmString;
import com.kodemore.utility.Kmu;

public class KmDoubleField
    extends KmAbstractField
{
    //##################################################
    //# variables
    //##################################################

    private Integer _places;

    //##################################################
    //# constructor
    //##################################################

    public KmDoubleField(KmUiManager ui)
    {
        super(ui);

        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    //##################################################
    //# accessing
    //##################################################

    public Double getValue()
    {
        return KmString.create(getText()).parseDouble();
    }

    public void setValue(Double e)
    {
        setText(formatValue(e));
    }

    public boolean hasValue()
    {
        return getValue() != null;
    }

    //##################################################
    //# format
    //##################################################

    private String formatValue(Double e)
    {
        if ( e == null )
            return "";

        if ( e.isNaN() )
            return "";

        if ( hasPlaces() )
            return Kmu.formatDouble(e, getPlaces());

        return e + "";
    }

    public Integer getPlaces()
    {
        return _places;
    }

    public void setPlaces(Integer e)
    {
        _places = e;
    }

    public boolean hasPlaces()
    {
        return _places != null;
    }
}
