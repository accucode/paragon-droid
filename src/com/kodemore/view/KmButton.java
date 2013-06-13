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

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import com.kodemore.drawable.KmDrawableBuilder;
import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.KmRunnable;

public class KmButton
    extends KmLinearLayout
{
    //##################################################
    //# enum
    //##################################################

    public enum Style
    {
        /**
         * Common styles across multiples apps.
         */
        TWO_TONE,
        GRADIENT,
        SOLID,
        ERROR,
        WARNING,

        /**
         * Application specific styles...
         */
        AO_BAR_INVENTORY,
        AO_BAR_ORDER,
        AO_BAR_REGISTER,
        AO_BAR_TEST,
        AO_BAR_INSTALL;
    }

    //##################################################
    //# variables
    //##################################################

    private KmTextView  _text;
    private KmImageView _firstImage;
    private KmImageView _secondImage;
    private Style       _style;

    //##################################################
    //# constructor
    //##################################################

    public KmButton(KmUiManager ui)
    {
        super(ui);

        setOrientationHorizontal();

        setGravityCenter();
        setOnTouchListener(newOnTouchListener());
        setClickable(true);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

        setStyleTwoTone();

        setItemWeightNone();
        setItemWidthWrapContent();
        setItemHeightWrapContent();

        _text = new KmTextView(ui);
        _text.setTextColor(Color.LTGRAY);
        _text.setTextSize(KmConstantsIF.TEXT_SIZE);
        _text.setSingleLine(false);
        _text.getHelper().setGravityFill();
        _text.setPadding(3, 0, 3, 0);
        _text.gone();

        _firstImage = new KmImageView(ui);
        _firstImage.gone();

        _secondImage = new KmImageView(ui);
        _secondImage.gone();

        addView(_firstImage);
        addView(_text);
        addView(_secondImage);
    }

    //##################################################
    //# auto save
    //##################################################

    @Override
    public void setAutoSave()
    {
        super.setAutoSave();

        _text.setAutoSave();
        _firstImage.setAutoSave();
        _secondImage.setAutoSave();
    }

    //##################################################
    //# orientation
    //##################################################

    public void setOrientationHorizontal()
    {
        setOrientation(HORIZONTAL);
    }

    public void setOrientationVertical()
    {
        setOrientation(VERTICAL);
    }

    public void toggleOrientation()
    {
        if ( isVertical() )
            setOrientationHorizontal();
        else
            setOrientationVertical();
    }

    public boolean isVertical()
    {
        return getOrientation() == VERTICAL;
    }

    public boolean isHorizontal()
    {
        return getOrientation() == HORIZONTAL;
    }

    //##################################################
    //# text
    //##################################################

    public void setText(CharSequence text)
    {
        _text.setText(text);
        _text.show();
    }

    public void setTextShadow(int color)
    {
        _text.setShadowLayer(2, 0, 0, color);
    }

    public void setHeight(int px)
    {
        _text.setHeight(px);
    }

    public void setTextSize(float sp)
    {
        _text.setTextSize(sp);
    }

    public void setTextColor(int color)
    {
        _text.setTextColor(color);
    }

    public void setTextColor(String color)
    {
        _text.setTextColor(Color.parseColor(color));
    }

    public void setTextBold()
    {
        _text.getHelper().setBold();
    }

    public void clearText()
    {
        _text.clearValue();
        _text.gone();
    }

    //##################################################
    //# images
    //##################################################

    public void setFirstImage(int resId)
    {
        _firstImage.setImage(resId);
        _firstImage.show();
    }

    public void setFirstImageScaled(int resId, int percent)
    {
        _firstImage.setImageScaled(resId, percent);
        _firstImage.show();
    }

    public void setSecondImage(int resId)
    {
        _secondImage.setImage(resId);
        _secondImage.show();
    }

    public void setSecondImageScaled(int resId, int percent)
    {
        _secondImage.setImageScaled(resId, percent);
        _secondImage.show();
    }

    public void setFirstImageBitmap(Bitmap e)
    {
        _firstImage.setImageBitmap(e);
    }

    public void setSecondImageBitmap(Bitmap e)
    {
        _secondImage.setImageBitmap(e);
    }

    //##################################################
    //# style
    //##################################################

    public void setStyle(Style e)
    {
        _style = e;
        setBackgroundDrawable(getNormalBackground());
    }

    public void setStyleGradient()
    {
        setStyle(Style.GRADIENT);
    }

    public void setStyleTwoTone()
    {
        setStyle(Style.TWO_TONE);
    }

    public void setStyleSolid()
    {
        setStyle(Style.SOLID);
    }

    public void setStyleError()
    {
        setStyle(Style.ERROR);
    }

    public void setStyleWarning()
    {
        setStyle(Style.WARNING);
    }

    //##################################################
    //# config
    //##################################################

    public void beError()
    {
        setStyleError();
        setFirstImage(RR.drawable.errorIcon);
        _text.gone();
        _secondImage.gone();
    }

    public void beWarning()
    {
        setStyleWarning();
        setFirstImage(RR.drawable.warningIcon);
        _text.gone();
        _secondImage.gone();
    }

    //##################################################
    //# touch
    //##################################################

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return true;
    }

    private OnTouchListener newOnTouchListener()
    {
        return new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent ev)
            {
                handleOnTouch(ev);
                return false;
            }
        };
    }

    private void handleOnTouch(MotionEvent ev)
    {
        int action = ev.getAction();

        if ( action == ev.ACTION_DOWN )
            setBackgroundDrawable(getPressedBackground());

        if ( action == ev.ACTION_UP )
            setBackgroundDrawable(getNormalBackground());

        if ( action == ev.ACTION_CANCEL )
            setBackgroundDrawable(getNormalBackground());
    }

    //##################################################
    //# background
    //##################################################

    private Drawable getNormalBackground()
    {
        switch ( _style )
        {
            case GRADIENT:
                return getGradientBackground(getTopColor(), getBottomColor());

            case SOLID:
                return getSolidBackground(getTopColor(), getBottomColor());

            case TWO_TONE:
                return getTwoToneBackground(getTopColor(), getBottomColor());

            case ERROR:
                return getGradientBackground("#ef4444", "#7c0804");

            case WARNING:
                return getGradientBackground("#FEEC04", "#EA9216");

            case AO_BAR_INVENTORY:
                return getTwoToneBackground("#324355", "#2d3d4d");

            case AO_BAR_ORDER:
                return getTwoToneBackground("#876708", "#745807");

            case AO_BAR_REGISTER:
                return getTwoToneBackground(getRedTopColor(), getRedBottomColor());

            case AO_BAR_TEST:
                return getTwoToneBackground(getRedTopColor(), getRedBottomColor());

            case AO_BAR_INSTALL:
                return getTwoToneBackground("#52495A", "#48404F");
        }
        return null;
    }

    private Drawable getPressedBackground()
    {
        switch ( _style )
        {
            case GRADIENT:
                return getGradientBackground(getBottomColor(), getTopColor());

            case SOLID:
                return getSolidBackground(getBottomColor(), getTopColor());

            case TWO_TONE:
                return getTwoToneBackground(getBottomColor(), getTopColor());

            case ERROR:
                return getGradientBackground("#7c0804", "#ef4444");

            case WARNING:
                return getGradientBackground("#EA9216", "#FEEC04");

            case AO_BAR_INVENTORY:
                return getTwoToneBackground("#2d3d4d", "#324355");

            case AO_BAR_ORDER:
                return getTwoToneBackground("#745807", "#876708");

            case AO_BAR_REGISTER:
                return getTwoToneBackground(getRedBottomColor(), getRedTopColor());

            case AO_BAR_TEST:
                return getTwoToneBackground(getRedBottomColor(), getRedTopColor());

            case AO_BAR_INSTALL:
                return getTwoToneBackground("#48404F", "#52495A");
        }
        return null;
    }

    private Drawable getTwoToneBackground(String top, String bottom)
    {
        KmDrawableBuilder e;
        e = new KmDrawableBuilder();
        e.addPad(2);
        e.addGradientDown(bottom, top).setCornerRadius(10);
        e.addPad(2);
        e.addVerticalTwoTone(top, bottom).setCornerRadius(8);
        e.addPad(8);
        return e.toDrawable();
    }

    private Drawable getGradientBackground(String top, String bottom)
    {
        KmDrawableBuilder e;
        e = new KmDrawableBuilder();
        e.addPad(2);
        e.addGradientDown(bottom, top).setCornerRadius(10);
        e.addPad(2);
        e.addGradientDown(top, bottom).setCornerRadius(8);
        e.addPad(8);
        return e.toDrawable();
    }

    private Drawable getSolidBackground(String top, String bottom)
    {
        KmDrawableBuilder e;
        e = new KmDrawableBuilder();
        e.addPad(2);
        e.addPaint(bottom).setCornerRadius(10);
        e.addPad(2);
        e.addPaint(top).setCornerRadius(8);
        e.addPad(8);
        return e.toDrawable();
    }

    private String getTopColor()
    {
        return "#202020";
    }

    private String getBottomColor()
    {
        return "#171717";
    }

    private String getRedTopColor()
    {
        return "#872808";
    }

    private String getRedBottomColor()
    {
        return "#742207";
    }

    //##################################################
    //# size
    //##################################################

    public void setTextSizeAsPercentOfScreen(int percent)
    {
        float textSize = ui().getTextSizeAsScreenPercent(percent);
        setTextSize(textSize);
    }

    public void setImageSizeAsPercentOfScreen(int resId, int percent)
    {
        Bitmap bmp = ui().toBitmap(resId, percent);
        setFirstImageBitmap(bmp);
    }

    public void setOrientationBasedOnScreenSize()
    {
        KmDisplay display = ui().getDisplay();
        boolean isPortrait = display.isPortrait();
        boolean isNotTabletSize = !display.isTabletSize();

        if ( isNotTabletSize && isPortrait )
            setOrientationVertical();
        else
            setOrientationHorizontal();
    }

    //##################################################
    //# padding
    //##################################################

    public void setTextPadding()
    {
        setPadding(PADDING);
    }

    public void setTextPadding(int i)
    {
        setPadding(i, i, i, i);
    }

    public void setTextPadding(int left, int top, int right, int bottom)
    {
        _text.setPadding(left, top, right, bottom);
    }

    //##################################################
    //# on click
    //##################################################

    public void setOnClickToast(String msg)
    {
        setOnClickListener(new KmToastAction(msg));
    }

    //##################################################
    //# async
    //##################################################

    public void setTextAsyncSafe(final CharSequence text)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setText(text);
            }
        }.runOnUiThread(ui());
    }

    public void setOrientationBasedOnScreenSizeAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setOrientationBasedOnScreenSize();
            }
        }.runOnUiThread(ui());
    }

    public void setOrientationHorizontalAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setOrientationHorizontal();
            }
        }.runOnUiThread(ui());
    }

    public void setOrientationVerticalAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setOrientationVertical();
            }
        }.runOnUiThread(ui());
    }

    public void setSecondImageAsyncSafe(final int resId)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setSecondImage(resId);
            }
        }.runOnUiThread(ui());
    }
}
