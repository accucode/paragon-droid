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

import android.graphics.drawable.Drawable;

public class KmTitleView
    extends KmRowLayout
{
    //##################################################
    //# constants
    //##################################################

    public static final KmBanner DEFAULT_BANNER    = KmTwoToneBanner.PURPLE;

    /**
     * These values are a percentage of the screen height
     */
    public static final int      TITLE_BAR_HEIGHT  = 8;
    public static final int      TITLE_TEXT_HEIGHT = 5;

    //##################################################
    //# variables
    //##################################################

    private KmTextView           _textView;
    private KmButton             _homeButton;
    private KmButton             _helpButton;
    private KmButton             _lightButton;
    private KmImageView          _logoView;
    private KmBanner             _banner;

    //##################################################
    //# constructor
    //##################################################

    public KmTitleView(KmUiManager ui)
    {
        super(ui);

        init();
    }

    private void init()
    {
        getDefaultBanner();
        setBackground();
        addHomeButton();
        addTitleText();
        addLightButton();
        addHelpButton();
        addLogo();
    }

    private void setBackground()
    {
        Drawable d = _banner.getDrawable();
        setBackgroundDrawable(d);
    }

    private void addTitleText()
    {
        setItemWeightFull();
        _textView = addTitleLabel();
        _textView.setPadding(10, 0, 10, 0);
    }

    private void addHomeButton()
    {
        _homeButton = ui().newImageButton(RR.drawable.homeIcon);
        setItemWeightNone();
        setItemHeightAsPercentageOfScreenSquare(TITLE_BAR_HEIGHT - 1);
        setGravityCenterVertical();
        addView(_homeButton);
    }

    private void addLightButton()
    {
        setItemWeightNone();
        _lightButton = addImageButton(RR.drawable.flashlightOnIcon);
    }

    private void addHelpButton()
    {
        setItemWeightNone();
        _helpButton = addImageButton(RR.drawable.helpIcon);
    }

    private void addLogo()
    {
        _logoView = ui().newImageView();
        _logoView.setBackgroundResource(RR.drawable.companyLogoIcon);

        setItemWeightNone();
        setItemHeightAsPercentageOfScreenSquare(TITLE_BAR_HEIGHT);
        addView(_logoView);
    }

    //##################################################
    //# accessing
    //##################################################

    public void setHomeButtonClickListener(KmAction a)
    {
        _homeButton.setOnClickListener(a);
    }

    public void setHelpButtonClickListener(KmAction a)
    {
        _helpButton.setOnClickListener(a);
    }

    public void setLightButtonClickListener(KmAction a)
    {
        _lightButton.setOnClickListener(a);
    }

    public void setLogoButtonClickListener(KmAction a)
    {
        _logoView.setOnClickListener(a);
    }

    public void setBanner(KmBanner e)
    {
        if ( e == null )
            setBackgroundDrawable((Drawable)null);
        else
            setBackgroundDrawable(e.getDrawable());
    }

    public void setText(String s)
    {
        _textView.setText(s);
    }

    //##################################################
    //# utility
    //##################################################

    public void addLogo(boolean showLogo)
    {
        if ( showLogo )
        {
            _logoView.show();
            return;
        }
        _logoView.gone();
    }

    public void addHome(boolean showHome)
    {
        if ( showHome )
        {
            _homeButton.show();
            return;
        }
        _homeButton.gone();
    }

    public void addHelp(boolean showHelp)
    {
        if ( showHelp )
        {
            _helpButton.show();
            return;
        }
        _helpButton.gone();
    }

    public void addLight(boolean showLight)
    {
        if ( showLight )
        {
            _lightButton.show();
            return;
        }
        _lightButton.gone();
    }

    private void getDefaultBanner()
    {
        _banner = DEFAULT_BANNER;
    }

    //##################################################
    //# convenience
    //##################################################

    public void setLightButtonIcon(int resId)
    {
        _lightButton.setFirstImage(resId);
    }
}
