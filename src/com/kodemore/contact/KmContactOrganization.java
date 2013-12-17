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

public class KmContactOrganization
{
    //##################################################
    //# variables
    //################################################## 

    private String                    _rawContactId;
    private String                    _company;
    private KmContactOrganizationType _type;

    /**
     * This holds the label of a custom email type
     */
    private String                    _label;

    private String                    _title;
    private String                    _department;
    private String                    _jobDescription;
    private String                    _symbol;
    private String                    _officeLocation;
    private String                    _phoneticName;

    /**
     * review_aaron: there is a column for phonetic name style in the organization table,
     * but there is not constant for it, and no information about what is is.  I'm leaving
     * it out for now.
     */

    //##################################################
    //# constructor
    //##################################################

    public KmContactOrganization()
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

    public String getCompany()
    {
        return _company;
    }

    public void setCompany(String e)
    {
        _company = e;
    }

    public KmContactOrganizationType getType()
    {
        return _type;
    }

    public void setType(KmContactOrganizationType e)
    {
        _type = e;
    }

    public boolean hasType()
    {
        return getType() != null;
    }

    public String getTypeName()
    {
        return hasType()
            ? getType().name()
            : null;
    }

    public String getLabel()
    {
        return _label;
    }

    public void setLabel(String e)
    {
        _label = e;
    }

    public String getTitle()
    {
        return _title;
    }

    public void setTitle(String title)
    {
        _title = title;
    }

    public String getDepartment()
    {
        return _department;
    }

    public void setDepartment(String department)
    {
        _department = department;
    }

    public String getJobDescription()
    {
        return _jobDescription;
    }

    public void setJobDescription(String jobDescription)
    {
        _jobDescription = jobDescription;
    }

    public String getSymbol()
    {
        return _symbol;
    }

    public void setSymbol(String symbol)
    {
        _symbol = symbol;
    }

    public String getOfficeLocation()
    {
        return _officeLocation;
    }

    public void setOfficeLocation(String officeLocation)
    {
        _officeLocation = officeLocation;
    }

    public String getPhoneticName()
    {
        return _phoneticName;
    }

    public void setPhoneticName(String phoneticName)
    {
        _phoneticName = phoneticName;
    }

    //##################################################
    //# convenience
    //##################################################

    public void setTypeOther()
    {
        setType(KmContactOrganizationType.OTHER);
    }

    public void setTypeWork()
    {
        setType(KmContactOrganizationType.WORK);
    }

    public void setTypeCustom()
    {
        setType(KmContactOrganizationType.CUSTOM);
    }

    public void setTypeCustom(String label)
    {
        setTypeCustom();
        setLabel(label);
    }

    public void setTypeFromInt(Integer i)
    {
        if ( i == null )
        {
            setType(null);
            return;
        }

        for ( KmContactOrganizationType e : KmContactOrganizationType.values() )
            if ( i == e.getCode() )
            {
                setType(e);
                return;
            }

        setType(null);
    }
}
