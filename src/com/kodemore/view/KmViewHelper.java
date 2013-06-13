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

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;

import com.kodemore.drawable.KmDrawableBuilder;
import com.kodemore.utility.KmRunnable;

public class KmViewHelper
{
    //##################################################
    //# constants
    //##################################################

    public static int DEFAULT_ANIMATION_SPEED = 300;

    //##################################################
    //# variables
    //##################################################

    private View      _view;

    //##################################################
    //# constructor
    //##################################################

    public KmViewHelper(View e)
    {
        _view = e;
    }

    //##################################################
    //# padding
    //##################################################

    public void setPadding(int px)
    {
        setPadding(px, px, px, px);
    }

    public void setPadding(int left, int top, int right, int bottom)
    {
        _view.setPadding(left, top, right, bottom);
    }

    public void setPaddingLeft(int left)
    {
        setPadding(left, _view.getPaddingTop(), _view.getPaddingRight(), _view.getPaddingBottom());
    }

    public void setPaddingTop(int top)
    {
        setPadding(_view.getPaddingLeft(), top, _view.getPaddingRight(), _view.getPaddingBottom());
    }

    public void setPaddingRight(int right)
    {
        setPadding(_view.getPaddingLeft(), _view.getPaddingTop(), right, _view.getPaddingBottom());
    }

    public void setPaddingBottom(int bottom)
    {
        setPadding(_view.getPaddingLeft(), _view.getPaddingTop(), _view.getPaddingRight(), bottom);
    }

    //##################################################
    //# visibility
    //##################################################

    public void show()
    {
        getView().setVisibility(View.VISIBLE);
    }

    public void hide()
    {
        getView().setVisibility(View.INVISIBLE);
    }

    public void gone()
    {
        getView().setVisibility(View.GONE);
    }

    public boolean isVisible()
    {
        return getView().getVisibility() == View.VISIBLE;
    }

    public boolean isHidden()
    {
        return getView().getVisibility() == View.INVISIBLE;
    }

    public boolean isGone()
    {
        return getView().getVisibility() == View.GONE;
    }

    public void toggleHidden()
    {
        if ( isVisible() )
            hide();
        else
            show();
    }

    public void toggleHiddenAnimated()
    {
        if ( isVisible() )
            hideAnimated();
        else
            showAnimated();
    }

    public void toggleGone()
    {
        if ( isVisible() )
            gone();
        else
            show();
    }

    public void toggleGoneAnimated()
    {
        if ( isVisible() )
            goneAnimated();
        else
            showAnimated();
    }

    //##################################################
    //# enabled
    //##################################################

    public void enable()
    {
        getView().setEnabled(true);
    }

    public void disable()
    {
        getView().setEnabled(false);
    }

    public boolean isEnabled()
    {
        return getView().isEnabled();
    }

    public void toggleEnabled()
    {
        if ( isEnabled() )
            disable();
        else
            enable();
    }

    //##################################################
    //# border
    //##################################################

    public void setRedBorder()
    {
        setBorder(Color.RED);
    }

    public void setGreenBorder()
    {
        setBorder(Color.GREEN);
    }

    public void setBlueBorder()
    {
        setBorder(Color.BLUE);
    }

    public void setBorder(int color)
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(2);
        b.addBorder(color, 2).setRadius(10);
        b.addPad(10);

        getView().setBackgroundDrawable(b.toDrawable());
    }

    //##################################################
    //# animate
    //##################################################

    public void showAnimated()
    {
        show();

        Animation a;
        a = new ScaleAnimation(1, 1, 0, 1);
        a.setDuration(DEFAULT_ANIMATION_SPEED);

        getView().startAnimation(a);
    }

    public void animateShowActionHide(KmAction onEndAction)
    {
        show();

        Animation a;
        a = new ScaleAnimation(1, 1, 1, 0);
        a.setAnimationListener(newEndAnimationListener(onEndAction));
        a.setDuration(DEFAULT_ANIMATION_SPEED);

        getView().startAnimation(a);

    }

    public void hideAnimated()
    {
        Animation a;
        a = new ScaleAnimation(1, 1, 1, 0);
        a.setAnimationListener(newHideAtEndAnimationListener());
        a.setDuration(DEFAULT_ANIMATION_SPEED);

        getView().startAnimation(a);
    }

    public void goneAnimated()
    {
        Animation a;
        a = new ScaleAnimation(1, 1, 1, 0);
        a.setAnimationListener(newGoneAtEndAnimationListener());
        a.setDuration(DEFAULT_ANIMATION_SPEED);

        getView().startAnimation(a);
    }

    private AnimationListener newGoneAtEndAnimationListener()
    {
        return new AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation e)
            {
                // ignored
            }

            @Override
            public void onAnimationRepeat(Animation e)
            {
                // ignored
            }

            @Override
            public void onAnimationEnd(Animation e)
            {
                gone();
            }
        };
    }

    private AnimationListener newHideAtEndAnimationListener()
    {
        return new AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation e)
            {
                // ignored
            }

            @Override
            public void onAnimationRepeat(Animation e)
            {
                // ignored
            }

            @Override
            public void onAnimationEnd(Animation e)
            {
                hide();
            }
        };
    }

    private AnimationListener newEndAnimationListener(final KmAction onEndAction)
    {
        return new AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation e)
            {
                // ignored
            }

            @Override
            public void onAnimationRepeat(Animation e)
            {
                // ignored
            }

            @Override
            public void onAnimationEnd(Animation e)
            {
                onEndAction.fire();
                showAnimated();
            }
        };
    }

    //##################################################
    //# support
    //##################################################

    protected Context getContext()
    {
        return _view.getContext();
    }

    protected View getView()
    {
        return _view;
    }

    //##################################################
    //# async
    //##################################################

    public void goneAsyncSafe(KmUiManager ui)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                gone();
            }
        }.runOnUiThread(ui);
    }

    public void showAsyncSafe(KmUiManager ui)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                show();
            }
        }.runOnUiThread(ui);
    }
}
