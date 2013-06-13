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

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;

import com.kodemore.utility.Kmu;

/**
 * Wraps display.
 */
public class KmDisplay
{
    //##################################################
    //# variables
    //##################################################

    private Activity _activity;
    private Display  _display;

    //##################################################
    //# constructor
    //##################################################

    public KmDisplay(Activity e)
    {
        _activity = e;
        _display = e.getWindowManager().getDefaultDisplay();
    }

    //##################################################
    //# misc
    //##################################################

    public int getDisplayId()
    {
        return _display.getDisplayId();
    }

    public float getRefreshRate()
    {
        return _display.getRefreshRate();
    }

    //##################################################
    //# screen size (nominal)
    //##################################################

    /**
     * Gets the configuration screen layout value.  May be one of: 
     *      SCREENLAYOUT_SIZE_SMALL 
     *      SCREENLAYOUT_SIZE_NORMAL 
     *      SCREENLAYOUT_SIZE_LARGE 
     *      SCREENLAYOUT_SIZE_XLARGE (not supported in API 8)
     */
    private int getScreenLayoutSize()
    {
        int layout = _activity.getResources().getConfiguration().screenLayout;
        int mask = Configuration.SCREENLAYOUT_SIZE_MASK;

        return layout & mask;
    }

    private boolean hasScreenLayoutSize(int size)
    {
        return getScreenLayoutSize() == size;
    }

    public boolean isSmallScreen()
    {
        return hasScreenLayoutSize(Configuration.SCREENLAYOUT_SIZE_SMALL);
    }

    public boolean isScreenNormal()
    {
        return hasScreenLayoutSize(Configuration.SCREENLAYOUT_SIZE_NORMAL);
    }

    public boolean isScreenLarge()
    {
        return hasScreenLayoutSize(Configuration.SCREENLAYOUT_SIZE_LARGE);
    }

    public boolean isScreenSizeUndefined()
    {
        return hasScreenLayoutSize(Configuration.SCREENLAYOUT_SIZE_UNDEFINED);
    }

    public String getSizeName()
    {
        if ( isSmallScreen() )
            return "small";

        if ( isScreenNormal() )
            return "normal";

        if ( isScreenLarge() )
            return "large";

        if ( isScreenSizeUndefined() )
            return "undefined";

        return "unknown";
    }

    //##################################################
    //# rotation
    //##################################################

    /**
     * Determine the screen's rotation from its default position.
     * Note that the default position is not necessarily in portrait.
     * Returns one of the Surface.ROTATION_* constants (e.g.: Surface.ROTATION_0).
     */
    public int getRotation()
    {
        return _display.getRotation();
    }

    public boolean isRotated()
    {
        return getRotation() != Surface.ROTATION_0;
    }

    /**
     * Determine if the device has been rotated sideways from its default orientation.
     * Note: this is NOT the same as detecting portrait vs landscape.
     */
    public boolean isSideways()
    {
        int i = getRotation();

        return i == Surface.ROTATION_90 || i == Surface.ROTATION_270;
    }

    public int getRotationDegrees()
    {
        int i = getRotation();
        switch ( i )
        {
            case Surface.ROTATION_0:
                return 0;

            case Surface.ROTATION_90:
                return 90;

            case Surface.ROTATION_180:
                return 180;

            case Surface.ROTATION_270:
                return 270;

            default:
                return -1;
        }
    }

    public String getRotationName()
    {
        int i = getRotation();

        if ( i == Surface.ROTATION_0 )
            return "none";

        if ( i == Surface.ROTATION_90 )
            return "right";

        if ( i == Surface.ROTATION_180 )
            return "flip";

        if ( i == Surface.ROTATION_270 )
            return "left";

        return Kmu.format("unknown(%s)", i);
    }

    //##################################################
    //# portrait
    //##################################################

    /**
     * True if display is taller than it is wide meaured in pixels.
     */
    public boolean isPortrait()
    {
        return getHorizontalPixels() <= getVerticalPixels();
    }

    /**
     * see isPortrait()
     */
    public boolean isLandscape()
    {
        return !isPortrait();
    }

    //##################################################
    //# metrics
    //##################################################

    public DisplayMetrics getMetrics()
    {
        DisplayMetrics e = new DisplayMetrics();
        _display.getMetrics(e);
        return e;
    }

    public String getDensityName()
    {
        DisplayMetrics m = getMetrics();
        int i = m.densityDpi;

        if ( i == m.DENSITY_LOW )
            return "low";

        if ( i == m.DENSITY_MEDIUM )
            return "medium";

        if ( i == m.DENSITY_HIGH )
            return "high";

        return Kmu.format("unknown(%s)", i);
    }

    //##################################################
    //# nominal density
    //##################################################

    /**
     * The nominal density, used to adjust the nominal dpi from
     * a baseline value of 160.  See getNominalDpi().
     */
    public double getNominalDensity()
    {
        return getMetrics().density;
    }

    /**
     * This is an approximate dpi where one Density Independent Pixel unit is 
     * one pixel on an approximately 160 dpi screen.
     */
    public double getNominalDpi()
    {
        return getNominalDensity() * 160;
    }

    //##################################################
    //# relative size
    //#
    //# These metrics take the device's current rotation
    //# into account.  That is, they will change when 
    //# the device is rotated sideways. 
    //##################################################

    public int getHorizontalPixels()
    {
        return _display.getWidth();
    }

    public double getHorizontalInches()
    {
        return getHorizontalPixels() / getHorizontalDpi();
    }

    public double getHorizontalDpi()
    {
        return !isSideways()
            ? getXDpi()
            : getYDpi();
    }

    public int getVerticalPixels()
    {
        return _display.getHeight();
    }

    public double getVerticalInches()
    {
        return getVerticalPixels() / getVerticalDpi();
    }

    public double getVerticalDpi()
    {
        return !isSideways()
            ? getYDpi()
            : getXDpi();
    }

    //##################################################
    //# absolute XY metrics
    //#
    //# The following are NOT adjusted based on the 
    //# device's current rotation.  Instead these are
    //# best interpreted as if the device is oriented
    //# with a default (0-degree) rotation.
    //##################################################

    public double getXPixels()
    {
        return !isSideways()
            ? getHorizontalPixels()
            : getVerticalPixels();
    }

    public double getXInches()
    {
        return getXPixels() / getXDpi();
    }

    public double getXDpi()
    {
        return getMetrics().xdpi;
    }

    public double getYPixels()
    {
        return !isSideways()
            ? getVerticalPixels()
            : getHorizontalPixels();
    }

    public double getYInches()
    {
        return getYPixels() / getYDpi();
    }

    public double getYDpi()
    {
        return getMetrics().ydpi;
    }

    //##################################################
    //# convenience
    //##################################################

    public boolean isTabletSize()
    {
        return getXInches() >= 3.5;
    }
}
