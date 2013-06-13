package com.kodemore.view;

import android.graphics.drawable.Drawable;

import com.kodemore.drawable.KmDrawableBuilder;

public class KmGradientBanner
    extends KmBanner
{
    //##################################################
    //# constants
    //##################################################

    public static KmGradientBanner RED    = new KmGradientBanner("#E00000", "#7A0000");
    public static KmGradientBanner ORANGE = new KmGradientBanner("#fe935e", "#b33c01");
    public static KmGradientBanner YELLOW = new KmGradientBanner("#fefe78", "#F4F401");

    //##################################################
    //# variable
    //##################################################

    private String                 _topColor;
    private String                 _bottomColor;

    //##################################################
    //# constructor
    //##################################################

    public KmGradientBanner(String color1, String color2)
    {
        super();
        _topColor = color1;
        _bottomColor = color2;
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public Drawable getDrawable()
    {
        KmDrawableBuilder e;
        e = new KmDrawableBuilder();
        e.addGradientDown(_topColor, _bottomColor);
        return e.toDrawable();
    }
}
