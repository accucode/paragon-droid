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

package com.kodemore.test;

import android.view.View;

import com.kodemore.view.KmActivity;
import com.kodemore.view.KmDisplay;
import com.kodemore.view.KmTwoLineListView;

/**
 * Retrieve and list a variety of information about the
 * application's display characteristics.
 */
public class TyDisplayInfoActivity
    extends KmActivity
{
    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        // none
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmDisplay e = getDisplay();

        KmTwoLineListView view;
        view = new KmTwoLineListView(ui());

        view.addItem("Size Name", e.getSizeName());

        view.addItem("Horizontal Pixels", e.getHorizontalPixels());
        view.addItem("Horizontal Inches", e.getHorizontalInches());
        view.addItem("Horizontal Dpi", e.getHorizontalDpi());

        view.addItem("Vertical Pixels", e.getVerticalPixels());
        view.addItem("Vertical Inches", e.getVerticalInches());
        view.addItem("Vertical Dpi", e.getVerticalDpi());

        view.addItem("X Pixels", e.getXPixels());
        view.addItem("X Inches", e.getXInches());
        view.addItem("X Dpi", e.getXDpi());

        view.addItem("Y Pixels", e.getYPixels());
        view.addItem("Y Inches", e.getYInches());
        view.addItem("Y Dpi", e.getYDpi());

        view.addItem("Is Portrait", e.isPortrait());
        view.addItem("Is Landscape", e.isLandscape());

        view.addItem("Rotation", e.getRotation());
        view.addItem("Rotation Name", e.getRotationName());
        view.addItem("Rotation Degress", e.getRotationDegrees());
        view.addItem("Rotated", e.isRotated());

        view.addItem("Density", e.getNominalDensity());
        view.addItem("Density Name", e.getDensityName());

        view.addItem("Nominal Dpi", e.getNominalDpi());
        view.addItem("RefreshRate", e.getRefreshRate());

        return view;
    }
}
