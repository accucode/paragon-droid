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
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;

/**
 * I am used to draw a simple two toned background that
 * covers (floods) the entire area.  We use this as a 
 * simple alternative to gradients since some devices
 * (e.g.: nexus 7) cause the gradients to flicker.
 * Most noticeable in black and gray gradients.
 */
public class KmVerticalTwoToneDrawable
    extends Drawable
{
    //##################################################
    //# variables
    //##################################################

    private PaintDrawable _topDrawable;
    private PaintDrawable _bottomDrawable;
    private Rect          _padding;

    //##################################################
    //# constructors
    //##################################################

    public KmVerticalTwoToneDrawable()
    {
        _topDrawable = new PaintDrawable();
        _bottomDrawable = new PaintDrawable();
        _padding = new Rect();
    }

    //##################################################
    //# colors
    //##################################################

    public void setTopColor(int c)
    {
        _topDrawable.setColorFilter(c, Mode.SRC);
    }

    public void setBottomColor(int c)
    {
        _bottomDrawable.setColorFilter(c, Mode.SRC);
    }

    @Override
    public void setAlpha(int e)
    {
        _topDrawable.setAlpha(e);
        _bottomDrawable.setAlpha(e);
    }

    @Override
    public void setColorFilter(ColorFilter cf)
    {
        _topDrawable.setColorFilter(cf);
        _bottomDrawable.setColorFilter(cf);
    }

    @Override
    public int getOpacity()
    {
        return PixelFormat.UNKNOWN;
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
        _padding.set(left, top, right, bottom);
        _topDrawable.setPadding(left, top, right, 0);
        _bottomDrawable.setPadding(left, 0, right, bottom);
    }

    @Override
    public boolean getPadding(Rect r)
    {
        r.set(_padding);
        return true;
    }

    //##################################################
    //# corners
    //##################################################

    public void setCornerRadius(int r)
    {
        _topDrawable.setCornerRadii(getTopCorners(r));
        _bottomDrawable.setCornerRadii(getBottomCorners(r));
    }

    private float[] getTopCorners(int r)
    {
        return toArray(r, r, r, r, 0, 0, 0, 0);
    }

    private float[] getBottomCorners(int r)
    {
        return toArray(0, 0, 0, 0, r, r, r, r);
    }

    private float[] toArray(float... arr)
    {
        return arr;
    }

    //##################################################
    //# draw
    //##################################################

    @Override
    public void draw(Canvas canvas)
    {
        Rect r = getBounds();
        int middle = (r.top + r.bottom) / 2;

        _topDrawable.setBounds(r.left, r.top, r.right, middle);
        _topDrawable.draw(canvas);

        _bottomDrawable.setBounds(r.left, middle, r.right, r.bottom);
        _bottomDrawable.draw(canvas);
    }
}
