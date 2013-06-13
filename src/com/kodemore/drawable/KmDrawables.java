package com.kodemore.drawable;

import android.graphics.Color;
import android.graphics.drawable.Drawable;


public abstract class KmDrawables
{
    public static Drawable getSummaryBackground()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(2);
        b.addPaint(Color.DKGRAY, 1).setCornerRadius(10);
        b.addPaint(Color.BLACK, 1).setCornerRadius(9);
        b.addPad(9);
        return b.toDrawable();
    }

    public static Drawable getFilterBackground()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(2);
        b.addPaint(Color.DKGRAY, 1).setCornerRadius(10);
        b.addPaint(Color.BLACK, 1).setCornerRadius(9);
        b.addPad(9);
        return b.toDrawable();
    }

    public static Drawable getUpcBackground()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(2);
        b.addPaint(Color.LTGRAY, 1).setCornerRadius(10);
        b.addPaint(Color.DKGRAY, 1).setCornerRadius(9);
        b.addPad(9);
        return b.toDrawable();
    }

    public static Drawable getTextFieldBackground()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(2);
        b.addPaint(Color.LTGRAY, 1).setCornerRadius(10);
        b.addPaint(Color.DKGRAY, 1).setCornerRadius(9);
        b.addPad(9);
        return b.toDrawable();
    }

    public static Drawable getSpinnerBackground()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(2);
        b.addPaint(Color.LTGRAY, 1).setCornerRadius(10);
        b.addPaint(Color.DKGRAY, 1).setCornerRadius(9);
        b.addPad(9);
        return b.toDrawable();
    }
}
