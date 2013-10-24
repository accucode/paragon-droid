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

import android.text.InputFilter;

import com.kodemore.types.KmQuantity;
import com.kodemore.utility.KmString;

public class KmQuantityField
    extends KmAbstractDecimalField
{
    //##################################################
    //# constants
    //##################################################

    private final static Integer SCALE     = KmQuantity.SCALE;
    private final static Integer PRECISION = KmQuantity.DATABASE_PRECISION;

    //##################################################
    //# variables
    //##################################################

    private int                  _maxLength;

    //##################################################
    //# constructor
    //##################################################

    public KmQuantityField(KmUiManager ui)
    {
        super(ui, SCALE, PRECISION);
        setMaxLength(6);
        initializeFilter();
    }

    //##################################################
    //# accessing
    //##################################################

    public int getMaxLength()
    {
        return _maxLength;
    }

    public void setMaxLength(int i)
    {
        _maxLength = i;
    }

    public KmQuantity getValue()
    {
        KmString s = KmString.create(getText()).trim();

        if ( s.isEmpty() )
            return null;

        return KmQuantity.parse(s.toString());
    }

    public void setValue(KmQuantity e)
    {
        if ( e == null )
            setText("");
        else
            setText(e.getDisplayString(SCALE, false));
    }

    public boolean hasValue()
    {
        return getValue() != null;
    }

    //##################################################
    //# convenience
    //##################################################

    private void initializeFilter()
    {
        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(_maxLength);
        setFilters(filter);
    }

}
