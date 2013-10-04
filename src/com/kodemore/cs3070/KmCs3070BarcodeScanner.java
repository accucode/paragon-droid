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

package com.kodemore.cs3070;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.KmBridge;
import com.kodemore.utility.Kmu;

/**
 * I provide a bluetooth socket interface to the Symbol CS3070
 * 1D barcode scanner.
 */
public class KmCs3070BarcodeScanner
{
    //##################################################
    //# constants
    //##################################################

    /**
     * The prefix used to automatically select the correct device 
     * from the list of available bonded (Paired) devices.
     */
    private static final String       NAME_PREFIX              = "cs3070";

    /**
     * The Service Description Protocol (SDP) for Serial Port Profile (SPP)
     * service class running on the RFComm bluetooth protocol.  (0x1101).
     */
    private static final String       UUID_SERIAL_PORT_PROFILE = "00001101-0000-1000-8000-00805F9B34FB";

    private static final int          CARRIAGE_RETURN          = 0x0D;
    private static final int          LINE_FEED                = 0x0A;

    //##################################################
    //# variables
    //##################################################

    /**
     * The currently selected device.
     */
    private BluetoothDevice           _device;

    /**
     * A background thread that loops while listening for barcode scans.
     */
    private LocalThread               _thread;

    /**
     * A hook for a barcode callback.  The listener will be notified
     * anytime a barcode is scanned.
     */
    private KmCs3070BarcodeListenerIF _listener;

    //##################################################
    //# constructor
    //##################################################

    public KmCs3070BarcodeScanner()
    {
        _device = null;
        _thread = null;
        _listener = null;
    }

    //##################################################
    //# device
    //##################################################

    public BluetoothDevice getDevice()
    {
        return _device;
    }

    public void setDevice(BluetoothDevice e)
    {
        _device = e;
    }

    /**
     * Update the currently set device to our best guess for
     * a bonded cs3070 scanner.  Returns true upon success.
     */
    public boolean setDeviceAuto()
    {
        _device = findBondedCs3070();

        return _device != null;
    }

    public boolean hasDevice()
    {
        return _device != null;
    }

    /**
     * Find a bonded device with a matching name.
     * This does NOT update the locally set device.
     */
    public BluetoothDevice findBondedCs3070()
    {
        try
        {
            BluetoothAdapter a = getBluetoothAdapter();
            if ( a == null )
                return null;

            Set<BluetoothDevice> v = a.getBondedDevices();
            for ( BluetoothDevice e : v )
                if ( isBondedCs3070(e) )
                    return e;

            return null;
        }
        catch ( Exception ex )
        {
            Kmu.handleUnhandledException(ex);
            return null;
        }
    }

    private boolean isBondedCs3070(BluetoothDevice e)
    {
        boolean hasName = e.getName().toLowerCase().startsWith(NAME_PREFIX);
        boolean isBonded = e.getBondState() == BluetoothDevice.BOND_BONDED;

        return hasName && isBonded;
    }

    //##################################################
    //# listeners
    //##################################################

    public KmCs3070BarcodeListenerIF getListener()
    {
        return _listener;
    }

    public void setListener(KmCs3070BarcodeListenerIF e)
    {
        _listener = e;
    }

    public boolean hasListener()
    {
        return getListener() != null;
    }

    public void clearListener()
    {
        setListener(null);
    }

    private void fireListener(String barcode)
    {
        if ( hasListener() )
            getListener().onBarcodeScan(barcode);
    }

    //##################################################
    //# start / stop
    //##################################################

    public boolean start()
    {
        if ( _thread != null )
            return false;

        BluetoothSocket socket = connect();
        if ( socket == null )
            return false;

        _thread = new LocalThread(socket);
        _thread.start();
        return true;
    }

    public boolean stop()
    {
        if ( _thread == null )
            return false;

        _thread.cancel();
        _thread = null;
        return true;
    }

    public boolean isRunning()
    {
        return _thread != null && _thread.isAlive();
    }

    public boolean isNotRunning()
    {
        return !isRunning();
    }

    //##################################################
    //# support
    //##################################################

    private BluetoothSocket connect()
    {
        try
        {
            BluetoothDevice device = getDevice();
            if ( device == null )
                return null;

            BluetoothAdapter adapter;
            adapter = getBluetoothAdapter();
            adapter.cancelDiscovery();

            String address = device.getAddress();
            device = adapter.getRemoteDevice(address);
            UUID uuid = UUID.fromString(UUID_SERIAL_PORT_PROFILE);

            return _connect(device, uuid);
        }
        catch ( IOException ex )
        {
            return null;
        }
    }

    /**
     * The following .connect() generates a Log Warning with the message:
     *      getBluetoothService() called with no BluetoothManagerCallback.
     *
     * The message appears harmless but it is annoying and clutter up the 
     * logcat output.  It doesn't appear that we are actually doing anything 
     * wrong.  Nor is there any obvious way register a listener so as to remove 
     * this message.
     * 
     * A pertinent copy of Google's code can be found here: http://goo.gl/wmt7m
     */
    private BluetoothSocket _connect(BluetoothDevice device, UUID uuid) throws IOException
    {
        BluetoothSocket e;
        e = device.createRfcommSocketToServiceRecord(uuid);
        e.connect();
        return e;
    }

    //##################################################
    //# thread
    //##################################################

    private class LocalThread
        extends Thread
    {
        private BluetoothSocket _socket;
        private KmStringBuilder _buffer;
        private boolean         _cancelled;

        public LocalThread(BluetoothSocket socket)
        {
            _socket = socket;
            _buffer = new KmStringBuilder();
            _cancelled = false;
        }

        @Override
        public void run()
        {
            try
            {
                while ( true )
                {
                    runOnce();

                    if ( _cancelled )
                        break;
                }
            }
            catch ( IOException ex )
            {
                Kmu.unhandledException(ex);
            }
            finally
            {
                closeSocket();
            }
        }

        /**
         * Append characters from the socket onto the buffer,
         * until there are no more characters currently available. 
         */
        private void runOnce() throws IOException
        {
            if ( _socket == null )
                return;

            InputStream is = _socket.getInputStream();
            while ( true )
            {
                if ( _cancelled )
                    break;

                int i = is.read();
                if ( i < 0 )
                    break;

                if ( i == CARRIAGE_RETURN || i == LINE_FEED )
                {
                    if ( _buffer.isEmpty() )
                        continue;

                    fireListener(_buffer.toString());
                    _buffer.clear();
                    continue;
                }

                _buffer.append((char)i);
            }
        }

        private void cancel()
        {
            _cancelled = true;
        }

        private void closeSocket()
        {
            try
            {
                if ( _socket != null )
                    _socket.close();
            }
            catch ( IOException ex )
            {
                Kmu.unhandledException(ex);
            }
            finally
            {
                _socket = null;
            }
        }
    }

    //##################################################
    //# adapter
    //##################################################

    private BluetoothAdapter getBluetoothAdapter()
    {
        return KmBridge.getInstance().getBluetoothAdapter();
    }
}
