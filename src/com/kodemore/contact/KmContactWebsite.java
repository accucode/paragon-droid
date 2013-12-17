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

public class KmContactWebsite
{
    //##################################################
    //# variables
    //################################################## 

    private String               _rawContactId;
    private String               _url;
    private KmContactWebsiteType _type;

    /**
     * This holds the label of a custom email type
     */
    private String               _label;

    //##################################################
    //# constructor
    //##################################################

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

    public String getUrl()
    {
        return _url;
    }

    public void setUrl(String e)
    {
        _url = e;
    }

    public KmContactWebsiteType getType()
    {
        return _type;
    }

    public String getTypeName()
    {
        return hasType()
            ? getType().name()
            : null;
    }

    public void setType(KmContactWebsiteType e)
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
    //# convenience
    //##################################################

    public void setTypeHome()
    {
        setType(KmContactWebsiteType.HOME);
    }

    public void setTypeOther()
    {
        setType(KmContactWebsiteType.OTHER);
    }

    public void setTypeWork()
    {
        setType(KmContactWebsiteType.WORK);
    }

    public void setTypeCustom()
    {
        setType(KmContactWebsiteType.CUSTOM);
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

        for ( KmContactWebsiteType e : KmContactWebsiteType.values() )
            if ( i == e.getCode() )
            {
                setType(e);
                return;
            }

        setType(null);
    }
}
