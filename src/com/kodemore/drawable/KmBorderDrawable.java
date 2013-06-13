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
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * I am used to draw a simple border.  Unliked the 
 * of my parent.  My subclasses allow easy access for
 * top, bottom, left, and right.
 */
public class KmBorderDrawable
    extends Drawable
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The border color.
     */
    private ColorFilter _colorFilter;

    /**
     * The alpha.
     */
    private int         _alpha;

    /**
     * The border width.
     */
    private int         _width;

    /**
     * The corner radius.
     */
    private int         _radius;

    //##################################################
    //# constructors
    //##################################################

    public KmBorderDrawable()
    {
        this(Color.BLACK);
    }

    public KmBorderDrawable(int color)
    {
        this(color, 1);
    }

    public KmBorderDrawable(int color, int width)
    {
        setColor(color);
        setOpaque();
        setWidth(width);
    }

    //##################################################
    //# color
    //##################################################

    public ColorFilter getColorFilter()
    {
        return _colorFilter;
    }

    @Override
    public void setColorFilter(ColorFilter e)
    {
        _colorFilter = e;
    }

    public void setColor(int color)
    {
        setColorFilter(color, Mode.SRC);
    }

    //##################################################
    //# alpha
    //##################################################

    public int getAlpha()
    {
        return _alpha;
    }

    @Override
    public void setAlpha(int e)
    {
        _alpha = e;
    }

    public void setOpaque()
    {
        setAlpha(255);
    }

    //##################################################
    //# width
    //##################################################

    public int getWidth()
    {
        return _width;
    }

    public void setWidth(int e)
    {
        _width = e;
    }

    //##################################################
    //# rounded
    //##################################################

    public int getRadius()
    {
        return _radius;
    }

    public void setRadius(int e)
    {
        _radius = e;
    }

    public boolean hasRadius()
    {
        return _radius > 0;
    }

    //##################################################
    //# draw
    //##################################################

    @Override
    public void draw(Canvas canvas)
    {
        Rect b = getBounds();

        Paint p;
        p = new Paint();
        p.setColorFilter(getColorFilter());
        p.setAlpha(getAlpha());
        p.setStyle(Style.STROKE);
        p.setStrokeWidth(getWidth());
        p.setAntiAlias(true);

        float left = (float)(b.left + (_width + .5) / 2);
        float right = (float)(b.right - (_width + .5) / 2);
        float top = (float)(b.top + (_width + .5) / 2);
        float bottom = (float)(b.bottom - (_width + .5) / 2);
        RectF r = new RectF(left, top, right, bottom);

        if ( hasRadius() )
            canvas.drawRoundRect(r, _radius, _radius, p);
        else
            canvas.drawRect(r, p);
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public int getOpacity()
    {
        return PixelFormat.UNKNOWN;
    }

    @Override
    public boolean getPadding(Rect r)
    {
        r.left = _width;
        r.right = _width;
        r.top = _width;
        r.bottom = _width;
        return true;
    }

}
