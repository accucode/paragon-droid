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

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContacts.Entity;

import com.kodemore.collection.KmList;
import com.kodemore.database.KmQueryBuilder;
import com.kodemore.database.KmSqlCursor;
import com.kodemore.utility.Kmu;

public class KmContactManager
{
    //##################################################
    //# variables
    //##################################################

    private Context _context;

    //##################################################
    //# constructor
    //##################################################

    public KmContactManager(Context e)
    {
        _context = e;
    }

    //##################################################
    //# contacts
    //##################################################

    public KmList<KmContact> getContacts()
    {
        return getContacts(Contacts.CONTENT_URI);
    }

    public KmList<KmContact> getContacts(Uri uri)
    {
        KmQueryBuilder q;
        q = new KmQueryBuilder(getContext());
        q.setUri(uri);

        return readContacts(q);
    }

    private KmList<KmContact> readContacts(KmQueryBuilder q)
    {
        KmSqlCursor c = q.toCursor();
        try
        {
            return readContacts(c);
        }
        finally
        {
            c.closeSafely();
        }
    }

    private KmList<KmContact> readContacts(KmSqlCursor c)
    {
        KmList<KmContact> v = new KmList<KmContact>();

        while ( c.next() )
            v.add(readContact(c));

        return v;
    }

    private KmContact readContact(KmSqlCursor c)
    {
        String id = c.getStringAt(Contacts._ID);
        String displayName = c.getStringAt(Contacts.DISPLAY_NAME);

        KmContact e;
        e = new KmContact();
        e.setId(id);
        e.setDisplayName(displayName);
        e.setStarredFromInt(c.getIntegerAt(Contacts.STARRED));
        e.setHasPhoneNumberFromInt(c.getIntegerAt(Contacts.HAS_PHONE_NUMBER));
        e.setTimesContacted(c.getIntegerAt(Contacts.TIMES_CONTACTED));
        e.setLastTimeContactedFromLong(c.getLongAt(Contacts.LAST_TIME_CONTACTED));
        e.setCustomRingtonUri(c.getStringAt(Contacts.CUSTOM_RINGTONE));

        String photoId;
        photoId = c.getStringAt(Contacts.PHOTO_ID);

        e.setPhoto(readPhoto(photoId));

        fill(e);

        return e;
    }

    private Bitmap readPhoto(String photoId)
    {
        if ( photoId == null )
            return null;

        KmQueryBuilder q;
        q = new KmQueryBuilder(getContext());
        q.setUri(Data.CONTENT_URI);
        q.whereEqualString(Data._ID, photoId);
        return readPhoto(q);
    }

    private Bitmap readPhoto(KmQueryBuilder q)
    {
        KmSqlCursor c = q.toCursor();

        if ( c.moveToFirst() )
            try
            {
                return readPhoto(c);
            }
            finally
            {
                c.closeSafely();
            }

        return null;
    }

    //##################################################
    //# Raw contacts
    //##################################################

    public KmList<String> getRawContactIds(KmContact c)
    {
        KmQueryBuilder q;
        q = new KmQueryBuilder(getContext());
        q.setUri(RawContacts.CONTENT_URI);
        q.whereEqualString(RawContacts.CONTACT_ID, c.getId());

        return readRawContactIds(q);
    }

    private KmList<String> readRawContactIds(KmQueryBuilder q)
    {
        KmSqlCursor c = q.toCursor();
        try
        {
            return readRawContactIds(c);
        }
        finally
        {
            c.closeSafely();
        }
    }

    private KmList<String> readRawContactIds(KmSqlCursor c)
    {
        KmList<String> v = new KmList<String>();

        while ( c.next() )
            v.add(readRawContactId(c));

        return v;
    }

    private String readRawContactId(KmSqlCursor c)
    {
        return c.getStringAt(RawContacts._ID);
    }

    //##################################################
    //# structured name
    //##################################################

