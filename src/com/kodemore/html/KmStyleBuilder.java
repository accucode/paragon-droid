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

import java.util.Iterator;
import java.util.List;

import com.kodemore.collection.KmList;
import com.kodemore.string.KmStringAssociation;
import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.KmValueHolder;
import com.kodemore.utility.KmValueHolderIF;
import com.kodemore.utility.Kmu;

/**
 * I am used to compose inline html styles.  As in the quoted value...
 *      <div style="...">
 */
public class KmStyleBuilder
{
    //##################################################
    //# constants
    //##################################################

    private static final int        DEFAULT_GAP = 10;

    //##################################################
    //# variables
    //##################################################

    private KmValueHolderIF<String> _holder;

    //##################################################
    //# constructor
    //##################################################

    public KmStyleBuilder()
    {
        _holder = new KmValueHolder<String>();
    }

    public KmStyleBuilder(KmValueHolderIF<String> holder)
    {
        _holder = holder;
    }

    //##################################################
    //# accessing
    //##################################################

    public String getValue()
    {
        return _holder.getValue();
    }

    public void setValue(String e)
    {
        _holder.setValue(e);
    }

    public void clearValue()
    {
        setValue(null);
    }

    /**
     * Return a copy.  The copy will have the same value as the 
     * original, but will be detached from the original value HOLDER.
     */
    public KmStyleBuilder getCopy()
    {
        KmStyleBuilder e;
        e = new KmStyleBuilder();
        e.setValue(getValue());
        return e;
    }

    //##################################################
    //# values
    //##################################################

    public void addValue(String attr)
    {
        setValue(join(attr));
    }

    public void addValue(String attr, String value)
    {
        setValue(join(attr, value));
    }

    public void addValue(String attr, Integer value)
    {
        setValue(join(attr, value));
    }

    public void addValue(String attr, Integer value, String unit)
    {
        setValue(join(attr, value, unit));
    }

    public void addValue(KmStringAssociation e)
    {
        if ( e == null )
            return;

        addValue(e.getKey(), e.getValue());
    }

    public void addValues(List<KmStringAssociation> v)
    {
        if ( v == null )
            return;

        for ( KmStringAssociation e : v )
            addValue(e);
    }

    /**
     * Remove all attributes with the specified key.
     */
    public void removeValue(String key)
    {
        boolean found = false;
        KmList<KmStringAssociation> v = getAssociations();

        Iterator<KmStringAssociation> i = v.iterator();
        while ( i.hasNext() )
            if ( i.next().hasKey(key) )
            {
                i.remove();
                found = true;
            }

        if ( found )
        {
            clearValue();
            addValues(v);
        }
    }

    public KmList<KmStringAssociation> getAssociations()
    {
        KmList<KmStringAssociation> v = new KmList<KmStringAssociation>();

        KmList<String> tokens = Kmu.tokenize(getValue(), KmConstantsIF.CHAR_SEMICOLON);
        for ( String token : tokens )
        {
            String s = token.trim();
            if ( s.length() == 0 )
                continue;

            KmStringAssociation a;
            a = new KmStringAssociation();

            int i = token.indexOf(KmConstantsIF.CHAR_COLON);
            if ( i < 0 )
            {
                a.setKey(s);
                v.add(a);
                continue;
            }

            String key = s.substring(0, i).trim();
            String value = s.substring(i + 1).trim();

            a.setKey(key);
            a.setValue(value);
            v.add(a);
        }

        return v;
    }

    //##################################################
    //# formatting
    //##################################################

    /**
     * Format a string suitable for output to an html stream.
     * 
     * I currently just return my value.  But use of this 
     * method will insulate clients from changes to my internal
     * implementation.
     */
    public String formatHtmlStyle()
    {
        return getValue();
    }

    //##################################################
    //# float
    //##################################################

    public KmStyleBuilder floatLeft()
    {
        addValue("float", "left");

        return this;
    }

    public KmStyleBuilder floatRight()
    {
        addValue("float", "right");

        return this;
    }

    //##################################################
    //# overflow
    //##################################################

    public KmStyleBuilder auto()
    {
        addValue("overflow", "auto");

        return this;
    }

    //##################################################
    //# width
    //##################################################

    public KmStyleBuilder width(Integer e)
    {
        if ( e != null )
            addValue("width", e, "px");

        return this;
    }

    public KmStyleBuilder widthFull()
    {
        addValue("width", 100, "%");

        return this;
    }

    public KmStyleBuilder minWidth(Integer e)
    {
        if ( e != null )
            addValue("min-width", e, "px");

        return this;
    }

    public KmStyleBuilder maxWidth(Integer e)
    {
        if ( e != null )
            addValue("max-width", e, "px");

        return this;
    }

