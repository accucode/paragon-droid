/*
  Copyright (c) 2005-2011 www.kodemore.com

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

package com.kodemore.types;

import java.math.BigDecimal;

public class KmRate
    extends KmFixedDecimal<KmRate>
{
    //##################################################
    //# constants
    //##################################################

    // todo_wyatt: db
    public static final int    MAX_PRECISION = 20;

    public static final int    SCALE         = 8;

    public static final KmRate ZERO          = new KmRate(0);

    //##################################################
    //# constructor
    //##################################################

    public KmRate(Double value)
    {
        super(value);
    }

    public KmRate(Integer value)
    {
        super(value);
    }

    protected KmRate(BigDecimal value)
    {
        super(value);
    }

    //##################################################
    //# policy
    //##################################################

    @Override
    public int getScale()
    {
        return SCALE;
    }

    @Override
    public int getDisplayScale()
    {
        return 4;
    }

    //##################################################
    //# support
    //##################################################

    @Override
    protected KmRate newFixed(BigDecimal value)
    {
        return new KmRate(value);
    }

    @Override
    public KmRate getZero()
    {
        return ZERO;
    }
}
