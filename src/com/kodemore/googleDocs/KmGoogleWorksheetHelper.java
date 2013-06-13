package com.kodemore.googleDocs;

import java.net.URI;
import java.net.URL;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;

public class KmGoogleWorksheetHelper
{
    //##################################################
    //# variables
    //##################################################

    private SpreadsheetService _service;

    private WorksheetEntry     _worksheet;

    private Exception          _exception;

    //##################################################
    //# constructor
    //##################################################

    public KmGoogleWorksheetHelper(SpreadsheetService service, WorksheetEntry entry)
    {
        _service = service;
        _worksheet = entry;
    }

    //##################################################
    //# insert/update/delete
    //##################################################

    public boolean updateRow(ListEntry entry, String columnHeader, String newContents)
    {
        try
        {
            entry.getCustomElements().setValueLocal(columnHeader, newContents);
            entry.update();
            return true;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return false;
    }

    public boolean deleteRow(ListEntry entry)
    {
        try
        {
            entry.delete();
            return true;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return false;
    }

    public boolean addRow(ListEntry entry)
    {
        try
        {
            getService().insert(getListFeedUrl(""), entry);
            return true;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return false;
    }

    //##################################################
    //# accessing
    //##################################################

    public Exception getException()
    {
        return _exception;
    }

    public boolean hasException()
    {
        return getException() != null;
    }

    public WorksheetEntry getWorksheet()
    {
        return _worksheet;
    }

    //==================================================
    //= accessing (list)
    //==================================================

    public KmList<ListEntry> getAllRows()
    {
        return getRows("");
    }

    /**
     * String query can be "column>20 && column2<=30" for example.
     * This will return an empty list if your query is invalid or if
     * the query is logically unsatisfiable.
     * 
     * Currently, only <, >, <=, >=, &&, ||, ==, != are supported.
     * 
     * The query is automatically URL encoded if needed. 
     * 
     * See http://eveinfo.net/wiki/refer~17.htm for more info on
     * other functions and syntax.
     */
    public KmList<ListEntry> getRows(String query)
    {
        try
        {
            if ( getWorksheet() == null )
                throw new NullPointerException("Worksheet is invalid or null");

            ListFeed feed = getListFeed(query);

            if ( feed == null )
                throw new IllegalStateException("Helper has invalid state");

            KmList<ListEntry> entries = new KmList<ListEntry>();
            entries.addAll(feed.getEntries());

            return entries;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return new KmList<ListEntry>();
    }

    //==================================================
    //= accessing :: cells
    //==================================================

    public KmList<CellEntry> getCells()
    {
        try
        {
            if ( getWorksheet() == null )
                throw new NullPointerException("Worksheet is invalid or null");

            CellFeed feed = getCellFeed();

            if ( feed == null )
                throw new IllegalStateException("Helper has invalid state");

            KmList<CellEntry> entries = new KmList<CellEntry>();
            entries.addAll(feed.getEntries());

            return entries;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return new KmList<CellEntry>();
    }

    //##################################################
    //# utility
    //##################################################

    private SpreadsheetService getService()
    {
        return _service;
    }

    //==================================================
    //= utility :: list
    //==================================================

    private ListFeed getListFeed(String query)
    {
        try
        {
            if ( getWorksheet() == null )
                throw new NullPointerException("Worksheet is null");

            URL listFeedUrl = getListFeedUrl(query);

            ListFeed listFeed = getService().getFeed(listFeedUrl, ListFeed.class);

            return listFeed;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return null;
    }

    private URL getListFeedUrl(String query)
    {
        try
        {
            if ( getWorksheet() == null )
                throw new NullPointerException("Worksheet is null");

            String formattedQuery = formatQuery(query);

            URL listFeedUrl = new URI(getWorksheet().getListFeedUrl().toString() + formattedQuery).toURL();

            return listFeedUrl;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return null;
    }

    private String formatQuery(String query)
    {
        String s = Kmu.toLowerCase(query);

        s = Kmu.replaceAll(s, "?sq=", "");
        s = Kmu.encodeUtf8(s);

        return "?sq=" + s;
    }

    //==================================================
    //= utility :: cells
    //==================================================

    private URL getCellFeedUrl()
    {
        try
        {
            if ( getWorksheet() == null )
                throw new NullPointerException("Worksheet is null");

            URL cellFeedUrl = new URI(getWorksheet().getCellFeedUrl().toString()).toURL();

            return cellFeedUrl;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return null;
    }

    private CellFeed getCellFeed()
    {
        try
        {
            if ( getWorksheet() == null )
                throw new NullPointerException("Worksheet is null");

            CellFeed cellFeed = getService().getFeed(getCellFeedUrl(), CellFeed.class);

            return cellFeed;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return null;
    }

    //##################################################
    //# convenience
    //##################################################

    public int getRowCount()
    {
        return getAllRows().size();
    }
}
