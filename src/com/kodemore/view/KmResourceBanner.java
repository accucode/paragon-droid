/**
 * 
 */
package com.kodemore.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class KmResourceBanner
    extends KmBanner
{
    //##################################################
    //# constants
    //##################################################

    private static final int NO_RESOURCE = -1;

    //##################################################
    //# variable
    //##################################################

    private int              _resId;
    private Context          _context;

    //##################################################
    //# constructor
    //##################################################

    public KmResourceBanner(Context context)
    {
        _resId = NO_RESOURCE;
        _context = context;
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public Drawable getDrawable()
    {
        if ( getResId() == NO_RESOURCE )
            return null;

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), _resId);

        BitmapDrawable e;
        e = new BitmapDrawable(bmp);
        e.setTileModeXY(Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        return e;
    }

    //##################################################
    //# accessing
    //##################################################

    public int getResId()
    {
        return _resId;
    }

    public void setResId(int e)
    {
        _resId = e;
    }

    protected Resources getResources()
    {
        return getContext().getResources();
    }

    //##################################################
    //# context
    //##################################################

    protected Context getContext()
    {
        return _context;
    }
}
