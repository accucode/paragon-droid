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

import android.content.Intent;
import android.view.View;

import com.kodemore.intent.KmParamIntentCallback;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Pick an animal.  Used to test passing parameters and
 * return values between activities.
 */
public class TyAnimalPickerClientActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmParamIntentCallback<String> _colorCallback;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _colorCallback = newColorCallback();
        _colorCallback.register(ui());
    }

    //==================================================
    //= init :: callback
    //==================================================

    private KmParamIntentCallback<String> newColorCallback()
    {
        return new KmParamIntentCallback<String>()
        {
            @Override
            public Intent getRequest(String color)
            {
                TyAnimalFilter f = getFilter(color);
                return TyAnimalPickerActivity.createRequest(getContext(), f);
            }

            @Override
            public void handleOk(Intent intent)
            {
                TyAnimal e = TyAnimalPickerActivity.getResponse(intent);
                toast(e.getDisplayString());
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("Pick any...", newPickAction(null));
        root.addButton("Pick blue...", newPickAction("blue"));
        root.addButton("Pick red...", newPickAction("red"));
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newPickAction(final String color)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handlePick(color);
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handlePick(String color)
    {
        _colorCallback.run(color);
    }

    //##################################################
    //# utility
    //##################################################

    private TyAnimalFilter getFilter(String color)
    {
        if ( color == null )
            return null;

        TyAnimalFilter f;
        f = new TyAnimalFilter();
        f.setColor(color);
        return f;
    }
}
