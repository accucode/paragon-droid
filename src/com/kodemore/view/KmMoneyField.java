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

import com.kodemore.types.KmMoney;
import com.kodemore.utility.KmDecimalInputFilter;
import com.kodemore.utility.KmRunnable;
import com.kodemore.utility.KmString;

public class KmMoneyField
    extends KmAbstractDecimalField
{
    //##################################################
    //# constants 
    //##################################################

    public static final Integer SCALE     = 2;
    public static final Integer PRECISION = 18;

    //##################################################
    //# constructor
    //##################################################

    public KmMoneyField(KmUiManager ui)
    {
        super(ui, SCALE, PRECISION);
    }

    //##################################################
    //# accessing
    //##################################################

    public KmMoney getValue()
    {
        KmString s = KmString.create(getText().toString().trim());

        if ( s.isEmpty() )
            return null;

        return KmMoney.parse(s.toString());
    }

    public void setValue(KmMoney e)
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

    @Override
    protected InputFilter newDecimalFilter()
    {
        KmDecimalInputFilter f;
        f = new KmDecimalInputFilter();
        f.setField(this);
        f.setScale(SCALE);
        f.setMaxPrecision(PRECISION);
        return f;
    }

    //##################################################
    //# async
    //##################################################

    public void setValueAsyncSafe(final KmMoney e)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setValue(e);
            }
        }.runOnUiThread(ui());
    }

}
