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

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Contacts;

import com.kodemore.collection.KmList;
import com.kodemore.database.KmSqlCursor;
import com.kodemore.database.KmQueryBuilder;


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

        return e;
    }

    //##################################################
    //# phones
    //##################################################

    public KmList<KmContactPhone> readPhones(String contactId)
    {
        KmQueryBuilder q;
        q = new KmQueryBuilder(getContext());
        q.setUri(Phone.CONTENT_URI);
        q.whereEqualString(Phone.CONTACT_ID, contactId);
        return readPhones(q);
    }

    private KmList<KmContactPhone> readPhones(KmQueryBuilder q)
    {
        KmSqlCursor c = q.toCursor();
        try
        {
            return readPhones(c);
        }
        finally
        {
            c.closeSafely();
        }
    }

    private KmList<KmContactPhone> readPhones(KmSqlCursor c)
    {
        KmList<KmContactPhone> v = new KmList<KmContactPhone>();

        while ( c.next() )
            v.add(readPhone(c));

        return v;
    }

    private KmContactPhone readPhone(KmSqlCursor c)
    {
        String id = c.getStringAt(Phone._ID);
        String displayName = c.getStringAt(Phone.DISPLAY_NAME);

        String label = c.getStringAt(Phone.LABEL);
        Integer typeCode = c.getIntegerAt(Phone.TYPE);
        String typeName = Phone.getTypeLabel(getResources(), typeCode, label).toString();

        String number = c.getStringAt(Phone.NUMBER);

        Integer primaryFlag = c.getIntegerAt(Phone.IS_PRIMARY);
        boolean primary = primaryFlag != null && primaryFlag != 0;

        Integer superPrimaryFlag = c.getIntegerAt(Phone.IS_SUPER_PRIMARY);
        boolean superPrimary = superPrimaryFlag != null && superPrimaryFlag != 0;

        KmContactPhone e;
        e = new KmContactPhone();
        e.setId(id);
        e.setDisplayName(displayName);
        e.setTypeCode(typeCode);
        e.setTypeName(typeName);

        e.setNumber(number);
        e.setPrimary(primary);
        e.setSuperPrimary(superPrimary);

        return e;
    }

    //##################################################
    //# email
    //##################################################

    public KmList<KmContactEmail> readEmails(String contactId)
    {
        KmQueryBuilder q;
        q = new KmQueryBuilder(getContext());
        q.setUri(Email.CONTENT_URI);
        q.whereEqualString(Email.CONTACT_ID, contactId);
        return readEmails(q);
    }

    private KmList<KmContactEmail> readEmails(KmQueryBuilder q)
    {
        KmSqlCursor c = q.toCursor();
        try
        {
            return readEmails(c);
        }
        finally
        {
            c.closeSafely();
        }
    }

    private KmList<KmContactEmail> readEmails(KmSqlCursor c)
    {
        KmList<KmContactEmail> v = new KmList<KmContactEmail>();

        while ( c.next() )
            v.add(readEmail(c));

        return v;
    }

    private KmContactEmail readEmail(KmSqlCursor c)
    {
        String id = c.getStringAt(Email._ID);
        String displayName = c.getStringAt(Email.DISPLAY_NAME);
        String data = c.getStringAt(Email.DATA);

        String label = c.getStringAt(Email.LABEL);
        Integer typeCode = c.getIntegerAt(Email.TYPE);
        String typeName = Email.getTypeLabel(getResources(), typeCode, label).toString();

        KmContactEmail e;
        e = new KmContactEmail();
        e.setId(id);
        e.setDisplayName(displayName);
        e.setData(data);
        e.setTypeCode(typeCode);
        e.setTypeName(typeName);
        return e;
    }

    //##################################################
    //# address
    //##################################################

    public KmList<KmContactAddress> readAddresses(String contactId)
    {
        KmQueryBuilder q;
        q = new KmQueryBuilder(getContext());
        q.setUri(StructuredPostal.CONTENT_URI);
        q.whereEqualString(StructuredPostal.CONTACT_ID, contactId);
        return readAddresses(q);
    }

    private KmList<KmContactAddress> readAddresses(KmQueryBuilder q)
    {
        KmSqlCursor c = q.toCursor();
        try
        {
            return readAddresses(c);
        }
        finally
        {
            c.closeSafely();
        }
    }

    private KmList<KmContactAddress> readAddresses(KmSqlCursor c)
    {
        KmList<KmContactAddress> v = new KmList<KmContactAddress>();

        while ( c.next() )
            v.add(readAddress(c));

        return v;
    }

    private KmContactAddress readAddress(KmSqlCursor c)
    {
        String id = c.getStringAt(StructuredPostal._ID);
        String displayName = c.getStringAt(StructuredPostal.DISPLAY_NAME);

        String label = c.getStringAt(StructuredPostal.LABEL);
        Integer typeCode = c.getIntegerAt(StructuredPostal.TYPE);
        String typeName = StructuredPostal.getTypeLabel(getResources(), typeCode, label).toString();

        String poBox = c.getStringAt(StructuredPostal.POBOX);
        String street = c.getStringAt(StructuredPostal.STREET);
        String city = c.getStringAt(StructuredPostal.CITY);
        String region = c.getStringAt(StructuredPostal.REGION);
        String postCode = c.getStringAt(StructuredPostal.POSTCODE);
        String country = c.getStringAt(StructuredPostal.COUNTRY);

        KmContactAddress e;
        e = new KmContactAddress();
        e.setId(id);
        e.setDisplayName(displayName);
        e.setTypeCode(typeCode);
        e.setTypeName(typeName);

        e.setPoBox(poBox);
        e.setStreet(street);
        e.setCity(city);
        e.setRegion(region);
        e.setPostCode(postCode);
        e.setCountry(country);

        return e;
    }

    //##################################################
    //# support
    //##################################################

    private Context getContext()
    {
        return _context;
    }

    private Resources getResources()
    {
        return getContext().getResources();
    }

    //##################################################
    //# fill
    //##################################################

    public void fill(KmContact c)
    {
        fillPhones(c);
        fillEmails(c);
        fillAddresses(c);
    }

    public void fillPhones(KmContact e)
    {
        KmList<KmContactPhone> v = readPhones(e.getId());
        e.setPhones(v);
    }

    public void fillEmails(KmContact e)
    {
        KmList<KmContactEmail> v = readEmails(e.getId());
        e.setEmails(v);
    }

    public void fillAddresses(KmContact e)
    {
        KmList<KmContactAddress> v = readAddresses(e.getId());
        e.setAddresses(v);
    }

}
