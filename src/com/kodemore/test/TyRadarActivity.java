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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.view.View;

import com.kodemore.intent.KmIntentUtility;
import com.kodemore.utility.KmLocationManager;
import com.kodemore.utility.KmPackageManager;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextView;
import com.kodemore.view.value.KmDoubleValue;

public class TyRadarActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    private static final String MY_RADAR_PREFERENCES = "myRadarPreferences";

    private static final String RADAR_PACKAGE        = "com.google.android.radar";
    private static final String RADAR_NAME           = "com.google.android.radar.SHOW_RADAR";

    //##################################################
    //# variables
    //##################################################

    private KmDoubleValue       _latitude;
    private KmDoubleValue       _longitude;

    private KmTextView          _addressText;
    private KmTextView          _accuracyText;
    private KmTextView          _latitudeText;
    private KmTextView          _longitudeText;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _latitude = ui().newDoubleValue();
        _latitude.setAutoSave();

        _longitude = ui().newDoubleValue();
        _longitude.setAutoSave();

        _addressText = ui().newTextView();
        _addressText.setAutoSave();

        _latitudeText = ui().newTextView();
        _latitudeText.setAutoSave();

        _longitudeText = ui().newTextView();
        _longitudeText.setAutoSave();

        _accuracyText = ui().newTextView();
        _accuracyText.setAutoSave();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        KmRowLayout buttons;
        buttons = root.addRow();
        buttons.addButton("Mark", newMarkAction());
        buttons.addButton("Find", newFindAction());

        root.addSpace();
        root.addText("Address");
        root.addView(_addressText);

        root.addSpace();
        root.addText("Latitude");
        root.addView(_latitudeText);

        root.addSpace();
        root.addText("Longitude");
        root.addView(_longitudeText);

        root.addSpace();
        root.addText("Accuracy");
        root.addView(_accuracyText);

        loadPreferences();

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newMarkAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleMark();
            }
        };
    }

    private KmAction newFindAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleFind();
            }
        };
    }

    private KmAction newOpenMarketAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleOpenMarket();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleMark()
    {
        KmLocationManager mgr;
        mgr = new KmLocationManager(getContext());

        Location loc;
        loc = mgr.getMostAccurateLocation();

        savePreferences(loc);
        loadPreferences();
    }

    private void handleFind()
    {
        if ( !checkRadar() )
            return;

        float lat = _latitude.getFloatValue();
        float lon = _longitude.getFloatValue();

        Intent i;
        i = new Intent(RADAR_NAME);
        i.putExtra("latitude", lat);
        i.putExtra("longitude", lon);
        startActivity(i);
    }

    //##################################################
    //# utility (install) 
    //##################################################

    private boolean checkRadar()
    {
        KmPackageManager pm = new KmPackageManager();
        if ( pm.supportsPackage(RADAR_PACKAGE) )
            return true;

        Builder b;
        b = new AlertDialog.Builder(getContext());
        b.setTitle("Radar Not Installed");
        b.setMessage("Open Market to install Radar?");
        b.setPositiveButton("Open Market", newOpenMarketAction());
        b.setNegativeButton("Not Now", KmAction.NOOP);
        b.show();

        return false;
    }

    private void handleOpenMarket()
    {
        KmIntentUtility.searchMarketForPackage(getContext(), RADAR_PACKAGE);
    }

    //##################################################
    //# preferences
    //##################################################

    private void savePreferences(Location loc)
    {
        KmLocationManager mgr;
        mgr = new KmLocationManager(getContext());
        String addr = mgr.formatAddressSummaryLineFor(loc);

        SharedPreferences p = getRadarPreferences();

        Editor e;
        e = p.edit();
        e.putString("address", addr);
        e.putFloat("latitude", (float)loc.getLatitude());
        e.putFloat("longitude", (float)loc.getLongitude());
        e.putFloat("accuracy", loc.getAccuracy());
        e.commit();
    }

    private void loadPreferences()
    {
        SharedPreferences p;
        p = getRadarPreferences();

        String addr = p.getString("address", "<none>");
        float acc = p.getFloat("accuracy", 0);
        float lat = p.getFloat("latitude", 0);
        float lon = p.getFloat("longitude", 0);

        _addressText.setText(addr);
        _latitudeText.setText(lat + "");
        _longitudeText.setText(lon + "");
        _accuracyText.setText(acc + "");

        _latitude.setFloatValue(lat);
        _longitude.setFloatValue(lon);
    }

    private SharedPreferences getRadarPreferences()
    {
        return getSharedPreferences(MY_RADAR_PREFERENCES, MODE_PRIVATE);
    }

}
