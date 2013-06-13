package com.kodemore.googleDocs;

import java.net.URL;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

import com.kodemore.collection.KmList;

public class KmGoogleSpreadsheetHelper
{
    //##################################################
    //# variables
    //##################################################

    private SpreadsheetService _service;

    private SpreadsheetEntry   _entry;
    private WorksheetFeed      _worksheetFeed;

    private Exception          _exception;

    //##################################################
    //# constructor
    //##################################################

    public KmGoogleSpreadsheetHelper(SpreadsheetService service, SpreadsheetEntry entry)
    {
        _service = service;
        _entry = entry;
    }

    //##################################################
    //# feeds
    //##################################################

    private void refreshFeeds()
    {
        try
        {
            URL worksheetUrl = getWorksheetFeedUrl();
            _worksheetFeed = getService().getFeed(worksheetUrl, WorksheetFeed.class);
        }
        catch ( Exception ex )
        {
            _exception = ex;
            clearWorksheetFeed();
        }
    }

    private void clearWorksheetFeed()
    {
        _worksheetFeed = null;
    }

    //##################################################
    //# insert/delete/update
    //##################################################

    protected boolean insertWorksheet(WorksheetEntry worksheet)
    {
        try
        {
            if ( worksheet == null )
                return false;

            getService().insert(getWorksheetFeedUrl(), worksheet);
            return true;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return false;
    }

    protected boolean deleteWorksheet(WorksheetEntry worksheet)
    {
        try
        {
            if ( worksheet == null )
                return false;

            worksheet.delete();
            return true;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return false;
    }

    protected boolean updateWorksheet(WorksheetEntry worksheet)
    {
        try
        {
            if ( worksheet == null )
                return false;

            worksheet.update();
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

    public SpreadsheetEntry getEntry()
    {
        return _entry;
    }

    //==================================================
    //= accessing (worksheets)
    //==================================================

    public KmList<WorksheetEntry> getWorksheets()
    {
        try
        {
            if ( getEntry() == null )
                throw new NullPointerException("Spreadsheet is inavlid or null");

            WorksheetFeed feed = getWorksheetFeed();

            if ( feed == null )
                throw new IllegalStateException("Helper has invalid state");

            KmList<WorksheetEntry> worksheets = new KmList<WorksheetEntry>();
            worksheets.addAll(feed.getEntries());

            return worksheets;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return new KmList<WorksheetEntry>();
    }

    public WorksheetEntry getWorksheet(String title)
    {
        for ( WorksheetEntry v : getWorksheets() )
        {
            String s = v.getTitle().getPlainText();

            if ( title.equals(s) )
                return v;
        }

        return null;
    }

    public boolean addWorksheet(String title, int rowCount, int colCount)
    {
        WorksheetEntry worksheet = new WorksheetEntry();
        worksheet.setTitle(new PlainTextConstruct(title));
        worksheet.setRowCount(rowCount);
        worksheet.setColCount(colCount);

        return insertWorksheet(worksheet);
    }

    public boolean changeTitleWorksheet(String oldTitle, String newTitle)
    {
        WorksheetEntry worksheet = getWorksheet(oldTitle);

        if ( worksheet == null )
            return false;

        worksheet.setTitle(new PlainTextConstruct(newTitle));

        return updateWorksheet(worksheet);
    }

    public boolean resizeWorksheet(String title, int row, int col)
    {
        WorksheetEntry worksheet = getWorksheet(title);

        if ( worksheet == null )
            return false;

        worksheet.setRowCount(row);
        worksheet.setColCount(col);

        return updateWorksheet(worksheet);
    }

    public boolean deleteWorksheet(String title)
    {
        return deleteWorksheet(getWorksheet(title));
    }

    //##################################################
    //# utility
    //##################################################

    private WorksheetFeed getWorksheetFeed()
    {
        refreshFeeds();

        return _worksheetFeed;
    }

    private URL getWorksheetFeedUrl()
    {
        if ( getEntry() == null )
            return null;

        return getEntry().getWorksheetFeedUrl();
    }

    private SpreadsheetService getService()
    {
        return _service;
    }

    //##################################################
    //# convenience
    //##################################################

    public int numberOfWorksheets()
    {
        return getWorksheets().size();
    }

    //==================================================
    //= convenience :: helper
    //==================================================

    public KmGoogleWorksheetHelper getHelperFor(WorksheetEntry worksheet)
    {
        if ( worksheet == null )
            return null;

        return new KmGoogleWorksheetHelper(getService(), worksheet);
    }

    public KmGoogleWorksheetHelper getHelperFor(String title)
    {
        return getHelperFor(getWorksheet(title));
    }
}
