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
 * This corresponds to Android's ContactsContract.RawContact which contains
 * account specific information about a contact. For more information see 
 * http://goo.gl/y3bZCU
 * 
 * Information we currently Access:
 *      - ID 
 *      - ID of the aggregated contact
 *      - Structured Name (first, middle, last, etc.)
 *      - Account name ("jdoe@gmail.com")
 *      - Account type (gmail, facebook, etc.)
 *      - Starred
 *      - Nickname
 *      - Organization
 *      - Note
 *      - Times contacted
 *      - Last time contacted
 *      - Send to voicemail
 *      - Custom ringtone
 *      - Photo
 *      - List of emails
 *      - List of phones
 *      - List of addresses
 *      - List of Websites
 *      - List of Group Ids
 *      - List of relations
 *      - List of Events
 *      - List of Ims
 */
public class KmRawContact
{
    //##################################################
    //# variables
    //##################################################

    private String                    _id;
    private String                    _contactId;
    private String                    _accountName;
    private String                    _accountType;
    private boolean                   _starred;
    private String                    _note;
    private int                       _timesContacted;
    private KmTimestamp               _lastTimeContacted;
    private boolean                   _sendToVoicemail;
    private String                    _customRingtoneUri;
    private Bitmap                    _photo;

    private KmContactStructuredName   _structuredName;
    private KmContactNickname         _nickname;
    private KmContactOrganization     _organization;

    private KmList<KmContactEmail>    _emails;
    private KmList<KmContactPhone>    _phones;
    private KmList<KmContactAddress>  _addresses;
    private KmList<KmContactWebsite>  _websites;
    private KmList<String>            _groupIds;
    private KmList<KmContactRelation> _relations;
    private KmList<KmContactEvent>    _events;
    private KmList<KmContactIm>       _ims;

    //##################################################
    //# constructor
    //##################################################

