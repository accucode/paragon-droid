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

import com.kodemore.utility.KmProgressDialogTask;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * I provide an example of managing long running tasks
 * in a robust manner with a progress dialog.
 */
public class TyProgressDialogActivity
    extends KmActivity
{
    //##################################################
    //# init
    //##################################################

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
        root.addButton("Run Background Task", newRunAction());
        return root;
    }

    //##################################################
    //# lifecycle
    //##################################################

    /**
     * Used instead of onResumeAsync() due to no User Interface interaction.
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        getTask().onResume(getContext());
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        getTask().onPause();
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newRunAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleRun();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleRun()
    {
        getTask().run();
    }

    //##################################################
    //# task
    //##################################################

    private KmProgressDialogTask getTask()
    {
        return TyProgressDialogTask.instance;
    }
}
