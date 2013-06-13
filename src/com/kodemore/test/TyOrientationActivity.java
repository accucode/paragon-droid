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

import com.kodemore.utility.KmLog;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextField;
import com.kodemore.view.KmTextView;
import com.kodemore.view.value.KmStringValue;

/**
 * Demonstration alternate layouts for portrait and landscape.
 * Save state across restarts.
 */
public class TyOrientationActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    /**
     * This is a simple non-visual value holder.  This value is NOT 
     * attached to the view hierarchy, but does register with the
     * activity in order to automatically manage its state.
     */
    private KmStringValue _name;

    /**
     * A simple text field. We display the name and phone fields in
     * different order depending on whether we are in portrait or
     * landscape.  The fields should retain their focus and value
     * when orientation is changed.
     */
    private KmTextField   _nameField;
    private KmTextField   _phoneField;

    /**
     * A simple text view.  We normally don't need to bind textViews,
     * since we usually don't set their content dynamically.  However,
     * this demonstrates that we can bind them and automatically manage
     * their state when needed.
     */
    private KmTextView    _customText;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _name = ui().newStringValue();
        _name.setAutoSave();

        _nameField = ui().newTextField();
        _nameField.setAutoSave();

        _phoneField = ui().newTextField();
        _phoneField.setAutoSave();

        _customText = ui().newTextView("...");
        _customText.setAutoSave();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        return isPortrait()
            ? createPortraitRoot()
            : createLandscapeRoot();
    }

    private View createPortraitRoot()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.setPadding(10);
        root.addText("Portrait");
        root.addText("Save state and focus on rotation.");
        root.addText("Name, then phone.");

        root.addSpace();
        root.addText("Name");
        root.addView(_nameField);

        root.addSpace();
        root.addText("Phone");
        root.addView(_phoneField);

        root.addSpace();
        root.addView(_customText);
        root.addSpace();

        KmRowLayout buttons;
        buttons = root.addEvenRow();
        buttons.addButton("Set Text", newSetTextAction());
        buttons.addButton("Show Msg", TyMessageActivity.class);
        buttons.addButton("Save Name", newSaveNameAction());
        buttons.addButton("Show Name", newShowNameAction());

        return root.inScrollView();
    }

    private View createLandscapeRoot()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.setPadding(10);
        root.addText("Landscape");
        root.addText("Save state and focus on rotation.");
        root.addText("Phone, then name.");

        root.addSpace();
        root.addText("Phone");
        root.addView(_phoneField);

        root.addSpace();
        root.addText("Name");
        root.addView(_nameField);

        root.addSpace();
        root.addView(_customText);
        root.addSpace();

        KmRowLayout buttons;
        buttons = root.addEvenRow();
        buttons.addButton("Set Text", newSetTextAction());
        buttons.addButton("Show Msg", TyMessageActivity.class);
        buttons.addButton("Save Name", newSaveNameAction());
        buttons.addButton("Show Name", newShowNameAction());

        return root.inScrollView();
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newSaveNameAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSaveName();
            }
        };
    }

    private KmAction newShowNameAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleShowName();
            }
        };
    }

    private KmAction newSetTextAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSetText();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleSetText()
    {
        String name = _nameField.getValue();
        String phone = _phoneField.getValue();

        String s = getDisplay().isPortrait()
            ? Kmu.format("%s %s", name, phone)
            : Kmu.format("%s %s", phone, name);

        _customText.setText(s);
    }

    private void handleSaveName()
    {
        KmLog.debug("handleSaveName");

        _name.setValue(_nameField.getValue());
    }

    private void handleShowName()
    {
        toast(Kmu.toDisplay(_name.getValue()));
    }
}
