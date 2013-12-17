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

package com.kodemore.contact;

import android.graphics.Bitmap;

import com.kodemore.collection.KmList;
import com.kodemore.time.KmTimestamp;

/**
 * This is the aggregate contact corresponding to Android's ContactsContract.Contact
 * which is comprised of RawContacts.  For more information see http://goo.gl/y3bZCU
 * 
 * Information we currently access:
 *      - The Contact ID
 *      - The contact display name
 *      - The Raw Contacts
 *      - The Contat's display name
 *      - Starred
 *      - Has phone number
 *      - Times contacted
 *      - Last time contacted
 *      - Send to voicemail (auto send to voicemail)
 *      - Photo
 *      - Custom ringtone
 *      
 * (Most of these values are aggregated from raw contacts)
 * Information we can access, but don't right now:
 *      - Contact IM presence (online status, which service, etc.)
 */
public class KmContact
{
    //##################################################
    //# variables
    //##################################################

    private String               _id;
    private String               _displayName;

    private boolean              _starred;
    private boolean              _hasPhoneNumber;

    private int                  _timesContacted;
    private KmTimestamp          _lastTimeContacted;

    private boolean              _sendToVoicemail;

    private Bitmap               _photo;

    private String               _customRingtonUri;

    private KmList<KmRawContact> _rawContacts;

    //##################################################
    //# constructor
    //##################################################

    public KmContact()
    {
        // none
    }

    //##################################################
    //# accessing
    //##################################################

    public String getId()
    {
        return _id;
    }

    public void setId(String e)
    {
        _id = e;
    }

    public String getDisplayName()
    {
        return _displayName;
    }

    public void setDisplayName(String e)
    {
        _displayName = e;
    }

    public boolean hasDisplayNameSubstring(String s)
    {
        if ( _displayName == null )
            return false;

        return _displayName.toLowerCase().trim().contains(s.toLowerCase());
    }

    public boolean getStarred()
    {
        return _starred;
    }

    public void setStarred(boolean e)
    {
        _starred = e;
    }

    /**
     * Convenience method to set value directly from android Contacts table.
     *
     * The android table stores this value as an Integer where 1 is true, 0
     * is false.
     */
    public void setStarredFromInt(Integer e)
    {
        boolean b = e != null && e == 1;
        setStarred(b);
    }

    public boolean getHasPhoneNumber()
    {
        return _hasPhoneNumber;
    }

    public void setHasPhoneNumber(boolean e)
    {
        _hasPhoneNumber = e;
    }

    /**
     * Convenience method to set value directly from android Contacts table.
     *
     * The android table stores this value as an Integer where 1 is true, 0
     * is false.
     */
    public void setHasPhoneNumberFromInt(Integer e)
    {
        boolean b = e != null && e == 1;
        setHasPhoneNumber(b);
    }

    public int getTimesContacted()
    {
        return _timesContacted;
    }

    public void setTimesContacted(int e)
    {
        _timesContacted = e;
    }

    public KmTimestamp getLastTimeContacted()
    {
        return _lastTimeContacted;
    }

    public void setLastTimeContacted(KmTimestamp e)
    {
        _lastTimeContacted = e;
    }

    /**
     * Convenience method to create set value directly from android Contacts table.
     *
     * The android table stored this value as the number of miliseconds since 1970
     */
    public void setLastTimeContactedFromLong(Long e)
    {
        if ( e == null )
        {
            setLastTimeContacted(null);
            return;
        }

        KmTimestamp ts = KmTimestamp.createFromMsSince1970(e);
        setLastTimeContacted(ts);
    }

    public boolean getSendToVoicemail()
    {
        return _sendToVoicemail;
    }

    public void setSendToVoicemail(boolean e)
    {
        _sendToVoicemail = e;
    }

    /**
     * Convenience method to set value directly from android Contacts table.
     *
     * The android table stores this value as an Integer where 1 is true, 0
     * is false.
     */
    public void setSendToVoicemailFromIn(Integer e)
    {
        boolean b = e != null && e == 1;
        setSendToVoicemail(b);
    }

    public Bitmap getPhoto()
    {
        return _photo;
    }

    public void setPhoto(Bitmap e)
    {
        _photo = e;
    }

    public String getCustomRingtonUri()
    {
        return _customRingtonUri;
    }

    public void setCustomRingtonUri(String e)
    {
        _customRingtonUri = e;
    }

    public KmList<KmRawContact> getRawContacts()
    {
        return _rawContacts;
    }

    public void setRawContacts(KmList<KmRawContact> e)
    {
        _rawContacts = e;
    }

    //##################################################
    //# convenience
    //##################################################

    public KmList<KmContactPhone> getPhones()
    {
        KmList<KmContactPhone> v = new KmList<KmContactPhone>();

        for ( KmRawContact e : getRawContacts() )
            v.addAll(e.getPhones());

        return v;
    }

    public KmList<KmContactAddress> getAddresses()
    {
        KmList<KmContactAddress> v;
        v = new KmList<KmContactAddress>();

        for ( KmRawContact e : getRawContacts() )
            v.addAll(e.getAddresses());

        return v;
    }

    public KmList<KmContactEmail> getEmails()
    {
        KmList<KmContactEmail> v;
        v = new KmList<KmContactEmail>();

        for ( KmRawContact e : getRawContacts() )
            v.addAll(e.getEmails());

        return v;
    }

    public KmList<Bitmap> getPhotos()
    {
        KmList<Bitmap> v;
        v = new KmList<Bitmap>();

        for ( KmRawContact e : getRawContacts() )
            v.add(e.getPhoto());

        return v;
    }
}
