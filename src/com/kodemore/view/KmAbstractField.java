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
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kodemore.drawable.KmDrawables;
import com.kodemore.utility.KmSpannableStringBuilder;
import com.kodemore.utility.Kmu;

public abstract class KmAbstractField
    extends EditText
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;
    private boolean     _isMultiLine;

    //##################################################
    //# constructor
    //##################################################

    public KmAbstractField(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;
        setSingleLine();
        setGravity(Gravity.TOP);

        setBackgroundDrawable(KmDrawables.getTextFieldBackground());
        setTextColor(Color.WHITE);

        addFilter(newPrintableFilter());
    }

    //##################################################
    //# core
    //##################################################

    protected KmUiManager ui()
    {
        return _ui;
    }

    public KmViewHelper getHelper()
    {
        return new KmViewHelper(this);
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
    //# text
    //##################################################

    public void clearText()
    {
        setText("");
    }

    public boolean isEmpty()
    {
        return getText().length() == 0;
    }

    public boolean isNotEmpty()
    {
        return !isEmpty();
    }

    public boolean hasText()
    {
        return isNotEmpty();
    }

    @Override
    public void setSingleLine()
    {
        _isMultiLine = false;

        super.setSingleLine();
    }

    @Override
    public void setSingleLine(boolean b)
    {
        _isMultiLine = !b;

        super.setSingleLine(b);
    }

    public boolean isSingleLine()
    {
        return !_isMultiLine;
    }

    public void setMultiLine()
    {
        setSingleLine(false);
    }

    public boolean isMultiLine()
    {
        return _isMultiLine;
    }

    //##################################################
    //# password mask
    //##################################################

    public void setPasswordMask()
    {
        setPasswordMask(true);
    }

    public void setPasswordMask(boolean e)
    {
        if ( e )
            setTransformationMethod(new PasswordTransformationMethod());
        else
            setTransformationMethod(null);
    }

    //##################################################
    //# enable
    //##################################################

    public void setEnabled()
    {
        setEnabled(true);
    }

    public void setDisabled()
    {
        setEnabled(false);
    }

    //##################################################
    //# input type
    //##################################################

    /**
     * Update the input type bitmask.
     */
    public void addInputType(int flags)
    {
        int type = Kmu.addBitFlagsTo(getInputType(), flags);
        setInputType(type);
    }

    public void removeInputType(int flags)
    {
        int type = Kmu.removeBitFlagsFrom(getInputType(), flags);
        setInputType(type);
    }

    public void addInputTypeCapitalLetters()
    {
        addInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
    }

    public void setInputTypeNone()
    {
        setInputType(InputType.TYPE_NULL);
    }

    //##################################################
    //# soft key
    //##################################################

    public void setSoftKeyDisabled()
    {
        setFocusable(false);
        setInputTypeNone();
    }

    public void disableSoftKeySuggest()
    {
        addInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    public void enableSoftKeySuggest()
    {
        removeInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    //##################################################
    //# text changed
    //##################################################

    public void addTextChangedAction(final KmAction e)
    {
        addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // ignored
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // ignored
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                e.fire();
            }
        });
    }

    //##################################################
    //# touch listener
    //##################################################

    public void setOnTouchFocusOnly()
    {
        setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent ev)
            {
                requestFocus();
                return true;
            }
        });
    }

    //##################################################
    //# editor listener
    //##################################################

    public void setOnEditorEnter(final KmAction action)
    {
        setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent ev)
            {
                boolean isDown = ev.getAction() == ev.ACTION_DOWN;
                boolean isEnter = ev.getKeyCode() == ev.KEYCODE_ENTER;

                if ( isDown && isEnter )
                {
                    action.fire();
                    return true;
                }

                return false;
            }
        });
    }

    public void setOnEditorShiftEnter(final KmAction action)
    {
        setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent ev)
            {
                boolean isDown = ev.getAction() == ev.ACTION_DOWN;
                boolean isShift = ev.isShiftPressed();
                boolean isEnter = ev.getKeyCode() == ev.KEYCODE_ENTER;

                if ( isDown && isShift && isEnter )
                {
                    action.fire();
                    return true;
                }

                return false;
            }
        });
    }

    //##################################################
    //# display error
    //##################################################

    /** Note you must setError(null) to remove error on
     *  e.g. Nexus 7. Otherwise it stays.
     */

    @Override
    public void setError(CharSequence msg)
    {
        super.setError(toBlackText(msg));
    }

    @Override
    public void setError(CharSequence msg, Drawable icon)
    {
        super.setError(toBlackText(msg), icon);
    }

    public void clearError()
    {
        setError(null);
    }

    public void displayHint(CharSequence msg)
    {
        super.setHint(toGreyText(msg));
    }

    private CharSequence toBlackText(CharSequence msg)
    {
        if ( msg == null )
            return null;

        KmSpannableStringBuilder b;
        b = new KmSpannableStringBuilder(msg);
        b.setForegroundBlack();
        return b;
    }

    private CharSequence toGreyText(CharSequence msg)
    {
        if ( msg == null )
            return null;

        KmSpannableStringBuilder b;
        b = new KmSpannableStringBuilder(msg);
        b.setForegroundColor(Color.LTGRAY);
        return b;
    }

    //##################################################
    //# input type
    //##################################################

    public void setInputTypeDecimalNumber()
    {
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public void setInputTypeNumber()
    {
        setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    public void setInputTypeAllCaps()
    {
        setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
    }

    public void setRequired()
    {
        addTextChangedListener(newTextRequiredAction());
        displayHint("Required field");
    }

    //##################################################
    //# text required validator
    //##################################################

    private KmAction newTextRequiredAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleTextRequired();
            }
        };
    }

    private void handleTextRequired()
    {
        if ( Kmu.isEmpty(getText()) )
            setError("Field is required.");
        else
            clearError();
    }

    //##################################################
    //# filters
    //##################################################

    public void addFilter(InputFilter e)
    {
        if ( e == null )
            return;

        InputFilter[] oldFilters = getFilters();
        InputFilter[] newFilters;

        if ( oldFilters == null )
            newFilters = new InputFilter[1];
        else
        {
            newFilters = new InputFilter[oldFilters.length + 1];
            System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.length);
        }

        newFilters[newFilters.length - 1] = e;
        setFilters(newFilters);
    }

    /**
     * Determine if the input contains any non-printable characters
     * and, if so, disallow the entire input. 
     */
    private InputFilter newPrintableFilter()
    {
        return new InputFilter()
        {
            @Override
            public CharSequence filter(
                CharSequence src,
                int srcStart,
                int srcEnd,
                Spanned dest,
                int destStart,
                int destEnd)
            {
                if ( isMultiLine() && Kmu.containsNonPrintable(src) )
                    return "";

                if ( isSingleLine() && Kmu.containsNonLinePrintable(src) )
                    return "";

                return null;
            }
        };
    }

    //##################################################
    //# size
    //##################################################

    public void setTextSizeBasedOnScreenSize()
    {
        KmDisplay display = ui().getDisplay();

        if ( display.isTabletSize() )
            setTextSize(40);
        else
            setTextSize(25);
    }
}
