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
import android.view.Gravity;
import android.view.View;

import com.kodemore.drawable.KmDrawableBuilder;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmImageView;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextView;
import com.kodemore.view.KmTextViewHelper;
import com.kodemore.view.RR;

/**
 * Some simple toasts.
 */
public class TyCustomToastActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    private static final int PADDING   = 10;
    private static final int FONT_SIZE = 12;

    //##################################################
    //# variables
    //##################################################

    private KmRowLayout      _toastView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _toastView = newToastView();
    }

    private KmRowLayout newToastView()
    {
        KmImageView imageView;
        imageView = ui().newImageView();
        imageView.setImage(RR.drawable.infoIcon);

        KmTextView nameText;
        nameText = ui().newTextView();
        nameText.setText("This is a custom toast");

        KmTextViewHelper h;
        h = nameText.getHelper();
        h.setFontSize(FONT_SIZE);
        h.setTextColor(Color.BLACK);
        h.setPadding(PADDING);
        h.setGravity(Gravity.CENTER);
        h.setBold();

        KmRowLayout root;
        root = ui().newRow();
        root.addView(imageView);
        root.addView(nameText);
        setBackground(root);

        return root;
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("Test Custom Toast", newViewToastAction());

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newViewToastAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleToastView();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleToastView()
    {
        Kmu.toast(_toastView);
    }

    //##################################################
    //# utility
    //##################################################

    private void setBackground(View e)
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addGradientRight(Color.TRANSPARENT, Color.LTGRAY);

        e.setBackgroundDrawable(b.toDrawable());
    }

}
