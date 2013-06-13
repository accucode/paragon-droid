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

import com.kodemore.utility.Kmu;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;


public class KmHorizontalScrollView
    extends HorizontalScrollView
{
    //##################################################
    //# variables
    //##################################################

    /**
     * I can be used to disable scrolling via touch events.
     * If true (the default), touch events are handled normally.
     * If false, touch events are ignored.
     */
    private boolean _touchEvents;

    //##################################################
    //# constructor
    //##################################################

    public KmHorizontalScrollView(KmUiManager ui)
    {
        super(ui.getContext());

        _touchEvents = true;
    }

    //##################################################
    //# accessing
    //##################################################

    public boolean getTouchEvents()
    {
        return _touchEvents;
    }

    public void setTouchEvents(boolean e)
    {
        _touchEvents = e;
    }

    public void disableTouchEvents()
    {
        setTouchEvents(false);
    }

    //##################################################
    //# fading edge
    //##################################################

    public void setFadingEdgeEnabled(boolean e)
    {
        setHorizontalFadingEdgeEnabled(e);
        setVerticalFadingEdgeEnabled(e);
    }

    public void disableFadingEdge()
    {
        setFadingEdgeEnabled(false);
    }

    public void enableFadingEdge()
    {
        setFadingEdgeEnabled(true);
    }

    //##################################################
    //# listeners
    //##################################################

    public void addOnGlobalLayoutListener(OnGlobalLayoutListener e)
    {
        super.getViewTreeObserver().addOnGlobalLayoutListener(e);
    }

    public void removeOnGlobalLayoutListener(OnGlobalLayoutListener e)
    {
        getViewTreeObserver().removeGlobalOnLayoutListener(e);
    }

    //##################################################
    //# scroll 
    //##################################################

    public boolean scrollTo(View e)
    {
        Point p = getPositionFor(e);
        if ( p == null )
            return false;

        scrollTo(p);
        return true;
    }

    private boolean scrollTo(Point p)
    {
        if ( p == null )
            return false;

        scrollTo(p.x, p.y);
        return true;
    }

    public boolean smoothScrollTo(View e)
    {
        Point p = getPositionFor(e);
        if ( p == null )
            return false;

        smoothScrollTo(p);
        return true;
    }

    public boolean smoothScrollTo(Point p)
    {
        if ( p == null )
            return false;

        smoothScrollTo(p.x, p.y);
        return true;
    }

    public boolean smoothScrollToStart()
    {
        int x = 0;
        int y = getScrollY();
        smoothScrollTo(x, y);

        return true;
    }

    public boolean smoothScrollToEnd()
    {
        int n = getChildCount();
        if ( n == 0 )
            return false;

        int x = getChildAt(n - 1).getWidth() - getWidth();
        int y = getScrollY();
        smoothScrollTo(x, y);

        return true;
    }

    public boolean scrollTo(View e, boolean smooth)
    {
        return smooth
            ? smoothScrollTo(e)
            : scrollTo(e);
    }

    //##################################################
    //# touch
    //##################################################

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return _touchEvents
            ? super.onTouchEvent(ev)
            : false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return _touchEvents
            ? super.onInterceptTouchEvent(ev)
            : false;
    }

    //##################################################
    //# convenience
    //##################################################

    public Point getPositionFor(View e)
    {
        return Kmu.getPositionIn(this, e);
    }
}
