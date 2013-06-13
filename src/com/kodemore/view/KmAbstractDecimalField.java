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

import com.kodemore.utility.KmDecimalInputFilter;
import com.kodemore.utility.Kmu;

public abstract class KmAbstractDecimalField
    extends KmAbstractField
{
    //##################################################
    //# constants
    //##################################################

    private static final String SCALE_MSG = "Value will be rounded.";

    //##################################################
    //# variables
    //##################################################

    private Integer             SCALE     = 10;
    private Integer             PRECISION = 20;

    //##################################################
    //# constructor
    //##################################################

    public KmAbstractDecimalField(KmUiManager ui)
    {
        super(ui);

        assignFilters();
    }

    public KmAbstractDecimalField(KmUiManager ui, int scale, int precision)
    {
        super(ui);

        SCALE = scale;
        PRECISION = precision;

        assignFilters();
    }

    private void assignFilters()
    {
        setInputTypeDecimalNumber();
        addTextChangedListener(newTextWatcherAction());
        addFilter(newDecimalFilter());
    }

    //##################################################
    //# convenience
    //##################################################

    public Integer getPrecision()
    {
        return PRECISION;
    }

    public Integer getScale()
    {
        return SCALE;
    }

    //##################################################
    //# format
    //##################################################

    protected String formatValue(Double e)
    {
        if ( e == null )
            return "";

        if ( e.isNaN() )
            return "";

        return Kmu.formatDouble(e, getScale());
    }

    //##################################################
    //# text validation
    //##################################################

    private void validateText()
    {
        String field = getText().toString();

        if ( Kmu.countDigitsAfterDecimalIn(field.trim()) > SCALE )
            setError(SCALE_MSG);
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newTextWatcherAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleTextWatcher();
            }
        };
    }

    //##################################################
    //# handle
    //################################################## 

    private void handleTextWatcher()
    {
        validateText();
    }

    //##################################################
    //# filter
    //##################################################

    protected InputFilter newDecimalFilter()
    {
        KmDecimalInputFilter f;
        f = new KmDecimalInputFilter();
        f.setField(this);
        f.setMaxPrecision(PRECISION);
        f.setScale(SCALE);
        return f;
    }

}
