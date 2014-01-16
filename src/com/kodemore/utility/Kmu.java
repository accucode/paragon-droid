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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.kodemore.collection.KmList;
import com.kodemore.collection.KmMap;
import com.kodemore.sql.KmSqlDatabase;
import com.kodemore.string.KmNameTokenizer;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.time.KmDate;
import com.kodemore.types.KmIntegerRange;
import com.kodemore.view.KmAction;

public class Kmu
    implements KmConstantsIF
{
    //##################################################
    //# constants
    //##################################################

    public static final char    CHAR_TAB           = 9;
    public static final char    CHAR_LF            = 10;
    public static final char    CHAR_CR            = 13;
    public static final char    CHAR_SPACE         = 32;
    public static final char    CHAR_COMMA         = ',';

    public static final String  CR_LF              = "" + CHAR_CR + CHAR_LF;

    public static final char[]  WHITESPACE_ARRAY   =
                                                   {
        CHAR_SPACE,
        CHAR_CR,
        CHAR_LF,
        CHAR_TAB
                                                   };

    public static final String  WHITESPACE_STRING  = new String(WHITESPACE_ARRAY);

    private static final String DIGITS             = "0123456789";
    private static final String UPPERCASE_LETTERS  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS  = "abcdefghijklmnopqrstuvwxyz";

    private static final String LETTERS            = UPPERCASE_LETTERS + LOWERCASE_LETTERS;
    private static final String DIGITS_AND_LETTERS = DIGITS + LETTERS;

    private static final String HEX_CHAR_STRING    = "0123456789ABCDEF";
    private static final char[] HEX_CHAR_ARRAY     = HEX_CHAR_STRING.toCharArray();

    private static final String BASE_62_STRING     = DIGITS + UPPERCASE_LETTERS + LOWERCASE_LETTERS;
    private static final char[] BASE_62_ARRAY      = BASE_62_STRING.toCharArray();

    private static final String BASE_36_STRING     = DIGITS + UPPERCASE_LETTERS;
    private static final char[] BASE_36_ARRAY      = BASE_36_STRING.toCharArray();

    private static final String BASE_20_STRING     = "BCDFGHJKLMNPQRSTWXZ";
    private static final char[] BASE_20_ARRAY      = BASE_20_STRING.toCharArray();

    //##################################################
    //# alerts
    //##################################################

    /**
     * Show a simple popup dialog alert.  This is primarily used
     * for quick tests.  The dialog state is not persisted; so
     * it will disappear if you do something like reorient the device.
     * 
     * You must provide a local ui context (e.g.: an activity).
     * Alerts cannot be shown using the global applicationContext.
     */
    public static void alert(Context context, String title, CharSequence msg)
    {
        Builder b;
        b = new AlertDialog.Builder(context);
        b.setTitle(title);
        b.setMessage(msg);
        b.setPositiveButton("Ok", KmAction.NOOP);
        b.show();
    }

    public static void alert(Context context, CharSequence msg)
    {
        alert(context, "Alert", msg);
    }

    //##################################################
    //# toast
    //##################################################

    public static void toast(CharSequence msg)
    {
        shortToast(msg);
    }

    public static void toast(View v)
    {
        shortToast(v);
    }

    public static void toast(String msg, Object... args)
    {
        String s = format(msg, args);
        toast(s);
    }

    public static void shortToast(CharSequence s)
    {
        try
        {
            Context context = getApplicationContext();
            String msg = toDisplay(s);
            int speed = Toast.LENGTH_SHORT;

            Toast.makeText(context, msg, speed).show();
        }
        catch ( RuntimeException ex )
        {
            KmLog.error(ex);
        }
    }

    public static void shortToast(View v)
    {
        try
        {
            Context context = getApplicationContext();
            int speed = Toast.LENGTH_SHORT;

            Toast toast = new Toast(context);
            toast.setDuration(speed);
            toast.setView(v);
            toast.show();
        }
        catch ( RuntimeException ex )
        {
            KmLog.error(ex);
        }
    }

    //##################################################
    //# thread
    //##################################################

    public static RuntimeException toRuntime(Throwable ex)
    {
        if ( ex instanceof RuntimeException )
            return (RuntimeException)ex;

        return new RuntimeException(ex);
    }

    public static void handleUnhandledException(Exception ex)
    {
        getBridge().handleException(ex);
    }

    public static void unhandledException(Exception ex)
    {
        KmLog.unhandled(ex);

        String title = formatCamelCaseWords(ex.getClass());
        String msg = ex.getMessage();

        KmStringBuilder out;
        out = new KmStringBuilder();
        out.println("Unhandled Exception");
        out.println(title);
        out.println(msg);

        toast(out.toString());
    }

    public static String formatTrace(Throwable ex)
    {
        try
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.close();
            return sw.toString();
        }
        catch ( RuntimeException rex )
        {
            return ex.getMessage();
        }
    }

    public static String formatMessage(Throwable ex)
    {
        if ( ex == null )
            return "";

        String s = ex.getMessage();
        if ( hasValue(s) )
            return s;

        return ex.toString();
    }

    public static void printStackTrace()
    {
        try
        {
            throw new RuntimeException();
        }
        catch ( RuntimeException ex )
        {
            ex.printStackTrace();
        }
    }

    //##################################################
    //# sleep
    //##################################################

    public static void sleepMs(int ms)
    {
        try
        {
            if ( ms > 0 )
                Thread.currentThread().sleep(ms);
        }
        catch ( InterruptedException ex )
        {
            toRuntime(ex);
        }
    }

    public static void sleepSecond()
    {
        sleepSeconds(1);
    }

    public static void sleepSeconds(int sec)
    {
        sleepMs(sec * 1000);
    }

    //##################################################
    //# format
    //##################################################

    public static KmList<String> getPascalCaseWords(String s)
    {
        KmList<String> v;
        v = getCamelCaseWords(s);

        KmList<String> pascalList;
        pascalList = new KmList<String>();

        for ( String e : v )
            pascalList.add(capitalizeFirstLetter(e));

        return pascalList;
    }

    public static KmList<String> getCamelCaseWords(String s)
    {
        KmList<String> words;
        words = new KmList<String>();

        KmStringBuilder word;
        word = new KmStringBuilder();

        for ( char c : s.toCharArray() )
        {
            if ( word.isEmpty() )
            {
                word.append(c);
                continue;
            }

            char prev = word.getLastChar();

            if ( Character.isLowerCase(c) && Character.isLetter(prev) )
            {
                word.append(c);
                continue;
            }

            if ( Character.isUpperCase(c) && Character.isUpperCase(prev) )
            {
                word.append(c);
                continue;
            }

            if ( Character.isDigit(c) && Character.isDigit(prev) )
            {
                word.append(c);
                continue;
            }

            words.add(word.toLowerCase());

            word.clear();
            word.append(c);
        }

        if ( word.isNotEmpty() )
            words.add(word.toLowerCase());

        return words;
    }

    public static String formatCamelCaseWords(String s)
    {
        KmStringBuilder out;
        out = new KmStringBuilder();

        KmList<String> v = getCamelCaseWords(s);
        for ( String e : v )
        {
            out.append(KmString.create(e).capitalize());
            out.space();
        }

        return out.toString().trim();
    }

    public static String formatCamelCaseWords(Class<?> c)
    {
        return formatCamelCaseWords(c.getSimpleName());
    }

    public static String formatYesNo(Boolean e)
    {
        if ( e == null )
            return "Null";

        return e
            ? "Yes"
            : "No";
    }

    public static String format(String msg, Object... args)
    {
        if ( msg == null )
            return "";

        if ( args == null || args.length == 0 )
            return msg;

        return String.format(msg, args);
    }

    /**
     * Concatenate the elements, separated by a single comma.
     */
    public static String format(List<?> v)
    {
        return format(v, ",");
    }

    /**
     * Concatenate the elements, separated by a new line character.
     */
    public static String formatLines(List<?> v)
    {
        return format(v, "\n");
    }

    /**
     * Concatenate the elements, separated by the delimiter.
     */
    public static String format(List<?> v, String delimiter)
    {
        if ( v == null || v.isEmpty() )
            return "";

        StringBuilder out = new StringBuilder();
        for ( Object e : v )
        {
            out.append(e);
            out.append(delimiter);
        }

        out.setLength(out.length() - delimiter.length());
        return out.toString();
    }

    /**
     * Add commas to the integer in standard american format.
     */
    public static String formatInteger(long value)
    {
        int min = value < 0
            ? 2
            : 1;

        StringBuilder out;
        out = new StringBuilder();
        out.append(value);

        int i = out.length() - 3;
        while ( i >= min )
        {
            out.insert(i, CHAR_COMMA);
            i -= 3;
        }

        return out.toString();
    }

    /**
     * Format a double using commas for the integer portion and rounded to the
     * appropriate number of decimal places.
     */
    public static String formatDouble(Double d, int places)
    {
        return formatDouble(d, places, true);
    }

    /**
     * Format a double using optional commas for the integer portion and rounded
     * to the appropriate number of decimal places.
     */
    public static String formatDouble(Double d, int places, boolean commas)
    {
        if ( d == null )
            return "";

        if ( Double.isNaN(d) )
            return "";

        double rounded = round(d, places);
        String s = getDecimalFormat(places, commas);
        return new DecimalFormat(s).format(rounded);
    }

    /**
     * This returns the appropriate format for the Java DecimalFormat class
     * depending on the desired number of decimal places and optional comma
     * display.
     */
    public static String getDecimalFormat(int places, boolean commas)
    {
        if ( places == 0 )
            return commas
                ? "#,##0"
                : "0";

        StringBuilder out = new StringBuilder();

        if ( commas )
            out.append("#,##");

        out.append("0.");

        for ( int i = 0; i < places; i++ )
            out.append("0");

        return out.toString();
    }

    /**
     * Format a uri suitable for use in either a desktop or android
     * browser.  On android this will take the user to the app
     * details page so they can immediately download or rate the app.
     */
    public static String formatMarketUri(String appId)
    {
        return "http://market.android.com/details?id=" + appId;
    }

    /**
     * Format the object using the default toString() implementation.
     * If the object is null, return a visible (non-null) label.  E.g.: <null>
     */
    public static String toDisplay(Object e)
    {
        if ( e == null )
            return "";

        if ( e instanceof KmDisplayStringIF )
            return ((KmDisplayStringIF)e).getDisplayString();

        return e.toString();
    }

    /**
     * Return the objects toString, or null if the object is null.
     */
    public static String toStringSafe(Object e)
    {
        return e == null
            ? null
            : e.toString();
    }

    //##################################################
    //# file name
    //##################################################

    /**
     * Returns a safe file name with file extension
     */
    public static String formatSafeFileName(String name, String ext)
    {
        name = _formatSafeFilePath(name, "no_name");
        ext = _formatSafeFilePath(ext, "default");
        return name + "." + ext;
    }

    private static String _formatSafeFilePath(String s, String def)
    {
        KmString ss;
        ss = KmString.create(s);
        ss = ss.toLinePrintable();
        ss = ss.stripWhitespace();
        ss = ss.stripSpaces();
        ss = ss.stripCommas();
        ss = ss.strip('.');
        ss = ss.strip('/');

        return ss.hasValue()
            ? ss.toString()
            : def;
    }

    //##################################################
    //# activity
    //##################################################

    /**
     * Start another activity.
     */
    public static void startActivity(Context context, Class<? extends Activity> activity)
    {
        Intent intent;
        intent = new Intent(context, activity);

        context.startActivity(intent);
    }

    /**
     * Restarts an activity, clearing the all the other activities above it in the stack.  
     * 
     * note if there are tabbed activites in the stack then you cannot finish to the tab
     * you must finish to the containing tabActivity or else the stack will be completely
     * cleared leaving only the launched activity in the stack, if you then try to back out
     * then the program will crash.
     */
    public static void startActivityClearTop(Context context, Class<? extends Activity> activity)
    {
        Intent intent;
        intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }

    public static void startActivity(Context context, String activityPkg, String activityName)
    {
        Intent intent;
        intent = new Intent();
        intent.setClassName(activityPkg, activityName);

        context.startActivity(intent);
    }

    /**
     * Start another activity, expecting a result.
     */
    public static void startActivityForResult(Activity sender, Class<?> activity, int requestCode)
    {
        Intent intent = new Intent(sender, activity);
        sender.startActivityForResult(intent, requestCode);
    }

    public static KmAction newStartAction(
        final Context context,
        final Class<? extends Activity> activity)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                Kmu.startActivity(context, activity);
            }
        };
    }

    public static Intent createIntent(String key, Serializable extra)
    {
        Intent e;
        e = new Intent();
        e.putExtra(key, extra);
        return e;
    }

    //##################################################
    //# service
    //##################################################

    public static void startService(Context context, Class<? extends Service> service)
    {
        Intent i = new Intent(context, service);
        context.startService(i);
    }

    //##################################################
    //# is empty
    //##################################################

    /**
     * Determine if s is either null or an empty string.
     */
    public static boolean isEmpty(CharSequence s)
    {
        return s == null || s.length() == 0;
    }

    /**
     * Determine if s is a non-null string with a minimun length of 1.
     */
    public static boolean hasValue(CharSequence s)
    {
        return !isEmpty(s);
    }

    /**
     * Determine if s is a non-null string with a minimun length of 1.
     */
    public static boolean isNotEmpty(CharSequence s)
    {
        return !isEmpty(s);
    }

    /**
     * Determines if all of the arguments are empty.
     */
    public static boolean areAllEmpty(String... arr)
    {
        int n = arr.length;
        for ( int i = 0; i < n; i++ )
            if ( !isEmpty(arr[i]) )
                return false;

        return true;
    }

    public static String emptyToNull(String s)
    {
        return isEmpty(s)
            ? null
            : s;
    }

    //##################################################
    //# is equal
    //##################################################

    public static boolean isEqual(Object a, Object b)
    {
        return a == null
            ? b == null
            : a.equals(b);
    }

    public static boolean isNotEqual(Object a, Object b)
    {
        return !isEqual(a, b);
    }

    public static boolean isEqualIgnoreCase(String a, String b)
    {
        return a == null
            ? b == null
            : a.equalsIgnoreCase(b);
    }

    /**
     * Determine if x = y; return true if both are nan.
     */
    public static boolean isEqual(double x, double y)
    {
        if ( isNan(x) )
            return isNan(y);

        if ( isNan(y) )
            return false;

        return x == y;
    }

    /**
     * Determine if x == y within some tolerance.
     * Return true if both x and y are Nan.
     */
    public static boolean isEqual(double x, double y, double tolerance)
    {
        if ( isNan(x) )
            return isNan(y);

        if ( isNan(y) )
            return false;

        return Math.abs(x - y) <= tolerance;
    }

    /**
     * Determine if x and y are equal within a common tolerance.
     */
    public static boolean isNearEqual(double x, double y)
    {
        return isEqual(x, y, .000000001);
    }

    /**
     * Get the hash code for an object.
     * Return 0 for null.
     */
    public static int toHashCode(Object e)
    {
        return e == null
            ? 0
            : e.hashCode();
    }

    /**
     * Get the hash code for a combination of objects.
     * (Starts with 0, and then XORs the hash code of each value).
     */
    public static int toHashCode(Object... arr)
    {
        int result = 0;

        int n = arr.length;
        for ( int i = 0; i < n; i++ )
            result ^= toHashCode(arr[i]);

        return result;
    }

    //##################################################
    //# printable
    //##################################################

    /**
     * Is printable across multiple lines.
     */
    public static boolean isPrintable(char c)
    {
        if ( isLinePrintable(c) )
            return true;

        return c == CHAR_TAB || c == CHAR_CR || c == CHAR_LF;
    }

    /**
     * Is printable on a single line.
     */
    public static boolean isLinePrintable(char c)
    {
        return 32 <= c && c < 127;
    }

    public static String stripNonPrintable(CharSequence s)
    {
        if ( s == null )
            return null;

        StringBuilder out = new StringBuilder();
        for ( char c : s.toString().toCharArray() )
            if ( isPrintable(c) )
                out.append(c);

        return out.toString();
    }

    public static String stripNonLinePrintable(CharSequence s)
    {
        if ( s == null )
            return null;

        StringBuilder out = new StringBuilder();
        for ( char c : s.toString().toCharArray() )
            if ( isLinePrintable(c) )
                out.append(c);

        return out.toString();
    }

    public static boolean containsNonPrintable(CharSequence s)
    {
        if ( s == null )
            return false;

        for ( char c : s.toString().toCharArray() )
            if ( !isPrintable(c) )
                return true;

        return false;
    }

    public static boolean containsNonLinePrintable(CharSequence s)
    {
        if ( s == null )
            return false;

        for ( char c : s.toString().toCharArray() )
            if ( !isLinePrintable(c) )
                return true;

        return false;
    }

    /**
     * Convert any non-printable characters into 
     * printable representations in the form {XX}
     * where XX is the hex code to the unprintable 
     * char.
     */
    public static String toPrintable(CharSequence in)
    {
        KmStringBuilder out;
        out = new KmStringBuilder();

        int n = in.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = in.charAt(i);
            if ( isPrintable(c) )
                out.print(c);
            else
                out.printf("{%s}", toHex(c));
        }

        return out.toString();
    }

    public static String toLinePrintable(CharSequence in)
    {
        KmStringBuilder out;
        out = new KmStringBuilder();

        int n = in.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = in.charAt(i);
            if ( isLinePrintable(c) )
                out.print(c);
            else
                out.printf("{%s}", toHex(c));
        }

        return out.toString();
    }

    //##################################################
    //# math
    //##################################################

    /**
     * Round the double to the nearest integer.
     */
    public static int round(double d)
    {
        return (int)round(d, 0);
    }

    /**
     * Round the double to the specified number of decimal places.
     */
    public static double round(double d, int n)
    {
        if ( Double.isNaN(d) )
            return Double.NaN;

        boolean neg = d < 0;

        double result = neg
            ? -d
            : d;

        double ten = Math.pow(10, n);
        result = Math.round(result * ten) / ten;

        return neg
            ? -result
            : result;
    }

    /**
     * Round the double to the next whole number.
     */
    public static double roundUpToInteger(double d)
    {
        return Math.ceil(d);
    }

    /**
     * Round the double to the next whole number.
     */
    public static double roundHalfUpToInteger(double d)
    {
        return Math.rint(d);
    }

    //##################################################
    //# string
    //##################################################
    //==================================================
    //= string :: prefix
    //==================================================

    /**
     * Remove the specified prefix from the beginning of the string. If the
     * string does not start with prefix, then return the original string.
     */
    public static String removePrefix(String s, String prefix)
    {
        if ( prefix == null )
            return s;

        if ( s.startsWith(prefix) )
            return s.substring(prefix.length());

        return s;
    }

    /**
     * Repeatedly remove the prefix and return the result.
     */
    public static String removeAllPrefix(String s, String prefix)
    {
        if ( prefix == null )
            return s;

        if ( s.startsWith(prefix) )
            s = s.substring(prefix.length());

        return s;
    }

    /**
     * If, and only if s starts with oldPrefix, then remove the old prefix
     * and prepend the new prefix.  If s is null, return null.
     */
    public static String replacePrefix(String s, String oldPrefix, String newPrefix)
    {
        if ( s == null )
            return null;

        if ( !s.startsWith(oldPrefix) )
            return s;

        return newPrefix + removePrefix(s, oldPrefix);
    }

    /**
     * Add the prefix to s, if it does not already have that prefix.
     */
    public static String ensurePrefix(String s, String prefix)
    {
        if ( s == null )
            return null;

        if ( isEmpty(prefix) )
            return s;

        if ( s.startsWith(prefix) )
            return s;

        return prefix + s;
    }

    /**
     * Remove all leading characters matching c.
     */
    public static String removeAllLeading(String s, char c)
    {
        while ( true )
        {
            if ( s.length() == 0 )
                return "";

            if ( s.charAt(0) != c )
                return s;

            s = s.substring(1);
        }
    }

    public static String reduceMultipleSpaces(String s)
    {
        return replaceAll(s, "  ", " ", true);
    }

    /**
     * Return the longest prefix that is common to both strings.
     * See getCommonPrefix(String, String, maxLength).
     */
    public static String getCommonPrefix(String a, String b)
    {
        if ( a == null )
            return null;

        if ( b == null )
            return null;

        int maxLength = min(a.length(), b.length());
        return getCommonPrefix(a, b, maxLength);
    }

    /**
     * Return the longest prefix that is common to both strings.
     * The prefix will be truncated to no more than maxLength.
     * Return null if either string is null.
     * Return empty string if there is no common prefix.
     */
    public static String getCommonPrefix(String a, String b, int maxLength)
    {
        if ( a == null )
            return null;

        if ( b == null )
            return null;

        String prefix = "";
        maxLength = min(maxLength, a.length(), b.length());

        for ( int i = 1; i <= maxLength; i++ )
        {
            String aPrefix = a.substring(0, i);
            String bPrefix = b.substring(0, i);

            if ( !aPrefix.equals(bPrefix) )
                break;

            prefix = aPrefix;
        }
        return prefix;
    }

    //==================================================
    //= string :: suffix
    //==================================================

    /**
     * Remove the specified suffix from the end of the string. If the string
     * does not end with the suffix, when return the original string.
     */
    public static String removeSuffix(String s, String suffix)
    {
        if ( suffix == null )
            return s;

        if ( s.endsWith(suffix) )
            return s.substring(0, s.length() - suffix.length());

        return s;
    }

    /**
     * Remove the specified suffix from the end of the string. If the string
     * does not end with the suffix, when return the original string.
     */
    public static String removeSuffix(String s, char suffix)
    {
        return removeSuffix(s, suffix + "");
    }

    /**
     * Removes the last character from the string.
     */
    public static String removeLastCharacter(String s)
    {
        return s.substring(0, s.length() - 1);
    }

    /**
     * Repeatedly remove the prefix and return the result.
     */
    public static String removeAllSuffix(String s, String suffix)
    {
        while ( true )
        {
            String result = removeSuffix(s, suffix);

            if ( result.equals(s) )
                return result;

            s = result;
        }
    }

    /**
     * Add the suffix to s, if it does not already have that suffix.
     */
    public static String ensureSuffix(String s, String suffix)
    {
        if ( s == null )
            return null;

        if ( isEmpty(suffix) )
            return s;

        if ( s.endsWith(suffix) )
            return s;

        return s + suffix;
    }

    /**
     * Determine if s ends with any of the the suffixes.
     */
    public static boolean endsWithAny(String s, String... suffixes)
    {
        for ( String suffix : suffixes )
            if ( s.endsWith(suffix) )
                return true;

        return false;
    }

    //==================================================
    //= string :: misc
    //==================================================

    public static String join(String a, String b, String join)
    {
        if ( a == null && b == null )
            return null;

        if ( a == null )
            return b;

        if ( b == null )
            return a;

        a = removeSuffix(a, join);
        b = removePrefix(b, join);

        return a + join + b;
    }

    public static String toHex(char c)
    {
        String s = Integer.toHexString(c).toUpperCase();
        return padLeft(s, 2, '0');
    }

    public static Integer getLastAlphaIndex(String s)
    {
        if ( isEmpty(s) )
            return 0;

        int n = s.length() - 1;

        while ( n >= 0 )
        {
            if ( !isDigit(s.charAt(n)) )
                break;

            n--;
        }

        return n;
    }

    public static boolean hasSubstring(String source, String substring)
    {
        if ( source == null )
            return false;

        if ( substring == null )
            return false;

        return source.indexOf(substring) >= 0;
    }

    public static boolean hasSubstringIgnoreCase(String source, CharSequence s)
    {
        return hasSubstring(toLowerCase(source), toLowerCase(s));
    }

    public static String toLowerCase(CharSequence e)
    {
        if ( e == null )
            return null;

        return e.toString().toLowerCase();
    }

    public static String toUpperCase(String e)
    {
        if ( e == null )
            return null;

        return e.toUpperCase();
    }

    public static Character toLowerCase(Character e)
    {
        if ( e == null )
            return null;

        return e.toString().toLowerCase().charAt(0);
    }

    public static Character toUpperCase(Character e)
    {
        if ( e == null )
            return null;

        return e.toString().toUpperCase().charAt(0);
    }

    //==================================================
    //= string :: repeat
    //==================================================

    /**
     * Create a string that repeats c, n times.
     */
    public static String repeat(char c, int n)
    {
        if ( n <= 0 )
            return "";

        StringBuilder out = new StringBuilder(n);
        for ( int i = 0; i < n; i++ )
            out.append(c);

        return out.toString();
    }

    /**
     * Create a string that repeats s, n times.
     */
    public static String repeat(String s, int n)
    {
        if ( n <= 0 )
            return "";

        StringBuilder out = new StringBuilder(s.length() * n);
        for ( int i = 0; i < n; i++ )
            out.append(s);

        return out.toString();
    }

    //==================================================
    //= string :: replace
    //==================================================

    /**
     * Replace the first occurence of a with b in s.
     */
    public static String replaceFirst(String s, String a, String b)
    {
        int i = s.indexOf(a);
        if ( i < 0 )
            return s;

        return s.substring(0, i) + b + s.substring(i + a.length());
    }

    /**
     * Replace all occurrences of a with b, within s.
     */
    public static String replaceAll(String s, String a, String b)
    {
        return replaceAll(s, a, b, false);
    }

    /**
     * Replace all occurrences of a with b, within s.
     */
    public static String replaceAll(String s, String a, String b, boolean searchReplacements)
    {
        if ( s == null )
            return null;

        int i = 0;
        while ( true )
        {
            i = s.indexOf(a, i);
            if ( i < 0 )
                return s;

            s = s.substring(0, i) + b + s.substring(i + a.length());
            if ( !searchReplacements )
                i += b.length();
        }
    }

    /**
     * Replace all occurrences of a with b, within s.
     */
    public static String replaceAll(String s, char a, String b)
    {
        return replaceAll(s, a + "", b);
    }

    /**
     * Replace all occurrences of a with b, within s.
     */
    public static String replaceAll(String s, char a, char b)
    {
        return replaceAll(s, a + "", b + "");
    }

    /**
     * Replace all occurrences of the map keys with the corresponding values.
     */
    public static String replaceAll(String s, KmMap<String,String> m)
    {
        if ( m == null )
            return s;

        for ( String k : m.getKeys() )
            s = replaceAll(s, k, m.get(k));

        return s;
    }

    //==================================================
    //= string :: ???
    //==================================================

    /**
     * Trim the value, and replace all whitespace runs with a single space.
     * Tabs, CRs, LFs are all converted to spaces.  Multiples spaces are 
     * converted to a single space.
     */
    public static String collapseWhitespace(String s)
    {
        if ( s == null )
            return null;

        s = s.trim();
        s = replaceAll(s, "\t", " ");
        s = replaceAll(s, "\r", " ");
        s = replaceAll(s, "\n", " ");
        s = replaceAll(s, "  ", " ", true);

        return s;
    }

    /**
     * Truncate s to a maximum length of n. If ellipses is true then "..." is
     * appended to the end of the string. The total string length, including
     * ellipses is guaranteed to be <= n.
     */
    public static String truncate(String s, int n, boolean ellipses)
    {
        if ( s == null )
            return null;

        if ( ellipses )
        {
            if ( s.length() <= n )
                return s;

            if ( n <= 3 )
                return repeat(".", n);

            return substringSafe(s, 0, n - 3) + "...";
        }
        return substringSafe(s, 0, n);
    }

    /**
     * Truncate the string to a maximum length of n.
     */
    public static String truncate(String s, int n)
    {
        return truncate(s, n, false);
    }

    /**
     * Returns the last X characters of the string where X is defined by
     * suffixLength. If the string is shorter than the suffix, return the
     * string, otherwise return an empty string. This method is guaranteed to
     * return a string value without error.
     */
    public static String suffix(String s, int suffixLength)
    {
        if ( s == null )
            return "";

        int start = s.length() - suffixLength;
        int end = s.length();

        return substringSafe(s, start, end);
    }

    /**
     * This serves the same purpose as String.substring except that it is
     * guaranteed to return a string value without error.
     */
    public static String substringSafe(String s, int start)
    {
        if ( s == null )
            return "";

        int end = s.length();

        if ( start < 0 )
            start = 0;

        if ( end < start )
            return "";

        return s.substring(start, end);
    }

    /**
     * This serves the same purpose as String.substring except that it is
     * guaranteed to return a string value without error.
     */
    public static String substringSafe(String s, int start, int end)
    {
        if ( s == null )
            return "";

        if ( start < 0 )
            start = 0;

        if ( start >= s.length() )
            return "";

        if ( end < start )
            return "";

        if ( end > s.length() )
            end = s.length();

        return s.substring(start, end);
    }

    //==================================================
    //= string :: testing
    //==================================================

    /**
     * Determine if the character is a letter.
     */
    public static boolean isLetter(char c)
    {
        return LETTERS.indexOf(c) >= 0;
    }

    /**
     * Determine if all of the characters in s are letters. A..Z, a..z.
     */
    public static boolean isAllLetters(String s)
    {
        return containsOnly(s, LETTERS);
    }

    /**
     * Determine if all of the characters in s are either letters or digits.
     */
    public static boolean isAllAlphaNumeric(String s)
    {
        return containsOnly(s, DIGITS_AND_LETTERS);
    }

    /**
     * Determine if all of the characters in s are uppercase letters.
     */
    public static boolean isAllUpperCase(String s)
    {
        return containsOnly(s, UPPERCASE_LETTERS);
    }

    /**
     * Determine if all of the characters in s are lowercase letters.
     */
    public static boolean isAllLowerCase(String s)
    {
        return containsOnly(s, LOWERCASE_LETTERS);
    }

    public static boolean isUpperCase(char c)
    {
        return UPPERCASE_LETTERS.indexOf(c) >= 0;
    }

    /**
     * Determine if the character is a digit.
     */
    public static boolean isDigit(char c)
    {
        return DIGITS.indexOf(c) >= 0;
    }

    /**
     * Determine if all of the characters in s are digits (0..9).
     */
    public static boolean isAllDigits(String s)
    {
        return containsOnly(s, DIGITS);
    }

    /**
     * Determine if the character is a hex digit.
     */
    public static boolean isHexDigit(char c)
    {
        return HEX_CHAR_STRING.indexOf(c) >= 0;
    }

    /**
     * Determine if all of the characters in s are hex digits (0..F).
     */
    public static boolean isAllHexDigits(String s)
    {
        return containsOnly(s, HEX_CHAR_STRING);
    }

    /**
     * Determine if all of the characters in s are either all letters A..Z, a..z or all digits (0..9).
     */
    public static boolean isAllLettersOrDigits(String s)
    {
        return isAllLetters(s) || isAllDigits(s);
    }

    /**
     * Determine if we have all digits with the possibility of a leading - (negative)
     */
    public static boolean isAllDigitsWithOptionalLeadingNegativeCommasAndDecimal(String s)
    {
        if ( isEmpty(s) )
            return true;

        s = stripCharacters(s, CHAR_COMMA);

        if ( countOccurences(s, CHAR_DOT) > 1 )
            return false;

        s = stripCharacters(s, CHAR_DOT);

        char c = s.charAt(0);

        if ( c == CHAR_DASH )
            return isAllDigits(s.substring(1));

        return isAllDigits(s);
    }

    /**
     * Determine if the character is alphanumeric: a..z, A..Z, 0..9.
     */
    public static boolean isAlphaNumeric(char c)
    {
        return DIGITS_AND_LETTERS.indexOf(c) >= 0;
    }

    /**
     * Determine if s contains any of the characters in chars.
     */
    public static boolean containsAny(String s, String chars)
    {
        int n = chars.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = chars.charAt(i);
            if ( s.indexOf(c) >= 0 )
                return true;
        }
        return false;
    }

    /**
     * Determine is s is composed solely of the characters in chars.
     */
    public static boolean containsOnly(String s, String chars)
    {
        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( chars.indexOf(c) < 0 )
                return false;
        }
        return true;
    }

    //==================================================
    //= string :: counts
    //==================================================

    /**
     * determine the number of times a particular character occurs in a given string.
     *
     * @param c the character you want to count.
     * @param s the string you want checked.
     * @return the number of times 'c' occurs in 's'
     */
    public static int countOccurences(String s, char c)
    {
        int n = 0;

        for ( int i = 0; i < s.length(); i++ )
        {
            char sc = s.charAt(i);
            if ( sc == c )
                n++;
        }

        return n;
    }

    /**
     * Count the number of digits in a string.
     */
    public static int countDigitsIn(String s)
    {
        if ( isEmpty(s) )
            return 0;

        int n = 0;
        for ( char c : s.toCharArray() )
            if ( isDigit(c) )
                n++;
        return n;
    }

    /**
     * Count the number of digits to the right of the last decimal.
     */
    public static int countDigitsAfterDecimalIn(String s)
    {
        if ( isEmpty(s) )
            return 0;

        int i = s.lastIndexOf('.');
        if ( i < 0 )
            return 0;

        s = s.substring(i + 1);
        return countDigitsIn(s);
    }

    /**
     * Count the number of digits to the left of the decimal.
     */
    public static int countDigitsBeforeDecimalIn(String s)
    {
        if ( isEmpty(s) )
            return 0;

        int i = s.lastIndexOf('.');
        if ( i < 0 )
            return countDigitsIn(s);

        s = s.substring(0, i + 1);
        return countDigitsIn(s);

    }

    //==================================================
    //= string :: stripping
    //==================================================

    /**
     * Remove leading characters from the string.
     */
    public static String stripLeadingCharacters(String s, char c)
    {
        if ( s == null )
            return null;

        String prefix = c + "";
        while ( s.startsWith(prefix) )
            s = s.substring(1);

        return s;
    }

    public static String stripLeadingZeros(String s)
    {
        return stripLeadingCharacters(s, '0');
    }

    /**
     * Remove trailing characters from the string.
     */
    public static String stripTrailingCharacters(String s, char c)
    {
        if ( s == null )
            return null;

        String suffix = c + "";
        while ( s.endsWith(suffix) )
            s = s.substring(0, s.length() - 1);

        return s;
    }

    /**
     * Remove trailing digits
     */
    public static String stripTrailingDigits(String s)
    {
        if ( s == null )
            return null;

        while ( true )
        {
            int n = s.length();
            if ( n == 0 )
                return s;

            char c = s.charAt(n - 1);
            if ( !isDigit(c) )
                return s;

            s = s.substring(0, n - 1);
        }
    }

    /**
     * Remove all letters from the string.
     */
    public static String stripLetters(String s)
    {
        if ( s == null )
            return null;

        StringBuilder out = new StringBuilder();

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( !isLetter(c) )
                out.append(c);
        }

        return out.toString();
    }

    /**
     * Remove all vowels (a, e, i, o, u) from the string.
     * Strips both upper and lower-case vowels.
     */
    public static String stripVowels(String s)
    {
        if ( s == null )
            return null;

        StringBuilder out = new StringBuilder();

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( !isVowel(c) )
                out.append(c);
        }

        return out.toString();
    }

    /**
     * Remove all characters that are not letters.
     */
    public static String stripNonLetters(String s)
    {
        if ( s == null )
            return null;

        StringBuilder out = new StringBuilder();

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( isLetter(c) )
                out.append(c);
        }

        return out.toString();
    }

    /**
     * Remove all non-digits from the string.
     */
    public static String stripNonDigits(String s)
    {
        if ( s == null )
            return null;

        StringBuilder out = new StringBuilder();

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( isDigit(c) )
                out.append(c);
        }

        return out.toString();
    }

    /**
     * Remove all characters matching ch from the string.
     */
    public static String stripCharacters(String s, int ch)
    {
        if ( s == null )
            return null;

        if ( s.indexOf(ch) < 0 )
            return s;

        StringBuilder out = new StringBuilder();

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( c != ch )
                out.append(c);
        }
        return out.toString();
    }

    /**
     * Remove all spaces from the string. This only strips the 'space'
     * character, other whitespace, such as tabs, is not removed.
     */
    public static String stripSpaces(String s)
    {
        return stripCharacters(s, ' ');
    }

    /**
     * Remove all dashes from the string.
     */
    public static String stripDashes(String s)
    {
        return stripCharacters(s, '-');
    }

    /**
     * Remove all non-alpha numeric characters from the string.
     */
    public static String stripNonAlphaNumeric(String s)
    {
        if ( s == null )
            return null;

        StringBuilder out = new StringBuilder();

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( isAlphaNumeric(c) )
                out.append(c);
        }

        return out.toString();
    }

    /**
     * Remove all of the non printable characters from the string.
     */
    public static String stripNonSingleLinePrintable(String s)
    {
        if ( s == null )
            return null;

        StringBuilder out = new StringBuilder();

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( isSingleLinePrintable(c) )
                out.append(c);
        }

        return out.toString();
    }

    /**
     * Remove all of the non printable characters from the string.
     */
    public static String stripNonMultiLinePrintable(String s)
    {
        if ( s == null )
            return null;

        StringBuilder out = new StringBuilder();

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( isMultiLinePrintable(c) )
                out.append(c);
        }

        return out.toString();
    }

    /**
     * Strip all characters that we do not allow during a form post.
     */
    public static String stripNonFormPostable(String s)
    {
        if ( s == null )
            return null;

        int n = s.length();
        StringBuilder out = new StringBuilder(n);

        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( isFormPostable(c) )
                out.append(c);
        }

        return out.toString();
    }

    //==================================================
    //= string :: tokenize
    //==================================================

    /**
     * Split the source string into pieces, using the delimiter as a separator.
     * None of the split strings will include the delimiter character. The
     * number of pieces will be exactly equals to the number of occurences of
     * delimiter + 1. Only single character delimiters are supported at this
     * time and there is no support for escape characters. Note: this method
     * does not return the same results as StringTokenizer. Parsing "a,b," using
     * "," would result in: "a", "b", "".
     */
    public static KmList<String> tokenize(String s, char delimiter)
    {
        KmList<String> v = new KmList<String>();

        if ( s == null )
            return v;

        char[] arr = s.toCharArray();
        int n = arr.length;
        int i = -1;
        StringBuilder out = new StringBuilder(n);

        while ( true )
        {
            i++;
            if ( i == n )
                break;

            char c = arr[i];
            if ( c == delimiter )
            {
                v.add(out.toString());
                out.setLength(0);
            }
            else
                out.append(c);
        }

        v.add(out.toString());
        return v;
    }

    public static KmList<String> tokenize(String s)
    {
        return tokenize(s, CHAR_COMMA);
    }

    //==================================================
    //= string :: ???
    //==================================================

    /**
     * Replace the non printable characters with a standard place holder.
     */
    public static String replaceNonSingleLinePrintable(String s)
    {
        if ( s == null )
            return null;

        StringBuilder out = new StringBuilder();

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( isSingleLinePrintable(c) )
                out.append(c);
            else
                out.append(CHAR_NON_PRINTABLE);
        }

        return out.toString();
    }

    /**
     * Determine if the character is considered printable on a single line. 
     * Basically, all characters with ascii values between 0x20 and 0xFE (inclusive).
     * Note: CR, LF, and TAB are not considered line printable.
     * See also, isWhitespace, isParagraphPrintable.
     */
    public static boolean isSingleLinePrintable(char c)
    {
        return c >= 32 && c <= 126;
    }

    /**
     * Determine if the character is considered printable across multiple lines. 
     * This includes all of the singleLinePrintable characters, and adds the
     * standard whitespace (CR, LF, TAB).
     */
    public static boolean isMultiLinePrintable(char c)
    {
        return isSingleLinePrintable(c) || isWhitespace(c);
    }

    /**
     * Determine if the character is considered whitespace.
     * Whitespace characters include: space, tab, cr, lf.
     */
    public static boolean isWhitespace(char c)
    {
        if ( c == CHAR_SPACE )
            return true;

        if ( c == CHAR_CR )
            return true;

        if ( c == CHAR_LF )
            return true;

        if ( c == CHAR_TAB )
            return true;

        return false;
    }

    /**
     * Determine if the character is allowed during a form post.
     * We allow all printable and whitespace characters.
     */
    public static boolean isFormPostable(char c)
    {
        return isMultiLinePrintable(c);
    }

    /**
     * Determine if the character is considered Unicode.
     * This criteria is met when the character breaches the high ascii mark (256+)
     */
    public static boolean isUnicode(char c)
    {
        if ( c > 255 )
            return true;

        return false;
    }

    public static boolean isUnicode(String s)
    {
        for ( int i = 0; i < s.length(); i++ )
            if ( isUnicode(s.charAt(i)) )
                return true;

        return false;
    }

    /**
     * Determine if the character is considered High Ascii.
     * This criteria is met when the character is within the high ascii area (128-255)
     */
    public static boolean isHighAscii(char c)
    {
        if ( c > 127 && c < 256 )
            return true;

        return false;
    }

    public static boolean isHighAscii(String s)
    {
        for ( int i = 0; i < s.length(); i++ )
            if ( isHighAscii(s.charAt(i)) )
                return true;

        return false;
    }

    /**
     * Replace all non-alpha numeric characters with the space character; the
     * resulting string will be the same length as the original.
     */
    public static String replaceNonAlphaNumericWithSpace(String s)
    {
        if ( s == null )
            return null;

        StringBuilder out = new StringBuilder();

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( !isAlphaNumeric(c) )
                c = ' ';
            out.append(c);
        }

        return out.toString();
    }

    /**
     * Set the first chracter to uppercase and the rest to lower.
     */
    public static String capitalizeWord(String s)
    {
        if ( s == null )
            return null;

        int n = s.length();
        if ( n == 0 )
            return s;

        if ( n == 1 )
            return s.toUpperCase();

        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     * Capitalize the first letter of each word. Use space, tab, carriage
     * return, and line feed for delimiters.
     */
    public static String capitalizeWords(String s)
    {
        return capitalizeWords(s, " \t\r\n");
    }

    /**
     * Capitalize the first letter of each word based on the delimiters.
     */
    public static String capitalizeWords(String s, String delimiters)
    {
        StringBuilder out = new StringBuilder();

        StringTokenizer st = new StringTokenizer(s, delimiters, true);
        while ( st.hasMoreTokens() )
        {
            String t = st.nextToken();
            out.append(capitalizeWord(t));
        }

        return out.toString();
    }

    /**
     * Set the first character to uppercase leave the rest alone.
     */
    public static String capitalizeFirstLetter(String s)
    {
        if ( s == null )
            return null;

        if ( s.length() == 0 )
            return s;

        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * Set the first character to lowercase leave the rest alone.
     */
    public static String lowercaseFirstLetter(String s)
    {
        if ( s == null )
            return null;

        if ( s.length() == 0 )
            return s;

        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    /**
     * Convert the input string into kneeling camel case, while delimiting on
     * any spaces.
     */
    public static String toKneelingCamelCase(String s)
    {
        StringBuilder out = new StringBuilder();
        boolean newWord = false;

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            if ( c == ' ' )
            {
                newWord = true;
                continue;
            }

            if ( newWord )
                c = Character.toUpperCase(c);

            out.append(c);
            newWord = false;
        }

        return lowercaseFirstLetter(out.toString());
    }

    /**
     * Convert the input string into camel case, while delimiting on any spaces.
     */
    public static String toCamelCase(String s)
    {
        return capitalizeFirstLetter(toKneelingCamelCase(s));
    }

    /**
     * Split a single camel case token into multiple words.  Insert a space before
     * each upper case letter, and then trim the result.
     */
    public static String formatCamelCaseAsWords(String s)
    {
        StringBuilder out = new StringBuilder();

        int n = s.length();
        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);

            if ( isUpperCase(c) )
                out.append(" ");

            out.append(c);
        }

        return out.toString().trim();
    }

    /**
     * Split a single camel case token into multiple words.  Insert a space before
     * each upper case letter, and then trim the result. In the case that the string
     * is a kneeling camel case, capitalize the first word.
     */
    public static String formatCamelCaseAsCapitalizedWords(String s)
    {
        return formatCamelCaseAsWords(capitalizeFirstLetter(s));
    }

    /**
     * Remove all sections of the string that begin with <begin> and end with
     * <end>.
     */
    public static String removeSections(String s, String begin, String end)
    {
        int i = s.indexOf(begin);
        if ( i < 0 )
            return s;

        int j = 0;
        StringBuilder out = new StringBuilder();

        while ( i >= 0 )
        {
            out.append(s.substring(j, i));
            j = s.indexOf(end, i + begin.length());

            if ( j < 0 )
                j = s.length();
            else
                j += end.length();

            i = s.indexOf(begin, j);
        }

        out.append(s.substring(j));
        return out.toString();
    }

    /**
     * Determine if the string begins with an eol pattern. One of: CRLF, CR, LF
     */
    public static boolean hasEndOfLinePrefix(String s)
    {
        if ( s.startsWith(STRING_CR) )
            return true;

        if ( s.startsWith(STRING_LF) )
            return true;

        return false;
    }

    /**
     * Remove a single eol pattern from the beginning of the string. One of:
     * CRLF, CR, LF
     */
    public static String removeEndOfLinePrefix(String s)
    {
        if ( s.startsWith(STRING_CRLF) )
            return removePrefix(s, STRING_CRLF);

        if ( s.startsWith(STRING_CR) )
            return removePrefix(s, STRING_CR);

        if ( s.startsWith(STRING_LF) )
            return removePrefix(s, STRING_LF);

        return s;
    }

    /**
     * Determine if the string begins with an eol pattern. One of: CRLF, CR, LF
     */
    public static boolean hasEndOfLineSuffix(String s)
    {
        if ( s.endsWith(STRING_CR) )
            return true;

        if ( s.endsWith(STRING_LF) )
            return true;

        return false;
    }

    /**
     * Remove a single eol pattern from the end of the string. One of: CRLF, CR,
     * LF
     */
    public static String removeEndOfLineSuffix(String s)
    {
        if ( s.endsWith(STRING_CRLF) )
            return removeSuffix(s, STRING_CRLF);

        if ( s.endsWith(STRING_CR) )
            return removeSuffix(s, STRING_CR);

        if ( s.endsWith(STRING_LF) )
            return removeSuffix(s, STRING_LF);

        return s;
    }

    /**
     * Convert a word to an all uppercase format, preserving word separation
     * using underscores. Spaces and uppercase letters in the original string
     * are assumed to mark word separators.
     */
    public static String formatAsConstant(String s)
    {
        StringBuilder out = new StringBuilder();

        Iterator<String> i = parseNameTokens(s).iterator();
        while ( i.hasNext() )
        {
            out.append(i.next().toUpperCase());
            if ( i.hasNext() )
                out.append("_");
        }

        return out.toString();
    }

    public static String formatAsLowerCaseNames(String name)
    {
        StringBuilder out = new StringBuilder();
        KmList<String> v = parseNameTokens(name);

        Iterator<String> i = v.iterator();
        while ( i.hasNext() )
        {
            out.append(i.next());

            if ( i.hasNext() )
                out.append(" ");
        }

        return out.toString();
    }

    public static String formatAsCapitalizedNames(String name)
    {
        StringBuilder out = new StringBuilder();
        KmList<String> v = parseNameTokens(name);

        Iterator<String> i = v.iterator();
        while ( i.hasNext() )
        {
            String s = capitalizeFirstLetter(i.next());
            out.append(s);

            if ( i.hasNext() )
                out.append(" ");
        }

        return out.toString();
    }

    public static KmList<String> parseNameTokens(String name)
    {
        return KmNameTokenizer.parse(name);
    }

    /**
     * Trim only the leading whitespace from the string. The intent is to use
     * the same rules at String.trim, except trailing whitespace is not
     * affected.
     */
    public static String trimLeading(String s)
    {
        if ( s == null )
            return null;

        char c = '\u0020';
        int i = 0;

        while ( true )
        {
            if ( i == s.length() )
                break;

            if ( s.charAt(i) > c )
                break;

            i++;
        }

        return s.substring(i);
    }

    public static String trim(String s)
    {
        if ( s == null )
            return null;

        return s.trim();
    }

    /**
     * Trim only the trailing whitespace from the string. The intent is to use
     * the same rules at String.trim, except leading whitespace is not affected.
     */
    public static String trimTrailing(String s)
    {
        if ( s == null )
            return null;

        char c = '\u0020';
        int n = s.length();

        while ( true )
        {
            if ( n == 0 )
                break;

            if ( s.charAt(n - 1) > c )
                break;

            n--;
        }

        return s.substring(0, n);
    }

    public static String trimSlashes(String s)
    {
        if ( s == null )
            return null;

        s = removeAllPrefix(s, SLASH);
        s = removeAllSuffix(s, SLASH);

        return s;
    }

    /**
     * Determine if the string starts with whitespace.
     */
    public static boolean hasLeadingWhitespace(String s)
    {
        return s.length() == 0
            ? false
            : s.charAt(0) <= '\u0020';
    }

    /**
     * Determine if the string ends with whitespace.
     */
    public static boolean hasTrailingWhitespace(String s)
    {
        return s.length() == 0
            ? false
            : s.charAt(s.length() - 1) <= '\u0020';
    }

    /**
     * Take a best guess as auto-converting a word to a its plural.
     * E.g:
     * egg   => eggs
     * city  => cities
     * child => children
     */
    public static String pluralize(String s)
    {
        if ( s == null )
            return null;

        int n = s.length();
        if ( n <= 1 )
            return s;

        if ( s.equalsIgnoreCase("child") )
            return s + "ren";

        char last = s.charAt(n - 1);
        if ( last == 's' )
            return s + "es";

        if ( isVowel(last) )
            return s + "s";

        if ( s.endsWith("y") )
        {
            char c = s.charAt(n - 2);
            return isVowel(c)
                ? s + "s"
                : s.substring(0, n - 1) + "ies";
        }

        return s + "s";
    }

    /**
     * Attempt to pluralize the string if i <> 1.
     */
    public static String pluralize(String s, int i)
    {
        return i == 1
            ? s
            : pluralize(s);
    }

    /**
     * Determine if c is a vowel.
     */
    public static boolean isVowel(char c)
    {
        return "aeiouAEIOU".indexOf(c) >= 0;
    }

    /**
     * Determine if c is a consonant.
     */
    public static boolean isConsonant(char c)
    {
        return !isVowel(c);
    }

    public static String escapeXml(String s)
    {
        s = replaceAll(s, "&", "&amp;");
        s = replaceAll(s, "'", "&apos;");
        s = replaceAll(s, "\"", "&quot;");
        s = replaceAll(s, "<", "&lt;");
        s = replaceAll(s, ">", "&gt;");
        return s;
    }

    /**
     * Useful for representing java generic types in xml.
     * E.g.: convert KmList<Person> to KmList(Person)
     */
    public static String escapeXmlType(String s)
    {
        s = replaceAll(s, "<", "(");
        s = replaceAll(s, ">", ")");
        s = escapeXml(s);
        return s;
    }

    /**
     * Return the list of lines (without the line separator characters) using
     * the standard line reading mechanism of BufferedReader.
     */
    public static KmList<String> getLines(String source)
    {
        try
        {
            if ( source == null )
                return new KmList<String>();

            BufferedReader br = new BufferedReader(new StringReader(source));
            KmList<String> v = new KmList<String>();

            while ( true )
            {
                String s = br.readLine();
                if ( s == null )
                    break;

                v.addNonNull(s);
            }

            return v;
        }
        catch ( Exception ex )
        {
            return new KmList<String>();
        }
    }

    /**
     * Count the number of lines found in the source using the 
     * standard line reading mechanism of BufferedReader.
     */
    public static int getLineCount(String source)
    {
        return getLineCount(toBufferedReader(source));
    }

    public static int getLineCount(InputStream source)
    {
        return getLineCount(toBufferedReader(source));
    }

    /**
     * Count the number of lines in the source.
     * This method closes the source when done. 
     */
    public static int getLineCount(Reader source)
    {
        try
        {
            int i = 0;
            BufferedReader r = toBufferedReader(source);

            while ( r.readLine() != null )
                i++;

            return i;
        }
        catch ( Exception ex )
        {
            return 0;
        }
        finally
        {
            closeSafely(source);
        }
    }

    /**
     * Get the first line.
     */
    public static String getFirstLine(String s)
    {
        return getLines(s).getFirstSafe();
    }

    /**
     * Get the last line.
     */
    public static String getLastLine(String s)
    {
        return getLines(s).getLastSafe();
    }

    /**
     * Return the suffix of the string. Up to n characters are returned. If (s ==
     * null) return null. If (s.length < n) return s.
     */
    public static String getSuffix(String s, int n)
    {
        if ( s == null )
            return null;

        if ( s.length() <= n )
            return s;

        return s.substring(s.length() - n);
    }

    /**
     * Remove the blank lines from the string.
     */
    public static String removeBlankLines(String source)
    {
        try
        {
            StringReader sr = new StringReader(source);
            BufferedReader br = new BufferedReader(sr);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

            while ( true )
            {
                String s = br.readLine();

                if ( s == null )
                    break;

                if ( s.trim().length() > 0 )
                    pw.println(s);
            }

            pw.flush();
            return sw.toString();
        }
        catch ( Exception ex )
        {
            return "";
        }
    }

    public static void removeBlankLines(KmList<String> lines)
    {
        Iterator<String> i = lines.iterator();
        while ( i.hasNext() )
        {
            String s = i.next();
            if ( s == null )
            {
                i.remove();
                continue;
            }
            if ( s.trim().length() == 0 )
            {
                i.remove();
                continue;
            }
        }
    }

    /**
     * Remove null and empty values.
     */
    public static void removeEmptyValues(List<String> v)
    {
        Iterator<String> i = v.iterator();
        while ( i.hasNext() )
            if ( isEmpty(i.next()) )
                i.remove();
    }

    public static void removeLinesStartingWith(KmList<String> v, String prefix)
    {
        Iterator<String> i = v.iterator();
        while ( i.hasNext() )
        {
            String s = i.next();
            if ( s.startsWith(prefix) )
                i.remove();
        }
    }

    public static String crop(String s, String prefix, String suffix)
    {
        int i;

        i = s.indexOf(prefix);
        if ( i >= 0 )
            s = s.substring(i + prefix.length());

        i = s.indexOf(suffix);
        if ( i >= 0 )
            s = s.substring(0, i);

        return s;
    }

    /**
     * Get the portion of the source before the specified split.
     * Returns null if the split is not found. 
     */
    public static String getBeforeFirst(String source, String split)
    {
        int i = source.indexOf(split);
        if ( i < 0 )
            return null;

        return source.substring(0, i);
    }

    /**
     * Get the portion of the source after the specified split.
     * Returns null if the split is not found. 
     */
    public static String getAfterFirst(String source, String split)
    {
        int i = source.indexOf(split);
        if ( i < 0 )
            return null;

        return source.substring(i + split.length());
    }

    //##################################################
    //# edit distance
    //##################################################

    /**
     * Compute the Levenshtein "edit" distance. This is the number of changes
     * necessary to convert one string into the other.
     */
    public static int getEditDistance(String s, String t)
    {
        int d[][]; // matrix
        int sn; // length of s
        int tn; // length of t
        int si; // iterates through s
        int ti; // iterates through t
        char sc; // ith character of s
        char tc; // ith character of t
        int cost; // cost

        sn = s.length();
        tn = t.length();

        if ( sn == 0 )
            return tn;

        if ( tn == 0 )
            return sn;

        d = new int[sn + 1][tn + 1];

        for ( si = 0; si <= sn; si++ )
            d[si][0] = si;

        for ( ti = 0; ti <= tn; ti++ )
            d[0][ti] = ti;

        for ( si = 1; si <= sn; si++ )
        {
            sc = s.charAt(si - 1);
            for ( ti = 1; ti <= tn; ti++ )
            {
                tc = t.charAt(ti - 1);

                if ( sc == tc )
                    cost = 0;
                else
                    cost = 1;

                d[si][ti] = min(d[si - 1][ti] + 1, d[si][ti - 1] + 1, d[si - 1][ti - 1] + cost);
            }
        }
        return d[sn][tn] + Math.abs(tn - sn);
    }

    /**
     * Compute the Levenshtein "edit" distance. This is the number of changes
     * necessary to convert one string into the other.
     */
    public static double getAdjustedEditDistance(String s, String t)
    {
        double d[][]; // matrix
        int sn; // length of s
        int tn; // length of t
        int si; // iterates through s
        int ti; // iterates through t
        char sc; // ith character of s
        char tc; // ith character of t
        int cost; // cost

        sn = s.length();
        tn = t.length();

        if ( sn == 0 )
            return tn * 1.01;

        if ( tn == 0 )
            return sn * 1.01;

        d = new double[sn + 1][tn + 1];

        for ( si = 0; si <= sn; si++ )
            d[si][0] = si;

        for ( ti = 0; ti <= tn; ti++ )
            d[0][ti] = ti;

        for ( si = 1; si <= sn; si++ )
        {
            sc = s.charAt(si - 1);
            for ( ti = 1; ti <= tn; ti++ )
            {
                tc = t.charAt(ti - 1);

                if ( sc == tc )
                    cost = 0;
                else
                    cost = 1;

                d[si][ti] = min(d[si - 1][ti] + 1, d[si][ti - 1] + 1, d[si - 1][ti - 1] + cost);
            }
        }

        return d[sn][tn] + Math.abs(tn - sn) * 0.01;
    }

    //##################################################
    //# pad left
    //##################################################

    /**
     * Pad s to a minimum length of n, with prefix spaces.
     */
    public static String padLeft(String s, int n)
    {
        return padLeft(s, n, ' ');
    }

    /**
     * Pad s to a minimum length of n, with the prefix pad.
     */
    public static String padLeft(String s, int n, char pad)
    {
        int padLen = n - s.length();
        if ( n <= 0 )
            return s;

        StringBuilder out = new StringBuilder(n);

        for ( int i = 0; i < padLen; i++ )
            out.append(pad);

        out.append(s);
        return out.toString();
    }

    public static String padLeft(Integer i, int n, char pad)
    {
        return padLeft(i + "", n, pad);
    }

    public static String padLeft(Long i, int n, char pad)
    {
        return padLeft(i + "", n, pad);
    }

    //##################################################
    //# pad right
    //##################################################

    /**
     * Pad s to a minimum length of n, with suffix spaces.
     */
    public static String padRight(String s, int n)
    {
        return padRight(s, n, ' ');
    }

    /**
     * Pad s to a minimum length of n, with the suffix pad.
     */
    public static String padRight(String s, int n, char pad)
    {
        StringBuilder out;
        out = new StringBuilder(n);
        out.append(s);

        while ( out.length() < n )
            out.append(pad);

        return out.toString();
    }

    public static String padRight(Integer i, int n, char pad)
    {
        return padRight(i + "", n, pad);
    }

    public static String padRight(Long i, int n, char pad)
    {
        return padRight(i + "", n, pad);
    }

    //##################################################
    //# convert
    //##################################################

    public static Double toDouble(Float e)
    {
        return e == null
            ? null
            : e.doubleValue();
    }

    public static Float toFloat(Double e)
    {
        return e == null
            ? null
            : e.floatValue();
    }

    //##################################################
    //# file
    //##################################################

    public static String joinPath(String... paths)
    {
        String s = null;

        for ( String e : paths )
            s = join(s, e, "/");

        return s;
    }

    public static String getCananicalPath(File root, String path)
    {
        File f = new File(root, path);
        return getCananicalPath(f);
    }

    public static String getCananicalPath(File file)
    {
        try
        {
            return file.getCanonicalPath();
        }
        catch ( IOException ex )
        {
            throw toRuntime(ex);
        }
    }

    /**
     * Return true iff the external storage is mounted and both readable/writeable.
     */
    public static boolean isExternalStorageAvailable()
    {
        String mounted = Environment.MEDIA_MOUNTED;
        String state = Environment.getExternalStorageState();
        return isEqual(state, mounted);
    }

    /**
     * Return true iff the external storage is mounted and readable.
     */
    public static boolean isExternalStorageAvailableReadOnly()
    {
        String mounted = Environment.MEDIA_MOUNTED_READ_ONLY;
        String state = Environment.getExternalStorageState();
        return isEqual(state, mounted);
    }

    /**
     * Return true if the external storage is readable, can also be writable but this method doesn't care
     */
    public static boolean isExternalStorageReadable()
    {
        return isExternalStorageAvailable() || isExternalStorageAvailableReadOnly();
    }

    /**
     * Create the folder, if it does not exists.
     */
    public static boolean createFolder(String folder)
    {
        File f = new File(folder);

        if ( f.exists() )
            return true;

        if ( !createFolder(f.getParent()) )
            return false;

        return f.mkdir();
    }

    /**
     * Equivalent to readSize(in, true);
     */
    public static int readSize(InputStream in)
    {
        return readSize(in, true);
    }

    /**
     * Read the number of bytes between the current position
     * (usually the beginning) and the end of the stream.
     * If close is true, then the stream will be closed.
     * This may throw a runtime exception.
     */
    public static int readSize(InputStream in, boolean close)
    {
        try
        {
            int size = 0;

            byte[] buf = new byte[1024];
            while ( true )
            {
                int n = in.read(buf);
                if ( n < 0 )
                    break;

                size += n;
            }
            return size;
        }
        catch ( IOException ex )
        {
            throw toRuntime(ex);
        }
        finally
        {
            if ( close )
                closeSafely(in);
        }
    }

    /**
     * Equivalent to readBytes(in, true);
     */
    public static byte[] readBytes(InputStream in)
    {
        return readBytes(in, true);
    }

    /**
     * Read the remaining bytes from the stream.
     * If close is true, then the stream will be closed.
     */
    public static byte[] readBytes(InputStream in, boolean close)
    {
        try
        {
            byte[] buf = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ( true )
            {
                int n = in.read(buf);
                if ( n < 0 )
                    break;

                out.write(buf, 0, n);
            }
            return out.toByteArray();
        }
        catch ( IOException ex )
        {
            throw toRuntime(ex);
        }
        finally
        {
            if ( close )
                closeSafely(in);
        }
    }

    /**
     * Copy all data from the in stream to the out stream.
     * Both streams are closed before the method exits.
     * This method may throw an exception.
     * Return true if the copy is successful.
     */
    public static boolean copyBytes(InputStream in, OutputStream out)
    {
        try
        {
            byte[] arr = new byte[1024];

            while ( true )
            {
                int i = in.read(arr);
                if ( i < 0 )
                    break;

                out.write(arr, 0, i);
            }
            return true;
        }
        catch ( IOException ex )
        {
            throw toRuntime(ex);
        }
        finally
        {
            closeSafely(in);
            closeSafely(out);
        }
    }

    /**
     * review_lucas (steve) this is the download a file utility
     */
    public static void downloadFileFromUrl(String urlString, String fileName)
    {
        try
        {
            URL url = new URL(urlString);

            URLConnection ucon = url.openConnection();

            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, fileName);

            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = ucon.getInputStream();

            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 )
                fileOutput.write(buffer, 0, bufferLength);

            fileOutput.close();
            //            toast(fileName + " Successfully downloaded");
        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    //##################################################
    //# reader tools
    //##################################################

    public static BufferedReader toBufferedReader(Reader e)
    {
        if ( e == null )
            return null;

        if ( e instanceof BufferedReader )
            return (BufferedReader)e;

        return new BufferedReader(e);
    }

    public static BufferedReader toBufferedReader(InputStream e)
    {
        if ( e == null )
            return null;

        return toBufferedReader(new InputStreamReader(e));
    }

    public static BufferedReader toBufferedReader(String s)
    {
        if ( s == null )
            return null;

        return toBufferedReader(new StringReader(s));
    }

    //##################################################
    //# close
    //##################################################

    public static void closeSafely(KmSqlDatabase e)
    {
        if ( e != null )
            e.closeSafely();
    }

    public static void closeSafely(InputStream e)
    {
        try
        {
            if ( e != null )
                e.close();
        }
        catch ( Exception ex )
        {
            // ignored
        }
    }

    public static void closeSafely(OutputStream e)
    {
        try
        {
            if ( e != null )
                e.close();
        }
        catch ( Exception ex )
        {
            // ignored
        }
    }

    public static void closeSafely(Cursor e)
    {
        try
        {
            if ( e != null && !e.isClosed() )
                e.close();
        }
        catch ( Exception ex )
        {
            // ignored
        }
    }

    public static void closeSafely(SQLiteDatabase e)
    {
        try
        {
            if ( e != null && e.isOpen() )
                e.close();
        }
        catch ( Exception ex )
        {
            // ignored
        }
    }

    public static void closeSafely(InsertHelper e)
    {
        try
        {
            if ( e != null )
                e.close();
        }
        catch ( Exception ex )
        {
            // ignored
        }
    }

    public static void closeSafely(AssetFileDescriptor e)
    {
        try
        {
            if ( e != null )
                e.close();
        }
        catch ( Exception ex )
        {
            // ignored
        }
    }

    public static void closeSafely(Reader e)
    {
        try
        {
            if ( e != null )
                e.close();
        }
        catch ( Exception ex )
        {
            // ignored
        }
    }

    public static void closeSafely(Writer e)
    {
        try
        {
            if ( e != null )
                e.close();
        }
        catch ( Exception ex )
        {
            // ignored
        }
    }

    //##################################################
    //# error
    //##################################################

    public static void fatal(String msg, Object... args)
    {
        String s = format(msg, args);
        throw new RuntimeException(s);
    }

    public static RuntimeException createFatal(String msg, Object... args)
    {
        String s = format(msg, args);
        return new RuntimeException(s);
    }

    //##################################################
    //# class
    //##################################################

    public static String getSimpleNameFor(Object e)
    {
        if ( e == null )
            return null;

        if ( e instanceof Class<?> )
            return ((Class<?>)e).getSimpleName();

        return e.getClass().getSimpleName();
    }

    public static String formatActivity(Activity e)
    {
        return formatActivity(e.getClass());
    }

    public static String formatActivity(Class<? extends Activity> c)
    {
        String s;
        s = c.getSimpleName();
        s = removeSuffix(s, "Activity");
        s = removePrefix(s, "My");
        s = removePrefix(s, "Km");
        s = removePrefix(s, "Ty");
        s = removePrefix(s, "Tk");

        return getPascalCaseWords(s).format(" ");
    }

    //##################################################
    //# uid
    //##################################################

    public static String newUid()
    {
        // Manually force to lowercase for consistency.
        return UUID.randomUUID().toString().toLowerCase();
    }

    //##################################################
    //# serialize
    //##################################################

    /**
     * Create a deep copy of the object using the java serialization protocol.
     */
    public static <T extends Serializable> T getSerializedCopy(T e)
    {
        return KmUnchecked.getSerializedCopy(e);
    }

    /**
     * Determine the size (in bytes) of the serialized object.
     */
    public static int getSerializedObjectSize(Serializable o)
    {
        return toSerializedBytes(o).length;
    }

    public static byte[] toSerializedBytes(Serializable o)
    {
        ObjectOutputStream oos = null;
        try
        {
            ByteArrayOutputStream baos;
            baos = new ByteArrayOutputStream();

            oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.flush();

            return baos.toByteArray();
        }
        catch ( Exception ex )
        {
            throw toRuntime(ex);
        }
        finally
        {
            if ( oos != null )
                try
                {
                    oos.close();
                }
                catch ( Exception ex )
                {
                    // ignored
                }
        }
    }

    public static Object fromSerializedBytes(byte[] arr)
    {
        ObjectInputStream ois = null;
        try
        {
            ByteArrayInputStream bais;
            bais = new ByteArrayInputStream(arr);

            ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch ( Exception ex )
        {
            throw toRuntime(ex);
        }
        finally
        {
            if ( ois != null )
                try
                {
                    ois.close();
                }
                catch ( Exception ex )
                {
                    // ignored
                }
        }
    }

    //##################################################
    //# view
    //##################################################

    /**
     * Determine the position of e within its ancestor.
     * Return null if either e or the ancestor are null.
     * Return null if e cannot trace the parent hierarchy.
     */
    public static Point getPositionIn(View ancestor, View child)
    {
        if ( ancestor == null )
            return null;

        int x = 0;
        int y = 0;

        while ( true )
        {
            if ( child == null )
                return null;

            if ( child == ancestor )
                return new Point(x, y);

            x += child.getLeft();
            y += child.getTop();

            if ( child.getParent() instanceof View )
                child = (View)child.getParent();
            else
                return null;
        }
    }

    public static void toggleGone(View view)
    {
        if ( view.isShown() )
            view.setVisibility(View.GONE);
        else
            view.setVisibility(View.VISIBLE);
    }

    public static void toggleHidden(View view)
    {
        if ( view.isShown() )
            view.setVisibility(View.INVISIBLE);
        else
            view.setVisibility(View.VISIBLE);
    }

    //##################################################
    //# collection
    //##################################################

    public static void addPrefixToAll(List<String> v, String prefix)
    {
        if ( v.isEmpty() )
            return;

        if ( isEmpty(prefix) )
            return;

        int n = v.size();
        for ( int i = 0; i < n; i++ )
        {
            String e = v.get(i);
            if ( e != null )
                v.set(i, prefix + e);
        }
    }

    public static void trimValues(List<String> v)
    {
        int n = v.size();
        for ( int i = 0; i < n; i++ )
        {
            String s = v.get(i);
            if ( s != null )
                v.set(i, s.trim());
        }
    }

    /**
     * Return a list of integers from..to (inclusive).
     */
    public static KmList<Integer> range(int from, int to)
    {
        return KmIntegerRange.create(from, to).toList();
    }

    /**
     * Return a list of integers 0..n-1.
     */
    public static KmList<Integer> getIntegers(int n)
    {
        return n >= 0
            ? range(0, n - 1)
            : range(0, n + 1);
    }

    //##################################################
    //# min / max
    //##################################################

    /**
     * Determine the minimum value of two integers. This is only here for
     * consistency. Compare to min(double, double).
     */
    public static int min(int a, int b)
    {
        return Math.min(a, b);
    }

    /**
     * Determine the maximum value of two integers. This is only here for
     * consistency. Compare to max(double, double).
     */
    public static int max(int a, int b)
    {
        return Math.max(a, b);
    }

    /**
     * Determine the minimum value of three integers.
     */
    public static int min(int a, int b, int c)
    {
        return min(a, min(b, c));
    }

    /**
     * Determine the maximum value of three integers.
     */
    public static int max(int a, int b, int c)
    {
        return max(a, max(b, c));
    }

    /**
     * Determine the minimum non-nan value of two doubles. If both are nan, then
     * return nan.
     */
    public static double min(double a, double b)
    {
        if ( isNan(a) )
            return b;

        if ( isNan(b) )
            return a;

        return Math.min(a, b);
    }

    /**
     * Determine the maximum non-nan value of two doubles. If both are nan, then
     * return nan.
     */
    public static double max(double a, double b)
    {
        if ( isNan(a) )
            return b;

        if ( isNan(b) )
            return a;

        return Math.max(a, b);
    }

    /**
     * Determine the minimum non-nan value of three doubles. If all three values
     * are nan, then return nan.
     */
    public static double min(double a, double b, double c)
    {
        return min(a, min(b, c));
    }

    /**
     * Determine the maximum non-nan value of three doubles. If all three values
     * are nan, then return nan.
     */
    public static double max(double a, double b, double c)
    {
        return max(a, max(b, c));
    }

    public static KmDate min(KmDate a, KmDate b)
    {
        if ( a == null )
            return b;

        if ( b == null )
            return a;

        if ( a.isBefore(b) )
            return a;

        return b;
    }

    public static KmDate max(KmDate a, KmDate b)
    {
        if ( a == null )
            return b;

        if ( b == null )
            return a;

        if ( a.isAfter(b) )
            return a;

        return b;
    }

    public static int constrain(int e, int min, int max)
    {
        if ( e < min )
            e = min;

        if ( e > max )
            e = max;

        return e;
    }

    public static long constrain(long e, long min, long max)
    {
        if ( e < min )
            e = min;

        if ( e > max )
            e = max;

        return e;
    }

    public static double constrain(double e, double min, double max)
    {
        if ( e < min )
            e = min;

        if ( e > max )
            e = max;

        return e;
    }

    //##################################################
    //# math
    //##################################################

    /**
     * Determine if the integer is even.
     */
    public static boolean isEven(int i)
    {
        return i % 2 == 0;
    }

    /**
     * Determine if the integer is odd.
     */
    public static boolean isOdd(int i)
    {
        return !isEven(i);
    }

    /**
     * This is a convenience wrapper for Double.isNaN(d)
     */
    public static boolean isNan(double d)
    {
        return Double.isNaN(d);
    }

    /**
     * Convert the byte -128..127 to an unsigned integer in the range 0..255.
     */
    public static int unsigned(byte b)
    {
        return (b >> 4 & 0x0F) << 4 | b & 0x0F;
    }

    //##################################################
    //# parse integer
    //##################################################

    /**
     * Parse an integer and return the value wrapped as an instance. Return null
     * if an error occurs.
     */
    public static Integer parseInteger(String s)
    {
        return parseInteger(s, null);
    }

    /**
     * Parse an integer and return the value wrapped as an instance. Return the
     * default value if an error occurs.
     */
    public static Integer parseInteger(String s, Integer def)
    {
        try
        {
            if ( s == null )
                return def;

            s = s.trim();
            if ( s.length() == 0 )
                return def;

            s = stripLeadingZeros(s);
            if ( s.length() == 0 )
                return 0;

            return new Integer(s);
        }
        catch ( NumberFormatException ex )
        {
            return def;
        }
    }

    /**
     * Parse a string into an integer. If an error occurs, return
     * KmConstantsIF.UNDEFINED_INT.
     */
    public static int parse_int(String s)
    {
        return parse_int(s, UNDEFINED_INT);
    }

    /**
     * Parse a string into an integer. If an error occurs, return the default
     * value.
     */
    public static int parse_int(String s, int def)
    {
        return parseInteger(s, def);
    }

    //##################################################
    //# parse long
    //##################################################

    /**
     * Parse a long and return the value wrapped as an instance. Return null
     * if an error occurs.
     */
    public static Long parseLong(String s)
    {
        return parseLong(s, null);
    }

    /**
     * Parse a long and return the value wrapped as an instance. Return the
     * default value if an error occurs.
     */
    public static Long parseLong(String s, Long def)
    {
        try
        {
            if ( s == null )
                return def;

            s = s.trim();
            s = stripLeadingZeros(s);
            return new Long(s);
        }
        catch ( NumberFormatException ex )
        {
            return def;
        }
    }

    /**
     * Parse a string into an integer. If an error occurs, return
     * KmConstantsIF.UNDEFINED_LONG.
     */
    public static long parse_long(String s)
    {
        return parse_long(s, UNDEFINED_LONG);
    }

    /**
     * Parse a string into a long. If an error occurs, return the default
     * value.
     */
    public static long parse_long(String s, long def)
    {
        return parseLong(s, def);
    }

    //##################################################
    //# parse double
    //##################################################

    /**
     * Parse a string into a double. If an error occurs, return null.
     */
    public static Double parseDouble(CharSequence text)
    {
        return parseDouble(text, null);
    }

    /**
     * Parse a string into a double. If an error occurs, return the default
     * value.
     */
    public static Double parseDouble(CharSequence text, Double def)
    {
        try
        {
            if ( text == null )
                return def;

            String s = text.toString().trim();
            return new Double(s);
        }
        catch ( NumberFormatException ex )
        {
            return def;
        }
    }

    /**
     * Parse a string into a double. If an error occurs, return Nan.
     */
    public static double parse_double(String s)
    {
        return parse_double(s, Double.NaN);
    }

    /**
     * Parse a string into a double. If an error occurs, return the default
     * value.
     */
    public static double parse_double(String s, double def)
    {
        return parseDouble(s, def);
    }

    /**
     * Parse the string into a boolean. Return null if an error occurs.
     */
    public static Boolean parseBoolean(String s)
    {
        return parseBoolean(s, null);
    }

    /**
     * Parse the string into a boolean. Return the default value if an error
     * occurs.
     */
    public static Boolean parseBoolean(String s, Boolean def)
    {
        if ( s == null )
            return def;

        s = s.toLowerCase();
        if ( s.equals("t") )
            return true;

        if ( s.equals("true") )
            return true;

        if ( s.equals("y") )
            return true;

        if ( s.equals("yes") )
            return true;

        if ( s.equals("on") )
            return true;

        if ( s.equals("1") )
            return true;

        if ( s.equals("f") )
            return false;

        if ( s.equals("false") )
            return false;

        if ( s.equals("n") )
            return false;

        if ( s.equals("no") )
            return false;

        if ( s.equals("off") )
            return false;

        if ( s.equals("0") )
            return false;

        return def;
    }

    /**
     * Parse the string into a boolean.
     * Return false if an error occurs.
     */
    public static boolean parse_boolean(String s)
    {
        return parse_boolean(s, false);
    }

    /**
     * Parse the string into a boolean.
     * Return the default value if an error occurs.
     */
    public static boolean parse_boolean(String s, boolean def)
    {
        return parseBoolean(s, def);
    }

    //##################################################
    //# compare
    //##################################################

    /**
     * A convenience method to compare a to b since it is easy to miswrite the
     * comparison logic.
     */
    public static int compare(long a, long b)
    {
        if ( a < b )
            return -1;

        if ( a > b )
            return 1;

        return 0;
    }

    /**
     * A convenience method to compare a to b since it is easy to miswrite the
     * comparison logic.
     */
    public static int compare(double a, double b)
    {
        if ( a < b )
            return -1;

        if ( a > b )
            return 1;

        return 0;
    }

    //##################################################
    //# html
    //##################################################

    public static String escapeHtml(String s)
    {
        s = replaceAll(s, "&", "&amp;");
        s = replaceAll(s, "'", "&apos;");
        s = replaceAll(s, "\"", "&quot;");
        s = replaceAll(s, "<", "&lt;");
        s = replaceAll(s, ">", "&gt;");
        s = replaceAll(s, "\r\n", "<br>");
        s = replaceAll(s, "\r", "<br>");
        s = replaceAll(s, "\n", "<br>");
        return s;
    }

    //##################################################
    //# join (html style)
    //##################################################

    public static String joinText(String prefix, String suffix, String separator)
    {
        if ( prefix == null )
            prefix = "";

        if ( suffix == null )
            suffix = "";

        if ( separator == null )
            separator = "";

        if ( prefix.length() == 0 )
            return suffix;

        if ( suffix.length() == 0 )
            return prefix;

        if ( prefix.endsWith(separator) || suffix.startsWith(separator) )
            return prefix + suffix;

        return prefix + separator + suffix;
    }

    public static String joinHtmlStyle(String css, String next)
    {
        return joinText(css, next, ";");
    }

    public static String joinHtmlStyle(String css, String nextAttr, String nextValue)
    {
        String next = nextAttr + ":" + nextValue;
        return joinHtmlStyle(css, next);
    }

    public static String joinHtmlStyle(String css, String nextAttr, Integer nextValue)
    {
        String nextUnit = null;
        return joinHtmlStyle(css, nextAttr, nextValue, nextUnit);
    }

    public static String joinHtmlStyle(
        String css,
        String nextAttr,
        Integer nextValue,
        String nextUnit)
    {
        String s = nextValue + "";

        if ( nextUnit != null )
            s += nextUnit;

        return joinHtmlStyle(css, nextAttr, s);
    }

    //##################################################
    //# encode
    //##################################################

    public static String encodeUtf8(String s)
    {
        try
        {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch ( Exception ex )
        {
            throw toRuntime(ex);
        }
    }

    public static String decodeUtf8(String s)
    {
        try
        {
            return URLDecoder.decode(s, "UTF-8");
        }
        catch ( Exception ex )
        {
            throw toRuntime(ex);
        }
    }

    public static String encodeIso8869(String s)
    {
        try
        {
            return URLEncoder.encode(s, "ISO-8859-1");
        }
        catch ( Exception ex )
        {
            throw toRuntime(ex);
        }
    }

    public static String decodeIso8869(String s)
    {
        try
        {
            return URLDecoder.decode(s, "ISO-8859-1");
        }
        catch ( Exception ex )
        {
            throw toRuntime(ex);
        }
    }

    //##################################################
    //# class names
    //##################################################

    /**
     * Return the package name of the class.
     */
    public static String getPackageName(Object e)
    {
        if ( e == null )
            return "null";

        Class<?> c = e instanceof Class<?>
            ? (Class<?>)e
            : e.getClass();

        return getPackageName(c);
    }

    /**
     * Return the package name of the class.
     */
    public static String getPackageName(Class<?> e)
    {
        if ( e == null )
            return "null";

        return e.getPackage().getName();
    }

    //##################################################
    //# class resource
    //##################################################

    /**
     * Read a resource from the class path.
     * For example: to read a file named "abc.txt"
     * located in the package com.kodemore you could use:
     * Kmu.readClassResource("com/kodemore/abc.txt");
     */
    public static byte[] readClassResource(String path)
    {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try
        {
            URL url = ClassLoader.getSystemResource(path);
            in = url.openStream();
            out = new ByteArrayOutputStream();

            while ( true )
            {
                int b = in.read();
                if ( b < 0 )
                    break;

                out.write(b);
            }

            return out.toByteArray();
        }
        catch ( IOException ex )
        {
            KmLog.error("Cannot read class resource(%s)", path);
            return null;
        }
        finally
        {
            closeSafely(in);
            closeSafely(out);
        }
    }

    public static String readClassString(String path)
    {
        byte[] bytes = readClassResource(path);
        if ( bytes == null )
            return null;

        return new String(bytes);
    }

    //##################################################
    //# file
    //##################################################

    /**
     * Determine if a path matches the pattern.
     * 
     * The path follow the form: a/b/c.
     * The path must NOT contain the @ character.  
     * Backslashes are converted to forward slashes.
     *
     * NOTE: the pattern matches on * (not @),
     * but I cannot put the stars in the comment.
     * So USE *'s!
     * 
     * The pattern follows the general form of
     *      a/b/c/@/d/@@/e
     * Where * matches EXACTLY ONE element;
     * And  ** matches ZERO to MANY elements.
     */
    public static boolean matchesPath(String path, String pattern)
    {
        path = replaceAll(path, BACKSLASH, SLASH);

        String r;
        r = pattern;
        r = replaceAll(r, "*", "@");
        r = replaceAll(r, "/@@/", "/.*/");
        r = replaceAll(r, "@@/", "([^/]*/)*");
        r = replaceAll(r, "/@@", "(/[^/]*)*");
        r = replaceAll(r, "@/", "[^/]*/");
        r = replaceAll(r, "/@", "/[^/]*");
        r = "^" + r + "$";

        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(path);

        return m.matches();
    }

    //##################################################
    //# assets
    //##################################################

    public static String readAssetText(String name)
    {
        byte[] arr = readAssetBytes(name);
        if ( arr == null )
            return null;

        return new String(arr);
    }

    public static byte[] readAssetBytes(String name)
    {
        InputStream is = null;

        try
        {
            is = getApplicationContext().getAssets().open(name);
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ( true )
            {
                int i = bis.read();
                if ( i < 0 )
                    break;

                out.write(i);
            }
            return out.toByteArray();
        }
        catch ( IOException ex )
        {
            return null;
        }
        finally
        {
            if ( is != null )
                try
                {
                    is.close();
                }
                catch ( Exception ex )
                {
                    // ignored
                }
        }
    }

    //##################################################
    //# email
    //##################################################

    public static final boolean isEmailSupported()
    {
        Intent intent;
        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");

        return new KmPackageManager().supports(intent);
    }

    //##################################################
    //# service
    //##################################################

    public static boolean isServiceRunning(Class<? extends Service> c)
    {
        KmList<RunningServiceInfo> v = getRunningServiceInfos();
        for ( RunningServiceInfo e : v )
            if ( c.getName().equals(e.service.getClassName()) )
                return true;

        return false;
    }

    public static KmList<RunningServiceInfo> getRunningServiceInfos()
    {
        Context ctx;
        ctx = getApplicationContext();

        ActivityManager mgr;
        mgr = (ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);

        List<RunningServiceInfo> v;
        v = mgr.getRunningServices(Integer.MAX_VALUE);

        return new KmList<RunningServiceInfo>(v);
    }

    //##################################################
    //# images
    //##################################################

    public static Bitmap decodeImage(int resId)
    {
        Resources r = getApplicationContext().getResources();
        return BitmapFactory.decodeResource(r, resId);
    }

    //##################################################
    //# support
    //##################################################

    private static KmBridge getBridge()
    {
        return KmBridge.getInstance();
    }

    private static Context getApplicationContext()
    {
        return getBridge().getApplicationContext();
    }

    //##################################################
    //# boolean 
    //##################################################

    public static boolean isTrue(Boolean e)
    {
        return isTrue(e, false);
    }

    public static boolean isTrue(Boolean e, boolean def)
    {
        return e == null
            ? def
            : e;
    }

    //##################################################
    //# bitwise operations
    //##################################################

    public static int addBitFlagsTo(int source, int flags)
    {
        return source | flags;
    }

    public static int removeBitFlagsFrom(int source, int flags)
    {
        return source & ~flags;
    }

    //##################################################
    //# radix
    //##################################################

    /**
     * Convert the integer to a string representation.  The chars
     * parameter defines the characters used and also implies the
     * radix (chars.length).
     */
    public static String formatRadixString(long i, char[] radixChars)
    {
        int radix = radixChars.length;
        char[] buf = new char[65]; // big enough for radix-2 (64) plus sign
        int charPos = 64;
        boolean negative = i < 0;

        if ( !negative )
            i = -i;

        while ( i <= -radix )
        {
            int j = (int)-(i % radix);
            buf[charPos--] = radixChars[j];
            i = i / radix;
        }

        buf[charPos] = radixChars[(int)-i];

        if ( negative )
            buf[--charPos] = CHAR_DASH;

        return new String(buf, charPos, 65 - charPos);
    }

    /**
     * Convert a string in the integer to a string representation.  The chars
     * parameter defines the characters used and also implies the
     * radix (chars.length).
     */
    public static long parseRadixString(String s, char[] radixChars)
    {
        int radix = radixChars.length;
        long result = 0;
        int n = s.length();

        for ( int i = 0; i < n; i++ )
        {
            char c = s.charAt(i);
            result *= radix;
            result += indexOf(radixChars, c);
        }

        return result;
    }

    public static String formatBase62(long i)
    {
        return formatRadixString(i, BASE_62_ARRAY);
    }

    public static long parseBase62(String s)
    {
        return parseRadixString(s, BASE_62_ARRAY);
    }

    public static String formatBase36(long i)
    {
        return formatRadixString(i, BASE_36_ARRAY);
    }

    public static long parseBase36(String s)
    {
        return parseRadixString(s, BASE_36_ARRAY);
    }

    public static String formatBase20(long i)
    {
        return formatRadixString(i, BASE_20_ARRAY);
    }

    public static long parseBase20(String s)
    {
        return parseRadixString(s, BASE_20_ARRAY);
    }

    //##################################################
    //# hex
    //##################################################

    /**
     * Return the 2-character hex code for this byte. Note, the return string is
     * always two characters even if the leading character (or both) are zero.
     */
    public static String formatHexString(byte b)
    {
        return "" + HEX_CHAR_ARRAY[b >> 4 & 15] + HEX_CHAR_ARRAY[b & 15];
    }

    /**
     * Return the hex string for the byte array. Each byte is converted to a
     * 2-byte hex string.  The bytes are printed with no separator.
     */
    public static String formatHexString(String s)
    {
        return formatHexString(s.getBytes());
    }

    /**
     * Return the hex string for the byte array. Each byte is converted to a
     * 2-byte hex string.
     */
    public static String formatHexString(String s, String separator)
    {
        return formatHexString(s.getBytes(), separator);
    }

    /**
     * Return the hex string for the byte array. Each byte is converted to a
     * 2-byte hex string.  The bytes are printed with no separator.
     */
    public static String formatHexString(byte[] arr)
    {
        return formatHexString(arr, Integer.MAX_VALUE);
    }

    /**
     * Return the hex string for the byte array. Each byte is converted to a
     * 2-byte hex string.  Only the first maxBytes are displayed.
     */
    public static String formatHexString(byte[] arr, int maxBytes)
    {
        return formatHexString(arr, maxBytes, null);
    }

    public static String formatHexString(byte[] arr, String separator)
    {
        return formatHexString(arr, Integer.MAX_VALUE, separator);
    }

    public static String formatHexString(byte[] arr, int maxBytes, String separator)
    {
        StringBuilder out = new StringBuilder();

        int n = Math.min(arr.length, maxBytes);
        for ( int i = 0; i < n; i++ )
        {
            byte b = arr[i];
            String c = formatHexString(b);
            out.append(c);

            if ( separator != null && i < n - 1 )
                out.append(separator);
        }

        return out.toString();
    }

    /**
     * Parse a hex string into a byte array. Assumes that every all characters
     * are valid hex characters 0..F. Upper and lowercase are accepted. Assumes
     * no spaces or other delimiters between the hex pairs.
     */
    public static byte[] parseHexBytes(String s)
    {
        s = s.toUpperCase();
        int n = s.length() / 2;
        byte[] arr = new byte[n];

        for ( int i = 0; i < n; i++ )
        {
            char c;
            byte b;

            c = s.charAt(i * 2);
            b = (byte)HEX_CHAR_STRING.indexOf(c);

            c = s.charAt(i * 2 + 1);
            b = (byte)(b << 4 | (byte)HEX_CHAR_STRING.indexOf(c));

            arr[i] = b;
        }
        return arr;
    }

    /**
     * Parse a hex string into a single byte.  The string must be exactly two
     * characters long.  Valid hex characters 0..F. Upper and lowercase are accepted.
     */
    public static byte parseHexByte(String s)
    {
        if ( s.length() != 2 )
            fatal("Hex string must be exactly two characters.");

        s = s.toUpperCase();

        char c;
        byte b;

        c = s.charAt(0);
        b = (byte)HEX_CHAR_STRING.indexOf(c);

        c = s.charAt(1);
        b = (byte)(b << 4 | (byte)HEX_CHAR_STRING.indexOf(c));

        return b;
    }

    //##################################################
    //# misc
    //##################################################

    public static String toCode(KmCodedEnumIF e)
    {
        return e == null
            ? null
            : e.getCode();
    }

    public static Throwable getStackTrace()
    {
        try
        {
            throw new RuntimeException();
        }
        catch ( Exception ex )
        {
            return ex;
        }
    }

    public static long indexOf(char[] arr, char c)
    {
        int n = arr.length;
        for ( int i = 0; i < n; i++ )
            if ( arr[i] == c )
                return i;

        return -1;
    }

    public static void error(String msg, Object... args)
    {
        throw new KmApplicationException(msg, args);
    }
}
