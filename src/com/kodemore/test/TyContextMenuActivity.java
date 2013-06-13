package com.kodemore.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.widget.TextView;

/**
 *  This test was written to explore an error that occurs
 *  when a context menu is rotated.  The activity generates
 *  an error message.  In order to determine if the error
 *  was in our code we stripped it down to the Android
 *  codebase.  The error appears to be in the C++ code that
 *  is the core of Android.  We might be able to get around this
 *  if we write our own handlers.  However at this
 *  time the error is not trapped or detected by the email
 *  generation software so we are not addressing this issue 
 *  until it become necessary
 */

public class TyContextMenuActivity
    extends Activity
{
    //##################################################
    //# variables
    //##################################################

    private TextView _textView;

    //##################################################
    //# onCreate
    //##################################################

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _textView = new TextView(this)
        {

            @Override
            protected void onCreateContextMenu(ContextMenu menu)
            {
                super.onCreateContextMenu(menu);
                menu.setHeaderTitle("Rotate Screen");
                menu.addSubMenu("90 degrees");
            }

        };
        _textView.setText("hold me");
        _textView.setGravity(Gravity.CENTER);
        _textView.setTextSize(50);
        registerForContextMenu(_textView);
        setContentView(_textView);
    }

}
