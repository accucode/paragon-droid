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

import com.kodemore.collection.*;
import com.kodemore.utility.*;

/**
 * I provide the basis for chaining css.  I delegate
 * responsibility for storing the underlying value
 * to a value holder.
 * 
 * I, and my subclasses, are intended to be used with
 * method "chaining."  
 * 
 *      Given: e.css() returns an ScCssWrapper
 *      Then: e.css().add("footer").remove("pad").toggle("bold");
 *
 * In most cases, subclasses with specialized methods should
 * be used.  The subclasses may be auto-generated to match
 * the selectors found in .css files.  So given a css subclass
 * that contains class selectors for "footer", "pad" and "bold"
 * you might...
 * 
 *      e.css().footer().pad().bold();
 *      e.css().add().footer().pad().bold();
 *      e.css().add().footer().remove().pad().toggle().bold();
 *      
 * General pattern... 
 * 
 *      Calling the add, remove, and toggle methods WITH a 
 *      parameter will mutate the underlying css selectors 
 *      appropriately.  These methods do NOT change the 
 *      current mode.  
 *        
 *      Calling the add, remove, and toggle methods WITHOUT 
 *      a parameter change the mode, but do NOT mutate the
 *      underlying value.  All subsequent calls to 'apply'
 *      reflect the current mode. 
 */
public class KmCssBuilder
{
    //##################################################
    //# constants
    //##################################################

    private static final String SEPARATOR = KmConstantsIF.SPACE;

    private enum Mode
    {
        ADD,
        REMOVE,
        TOGGLE;
    }

    //##################################################
    //# variables
    //##################################################

    private Mode                    _mode;
    private KmValueHolderIF<String> _holder;

    //##################################################
    //# constructor
    //##################################################

    public KmCssBuilder()
    {
        this(new KmValueHolder<String>());
    }

    public KmCssBuilder(KmValueHolderIF<String> holder)
    {
        _mode = Mode.ADD;
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

    /**
     * Return a copy.  The copy will have the same value as the 
     * original, but will be detached from the original value HOLDER.
     */
    public KmCssBuilder getCopy()
    {
        KmCssBuilder copy;
        copy = new KmCssBuilder();
        copy.setValue(getValue());
        return copy;
    }

    //##################################################
    //# abstract accessing
    //##################################################

    public KmCssBuilder clear()
    {
        _holder.setValue(null);
        return this;
    }

    public KmCssBuilder add(String e)
    {
        if ( Kmu.isEmpty(e) )
            return this;

        KmList<String> v = getSelectors();

        if ( v.addDistinct(e) )
            setValue(v.format(SEPARATOR));

        return this;
    }

    public KmCssBuilder add(String prefix, String part, String flavor)
    {
        return add(formatComposite(prefix, part, flavor));
    }

    public KmCssBuilder remove(String e)
    {
        if ( Kmu.isEmpty(e) )
            return this;

        KmList<String> v = getSelectors();

        if ( v.remove(e) )
            setValue(v.format(SEPARATOR));

        return this;
    }

    public KmCssBuilder remove(String prefix, String part, String flavor)
    {
        return remove(formatComposite(prefix, part, flavor));
    }

    public KmCssBuilder toggle(String e)
    {
        if ( contains(e) )
            return remove(e);

        return add(e);
    }

    public KmCssBuilder toggle(String prefix, String part, String flavor)
    {
        return toggle(formatComposite(prefix, part, flavor));
    }

    //##################################################
    //# accessing - mode
    //##################################################

    public KmCssBuilder add()
    {
        _mode = Mode.ADD;
        return this;
    }

    public KmCssBuilder remove()
    {
        _mode = Mode.REMOVE;
        return this;
    }

    public KmCssBuilder toggle()
    {
        _mode = Mode.TOGGLE;
        return this;
    }

    //##################################################
    //# apply
    //##################################################

    public KmCssBuilder apply(String e)
    {
        switch ( _mode )
        {
            case ADD:
                return add(e);

            case REMOVE:
                return remove(e);

            case TOGGLE:
                return toggle(e);
        }

        throw Kmu.createFatal("Unhandled mode");
    }

    public KmCssBuilder apply(String prefix, String part, String flavor)
    {
        return apply(formatComposite(prefix, part, flavor));
    }

    //##################################################
    //# convenience
    //##################################################

    public KmList<String> getSelectors()
    {
        if ( Kmu.isEmpty(getValue()) )
            return new KmList<String>();

        return Kmu.tokenize(getValue(), KmConstantsIF.CHAR_SPACE);
    }

    public boolean contains(String e)
    {
        return getSelectors().contains(e);
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

    //##################################################
    //# support
    //##################################################

    private String formatComposite(String prefix, String part, String flavor)
    {
        return Kmu.isEmpty(flavor)
            ? Kmu.format("%s-%s", prefix, part)
            : Kmu.format("%s-%s-%s", prefix, part, flavor);
    }

}
