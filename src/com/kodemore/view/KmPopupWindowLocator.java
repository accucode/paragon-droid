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

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * I am used to determine the best position to locate a popup
 * window relative to an anchor.  Ideally, I center the popup
 * centered below the anchor; but if that doesn't fit on the 
 * screen, alternate positions are tried.
 */
public class KmPopupWindowLocator
{
    //##################################################
    //# static
    //##################################################

    public static Point getLocationFor(KmUiManager ui, View anchor, View content)
    {
        KmPopupWindowLocator e;
        e = new KmPopupWindowLocator();
        e._ui = ui;
        e._anchor = anchor;
        e._content = content;
        return e.getLocation();
    }

    //##################################################
    //# variables
    //##################################################

    /**
     * Access to the ui manager.  This provides convenience methods
     * to access the context, display, and other info.
     */
    private KmUiManager _ui;

    /**
     * The view to which we want to anchor the popup.
     */
    private View        _anchor;

    /**
     * The content that will be displayed in the popup.
     */
    private View        _content;

    //==================================================
    //= variables :: cache
    //==================================================

    private Rect        _anchorLocation;
    private Point       _contentSize;
    private Integer     _screenWidth;
    private Integer     _screenHeight;

    //##################################################
    //# constructors
    //##################################################

    private KmPopupWindowLocator()
    {
        // private
    }

    //##################################################
    //# location
    //##################################################

    private Point getLocation()
    {
        return new Point(getX(), getY());
    }

    private int getX()
    {
        int left = getLeftOption();
        int right = getRightOption();
        int center = getCenterOption();

        if ( fitsAtX(center) )
            return center;

        if ( fitsAtX(left) )
            return left;

        if ( fitsAtX(right) )
            return right;

        return 0;
    }

    private int getY()
    {
        int lower = getLowerOption();
        int upper = getUpperOption();

        if ( fitsAtY(lower) )
            return lower;

        if ( fitsAtY(upper) )
            return upper;

        return lower;
    }

    //##################################################
    //# fits
    //##################################################

    private boolean fitsAtX(Integer x)
    {
        if ( x < 0 )
            return false;

        return x + getContentWidth() < getScreenWidth();
    }

    private boolean fitsAtY(Integer y)
    {
        if ( y < 0 )
            return false;

        return y + getContentHeight() < getScreenHeight();
    }

    //##################################################
    //# options
    //##################################################

    private int getLeftOption()
    {
        return getAnchorLeft();
    }

    private int getCenterOption()
    {
        return getAnchorCenterX() - getContentWidth() / 2;
    }

    private int getRightOption()
    {
        return getAnchorRight() - getContentWidth() - 1;
    }

    private int getUpperOption()
    {
        return getAnchorTop() - getContentHeight();
    }

    private int getLowerOption()
    {
        return getAnchorBottom();
    }

    //##################################################
    //# utility
    //##################################################

    private Rect getAnchorLocation()
    {
        if ( _anchorLocation == null )
            _anchorLocation = _ui.getLocationOnScreen(_anchor);

        return _anchorLocation;
    }

    private int getAnchorLeft()
    {
        return getAnchorLocation().left;
    }

    private int getAnchorRight()
    {
        return getAnchorLocation().right;
    }

    private int getAnchorTop()
    {
        return getAnchorLocation().top;
    }

    private int getAnchorBottom()
    {
        return getAnchorLocation().bottom;
    }

    private int getAnchorCenterX()
    {
        return getAnchorLocation().centerX();
    }

    private Point getContentSize()
    {
        if ( _contentSize == null )
            _contentSize = _ui.measureForContent(_content);

        return _contentSize;
    }

    private int getContentWidth()
    {
        return getContentSize().x;
    }

    private int getContentHeight()
    {
        return getContentSize().y;
    }

    private int getScreenWidth()
    {
        if ( _screenWidth == null )
            _screenWidth = getDisplay().getHorizontalPixels();

        return _screenWidth;
    }

    private int getScreenHeight()
    {
        if ( _screenHeight == null )
            _screenHeight = getDisplay().getVerticalPixels();

        return _screenHeight;
    }

    private KmDisplay getDisplay()
    {
        return _ui.getDisplay();
    }

}
