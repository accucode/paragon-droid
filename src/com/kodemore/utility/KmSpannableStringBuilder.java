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

package com.kodemore.utility;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

public class KmSpannableStringBuilder
    extends SpannableStringBuilder
{
    //##################################################
    //# constructors
    //##################################################

    public KmSpannableStringBuilder()
    {
        super();
    }

    public KmSpannableStringBuilder(CharSequence text)
    {
        super(text);
    }

    public KmSpannableStringBuilder(CharSequence text, int start, int end)
    {
        super(text, start, end);
    }

    //##################################################
    //# accessing
    //##################################################

    public void setSpan(Object what)
    {
        int start = 0;
        int end = length();
        int flags = 0;
        setSpan(what, start, end, flags);
    }

    public void setForegroundBlack()
    {
        setForegroundColor(Color.BLACK);
    }

    public void setForegroundColor(int color)
    {
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        setSpan(span);
    }

    public void setStyleNormal()
    {
        setStyle(Typeface.NORMAL);
    }

    public void setStyleItalic()
    {
        setStyle(Typeface.ITALIC);
    }

    public void setStyleBold()
    {
        setStyle(Typeface.BOLD);
    }

    public void setStyleBoldItalic()
    {
        setStyle(Typeface.BOLD_ITALIC);
    }

    public void setStyle(int style)
    {
        StyleSpan span = new StyleSpan(style);
        setSpan(span);
    }

    //##################################################
    //# append
    //##################################################

    public void appendItalic(String s)
    {
        StyleSpan span = new StyleSpan(Typeface.ITALIC);
        _append(s, span);
    }

    public void appendBold(String s)
    {
        StyleSpan span = new StyleSpan(Typeface.BOLD);
        _append(s, span);
    }

    public void appendBoldItalic(String s)
    {
        StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
        _append(s, span);
    }

    public void appendUnderline(String s)
    {
        UnderlineSpan span = new UnderlineSpan();
        _append(s, span);
    }

    //##################################################
    //# private 
    //##################################################

    /**
     * Append a string, applying the specified style to the newly added text.
     * Note that the android documentation is unclear about what types of
     * styles are actually supported by the setSpan method.  The javadoc
     * just says that span in an Object.  For now we are assuming that all
     * CharacterStyles are supported, but we have not explicitly tested this.
     */
    private void _append(String s, CharacterStyle span)
    {
        if ( s == null )
            return;

        int start = length();
        int end = start + s.length();

        append(s);

        setSpan(span, start, end, SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
