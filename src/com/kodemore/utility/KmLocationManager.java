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

package com.kodemore.utility;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.kodemore.collection.KmList;

public class KmLocationManager
{
    //##################################################
    //# variables
    //##################################################

    private Context _context;
    private String  _preferredProvider;

    //##################################################
    //# constructor
    //##################################################

    public KmLocationManager(Context context)
    {
        _context = context;
        _preferredProvider = null;
    }

    //##################################################
    //# accessing
    //##################################################

    public String getPreferredProvider()
    {
        return _preferredProvider;
    }

    public void setPreferredProvider(String e)
    {
        _preferredProvider = e;
    }

    public void clearPreferredProvider()
    {
        setPreferredProvider(null);
    }

    public boolean hasPreferredProvider()
    {
        return _preferredProvider != null;
    }

    //##################################################
    //# abstract accessing
    //##################################################

    public Address getAddress()
    {
        Location loc = getLocation();
        return getAddressFor(loc);
    }

    public Location getLocation()
    {
        String p = getProvider();
        if ( p == null )
            return null;

        return getLastLocationFor(p);
    }

    //##################################################
    //# locations
    //##################################################

    public Location getLastLocation()
    {
        Location bestLoc = null;
        long bestTime = 0;

        for ( Location e : getLastLocations() )
            if ( e.getTime() > bestTime )
            {
                bestTime = e.getTime();
                bestLoc = e;
            }

        return bestLoc;
    }

    public Location getMostAccurateLocation()
    {
        Location bestLoc = null;
        float bestAccuracy = Float.MAX_VALUE;

        for ( Location e : getLastLocations() )
        {
            float a = e.getAccuracy();
            if ( a > 0 && a < bestAccuracy )
            {
                bestAccuracy = a;
                bestLoc = e;
            }
        }

        return bestLoc;
    }

    public KmList<Location> getLastLocations()
    {
        KmList<Location> v = new KmList<Location>();

        for ( String e : getEnabledProviders() )
            v.addNonNull(getLastLocationFor(e));

        return v;
    }

    public Location getLastLocationFor(String name)
    {
        return getInner().getLastKnownLocation(name);
    }

    //##################################################
    //# provider
    //##################################################

    private String getProvider()
    {
        if ( hasPreferredProvider() )
            return getPreferredProvider();

        return getLastProvider();
    }

    private String getLastProvider()
    {
        String lastName = null;
        long lastTime = 0;

        List<String> names = getEnabledProviders();
        for ( String name : names )
        {
            Location loc = getLastLocationFor(name);
            if ( loc == null )
                continue;

            if ( loc.getTime() > lastTime )
            {
                lastTime = loc.getTime();
                lastName = name;
            }
        }

        return lastName;
    }

    public List<String> getEnabledProviders()
    {
        boolean enabled = true;
        List<String> v = getInner().getProviders(enabled);
        return v;
    }

    public String getCourseProvider()
    {
        Criteria c;
        c = new Criteria();
        c.setAccuracy(c.ACCURACY_COARSE);
        c.setPowerRequirement(c.POWER_LOW);
        c.setAltitudeRequired(false);
        c.setBearingRequired(false);
        c.setCostAllowed(false);
        c.setSpeedRequired(false);

        boolean enabled = true;

        return getInner().getBestProvider(c, enabled);
    }

    //##################################################
    //# support
    //##################################################

    private Context getContext()
    {
        return _context;
    }

    public LocationManager getInner()
    {
        String key = Context.LOCATION_SERVICE;
        return (LocationManager)getContext().getSystemService(key);
    }

    //##################################################
    //# address
    //##################################################

    private Address getAddressFor(Location loc)
    {
        try
        {
            if ( loc == null )
                return null;

            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            int max = 1;

            Geocoder g;
            g = new Geocoder(getContext(), Locale.US);

            List<Address> v = g.getFromLocation(lat, lon, max);
            if ( v == null || v.isEmpty() )
                return null;

            return v.get(0);
        }
        catch ( IOException ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    public String formatAddressSummaryLine()
    {
        Address addr = getAddress();
        return formatAddressSummaryLineFor(addr);
    }

    public String formatAddressSummaryLineFor(Location loc)
    {
        Address addr = getAddressFor(loc);
        return formatAddressSummaryLineFor(addr);
    }

    public String formatAddressSummaryLineFor(Address addr)
    {
        if ( addr == null )
            return null;

        String street = addr.getMaxAddressLineIndex() >= 0
            ? addr.getAddressLine(0)
            : null;

        String city = addr.getSubAdminArea();
        String state = addr.getAdminArea();
        String zip = addr.getPostalCode();

        KmList<String> v;
        v = new KmList<String>();
        v.addNonNull(street);
        v.addNonNull(city);
        v.addNonNull(state);
        v.addNonNull(zip);
        return v.format(", ");
    }

}
