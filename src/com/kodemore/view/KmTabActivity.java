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

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost.OnTabChangeListener;

import com.kodemore.utility.KmAppInfo;
import com.kodemore.utility.KmFlashLightTool;
import com.kodemore.utility.KmLog;
import com.kodemore.utility.Kmu;
import com.kodemore.view.value.KmValue;

/**
 * It appears we will have an issue with hosted activities, OR
 * hosted views as follows : 
 * 
 * W/PhoneWindow(16856): Previously focused view reported id 2002 during save, but can't be found during restore.
 *  
 * Please see TyTabTestActivity for more information. 
 */
public abstract class KmTabActivity
    extends TabActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager _ui;
    private boolean     _isRestart;

    private KmTitleView _titleView;

    //##################################################
    //# constructor
    //##################################################

    public KmTabActivity()
    {
        _ui = new KmUiManager(this);
    }

    //##################################################
    //# override 
    //##################################################

    @Override
    public final void onCreate(Bundle savedInstanceState)
    {
        try
        {
            onCreateTry(savedInstanceState);
        }
        catch ( RuntimeException ex )
        {
            Kmu.handleUnhandledException(ex);
        }
    }

    protected void onCreateTry(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if ( savedInstanceState != null )
            _isRestart = true;

        View root = createRoot();
        if ( root == null )
        {
            Class<? extends KmTabActivity> c = getClass();
            KmLog.warn(c, "Root is Null");
            root = newText(Kmu.formatCamelCaseWords(c));
        }

        setContentView(root);

        /**
         * the tab change listener MUST be set after the 
         * content view is set or else it will not work.
         */
        getTabHost().setOnTabChangedListener(new OnTabChangeListener()
        {

            @Override
            public void onTabChanged(String tabId)
            {
                handleOnTabChanged();
            }
        });

    }

    protected View createRoot()
    {
        KmTabHost host = new KmTabHost(this);
        host.setTitleView(createTitleView());
        installTabsOn(host);
        return host;
    }

    protected abstract void installTabsOn(KmTabHost host);

    /**
     * here is the hook to capture the tab changed event.
     */
    protected void handleOnTabChanged()
    {
        // nothing
    }

    //##################################################
    //# title bar
    //##################################################

    protected KmTitleView createTitleView()
    {
        _titleView = new KmTitleView(ui());
        _titleView.setHelpButtonClickListener(newHelpPressedAction());
        _titleView.setLightButtonClickListener(newLightPressedAction());
        _titleView.setHomeButtonClickListener(newHomePressedAction());
        _titleView.setLogoButtonClickListener(newLogoPressedAction());
        _titleView.addHome(showsTitleHomeButton());
        _titleView.addHelp(showsTitleHelpButton());
        _titleView.addLogo(showsAccucodeIcon());
        _titleView.addLight(showsLightButton());
        _titleView.setText(getDefaultTitle());
        _titleView.setBanner(getDefaultBanner());

        return _titleView;
    }

    protected KmAction newHelpPressedAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleHelpPressed();
            }
        };
    }

    protected KmAction newLightPressedAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleLightPressed();
            }
        };
    }

    protected KmAction newHomePressedAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleHomePressed();
            }
        };
    }

    protected KmAction newLogoPressedAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleAccucodeIconPressed();
            }
        };
    }

    protected void handleLightPressed()
    {
        KmFlashLightTool.instance.toggleLight();
        refreshLightButtonImage();
    }

    protected void handleHomePressed()
    {
        // subclass
    }

    protected void handleHelpPressed()
    {
        // subclass
    }

    protected void handleAccucodeIconPressed()
    {
        // subclass
    }

    protected boolean showsTitleBar()
    {
        return true;
    }

    protected boolean showsTitleHomeButton()
    {
        return false;
    }

    protected boolean showsTitleHelpButton()
    {
        return false;
    }

    protected boolean showsAccucodeIcon()
    {
        return false;
    }

    protected boolean showsLightButton()
    {
        return false;
    }

    protected KmBanner getDefaultBanner()
    {
        return KmTitleView.DEFAULT_BANNER;
    }

    protected String getDefaultTitle()
    {
        return Kmu.formatActivity(this);
    }

    //##################################################
    //# lifecycle
    //##################################################

    @Override
    protected void onPause()
    {
        super.onPause();

        softKeyOnPause().applyTo(this);

        _isRestart = true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        softKeyOnResume().applyTo(this);

        refreshLightButtonImage();
    }

    //##################################################
    //# convenience
    //##################################################

    /**
     * An active is, itself, a context.
     * However, using an explicit method has several advantages.
     * - Clarifies the intent.
     * - Anonomous inner classes don't need to quality "this".
     * - Consistency; this allows code to be more easily moved or refactored.
     */
    protected Context getContext()
    {
        return this;
    }

    protected KmTabActivity getActivity()
    {
        return this;
    }

    protected KmAppInfo getAppInfo()
    {
        return new KmAppInfo(getContext());
    }

    public KmDisplay getDisplay()
    {
        return new KmDisplay(this);
    }

    public int getScreenWidth()
    {
        return getDisplay().getHorizontalPixels();
    }

    public int getScreenHeight()
    {
        return getDisplay().getVerticalPixels();
    }

    public boolean isPortrait()
    {
        return getDisplay().isPortrait();
    }

    public boolean isLandscape()
    {
        return getDisplay().isLandscape();
    }

    protected boolean hasLastNonConfigurationInstance()
    {
        return getLastNonConfigurationInstance() != null;
    }

    protected boolean hasWindow()
    {
        return getWindow() != null;
    }

    protected boolean hasCurrentFocus()
    {
        return getCurrentFocus() != null;
    }

    //==================================================
    //= convenience :: title bar 
    //==================================================

    protected KmTitleView getTitleView()
    {
        return _titleView;
    }

    //##################################################
    //# utility
    //##################################################

    private void refreshLightButtonImage()
    {
        if ( !showsTitleBar() || !showsLightButton() )
            return;

        if ( KmFlashLightTool.instance.isLightOn() )
            getTitleView().setLightButtonIcon(RR.drawable.flashlightOnIcon);
        else
            getTitleView().setLightButtonIcon(RR.drawable.flashlightOffIcon);
    }

    //##################################################
    //# first start
    //##################################################

    protected boolean isRestart()
    {
        return _isRestart;
    }

    protected boolean isFirstStart()
    {
        return !isRestart();
    }

    //##################################################
    //# alerts
    //##################################################

    protected void alert(CharSequence msg)
    {
        Kmu.alert(getContext(), msg);
    }

    protected void alert(Exception ex)
    {
        Kmu.alert(getContext(), "Exception", Kmu.formatTrace(ex));
    }

    protected void alert(String title, CharSequence msg)
    {
        Kmu.alert(getContext(), title, msg);
    }

    //##################################################
    //# toasts
    //##################################################

    protected void toast(CharSequence msg)
    {
        Kmu.toast(msg);
    }

    protected void toast(String msg, Object... args)
    {
        Kmu.toast(msg, args);
    }

    protected KmAction newToastAction(String msg)
    {
        return new KmToastAction(msg);
    }

    //##################################################
    //# soft-key control
    //##################################################

    /**
     *  It is recommended not to override this in tabbed
     *  activities unless you know what you are doing.
     */

    protected KmSoftKeyFlags softKeyOnPause()
    {
        return KmSoftKeyFlags.HIDE;
    }

    /**
     *  It is recommended not to override this in tabbed
     *  activities unless you know what you are doing.
     */

    protected KmSoftKeyFlags softKeyOnResume()
    {
        return KmSoftKeyFlags.HIDE;
    }

    protected void hideSoftKey()
    {
        KmSoftKeyFlags flag = KmSoftKeyFlags.HIDE;
        flag.applyTo(this);
    }

    protected void showSoftKey()
    {
        KmSoftKeyFlags flag = KmSoftKeyFlags.SHOW;
        flag.applyTo(this);
    }

    /**
     * We do not preserve the soft key state in
     * tab activities because they host two or more
     * activities. We need to handle the soft key
     * separately for each.
     */
    protected void preserveSoftKey()
    {
        //do nothing
    }

    //##################################################
    //# controls
    //##################################################

    protected KmButton newButton(CharSequence text, OnClickListener listener)
    {
        return ui().newButton(text, listener);
    }

    //##################################################
    //# text
    //##################################################

    protected KmTextView newText()
    {
        return ui().newTextView();
    }

    protected KmTextView newText(CharSequence text)
    {
        return ui().newTextView(text);
    }

    //##################################################
    //# fields
    //##################################################

    protected KmTextField newEditText()
    {
        return ui().newTextField();
    }

    protected KmTextField newEditText(String text)
    {
        KmTextField e;
        e = newEditText();
        e.setText(text);
        return e;
    }

    //##################################################
    //# start
    //##################################################

    /**
     * Start another activity.
     * It seems strange that such a simple task required four
     * lines of code.  However, perhaps subsequent experience
     * will discover that this simplistic usage is actually 
     * unusual.
     */
    protected void startActivity(Class<? extends Activity> e)
    {
        Kmu.startActivity(getContext(), e);
    }

    //##################################################
    //# finish
    //##################################################

    protected void finishOk(Intent e)
    {
        setResult(RESULT_OK, e);
        finish();
    }

    //##################################################
    //# result
    //##################################################

    /**
     * Usually cancels are ignored, and unknown results should
     * be logged.  So take care of this by default.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if ( resultCode == RESULT_OK )
        {
            handleActivityResultOk(data);
            return;
        }

        if ( resultCode == RESULT_CANCELED )
        {
            handleActivityResultCancelled();
            return;
        }

        handleActivityResultUnknown(resultCode);
    }

    protected void handleActivityResultOk(Intent data)
    {
        KmLog.warn(getClass(), "Unhandled Ok Result(%s).");
    }

    protected void handleActivityResultUnknown(int resultCode)
    {
        KmLog.warn(getClass(), "Unknown resultCode (%s).", resultCode);
    }

    protected void handleActivityResultCancelled()
    {
        // normally ignored.
    }

    //##################################################
    //# actions
    //##################################################

    protected KmAction newFinishAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                finish();
            }
        };
    }

    protected Runnable newFinishRunnable()
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                finish();
            }
        };
    }

    protected KmAction newBackPressedAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                onBackPressed();
            }
        };
    }

    //##################################################
    //# ui
    //##################################################

    public KmUiManager ui()
    {
        return _ui;
    }

    protected void register(KmDialogManager e)
    {
        ui().register(e);
    }

    public void register(KmValue<?> e)
    {
        ui().register(e);
    }

    protected void selectTab(String tag)
    {
        getTabHost().setCurrentTabByTag(tag);
    }

    //##################################################
    //# preferences
    //##################################################

    protected SharedPreferences getPrivatePreferences()
    {
        return getPreferences(MODE_PRIVATE);
    }
}
