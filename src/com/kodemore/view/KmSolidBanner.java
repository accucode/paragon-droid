/**
 * 
 */
package com.kodemore.view;

import android.graphics.drawable.Drawable;

import com.kodemore.drawable.KmDrawableBuilder;

public class KmSolidBanner
    extends KmBanner
{
    //##################################################
    //# constants
    //##################################################

    public static KmSolidBanner GREY = new KmSolidBanner("#5C5C5C");

    //##################################################
    //# variable
    //##################################################

    private String              _color;

    //##################################################
    //# constructor
    //##################################################

    public KmSolidBanner(String color)
    {
        super();
        _color = color;
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public Drawable getDrawable()
    {
        KmDrawableBuilder e;
        e = new KmDrawableBuilder();
        e.addPaint(_color);
        return e.toDrawable();
    }
}
