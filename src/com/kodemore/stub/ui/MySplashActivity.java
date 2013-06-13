package com.kodemore.stub.ui;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

import com.kodemore.test.TyRegistry;
import com.kodemore.utility.Kmu;

public class MySplashActivity
    extends MyActivity
{
    //##################################################
    //# constants
    //##################################################//

    private static final int DELAY_MS = 1000;

    //##################################################
    //# init
    //##################################################//

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
        return ui().newTextView(Kmu.repeat("Splash ", 1000));
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        Class<? extends Activity> c = TyRegistry.getQuickTest();
        if ( c != null )
        {
            startActivity(c);
            finish();
            return;
        }

        if ( !getPreferences().getShowsSplash() )
        {
            handlePostSplash();
            return;
        }

        new Handler().postDelayed(newPostSplashRunnable(), DELAY_MS);
    }

    private Runnable newPostSplashRunnable()
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                handlePostSplash();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################//

    private void handlePostSplash()
    {
        startActivity(MyMainActivity.class);
        finish();
    }
}
