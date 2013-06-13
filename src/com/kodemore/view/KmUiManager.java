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
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;

import com.kodemore.collection.KmList;
import com.kodemore.intent.KmIntentCallback;
import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.Kmu;
import com.kodemore.view.value.KmBooleanValue;
import com.kodemore.view.value.KmDoubleValue;
import com.kodemore.view.value.KmIntegerValue;
import com.kodemore.view.value.KmStringValue;
import com.kodemore.view.value.KmValue;

public class KmUiManager
{
    //##################################################
    //# variables
    //##################################################

    private Activity                    _activity;
    private int                         _nextId;

    private KmList<KmIntentCallback<?>> _intentCallbacks;
    private KmList<KmDialogManager>     _dialogManagers;
    private KmList<KmValue<?>>          _values;

    //##################################################
    //# constructor
    //##################################################

    public KmUiManager(Activity e)
    {
        _activity = e;
        _nextId = 1;

        _intentCallbacks = new KmList<KmIntentCallback<?>>();
        _dialogManagers = new KmList<KmDialogManager>();
        _values = new KmList<KmValue<?>>();
    }

    //##################################################
    //# accessing
    //##################################################

    public Activity getActivity()
    {
        return _activity;
    }

    public Context getContext()
    {
        return getActivity();
    }

    public Resources getResources()
    {
        return getContext().getResources();
    }

    public int nextId()
    {
        return _nextId++;
    }

    public void setNextId(int i)
    {
        _nextId = i;
    }

    public KmUiTheme getTheme()
    {
        return KmUiTheme.getInstance();
    }

    //##################################################
    //# display
    //##################################################

    public KmDisplay getDisplay()
    {
        return new KmDisplay(getActivity());
    }

    public int getDisplayHeight()
    {
        return getDisplay().getVerticalPixels();
    }

    public int getDisplayWidth()
    {
        return getDisplay().getHorizontalPixels();
    }

    //##################################################
    //# layouts
    //##################################################

    public KmRowLayout newRow()
    {
        return new KmRowLayout(this);
    }

    public KmRowLayout newEvenRow()
    {
        KmRowLayout row;
        row = newRow();
        row.setItemWidthNone();
        row.setItemWeightFull();
        return row;
    }

    public KmColumnLayout newColumn()
    {
        return new KmColumnLayout(this);
    }

    public KmColumnLayout newEvenColumn()
    {
        KmColumnLayout col;
        col = newColumn();
        col.setItemHeightNone();
        col.setItemWeightFull();
        return col;
    }

    public KmTableLayout newTableLayout()
    {
        return new KmTableLayout(this);
    }

    public KmGridView newGridView()
    {
        return new KmGridView(this);
    }

    public KmBorderLayout newBorderLayout()
    {
        return new KmBorderLayout(this);
    }

    public KmFrameLayout newFrameLayout()
    {
        return new KmFrameLayout(this);
    }

    public KmWallpaperFrame newBackgroundImageLayout()
    {
        return new KmWallpaperFrame(this);
    }

    //##################################################
    //# buttons
    //##################################################

    public KmButton newButton()
    {
        return new KmButton(this);
    }

    public KmButton newButton(CharSequence text)
    {
        KmButton e;
        e = newButton();
        e.setText(text);
        return e;
    }

    public KmButton newButton(CharSequence text, OnClickListener listener)
    {
        KmButton e;
        e = newButton();
        e.setOnClickListener(listener);
        e.setText(text);
        return e;
    }

    public KmButton newButton(CharSequence text, Class<? extends Activity> activity)
    {
        KmAction action = Kmu.newStartAction(getContext(), activity);
        return newButton(text, action);
    }

    public KmButton newButton(CharSequence text, KmSimpleIntentCallback callback)
    {
        return newButton(text, callback.newAction());
    }

    public KmButton newNumberPadButton(CharSequence text)
    {
        KmButton e;
        e = newButton(text);
        e.setTextSizeAsPercentOfScreen(KmConstantsIF.BUTTON_PAD_TEXT_SIZE);
        return e;
    }

    public KmButton newNumberPadButton(CharSequence text, KmAction action)
    {
        KmButton e;
        e = newNumberPadButton(text);
        e.setOnClickListener(action);
        return e;
    }

    public KmButton newImageButton(int resId, CharSequence text, KmAction action)
    {
        KmButton e;
        e = new KmButton(this);
        e.setFirstImage(resId);
        e.setText(text);
        e.setOnClickListener(action);
        return e;
    }

    public KmButton newImageButton(CharSequence text, KmAction action)
    {
        KmButton e;
        e = new KmButton(this);
        e.setText(text);
        e.setOnClickListener(action);
        return e;
    }

