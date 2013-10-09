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

import android.graphics.Typeface;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.kodemore.collection.KmList;

public class KmTextSwitcherHelper
    extends KmViewHelper
{
    //##################################################
    //# constructor
    //##################################################

    public KmTextSwitcherHelper(TextSwitcher e)
    {
        super(e);
    }

    //##################################################
    //# accessing 
    //##################################################

    @Override
    protected TextSwitcher getView()
    {
        return (TextSwitcher)super.getView();
    }

    //##################################################
    //# font family
    //##################################################

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

    //##################################################
    //# font style
    //##################################################

    public void setNormal()
    {
        setFontStyle(Typeface.NORMAL);
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

    //##################################################
    //# text size
    //##################################################

    /**
     * Set size as Scaled Pixels.
     * http://developer.android.com/guide/topics/resources/more-resources.html#Dimension
     */
    public void setFontSize(int i)
    {
        for ( KmTextViewHelper e : getHelpers() )
            e.setFontSize(i);
    }

    //##################################################
    //# support (font)
    //##################################################

    public void setFontStyle(int style)
    {
        for ( KmTextViewHelper e : getHelpers() )
            e.setFontStyle(style);
    }

    public void setTextColor(int color)
    {
        for ( KmTextViewHelper e : getHelpers() )
            e.setTextColor(color);
    }

    public void setFontFamily(Typeface family)
    {
        for ( KmTextViewHelper e : getHelpers() )
            e.setFontFamily(family);
    }

    private KmList<TextView> getChildren()
    {
        KmList<TextView> v = new KmList<TextView>();
        TextSwitcher view = getView();

        int n = view.getChildCount();
        for ( int i = 0; i < n; i++ )
            v.add((TextView)view.getChildAt(i));

        return v;
    }

    private KmList<KmTextViewHelper> getHelpers()
    {
        KmList<KmTextViewHelper> v = new KmList<KmTextViewHelper>();

        KmList<TextView> children = getChildren();
        for ( TextView tv : children )
            v.add(new KmTextViewHelper(tv));

        return v;
    }
}
