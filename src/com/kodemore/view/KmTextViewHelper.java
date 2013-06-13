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

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.kodemore.utility.Kmu;

public class KmTextViewHelper
    extends KmViewHelper
{
    //##################################################
    //# constructor
    //##################################################

    public KmTextViewHelper(TextView e)
    {
        super(e);
    }

    //##################################################
    //# font
    //##################################################

    //==================================================
    //= font :: family
    //==================================================

    public void setSansSerif()
    {
        setFontFamily(Typeface.SANS_SERIF);
    }

    public void setSerif()
    {
        setFontFamily(Typeface.SERIF);
    }

    public void setMonospaced()
    {
        setFontFamily(Typeface.MONOSPACE);
    }

    //==================================================
    //= font :: family
    //==================================================

    public void setNormal()
    {
        setFontStyleNormal();
    }

    public void setBold()
    {
        setFontStyle(Typeface.BOLD);
    }

    public void setItalic()
    {
        setFontStyle(Typeface.ITALIC);
    }

    public void setBoldItalic()
    {
        setFontStyle(Typeface.BOLD_ITALIC);
    }

    //==================================================
    //= font :: underline
    //==================================================

    public void setUnderlined()
    {
        addPaintFlag(Paint.UNDERLINE_TEXT_FLAG);
    }

    public void addPaintFlag(int flag)
    {
        Kmu.addBitFlagsTo(getView().getPaintFlags(), flag);
    }

    public void removePaintFlag(int flag)
    {
        Kmu.removeBitFlagsFrom(getView().getPaintFlags(), flag);
    }

    //##################################################
    //# text size
    //##################################################

    /**
     * Set size as Scaled Pixels.
     * http://developer.android.com/guide/topics/resources/more-resources.html#Dimension
     */
    public void setFontSize(int i)
    {
        setFontSizeScaled(i);
    }

    /**
     * Set the size, as Scaled Pixels.
     * This is the recommended approach as it will scale
     * based on both screen density and also users text
     * size preference. 
     */
    public void setFontSizeScaled(int i)
    {
        getView().setTextSize(TypedValue.COMPLEX_UNIT_SP, i);
    }

    /**
     * Set the text size in Device Independent Pixels.
     * Does not scale to reflect user preference.
     * http://developer.android.com/guide/topics/resources/more-resources.html#Dimension
     */
    public void setFontSizeDensityIndependent(int i)
    {
        getView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, i);
    }

    /**
     * Set the point size.  72 points per inch.  Not exact
     * since the system has to guess at the physical dimensions.
     */
    public void setFontSizePoints(int i)
    {
        getView().setTextSize(TypedValue.COMPLEX_UNIT_PT, i);
    }

    //##################################################
    //# color
    //##################################################

    public void setTextColorRed()
    {
        setTextColor(Color.RED);
    }

    public void setTextColorYellow()
    {
        setTextColor(Color.YELLOW);
    }

    public void setTextColorGreen()
    {
        setTextColor(Color.GREEN);
    }

    public void setTextColorBlue()
    {
        setTextColor(Color.BLUE);
    }

    public void setTextColorBlack()
    {
        setTextColor(Color.BLACK);
    }

    public void setTextColorLtGray()
    {
        setTextColor(Color.LTGRAY);
    }

    public void setTextColor(int e)
    {
        getView().setTextColor(e);
    }

    //##################################################
    //# gravity
    //##################################################

    public void setGravity(int e)
    {
        getView().setGravity(e);
    }

    public void setGravityFill()
    {
        setGravity(Gravity.FILL);
    }

    public void setGravityTop()
    {
        setGravity(Gravity.TOP);
    }

    public void setGravityBottom()
    {
        setGravity(Gravity.BOTTOM);
    }

    public void setGravityLeft()
    {
        setGravity(Gravity.LEFT);
    }

    public void setGravityRight()
    {
        setGravity(Gravity.RIGHT);
    }

    public void setGravityCenter()
    {
        setGravity(Gravity.CENTER);
    }

    public void setGravityCenterHorizontal()
    {
        setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public void setGravityCenterVertical()
    {
        setGravity(Gravity.CENTER_VERTICAL);
    }

    //##################################################
    //# support
    //##################################################

    @Override
    protected TextView getView()
    {
        return (TextView)super.getView();
    }

    //##################################################
    //# support (font)
    //##################################################

    public void setFontStyleNormal()
    {
        getView().setTypeface(Typeface.DEFAULT);
    }

    public void setFontStyle(int style)
    {
        getView().setTypeface(getTypeface(), style);
    }

    public void setFontFamily(Typeface family)
    {
        getView().setTypeface(family, getFontStyle());
    }

    private int getFontStyle()
    {
        return getTypeface().getStyle();
    }

    private Typeface getTypeface()
    {
        Typeface e = getView().getTypeface();

        return e == null
            ? Typeface.DEFAULT
            : e;
    }
}
