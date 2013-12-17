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

public class KmContactIm
{
    //##################################################
    //# variables
    //##################################################

    private String              _data;
    private KmContactImType     _type;

    /**
     * This holds the label of a custom IM type
     */
    private String              _label;
    private KmContactImProtocol _protocol;

    /**
     * This holds the label of a custom IM protocol
     */
    private String              _customProtocol;

    //##################################################
    //# constructor
    //##################################################

    public KmContactIm()
    {
        //none
    }

    //##################################################
    //# accessing
    //##################################################

    public String getData()
    {
        return _data;
    }

    public void setData(String e)
    {
        _data = e;
    }

    public KmContactImType getType()
    {
        return _type;
    }

    public void setType(KmContactImType e)
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

    public KmContactImProtocol getProtocol()
    {
        return _protocol;
    }

    public void setProtocol(KmContactImProtocol e)
    {
        _protocol = e;
    }

    public boolean hasProtocol()
    {
        return getProtocol() != null;
    }

    public String getProtocolName()
    {
        return hasProtocol()
            ? getProtocol().name()
            : null;
    }

    public String getCustomProtocol()
    {
        return _customProtocol;
    }

    public void setCustomProtocol(String e)
    {
        _customProtocol = e;
    }

    //##################################################
    //# conveniece
    //##################################################

    /**
     * Convenience method to set this value directly from the Android contact data table
     */
    public void setTypeFromInt(Integer i)
    {
        if ( i == null )
        {
            setType(null);
            return;
        }

        for ( KmContactImType e : KmContactImType.values() )
            if ( i == e.getCode() )
            {
                setType(e);
                return;
            }

        setType(null);
    }

    /**
     * Convenience method to set this value directly from the Android contact data table
     */
    public void setProtocolFromInt(Integer i)
    {
        if ( i == null )
        {
            setType(null);
            return;
        }

        for ( KmContactImProtocol e : KmContactImProtocol.values() )
            if ( i == e.getCode() )
            {
                setProtocol(e);
                return;
            }

        setType(null);
    }
}
