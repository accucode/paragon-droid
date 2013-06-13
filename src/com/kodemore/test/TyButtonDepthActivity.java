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

import android.graphics.Color;
import android.view.View;

import com.kodemore.drawable.KmDrawableBuilder;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmButton;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;

/**
 * I am used for temporary tests.
 */
public class TyButtonDepthActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    private static final Integer BUTTON_HEIGHT = 50;

    //##################################################
    //# variables
    //##################################################

    private KmButton             _firstButton;
    private KmButton             _secondButton;

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

        KmRowLayout buttons;
        buttons = root.addEvenRow();

        _firstButton = buttons.addButton("One", newToastAction("One"));
        _firstButton.setHeight(BUTTON_HEIGHT);

        _secondButton = buttons.addButton("Two", newToastAction("Two"));
        _secondButton.setHeight(BUTTON_HEIGHT);

        setBackgroundFirstButton();
        setBackgroundSecondButton();

        return root;
    }

    //##################################################
    //# utility
    //##################################################

    private void setBackgroundFirstButton()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addBorder(Color.parseColor("#626271"), 2);
        b.addGradientDown(
            Color.parseColor("#808080"),
            Color.parseColor("#a6a8ad"),
            Color.parseColor("#a6a8ad"),
            Color.parseColor("#808080"));

        _firstButton.setBackgroundDrawable(b.toDrawable());
    }

    private void setBackgroundSecondButton()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addBorder(Color.BLACK, 2);
        b.addGradientRectangle(
            _secondButton,
            Color.DKGRAY,
            Color.LTGRAY,
            Color.LTGRAY,
            Color.LTGRAY,
            Color.LTGRAY,
            Color.LTGRAY,
            Color.DKGRAY);

        _secondButton.setBackgroundDrawable(b.toDrawable());
    }
}
