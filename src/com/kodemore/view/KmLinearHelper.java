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

import android.view.Gravity;
import android.widget.LinearLayout;

public class KmLinearHelper
    extends KmGroupHelper
{
    //##################################################
    //# constructor
    //##################################################

    public KmLinearHelper(LinearLayout e, KmUiManager ui)
    {
        super(e, ui);
    }

    //##################################################
    //# gravity
    //##################################################

    public void setGravityTop()
    {
        getLine().setBaselineAligned(false);
        getLine().setGravity(Gravity.TOP);
    }

    public void setGravityBottom()
    {
        getLine().setBaselineAligned(true);
        getLine().setGravity(Gravity.BOTTOM);
    }

    public void setGravityCenter()
    {
        getLine().setBaselineAligned(false);
        getLine().setGravity(Gravity.CENTER);
    }

    public void setGravityCenterVertical()
    {
        getLine().setBaselineAligned(false);
        getLine().setGravity(Gravity.CENTER_VERTICAL);
    }

    public void setGravityCenterHorizontal()
    {
        getLine().setGravity(Gravity.CENTER_HORIZONTAL);
    }

    //##################################################
    //# support
    //##################################################

    public LinearLayout getLine()
    {
        return (LinearLayout)getGroup();
    }

}