    public KmRawContact()
    {
        _emails = new KmList<KmContactEmail>();
        _phones = new KmList<KmContactPhone>();
        _addresses = new KmList<KmContactAddress>();
        _websites = new KmList<KmContactWebsite>();
        _groupIds = new KmList<String>();
        _relations = new KmList<KmContactRelation>();
        _events = new KmList<KmContactEvent>();
        _ims = new KmList<KmContactIm>();
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

    public String getContactId()
    {
        return _contactId;
    }

    public void setContactId(String e)
    {
        _contactId = e;
    }

    public String getAccountName()
    {
        return _accountName;
    }

    public void setAccountName(String e)
    {
        _accountName = e;
    }

    public String getAccountType()
    {
        return _accountType;
    }

    public void setAccountType(String e)
    {
        _accountType = e;
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
     * Convenience method to set value directly from android RawContact table.
     *
     * The android table stores this value as an Integer where 1 is true, 0
     * is false.
     */
    public void setStarredFromInt(Integer e)
    {
        boolean b = e != null && e == 1;
        setStarred(b);
    }

    public String getNote()
    {
        return _note;
    }

    public void setNote(String e)
    {
        _note = e;
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
     * Convenience method to create set value directly from android RawContact table.
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
     * Convenience method to set value directly from android Contact data table.
     *
     * The android table stores this value as an Integer where 1 is true, 0
     * is false.
     */
    public void setSendToVoicemailFromInt(Integer e)
    {
        boolean b = e != null && e == 1;
        setSendToVoicemail(b);
    }

    public String getCustomRingtoneUri()
    {
        return _customRingtoneUri;
    }

    public void setCustomRingtoneUri(String customRingtone)
    {
        _customRingtoneUri = customRingtone;
    }

    public Bitmap getPhoto()
    {
        return _photo;
    }

    public void setPhoto(Bitmap e)
    {
        _photo = e;
    }

    //==================================================
    //= accessing (name)
    //==================================================

    public KmContactStructuredName getStructuredName()
    {
        return _structuredName;
    }

    public void setStructuredName(KmContactStructuredName e)
    {
        _structuredName = e;
    }

    public boolean hasStructuredName()
    {
        return getStructuredName() != null;
    }

    public String getFirstName()
    {
        return hasStructuredName()
            ? getStructuredName().getFirstName()
            : null;
    }

    public String getMiddleName()
    {
        return hasStructuredName()
            ? getStructuredName().getMiddleName()
            : null;
    }

    public String getLastName()
    {
        return hasStructuredName()
            ? getStructuredName().getLastName()
            : null;
    }

    public KmContactNickname getNickname()
    {
        return _nickname;
    }

    public void setNickname(KmContactNickname e)
    {
        _nickname = e;
    }

    public boolean hasNickname()
    {
        return getNickname() != null;
    }

    //==================================================
    //= accessing (organization)
    //==================================================

    public KmContactOrganization getOrganization()
    {
        return _organization;
    }

    public void setOrganization(KmContactOrganization e)
    {
        _organization = e;
    }

    public boolean hasOrganization()
    {
        return getOrganization() != null;
    }

    //==================================================
    //= accessing (emails)
    //==================================================

    public KmList<KmContactEmail> getEmails()
    {
        return _emails;
    }

    public void setEmails(KmList<KmContactEmail> e)
    {
        if ( e == null )
        {
            getEmails().clear();
            return;
        }

        _emails = e;
    }

    public void addEmail(KmContactEmail e)
    {
        getEmails().add(e);
    }

    public boolean hasEmails()
    {
        return _emails.isNotEmpty();
    }

    //==================================================
    //= accessing (phones)
    //==================================================

    public KmList<KmContactPhone> getPhones()
    {
        return _phones;
    }

    public void setPhones(KmList<KmContactPhone> e)
    {
        if ( e == null )
        {
            getPhones().clear();
            return;
        }

        _phones = e;
    }

    public void addPhone(KmContactPhone e)
    {
        getPhones().add(e);
    }

    public boolean hasPhones()
    {
        return _phones.isNotEmpty();
    }

    //==================================================
    //= accessing (addresses)
    //==================================================

    public KmList<KmContactAddress> getAddresses()
    {
        return _addresses;
    }

    public void setAddresses(KmList<KmContactAddress> e)
    {
        if ( e == null )
        {
            getAddresses().clear();
            return;
        }

        _addresses = e;
    }

    public void addAddress(KmContactAddress e)
    {
        getAddresses().add(e);
    }

    public boolean hasAddresses()
    {
        return _addresses.isNotEmpty();
    }

    //==================================================
    //= accessing (website)
    //==================================================

    public KmList<KmContactWebsite> getWebsites()
    {
        return _websites;
    }

    public void setWebsites(KmList<KmContactWebsite> e)
    {
        if ( e == null )
        {
            getWebsites().clear();
            return;
        }

        _websites = e;
    }

    public void addWebsite(KmContactWebsite e)
    {
        getWebsites().add(e);
    }

    public boolean hasWebsites()
    {
        return _websites.isNotEmpty();
    }

    //==================================================
    //= accessing (groups)
    //==================================================

    public KmList<String> getGroupIds()
    {
        return _groupIds;
    }

    public void setGroupIds(KmList<String> e)
    {
        if ( e == null )
        {
            getGroupIds().clear();
            return;
        }

        _groupIds = e;
    }

    public boolean hasGroupIds()
    {
        return _groupIds.isNotEmpty();
    }

    public void addGroupId(String e)
    {
        getGroupIds().add(e);
    }

    //==================================================
    //= accessing (relations)
    //==================================================

    public KmList<KmContactRelation> getRelations()
    {
        return _relations;
    }

    public void setRelations(KmList<KmContactRelation> e)
    {
        if ( e == null )
        {
            getRelations().clear();
            return;
        }

        _relations = e;
    }

    public void addRelation(KmContactRelation e)
    {
        getRelations().add(e);
    }

    public boolean hasRelations()
    {
        return getRelations().isNotEmpty();
    }

    //==================================================
    //= accessing (events)
    //==================================================

    public KmList<KmContactEvent> getEvents()
    {
        return _events;
    }

    public void setEvents(KmList<KmContactEvent> e)
    {
        if ( e == null )
        {
            getEvents().clear();
            return;
        }

        _events = e;
    }

    public void addEvent(KmContactEvent e)
    {
        getEvents().add(e);
    }

    public boolean hasEvents()
    {
        return getEvents().isNotEmpty();
    }

    //==================================================
    //= accessings (ims)
    //==================================================

    public KmList<KmContactIm> getIms()
    {
        return _ims;
    }

    public void setIms(KmList<KmContactIm> e)
    {
        if ( e == null )
        {
            getIms().clear();
            return;
        }

        _ims = e;
    }

    public void addIm(KmContactIm e)
    {
        getIms().add(e);
    }

    public boolean hasIms()
    {
        return getIms().isNotEmpty();
    }
}