    private KmContactStructuredName readStructuredName(KmSqlCursor c)
    {
        KmContactStructuredName name;
        name = new KmContactStructuredName();
        name.setDisplayName(c.getStringAt(StructuredName.DISPLAY_NAME));
        name.setPrefix(c.getStringAt(StructuredName.PREFIX));
        name.setFirstName(c.getStringAt(StructuredName.GIVEN_NAME));
        name.setMiddleName(c.getStringAt(StructuredName.MIDDLE_NAME));
        name.setLastName(c.getStringAt(StructuredName.FAMILY_NAME));
        name.setSuffix(c.getStringAt(StructuredName.SUFFIX));
        name.setPhoneticFirstName(c.getStringAt(StructuredName.PHONETIC_GIVEN_NAME));
        name.setPhoneticMiddleName(c.getStringAt(StructuredName.PHONETIC_MIDDLE_NAME));
        name.setPhoneticLastName(c.getStringAt(StructuredName.PHONETIC_FAMILY_NAME));
        return name;
    }

    //##################################################
    //# nickname
    //##################################################

    private KmContactNickname readNickname(KmSqlCursor c)
    {
        KmContactNickname n;
        n = new KmContactNickname();
        n.setTypeFromInt(c.getIntegerAt(Nickname.TYPE));
        n.setName(c.getStringAt(Nickname.NAME));
        n.setLabel(c.getStringAt(Nickname.LABEL));
        return n;
    }

    //##################################################
    //# phones
    //##################################################

    private KmContactPhone readPhone(KmSqlCursor c)
    {
        Integer typeCode = c.getIntegerAt(Phone.TYPE);
        String label = c.getStringAt(Phone.LABEL);
        String number = c.getStringAt(Phone.NUMBER);

        Integer primary = c.getIntegerAt(Phone.IS_PRIMARY);
        Integer superPrimary = c.getIntegerAt(Phone.IS_SUPER_PRIMARY);

        KmContactPhone e;
        e = new KmContactPhone();
        e.setTypeFromInt(typeCode);
        e.setLabel(label);

        e.setNumber(number);
        e.setPrimaryFromInt(primary);
        e.setSuperPrimaryFromInt(superPrimary);

        return e;
    }

    //##################################################
    //# email
    //##################################################

    private KmContactEmail readEmail(KmSqlCursor c)
    {
        String address = c.getStringAt(Email.DATA);

        Integer primary = c.getIntegerAt(Email.IS_PRIMARY);
        Integer superPrimary = c.getIntegerAt(Email.IS_SUPER_PRIMARY);

        String label = c.getStringAt(Email.LABEL);
        Integer typeCode = c.getIntegerAt(Email.TYPE);

        KmContactEmail e;
        e = new KmContactEmail();
        e.setAddress(address);
        e.setTypeFromInt(typeCode);
        e.setLabel(label);
        e.setPrimaryFromInt(primary);
        e.setSuperPrimaryFromInt(superPrimary);
        return e;
    }

    //##################################################
    //# address
    //##################################################

    private KmContactAddress readAddress(KmSqlCursor c)
    {
        String label = c.getStringAt(StructuredPostal.LABEL);
        Integer typeCode = c.getIntegerAt(StructuredPostal.TYPE);

        Integer primary = c.getIntegerAt(StructuredPostal.IS_PRIMARY);
        Integer superPrimary = c.getIntegerAt(StructuredPostal.IS_SUPER_PRIMARY);

        String formatted = c.getStringAt(StructuredPostal.FORMATTED_ADDRESS);
        String poBox = c.getStringAt(StructuredPostal.POBOX);
        String street = c.getStringAt(StructuredPostal.STREET);
        String city = c.getStringAt(StructuredPostal.CITY);
        String region = c.getStringAt(StructuredPostal.REGION);
        String postCode = c.getStringAt(StructuredPostal.POSTCODE);
        String country = c.getStringAt(StructuredPostal.COUNTRY);

        KmContactAddress e;
        e = new KmContactAddress();
        e.setTypeFromInt(typeCode);
        e.setLabel(label);

        e.setPrimaryFromInt(primary);
        e.setSuperPrimaryFromInt(superPrimary);

        e.setFormattedAddress(formatted);
        e.setPoBox(poBox);
        e.setStreet(street);
        e.setCity(city);
        e.setRegion(region);
        e.setPostCode(postCode);
        e.setCountry(country);

        return e;
    }

