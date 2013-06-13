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

package com.kodemore.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.ShapeDrawable.ShaderFactory;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.utility.KmBridge;

/**
 * I am used to create a drawable.  I support various
 * convenience methods that make it easier to compose
 * complex.  Multiple layers can be created and are 
 * composed starting from the outside and working your
 * way in.
 */
public class KmDrawableBuilder
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The list of drawables.
     */
    private KmList<Drawable> _list;

    //##################################################
    //# constructor
    //##################################################

    public KmDrawableBuilder()
    {
        _list = new KmList<Drawable>();
    }

    //##################################################
    //# basics
    //##################################################

    public <E extends Drawable> E add(E e)
    {
        _list.add(e);
        return e;
    }

    public PaintDrawable addPad(int px)
    {
        return addPad(px, px, px, px);
    }

    public PaintDrawable addPad(int left, int top, int right, int bottom)
    {
        PaintDrawable e;
        e = new PaintDrawable(Color.TRANSPARENT);
        e.setPadding(left, top, right, bottom);
        return add(e);
    }

    //##################################################
    //# paint
    //##################################################

    public PaintDrawable addPaint(String color)
    {
        return addPaint(toInt(color));
    }

    public PaintDrawable addPaint(String color, int pad)
    {
        return addPaint(Color.parseColor(color), pad);
    }

    public PaintDrawable addPaint(int color)
    {
        PaintDrawable e;
        e = new PaintDrawable(color);
        return add(e);
    }

    public PaintDrawable addPaint(int color, int pad)
    {
        PaintDrawable e;
        e = addPaint(color);
        e.setPadding(pad, pad, pad, pad);
        return e;
    }

    //##################################################
    //# border
    //##################################################

    public KmBorderDrawable addBorder(int color)
    {
        KmBorderDrawable e;
        e = new KmBorderDrawable(color);
        return add(e);
    }

    public KmBorderDrawable addBorder(int color, int width)
    {
        KmBorderDrawable e;
        e = new KmBorderDrawable(color, width);
        return add(e);
    }

    //##################################################
    //# two tone
    //##################################################

    public KmVerticalTwoToneDrawable addVerticalTwoTone(int topColor, int bottomColor)
    {
        KmVerticalTwoToneDrawable e;
        e = new KmVerticalTwoToneDrawable();
        e.setTopColor(topColor);
        e.setBottomColor(bottomColor);
        return add(e);
    }

    public KmVerticalTwoToneDrawable addVerticalTwoTone(int topColor, int bottomColor, int pad)
    {
        KmVerticalTwoToneDrawable e;
        e = addVerticalTwoTone(topColor, bottomColor);
        e.setPadding(pad);
        return e;
    }

    public KmVerticalTwoToneDrawable addVerticalTwoTone(String topColor, String bottomColor)
    {
        int t = Color.parseColor(topColor);
        int b = Color.parseColor(bottomColor);
        return addVerticalTwoTone(t, b);
    }

    public KmVerticalTwoToneDrawable addVerticalTwoTone(String topColor, String bottomColor, int pad)
    {
        int t = Color.parseColor(topColor);
        int b = Color.parseColor(bottomColor);
        return addVerticalTwoTone(t, b, pad);
    }

    //##################################################
    //# gradient
    //##################################################

    public GradientDrawable addGradientDown(String... colors)
    {
        return addGradientDown(toInts(colors));
    }

    public GradientDrawable addGradientDown(int... colors)
    {
        GradientDrawable e;
        e = new GradientDrawable(Orientation.TOP_BOTTOM, colors);
        return add(e);
    }

    public GradientDrawable addGradientUp(int... colors)
    {
        GradientDrawable e;
        e = new GradientDrawable(Orientation.BOTTOM_TOP, colors);
        return add(e);
    }

    public GradientDrawable addGradientLeft(int... colors)
    {
        GradientDrawable e;
        e = new GradientDrawable(Orientation.RIGHT_LEFT, colors);
        return add(e);
    }

    public GradientDrawable addGradientRight(int... colors)
    {
        GradientDrawable e;
        e = new GradientDrawable(Orientation.LEFT_RIGHT, colors);
        return add(e);
    }

    //##################################################
    //# gradient (rectangle)
    //##################################################

    public PaintDrawable addGradientRectangle(View view, int... colors)
    {
        int n = colors.length;
        if ( n < 2 )
            return null;

        float step = 1 / ((float)n - 1);
        final float[] positions = new float[n];

        for ( int i = 0; i < n; i++ )
            positions[i] = i * step;

        PaintDrawable e;
        e = new PaintDrawable();
        e.setShape(new RectShape());
        e.setShaderFactory(newRectangleShaderFactory(view, positions, colors));
        return add(e);
    }

    private ShaderFactory newRectangleShaderFactory(
        final View view,
        final float[] positions,
        final int... colors)
    {
        return new ShapeDrawable.ShaderFactory()
        {
            @Override
            public Shader resize(int width, int height)
            {
                LinearGradient dst = new LinearGradient(
                    0,
                    0,
                    0,
                    view.getHeight(),
                    colors,
                    positions,
                    Shader.TileMode.REPEAT);

                LinearGradient src = new LinearGradient(
                    0,
                    0,
                    view.getWidth(),
                    0,
                    colors,
                    positions,
                    Shader.TileMode.REPEAT);

                return new ComposeShader(dst, src, PorterDuff.Mode.MULTIPLY);
            }
        };
    }

    //##################################################
    //# image
    //##################################################

    public BitmapDrawable addImage(int resId)
    {
        Resources r = getApplicatinonContext().getResources();
        Bitmap bmp = BitmapFactory.decodeResource(r, resId);
        BitmapDrawable e = new BitmapDrawable(bmp);
        return add(e);
    }

    public BitmapDrawable addTiledImage(int resId)
    {
        BitmapDrawable e;
        e = addImage(resId);
        e.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        return e;
    }

    public BitmapDrawable addMirroredImage(int resId)
    {
        BitmapDrawable d;
        d = addImage(resId);
        d.setTileModeXY(Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        return d;
    }

    //##################################################
    //# results
    //##################################################

    /**
     * Return the resulting drawable.
     */
    public Drawable toDrawable()
    {
        if ( _list.isEmpty() )
            return null;

        if ( _list.isSingleton() )
            return _list.getFirst();

        Drawable[] arr;
        arr = new Drawable[_list.size()];
        arr = _list.toArray(arr);
        return new LayerDrawable(arr);
    }

    //##################################################
    //# conversion
    //##################################################

    private int toInt(String s)
    {
        return Color.parseColor(s);
    }

    private int[] toInts(String... strings)
    {
        int n = strings.length;
        int[] ints = new int[n];

        for ( int i = 0; i < n; i++ )
            ints[i] = toInt(strings[i]);

        return ints;
    }

    //##################################################
    //# support
    //##################################################

    private Context getApplicatinonContext()
    {
        return KmBridge.getInstance().getApplicationContext();
    }

}
