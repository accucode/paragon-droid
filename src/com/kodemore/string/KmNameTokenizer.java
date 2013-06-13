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

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;

/**
 * Take a loosely formatted name and return the list of tokens that
 * it contains.  Spaces, periods, and underscores act as delimiters.
 * Multiple uppercase letters in sequence are considered a word.
 * 
 * E.g.: a methodName 123abc_endTLA
 * returns: a, method, name, 123, abc, end, tla
 * Note: this implementation is NOT optimized and is probably very
 * SLOW for large data sets.
 */
public class KmNameTokenizer
{
    //##################################################
    //# public
    //##################################################

    public static KmList<String> parse(String s)
    {
        return new KmNameTokenizer()._parse(s);
    }

    //##################################################
    //# variables
    //##################################################

    private String         _name;
    private KmList<String> _tokens;

    //##################################################
    //# parse
    //##################################################

    private KmList<String> _parse(String s)
    {
        _name = s;
        _tokens = new KmList<String>();
        convertDelimiters();

        while ( notEmpty() )
        {
            skipSpaces();

            if ( isLowerCaseWord() )
            {
                addLowerCaseLetterWord();
                continue;
            }

            if ( isCamelCaseWord() )
            {
                addCamelCaseWord();
                continue;
            }

            if ( isUpperCaseWord() )
            {
                addUpperCaseWord();
                continue;
            }

            if ( isDigit() )
            {
                addDigitWord();
                continue;
            }

            addSingleLetterWord();
        }
        return _tokens;
    }

    private void addSingleLetterWord()
    {
        String s = read() + "";
        _tokens.add(s.toLowerCase());
    }

    private void addLowerCaseLetterWord()
    {
        StringBuilder sb = new StringBuilder();
        while ( isLowerCaseLetter() )
            sb.append(read());
        _tokens.add(sb.toString());
    }

    private void addCamelCaseWord()
    {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append(current());

        skip();
        while ( isLowerCaseLetter() )
            sb.append(read());

        _tokens.add(sb.toString().toLowerCase());
    }

    private void addUpperCaseWord()
    {
        StringBuilder sb = new StringBuilder();
        while ( isUpperCaseLetter() )
            sb.append(read());
        _tokens.add(sb.toString().toLowerCase());
    }

    private void addDigitWord()
    {
        StringBuilder sb = new StringBuilder();
        while ( isDigit() )
            sb.append(read());
        _tokens.add(sb.toString());
    }

    private boolean isDigit()
    {
        return notEmpty() && isDigit(current());
    }

    private boolean isDigit(char c)
    {
        return Character.isDigit(c);
    }

    private boolean isLowerCaseLetter()
    {
        return notEmpty() && isLowerCaseLetter(current());
    }

    private boolean isLowerCaseLetter(char c)
    {
        return Character.isLowerCase(c);
    }

    private boolean isUpperCaseLetter()
    {
        return notEmpty() && isUpperCaseLetter(current());
    }

    private boolean isUpperCaseLetter(char c)
    {
        return Character.isUpperCase(c);
    }

    private boolean isLowerCaseWord()
    {
        return Character.isLowerCase(current());
    }

    private boolean isUpperCaseWord()
    {
        if ( size() == 1 )
            return Character.isUpperCase(current());

        return Character.isUpperCase(current()) && Character.isUpperCase(next());
    }

    private boolean isCamelCaseWord()
    {
        if ( size() == 1 )
            return false;

        return Character.isUpperCase(current()) && Character.isLowerCase(next());
    }

    private int size()
    {
        return _name.length();
    }

    private char current()
    {
        return _name.charAt(0);
    }

    private char next()
    {
        return _name.charAt(1);
    }

    private void convertDelimiters()
    {
        StringBuilder out = new StringBuilder();
        for ( char c : _name.toCharArray() )
            if ( Kmu.isAlphaNumeric(c) )
                out.append(c);
            else
                out.append(' ');

        _name = out.toString().trim();
    }

    private boolean isSpace()
    {
        return current() == ' ';
    }

    private boolean isEmpty()
    {
        return _name.length() == 0;
    }

    private boolean notEmpty()
    {
        return !isEmpty();
    }

    private void skipSpaces()
    {
        while ( isSpace() )
            skip();
    }

    private void skip()
    {
        _name = _name.substring(1);
    }

    private char read()
    {
        char c = current();
        skip();
        return c;
    }

    //##################################################
    //# main
    //##################################################

    public static void main(String[] args)
    {
        String s = "a methodName 123abc_endTLA C-Stores";
        KmList<String> v = KmNameTokenizer.parse(s);
        System.out.println(s);
        System.out.println(v.format());
    }
}
