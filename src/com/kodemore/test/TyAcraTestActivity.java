package com.kodemore.test;

import org.acra.ACRA;

import android.view.View;

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
        root.addText("This will throw an exception and launch ACRA : crash handling programmatically. See MyApplication for more details.");
        root.addButton("Throw Handled Exception", newExceptionAction());
        root.addText("This will throw an unhandled exception and ACRA will pick it up if enabled. See MyApplication for more details.");
        root.addButton("Throw Unhandled Exception", newUnhandledExceptionAction());

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newExceptionAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleException();
            }
        };
    }

    private KmAction newUnhandledExceptionAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleUnhandledException();
            }
        };
    }

    //##################################################
    //# handle 
    //##################################################

    private void handleException()
    {
        try
        {
            throw new RuntimeException("Dying on purpose : ACRA Test.");
        }
        catch ( RuntimeException e )
        {
            try
            {
                ACRA.getErrorReporter().handleSilentException(e);
            }
            catch ( IllegalStateException ex )
            {
                alert("ACRA isn't installed, cannot send silent exception to ACRA");
            }
        }
    }

    private void handleUnhandledException()
    {
        throw new RuntimeException("Dying on purpose : ACRA Test.");
    }
}
