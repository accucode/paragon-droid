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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.TypedValue;

import com.kodemore.drawable.KmDrawableBuilder;
import com.kodemore.utility.KmRunnable;
import com.kodemore.utility.Kmu;

public class KmTernaryCheckBoxView
    extends KmColumnLayout
{
    //##################################################
    //# constants
    //##################################################

    private static final String STATE_ORIGINAL = "original";
    private static final String STATE_CHECKED  = "checked";

    //##################################################
    //# variables
    //##################################################

    private Boolean             _state;

    private KmImageView         _trueImageView;
    private KmImageView         _falseImageView;
    private KmImageView         _nullImageView;

    private int                 _dp;

    private int                 _px;

    private int                 _trueImageId;
    private int                 _falseImageId;
    private int                 _nullImageId;

    private KmAction            _onChangedListener;

    private int                 _backgroundColor;

    //##################################################
    //# constructor
    //##################################################

    public KmTernaryCheckBoxView(KmUiManager ui)
    {
        super(ui);

        _dp = 24;
        _px = convertDpToPx(_dp);

        initialize();

        KmDrawableBuilder b = new KmDrawableBuilder();
        b.addBorder(Color.DKGRAY, 2);
        b.addBorder(Color.WHITE, 2);

        setBackgroundDrawable(b.toDrawable());
    }

    private void initialize()
    {
        _trueImageId = RR.drawable.trueIcon;
        _falseImageId = RR.drawable.falseIcon;
        _nullImageId = RR.drawable.nullIcon;

        _backgroundColor = Color.LTGRAY;

        _trueImageView = newImageView(_trueImageId, swapViewsAction());
        _falseImageView = newImageView(_falseImageId, swapViewsAction());
        _nullImageView = newImageView(_nullImageId, swapViewsAction());

        setItemWeightNone();
        setItemWidth(_px);
        addView(_trueImageView);
        addView(_falseImageView);
        addView(_nullImageView);

        refreshViews();
    }

    //##################################################
    //# image view
    //##################################################

    private KmImageView newImageView(int resid, KmAction action)
    {
        int imageHeight = _px;

        Bitmap image;
        image = BitmapFactory.decodeResource(getResources(), resid);
        image = Bitmap.createScaledBitmap(image, imageHeight, imageHeight, true);

        KmImageView e;
        e = ui().newImageView();
        e.setImageBitmap(image);
        e.setBackgroundColor(_backgroundColor);
        e.setOnClickListener(action);

        return e;
    }

    private int convertDpToPx(int dp)
    {
        float px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            getResources().getDisplayMetrics());

        return (int)px;
    }

    //##################################################
    //# change listener
    //##################################################

    public void setOnChangedListener(KmAction action)
    {
        _onChangedListener = action;
    }

    private void runExternalAction()
    {
        if ( _onChangedListener == null )
            return;

        _onChangedListener.fire();
    }

    //##################################################
    //# access
    //##################################################

    public Boolean getState()
    {
        return _state;
    }

    public void setState(Boolean e)
    {
        _state = e;

        refreshViews();
    }

    public int getTrueImageId()
    {
        return _trueImageId;
    }

    public void setTrueImageId(int trueImageId)
    {
        _trueImageId = trueImageId;

        reinitialize();
    }

    public int getFalseImageId()
    {
        return _falseImageId;
    }

    public void setFalseImageId(int falseImageId)
    {
        _falseImageId = falseImageId;

        reinitialize();
    }

    public int getNullImageId()
    {
        return _nullImageId;
    }

    public void setNullImageId(int nullImageId)
    {
        _nullImageId = nullImageId;

        reinitialize();
    }

    public int getBoxColor()
    {
        return _backgroundColor;
    }

    public void setBoxColor(int backgroundColor)
    {
        _backgroundColor = backgroundColor;

        reinitialize();
    }

    //##################################################
    //# action
    //##################################################

    private KmAction swapViewsAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSwapViews();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleSwapViews()
    {
        toggleState();
        runExternalAction();
        refreshViews();
    }

    //##################################################
    //# utility
    //##################################################

    private void reinitialize()
    {
        removeAllViews();
        initialize();
        invalidate();
    }

    private void refreshViews()
    {
        _trueImageView.gone();
        _falseImageView.gone();
        _nullImageView.gone();

        if ( _state == null )
        {
            _nullImageView.show();
            return;
        }

        if ( _state )
        {
            _trueImageView.show();
            return;
        }

        _falseImageView.show();
    }

    private void toggleState()
    {
        _state = !Kmu.isTrue(_state);
    }

    //##################################################
    //# save state
    //##################################################

    @Override
    protected Parcelable onSaveInstanceState()
    {
        Parcelable original = super.onSaveInstanceState();

        Bundle e;
        e = new Bundle();
        e.putParcelable(STATE_ORIGINAL, original);
        e.putString(STATE_CHECKED, getStateString());
        return e;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if ( state instanceof Bundle )
        {
            Bundle bundle = (Bundle)state;
            super.onRestoreInstanceState(bundle.getParcelable(STATE_ORIGINAL));
            setStateString(bundle.getString(STATE_CHECKED));
            return;
        }

        super.onRestoreInstanceState(state);
    }

    private String getStateString()
    {
        if ( _state == null )
            return "null";

        return _state
            ? "true"
            : "false";
    }

    private void setStateString(String s)
    {
        setState(Kmu.parseBoolean(s));
    }

    //##################################################
    //# async
    //##################################################

    public void setStateAsyncSafe(final Boolean e)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setState(e);
            }
        }.runOnUiThread(ui());
    }
}
