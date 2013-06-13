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

import com.kodemore.collection.KmList;
import com.kodemore.utility.KmSamples;
import com.kodemore.view.KmSlideMenuActivity;
import com.kodemore.view.KmStringListView;

/**
 * This is a test of the KmSlideMenuActivity.  
 * To add views and menus, views must be set inside the
 * setViews() method.
 */
public class TySlideMenuActivity
    extends KmSlideMenuActivity
{
    //##################################################
    //# setup
    //##################################################

    @Override
    public void setup()
    {
        setMainView(createMain());
        setMainHeader(createMainHeader());

        addLeftMenuItem("Left 1", newToastAction("L1"));
        addLeftMenuItem("Left 2", newToastAction("L2"));
        addLeftMenuItem("one more for the left", newToastAction("L3"));

        addRightMenuItem("Right?", newToastAction("R1"));
        addRightMenuItem("Right", newToastAction("R2"));
        addRightMenuItem("I'm confused...", newToastAction("R3"));
    }

    //##################################################
    //# create main views
    //##################################################

    private View createMain()
    {
        KmList<String> items = KmSamples.getAnimalTypes();

        KmStringListView list;
        list = new KmStringListView(ui());
        list.addItems(items);
        return list;
    }

    private View createMainHeader()
    {
        return ui().newTextView("An optional header");
    }

}
