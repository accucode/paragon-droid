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

/**
 * I represent weight in kilograms.
 *
 * IMPORTANT: I should NOT be used when running calculations
 * against large sets of data, as I wrap the KmDecimal.
 * See KmDecimal for more details.
 */
public class KmKilogram
    extends KmFixedDecimal<KmKilogram>
{
    //##################################################
    //# constants
    //##################################################

    public static final KmKilogram ZERO  = new KmKilogram(0);

    private static final int       SCALE = 5;

    //##################################################
    //# constructor
    //##################################################

    public KmKilogram(Double value)
    {
        super(value);
    }

    public KmKilogram(Integer value)
    {
        super(value);
    }

    protected KmKilogram(BigDecimal value)
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

    //##################################################
    //# support
    //##################################################

    @Override
    protected KmKilogram newFixed(BigDecimal value)
    {
        return new KmKilogram(value);
    }

    @Override
    public KmKilogram getZero()
    {
        return ZERO;
    }
}
