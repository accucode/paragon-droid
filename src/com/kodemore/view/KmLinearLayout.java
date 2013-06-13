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
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;

import com.kodemore.drawable.KmDrawableBuilder;
import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.KmRunnable;
import com.kodemore.utility.Kmu;
import com.kodemore.view.value.KmBooleanValue;

public abstract class KmLinearLayout
    extends LinearLayout
{
    //##################################################
    //# constants
    //##################################################

    public static final int     TEXT_HEIGHT_SMALL = 2;

    protected static final int  PADDING           = 10;

    private final static String OK_BUTTON_TEXT    = "Ok";
    private final static int    OK_BUTTON_COLOR   = Color.parseColor("#0d824f");

    private static final String STATE_ORIGINAL    = "original";
    private static final String STATE_VISIBILITY  = "visibility";

    //##################################################
    //# variables
    //##################################################

    private KmUiManager         _ui;

    private int                 _itemWidth;
    private int                 _itemHeight;
    private double              _itemWeight;

    private boolean             _saveVisibility;

    //##################################################
    //# constructor
    //##################################################

    public KmLinearLayout(KmUiManager ui)
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

    public KmLinearHelper getHelper()
    {
        return new KmLinearHelper(this, ui());
    }

    //##################################################
    //# autosave
    //##################################################

    public void setAutoSave()
    {
        setId();
        setSaveEnabled(true);
    }

    public boolean getSaveVisibility()
    {
        return _saveVisibility;
    }

    public void setSaveVisibility(boolean e)
    {
        _saveVisibility = e;
    }

    public void setSaveVisibility()
    {
        setSaveVisibility(true);
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
    //# width
    //##################################################

    public int getItemWidth()
    {
        return _itemWidth;
    }

    public void setItemWidth(int e)
    {
        _itemWidth = e;
    }

    public void setItemWidthNone()
    {
        setItemWidth(0);
    }

    public void setItemWidthWrapContent()
    {
        setItemWidth(LayoutParams.WRAP_CONTENT);
    }

    public void setItemWidthMatchParent()
    {
        setItemWidth(LayoutParams.MATCH_PARENT);
    }

    //##################################################
    //# height
    //##################################################

    public int getItemHeight()
    {
        return _itemHeight;
    }

    public void setItemHeight(int e)
    {
        _itemHeight = e;
    }

    public void setItemHeightWrapContent()
    {
        setItemHeight(LayoutParams.WRAP_CONTENT);
    }

    public void setItemHeightMatchParent()
    {
        setItemHeight(LayoutParams.MATCH_PARENT);
    }

    public void setItemHeightNone()
    {
        setItemHeight(0);
    }

    /**
     * Sets the item height as a percentage of the overall screen height.
     */
    public void setItemHeightAsPercentageOfScreen(int percent)
    {
        KmDisplay display = ui().getDisplay();
        int screenHeight = display.getVerticalPixels();
        int itemHeight = (int)(1.0 * screenHeight / 100 * percent);

        setItemHeight(itemHeight);
    }

    public void setItemHeightAsPercentageOfScreenSquare(int percent)
    {
        KmDisplay display = ui().getDisplay();
        int screenHeight = display.getVerticalPixels();
        int itemHeight = (int)(1.0 * screenHeight / 100 * percent);

        setItemHeight(itemHeight);
        setItemWidth(itemHeight);
    }

    //##################################################
    //# weight
    //##################################################

    public double getItemWeight()
    {
        return _itemWeight;
    }

    public void setItemWeight(double e)
    {
        _itemWeight = e;
    }

    public void setItemWeightNone()
    {
        setItemWeight(0);
    }

    public void setItemWeightFull()
    {
        setItemWeight(1);
    }

    //##################################################
    //# padding
    //##################################################

    public void setPadding()
    {
        setPadding(PADDING);
    }

    public void setPadding(int px)
    {
        setPadding(px, px, px, px);
    }

    public void setPaddingNone()
    {
        setPadding(0, 0, 0, 0);
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public void addView(View e)
    {
        super.addView(e);

        applyDefaultItemLayoutParams(e);
    }

    public void setView(View e)
    {
        removeAllViews();

        if ( e != null )
            addView(e);
    }

    /**
     * Called immediately after addView, I provide 
     * an opportunity to set default layout param
     * on all of my children.
     */
    private void applyDefaultItemLayoutParams(View e)
    {
        LinearLayout.LayoutParams p;
        p = getLayoutParamsFor(e);
        p.width = _itemWidth;
        p.height = _itemHeight;
        p.weight = (float)_itemWeight;
    }

    public LinearLayout.LayoutParams getLayoutParamsFor(View e)
    {
        return (LinearLayout.LayoutParams)e.getLayoutParams();
    }

    public LinearLayout.LayoutParams getLayoutParamsForLast()
    {
        return getLayoutParamsFor(getLastChild());
    }

    public View getLastChild()
    {
        return getChildAt(getChildCount() - 1);
    }

    //##################################################
    //# layouts
    //##################################################

    public KmRowLayout addRow()
    {
        KmRowLayout e = ui().newRow();
        addView(e);
        return e;
    }

    public KmRowLayout addFullRow()
    {
        KmRowLayout row;
        row = addRow();
        row.setItemWeightFull();
        return row;
    }

    public KmRowLayout addEvenRow()
    {
        KmRowLayout row;
        row = addRow();
        row.setItemWidthNone();
        row.setItemWeightFull();
        return row;
    }

    public KmColumnLayout addColumn()
    {
        KmColumnLayout e = ui().newColumn();
        addView(e);
        return e;
    }

    public KmColumnLayout addFullColumn()
    {
        KmColumnLayout e;
        e = addColumn();
        e.setItemWeightFull();
        return e;
    }

    public KmTableLayout addTable()
    {
        KmTableLayout e = ui().newTableLayout();
        addView(e);
        return e;
    }

    //##################################################
    //# buttons
    //##################################################

    public KmButton addButton()
    {
        KmButton e = ui().newButton();
        addView(e);
        return e;
    }

    public KmButton addButton(CharSequence text)
    {
        KmButton e;
        e = addButton();
        e.setText(text);
        return e;
    }

    public KmButton addButton(CharSequence text, KmAction action)
    {
        KmButton e;
        e = addButton();
        e.setText(text);
        e.setOnClickListener(action);
        return e;
    }

    public KmButton addButton(CharSequence text, KmSimpleIntentCallback e)
    {
        return addButton(text, e.newAction());
    }

    public KmButton addButton(CharSequence text, KmDialogManager e)
    {
        return addButton(text, e.newShowDialogAction());
    }

    public KmButton addButton(CharSequence text, Class<? extends Activity> activity)
    {
        KmAction action = Kmu.newStartAction(getContext(), activity);
        return addButton(text, action);
    }

    public KmButton addButton(final Class<? extends Activity> activity)
    {
        String s = Kmu.formatActivity(activity);
        return addButton(s, activity);
    }

    public KmButton addBackButton()
    {
        return getHelper().addBackButton();
    }

    public KmButton addCancelButton(KmAction action)
    {
        return getHelper().addCancelButton(action);
    }

    public KmButton addExportButton(KmAction action)
    {
        return getHelper().addExportButton(action);
    }

    public KmButton addForwardButton(CharSequence text, KmAction action)
    {
        return getHelper().addForwardButton(text, action);
    }

    public KmButton addForwardButton(CharSequence text, Class<? extends Activity> activity)
    {
        KmAction action = Kmu.newStartAction(getContext(), activity);
        return getHelper().addForwardButton(text, action);
    }

    public KmButton addSaveButton(KmAction e)
    {
        return getHelper().addSaveButton(e);
    }

    public KmButton addSendButton(KmAction e)
    {
        return getHelper().addSendButton(e);
    }

    public KmButton addAddButton(KmAction e)
    {
        return getHelper().addAddButton(e);
    }

    public KmButton addAddButton(KmSimpleIntentCallback e)
    {
        return getHelper().addAddButton(e);
    }

    public KmButton addEditButton(KmAction e)
    {
        return getHelper().addEditButton(e);
    }

    public KmButton addEditButton(KmSimpleIntentCallback e)
    {
        return getHelper().addEditButton(e);
    }

    public KmButton addSearchButton(KmAction action)
    {
        return getHelper().addSearchButton(action);
    }

    public KmButton addSearchButton(KmSimpleIntentCallback e)
    {
        return getHelper().addSearchButton(e);
    }

    public KmButton addSearchButton(KmAction action, boolean showText)
    {
        return getHelper().addSearchButton(action, showText);
    }

    public KmButton addReviewButton(KmAction action)
    {
        return getHelper().addReviewButton(action);
    }

    public KmButton addOkButton(KmAction action)
    {
        KmButton e;
        e = addButton(OK_BUTTON_TEXT, action);
        e.setTextColor(OK_BUTTON_COLOR);
        return e;
    }

    public KmButton addToastButton(String text)
    {
        KmToastAction action = new KmToastAction(text);
        return addButton(text, action);
    }

    public KmButton addImageButton(int resId, CharSequence text, KmAction action)
    {
        KmButton e = ui().newImageButton(resId, text, action);
        addView(e);
        return e;
    }

    public KmButton addImageButton(int resId, KmAction action)
    {
        KmButton e = ui().newImageButton(resId, action);
        addView(e);
        return e;
    }

    public KmButton addImageButton(int resId)
    {
        KmButton e = ui().newImageButton(resId);
        addView(e);
        return e;
    }

    public KmButton addImageToastButton(int resId, String msg)
    {
        KmToastAction action = new KmToastAction(msg);
        return addImageButton(resId, action);
    }

    public KmButton addImageButton(int resId, CharSequence text, KmSimpleIntentCallback e)
    {
        return addImageButton(resId, text, e.newAction());
    }

    public KmButton addImageButtonScaled(int resId, KmAction action, int percent)
    {
        KmButton e;
        e = addButton();
        e.setOnClickListener(action);
        e.setImageSizeAsPercentOfScreen(resId, percent);
        return e;
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

    public void addSpaces(int n)
    {
        for ( int i = 0; i < n; i++ )
            addSpace();
    }

    public void addFiller()
    {
        addFiller(1.0);
    }

    public void addFiller(double weight)
    {
        double w = _itemWeight;

        setItemWeight(weight);
        addSpace();

        setItemWeight(w);
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

    public KmTextView addLabelGravityCenterVertical(CharSequence text)
    {
        KmTextView e = ui().newLabel(text);
        e.setGravityCenterVertical();
        addView(e);
        return e;
    }

    public KmTextView addBoldLabel(CharSequence text)
    {
        KmTextView e;
        e = ui().newBoldLabel(text);
        e.setTextColor(Color.WHITE);
        addView(e);
        return e;
    }

    public KmTextView addSmallLabel(CharSequence text)
    {
        KmTextView e;
        e = addLabel(text);
        e.setTextSizeAsPercentOfScreen(TEXT_HEIGHT_SMALL);
        return e;
    }

    public KmTextView addSmallBoldLabel(CharSequence text)
    {
        KmTextView e;
        e = addBoldLabel(text);
        e.setTextSizeAsPercentOfScreen(TEXT_HEIGHT_SMALL);
        return e;
    }

    public KmTextView addTitleLabel()
    {
        KmTextView e;
        e = addLabel();
        e.setTextColor(Color.WHITE);
        e.setTypeface(Typeface.DEFAULT_BOLD);
        e.setShadowLayer(4, 3, 3, Color.BLACK);
        e.setTextSizeAsPercentOfScreen(KmConstantsIF.TITLE_TEXT_HEIGHT);
        return e;
    }

    public KmTextView addTitleLabel(CharSequence text)
    {
        KmTextView e;
        e = addTitleLabel();
        e.setText(" " + text);
        return e;
    }

    //##################################################
    //# misc
    //##################################################

    public KmScrollView addScroll(View e)
    {
        KmScrollView scroll;
        scroll = ui().newScrollView();
        scroll.addView(e);

        addView(scroll);

        return scroll;
    }

    //##################################################
    //# convenience
    //##################################################

    public void addViewFullWeight(View view)
    {
        double w = _itemWeight;

        setItemWeightFull();
        addView(view);

        setItemWeight(w);
    }

    public void addViewNoWeight(View view)
    {
        double w = _itemWeight;

        setItemWeightNone();
        addView(view);

        setItemWeight(w);
    }

    public void addView(View view, double weight)
    {
        double w = _itemWeight;

        setItemWeight(weight);
        addView(view);

        setItemWeight(w);
    }

    //##################################################
    //# visibility
    //##################################################

    public void show()
    {
        getHelper().show();
    }

    public void showOrGone(boolean b)
    {
        if ( b )
            show();
        else
            gone();
    }

    public void showOrGone(KmBooleanValue e)
    {
        showOrGone(e.isTrue());
    }

    public void showOrHide(boolean b)
    {
        if ( b )
            show();
        else
            hide();
    }

    public void showOrHide(KmBooleanValue e)
    {
        showOrGone(e.isTrue());
    }

    public void hide()
    {
        getHelper().hide();
    }

    public void gone()
    {
        getHelper().gone();
    }

    public boolean isVisible()
    {
        return getHelper().isVisible();
    }

    public boolean isHidden()
    {
        return getHelper().isHidden();
    }

    public boolean isGone()
    {
        return getHelper().isGone();
    }

    public void toggleHidden()
    {
        getHelper().toggleHidden();
    }

    public void toggleGone()
    {
        getHelper().toggleGone();
    }

    //##################################################
    //# animate
    //##################################################

    public void showAnimated()
    {
        getHelper().showAnimated();
    }

    public void showAnimatedUp(KmAction onEndAction)
    {
        getHelper().animateShowActionHide(onEndAction);
    }

    public void hideAnimated()
    {
        getHelper().goneAnimated();
    }

    public void goneAnimated()
    {
        getHelper().goneAnimated();
    }

    public void toggleHiddenAnimated()
    {
        getHelper().toggleHiddenAnimated();
    }

    public void toggleGoneAnimated()
    {
        getHelper().toggleGoneAnimated();
    }

    //##################################################
    //# gravity
    //##################################################

    public void setGravityTop()
    {
        getHelper().setGravityTop();
    }

    public void setGravityBottom()
    {
        getHelper().setGravityBottom();
    }

    public void setGravityCenter()
    {
        getHelper().setGravityCenter();
    }

    public void setGravityCenterVertical()
    {
        getHelper().setGravityCenterVertical();
    }

    public void setGravityCenterHorizontal()
    {
        getHelper().setGravityCenterHorizontal();
    }

    //##################################################
    //# convenience
    //##################################################

    public void setBackgroundDrawable(KmDrawableBuilder b)
    {
        Drawable d = b == null
            ? null
            : b.toDrawable();

        setBackgroundDrawable(d);
    }

    public KmScrollView inScrollView()
    {
        KmScrollView e;
        e = ui().newScrollView();
        e.setView(this);
        return e;
    }

    //##################################################
    //# save state
    //##################################################

    @Override
    protected Parcelable onSaveInstanceState()
    {
        Parcelable original = super.onSaveInstanceState();

        Bundle e;
        e = new Bundle();
        e.putParcelable(STATE_ORIGINAL, original);
        e.putInt(STATE_VISIBILITY, getVisibility());
        return e;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if ( state instanceof Bundle )
        {
            Bundle bundle = (Bundle)state;
            super.onRestoreInstanceState(bundle.getParcelable(STATE_ORIGINAL));
            setVisibility(bundle.getInt(STATE_VISIBILITY));
            return;
        }

        super.onRestoreInstanceState(state);
    }

    //##################################################
    //# async
    //##################################################

    public void removeAllViewsAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                removeAllViews();
            }
        }.runOnUiThread(ui());
    }

    //==================================================
    //= async :: convenience
    //==================================================

    public void addViewFullWeightAsyncSafe(final View view)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                addViewFullWeight(view);
            }
        }.runOnUiThread(ui());
    }

    //==================================================
    //= async :: visibility
    //==================================================

    public void showAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                show();
            }
        }.runOnUiThread(ui());
    }

    public void showOrGoneAsyncSafe(final KmBooleanValue e)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                showOrGone(e);
            }
        }.runOnUiThread(ui());
    }

    public void showOrGoneAsyncSafe(final boolean b)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                showOrGone(b);
            }
        }.runOnUiThread(ui());
    }

    public void hideAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                hide();
            }
        }.runOnUiThread(ui());
    }

    public void goneAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                gone();
            }
        }.runOnUiThread(ui());
    }

    //==================================================
    //= async :: animate
    //==================================================

    public void showAnimatedAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                showAnimated();
            }
        }.runOnUiThread(ui());
    }

    public void showAnimatedUpAsyncSafe(final KmAction onEndAction)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                showAnimatedUp(onEndAction);
            }
        }.runOnUiThread(ui());
    }
}
