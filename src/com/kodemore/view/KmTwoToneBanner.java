/**
 * 
 */
package com.kodemore.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.kodemore.drawable.KmDrawableBuilder;

public class KmTwoToneBanner
    extends KmBanner
{
    //##################################################
    //# constants
    //##################################################

    public static final KmTwoToneBanner RED    = new KmTwoToneBanner("#4e0904", "#4a0b07");
    public static final KmTwoToneBanner GREEN  = new KmTwoToneBanner("#085332", "#0e4e32");
    public static final KmTwoToneBanner BLUE   = new KmTwoToneBanner("#324355", "#2d3d4d");
    public static final KmTwoToneBanner TEAL   = new KmTwoToneBanner("#03333A", "#092E34");
    public static final KmTwoToneBanner PURPLE = new KmTwoToneBanner("#52495A", "#48404F");
    public static final KmTwoToneBanner GOLD   = new KmTwoToneBanner("#876708", "#745807");

    //##################################################
    //# variable
    //##################################################

    private String                      _topColor;
    private String                      _bottomColor;

    //##################################################
    //# constructor
    //##################################################

    public KmTwoToneBanner(String topColor, String bottomColor)
    {
        super();

        _topColor = topColor;
        _bottomColor = bottomColor;
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public Drawable getDrawable()
    {
        int top = Color.parseColor(_topColor);
        int bottom = Color.parseColor(_bottomColor);

        KmDrawableBuilder e;
        e = new KmDrawableBuilder();
        e.addPaint(bottom, 1);
        e.addVerticalTwoTone(top, bottom);
        return e.toDrawable();
    }
}
