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

package com.kodemore.utility;

import com.kodemore.time.KmDate;
import com.kodemore.time.KmTimestamp;

/**
 * Miscellaneous constants.
 */
public interface KmConstantsIF
{
    //##################################################
    //# general
    //##################################################

    int         UNDEFINED_INT         = Integer.MIN_VALUE;
    long        UNDEFINED_LONG        = Long.MIN_VALUE;

    //##################################################
    //# day of week
    //##################################################

    char        DOW_CHAR_MONDAY       = 'M';
    char        DOW_CHAR_TUESDAY      = 'T';
    char        DOW_CHAR_WEDNESDAY    = 'W';
    char        DOW_CHAR_THURSDAY     = 'H';
    char        DOW_CHAR_FRIDAY       = 'F';
    char        DOW_CHAR_SATURDAY     = 'S';
    char        DOW_CHAR_SUNDAY       = 'U';

    char        DOW_INT_MONDAY        = '1';
    char        DOW_INT_TUESDAY       = '2';
    char        DOW_INT_WEDNESDAY     = '3';
    char        DOW_INT_THURSDAY      = '4';
    char        DOW_INT_FRIDAY        = '5';
    char        DOW_INT_SATURDAY      = '6';
    char        DOW_INT_SUNDAY        = '7';

    String      DOW_MONDAY            = "" + DOW_CHAR_MONDAY;
    String      DOW_TUESDAY           = "" + DOW_CHAR_TUESDAY;
    String      DOW_WEDNESDAY         = "" + DOW_CHAR_WEDNESDAY;
    String      DOW_THURSDAY          = "" + DOW_CHAR_THURSDAY;
    String      DOW_FRIDAY            = "" + DOW_CHAR_FRIDAY;
    String      DOW_SATURDAY          = "" + DOW_CHAR_SATURDAY;
    String      DOW_SUNDAY            = "" + DOW_CHAR_SUNDAY;

    //##################################################
    //# sql limits
    //##################################################

    KmTimestamp MINIMUM_TIMESTAMP     = KmTimestamp.create(1980, 1, 1);
    KmTimestamp MAXIMUM_TIMESTAMP     = KmTimestamp.create(2030, 1, 1);

    KmDate      MINIMUM_DATE          = KmDate.create(1800, 1, 1);
    KmDate      MAXIMUM_DATE          = KmDate.create(2030, 1, 1);

    //##################################################
    //# characters
    //##################################################

    char        CHAR_NULL             = 0;
    char        CHAR_SPACE            = ' ';
    char        CHAR_FF               = '\f';
    char        CHAR_CR               = '\r';
    char        CHAR_LF               = '\n';
    char        CHAR_TAB              = '\t';
    char        CHAR_BACKSLASH        = '\\';
    char        CHAR_TICK             = '\'';
    char        CHAR_QUOTE            = '\"';
    char        CHAR_OPEN_BRACE       = '{';
    char        CHAR_CLOSE_BRACE      = '}';
    char        CHAR_OPEN_PAREN       = '(';
    char        CHAR_CLOSE_PAREN      = ')';
    char        CHAR_COMMA            = ',';
    char        CHAR_SEMICOLON        = ';';
    char        CHAR_COLON            = ':';
    char        CHAR_EQUAL            = '=';
    char        CHAR_DASH             = '-';
    char        CHAR_SLASH            = '/';
    char        CHAR_UNDERSCORE       = '_';
    char        CHAR_PERCENT          = '%';

    char        CHAR_PERIOD           = '.';
    char        CHAR_DOT              = CHAR_PERIOD;

    char        CHAR_ZERO             = '0';
    char        CHAR_A                = 'A';

    //##################################################
    //# character strings
    //##################################################

    String      FF                    = "" + CHAR_FF;
    String      CR                    = "" + CHAR_CR;
    String      LF                    = "" + CHAR_LF;
    String      CRLF                  = CR + LF;
    String      UNDERSCORE            = "" + CHAR_UNDERSCORE;
    String      SPACE                 = "" + CHAR_SPACE;
    String      TAB                   = "" + CHAR_TAB;
    String      SLASH                 = "" + CHAR_SLASH;
    String      BACKSLASH             = "" + CHAR_BACKSLASH;
    String      DOT                   = "" + CHAR_DOT;
    String      OPEN_PAREN            = "" + CHAR_OPEN_PAREN;
    String      CLOSE_PAREN           = "" + CHAR_CLOSE_PAREN;
    String      QUOTE                 = "" + CHAR_QUOTE;

    //##################################################
    //# weight conversion
    //##################################################

    double      KILOGRAMS_PER_POUND   = 0.4535924;
    double      POUNDS_PER_KILOGRAM   = 1 / KILOGRAMS_PER_POUND;

    //##################################################
    //# weight conversion
    //##################################################

    String      UTF_16                = "UTF-16";
    String      UTF_16_LITTLE_ENDIAN  = "UTF-16LE";
    String      UNICODE_PAGE_ENCODING = UTF_16_LITTLE_ENDIAN;

    //##################################################
    //# misc
    //##################################################

    String      DIGITS                = "0123456789";
    String      UPPERCASE_LETTERS     = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String      LOWERCASE_LETTERS     = "abcdefghijklmnopqrstuvwxyz";

    String      LETTERS               = UPPERCASE_LETTERS + LOWERCASE_LETTERS;
    String      DIGITS_AND_LETTERS    = DIGITS + LETTERS;

    String      HEX_CHAR_STRING       = "0123456789ABCDEF";
    char[]      HEX_CHAR_ARRAY        = HEX_CHAR_STRING.toCharArray();

    String      BASE_62_STRING        = DIGITS + UPPERCASE_LETTERS + LOWERCASE_LETTERS;
    char[]      BASE_62_ARRAY         = BASE_62_STRING.toCharArray();

    String      BASE_36_STRING        = DIGITS + UPPERCASE_LETTERS;
    char[]      BASE_36_ARRAY         = BASE_36_STRING.toCharArray();

    String      BASE_20_STRING        = "BCDFGHJKLMNPQRSTWXZ";
    char[]      BASE_20_ARRAY         = BASE_20_STRING.toCharArray();

    char        CHAR_NON_PRINTABLE    = '?';

    String      STRING_CR             = "" + CHAR_CR;
    String      STRING_LF             = "" + CHAR_LF;
    String      STRING_CRLF           = "" + CHAR_CR + CHAR_LF;

    //##################################################
    //# app constants
    //##################################################

    float       LABEL_SIZE            = 20;
    float       TEXT_SIZE             = 20;
    int         BUTTON_PAD_TEXT_SIZE  = 5;

    //##################################################
    //# title bar
    //##################################################
    /**
     * These values are a percentage of the screen height
     */
    int         TITLE_BAR_HEIGHT      = 8;
    int         TITLE_TEXT_HEIGHT     = 5;

}
