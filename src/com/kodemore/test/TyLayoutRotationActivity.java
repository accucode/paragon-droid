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

import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;

/**
 * An activity that changes between two different layouts 
 * depending on orientation.  
 */
public class TyLayoutRotationActivity
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
        if ( getDisplay().isPortrait() )
            return installPortraitLayout();

        return installLandscapeLayout();
    }

    //==================================================
    //= layout :: layouts
    //==================================================

    private View installPortraitLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        KmRowLayout buttons;
        buttons = root.addRow();
        buttons.addButton("Portrait", newToastAction("Portrait"));

        root.addText("I am the portrait layout");

        root.setItemWeightFull();
        root.addView(ui().newTextField());

        root.setItemWeightNone();
        buttons = root.addRow();
        buttons.addButton("Portrait", newToastAction("Portrait"));

        return root;
    }

    private View installLandscapeLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        KmRowLayout buttons;
        buttons = root.addRow();
        buttons.addButton("Landscape", newToastAction("Landscape"));
        buttons.addButton("Landscape", newToastAction("Landscape"));

        root.addText("I am the landscape layout");

        root.setItemWeightFull();
        root.addView(ui().newTextField());

        root.setItemWeightNone();
        buttons = root.addRow();
        buttons.addButton("Landscape", newToastAction("Landscape"));
        buttons.addButton("Landscape", newToastAction("Landscape"));
        buttons.addButton("Landscape", newToastAction("Landscape"));

        return root;
    }
}
