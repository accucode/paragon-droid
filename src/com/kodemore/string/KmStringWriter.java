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

import java.io.IOException;
import java.io.Writer;

/**
 * An alternative to StringWriter that allows the buffered contents
 * to be redirected to an alternate writer without the creation of
 * an intermediate String.
 */
public class KmStringWriter
    extends Writer
{
    //##################################################
    //# variables
    //##################################################

    private char[] _buffer;
    private int    _length;
    private int    _growSize;

    //##################################################
    //# constructor
    //##################################################

    public KmStringWriter()
    {
        this(1000, 1000);
    }

    public KmStringWriter(int initialSize, int growSize)
    {
        _buffer = new char[initialSize];
        _growSize = growSize;
        _length = 0;
    }

    //##################################################
    //# accessing
    //##################################################

    public int getGrowSize()
    {
        return _growSize;
    }

    public void setGrowSize(int e)
    {
        _growSize = e;
    }

    public int getLength()
    {
        return _length;
    }

    //##################################################
    //# writer
    //##################################################

    @Override
    public void write(char arr[], int offset, int length)
    {
        _checkCapacity(length);
        int end = offset + length;
        for ( int i = offset; i < end; i++ )
            _buffer[_length++] = arr[i];
    }

    @Override
    public void flush()
    {
        // nothing to do.
    }

    @Override
    public void close()
    {
        // nothing to do.
    }

    public void clear()
    {
        _length = 0;
    }

    //##################################################
    //# utility
    //##################################################

    public void copyTo(Writer out) throws IOException
    {
        out.write(_buffer, 0, _length);
    }

    //##################################################
    //# private
    //##################################################

    public void _checkCapacity(int addLength)
    {
        int newLength = _length + addLength;
        if ( newLength > _buffer.length )
        {
            char[] arr = new char[newLength + _growSize];
            int n = _length;
            for ( int i = 0; i < n; i++ )
                arr[i] = _buffer[i];
        }
    }
}
