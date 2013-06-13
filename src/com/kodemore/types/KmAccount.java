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

package com.kodemore.types;

import java.io.Serializable;

import com.kodemore.utility.KmAccountManager;
import com.kodemore.utility.Kmu;

public class KmAccount
    implements Serializable
{
    //##################################################
    //# static 
    //##################################################

    public static final KmAccountManager tools = new KmAccountManager();

    //##################################################
    //# variables
    //##################################################

    private final String                 _uid;
    private String                       _type;
    private String                       _name;

    //##################################################
    //# constructor
    //##################################################

    public KmAccount()
    {
        _uid = Kmu.newUid();
    }

    //##################################################
    //# uid
    //##################################################

    public String getUid()
    {
        return _uid;
    }

    public boolean hasUid(String e)
    {
        return _uid.equals(e);
    }

    //##################################################
    //# type
    //##################################################

    public String getType()
    {
        return _type;
    }

    public void setType(String e)
    {
        _type = e;
    }

    public boolean hasType()
    {
        return Kmu.hasValue(_type);
    }

    public boolean hasType(String e)
    {
        return Kmu.isEqual(_type, e);
    }

    //##################################################
    //# name
    //##################################################

    public String getName()
    {
        return _name;
    }

    public void setName(String e)
    {
        _name = e;
    }

    public boolean hasName()
    {
        return Kmu.hasValue(_name);
    }

    public boolean hasName(String e)
    {
        return Kmu.isEqual(_name, e);
    }

    //##################################################
    //# equals
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        if ( e instanceof KmAccount )
            return ((KmAccount)e).hasUid(_uid);

        return false;
    }

    @Override
    public int hashCode()
    {
        return _uid.hashCode();
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return Kmu.format("User-" + getUid());
    }

    public String getDisplayString()
    {
        return Kmu.join(getName(), getType(), " ");
    }

    public boolean hasDisplayStringSubstring(String e)
    {
        String display = getDisplayString().toLowerCase();
        e = e.toLowerCase();

        return display.contains(e);
    }
}
