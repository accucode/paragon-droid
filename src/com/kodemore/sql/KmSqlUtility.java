package com.kodemore.sql;

import com.kodemore.time.KmDate;
import com.kodemore.time.KmTimestamp;

public abstract class KmSqlUtility
    implements KmSqlConstantsIF
{
    //##################################################
    //# date
    //##################################################

    public static KmDate msToDate(Long ms)
    {
        return ms == null
            ? null
            : KmDate.createFromMsSince1970(ms);
    }

    public static Long dateToMs(KmDate e)
    {
        return e == null
            ? null
            : e.getMsSince1970();
    }

    public static KmTimestamp msToTimestamp(Long ms)
    {
        return ms == null
            ? null
            : KmTimestamp.createFromMsSince1970(ms);
    }

    public static Long timestampToMs(KmTimestamp e)
    {
        return e == null
            ? null
            : e.getMsSince1970();
    }

    //##################################################
    //# quote
    //##################################################

    /**
     * Return the string literal, escaped, and then wrapped
     * in quotes.
     */
    public static String quote(String s)
    {
        return STRING_LITERAL + escape(s) + STRING_LITERAL;
    }

    /**
     * Apply any escape sequence necessary to make the string
     * value safe for sql.  This does NOT wrap the value in
     * quotes.
     */
    public static String escape(String s)
    {
        if ( s.indexOf(STRING_LITERAL) < 0 )
            return s;

        StringBuilder out = new StringBuilder();

        for ( char c : s.toCharArray() )
        {
            if ( c == STRING_LITERAL )
                out.append(STRING_ESCAPE);

            out.append(c);
        }

        return out.toString();
    }

    //##################################################
    //# quote like
    //##################################################

    public static String quoteLike(String s)
    {
        return STRING_LITERAL + escapeLike(s) + STRING_LITERAL;
    }

    public static String escapeLike(CharSequence s)
    {
        StringBuilder out = new StringBuilder();

        for ( char c : s.toString().toCharArray() )
        {
            if ( c == LIKE_MANY )
                out.append(LIKE_ESCAPE);

            if ( c == LIKE_ONE )
                out.append(LIKE_ESCAPE);

            if ( c == LIKE_ESCAPE )
                out.append(LIKE_ESCAPE);

            if ( c == STRING_LITERAL )
                out.append(STRING_ESCAPE);

            out.append(c);
        }

        return out.toString();
    }

    //##################################################
    //# format
    //##################################################

    public static String formatColumn(String table, KmSqlColumnIF col)
    {
        return table == null
            ? col.getName()
            : table + "." + col.getName();
    }

}