    //##################################################
    //# im
    //##################################################

    private KmContactIm readIm(KmSqlCursor c)
    {
        KmContactIm e;
        e = new KmContactIm();

        e.setTypeFromInt(c.getIntegerAt(Im.TYPE));
        e.setLabel(c.getStringAt(Im.LABEL));
        e.setProtocolFromInt(c.getIntegerAt(Im.PROTOCOL));
        e.setCustomProtocol(c.getStringAt(Im.CUSTOM_PROTOCOL));
        e.setData(c.getStringAt(Im.DATA));
        return e;
    }

    //##################################################
    //# website
    //##################################################

    private KmContactWebsite readWebsite(KmSqlCursor c)
    {
        KmContactWebsite e;
        e = new KmContactWebsite();
        e.setTypeFromInt(c.getIntegerAt(Website.TYPE));
        e.setLabel(c.getStringAt(Website.LABEL));
        e.setUrl(c.getStringAt(Website.URL));
        return e;
    }

    //##################################################
    //# groups
    //##################################################

    private String readGroupId(KmSqlCursor c)
    {
        return c.getStringAt(GroupMembership.GROUP_SOURCE_ID);
    }

    //##################################################
    //# relations
    //##################################################

    private KmContactRelation readRelation(KmSqlCursor c)
    {
        KmContactRelation e;
        e = new KmContactRelation();
        e.setTypeFromInt(c.getIntegerAt(Relation.TYPE));
        e.setLabel(c.getStringAt(Relation.LABEL));
        e.setName(c.getStringAt(Relation.NAME));
        return e;
    }

    //##################################################
    //# events
    //##################################################

    private KmContactEvent readEvent(KmSqlCursor c)
    {
        KmContactEvent e;
        e = new KmContactEvent();
        e.setTypeFromInt(c.getIntegerAt(Event.TYPE));
        e.setLabel(c.getStringAt(Event.LABEL));
        e.setStartDateFromString(c.getStringAt(Event.START_DATE));
        return e;
    }

    //##################################################
    //# photo
    //##################################################

    private Bitmap readPhoto(KmSqlCursor c)
    {
        byte[] blob = c.getBlobAt(Photo.PHOTO);

        if ( blob == null )
            return null;

        BitmapFactory b = new BitmapFactory();
        return b.decodeByteArray(blob, 0, blob.length);
    }

    //##################################################
    //# organization
    //##################################################

    private KmContactOrganization readOrganization(KmSqlCursor c)
    {
        KmContactOrganization e;
        e = new KmContactOrganization();

        e.setTypeFromInt(c.getIntegerAt(Organization.TYPE));
        e.setLabel(c.getStringAt(Organization.LABEL));

        e.setCompany(c.getStringAt(Organization.COMPANY));
        e.setTitle(c.getStringAt(Organization.TITLE));
        e.setDepartment(c.getStringAt(Organization.DEPARTMENT));
        e.setJobDescription(c.getStringAt(Organization.JOB_DESCRIPTION));
        e.setSymbol(c.getStringAt(Organization.SYMBOL));
        e.setOfficeLocation(c.getStringAt(Organization.OFFICE_LOCATION));
        e.setPhoneticName(c.getStringAt(Organization.PHONETIC_NAME));

        return e;
    }

    //##################################################
    //# support
    //##################################################

    private Context getContext()
    {
        return _context;
    }

    //##################################################
    //# fill
    //##################################################

    public void fill(KmContact c)
    {
        fillRawContacts(c);
    }

    public void fillRawContacts(KmContact e)
    {
        KmList<KmRawContact> v = new KmList<KmRawContact>();

        for ( String s : getRawContactIds(e) )
        {
            KmRawContact raw;
            raw = new KmRawContact();
            raw.setId(s);
            raw.setContactId(e.getId());
            fill(raw);
            v.add(raw);
        }

        e.setRawContacts(v);
    }

