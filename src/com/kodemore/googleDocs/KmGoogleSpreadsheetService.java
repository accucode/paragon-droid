package com.kodemore.googleDocs;

import java.net.URL;

import com.google.gdata.client.ClientLoginAccountType;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

import com.kodemore.collection.KmList;

public class KmGoogleSpreadsheetService
{
    //##################################################
    //# constants
    //##################################################

    private static final String DEFAULT_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

    //##################################################
    //# variables
    //##################################################

    private SpreadsheetService  _spreadsheetService;
    private SpreadsheetFeed     _feed;

    private URL                 _url;
    private Exception           _exception;

    //##################################################
    //# constructor
    //##################################################

    public KmGoogleSpreadsheetService(String applicationName)
    {
        try
        {
            _spreadsheetService = new SpreadsheetService(applicationName);
            _spreadsheetService.setProtocolVersion(SpreadsheetService.Versions.V3);
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }
    }

    //##################################################
    //# feeds
    //##################################################

    private void refreshFeed()
    {
        try
        {
            if ( _url == null )
            {
                createSpreadsheetFeed(new URL(DEFAULT_URL));
                return;
            }

            createSpreadsheetFeed(_url);
        }
        catch ( Exception ex )
        {
            _exception = ex;
            clearSpreadsheetFeed();
        }
    }

    private void createSpreadsheetFeed(URL url)
    {
        try
        {
            _feed = getService().getFeed(url, SpreadsheetFeed.class);
        }
        catch ( Exception ex )
        {
            _exception = ex;
            clearSpreadsheetFeed();
        }
    }

    private void clearSpreadsheetFeed()
    {
        _feed = null;
    }

    //##################################################
    //# authentication
    //##################################################

    public void setLoginCredentials(String userName, String password)
    {
        setLoginCredentials(userName, password, ClientLoginAccountType.HOSTED_OR_GOOGLE);
    }

    public void setLoginCredentials(String userName, String password, ClientLoginAccountType type)
    {
        try
        {
            _spreadsheetService.setUserCredentials(userName, password, type);
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }
    }

    //##################################################
    //# accessing
    //##################################################

    public URL getUrl()
    {
        return _url;
    }

    public void setUrl(URL url)
    {
        _url = url;
    }

    public Exception getException()
    {
        return _exception;
    }

    public boolean hasException()
    {
        return _exception != null;
    }

    //==================================================
    //= accessing (entries)
    //==================================================

    public KmList<SpreadsheetEntry> getEntries()
    {
        try
        {
            if ( getSpreadsheetFeed() == null )
                throw new NullPointerException("Spreadsheet is invalid or hasn't been initialized");

            KmList<SpreadsheetEntry> entries = new KmList<SpreadsheetEntry>();
            entries.addAll(getSpreadsheetFeed().getEntries());

            return entries;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return new KmList<SpreadsheetEntry>();
    }

    public SpreadsheetEntry findEntryByTitle(String title)
    {
        for ( SpreadsheetEntry v : getEntries() )
        {
            String s = v.getTitle().getPlainText();

            if ( title.equalsIgnoreCase(s) )
                return v;
        }

        return null;
    }

    public SpreadsheetEntry findEntryByKey(String key)
    {
        try
        {
            String url = DEFAULT_URL + "/" + key;

            return _spreadsheetService.getEntry(new URL(url), SpreadsheetEntry.class);
        }
        catch ( Exception ex )
        {
            _exception = ex;
            clearSpreadsheetFeed();
            return null;
        }
    }

    //##################################################
    //# utility
    //##################################################

    private SpreadsheetFeed getSpreadsheetFeed()
    {
        refreshFeed();

        return _feed;
    }

    private SpreadsheetService getService()
    {
        return _spreadsheetService;
    }

    //##################################################
    //# convenience
    //##################################################

    public int getSpreadsheetCount()
    {
        return getEntries().size();
    }

    public KmList<String> getSpreadsheetTitles()
    {
        KmList<String> titles = new KmList<String>();

        for ( SpreadsheetEntry e : getEntries() )
            titles.add(e.getTitle().getPlainText());

        return titles;
    }

    //==================================================
    //= convenience :: helpers
    //==================================================

    public KmGoogleSpreadsheetHelper getHelperFor(SpreadsheetEntry spreadsheet)
    {
        if ( spreadsheet == null )
            return null;

        return new KmGoogleSpreadsheetHelper(getService(), spreadsheet);
    }

    public KmGoogleWorksheetHelper getHelperFor(WorksheetEntry worksheet)
    {
        if ( worksheet == null )
            return null;

        return new KmGoogleWorksheetHelper(getService(), worksheet);
    }

    //==================================================
    //= convenience :: worksheets
    //==================================================

    public KmList<WorksheetEntry> getWorksheetsFrom(SpreadsheetEntry spreadsheet)
    {
        if ( spreadsheet == null )
            return null;

        KmGoogleSpreadsheetHelper helper = getHelperFor(spreadsheet);

        KmList<WorksheetEntry> worksheets;
        worksheets = new KmList<WorksheetEntry>();
        worksheets.addAll(helper.getWorksheets());
        return worksheets;
    }

    public WorksheetEntry getWorkheet(KmList<WorksheetEntry> v, String title)
    {
        String s;

        for ( WorksheetEntry e : v )
        {
            s = e.getTitle().getPlainText();

            if ( s.equalsIgnoreCase(title) )
                return e;
        }

        return null;
    }

    //==================================================
    //= convenience :: list entries
    //==================================================

    public KmList<ListEntry> getListEntriesFrom(WorksheetEntry worksheet)
    {
        if ( worksheet == null )
            return null;

        KmGoogleWorksheetHelper helper = getHelperFor(worksheet);

        KmList<ListEntry> listEntries;
        listEntries = new KmList<ListEntry>();
        listEntries.addAll(helper.getAllRows());
        return listEntries;
    }

    public KmList<ListEntry> getListEntriesFrom(WorksheetEntry worksheet, String query)
    {
        if ( worksheet == null )
            return null;

        KmGoogleWorksheetHelper helper = getHelperFor(worksheet);

        KmList<ListEntry> listEntries;
        listEntries = new KmList<ListEntry>();
        listEntries.addAll(helper.getRows(query));
        return listEntries;
    }

    //==================================================
    //= convenience :: cell entries
    //==================================================

    public KmList<CellEntry> getCellEntriesFrom(WorksheetEntry worksheet)
    {
        if ( worksheet == null )
            return null;

        KmGoogleWorksheetHelper helper = getHelperFor(worksheet);

        KmList<CellEntry> cellEntries;
        cellEntries = new KmList<CellEntry>();
        cellEntries.addAll(helper.getCells());
        return cellEntries;
    }
}
