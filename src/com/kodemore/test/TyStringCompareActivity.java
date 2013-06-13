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

package com.kodemore.test;

import android.view.View;

import com.kodemore.string.KmStringBuilder;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * This is an example of how NOT to compare two strings.
 */
public class TyStringCompareActivity
    extends KmActivity
{
    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        // none
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("Go", newGoAction());
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newGoAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleGo();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleGo()
    {
        String empty = "";
        String space = " ";
        String trimmed1 = space.trim();
        String trimmed2 = space.trim();

        KmStringBuilder out;
        out = new KmStringBuilder();
        out.println("empty.length = " + empty.length());
        out.println("trimmed1.length = " + trimmed1.length());
        out.println("trimmed2.length = " + trimmed2.length());
        out.println("empty == trimmed1? " + (empty == trimmed1));
        out.println("empty == trimmed2? " + (empty == trimmed2));
        out.println("trimmed1 == trimmed2? " + (empty == trimmed2));

        alert(out);
    }
}
