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

import com.kodemore.utility.KmFlashLightTool;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Test the light via the camera.
 */
public class TyLightActivity
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

        root.addButton("Flash Supported", newSupportedAction());
        root.addSpace();
        root.addButton("Flash On", newTorchAction());
        root.addButton("Flash Off", newOffAction());
        root.addButton("Flash Toggle", newToggleAction());
        root.addButton("RAVE!!!", newStrobeAction());

        return root;
    }

    //##################################################
    //# lifecycle
    //##################################################

    @Override
    public void onPause()
    {
        super.onPause();

        getLight().stop();
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newTorchAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleTorch();
            }
        };
    }

    private KmAction newOffAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleOff();
            }
        };
    }

    private KmAction newToggleAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleToggle();
            }
        };
    }

    private KmAction newSupportedAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSupported();
            }
        };
    }

    private KmAction newStrobeAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleStrobe();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleTorch()
    {
        if ( !hasLight() )
        {
            toast("Not Supported");
            return;
        }

        getLight().turnOnLight();
    }

    private void handleOff()
    {
        if ( !hasLight() )
        {
            toast("Not Supported");
            return;
        }

        getLight().turnOffLight();
    }

    private void handleToggle()
    {
        if ( !hasLight() )
        {
            toast("Not Supported");
            return;
        }

        getLight().toggleLight();
    }

    private void handleSupported()
    {
        if ( hasLight() )
            toast("Flash supported");
        else
            toast("Flash NOT supported");
    }

    private void handleStrobe()
    {
        if ( !hasLight() )
        {
            toast("Not Supported");
            return;
        }

        int delay = 10;
        int n = 50;
        for ( int i = 0; i < n; i++ )
        {
            getLight().toggleLight();

            Kmu.sleepMs(delay);
        }

        getLight().turnOffLight();
    }

    //==================================================
    //= utility
    //==================================================

    private KmFlashLightTool getLight()
    {
        return KmFlashLightTool.instance;
    }

    private boolean hasLight()
    {
        return getLight().hasFlashLight();
    }
}
