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

public class KmCost
    extends KmFixedDecimal<KmCost>
{
    //##################################################
    //# constants
    //##################################################

    public static final KmCost ZERO  = new KmCost(0);

    private static final int   SCALE = 5;

    //##################################################
    //# constructor
    //##################################################

    public KmCost(Double value)
    {
        super(value);
    }

    public KmCost(Integer value)
    {
        super(value);
    }

    protected KmCost(BigDecimal value)
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
        return 5;
    }

    //##################################################
    //# support
    //##################################################

    @Override
    protected KmCost newFixed(BigDecimal value)
    {
        return new KmCost(value);
    }

    @Override
    public KmCost getZero()
    {
        return ZERO;
    }
}
