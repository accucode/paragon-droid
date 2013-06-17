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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.kodemore.alert.KmDialogBuilder;
import com.kodemore.network.KmConnectionListener;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.KmAppInfo;
import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.KmFlashLightTool;
import com.kodemore.utility.KmLog;
import com.kodemore.utility.KmRunnable;
import com.kodemore.utility.KmSimpleAsyncTask;
import com.kodemore.utility.KmTimer;
import com.kodemore.utility.Kmu;
import com.kodemore.view.value.KmBooleanValue;
import com.kodemore.view.value.KmValue;

public abstract class KmActivity
    extends Activity
{
    //##################################################
    //# constants
    //##################################################

    protected static final KmBanner DEFAULT_BANNER    = KmTwoToneBanner.PURPLE;

    public static final String      REQUEST_ID_OFFSET = "idOffset";

    //##################################################
    //# variables
    //##################################################

    private KmUiManager             _ui;
    private KmConnectionListener    _connectionListener;

    /**
     * It seems reasonable that an activity should have one popup window. That
     * is, one popup window OPEN at a time. The popup window's content,
     * position, style, etc can be changed. The popup window is NOT reopened
     * when the activity is restarted.
     */
    private KmPopupWindow           _popup;

    /**
     * Indicates if this activity has been restarted; e.g., after reorienting
     * the device.
     */
    private KmBooleanValue          _isRestart;

    /**
     * Used to determined if the activity has been paused.
     * Defaults to false, toggled by onPause and onResume. 
     */
    private boolean                 _isPaused;

    private KmTitleView             _titleView;

    //##################################################
    //# constructor
    //##################################################

    public KmActivity()
    {
        _ui = new KmUiManager(this);

        _isRestart = new KmBooleanValue(ui());
        _isRestart.setFalse();
        _isRestart.setAutoSave();
    }

    //##################################################
    //# override 
    //##################################################

    @Override
    public final void onCreate(Bundle state)
    {
        try
        {
            KmTimer t = KmTimer.run(getClass(), "onCreate");
            onCreateTry(state);
            t.check();
        }
        catch ( RuntimeException ex )
        {
            Kmu.handleUnhandledException(ex);
        }
    }

    protected void onCreateTry(Bundle state)
    {
        installDefaultId();
        installDefaultTitle();
        installDefaultOrientation();

        preCreate();

        super.onCreate(state);

        init();

        installTooling();
        ui().onRestoreInstanceState(state);

        setContentView(createContentView());
        checkDefaultFocus();
        createConnectionListener();
    }

    private void installDefaultId()
    {
        int def = 1;
        int id = getIntent().getIntExtra(REQUEST_ID_OFFSET, def);
        ui().setNextId(id);
    }

    private boolean installDefaultOrientation()
    {
        int req = getRequestedOrientation();
        int def = getDefaultOrientation().getActivityInfoValue();

        if ( req == def )
            return false;

        setRequestedOrientation(getDefaultOrientation());
        return true;
    }

    /**
     * This method is intended as a hook for framework tooling that is careful
     * to always call super.
     */
    protected void installTooling()
    {
        // subclass
    }

    protected void preCreate()
    {
        // subclass
    }

    private View createRoot()
    {
        View root = createLayout();

        if ( root == null )
            return createEmptyRoot();

        if ( root.getParent() != null )
            throw new RuntimeException("Root view should not have a parent.");

        return root;
    }

    private View createEmptyRoot()
    {
        Class<? extends KmActivity> c = getClass();
        KmLog.warn(c, "Root is Null");

        String name = Kmu.formatCamelCaseWords(c);
        return ui().newTextView(name);
    }

    /**
     * Perform basic initialization of the activity. Subclasses are required to
     * override this, and should typically perform a consistent set of
     * initialization. This is where fields, dialog managers, and other
     * variables are initialized in a consistent sequence. Implementations of
     * init should generally NOT contain branching logic.
     */
    protected abstract void init();

    /**
     * Subclass hook to create the main view. Loosely speaking, this view will
     * fill the window; however, we may add some decorations around this layout.
     * E.g.: we typically add a custom banner at the top of each page.
     */
    protected abstract View createLayout();

    //##################################################
    //# lifecycle
    //##################################################

    @Override
    protected void onResume()
    {
        super.onResume();

        _isPaused = false;

        registerConnectionListener();
        getOnResumeSoftKey().applyTo(this);
        newOnResumeTask().execute();
    }

    private KmSimpleAsyncTask newOnResumeTask()
    {
        return new KmSimpleAsyncTask()
        {
            @Override
            public void doInBackground()
            {
                onResumeAsync();
            }
        };
    }

    /**
     * Called at the end of onResume, to provide a convenience
     * hook for subclasses that need to run background processes.
     */
    protected void onResumeAsync()
    {
        refreshLightButtonImageAsyncSafe();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        unregisterConnectionListener();
        dismissDialogs();
        getOnPauseSoftKey().applyTo(this);

        _isPaused = true;
    }

    @Override
    public void onBackPressed()
    {
        if ( isPopupShowing() )
            getPopup().dismiss();
        else
            super.onBackPressed();
    }

    protected boolean isPaused()
    {
        return _isPaused;
    }

    //##################################################
    //# popup window
    //##################################################

    public KmPopupWindow getPopup()
    {
        if ( _popup == null )
            _popup = new KmPopupWindow(ui());

        return _popup;
    }

    public boolean isPopupShowing()
    {
        if ( _popup == null )
            return false;

        return _popup.isShowing();
    }

    //##################################################
    //# first start
    //##################################################

    protected boolean isRestart()
    {
        return _isRestart.isTrue();
    }

    protected boolean isFirstStart()
    {
        return !isRestart();
    }

    //##################################################
    //# menu
    //##################################################

    @Override
    public final boolean onCreateOptionsMenu(Menu m)
    {
        return onCreateOptionsMenu(new KmMenuHelper(m));
    }

    public boolean onCreateOptionsMenu(KmMenuHelper h)
    {
        return false;
    }

    //##################################################
    //# title
    //##################################################

    private void installDefaultTitle()
    {
        String s = getDefaultTitle();
        if ( s != null )
            setTitle(s);
    }

    protected String getDefaultTitle()
    {
        return Kmu.formatActivity(this);
    }

    //##################################################
    //# orientation
    //##################################################

    protected KmScreenOrientation getDefaultOrientation()
    {
        return KmScreenOrientation.UNSPECIFIED;
    }

    public void setRequestedOrientation(KmScreenOrientation e)
    {
        if ( e == null )
            e = KmScreenOrientation.UNSPECIFIED;

        setRequestedOrientation(e.getActivityInfoValue());
    }

    //##################################################
    //# content view
    //##################################################
    //==================================================
    //= content view :: override
    //==================================================

    /**
     * When true, the activity will show the title bar.
     */
    protected boolean showsTitleBar()
    {
        return true;
    }

    protected boolean showsTitleHomeButton()
    {
        return false;
    }

    /**
     * When true, the activity will show the help button in the title bar.
     */
    protected boolean showsTitleHelpButton()
    {
        return false;
    }

    /**
     * When true, the activity will show the accucode button in the title bar.
     */
    protected boolean showsAccucodeIcon()
    {
        return false;
    }

    /**
     * When true, the activity will show the flash light button in the title bar.  This is 
     * also dependent on whether or not the flashlight is supported by the device.
     */
    protected boolean showsLightButton()
    {
        return false;
    }

    //==================================================
    //= content view :: private
    //==================================================

    private View createContentView()
    {
        View root = createRoot();

        if ( !showsTitleBar() )
            return root;

        View title;
        title = createTitleView();

        KmColumnLayout wrapper;
        wrapper = ui().newColumn();
        wrapper.setItemHeightAsPercentageOfScreen(KmConstantsIF.TITLE_BAR_HEIGHT);
        wrapper.addView(title);
        wrapper.setItemHeightNone();
        wrapper.addViewFullWeight(root);
        return wrapper;
    }

    protected View createTitleView()
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

    protected KmBanner getDefaultBanner()
    {
        return KmTitleView.DEFAULT_BANNER;
    }

    //##################################################
    //# default focus
    //##################################################

    private void checkDefaultFocus()
    {
        if ( isRestart() )
            return;

        if ( hasDefaultField() )
            getDefaultField().requestFocus();
    }

    /**
     * This provides a convenient way to specify which field has default focus.
     * There are a couple of reasons why this is useful.
     * 
     * First, you cannot request focus until after the field has been attached
     * to the activity via setContentView. This means that you cannot request
     * default focus during the createFields or createLayout methods.
     * 
     * Also, we typically DON'T want to reapply default focus when the
     * application is restarting, e.g.: due to an orientation change. Rather we
     * want to preserve focus on the current field when we rotate the screen.
     * This method is only used when entering activity is first started.
     */
    protected View getDefaultField()
    {
        return null;
    }

    private boolean hasDefaultField()
    {
        return getDefaultField() != null;
    }

    //##################################################
    //# convenience
    //##################################################

    /**
     * An active is, itself, a context. However, using an explicit method has
     * several advantages. - Clarifies the intent. - Anonomous inner classes
     * don't need to quality "this". - Consistency; this allows code to be more
     * easily moved or refactored.
     */
    protected Context getContext()
    {
        return this;
    }

    protected KmActivity getActivity()
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
    //= convenience :: device dialer
    //==================================================

    protected boolean canDialPhone()
    {
        TelephonyManager tm = (TelephonyManager)getContext().getSystemService(
            Context.TELEPHONY_SERVICE);

        if ( tm.getPhoneType() == tm.PHONE_TYPE_NONE )
            return false;

        return true;
    }

    protected void dialPhone(String phone)
    {
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:" + phone));
        startActivity(i);
    }

    protected void callPhone(String phone)
    {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:" + phone));
        startActivity(i);
    }

    //==================================================
    //= convenience :: network status
    //==================================================

    public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(
            Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
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

    private void refreshLightButtonImageAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                refreshLightButtonImage();
            }
        }.runOnUiThread(ui());
    }

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

    protected void alertAsyncSafe(final CharSequence msg)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                alert(msg);
            }
        }.runOnUiThread(ui());
    }

    protected void alertAsyncSafe(final String title, final CharSequence msg)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                alert(title, msg);
            }
        }.runOnUiThread(ui());
    }

    protected void alertAsyncSafe(final Exception ex)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                alert(ex);
            }
        }.runOnUiThread(ui());
    }

    //##################################################
    //# toasts
    //##################################################

    protected void toast(CharSequence msg)
    {
        Kmu.toast(msg);
    }

    protected void toastAsyncSafe(final CharSequence msg)
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                toast(msg);
            }
        }.runOnUiThread(ui());
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

    protected KmSoftKeyFlags getOnPauseSoftKey()
    {
        return KmSoftKeyFlags.HIDE;
    }

    protected KmSoftKeyFlags getOnResumeSoftKey()
    {
        return KmSoftKeyFlags.HIDE;
    }

    protected void hideSoftKey()
    {
        KmSoftKeyFlags.HIDE.applyTo(this);
    }

    protected void showSoftKey()
    {
        KmSoftKeyFlags.SHOW.applyTo(this);
    }

    protected void preserveSoftKey()
    {
        KmSoftKeyFlags.NORMAL.applyTo(this);
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

    public void dismissDialogs()
    {
        ui().dismissDialogs();
    }

    //##################################################
    //# activity
    //##################################################

    protected void startActivity(Class<? extends Activity> e)
    {
        boolean clearTop = true;
        startActivity(e, clearTop);
    }

    protected void startActivity(Class<? extends Activity> e, boolean clearTop)
    {
        if ( clearTop )
            Kmu.startActivityClearTop(getContext(), e);
        else
            Kmu.startActivity(getContext(), e);
    }

    @Override
    public void startActivity(Intent i)
    {
        boolean clearTop = true;
        startActivity(i, clearTop);
    }

    public void startActivity(Intent i, boolean clearTop)
    {
        if ( clearTop )
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        super.startActivity(i);
    }

    protected void finishOk(Intent e)
    {
        setResult(RESULT_OK, e);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        ui().handleResultCallback(requestCode, resultCode, data);
    }

    //##################################################
    //# service
    //##################################################

    protected void startService(Class<? extends Service> c)
    {
        Intent i = new Intent(getContext(), c);
        startService(i);
    }

    protected void stopService(Class<? extends Service> c)
    {
        Intent i = new Intent(getContext(), c);
        stopService(i);
    }

    //##################################################
    //# connection
    //##################################################

    private void createConnectionListener()
    {
        _connectionListener = newConnectionListener();
    }

    private KmConnectionListener newConnectionListener()
    {
        return new KmConnectionListener(getContext())
        {
            @Override
            protected void handle()
            {
                handleConnectionChange();
            }
        };
    }

    private void registerConnectionListener()
    {
        try
        {
            registerReceiver(_connectionListener, _connectionListener.getIntentFilter());
        }
        catch ( Exception ex )
        {
            KmLog.error(ex);
        }
    }

    private void unregisterConnectionListener()
    {
        try
        {
            unregisterReceiver(_connectionListener);
        }
        catch ( IllegalArgumentException ex )
        {
            // ignored, may throw an exception if not already/currently registered.
        }
        catch ( Exception ex )
        {
            KmLog.error(ex);
        }
    }

    protected boolean isConnected()
    {
        return _connectionListener.isConnected();
    }

    protected void handleConnectionChange()
    {
        // subclass
    }

    //##################################################
    //# dialogs
    //##################################################

    protected Builder newDialogBuilder()
    {
        return new AlertDialog.Builder(getContext());
    }

    //##################################################
    //# save / restore
    //################################################## 

    @Override
    protected void onSaveInstanceState(Bundle state)
    {
        super.onSaveInstanceState(state);

        _isRestart.setTrue();
        ui().onSaveInstanceState(state);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state)
    {
        super.onRestoreInstanceState(state);
        ui().onRestoreInstanceState(state);
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

    protected KmAction newBackAction()
    {
        return KmActions.newBackAction(this);
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

    //##################################################
    //# app store utilities
    //##################################################

    /**
     * Launches the google store with search string "search" to find
     * applications. Used to prompt user to see if they want to search for a
     * needed application such as an Email client. "prompt" should be the
     * details, e.g. "Do you want to find an email app?".
     */
    protected void launchAppStore(String prompt, String search)
    {
        newAppStoreDialogManager(prompt, search).show();
    }

    private KmDialogManager newAppStoreDialogManager(final String prompt, final String search)
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return createAppStoreDialog(prompt, search);
            }
        };
    }

    private Dialog createAppStoreDialog(String prompt, String search)
    {
        KmStringBuilder msg;
        msg = new KmStringBuilder();
        msg.println(prompt);

        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setMessage(msg);
        e.setTitle("Search for app?");
        e.setPositiveButton("Yes", newAppStoreDialogYesAction(search));
        e.setNegativeButton("No");
        return e.create();
    }

    private KmAction newAppStoreDialogYesAction(final String search)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                try
                {
                    String s = Kmu.format("market://search?q=%s&c=apps", search);
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s)));
                }
                catch ( ActivityNotFoundException ex )
                {
                    launchBrowser(search);
                }
            }
        };
    }

    private void launchBrowser(String search)
    {
        try
        {
            startActivity(new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/search?q=" + search + "&c=apps")));
        }
        catch ( ActivityNotFoundException ex )
        {
            alert("You do not have a web browser installed! This is the minimum requirement to install new apps.");
        }
    }

    /**
     * Launches the google store linking to package "pkg", e.g.
     * com.mycompany.app_name Used to prompt user to see if they want to install
     * a specific app. "prompt" should be the details, e.g. "Do you want to
     * install <app_name>?".
     */

    protected void launchAppStorePackage(String prompt, String pkg)
    {
        KmDialogManager mgr;
        mgr = newAppStorePackageDialogManager(prompt, pkg);
        mgr.show();
    }

    private KmDialogManager newAppStorePackageDialogManager(final String prompt, final String pkg)
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return createAppStorePackageDialog(prompt, pkg);
            }
        };
    }

    private Dialog createAppStorePackageDialog(String prompt, String pkg)
    {
        KmStringBuilder msg;
        msg = new KmStringBuilder();
        msg.println(prompt);

        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setMessage(msg);
        e.setTitle("Link to App?");
        e.setPositiveButton("Yes", newAppStorePkgDialogYesAction(pkg));
        e.setNegativeButton("No", newAppStorePkgDialogNoAction());
        return e.create();
    }

    private KmAction newAppStorePkgDialogYesAction(final String pkg)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                try
                {
                    Uri uri = Uri.parse("market://details?id=" + pkg);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
                catch ( android.content.ActivityNotFoundException ex )
                {
                    launchBrowserPkg(pkg);
                }
            }
        };
    }

    private KmAction newAppStorePkgDialogNoAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                // none
            }
        };
    }

    private void launchBrowserPkg(String pkg)
    {
        try
        {
            startActivity(new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + pkg)));
        }
        catch ( ActivityNotFoundException ex )
        {
            alert(""
                + "You do not have a web browser installed! This is the minimum "
                + "requirement to install new packages.");
        }
    }

    //##################################################
    //# ui
    //##################################################

    /**
     * This can be useful as a partial solution for long running tasks where we
     * don't want the screen to turn off or re-orient. Note, this it typically
     * only a stop gap solution, and long-running tasks usually need to be
     * converted to services to be truly safe.
     */
    protected void disableScreenChanges()
    {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    protected void enableScreenChanges()
    {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    protected InputMethodManager getInputManager()
    {
        return (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    }
}
