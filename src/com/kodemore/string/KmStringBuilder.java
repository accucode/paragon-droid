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

import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.Kmu;

/**
 * An alternative to StringBuilder with a variety of convenience methods.
 */
public class KmStringBuilder
    implements CharSequence, KmConstantsIF
{
    //##################################################
    //# constants
    //##################################################

    private static final String LINE_END = CRLF;

    //##################################################
    //# variables
    //##################################################

    private StringBuilder       _buffer;

    //##################################################
    //# constructor
    //##################################################

    public KmStringBuilder()
    {
        _buffer = new StringBuilder();
    }

    public KmStringBuilder(String s)
    {
        _buffer = new StringBuilder(s);
    }

    //##################################################
    //# print
    //##################################################

    public void printf(String format, Object... args)
    {
        String s = Kmu.format(format, args);
        _buffer.append(s);
    }

    public void printfln()
    {
        println();
    }

    public void printfln(String format, Object... args)
    {
        printf(format, args);
        newLine();
    }

    public void print(Object e)
    {
        _buffer.append(e);
    }

    public void println(Object e)
    {
        print(e);
        newLine();
    }

    public void println()
    {
        newLine();
    }

    public void printLines(int n)
    {
        for ( int i = 0; i < n; i++ )
            newLine();
    }

    public void printRepeat(Object e, int n)
    {
        for ( int i = 0; i < n; i++ )
            print(e);
    }

    public void newLine()
    {
        _buffer.append(LINE_END);
    }

    //##################################################
    //# misc
    //##################################################

    public void clear()
    {
        _buffer.setLength(0);
    }

    public int getLength()
    {
        return _buffer.length();
    }

    public boolean isEmpty()
    {
        return getLength() == 0;
    }

    public boolean isNotEmpty()
    {
        return !isEmpty();
    }

    public char getLastChar()
    {
        return _buffer.charAt(length() - 1);
    }

    public String toLowerCase()
    {
        return toString().toLowerCase();
    }

    public String toUpperCase()
    {
        return toString().toUpperCase();
    }

    //##################################################
    //# equals
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        return e instanceof KmStringBuilder
            && ((KmStringBuilder)e)._buffer.toString().equals(_buffer.toString());
    }

    @Override
    public int hashCode()
    {
        return _buffer.toString().hashCode();
    }

    //##################################################
    //# display
    //##################################################

    /**
     * Return this contents of the buffer.
     */
    @Override
    public String toString()
    {
        return _buffer.toString();
    }

    //##################################################
    //# delegation
    //##################################################

    public void append(boolean b)
    {
        _buffer.append(b);
    }

    public void append(char c)
    {
        _buffer.append(c);
    }

    public void append(char[] str, int offset, int len)
    {
        _buffer.append(str, offset, len);
    }

    public void append(char[] str)
    {
        _buffer.append(str);
    }

    public void append(CharSequence s, int start, int end)
    {
        _buffer.append(s, start, end);
    }

    public void append(CharSequence s)
    {
        _buffer.append(s);
    }

    public void append(double d)
    {
        _buffer.append(d);
    }

    public void append(float f)
    {
        _buffer.append(f);
    }

    public void append(int i)
    {
        _buffer.append(i);
    }

    public void append(long lng)
    {
        _buffer.append(lng);
    }

    public void append(Object obj)
    {
        _buffer.append(obj);
    }

    public void append(String str)
    {
        _buffer.append(str);
    }

    public void append(StringBuffer sb)
    {
        _buffer.append(sb);
    }

    public void appendCodePoint(int codePoint)
    {
        _buffer.appendCodePoint(codePoint);
    }

    public int capacity()
    {
        return _buffer.capacity();
    }

    @Override
    public char charAt(int index)
    {
        return _buffer.charAt(index);
    }

    public int codePointAt(int index)
    {
        return _buffer.codePointAt(index);
    }

    public int codePointBefore(int index)
    {
        return _buffer.codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex)
    {
        return _buffer.codePointCount(beginIndex, endIndex);
    }

    public void delete(int start, int end)
    {
        _buffer.delete(start, end);
    }

    public void deleteCharAt(int index)
    {
        _buffer.deleteCharAt(index);
    }

    public void ensureCapacity(int minimumCapacity)
    {
        _buffer.ensureCapacity(minimumCapacity);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
    {
        _buffer.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    public int indexOf(String str, int fromIndex)
    {
        return _buffer.indexOf(str, fromIndex);
    }

    public int indexOf(String str)
    {
        return _buffer.indexOf(str);
    }

    public void insert(int offset, boolean b)
    {
        _buffer.insert(offset, b);
    }

    public void insert(int offset, char c)
    {
        _buffer.insert(offset, c);
    }

    public void insert(int index, char[] str, int offset, int len)
    {
        _buffer.insert(index, str, offset, len);
    }

    public void insert(int offset, char[] str)
    {
        _buffer.insert(offset, str);
    }

    public void insert(int dstOffset, CharSequence s, int start, int end)
    {
        _buffer.insert(dstOffset, s, start, end);
    }

    public void insert(int dstOffset, CharSequence s)
    {
        _buffer.insert(dstOffset, s);
    }

    public void insert(int offset, double d)
    {
        _buffer.insert(offset, d);
    }

    public void insert(int offset, float f)
    {
        _buffer.insert(offset, f);
    }

    public void insert(int offset, int i)
    {
        _buffer.insert(offset, i);
    }

    public void insert(int offset, long l)
    {
        _buffer.insert(offset, l);
    }

    public void insert(int offset, Object obj)
    {
        _buffer.insert(offset, obj);
    }

    public void insert(int offset, String str)
    {
        _buffer.insert(offset, str);
    }

    public int lastIndexOf(String str, int fromIndex)
    {
        return _buffer.lastIndexOf(str, fromIndex);
    }

    public int lastIndexOf(String str)
    {
        return _buffer.lastIndexOf(str);
    }

    @Override
    public int length()
    {
        return _buffer.length();
    }

    public int offsetByCodePoints(int index, int codePointOffset)
    {
        return _buffer.offsetByCodePoints(index, codePointOffset);
    }

    public void replace(int start, int end, String str)
    {
        _buffer.replace(start, end, str);
    }

    public void reverse()
    {
        _buffer.reverse();
    }

    public void setCharAt(int index, char ch)
    {
        _buffer.setCharAt(index, ch);
    }

    public void setLength(int newLength)
    {
        _buffer.setLength(newLength);
    }

    @Override
    public CharSequence subSequence(int start, int end)
    {
        return _buffer.subSequence(start, end);
    }

    public String substring(int start, int end)
    {
        return _buffer.substring(start, end);
    }

    public String substring(int start)
    {
        return _buffer.substring(start);
    }

    public void trimToSize()
    {
        _buffer.trimToSize();
    }

    //##################################################
    //# prepend
    //##################################################

    public void prepend(boolean b)
    {
        _buffer.insert(0, b);
    }

    public void prepend(char c)
    {
        _buffer.insert(0, c);
    }

    public void prepend(char[] str, int offset, int len)
    {
        _buffer.insert(0, str, offset, len);
    }

    public void prepend(char[] str)
    {
        _buffer.insert(0, str);
    }

    public void prepend(CharSequence s, int start, int end)
    {
        _buffer.insert(0, s, start, end);
    }

    public void prepend(CharSequence s)
    {
        _buffer.insert(0, s);
    }

    public void prepend(double d)
    {
        _buffer.insert(0, d);
    }

    public void prepend(float f)
    {
        _buffer.insert(0, f);
    }

    public void prepend(int i)
    {
        _buffer.insert(0, i);
    }

    public void prepend(long lng)
    {
        _buffer.insert(0, lng);
    }

    public void prepend(Object obj)
    {
        _buffer.insert(0, obj);
    }

    public void prepend(String str)
    {
        _buffer.insert(0, str);
    }

    public void prepend(StringBuffer sb)
    {
        _buffer.insert(0, sb);
    }

    //##################################################
    //# insert
    //##################################################

    /**
     * Insert s at the offset, then again every freq characters.
     * Note that the freq positions are calculated the same independent
     * of the length of the string being insert.
     * E.g.:
     *      aabbcc.insertEvery(0, 2, ".")  => .aa.bb.cc.
     *      aabbcc.insertEvery(0, 2, "..") => ..aa..bb..cc..
     */
    public void insertEvery(int offset, int freq, String s)
    {
        while ( offset < length() )
        {
            insert(offset, s);
            offset += freq + s.length();
        }
    }

    //##################################################
    //# prefix
    //##################################################

    public boolean hasPrefix(String s)
    {
        if ( Kmu.isEmpty(s) )
            return false;

        return toString().startsWith(s);
    }

    public boolean removePrefix(String s)
    {
        if ( !hasPrefix(s) )
            return false;

        delete(0, s.length());
        return true;
    }

    /**
     * Remove the first n characters (or everything if i is longer than me). 
     * Throw an exception if I am shorter than n.
     */
    public void removeFirst(int i)
    {
        if ( i < length() )
            delete(0, i);
        else
            clear();
    }

    //##################################################
    //# suffix
    //##################################################

    public boolean hasSuffix(String s)
    {
        if ( Kmu.isEmpty(s) )
            return false;

        return toString().endsWith(s);
    }

    public boolean removeSuffix(String s)
    {
        if ( !hasSuffix(s) )
            return false;

        removeLast(s.length());
        return true;
    }

    /**
     * Remove the last n characters (or everything if i is longer than me). 
     */
    public void removeLast(int i)
    {
        int n = length();
        if ( i < n )
            delete(n - i, n);
        else
            clear();
    }

    //##################################################
    //# pad
    //##################################################

    /**
     * Use spaces to pad the buffer to a specified length.
     */
    public void padToLength(int n)
    {
        while ( length() < n )
            append(" ");
    }

    /**
     * Use spaces to pad the buffer to the next increment.
     * For example, padToIncrement(4) will increase the
     * length to 4, 8, 12, 16.
     */
    public void padToIncrement(int n)
    {
        while ( length() % n != 0 )
            append(" ");
    }

    /**
     * Use s to pad the buffer to minimum length.
     * s is repeatedly added to the end of the buffer until
     * the buffer's length >= n.  Do nothing if s is empty.
     */
    public void padRight(int n, String s)
    {
        if ( Kmu.isEmpty(s) )
            return;

        while ( getLength() < n )
            append(s);
    }

    /**
     * Use s to pad the buffer to minimum length.
     * s is repeatedly added to the beginning of the buffer 
     * until the buffer's length >= n.  Do nothing if s is empty.
     */
    public void padLeft(int n, String s)
    {
        if ( Kmu.isEmpty(s) )
            return;

        while ( getLength() < n )
            insert(0, s);
    }

    //##################################################
    //# convenience
    //##################################################

    public void space()
    {
        print(SPACE);
    }

}
