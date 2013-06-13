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

package com.kodemore.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * I am used to draw a child within a limited area 
 * of my parent.  My subclasses allow easy access for
 * top, bottom, left, and right.
 */
public abstract class KmAbstractEdgeDrawable
    extends Drawable
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The element to drop in the top section.
     */
    private Drawable _child;

    /**
     * The size of the top section.
     */
    private int      _size;

    /**
     * If true, the top will be drawn as an overlay, and will not 
     * create any padding.  If false, the top component will act
     * as padding that adjusts the layout.
     */
    private boolean  _overlay;

    //##################################################
    //# constructors
    //##################################################

    public KmAbstractEdgeDrawable(Drawable child, int size)
    {
        _child = child;
        _size = size;
        _overlay = false;
    }

    //##################################################
    //# accessing
    //##################################################

    public Drawable getChild()
    {
        return _child;
    }

    public int getSize()
    {
        return _size;
    }

    public void setOverlay(boolean e)
    {
        _overlay = e;
    }

    public void setOverlay()
    {
        setOverlay(true);
    }

    public boolean isOverlay()
    {
        return _overlay;
    }

    //##################################################
    //# draw
    //##################################################

    @Override
    public void draw(Canvas canvas)
    {
        getChild().draw(canvas);
    }

    @Override
    public int getChangingConfigurations()
    {
        int a = super.getChangingConfigurations();
        int b = getChild().getChangingConfigurations();

        return a | b;
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart)
    {
        getChild().setVisible(visible, restart);

        return super.setVisible(visible, restart);
    }

    @Override
    public void setAlpha(int alpha)
    {
        getChild().setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter e)
    {
        getChild().setColorFilter(e);
    }

    @Override
    public boolean isStateful()
    {
        return getChild().isStateful();
    }

    @Override
    protected boolean onStateChange(int[] state)
    {
        boolean changed;
        changed = getChild().setState(state);
        onBoundsChange(getBounds());
        return changed;
    }

    @Override
    public ConstantState getConstantState()
    {
        return null;
    }

    @Override
    public Drawable mutate()
    {
        return this;
    }

    @Override
    public int getOpacity()
    {
        return PixelFormat.UNKNOWN;
    }

    //##################################################
    //# abstract
    //##################################################

    @Override
    public abstract boolean getPadding(Rect r);

    @Override
    protected abstract void onBoundsChange(Rect r);

    @Override
    public abstract int getIntrinsicWidth();

    @Override
    public abstract int getIntrinsicHeight();

}
