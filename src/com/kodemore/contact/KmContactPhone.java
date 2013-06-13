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

public class KmContactPhone
{
    //##################################################
    //# variables
    //##################################################

    private String  _id;
    private String  _displayName;
    private String  _number;
    private Integer _typeCode;
    private String  _typeName;
    private boolean _primary;
    private boolean _superPrimary;

    //##################################################
    //# constructor
    //##################################################

    public KmContactPhone()
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

    public String getNumber()
    {
        return _number;
    }

    public void setNumber(String e)
    {
        _number = e;
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

    public boolean isPrimary()
    {
        return _primary;
    }

    public void setPrimary(boolean e)
    {
        _primary = e;
    }

    public boolean isSuperPrimary()
    {
        return _superPrimary;
    }

    public void setSuperPrimary(boolean e)
    {
        _superPrimary = e;
    }
}
