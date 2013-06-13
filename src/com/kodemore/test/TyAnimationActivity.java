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

import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Use animation to toggle the visibility of a view.
 * 
 * This also demonstrates how a linear layout (row or column)
 * can be set to automatically save its visibility state using
 * setSaveVisibility(). 
 */
public class TyAnimationActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmColumnLayout _animatedView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _animatedView = createAnimatedView();
        _animatedView.gone();
        _animatedView.setAutoSave();
        _animatedView.setSaveVisibility();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("Toggle...", newToggleAction());
        root.addText("This is before...");
        root.addView(_animatedView);
        root.addText("This is after.");
        return root;
    }

    private KmColumnLayout createAnimatedView()
    {
        KmColumnLayout e;
        e = ui().newColumn();
        e.addText("------------------------------");
        e.addText("Hidden View");
        e.addSpace();
        e.addText("More Words");
        e.addSpace();
        e.addText("Even More Words!");
        e.addText("------------------------------");
        e.setBackgroundColor(Color.DKGRAY);
        return e;
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newToggleAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleToggle();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleToggle()
    {
        _animatedView.toggleGoneAnimated();
    }
}
