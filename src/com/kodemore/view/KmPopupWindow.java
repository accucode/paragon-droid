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
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.kodemore.drawable.KmDrawableBuilder;

public class KmPopupWindow
    extends PopupWindow
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager   _ui;
    private KmFrameLayout _contentView;

    //##################################################
    //# constructors
    //##################################################

    public KmPopupWindow(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;

        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable();

        _contentView = _ui.newFrameLayout();
        _contentView.setBackgroundDrawable(getDefaultBackgroundDrawable());
        _contentView.setPadding(10, 10, 10, 10);
    }

    //##################################################
    //# accessing
    //##################################################

    private KmUiManager ui()
    {
        return _ui;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newDismissAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleDismiss();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleDismiss()
    {
        dismiss();
    }

    //##################################################
    //# convenience
    //##################################################

    @Override
    public void setContentView(View contentView)
    {
        _contentView.setView(contentView);

        super.setContentView(_contentView);
    }

    public void setDismissOnTouch()
    {
        setTouchInterceptor(newDismissAction());
    }

    public void setOutsideTouchable()
    {
        setOutsideTouchable(true);
    }

    /**
     * Show the popup automatically positioned near the specified anchor.
     * If the popup is already showing, it is first dismissed.
     */
    public void showAt(View anchor)
    {
        if ( isShowing() )
            dismiss();

        Point loc = KmPopupWindowLocator.getLocationFor(ui(), anchor, _contentView);

        showAtLocation(anchor, Gravity.NO_GRAVITY, loc.x, loc.y);
    }

    /**
     * Return an action that shows this popup, anchored at the
     * provided view.  If popup is already showing, it is first
     * dismissed.
     */
    public KmAction newShowAtAction(final View anchor)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                showAt(anchor);
            }
        };
    }

    //##################################################
    //# utility
    //##################################################

    private Drawable getDefaultBackgroundDrawable()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPaint(Color.RED, 3).setCornerRadius(10);
        b.addPaint(Color.DKGRAY, 1).setCornerRadius(7);
        return b.toDrawable();
    }
}
