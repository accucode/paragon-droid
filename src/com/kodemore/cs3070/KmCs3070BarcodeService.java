package com.kodemore.cs3070;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.kodemore.utility.KmLoopRunnable;
import com.kodemore.utility.Kmu;

/**
 * I am used to scan barcodes in a background service and report
 * those barcodes to the application.  Bluetooth connections relatively slow process that usually takes several seconds, 
 * 
 * I am currently hardcoded scan 1D barcodes via the Motorola/Symbol 
 * CS3070 Barcode bluetooth scanner, using the SPP protocol.    
 */
public class KmCs3070BarcodeService
    extends Service
{
    //##################################################
    //# listener (static)
    //##################################################

    private static KmCs3070BarcodeListenerIF _listener;

    public static KmCs3070BarcodeListenerIF getListener()
    {
        return _listener;
    }

    public static void setListener(KmCs3070BarcodeListenerIF e)
    {
        _listener = e;
    }

    public static void clearListener()
    {
        setListener(null);
    }

    public static void fireListener(String s)
    {
        KmCs3070BarcodeListenerIF e = getListener();
        if ( e != null )
            e.onBarcodeScan(s);
    }

    //##################################################
    //# variables
    //##################################################

    /**
     * The class actually responsible for the bluetooth connection
     * to the cs3070 scanner.
     */
    private KmCs3070BarcodeScanner _scanner;

    private KmLoopRunnable         _autoConnectRunnable;

    //##################################################
    //# constructor
    //##################################################

    public KmCs3070BarcodeService()
    {
        // none
    }

    //##################################################
    //# create / destroy
    //##################################################

    @Override
    public void onCreate()
    {
        _scanner = null;

        startAutoConnect();
    }

    @Override
    public void onDestroy()
    {
        stopAutoConnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    //##################################################
    //# scanner
    //##################################################

    public boolean isScannerConnected()
    {
        return _scanner != null && _scanner.isRunning();
    }

    /**
     * Connect to the bluetooth scanner.
     * Return true iff a _new_ connection is established.
     * Note: this returns false if the scanner was already connected.
     */
    private boolean connectScanner()
    {
        if ( isScannerConnected() )
            return false;

        KmCs3070BarcodeScanner temp = new KmCs3070BarcodeScanner();

        if ( !temp.setDeviceAuto() )
            return false;

        if ( !temp.start() )
            return false;

        _scanner = temp;
        _scanner.setListener(newScannerListener());
        return true;
    }

    /**
     * Disconnect from the bluetooth scanner. 
     */
    private void disconnectScanner()
    {
        if ( isScannerConnected() )
            _scanner.stop();

        _scanner = null;
    }

    //##################################################
    //# support
    //##################################################

    private KmCs3070BarcodeListenerIF newScannerListener()
    {
        return new KmCs3070BarcodeListenerIF()
        {
            @Override
            public void onBarcodeScan(String e)
            {
                fireListener(e);
            }
        };
    }

    //##################################################
    //# auto connect
    //##################################################

    public void startAutoConnect()
    {
        if ( _autoConnectRunnable != null )
            return;

        _autoConnectRunnable = new AutoConnectRunnable();
        _autoConnectRunnable.startNewThread();
    }

    public void stopAutoConnect()
    {
        if ( _autoConnectRunnable == null )
            return;

        _autoConnectRunnable.cancel();
        _autoConnectRunnable = null;
    }

    public boolean isAutoConnectRunning()
    {
        return _autoConnectRunnable != null && _autoConnectRunnable.isNotCancelled();
    }

    private class AutoConnectRunnable
        extends KmLoopRunnable
    {
        @Override
        public void runOnce()
        {
            try
            {
                int delaySec = 1;

                if ( !isScannerConnected() )
                {
                    connectScanner();
                    Kmu.sleepSeconds(delaySec);
                }

                if ( _scanner != null && _scanner.isNotRunning() )
                {
                    resetScanner();
                    Kmu.sleepSeconds(delaySec);
                }

                Kmu.sleepSeconds(delaySec);
            }
            catch ( RuntimeException ex )
            {
                // ignored
            }
        }

        private void resetScanner()
        {
            _scanner.stop();
            _scanner.clearListener();
            _scanner = null;
        }

        @Override
        public void onStop()
        {
            disconnectScanner();
        }
    }
}
