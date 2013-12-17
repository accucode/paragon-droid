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

public class KmContactNickname
{
    //##################################################
    //# variables
    //##################################################

    private String                _rawContactId;
    private String                _name;
    private KmContactNicknameType _type;

    /**
     * This holds the label of a custom nickname type
     */
    private String                _label;

    //##################################################
    //# constructors
    //##################################################

    public KmContactNickname()
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

    public String getName()
    {
        return _name;
    }

    public void setName(String e)
    {
        _name = e;
    }

    public KmContactNicknameType getType()
    {
        return _type;
    }

    public void setType(KmContactNicknameType e)
    {
        _type = e;
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

    public void setTypeDefault()
    {
        setType(KmContactNicknameType.DEFAULT);
    }

    public void SetTypeInitials()
    {
        setType(KmContactNicknameType.INITIALS);
    }

    public void setTypeMaidenName()
    {
        setType(KmContactNicknameType.MAIDEN_NAME);
    }

    public void setTypeOther()
    {
        setType(KmContactNicknameType.OTHER);
    }

    public void setTypeShortName()
    {
        setType(KmContactNicknameType.SHORT_NAME);
    }

    public void setTypeCustom()
    {
        setType(KmContactNicknameType.CUSTOM);
    }

    public void setTypeCustom(String label)
    {
        setTypeCustom();
        setLabel(label);
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

    public int getTypeCode()
    {
        return getType().getCode();
    }

    public void setTypeFromInt(Integer i)
    {
        if ( i == null )
        {
            setType(null);
            return;
        }

        for ( KmContactNicknameType e : KmContactNicknameType.values() )
            if ( i == e.getCode() )
            {
                setType(e);
                return;
            }

        setType(null);
    }
}
