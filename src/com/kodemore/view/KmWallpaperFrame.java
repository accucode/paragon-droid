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

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class KmWallpaperFrame
    extends RelativeLayout
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager   _ui;

    private KmImageView   _backgroundImageView;
    private KmFrameLayout _contentFrame;

    //##################################################
    //# constructor
    //##################################################

    public KmWallpaperFrame(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;

        _backgroundImageView = ui().newImageView();
        _backgroundImageView.setScaleType(ScaleType.CENTER_CROP);

        _contentFrame = ui().newFrameLayout();

        addView(_backgroundImageView);
        addView(_contentFrame);
    }

    //##################################################
    //# core
    //##################################################

    protected KmUiManager ui()
    {
        return _ui;
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
    //# image view
    //##################################################

    /**
     * Sets the backround image.
     * @param e = Drawable to be set as background.
     */
    public void setBackgroundImage(Drawable e)
    {
        _backgroundImageView.setImageDrawable(e);
    }

    /**
     * Sets the backround image.
     */
    public void setBackgroundImage(int resId)
    {
        _backgroundImageView.setImage(resId);
    }

    /**
     * Sets the backround image.
     * @param bm = Bitmap to be set as background.
     */
    public void setBackgroundImage(Bitmap bm)
    {
        _backgroundImageView.setImageBitmap(bm);
    }

    //##################################################
    //# content view
    //##################################################

    /**
     * Sets the view to be displayed over the backround image.
     * @param e = View to be displayed
     */
    public void setContentView(View e)
    {
        _contentFrame.removeAllViews();
        _contentFrame.addView(e);
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

    /**
     * Called immediately after addView, I provide 
     * an opportunity to set default layout param
     * on all of my children.
     */
    private void applyDefaultItemLayoutParams(View e)
    {
        LayoutParams p;
        p = getLayoutParamsFor(e);
        p.width = LayoutParams.MATCH_PARENT;
        p.height = LayoutParams.MATCH_PARENT;
    }

    public LayoutParams getLayoutParamsFor(View e)
    {
        return (LayoutParams)e.getLayoutParams();
    }
}
