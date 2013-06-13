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

import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTextView;

/**
 * I demonstrate the use of alternate font families and
 * styles.  E.g.: serif, with bold and italic.
 */
public class TyFontFamilyActivity
    extends KmActivity
{
    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        //none
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        root.addBoldLabel("Default:");
        root.addSpace();

        KmTextView text;
        text = root.addLabel("This is sample text using the Default font");

        text = root.addLabel("This is sample text using the Default font in Bold");
        text.getHelper().setBold();

        text = root.addLabel("This is sample text using the Default font in Italic");
        text.getHelper().setItalic();

        text = root.addLabel("This is sample text using the Default font in Bold Italic");
        text.getHelper().setBoldItalic();

        root.addSpace();
        root.addBoldLabel("Serif:");
        root.addSpace();

        text = root.addLabel("This is sample text using the Serif font");
        text.getHelper().setSerif();

        text = root.addLabel("This is sample text using the Serif font in Bold");
        text.getHelper().setSerif();
        text.getHelper().setBold();

        text = root.addLabel("This is sample text using the Serif font in Italic");
        text.getHelper().setSerif();
        text.getHelper().setItalic();

        text = root.addLabel("This is sample text using the Serif font in Bold Italic");
        text.getHelper().setSerif();
        text.getHelper().setBoldItalic();

        root.addSpace();
        root.addBoldLabel("Sans Serif:");
        root.addSpace();

        text = root.addLabel("This is sample text using the Sans Serif font");
        text.getHelper().setSansSerif();

        text = root.addLabel("This is sample text using the Sans Serif font in Bold");
        text.getHelper().setSansSerif();
        text.getHelper().setBold();

        text = root.addLabel("This is sample text using the Sans Serif font in Italic");
        text.getHelper().setSansSerif();
        text.getHelper().setItalic();

        text = root.addLabel("This is sample text using the Sans Serif font in Bold Italic");
        text.getHelper().setSansSerif();
        text.getHelper().setBoldItalic();

        root.addSpace();
        root.addBoldLabel("Monospace:");
        root.addSpace();

        text = root.addLabel("This is sample text using the Monospace font");
        text.getHelper().setMonospaced();

        text = root.addLabel("This is sample text using the Monospace font in Bold");
        text.getHelper().setMonospaced();
        text.getHelper().setBold();

        text = root.addLabel("This is sample text using the Monospace font in Italic");
        text.getHelper().setMonospaced();
        text.getHelper().setItalic();

        text = root.addLabel("This is sample text using the Monospace font in Bold Italic");
        text.getHelper().setMonospaced();
        text.getHelper().setBoldItalic();

        root.addSpace();
        root.addBoldLabel("Other Style Options:");
        root.addSpace();

        text = root.addLabel("This is bold black text with a white glow");
        text.getHelper().setBold();
        text.getHelper().setTextColorBlack();
        text.setShadowLayer(2, 0, 0, Color.WHITE);

        text = root.addLabel("This is bold default text with a blue shadow");
        text.getHelper().setBold();
        text.setShadowLayer(4, 0, 2, Color.BLUE);

        return root.inScrollView();
    }
}
