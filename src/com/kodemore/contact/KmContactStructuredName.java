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

public class KmContactStructuredName
{
    //##################################################
    //# variables
    //##################################################

    private String _rawContactId;

    private String _displayName;
    private String _prefix;
    private String _firstName;
    private String _middleName;
    private String _lastName;
    private String _suffix;

    private String _phoneticFirstName;
    private String _phoneticMiddleName;
    private String _phoneticLastName;

    //##################################################
    //# constructors
    //##################################################

    public KmContactStructuredName()
    {
        // none
    }

    //##################################################
    //# accessing
    //##################################################

    public String getRawContactId()
    {
        return _rawContactId;
    }

    public void setRawContactId(String e)
    {
        _rawContactId = e;
    }

    public String getDisplayName()
    {
        return _displayName;
    }

    public void setDisplayName(String e)
    {
        _displayName = e;
    }

    public String getPrefix()
    {
        return _prefix;
    }

    public void setPrefix(String e)
    {
        _prefix = e;
    }

    public String getFirstName()
    {
        return _firstName;
    }

    public void setFirstName(String e)
    {
        _firstName = e;
    }

    public String getMiddleName()
    {
        return _middleName;
    }

    public void setMiddleName(String e)
    {
        _middleName = e;
    }

    public String getLastName()
    {
        return _lastName;
    }

    public void setLastName(String e)
    {
        _lastName = e;
    }

    public String getSuffix()
    {
        return _suffix;
    }

    public void setSuffix(String e)
    {
        _suffix = e;
    }

    public String getPhoneticFirstName()
    {
        return _phoneticFirstName;
    }

    public void setPhoneticFirstName(String e)
    {
        _phoneticFirstName = e;
    }

    public String getPhoneticMiddleName()
    {
        return _phoneticMiddleName;
    }

    public void setPhoneticMiddleName(String e)
    {
        _phoneticMiddleName = e;
    }

    public String getPhoneticLastName()
    {
        return _phoneticLastName;
    }

    public void setPhoneticLastName(String e)
    {
        _phoneticLastName = e;
    }
}
