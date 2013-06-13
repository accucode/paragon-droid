/*
  Copyright (c) 2005-2012 Wyatt Love

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

import android.util.Log;

public class KmLog
{
    //##################################################
    //# constants
    //##################################################

    public static final String TAG = "kodemore";

    //##################################################
    //# convenience
    //##################################################

    public static void verbose(String msg, Object... args)
    {
        String s = format(msg, args);
        Log.v(TAG, s);
    }

    public static void print(String msg, Object... args)
    {
        info(msg, args);
    }

    public static void info(String msg, Object... args)
    {
        String s = format(msg, args);
        Log.i(TAG, s);
    }

    public static void debug(String msg, Object... args)
    {
        String s = format(msg, args);
        Log.d(TAG, s);
    }

    public static void error(Throwable ex)
    {
        error(Kmu.formatTrace(ex));
    }

    public static void error(String msg, Object... args)
    {
        String s = format(msg, args);
        Log.e(TAG, s);
    }

    public static void warn(String msg, Object... args)
    {
        String s = format(msg, args);
        Log.w(TAG, s);
    }

    public static void warn(Class<?> tagClass, String msg, Object... args)
    {
        String tag = tagClass.getSimpleName();
        String s = format(msg, args);
        Log.w(tag, s);
    }

    public static void warn(Class<?> tagClass, Exception ex)
    {
        String tag = tagClass.getSimpleName();
        String s = ex.getMessage();
        Log.w(tag, s, ex);
    }

    public static void warnTrace(String msg, Object... args)
    {
        String s = format(msg, args);
        Log.w(TAG, s, Kmu.getStackTrace());
    }

    public static void unhandled(Exception ex)
    {
        String title = Kmu.formatCamelCaseWords(ex.getClass());
        Log.e(TAG, title, ex);
    }

    //##################################################
    //# support
    //##################################################

    private static String format(String msg, Object... args)
    {
        String s = Kmu.format(msg, args);

        if ( s.length() == 0 )
            s = ".";

        return s;
    }

}
