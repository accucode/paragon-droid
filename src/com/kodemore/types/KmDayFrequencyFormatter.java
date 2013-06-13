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

import com.kodemore.utility.KmConstantsIF;

/**
 * I provide a default implementation of the KmTimeFormatterIF
 * and support the format codes defined in KmTimeUtility.format().
 */
public class KmDayFrequencyFormatter
    implements KmConstantsIF
{
    //##################################################
    //# format
    //##################################################

    public String formatAsNumbers(KmDayFrequency e)
    {
        StringBuilder sb = new StringBuilder();
        if ( e.hasMonday() )
            sb.append(DOW_INT_MONDAY);

        if ( e.hasTuesday() )
            sb.append(DOW_INT_TUESDAY);

        if ( e.hasWednesday() )
            sb.append(DOW_INT_WEDNESDAY);

        if ( e.hasThursday() )
            sb.append(DOW_INT_THURSDAY);

        if ( e.hasFriday() )
            sb.append(DOW_INT_FRIDAY);

        if ( e.hasSaturday() )
            sb.append(DOW_INT_SATURDAY);

        if ( e.hasSunday() )
            sb.append(DOW_INT_SUNDAY);

        return sb.toString();
    }
}
