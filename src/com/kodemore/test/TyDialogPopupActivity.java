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

package com.kodemore.test;

import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Test using an activity as a dialog.
 * 
 * We are trying to control the theme programmatically,
 * but it appears broken.  In particular, it does not 
 * seem possible to set the background dim via code.
 * 
 * We have tried using a custom theme, and also direct
 * manipulation of the windows's dim attributes.  The 
 * attributes are updated BEFORE creating the view but
 * it still doesn't work.
 * 
 * Research on the web indicates that this is broken
 * and that there is no solution without setting the 
 * activity's theme in the AndroidManifest.xml.
 */
public class TyDialogPopupActivity
    extends KmActivity
{
    //##################################################
    //# init
    //##################################################

    @Override
    protected void preCreate()
    {
        // neither of these work.
        // web research indicates that the sdk is simply broken.
        setTheme(android.R.style.Theme_Dialog);
        setDim(.5);
    }

    @Override
    protected void init()
    {
        // none
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addText("This is the dialog.");
        root.addButton("Close", newFinishAction());
        root.addButton("Dim 0.0", newDimAction(0.0));
        root.addButton("Dim 0.5", newDimAction(0.5));
        root.addButton("Dim 1.0", newDimAction(1.0));

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newDimAction(final double d)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                setDim(d);
            }
        };
    }

    //##################################################
    //# utility
    //##################################################

    /**
     * This does work, but not for dim amount.
     * You can demonstrate that it works by uncommenting 
     * the line that sets the alpha.  
     */
    private void setDim(final double d)
    {
        Window w = getWindow();

        LayoutParams attr;
        attr = w.getAttributes();
        attr.flags = attr.FLAG_DIM_BEHIND;
        attr.dimAmount = (float)d;
        // attr.alpha = (float)d;

        w.setAttributes(attr);
    }

}
