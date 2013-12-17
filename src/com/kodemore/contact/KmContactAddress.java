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

public class KmContactAddress
{
    //##################################################
    //# variables
    //##################################################

    private String               _rawContactId;
    private boolean              _primary;
    private boolean              _superPrimary;
    private KmContactAddressType _type;

    /**
     * This holds the label of a custom address type
     */
    private String               _label;

    private String               _formattedAddress;

    private String               _poBox;
    private String               _street;
    private String               _city;
    private String               _region;
    private String               _postCode;
    private String               _country;

    //##################################################
    //# constructor
    //##################################################

    public KmContactAddress()
    {
        // none
    }

    //##################################################
    //# accessing (general)
    //##################################################

    public String getRawContactId()
    {
        return _rawContactId;
    }

    public void setRawContactId(String e)
    {
        _rawContactId = e;
    }

    public boolean getPrimary()
    {
        return _primary;
    }

    public void setPrimary(boolean e)
    {
        _primary = e;
    }

    public boolean isPrimary()
    {
        return getPrimary();
    }

    public boolean getSuperPrimary()
    {
        return _superPrimary;
    }

    public void setSuperPrimary(boolean e)
    {
        _superPrimary = e;
    }

    public boolean isSuperPrimary()
    {
        return getSuperPrimary();
    }

    public KmContactAddressType getType()
    {
        return _type;
    }

    public String getTypeName()
    {
        return hasType()
            ? getType().name()
            : null;
    }

    public void setType(KmContactAddressType e)
    {
        _type = e;
    }

    public boolean hasType()
    {
        return getType() != null;
    }

    public String getLabel()
    {
        return _label;
    }

    public void setLabel(String e)
    {
        _label = e;
    }

    //##################################################
    //# accessing (fields)
    //##################################################

    public String getFormattedAddress()
    {
        return _formattedAddress;
    }

    public void setFormattedAddress(String e)
    {
        _formattedAddress = e;
    }

    public String getPoBox()
    {
        return _poBox;
    }

    public void setPoBox(String e)
    {
        _poBox = e;
    }

    public String getStreet()
    {
        return _street;
    }

    public void setStreet(String e)
    {
        _street = e;
    }

    public String getCity()
    {
        return _city;
    }

    public void setCity(String e)
    {
        _city = e;
    }

    public String getRegion()
    {
        return _region;
    }

    public String getState()
    {
        return getRegion();
    }

    public void setRegion(String e)
    {
        _region = e;
    }

    public String getPostCode()
    {
        return _postCode;
    }

    public void setPostCode(String e)
    {
        _postCode = e;
    }

    public String getCountry()
    {
        return _country;
    }

    public void setCountry(String e)
    {
        _country = e;
    }

    //##################################################
    //# convenience
    //##################################################

    public void setTypeHome()
    {
        setType(KmContactAddressType.HOME);
    }

    public void setTypeOther()
    {
        setType(KmContactAddressType.OTHER);
    }

    public void setTypeWork()
    {
        setType(KmContactAddressType.WORK);
    }

    public void setTypeCustom()
    {
        setType(KmContactAddressType.CUSTOM);
    }

    public void setTypeCustom(String label)
    {
        setTypeCustom();
        setLabel(label);
    }

    /**
     * Convenience method to set this value directly from the android's contacts data table
     */
    public void setTypeFromInt(Integer i)
    {
        if ( i == null )
        {
            setType(null);
            return;
        }

        for ( KmContactAddressType e : KmContactAddressType.values() )
            if ( i == e.getCode() )
            {
                setType(e);
                return;
            }

        setType(null);
    }

    /**
     * Convenience method to set this value directly from the android's contacts contract
     * table.
     * 
     * The android table stores this value as an Integer, if set, non-0 means true.   
     */
    public void setPrimaryFromInt(Integer e)
    {
        boolean b = e != null && e != 0;
        setPrimary(b);
    }

    /**
     * Convenience method to set this value directly from the android's contacts contract
     * table.
     * 
     * The android table stores this value as an Integer, if set, non-0 means true.   
     */
    public void setSuperPrimaryFromInt(Integer e)
    {
        boolean b = e != null && e != 0;
        setSuperPrimary(b);
    }
}
