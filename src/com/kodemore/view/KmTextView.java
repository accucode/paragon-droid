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

import android.widget.TextView;

import com.kodemore.utility.KmRunnable;
import com.kodemore.utility.Kmu;

public class KmTextView
    extends TextView
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;

    //##################################################
    //# constructor
    //##################################################

    public KmTextView(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;
        getHelper().setGravityTop();
    }

    //##################################################
    //# core
    //##################################################

    public KmUiManager ui()
    {
        return _ui;
    }

    public KmTextViewHelper getHelper()
    {
        return new KmTextViewHelper(this);
    }

    public void setAutoSave()
    {
        setId();
        setSaveEnabled(true);
        setFreezesText(true);
    }

    //##################################################
    //# id
    //##################################################

    public void setId()
    {
        if ( !hasId() )
            setId(ui().nextId());
    }

    public boolean hasId()
    {
        return getId() != NO_ID;
    }

    //##################################################
    //# visibility
    //##################################################

    public void show()
    {
        setVisibility(VISIBLE);
    }

    public void hide()
    {
        setVisibility(INVISIBLE);
    }

    public void gone()
    {
        setVisibility(GONE);
    }

    //##################################################
    //# gravity
    //##################################################

    public void setGravityTop()
    {
        getHelper().setGravityTop();
    }

    public void setGravityBottom()
    {
        getHelper().setGravityBottom();
    }

    public void setGravityLeft()
    {
        getHelper().setGravityLeft();
    }

    public void setGravityRight()
    {
        getHelper().setGravityRight();
    }

    public void setGravityCenter()
    {
        getHelper().setGravityCenter();
    }

    public void setGravityCenterVertical()
    {
        getHelper().setGravityCenterVertical();
    }

    public void setGravityCenterHorizontal()
    {
        getHelper().setGravityCenterHorizontal();
    }

    //##################################################
    //# convenience
    //##################################################

    public void setValue(CharSequence e)
    {
        if ( e == null )
            setText("");
        else
            setText(e);
    }

    public void setFormattedText(String msg, Object... args)
    {
        String s = Kmu.format(msg, args);
        setText(s);
    }

    public void clearValue()
    {
        setValue(null);
    }

    /**
     * This sets the text size as a percentage of the screen
     * height, independant of density.
     */
    public void setTextSizeAsPercentOfScreen(int percent)
    {
        KmDisplay display = ui().getDisplay();

        int screenHeight = display.getVerticalPixels();
        double density = display.getNominalDensity();

        int textHeight = (int)(1.0 * screenHeight / 100 * percent);
        int textSize = (int)Math.round(textHeight / density);

        setTextSize(textSize);
    }

    //##################################################
    //# async
    //##################################################

    public void setValueAsyncSafe(final CharSequence e)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setValue(e);
            }
        }.runOnUiThread(ui());
    }

    public void setFormattedTextAsyncSafe(final String msg, final Object... args)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                setFormattedText(msg, args);
            }
        }.runOnUiThread(ui());
    }

    public void clearValueAsync()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                clearValue();
            }
        }.runOnUiThread(ui());
    }

    //==================================================
    //= async :: visibility
    //==================================================

    public void showAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                show();
            }
        }.runOnUiThread(ui());
    }

    public void hideAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                hide();
            }
        }.runOnUiThread(ui());
    }

    public void goneAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                gone();
            }
        }.runOnUiThread(ui());
    }

    //==================================================
    //= async :: color
    //==================================================

    public void setTextColorAsyncSafe(final int e)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                getHelper().setTextColor(e);
            }
        }.runOnUiThread(ui());
    }

    public void setTextColorGreenAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                getHelper().setTextColorGreen();
            }
        }.runOnUiThread(ui());
    }

    public void setTextColorRedAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                getHelper().setTextColorRed();
            }
        }.runOnUiThread(ui());
    }

    public void setTextColorYellowAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                getHelper().setTextColorYellow();
            }
        }.runOnUiThread(ui());
    }
}
