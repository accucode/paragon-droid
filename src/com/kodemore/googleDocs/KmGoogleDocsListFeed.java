package com.kodemore.googleDocs;

import java.net.URL;

import com.google.gdata.client.ClientLoginAccountType;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthSigner;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.docs.DocumentListFeed;

import com.kodemore.utility.Kmu;

public class KmGoogleDocsListFeed
{
    //##################################################
    //# constants
    //##################################################//

    protected static final String DEFAULT             = "https://docs.google.com/feeds/default/private/full";
    protected static final String QUERY               = "/-/";
    protected static final String ALL_FOLDERS_QUERY   = "folder?showfolders=true";
    protected static final String SPREADSHEETS_QUERY  = "spreadsheet";
    protected static final String WORD_DOCS_QUERY     = "document";
    protected static final String PRESENTATIONS_QUERY = "presentation/mine";

    //##################################################
    //# variables
    //##################################################//

    private DocsService           _service;
    private Exception             _exception;

    //##################################################
    //# constructor
    //##################################################//

    public KmGoogleDocsListFeed(String applicationName)
    {
        try
        {
            _service = new DocsService(applicationName);
            _service.setProtocolVersion(DocsService.Versions.V3);
        }
        catch ( Exception ex )
        {
            setException(ex);
        }
    }

    //##################################################
    //# authentication
    //##################################################//

    public void setLoginCredentials(String userName, String password)
    {
        setLoginCredentials(userName, password, ClientLoginAccountType.HOSTED_OR_GOOGLE);
    }

    public void setLoginCredentials(String userName, String password, ClientLoginAccountType type)
    {
        try
        {
            _service.setUserCredentials(userName, password, type);
        }
        catch ( Exception ex )
        {
            setException(ex);
        }
    }

    public void setOAuthCredentials(GoogleOAuthParameters parameters, OAuthSigner signer)
    {
        try
        {
            _service.setOAuthCredentials(parameters, signer);
        }
        catch ( Exception ex )
        {
            setException(ex);
        }
    }

    //##################################################
    //# subclass access
    //##################################################//

    protected void setException(Exception ex)
    {
        _exception = ex;
    }

    protected DocsService getService()
    {
        return _service;
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

    public DocumentListFeed getDocuments()
    {
        URL feedUrl = newUrl(DEFAULT);

        return getFeed(feedUrl);
    }

    public DocumentListFeed getDocuments(String directory)
    {
        URL feedUrl = newUrl(DEFAULT + QUERY + directory);

        return getFeed(feedUrl);
    }

    public DocumentListFeed getSpreadsheets()
    {
        URL feedUrl = newUrl(DEFAULT + QUERY + SPREADSHEETS_QUERY);

        return getFeed(feedUrl);
    }

    public DocumentListFeed getPresentations()
    {
        URL feedUrl = newUrl(DEFAULT + QUERY + PRESENTATIONS_QUERY);

        return getFeed(feedUrl);
    }

    public DocumentListFeed getWordDocuments()
    {
        URL feedUrl = newUrl(DEFAULT + QUERY + WORD_DOCS_QUERY);

        return getFeed(feedUrl);
    }

    public DocumentListFeed getFolders()
    {
        URL feedUrl = newUrl(DEFAULT + QUERY + ALL_FOLDERS_QUERY);

        return getFeed(feedUrl);
    }

    public DocumentListFeed getDocuments(KmGoogleDocsQuery query)
    {
        return getFeed(query);
    }

    //##################################################
    //# utility
    //##################################################//

    protected URL newUrl(String urlString)
    {
        try
        {
            return new URL(Kmu.encodeUtf8(urlString));
        }
        catch ( Exception ex )
        {
            setException(ex);
        }

        return null;
    }

    protected DocumentListFeed getFeed(URL url)
    {
        try
        {
            DocumentListFeed feed = _service.getFeed(url, DocumentListFeed.class);

            return feed;
        }
        catch ( Exception ex )
        {
            setException(ex);
        }

        return null;
    }

    protected DocumentListFeed getFeed(KmGoogleDocsQuery query)
    {
        try
        {
            DocumentListFeed feed = _service.getFeed(query.getQuery(), DocumentListFeed.class);

            return feed;
        }
        catch ( Exception ex )
        {
            setException(ex);
        }

        return null;
    }
}
