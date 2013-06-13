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

import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import com.kodemore.collection.KmList;
import com.kodemore.io.KmIndentPrintWriter;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.Kmu;

/**
 * I represent an element parsed from an stf file.
 */
public class KmStfElement
    implements KmStfConstantsIF, KmConstantsIF
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The element's name.
     * Element names must start with a letter and cannot 
     * contain whitespace.
     */
    private String                 _name;

    /**
     * The line where this element occurred in the source.
     */
    private int                    _line;

    /**
     * The indentation level of this element.
     * Effectively the number of spaces that prefix the line.
     */
    private int                    _indent;

    /**
     * The attribute values associated with this element.
     */
    private KmList<KmStfAttribute> _attributes;

    /**
     * My parent element.  This is null for top level elements.
     */
    private KmStfElement           _parent;

    /**
     * The children.  This is a recursive composite.
     */
    private KmList<KmStfElement>   _children;

    //##################################################
    //# constructor
    //##################################################

    public KmStfElement()
    {
        _attributes = new KmList<KmStfAttribute>();
        _children = new KmList<KmStfElement>();
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
    //# line
    //##################################################

    public int getLine()
    {
        return _line;
    }

    public void setLine(int e)
    {
        _line = e;
    }

    public String getLocation()
    {
        return Kmu.format("%s:%s", getLine(), getIndent());
    }

    //##################################################
    //# indent
    //##################################################

    public int getIndent()
    {
        return _indent;
    }

    public void setIndent(int e)
    {
        _indent = e;
    }

    public boolean hasIndent(int e)
    {
        return _indent == e;
    }

    //##################################################
    //# attributes
    //##################################################

    public KmList<KmStfAttribute> getAttributes()
    {
        return _attributes;
    }

    public KmList<KmStfAttribute> getAttributes(String key)
    {
        KmList<KmStfAttribute> v = new KmList<KmStfAttribute>();

        for ( KmStfAttribute e : _attributes )
            if ( e.hasKey(key) )
                v.add(e);

        return v;

    }

    public KmStfAttribute getAttribute(String key)
    {
        return getAttributes(key).getFirstSafe();
    }

    public boolean hasAttribute(String key)
    {
        return getAttributes(key).isNotEmpty();
    }

    public boolean hasAttribute(String key, String value)
    {
        return hasAttribute(key) && Kmu.isEqual(getValue(key), value);
    }

    public void addAttribute(KmStfAttribute e)
    {
        _attributes.add(e);
    }

    public void addAttribute(String key, String value)
    {
        KmStfAttribute e;
        e = new KmStfAttribute();
        e.setKey(key);
        e.setValue(value);

        addAttribute(e);
    }

    //##################################################
    //# attribute keys
    //##################################################

    public KmList<String> getKeys()
    {
        KmList<String> v = new KmList<String>();

        for ( KmStfAttribute e : getAttributes() )
            v.add(e.getKey());

        return v;
    }

    //##################################################
    //# attribute values
    //##################################################

    public KmList<String> getValues(String key)
    {
        KmList<String> v = new KmList<String>();

        for ( KmStfAttribute attr : getAttributes(key) )
            v.add(attr.getValue());

        return v;
    }

    public String getValue(String key)
    {
        return getValues(key).getFirstSafe();
    }

    //##################################################
    //# parent
    //##################################################

    public KmStfElement getParent()
    {
        return _parent;
    }

    public void setParent(KmStfElement e)
    {
        _parent = e;
    }

    public boolean hasParent()
    {
        return _parent != null;
    }

    public boolean isRoot()
    {
        return !hasParent();
    }

    public int getDepth()
    {
        return isRoot()
            ? 0
            : getParent().getDepth() + 1;
    }

    public String getPath()
    {
        if ( isRoot() )
            return getName();

        return getParent().getPath() + SLASH + getName();
    }

    //##################################################
    //# children
    //##################################################

    public KmList<KmStfElement> getChildren()
    {
        return _children;
    }

    public KmList<KmStfElement> getChildren(String name)
    {
        KmList<KmStfElement> v = new KmList<KmStfElement>();

        for ( KmStfElement e : getChildren() )
            if ( e.hasName(name) )
                v.add(e);

        return v;
    }

    public KmStfElement getChild(String name)
    {
        return getChildren(name).getFirstSafe();
    }

    public KmList<KmStfElement> getChildrenWithAttribute(String name, String key, String value)
    {
        KmList<KmStfElement> v = new KmList<KmStfElement>();

        for ( KmStfElement e : getChildren() )
            if ( e.hasName(name) && e.hasAttribute(key, value) )
                v.add(e);

        return v;
    }

    public KmStfElement getChildWithAttribute(String name, String key, String value)
    {
        return getChildrenWithAttribute(name, key, value).getFirstSafe();
    }

    public boolean hasChildren()
    {
        return _children.isNotEmpty();
    }

    public void addChild(KmStfElement e)
    {
        e.setParent(this);
        _children.add(e);
    }

    public KmStfElement addChild()
    {
        KmStfElement e;
        e = new KmStfElement();

        addChild(e);

        return e;
    }

    public KmStfElement addChild(String name)
    {
        KmStfElement e;
        e = addChild();
        e.setName(name);
        return e;
    }

    //##################################################
    //# print
    //##################################################

    public String formatTree()
    {
        StringWriter out = new StringWriter();
        printTreeOn(out);
        return out.toString();
    }

    public void printTree()
    {
        printTreeOn(new OutputStreamWriter(System.out));
    }

    public void printTreeOn(Writer out)
    {
        int tab = 4;

        KmIndentPrintWriter pw;
        if ( out instanceof KmIndentPrintWriter )
            pw = (KmIndentPrintWriter)out;
        else
            pw = new KmIndentPrintWriter(out);

        pw.getIndentWriter().setIndentSpaces(4);
        _printTreeOn(pw, tab);
    }

    protected void _printTreeOn(KmIndentPrintWriter out, int tab)
    {
        out.println(getName());

        for ( KmStfAttribute e : getAttributes() )
            printAttribute(out, tab, e);

        out.println();
        out.indent();

        for ( KmStfElement e : getChildren() )
            e._printTreeOn(out, tab);

        out.undent();
        out.flush();
    }

    private void printAttribute(KmIndentPrintWriter out, int tab, KmStfAttribute a)
    {
        KmList<String> values = a.getValueLines();
        int max = getMaximumKeyLength();

        KmStringBuilder keyLine;
        keyLine = new KmStringBuilder();
        keyLine.print(ATTRIBUTE_PREFIX);
        keyLine.print(SPACE);

        if ( values.isEmpty() )
        {
            // null value

            keyLine.print(a.getKey());
            out.println(keyLine);
            return;
        }

        keyLine.print(Kmu.padRight(a.getKey(), max));
        keyLine.print(SPACE);
        keyLine.padToIncrement(tab);

        if ( values.isSingleton() )
        {
            // single line value

            keyLine.print(ATTRIBUTE_ASSIGN);
            keyLine.print(SPACE);
            keyLine.print(values.getFirst());
            out.println(keyLine);
            return;
        }

        // multi line value

        keyLine.print(ATTRIBUTE_CONTINUE);
        out.println(keyLine);

        out.indent();
        out.println(STRING_EQUALS);

        for ( String e : values )
            out.println(e);

        out.println(STRING_EQUALS);
        out.undent();
    }

    //##################################################
    //# support
    //##################################################

    private int getMaximumKeyLength()
    {
        int max = 0;

        KmList<KmStfAttribute> attrs = getAttributes();
        for ( KmStfAttribute attr : attrs )
            max = Kmu.max(max, attr.getKey().length());

        return max;
    }
}
