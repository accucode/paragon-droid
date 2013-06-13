package com.kodemore.sql;

import com.kodemore.collection.KmList;
import com.kodemore.file.KmFilePath;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.Kmu;

/**
 * A simple parser that reads a text file and converts
 * it into a list of sql statements.
 * 
 * Split into multiple statements using semicolons as 
 * delimiters when found at end-of-line.
 * 
 * Ignore blank lines.
 * Ignore lines starting with # (comments).
 * 
 * Whitespace is trimmed at end of lines.
 * Whitespace is NOT trimmed at start of lines.
 */
public class KmSqlParser
{
    //##################################################
    //# static 
    //##################################################

    public static KmList<String> parseFile(KmFilePath path)
    {
        KmSqlParser e;
        e = new KmSqlParser();
        e.setSourceFile(path);
        e.parse();
        return e.getStatements();
    }

    //##################################################
    //# variables
    //##################################################

    private String         _source;
    private KmList<String> _statements;

    //##################################################
    //# constructor
    //##################################################

    public KmSqlParser()
    {
        _statements = new KmList<String>();
    }

    //##################################################
    //# accessing
    //##################################################

    public String getSource()
    {
        return _source;
    }

    public void setSource(String s)
    {
        _source = s;
    }

    public void setSourceFile(KmFilePath e)
    {
        String s = e.readString();
        setSource(s);
    }

    public KmList<String> getStatements()
    {
        return _statements;
    }

    //##################################################
    //# parse 
    //##################################################

    public void parse()
    {
        _statements.clear();

        KmStringBuilder out = new KmStringBuilder();

        for ( String line : getLines() )
            parseLine(line, out);

        addStatement(out);
    }

    //##################################################
    //# parse (support)
    //##################################################

    private KmList<String> getLines()
    {
        return Kmu.getLines(_source);
    }

    private void parseLine(String line, KmStringBuilder out)
    {
        final String semicolon = ";";

        line = normalize(line);

        if ( isBlank(line) )
            return;

        if ( isComment(line) )
            return;

        boolean endOfStatement = line.endsWith(semicolon);
        if ( endOfStatement )
            line = Kmu.removeSuffix(line, semicolon);

        out.println(line);

        if ( endOfStatement )
            addStatement(out);
    }

    private String normalize(String s)
    {
        return Kmu.trimTrailing(s);
    }

    private boolean isBlank(String s)
    {
        return s.trim().length() == 0;
    }

    private boolean isComment(String s)
    {
        return s.startsWith("#");
    }

    private void addStatement(KmStringBuilder out)
    {
        String s = out.toString();

        out.clear();

        if ( s.trim().length() > 0 )
            _statements.add(s);
    }

}
