package com.kodemore.googleDocs;

import java.net.URL;

import com.google.gdata.client.DocumentQuery;

import com.kodemore.utility.Kmu;

public class KmGoogleDocsQuery
{
    //##################################################
    //# constants
    //##################################################//

    protected static final String DEFAULT = "https://docs.google.com/feeds/default/private/full";

    //##################################################
    //# variables
    //##################################################//

    private DocumentQuery         _query;
    private Exception             _exception;

    //##################################################
    //# constructor
    //##################################################//

    public KmGoogleDocsQuery()
    {
        _query = new DocumentQuery(newUrl(DEFAULT));
    }

    //##################################################
    //# accessing
    //##################################################//

    public Exception getException()
    {
        return _exception;
    }

    public boolean hasException()
    {
        return getException() != null;
    }

    public DocumentQuery getQuery()
    {
        return _query;
    }

    public void setUrl(String url)
    {
        setUrl(newUrl(url));
    }

    public void setUrl(URL url)
    {
        _query = new DocumentQuery(url);
    }

    public void setSortMode(String mode)
    {
        _query.setSortMode(mode);
    }

    public void setTitleQuery(String title)
    {
        _query.setTitleQuery(title);
    }

    public void setTitleExact(boolean value)
    {
        _query.setTitleExact(value);
    }

    public void setMaxResults(int number)
    {
        _query.setMaxResults(number);
    }

    public void setStrict(boolean value)
    {
        _query.setStrict(value);
    }

    public void setFullTextSearch(String query)
    {
        _query.setFullTextQuery(query);
    }

    //##################################################
    //# utility
    //##################################################//

    protected URL newUrl(String urlString)
    {
        try
        {
            String s = Kmu.encodeUtf8(urlString);
            return new URL(s);
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return null;
    }
}
