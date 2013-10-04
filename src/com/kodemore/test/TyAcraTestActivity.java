package com.kodemore.test;

import android.view.View;

import com.kodemore.acra.KmAcra;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

public class TyAcraTestActivity
    extends KmActivity
{
    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        //nothing
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("Manual (call acra directly)", newManualAction());
        root.addButton("Automatic (handled in KmAction)", newAutomaticAction());
        root.addButton("Automagic (jvm hook, terminates app)", newAutomagicAction());
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newManualAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleManual();
            }
        };
    }

    private KmAction newAutomaticAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleAutomatic();
            }
        };
    }

    private KmAction newAutomagicAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleAutomagic();
            }

            @Override
            protected boolean catchesExceptions()
            {
                return false;
            }
        };
    }

    //##################################################
    //# handle 
    //##################################################

    /**
     * This demonstrates how to manually send exceptions to ACRA.
     * However, as we'll see below, this is usually not needed.
     */
    private void handleManual()
    {
        try
        {
            throwRuntime();
        }
        catch ( Exception ex )
        {
            KmAcra.handleSilentException(ex);
        }
    }

    /**
     * This demonstrates the try-catch block that is built into the KmAction
     * class.  See KmAction.fire() and trace through it.  You should find that
     * KmAction delegates to the KmBridge, which eventually delegates to ACRA.
     */
    private void handleAutomatic()
    {
        throwRuntime();
    }

    /**
     * This demonstrates ACRAs hook into the JVM runtime.  Note that we override
     * catchesExceptions() in the action above to disable exception handling.
     * This allows us to test what happens even when we do not catch the exception.
     * This shows how Acra allows us to capture truly unhandled exceptions, usually
     * even if they terminate the app.
     */
    private void handleAutomagic()
    {
        throwRuntime();
    }

    //##################################################
    //# utility
    //##################################################

    private void throwRuntime()
    {
        throw new RuntimeException("Acra Test");
    }
}
