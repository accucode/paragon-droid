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

import com.kodemore.utility.Kmu;

public class KmUpcField
    extends KmAbstractField
{
    //##################################################
    //# constructor
    //##################################################

    public KmUpcField(KmUiManager ui)
    {
        super(ui);
        setInputTypeNumber();
        setMaxLength(13);
    }

    public KmUpcField(KmUiManager ui, int maxLength)
    {
        this(ui);

        setMaxLength(maxLength);
    }

    //##################################################
    //# accessing
    //##################################################

    public String getValue()
    {
        String s = getText().toString().trim();

        return Kmu.isEmpty(s)
            ? null
            : s;
    }

    public void setValue(String e)
    {
        if ( e == null )
            clearText();
        else
            setText(e);
    }

    public boolean hasValue()
    {
        return Kmu.hasValue(getValue());
    }

    public boolean isNull()
    {
        return !hasValue();
    }

    public void clearValue()
    {
        setValue(null);
    }

    //##################################################
    //# max length
    //##################################################

    public void setMaxLength(int i)
    {
        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(i);
        setFilters(filter);
    }
}
