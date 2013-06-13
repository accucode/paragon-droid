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

public class KmBorderLayout
    extends KmLinearLayout
{
    //##################################################
    //# variables
    //##################################################

    private KmRowLayout    _top;
    private KmColumnLayout _left;
    private KmColumnLayout _center;
    private KmColumnLayout _right;
    private KmRowLayout    _bottom;

    private boolean        _updateTopVisibilityFlag;
    private boolean        _updateLeftVisibilityFlag;
    private boolean        _updateCenterVisibilityFlag;
    private boolean        _updateRightVisibilityFlag;
    private boolean        _updateBottomVisibilityFlag;

    //##################################################
    //# constructor
    //##################################################

    public KmBorderLayout(KmUiManager ui)
    {
        super(ui);

        _updateTopVisibilityFlag = true;
        _updateLeftVisibilityFlag = true;
        _updateCenterVisibilityFlag = true;
        _updateRightVisibilityFlag = true;
        _updateBottomVisibilityFlag = true;

        setOrientation(VERTICAL);
        setItemWidthMatchParent();
        setItemHeightWrapContent();
        setItemWeightNone();

        _top = ui.newRow();
        _top.getHelper().gone();
        _top.getHelper().setPadding(5);

        _left = ui.newColumn();
        _left.getHelper().gone();
        _left.getHelper().setPadding(5);

        _center = ui.newColumn();
        _center.getHelper().gone();
        _center.getHelper().setPadding(5);

        _right = ui.newColumn();
        _right.getHelper().gone();
        _right.getHelper().setPadding(5);

        _bottom = ui.newRow();
        _bottom.getHelper().gone();
        _bottom.getHelper().setPadding(5);

        KmRowLayout container;
        container = ui.newRow();
        container.getHelper().setPadding(5);
        container.addView(_left);

        container.setItemWeightFull();
        container.addView(_center);

        container.setItemWeightNone();
        container.addView(_right);

        addView(_top);

        setItemWeightFull();
        addView(container);

        setItemWeightNone();
        addView(_bottom);
    }

    //##################################################
    //# debug
    //##################################################

    public void enableDebugColoring()
    {
        _top.setBackgroundColor(Color.RED);
        _left.setBackgroundColor(Color.BLUE);
        _center.setBackgroundColor(Color.GREEN);
        _right.setBackgroundColor(Color.YELLOW);
        _bottom.setBackgroundColor(Color.MAGENTA);
    }

    //##################################################
    //# accessor
    //##################################################

    public KmRowLayout getTopView()
    {
        if ( _updateTopVisibilityFlag )
        {
            _top.getHelper().show();
            _updateTopVisibilityFlag = false;
        }

        return _top;
    }

    public KmColumnLayout getLeftView()
    {
        if ( _updateLeftVisibilityFlag )
        {
            _left.getHelper().show();
            _updateLeftVisibilityFlag = false;
        }

        return _left;
    }

    public KmColumnLayout getCenterView()
    {
        if ( _updateCenterVisibilityFlag )
        {
            _center.getHelper().show();
            _updateCenterVisibilityFlag = false;
        }

        return _center;
    }

    public KmColumnLayout getRightView()
    {
        if ( _updateRightVisibilityFlag )
        {
            _right.getHelper().show();
            _updateRightVisibilityFlag = false;
        }

        return _right;
    }

    public KmRowLayout getBottomView()
    {
        if ( _updateBottomVisibilityFlag )
        {
            _bottom.getHelper().show();
            _updateBottomVisibilityFlag = false;
        }

        return _bottom;
    }

}
