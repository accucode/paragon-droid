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

package com.kodemore.html;

import com.kodemore.collection.KmList;
import com.kodemore.collection.KmMap;

/**
 * I supply some convenience methods for building simple html.
 */
public class KmHtmlNode
{
    //##################################################
    //# instance creation
    //##################################################

    public static KmHtmlNode createHtml()
    {
        KmHtmlNode e;
        e = new KmHtmlNode();
        e.setTag("html");
        return e;
    }

    //##################################################
    //# variables
    //##################################################

    private String               _tag;
    private boolean              _endTag;
    private KmMap<String,String> _attributes;
    private KmList<Object>       _children;

    //##################################################
    //# constructor
    //##################################################

    public KmHtmlNode()
    {
        _tag = null;
        _endTag = true;
        _attributes = new KmMap<String,String>();
        _children = new KmList<Object>();
    }

    //##################################################
    //# accessing
    //##################################################

    public String getTag()
    {
        return _tag;
    }

    public void setTag(String e)
    {
        _tag = e;
    }

    public boolean hasTag()
    {
        return _tag != null;
    }

    public boolean getEndTag()
    {
        return _endTag;
    }

    public void setEndTag(boolean e)
    {
        _endTag = e;
    }

    public void noEndTag()
    {
        setEndTag(false);
    }

    public KmMap<String,String> getAttributes()
    {
        return _attributes;
    }

    public void setAttribute(String key, String value)
    {
        _attributes.put(key, value);
    }

    public void setAttribute(String key)
    {
        setAttribute(key, null);
    }

    public boolean hasAttributes()
    {
        return _attributes.isNotEmpty();
    }

    public KmList<Object> getChildren()
    {
        return _children;
    }

    public KmHtmlNode addTag(String tag)
    {
        KmHtmlNode e = new KmHtmlNode();
        e.setTag(tag);
        _children.add(e);
        return e;
    }

    public void addText(Object text)
    {
        if ( text == null )
            return;

        _children.add(text.toString());
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        KmHtmlBuilder out;
        out = new KmHtmlBuilder();

        printOn(out);

        return out.toString();
    }

    public void printOn(KmHtmlBuilder out)
    {
        if ( hasTag() )
        {
            out.open(_tag);
            for ( String k : _attributes.keySet() )
                out.printAttribute(k, _attributes.get(k));
            out.close();
        }

        for ( Object e : _children )
            if ( e instanceof KmHtmlNode )
                ((KmHtmlNode)e).printOn(out);
            else
                out.print(e);

        if ( hasTag() && _endTag )
            out.end(_tag);
    }

    //##################################################
    //# convenience
    //##################################################

    public void addBreaks(int n)
    {
        for ( int i = 0; i < n; i++ )
            addBreak();
    }

    public void addBreak()
    {
        addTag("br").noEndTag();
    }

    public KmHtmlNode addHiddenField(String name, String value)
    {
        KmHtmlNode e = addTag("input");
        e.noEndTag();
        e.setAttribute("type", "hidden");
        e.setAttribute("name", name);
        e.setAttribute("value", value);
        return e;
    }

    public KmHtmlNode addHeader(int level, String text)
    {
        String tag = "h" + level;
        KmHtmlNode e = addTag(tag);
        e.addText(text);
        return e;
    }

    public KmHtmlNode addTable()
    {
        return addTag("table");
    }

    public KmHtmlNode addRow()
    {
        return addTag("tr");
    }

    public KmHtmlNode addRow(Object... values)
    {
        KmHtmlNode e = addRow();
        e.addCells(values);
        return e;
    }

    public void addCells(Object... values)
    {
        for ( Object e : values )
            addTag("td").addText(e);
    }

    public KmHtmlNode addBody()
    {
        return addTag("body");
    }

    public KmHtmlNode addForm()
    {
        return addTag("form");
    }
}
