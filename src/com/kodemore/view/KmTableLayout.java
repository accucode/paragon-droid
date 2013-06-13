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
import android.widget.TableLayout;
import android.widget.TableRow;

public class KmTableLayout
    extends TableLayout
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;
    private double      _rowWeight;

    private Integer     _rowItemHeight;
    private Integer     _rowItemWidth;

    //##################################################
    //# constructor
    //##################################################

    public KmTableLayout(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;
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
    }

    //##################################################
    //# id
    //##################################################

    public void setId()
    {
        if ( !hasId() )
            setId(ui().nextId());
    }

    private boolean hasId()
    {
        return getId() != NO_ID;
    }

    //##################################################
    //# row weight
    //##################################################

    public double getRowWeight()
    {
        return _rowWeight;
    }

    public void setRowWeight(double e)
    {
        _rowWeight = e;
    }

    public void setRowWeightFull()
    {
        setRowWeight(1);
    }

    //##################################################
    //# stretch
    //##################################################

    public void setStretchAllColumns()
    {
        setStretchAllColumns(true);
    }

    public void setColumnStretchable(int columnIndex)
    {
        setColumnStretchable(columnIndex, true);
    }

    //##################################################
    //# row item height
    //##################################################

    public Integer getRowItemHeight()
    {
        return _rowItemHeight;
    }

    public boolean hasRowItemHeight()
    {
        return getRowItemHeight() != null;
    }

    public void setRowItemHeight(Integer e)
    {
        _rowItemHeight = e;
    }

    public void setRowItemHeightMatchParent()
    {
        setRowItemHeight(TableRow.LayoutParams.MATCH_PARENT);
    }

    public void setItemHeightWrapContent()
    {
        setRowItemHeight(TableRow.LayoutParams.WRAP_CONTENT);
    }

    //##################################################
    //# row item width
    //##################################################

    public Integer getRowItemWidth()
    {
        return _rowItemWidth;
    }

    public boolean hasRowItemWidth()
    {
        return getRowItemWidth() != null;
    }

    public void setRowItemWidth(Integer e)
    {
        _rowItemWidth = e;
    }

    public void setRowItemWidthMatchParent()
    {
        setRowItemWidth(TableRow.LayoutParams.MATCH_PARENT);
    }

    public void setRowItemWidthWrapContent()
    {
        setRowItemWidth(TableRow.LayoutParams.WRAP_CONTENT);
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

    public KmTableRow addRow()
    {
        KmTableRow e;
        e = new KmTableRow(ui());
        addView(e);
        applyItemLayout(e);
        return e;
    }

    public TableLayout.LayoutParams getLayoutParamsFor(View e)
    {
        return (TableLayout.LayoutParams)e.getLayoutParams();
    }

    private void applyItemLayout(View e)
    {
        TableLayout.LayoutParams p;
        p = getLayoutParamsFor(e);
        p.weight = (float)_rowWeight;

        if ( e instanceof KmTableRow )
        {
            KmTableRow row = (KmTableRow)e;

            if ( hasRowItemHeight() )
                row.setItemHeight(getRowItemHeight());

            if ( hasRowItemWidth() )
                row.setItemWidth(getRowItemWidth());
        }
    }

    //##################################################
    //# text
    //##################################################

    public KmTextView addText()
    {
        KmTextView e;
        e = ui().newTextView();
        addView(e);
        return e;
    }

    public KmTextView addText(CharSequence text)
    {
        KmTextView e;
        e = addText();
        e.setText(text);
        return e;
    }

    public void addSpace()
    {
        addText("");
    }
}
