package com.kodemore.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public abstract class KmConnectionListener
    extends BroadcastReceiver
{
    //##################################################
    //# variables
    //##################################################

    private Context             _context;
    private ConnectivityManager _manager;
    private IntentFilter        _intentFilter;
    private NetworkInfo         _activeInfo;
    private State               _activeState;
    private boolean             _statusChanged;

    //##################################################
    //# constructor
    //##################################################

    /**
     * A connection listener won't do anything unless you register it.
     */
    public KmConnectionListener(Context context)
    {
        _context = context;
        _activeState = null;
        _statusChanged = false;
        _manager = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);

        _intentFilter = new IntentFilter();
        _intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public void onReceive(Context context, Intent intent)
    {
        updateStates();

        if ( isConnectionChanged() )
            fire();
    }

    //##################################################
    //# update
    //##################################################

    protected abstract void handle();

    private void fire()
    {
        handle();
    }

    private void updateStates()
    {
        updateInfo();

        State newState = _activeInfo != null
            ? _activeInfo.getState()
            : null;

        if ( _activeState != newState )
            _statusChanged = true;
        else
            _statusChanged = false;

        _activeState = newState;
    }

    private void updateInfo()
    {
        _activeInfo = _manager.getActiveNetworkInfo();
    }

    //##################################################
    //# accessing
    //##################################################

    /**
     * One cannot register the reciever inside of itself
     * and, as such, one needs a way to grab the intent filter.
     */
    public IntentFilter getIntentFilter()
    {
        return _intentFilter;
    }

    public boolean isConnected()
    {
        return _activeState == State.CONNECTED;
    }

    public boolean isDisconnected()
    {
        return !isConnected();
    }

    //##################################################
    //# utility
    //##################################################

    private boolean isConnectionChanged()
    {
        return _statusChanged;
    }

}
