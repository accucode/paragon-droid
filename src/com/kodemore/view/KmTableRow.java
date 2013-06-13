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

import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class KmTableRow
    extends TableRow
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;

    private int         _itemHeight;
    private int         _itemWidth;

    //##################################################
    //# constructor
    //##################################################

    public KmTableRow(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;
        setItemHeightWrapContent();
        setItemWidthWrapContent();
    }

    //##################################################
    //# core
    //##################################################

    protected KmUiManager ui()
    {
        return _ui;
    }

    public KmLinearHelper getHelper()
    {
        return new KmLinearHelper(this, ui());
    }

    public void setAutoSave()
    {
        setId();
        setSaveEnabled(true);
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
    //# views
    //##################################################

    public void skip()
    {
        TextView e = ui().newTextView();
        addView(e);
    }

    public KmButton addButton(String text, KmAction action)
    {
        KmButton e;
        e = ui().newButton(text, action);
        addView(e);
        return e;
    }

    //##################################################
    //# item
    //##################################################
    //==================================================
    //= item :: height
    //==================================================

    public int getItemHeight()
    {
        return _itemHeight;
    }

    public void setItemHeight(int e)
    {
        _itemHeight = e;
    }

    public void setItemHeightMatchParent()
    {
        setItemHeight(TableRow.LayoutParams.MATCH_PARENT);
    }

    public void setItemHeightWrapContent()
    {
        setItemHeight(TableRow.LayoutParams.WRAP_CONTENT);
    }

    //==================================================
    //= item :: width
    //==================================================

    public int getItemWidth()
    {
        return _itemWidth;
    }

    public void setItemWidth(int e)
    {
        _itemWidth = e;
    }

    public void setItemWidthMatchParent()
    {
        setItemWidth(TableRow.LayoutParams.MATCH_PARENT);
    }

    public void setItemWidthWrapContent()
    {
        setItemWidth(TableRow.LayoutParams.WRAP_CONTENT);
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    public void addView(View e)
    {
        super.addView(e);

        applyItemLayout(e);
    }

    private void applyItemLayout(View e)
    {
        TableRow.LayoutParams p;
        p = getLayoutParamsFor(e);
        p.height = _itemHeight;
        p.width = _itemWidth;
    }

    public TableRow.LayoutParams getLayoutParamsFor(View e)
    {
        return (TableRow.LayoutParams)e.getLayoutParams();
    }

    public TableRow.LayoutParams getLayoutParamsForLast()
    {
        return getLayoutParamsFor(getLastChild());
    }

    //##################################################
    //# children
    //##################################################

    public View getFirstChild()
    {
        return getChildAt(0);
    }

    public View getLastChild()
    {
        return getChildAt(getChildCount() - 1);
    }

    //##################################################
    //# text
    //##################################################

    public KmTextView addText(CharSequence s)
    {
        return getHelper().addText(s);
    }

    public KmTextView addLabel()
    {
        KmTextView e = ui().newLabel();
        addView(e);
        return e;
    }

    public KmTextView addLabel(CharSequence text)
    {
        KmTextView e;
        e = addLabel();
        e.setText(text);
        return e;
    }

    //##################################################
    //# buttons
    //##################################################

    public KmButton addButton()
    {
        return getHelper().addButton();
    }

    public KmButton addButton(CharSequence s)
    {
        return getHelper().addButton(s);
    }

    public KmButton addButton(CharSequence s, KmAction a)
    {
        return getHelper().addButton(s, a);
    }

    public KmButton addBackButton()
    {
        return getHelper().addBackButton();
    }

    public KmButton addScaledBackButton(int percent)
    {
        return getHelper().addScaledBackButton(percent);
    }

    public KmButton addImageButton(int resId, KmAction action)
    {
        KmButton e = ui().newImageButton(resId, action);
        addView(e);
        return e;
    }

    public KmButton addImageButtonScaled(int resId, int percent, KmAction action)
    {
        return getHelper().addImageButtonScaled(resId, percent, action);
    }

    public KmButton addImageButtonScaled(int resId, String text, int percent, KmAction action)
    {
        return getHelper().addImageButtonScaled(resId, text, percent, action);
    }
}
