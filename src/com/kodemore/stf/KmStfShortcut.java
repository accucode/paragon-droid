/*
  Copyright (c) 2005-2011 www.kodemore.com

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

package com.kodemore.stf;

import com.kodemore.utility.Kmu;

/**
 * I am used to define element argument shortcuts.
 * Shortcuts convert element arguments into attribute values.
 */
public class KmStfShortcut
{
    //##################################################
    //# variables
    //##################################################

    /**
     * If set, then this shortcut will only apply when
     * parsing the attributes of an element with a matching
     * name.
     */
    private String  _elementName;

    /**
     * If set, then this shortcut will only apply when parsing
     * an element that matches this path.  Patterns should be in 
     * the form:
     *      a/@/b/@@/c
     * where *'s are used in place of @'.  A single * indicates 
     * exactly one wildcard path element.  A double ** indicates
     * 0-to-many wildcard path elements.
     */
    private String  _elementPath;

    /**
     * If defined, the shortcut will only match arguments
     * that start with this prefix.  If null, then match
     * any arguments not matched by another shortcut.  The
     * prefix is typically a symbol (e.g.: @, #, $) to avoid
     * confusion, but this is not strictly required.
     */
    private String  _argumentPrefix;

    /**
     * The attribute key the argument will be applied to.
     */
    private String  _attributeKey;

    /**
     * If true, multiple arguments will be appended to the 
     * same attribute separated by a space. If false (the default), 
     * multiple arguments will added as multiple attributes.
     */
    private boolean _appendMode;

    //##################################################
    //# accessing
    //##################################################

    public String getElementName()
    {
        return _elementName;
    }

    public void setElementName(String e)
    {
        _elementName = e;
    }

    public boolean hasElementName()
    {
        return Kmu.hasValue(_elementName);
    }

    public boolean hasElementName(String e)
    {
        return Kmu.isEqual(_elementName, e);
    }

    public String getElementPath()
    {
        return _elementPath;
    }

    public void setElementPath(String e)
    {
        _elementPath = e;
    }

    public boolean hasElementPath()
    {
        return Kmu.hasValue(_elementPath);
    }

    public String getArgumentPrefix()
    {
        return _argumentPrefix;
    }

    public void setArgumentPrefix(String e)
    {
        _argumentPrefix = e;
    }

    public boolean hasArgumentPrefix()
    {
        return Kmu.hasValue(_argumentPrefix);
    }

    public boolean isDefaultArgument()
    {
        return !hasArgumentPrefix();
    }

    public String getAttributeKey()
    {
        return _attributeKey;
    }

    public void setAttributeKey(String e)
    {
        _attributeKey = e;
    }

    public boolean getAppendMode()
    {
        return _appendMode;
    }

    public void setAppendMode(boolean e)
    {
        _appendMode = e;
    }

    public void setAppendMode()
    {
        setAppendMode(true);
    }

    //##################################################
    //# matches
    //##################################################

    public boolean matches(KmStfElement e, String attributeKey)
    {
        if ( !matchesElementName(e) )
            return false;

        if ( !matchesElementPath(e) )
            return false;

        if ( !matchesPrefix(attributeKey) )
            return false;

        return true;
    }

    private boolean matchesElementName(KmStfElement e)
    {
        if ( !hasElementName() )
            return true;

        return hasElementName(e.getName());
    }

    private boolean matchesElementPath(KmStfElement e)
    {
        if ( !hasElementPath() )
            return true;

        return Kmu.matchesPath(e.getPath(), getElementPath());
    }

    private boolean matchesPrefix(String attributeKey)
    {
        if ( !hasArgumentPrefix() )
            return true;

        return attributeKey.startsWith(getArgumentPrefix());
    }

    //##################################################
    //# apply
    //##################################################

    public boolean applyTo(KmStfElement e, String arg)
    {
        if ( !matches(e, arg) )
            return false;

        String key = getAttributeKey();

        String value = hasArgumentPrefix()
            ? Kmu.removePrefix(arg, getArgumentPrefix())
            : arg;

        if ( getAppendMode() && e.hasAttribute(key) )
            e.getAttribute(key).appendValue(" " + value);
        else
            e.addAttribute(key, value);

        return true;
    }
}
