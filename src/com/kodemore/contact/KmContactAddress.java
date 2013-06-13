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

    private String  _id;
    private String  _displayName;
    private Integer _typeCode;
    private String  _typeName;

    private String  _poBox;
    private String  _street;
    private String  _city;
    private String  _region;
    private String  _postCode;
    private String  _country;

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

    public Integer getTypeCode()
    {
        return _typeCode;
    }

    public void setTypeCode(Integer e)
    {
        _typeCode = e;
    }

    public String getTypeName()
    {
        return _typeName;
    }

    public void setTypeName(String e)
    {
        _typeName = e;
    }

    //##################################################
    //# accessing (fields)
    //##################################################

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
}
