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

package com.kodemore.string;

import com.kodemore.utility.Kmu;

/**
 * I am used to read pieces of a string.
 * 
 * E.g.: 
 * 
 * KmStringReader r = new KmStringReader("2007-01-31");
 * int year = r.readInteger(4);
 * r.skip();
 * int month = r.readInteger(2); 
 * r.skip();
 * int day = r.readInteger(2); 
 * 
 */
public class KmStringReader
{
    //##################################################
    //# variables
    //##################################################

    private String _source;
    private int    _index;

    //##################################################
    //# constructor
    //##################################################

    public KmStringReader(String e)
    {
        _source = e;
        _index = 0;
    }

    //##################################################
    //# accessing
    //##################################################

    public char readCharacter()
    {
        checkIndex(1);
        char c = _source.charAt(_index);
        _index++;
        return c;
    }

    public String readString(int n)
    {
        checkIndex(n);
        String s = _source.substring(_index, _index + n);
        _index += n;
        return s;
    }

    public Integer readInteger(int n)
    {
        String s = readString(n);
        return Kmu.parseInteger(s);
    }

    public void skip()
    {
        skip(1);
    }

    public void skip(int n)
    {
        checkIndex(n);
        _index += n;
    }

    //##################################################
    //# private 
    //##################################################

    private void checkIndex(int i)
    {
        if ( _index + i > _source.length() )
            Kmu.fatal("Index out of bounds.");
    }
}
