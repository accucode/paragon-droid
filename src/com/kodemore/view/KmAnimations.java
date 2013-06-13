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

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Static utility methods to create animations.
 */
public abstract class KmAnimations
{
    //##################################################
    //# constants
    //##################################################

    private static final int DEFAULT_SPEED = 200;

    //##################################################
    //# slide
    //##################################################

    public static Animation slideInFromLeft()
    {
        return slideX(-1.0f, 0.0f);
    }

    public static Animation slideInFromRight()
    {
        return slideX(1.0f, 0.0f);
    }

    public static Animation slideOutToLeft()
    {
        return slideX(0.0f, -1.0f);
    }

    public static Animation slideOutToRight()
    {
        return slideX(0.0f, 1.0f);
    }

    //##################################################
    //# fade in
    //##################################################

    public static Animation fadeIn()
    {
        return fadeIn(DEFAULT_SPEED);
    }

    public static Animation fadeIn(int speedMs)
    {
        return fade(0, 1, speedMs);
    }

    public static Animation fadeIn(int speedMs, int offsetMs)
    {
        Animation e;
        e = fade(0, 1, speedMs);
        e.setStartOffset(offsetMs);
        return e;
    }

    //##################################################
    //# fade out
    //##################################################

    public static Animation fadeOut()
    {
        return fadeOut(DEFAULT_SPEED);
    }

    public static Animation fadeOut(int speedMs)
    {
        return fadeOut(speedMs, 0);
    }

    public static Animation fadeOut(int speedMs, int offsetMs)
    {
        Animation e;
        e = fade(1, 0, speedMs);
        e.setStartOffset(offsetMs);
        return e;
    }

    //##################################################
    //# support
    //##################################################

    private static Animation fade(float from, float to, int speedMs)
    {
        AlphaAnimation e;
        e = new AlphaAnimation(from, to);
        e.setDuration(speedMs);
        return e;
    }

    private static Animation slideX(float fromX, float toX)
    {
        Animation e;
        e = new TranslateAnimation(
            Animation.RELATIVE_TO_PARENT,
            fromX,
            Animation.RELATIVE_TO_PARENT,
            toX,
            Animation.RELATIVE_TO_PARENT,
            0.0f,
            Animation.RELATIVE_TO_PARENT,
            0.0f);

        e.setDuration(DEFAULT_SPEED);
        e.setInterpolator(new AccelerateInterpolator());
        return e;
    }

}