    public KmButton newImageButton(int resId, KmAction action)
    {
        KmButton e;
        e = new KmButton(this);
        e.setFirstImage(resId);
        e.setOnClickListener(action);
        return e;
    }

    public KmButton newImageButton(int resId)
    {
        KmButton e;
        e = new KmButton(this);
        e.setFirstImage(resId);
        return e;
    }

    public KmButton newImageButtonScaled(int resId, int percent, KmAction action)
    {
        KmButton e;
        e = newImageButtonScaled(resId, percent);
        e.setOnClickListener(action);
        return e;
    }

    public KmButton newImageButtonScaled(int resId, String text, int percent, KmAction action)
    {
        KmButton e;
        e = newImageButtonScaled(resId, percent);
        e.setText(text);
        e.setOnClickListener(action);
        return e;
    }

    public KmButton newImageButtonScaled(int resId, int percent)
    {
        KmButton e;
        e = newButton();
        e.setFirstImageScaled(resId, percent);
        return e;
    }

    //##################################################
    //# text
    //##################################################

    public KmTextView newTextView()
    {
        return new KmTextView(this);
    }

    public KmTextView newTextView(CharSequence text)
    {
        KmTextView e;
        e = newTextView();
        e.setText(text);
        return e;
    }

    public KmTextView newLabel()
    {
        return newLabel("");
    }

    public KmTextView newLabel(CharSequence text)
    {
        if ( text == null )
            text = "";

        KmTextView e;
        e = new KmTextView(this);
        e.setText(text);
        e.setTextSize(KmConstantsIF.LABEL_SIZE);
        e.setTextColor(Color.LTGRAY);
        return e;
    }

    public KmTextView newBoldLabel()
    {
        return newBoldLabel("");
    }

    public KmTextView newBoldLabel(CharSequence text)
    {
        if ( text == null )
            text = "";

        KmTextView e;
        e = new KmTextView(this);
        e.setText(text);
        e.setTextSize(KmConstantsIF.TEXT_SIZE);
        e.getHelper().setBold();
        e.setTextColor(Color.WHITE);
        return e;
    }

    //##################################################
    //# fields
    //##################################################

    public KmTextField newTextField()
    {
        return new KmTextField(this);
    }

    public KmTextField newTextField(CharSequence text)
    {
        KmTextField e;
        e = newTextField();
        e.setText(text);
        return e;
    }

    public KmIntegerField newIntegerField()
    {
        return new KmIntegerField(this);
    }

    public KmDoubleField newDoubleField()
    {
        return new KmDoubleField(this);
    }

    public KmQuantityField newQuantityField()
    {
        return new KmQuantityField(this);
    }

    public KmMoneyField newMoneyField()
    {
        return new KmMoneyField(this);
    }

    public KmUpcField newUpcField()
    {
        return new KmUpcField(this);
    }

    public <K> KmSpinner<K> newSpinner()
    {
        return new KmSpinner<K>(this);
    }

    public KmCheckBox newCheckBox()
    {
        return new KmCheckBox(this);
    }

    public KmTernaryCheckBoxView newTernaryCheckBox()
    {
        return new KmTernaryCheckBoxView(this);
    }

    public KmDatePicker newDatePicker()
    {
        return new KmDatePicker(this);
    }

    public KmTimePicker newTimePicker()
    {
        return new KmTimePicker(this);
    }

    public <K> KmListView<K> newListView()
    {
        return new KmListView<K>(this);
    }

    public KmOneLineView newOneLineView(Object e)
    {
        return new KmOneLineView(this, e);
    }

    public KmOneLineView newOneLineView()
    {
        return new KmOneLineView(this);
    }

    public KmOneLineView newOneLineView(String msg, Object... args)
    {
        String s = Kmu.format(msg, args);
        return new KmOneLineView(this, s);
    }

    public KmTwoLineView newTwoLineView(Object one, Object two)
    {
        return new KmTwoLineView(this, one, two);
    }

    public KmScrollView newScrollView()
    {
        return new KmScrollView(this);
    }

    //##################################################
    //# image
    //##################################################

    public KmImageView newImageView()
    {
        return new KmImageView(this);
    }

    //##################################################
    //# intent callbacks
    //##################################################

    public void register(KmIntentCallback<?> e)
    {
        _intentCallbacks.add(e);
    }

    public boolean handleResultCallback(int requestCode, int resultCode, Intent data)
    {
        KmIntentCallback<?> e = findIntentCallback(requestCode);
        if ( e == null )
            return false;

        e.handle(resultCode, data);
        return true;
    }

    private KmIntentCallback<?> findIntentCallback(int requestCode)
    {
        for ( KmIntentCallback<?> e : _intentCallbacks )
            if ( e.hasRequestCode(requestCode) )
                return e;

        return null;
    }

    //##################################################
    //# dialog managers
    //##################################################

