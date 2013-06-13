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

import android.app.Dialog;

public abstract class KmDialogManager
{
    //##################################################
    //# constants
    //##################################################

    public static final String MASTER_KEY = "dialogManager";

    //##################################################
    //# variables
    //##################################################

    private KmUiManager        _ui;
    private Dialog             _dialog;
    private boolean            _autoSave;

    //##################################################
    //# constructor 
    //##################################################

    public KmDialogManager()
    {
        setAutoSave(false);
    }

    public void register(KmUiManager ui)
    {
        _ui = ui;
        _ui.register(this);
        setAutoSave(false);
    }

    protected KmUiManager ui()
    {
        return _ui;
    }

    //##################################################
    //# accessing
    //##################################################

    /**
     * This defaults to my class name.  This works well
     * for anonymous inner classes, but may need to be
     * overridden if you reuse the same subclass for 
     * multiple contexts.
     */
    public String getKey()
    {
        return getClass().getName();
    }

    public boolean isAutoSave()
    {
        return _autoSave;
    }

    public void setAutoSave()
    {
        _autoSave = true;
    }

    public void setAutoSave(boolean value)
    {
        _autoSave = value;
    }

    //##################################################
    //# dialog
    //##################################################

    public abstract Dialog create();

    public void show()
    {
        show(true);
    }

    public void show(boolean flushCache)
    {
        if ( isShowing() )
            return;

        if ( flushCache )
            flushCache();

        lazyGetDialog().show();
    }

    public Dialog getDialog()
    {
        return _dialog;
    }

    public Dialog lazyGetDialog()
    {
        if ( _dialog == null )
            _dialog = create();

        return _dialog;
    }

    public boolean isShowing()
    {
        return _dialog != null && _dialog.isShowing();
    }

    public void dismissDialog()
    {
        _dialog.dismiss();
    }

    public void flushCache()
    {
        if ( _dialog == null )
            return;

        _dialog.dismiss();
        _dialog = null;
    }

    //##################################################
    //# action
    //##################################################

    public KmAction newShowDialogAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                show();
            }
        };
    }
}
