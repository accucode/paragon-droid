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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;

import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmDisplay;
import com.kodemore.view.KmImageView;
import com.kodemore.view.RR;

/**
 * image view that togles between three states
 * 
 */
public class TyTriStateCheckBoxActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    KmColumnLayout _root;

    Boolean        _state;

    KmImageView    _t;
    KmImageView    _f;
    KmImageView    _n;

    int            _percent;

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

        _state = true;

        _percent = 4;

        _root = ui().newColumn();

        _t = newImageView(RR.drawable.trueIcon, swapViewsAction());
        _f = newImageView(RR.drawable.falseIcon, swapViewsAction());
        _n = newImageView(RR.drawable.nullIcon, swapViewsAction());

        _root.setItemWeightNone();
        _root.setItemWidth(calculateBoxSize());
        _root.addView(_t);
        _root.addView(_f);
        _root.addView(_n);

        _f.getHelper().gone();
        _n.getHelper().gone();

        return _root;
    }

    private KmImageView newImageView(int resid, KmAction action)
    {
        KmImageView e;
        e = ui().newImageView();

        int imageHeight = calculateBoxSize();

        Bitmap image = BitmapFactory.decodeResource(getResources(), resid);
        image = Bitmap.createScaledBitmap(image, imageHeight, imageHeight, true);

        e.setImageBitmap(image);
        e.setBackgroundColor(Color.LTGRAY);
        e.setOnClickListener(action);

        return e;

    }

    //##################################################
    //# action
    //##################################################

    private KmAction swapViewsAction()
    {

        return new KmAction()
        {
            @Override
            protected void handle()
            {
                /**
                 * the null check has to be first or else it breaks
                 */
                if ( _state == null )
                {
                    _n.getHelper().gone();
                    _t.getHelper().show();
                    _state = true;
                    toast("" + _state);
                    return;
                }

                if ( _state == true )
                {
                    _state = false;
                    _t.getHelper().gone();
                    _f.getHelper().show();
                    toast("" + _state);
                    return;
                }

                if ( _state == false )
                {
                    _f.getHelper().gone();
                    _n.getHelper().show();
                    _state = null;
                    toast("" + _state);
                    return;
                }

            }
        };
    }

    //##################################################
    //# utility
    //##################################################

    private int calculateBoxSize()
    {
        int percent = _percent;

        KmDisplay display = ui().getDisplay();

        int screenHeight = display.getVerticalPixels();
        int imageHeight = (int)(1.0 * screenHeight / 100 * percent);
        return imageHeight;
    }
}
