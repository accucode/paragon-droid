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

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class KmLeftDrawable
    extends KmAbstractEdgeDrawable
{
    //##################################################
    //# constructors
    //##################################################

    public KmLeftDrawable(Drawable child, int size)
    {
        super(child, size);
    }

    //##################################################
    //# bounds
    //##################################################

    @Override
    public boolean getPadding(Rect r)
    {
        if ( isOverlay() )
            return false;

        r.setEmpty();
        r.left = getSize();
        return true;
    }

    @Override
    protected void onBoundsChange(Rect r)
    {
        int left = r.left;
        int right = Math.min(r.right, left + getSize());
        int top = r.top;
        int bottom = r.bottom;

        getChild().setBounds(left, top, right, bottom);
    }

    @Override
    public int getIntrinsicWidth()
    {
        return getSize();
    }

    @Override
    public int getIntrinsicHeight()
    {
        return getChild().getIntrinsicHeight();
    }
}
