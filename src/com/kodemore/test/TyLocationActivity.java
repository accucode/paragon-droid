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

import java.util.List;

import android.location.Address;
import android.location.Location;
import android.view.View;

import com.kodemore.utility.KmLocationManager;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTwoLineListView;

/**
 * Test the location manager.
 * 
 * PERMISSIONS in AndroidManifest.xml
 * (one or both of the following)
 *      <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 *      <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 */
public class TyLocationActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTwoLineListView _list;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _list = new KmTwoLineListView(ui());
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        KmRowLayout row;
        row = root.addRow();
        row.addButton("Providers", newProvidersAction());
        row.addButton("Location", newLocationAction());
        row.addButton("Address", newAddressAction());

        root.addView(_list);
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newProvidersAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleProviders();
            }
        };
    }

    private KmAction newLocationAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleLocation();
            }
        };
    }

    private KmAction newAddressAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleAddress();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleProviders()
    {
        _list.clearItems();

        KmLocationManager mgr;
        mgr = new KmLocationManager(getContext());

        List<String> names = mgr.getEnabledProviders();
        for ( String name : names )
        {
            mgr.setPreferredProvider(name);
            String s = mgr.formatAddressSummaryLine();
            _list.addItem(name, s);
        }
    }

    private void handleLocation()
    {
        _list.clearItems();

        KmLocationManager mgr;
        mgr = new KmLocationManager(getContext());

        Location loc;
        loc = mgr.getLocation();

        if ( loc == null )
        {
            alert("No Location Found.");
            return;
        }

        _list.addItem("Provider", loc.getProvider());
        _list.addItem("Latitude", loc.getLatitude());
        _list.addItem("Longitude", loc.getLongitude());
        _list.addItem("Time", loc.getTime());
        _list.addItem("Speed", loc.getSpeed());
        _list.addItem("Altitude", loc.getAltitude());
        _list.addItem("Bearing", loc.getBearing());
        _list.addItem("Accuracy", loc.getAccuracy());
    }

    private void handleAddress()
    {
        _list.clearItems();

        KmLocationManager mgr;
        mgr = new KmLocationManager(getContext());

        Address addr;
        addr = mgr.getAddress();

        if ( addr == null )
        {
            alert("No Address Found.");
            return;
        }

        int n = addr.getMaxAddressLineIndex();
        for ( int i = 0; i < n; i++ )
            _list.addItem("Address [" + i + "]", addr.getAddressLine(i));

        _list.addItem("Postal Code", addr.getPostalCode());

        _list.addItem("Country Code", addr.getCountryCode());
        _list.addItem("Country Name", addr.getCountryName());

        _list.addItem("Admin Area", addr.getAdminArea());
        _list.addItem("Admin Area (Sub)", addr.getSubAdminArea());

        _list.addItem("Thoroughfare", addr.getThoroughfare());
        _list.addItem("Thoroughfare (Sub)", addr.getSubThoroughfare());

        _list.addItem("Locality", addr.getLocality());
        _list.addItem("Locality (Sub)", addr.getSubLocality());

        _list.addItem("Premises", addr.getPremises());
        _list.addItem("Feature Name", addr.getFeatureName());
        _list.addItem("Phone", addr.getPhone());
        _list.addItem("Url", addr.getUrl());
        _list.addItem("Locale", addr.getLocale());

        _list.addItem("Latitude", addr.getLatitude());
        _list.addItem("Longitude", addr.getLongitude());
    }
}
