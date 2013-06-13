package com.kodemore.sql;

import com.kodemore.utility.KmConstantsIF;

public interface KmSqlConstantsIF
{
    char   STRING_LITERAL = KmConstantsIF.CHAR_TICK;
    char   STRING_ESCAPE  = KmConstantsIF.CHAR_TICK;

    char   LIKE_ESCAPE    = KmConstantsIF.CHAR_BACKSLASH;
    char   LIKE_MANY      = KmConstantsIF.CHAR_PERCENT;
    char   LIKE_ONE       = KmConstantsIF.CHAR_UNDERSCORE;

    String SPACE          = " ";
    String COMMA          = ",";
    String AND            = "and";
    String OR             = "or";
    String OPEN_PAREN     = "(";
    String CLOSE_PAREN    = ")";
}
