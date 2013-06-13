package com.kodemore.test;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class TyCustomPath
{
    //##################################################
    //# variables
    //##################################################

    private Path  _path;
    private Paint _paint;

    //##################################################
    //# constructor
    //##################################################

    public TyCustomPath()
    {
        _path = new Path();
        _paint = new Paint();
    }

    //##################################################
    //# accessing
    //##################################################

    public Path getPath()
    {
        return _path;
    }

    public void setPath(Path e)
    {
        _path = e;
    }

    public Paint getPaint()
    {
        return _paint;
    }

    public void setPaint(Paint e)
    {
        _paint = e;
    }

    //##################################################
    //# draw
    //##################################################

    public void drawOn(Canvas canvas)
    {
        canvas.drawPath(getPath(), getPaint());
    }

}
