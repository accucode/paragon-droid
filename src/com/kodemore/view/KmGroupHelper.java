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
import android.view.View;
import android.view.ViewGroup;

import com.kodemore.intent.KmSimpleIntentCallback;

public class KmGroupHelper
    extends KmViewHelper
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;

    private boolean     _usesItemPadding;

    private int         _itemPaddingLeft;
    private int         _itemPaddingRight;
    private int         _itemPaddingTop;
    private int         _itemPaddingBottom;

    //##################################################
    //# constructor
    //##################################################

    public KmGroupHelper(ViewGroup e, KmUiManager ui)
    {
        super(e);

        _ui = ui;
    }

    //##################################################
    //# ui
    //##################################################

    public KmUiManager ui()
    {
        return _ui;
    }

    //##################################################
    //# add
    //##################################################

    public void addView(View e)
    {
        add(e);
    }

    //##################################################
    //# text
    //##################################################

    public KmTextView addText(CharSequence text)
    {
        KmTextView e;
        e = ui().newTextView();
        e.setText(text);
        return add(e);
    }

    //##################################################
    //# buttons
    //##################################################

    public KmButton addButton()
    {
        return add(ui().newButton());
    }

    public KmButton addButton(CharSequence text)
    {
        KmButton e;
        e = addButton();
        e.setText(text);
        return e;
    }

    public KmButton addButton(CharSequence text, KmAction a)
    {
        KmButton e;
        e = addButton();
        e.setText(text);
        e.setOnClickListener(a);
        return e;
    }

    public KmButton addBackButton()
    {
        Activity activity = ui().getActivity();
        KmAction action = KmActions.newBackAction(activity);

        KmButton e;
        e = addButton();
        e.setFirstImage(RR.drawable.backIcon);
        e.setText("Back");
        e.setOnClickListener(action);
        return e;
    }

    public KmButton addScaledBackButton(int percent)
    {
        KmButton e;
        e = addBackButton();
        e.setFirstImageScaled(RR.drawable.backIcon, percent);
        return e;
    }

    public KmButton addCancelButton(KmAction action)
    {
        KmButton e;
        e = addButton("Cancel", action);
        e.setFirstImage(RR.drawable.cancelIcon);
        return e;
    }

    public KmButton addExportButton(KmAction action)
    {
        KmButton e;
        e = addButton("Export", action);
        e.setFirstImage(RR.drawable.exportIcon);
        return e;
    }

    public KmButton addForwardButton(CharSequence text, KmAction action)
    {
        KmButton e;
        e = addButton(text, action);
        e.setSecondImage(RR.drawable.forwardIcon);
        return e;
    }

    public KmButton addSaveButton(KmAction action)
    {
        return addForwardButton("Save", action);
    }

    public KmButton addSendButton(KmAction action)
    {
        KmButton e;
        e = addButton("Send", action);
        e.setFirstImage(RR.drawable.sendIcon);
        return e;
    }

    public KmButton addAddButton(KmAction action)
    {
        KmButton e;
        e = addButton("Add", action);
        e.setFirstImage(RR.drawable.addIcon);
        return e;
    }

    public KmButton addAddButton(KmSimpleIntentCallback e)
    {
        return addAddButton(e.newAction());
    }

    public KmButton addEditButton(KmAction action)
    {
        KmButton e;
        e = addButton("Edit", action);
        e.setFirstImage(RR.drawable.editIcon);
        return e;
    }

    public KmButton addEditButton(KmSimpleIntentCallback e)
    {
        return addEditButton(e.newAction());
    }

    public KmButton addSearchButton(KmAction action)
    {
        return addSearchButton(action, true);
    }

    public KmButton addSearchButton(KmSimpleIntentCallback e)
    {
        return addSearchButton(e.newAction());
    }

    public KmButton addSearchButton(KmAction action, boolean showText)
    {
        KmButton e;
        e = addButton();
        e.setOnClickListener(action);
        e.setFirstImage(RR.drawable.searchIcon);

        if ( showText )
            e.setText("Search");

        return e;
    }

    public KmButton addReviewButton(KmAction action)
    {
        KmButton e;
        e = addButton("Review", action);
        e.setFirstImage(RR.drawable.reviewIcon);
        return e;
    }

    public KmButton addImageButtonScaled(int resId, int percent, KmAction action)
    {
        KmButton e;
        e = addButton();
        e.setFirstImageScaled(resId, percent);
        e.setOnClickListener(action);
        return e;
    }

    public KmButton addImageButtonScaled(int resId, String text, int percent, KmAction action)
    {
        KmButton e;
        e = addButton();
        e.setFirstImageScaled(resId, percent);
        e.setText(text);
        e.setOnClickListener(action);
        return e;
    }

    //##################################################
    //# item padding
    //##################################################

    public void clearItemPadding()
    {
        _usesItemPadding = false;
    }

    public void setItemPadding(int px)
    {
        setItemPadding(px, px, px, px);
    }

    public void setItemPadding(int left, int top, int right, int bottom)
    {
        _itemPaddingLeft = left;
        _itemPaddingTop = top;
        _itemPaddingRight = right;
        _itemPaddingBottom = bottom;

        _usesItemPadding = true;
    }

    //##################################################
    //# support
    //##################################################

    protected ViewGroup getGroup()
    {
        return (ViewGroup)getView();
    }

    protected <E extends View> E add(E e)
    {
        if ( _usesItemPadding )
            e.setPadding(_itemPaddingLeft, _itemPaddingTop, _itemPaddingRight, _itemPaddingBottom);

        getGroup().addView(e);
        return e;
    }
}