    //##################################################
    //# height
    //##################################################

    public KmStyleBuilder height(Integer e)
    {
        if ( e != null )
            addValue("height", e, "px");

        return this;
    }

    //##################################################
    //# margin
    //##################################################

    public KmStyleBuilder margin()
    {
        return margin(DEFAULT_GAP);
    }

    public KmStyleBuilder margin(Integer e)
    {
        if ( e != null )
            addValue("margin", e, "px");

        return this;
    }

    public KmStyleBuilder marginTop()
    {
        return marginTop(DEFAULT_GAP);
    }

    public KmStyleBuilder marginTop(Integer e)
    {
        if ( e != null )
            addValue("margin-top", e, "px");

        return this;
    }

    public KmStyleBuilder marginRight(Integer e)
    {
        if ( e != null )
            addValue("margin-right", e, "px");

        return this;
    }

    public KmStyleBuilder marginBottom(Integer e)
    {
        if ( e != null )
            addValue("margin-bottom", e, "px");

        return this;
    }

    public KmStyleBuilder marginLeft(Integer e)
    {
        if ( e != null )
            addValue("margin-left", e, "px");

        return this;
    }

    public KmStyleBuilder marginCenter()
    {
        addValue("margin-left", "auto");
        addValue("margin-right", "auto");

        return this;
    }

    //##################################################
    //# padding
    //##################################################

    public KmStyleBuilder pad()
    {
        return pad(DEFAULT_GAP);
    }

    public KmStyleBuilder pad(Integer e)
    {
        if ( e != null )
            addValue("padding", e, "px");

        return this;
    }

    public KmStyleBuilder padTop()
    {
        return padTop(DEFAULT_GAP);
    }

    public KmStyleBuilder padTop(Integer e)
    {
        if ( e != null )
            addValue("padding-top", e, "px");

        return this;
    }

    public KmStyleBuilder padRight(Integer e)
    {
        if ( e != null )
            addValue("padding-right", e, "px");

        return this;
    }

    public KmStyleBuilder padBottom(Integer e)
    {
        if ( e != null )
            addValue("padding-bottom", e, "px");

        return this;
    }

    public KmStyleBuilder padLeft(Integer e)
    {
        if ( e != null )
            addValue("padding-left", e, "px");

        return this;
    }

    //##################################################
    //# text align
    //##################################################

    public KmStyleBuilder alignLeft()
    {
        addValue("text-align", "left");
        return this;
    }

    public KmStyleBuilder alignRight()
    {
        addValue("text-align", "right");
        return this;
    }

    public KmStyleBuilder alignCenter()
    {
        addValue("text-align", "center");
        return this;
    }

    //##################################################
    //# font
    //##################################################

    public KmStyleBuilder bold()
    {
        addValue("font-weight", "bold");
        return this;
    }

    public KmStyleBuilder italic()
    {
        addValue("font-style", "italic");
        return this;
    }

    public KmStyleBuilder strike()
    {
        addValue("text-decoration", "line-through");
        return this;
    }

    public KmStyleBuilder size(int px)
    {
        addValue("font-size", px, "px");
        return this;
    }

    public KmStyleBuilder sans()
    {
        addValue("font-family", "sans-serif");
        return this;
    }

    public KmStyleBuilder serif()
    {
        addValue("font-family", "serif");
        return this;
    }

    public KmStyleBuilder mono()
    {
        addValue("font-family", "monospace");
        return this;
    }

    //##################################################
    //# display (show/hide)
    //##################################################

    public KmStyleBuilder show(boolean e)
    {
        return e
            ? show()
            : hide();
    }

    public KmStyleBuilder show()
    {
        removeValue("display");
        return this;
    }

    public KmStyleBuilder hide()
    {
        removeValue("display");
        addValue("display", "none");
        return this;
    }

    //##################################################
    //# border
    //##################################################

    public KmStyleBuilder noBorder()
    {
        removeValue("border");
        addValue("border", "none");
        return this;
    }

    //##################################################
    //# support
    //##################################################

    private String join(String attr)
    {
        return Kmu.joinHtmlStyle(getValue(), attr);
    }

    private String join(String attr, String value)
    {
        return Kmu.joinHtmlStyle(getValue(), attr, value);
    }

    private String join(String attr, Integer value)
    {
        return Kmu.joinHtmlStyle(getValue(), attr, value);
    }

    private String join(String attr, Integer value, String unit)
    {
        return Kmu.joinHtmlStyle(getValue(), attr, value, unit);
    }

    //##################################################
    //# display
    //##################################################

    /**
     * This method should return getValue() and may be safely
     * used in place of getValue().
     */
    @Override
    public final String toString()
    {
        return getValue();
    }
}