    public void fill(KmRawContact e)
    {
        fillfields(e);
        fillData(e);
    }

    private void fillfields(KmRawContact e)
    {
        KmQueryBuilder q;
        q = new KmQueryBuilder(getContext());
        q.setUri(RawContacts.CONTENT_URI);
        q.whereEqualString(RawContacts._ID, e.getId());

        KmSqlCursor c;
        c = q.toCursor();

        try
        {
            fillFields(e, c);
        }
        finally
        {
            c.closeSafely();
        }
    }

    private void fillFields(KmRawContact e, KmSqlCursor c)
    {
        if ( c.moveToFirst() )
        {
            e.setAccountName(c.getStringAt(RawContacts.ACCOUNT_NAME));
            e.setAccountType(c.getStringAt(RawContacts.ACCOUNT_TYPE));
            e.setStarredFromInt(c.getIntegerAt(RawContacts.STARRED));
            e.setTimesContacted(c.getIntegerAt(RawContacts.TIMES_CONTACTED));
            e.setLastTimeContactedFromLong(c.getLongAt(RawContacts.LAST_TIME_CONTACTED));
            e.setSendToVoicemailFromInt(c.getIntegerAt(RawContacts.SEND_TO_VOICEMAIL));
            e.setCustomRingtoneUri(c.getStringAt(RawContacts.CUSTOM_RINGTONE));
        }
    }

    private void fillData(KmRawContact e)
    {
        Long longId = Kmu.parseLong(e.getId());

        Uri rawContactUri = ContentUris.withAppendedId(RawContacts.CONTENT_URI, longId);
        Uri entityUri = Uri.withAppendedPath(rawContactUri, Entity.CONTENT_DIRECTORY);

        KmQueryBuilder q;
        q = new KmQueryBuilder(getContext());
        q.setUri(entityUri);

        KmSqlCursor c;
        c = q.toCursor();

        try
        {
            fillData(e, c);
        }
        finally
        {
            c.closeSafely();
        }
    }

    private void fillData(KmRawContact e, KmSqlCursor c)
    {
        while ( c.next() )
        {
            String mimeType = c.getStringAt(RawContacts.Entity.MIMETYPE);

            if ( mimeType.equals(StructuredName.CONTENT_ITEM_TYPE) )
                e.setStructuredName(readStructuredName(c));

            if ( mimeType.equals(Nickname.CONTENT_ITEM_TYPE) )
                e.setNickname(readNickname(c));

            if ( mimeType.equals(StructuredPostal.CONTENT_ITEM_TYPE) )
                e.addAddress(readAddress(c));

            if ( mimeType.equals(Email.CONTENT_ITEM_TYPE) )
                e.addEmail(readEmail(c));

            if ( mimeType.equals(Phone.CONTENT_ITEM_TYPE) )
                e.addPhone(readPhone(c));

            if ( mimeType.equals(Im.CONTENT_ITEM_TYPE) )
                e.addIm(readIm(c));

            if ( mimeType.equals(Website.CONTENT_ITEM_TYPE) )
                e.addWebsite(readWebsite(c));

            if ( mimeType.equals(GroupMembership.CONTENT_ITEM_TYPE) )
                e.addGroupId(readGroupId(c));

            if ( mimeType.equals(Relation.CONTENT_ITEM_TYPE) )
                e.addRelation(readRelation(c));

            if ( mimeType.equals(Event.CONTENT_ITEM_TYPE) )
                e.addEvent(readEvent(c));

            if ( mimeType.equals(Photo.CONTENT_ITEM_TYPE) )
                e.setPhoto(readPhoto(c));

            if ( mimeType.equals(Note.CONTENT_ITEM_TYPE) )
                e.setNote(c.getStringAt(Note.NOTE));

            if ( mimeType.equals(Organization.CONTENT_ITEM_TYPE) )
                e.setOrganization(readOrganization(c));
        }
    }
}