    public void register(KmDialogManager e)
    {
        _dialogManagers.add(e);
    }

    /**
     * Dismiss all open dialogs.  This is typically used
     * when an activity is paused to prevent memory leaks.
     */
    public void dismissDialogs()
    {
        for ( KmDialogManager e : _dialogManagers )
            if ( e.isShowing() )
                e.dismissDialog();
    }

    private KmDialogManager getDialogManager(String key)
    {
        for ( KmDialogManager e : _dialogManagers )
            if ( e.getKey().equals(key) )
                return e;

        return null;
    }

    private void showDialogManager(String key)
    {
        KmDialogManager mgr = getDialogManager(key);

        if ( mgr == null )
            Kmu.fatal("Unknown dialog manager key (%s)", key);

        mgr.show();
    }

    private void saveDialogManagerState(Bundle state)
    {
        for ( KmDialogManager e : _dialogManagers )
            if ( e.isShowing() && e.isAutoSave() )
            {
                state.putString(KmDialogManager.MASTER_KEY, e.getKey());
                break;
            }
    }

    private void restoreDialogManagerState(Bundle state)
    {
        if ( !state.containsKey(KmDialogManager.MASTER_KEY) )
            return;

        String key = state.getString(KmDialogManager.MASTER_KEY);
        showDialogManager(key);
    }

    //##################################################
    //# values
    //##################################################

    public void register(KmValue<?> e)
    {
        _values.add(e);
    }

    private void saveValueStates(Bundle state)
    {
        for ( KmValue<?> e : _values )
            e.saveState(state);
    }

    private void restoreValueStates(Bundle state)
    {
        for ( KmValue<?> e : _values )
            e.restoreState(state);
    }

    public KmStringValue newStringValue()
    {
        return new KmStringValue(this);
    }

    public KmBooleanValue newBooleanValue()
    {
        return new KmBooleanValue(this);
    }

    public KmBooleanValue newBooleanValue(boolean def)
    {
        KmBooleanValue e;
        e = newBooleanValue();
        e.setValue(def);
        return e;
    }

    public KmIntegerValue newIntegerValue()
    {
        return new KmIntegerValue(this);
    }

    public KmDoubleValue newDoubleValue()
    {
        return new KmDoubleValue(this);
    }

    //##################################################
    //# state
    //##################################################

    public void onSaveInstanceState(Bundle state)
    {
        /**
         * The order of operations is important.
         * The sequence matches onRestoreInstanceState below.
         */
        saveValueStates(state);
        saveDialogManagerState(state);
    }

    public void onRestoreInstanceState(Bundle state)
    {
        if ( state == null )
            return;

        /**
         * The order of operations is important.
         * The sequence matches onSaveInstanceState above.
         */
        restoreValueStates(state);
        restoreDialogManagerState(state);
    }

    //##################################################
    //# measure
    //##################################################

    public int measureTextWidth(CharSequence s)
    {
        return measureForUnspecified(newTextView(s)).x;
    }

    public int measureWidth(View e)
    {
        return measureForUnspecified(e).x;
    }

    public int measureHeight(View e)
    {
        return measureForUnspecified(e).y;
    }

    public Point measureForUnspecified(View e)
    {
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        e.measure(widthMeasureSpec, heightMeasureSpec);
        return getMeasuredSize(e);
    }

    public Point measureForContent(View e)
    {
        e.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        return getMeasuredSize(e);
    }

    private Point getMeasuredSize(View e)
    {
        int x = e.getMeasuredWidth();
        int y = e.getMeasuredHeight();

        return new Point(x, y);
    }

    //##################################################
    //# utility
    //##################################################

    public Rect getLocationOnScreen(View e)
    {
        int[] xy = new int[2];
        e.getLocationOnScreen(xy);

        int width = e.getWidth();
        int height = e.getHeight();

        int left = xy[0];
        int top = xy[1];
        int right = left + width;
        int bottom = top + height;

        return new Rect(left, top, right, bottom);
    }

    public Bitmap toBitmap(int resId, int percent)
    {
        KmDisplay display = getDisplay();

        int screenHeight = display.getVerticalPixels();
        int imageHeight = (int)(1.0 * screenHeight / 100 * percent);

        Bitmap bmp;
        bmp = BitmapFactory.decodeResource(getResources(), resId);
        bmp = Bitmap.createScaledBitmap(bmp, imageHeight, imageHeight, true);
        return bmp;
    }

    public float getTextSizeAsScreenPercent(int percent)
    {
        KmDisplay display = getDisplay();

        int screenHeight = display.getVerticalPixels();
        double density = display.getNominalDensity();

        int textHeight = (int)(1.0 * screenHeight / 100 * percent);
        int textSize = (int)Math.round(textHeight / density);

        return textSize;
    }
}
