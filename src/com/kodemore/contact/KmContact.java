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

import com.kodemore.collection.KmList;

public class KmContact
{
    //##################################################
    //# variables
    //##################################################

    private String                   _id;
    private String                   _displayName;

    private KmList<KmContactEmail>   _emails;
    private KmList<KmContactPhone>   _phones;
    private KmList<KmContactAddress> _addresses;

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

    public KmList<KmContactEmail> getEmails()
    {
        return _emails;
    }

    public void setEmails(KmList<KmContactEmail> v)
    {
        _emails = v;
    }

    public KmList<KmContactPhone> getPhones()
    {
        return _phones;
    }

    public void setPhones(KmList<KmContactPhone> v)
    {
        _phones = v;
    }

    public KmList<KmContactAddress> getAddresses()
    {
        return _addresses;
    }

    public void setAddresses(KmList<KmContactAddress> v)
    {
        _addresses = v;
    }

    //##################################################
    //# abstract accessing
    //##################################################

    public String getPrimaryPhoneNumber()
    {
        KmContactPhone e = getPrimaryPhone();
        return e == null
            ? null
            : e.getNumber();
    }

    public KmContactPhone getPrimaryPhone()
    {
        KmList<KmContactPhone> v = getPhones();
        if ( v == null )
            return null;

        for ( KmContactPhone e : v )
            if ( e.isSuperPrimary() )
                return e;

        for ( KmContactPhone e : v )
            if ( e.isPrimary() )
                return e;

        return null;
    }

}
