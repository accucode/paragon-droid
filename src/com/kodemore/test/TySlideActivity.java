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

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewAnimator;

import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmAnimations;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextView;

/**
 * I test the ability slide views using you finger.
 * The view animator provides animation support.
 * The gesture detector lets you slide with your finger.
 */
public class TySlideActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private GestureDetector _gestureDetector;
    private ViewAnimator    _viewAnimator;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _gestureDetector = new GestureDetector(getContext(), newGestureListener());

        _viewAnimator = new ViewAnimator(getContext());
        _viewAnimator.setAnimateFirstView(true);

        _viewAnimator.addView(newText("aaa "), 0);
        _viewAnimator.addView(newText("bbb "), 1);
        _viewAnimator.addView(newText("ccc "), 2);
        _viewAnimator.addView(newText("ddd "), 3);
    }

    private KmTextView newText(String s)
    {
        return ui().newTextView(Kmu.repeat(s, 500));
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
        buttons = root.addRow();
        buttons.addButton("First", newFirstAction());
        buttons.addButton("<<", newPreviousAction());
        buttons.addButton("Test", newTestAction());
        buttons.addButton(">>", newNextAction());
        buttons.addButton("Last", newLastAction());

        root.addView(_viewAnimator);

        return root;
    }

    //##################################################
    //# motion
    //##################################################

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return _gestureDetector.onTouchEvent(event);
    }

    private GestureDetector.OnGestureListener newGestureListener()
    {
        return new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onFling(
                MotionEvent ev1,
                MotionEvent ev2,
                float velocityX,
                float velocityY)
            {
                return handleFling(velocityX);
            }
        };
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newFirstAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleFirst();
            }
        };
    }

    private KmAction newLastAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleLast();
            }
        };
    }

    private KmAction newPreviousAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handlePrevious();
            }
        };
    }

    private KmAction newNextAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleNext();
            }
        };
    }

    private KmAction newTestAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleTest();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleFirst()
    {
        int i = 0;
        gotoChild(i);
    }

    private void handleLast()
    {
        int i = _viewAnimator.getChildCount() - 1;
        gotoChild(i);
    }

    private void gotoChild(int i)
    {
        _viewAnimator.setOutAnimation(null);
        _viewAnimator.setInAnimation(null);
        _viewAnimator.setDisplayedChild(i);
    }

    private void handlePrevious()
    {
        _viewAnimator.setOutAnimation(KmAnimations.slideOutToRight());
        _viewAnimator.setInAnimation(KmAnimations.slideInFromLeft());
        _viewAnimator.showPrevious();
    }

    private void handleNext()
    {
        _viewAnimator.setOutAnimation(KmAnimations.slideOutToLeft());
        _viewAnimator.setInAnimation(KmAnimations.slideInFromRight());
        _viewAnimator.showNext();
    }

    private void handleTest()
    {
        toast("test");
    }

    private boolean handleFling(float velocityX)
    {
        if ( velocityX == 0 )
            return false;

        if ( velocityX < 0 )
            handleNext();
        else
            handlePrevious();

        return true;
    }
}
