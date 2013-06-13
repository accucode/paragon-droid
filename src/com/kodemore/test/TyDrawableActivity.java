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

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.kodemore.drawable.KmDrawableBuilder;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmLinearLayout;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmNamedAction;
import com.kodemore.view.KmTextView;

/**
 * Test the use of various drawable backgrounds.
 */
public class TyDrawableActivity
    extends KmActivity
{
    //##################################################
    //# variables 
    //##################################################

    private KmTextView                _targetView;
    private KmListView<KmNamedAction> _listView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _targetView = ui().newTextView("Sample Text");
        _targetView.setWidth(getDisplay().getHorizontalPixels());
        _targetView.setHeight(getDisplay().getVerticalPixels());

        _listView = newListView();
        _listView.setFastScrollEnabled();
        addSamples();
    }

    private KmListView<KmNamedAction> newListView()
    {
        return new KmListView<KmNamedAction>(ui())
        {
            @Override
            protected void onItemClick(KmNamedAction item)
            {
                item.fire();
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmLinearLayout root;

        root = getDisplay().isPortrait()
            ? ui().newColumn()
            : ui().newRow();

        root.setItemWeightFull();
        root.addView(_targetView);
        root.addView(_listView);
        return root;
    }

    //##################################################
    //# samples
    //##################################################

    private void addSamples()
    {
        addNone();

        addBorder1();
        addBorder2();
        addBorder3();

        addPaint1();
        addPaint2();

        addTwoTone1();
        addTwoTone2();

        addGradient1();
        addGradient2();
        addGradient3();
        addGradient4();
    }

    private void addNone()
    {
        add("none", null);
    }

    private void addBorder1()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addBorder(Color.RED);

        add("border 1", b);
    }

    private void addBorder2()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(5);
        b.addBorder(Color.RED);

        add("border 2", b);
    }

    private void addBorder3()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(5);
        b.addBorder(Color.RED);
        b.addPad(5);

        add("border 3", b);
    }

    private void addPaint1()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(5);
        b.addPaint(Color.BLUE, 5).setCornerRadius(20);

        add("paint 1", b);
    }

    private void addPaint2()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(5);
        b.addPaint(Color.BLUE, 10).setCornerRadius(20);
        b.addPaint(Color.GREEN, 10).setCornerRadius(10);
        b.addPaint(Color.RED, 10);
        b.addPaint(Color.BLACK, 2).setCornerRadius(10);
        b.addPaint(Color.YELLOW, 10).setCornerRadius(8);

        add("paint 2", b);
    }

    private void addGradient1()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addGradientDown(Color.WHITE, Color.BLACK);

        add("gradient 1", b);
    }

    private void addGradient2()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addBorder(Color.RED, 2);
        b.addGradientDown(Color.WHITE, Color.BLACK);

        add("gradient 2", b);
    }

    private void addGradient3()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addBorder(Color.RED, 2);
        b.addGradientDown(Color.WHITE, Color.BLACK);
        b.addPad(20);

        add("gradient 3", b);
    }

    private void addGradient4()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addBorder(Color.RED, 2);
        b.addGradientDown(Color.WHITE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.BLACK);
        b.addPad(20);

        add("gradient 4", b);
    }

    private void addTwoTone1()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addVerticalTwoTone(Color.RED, Color.BLUE).setCornerRadius(10);

        add("two tone 1", b);
    }

    private void addTwoTone2()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(5);
        b.addVerticalTwoTone(Color.RED, Color.BLUE, 10).setCornerRadius(10);

        add("two tone 2", b);
    }

    //##################################################
    //# utility
    //##################################################

    private void add(String name, KmDrawableBuilder b)
    {
        Drawable d = b == null
            ? null
            : b.toDrawable();

        KmNamedAction e = newAction(name, d);
        _listView.addItem(e);
    }

    private KmNamedAction newAction(String name, final Drawable d)
    {
        return new KmNamedAction(name)
        {
            @Override
            protected void handle()
            {
                _targetView.setBackgroundDrawable(d);
            }
        };
    }
}
