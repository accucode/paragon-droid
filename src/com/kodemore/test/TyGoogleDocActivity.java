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

package com.kodemore.test;

import android.view.View;

import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

import com.kodemore.collection.KmList;
import com.kodemore.googleDocs.KmGoogleSpreadsheetService;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmOneLineView;

public class TyGoogleDocActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################//

    /**
     * these constants need to be filled with googledrive info
     */
    private static final String          USER_NAME = "";
    private static final String          PASSWORD  = "";

    //##################################################
    //# variables
    //##################################################

    private KmGoogleSpreadsheetService   _service;

    private KmListView<SpreadsheetEntry> _spreadsheets;
    private KmListView<WorksheetEntry>   _worksheets;
    private KmListView<ListEntry>        _rows;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _service = new KmGoogleSpreadsheetService("Spreadsheet");
        _service.setLoginCredentials(USER_NAME, PASSWORD);

        _spreadsheets = newSpreadsheetList();
        _spreadsheets.setItems(getSpreadsheets());
        _spreadsheets.setFastScrollEnabled();

        _worksheets = newWorksheetList();
        _worksheets.setFastScrollEnabled();
        _rows = newRowList();
        _rows.setFastScrollEnabled();
    }

    private KmListView<SpreadsheetEntry> newSpreadsheetList()
    {
        return new KmListView<SpreadsheetEntry>(ui())
        {
            @Override
            protected View getView(SpreadsheetEntry item, int index, View oldView)
            {
                KmOneLineView view;
                view = KmOneLineView.createOrCast(ui(), oldView);
                view.setText(item.getTitle().getPlainText());
                return view;
            }

            @Override
            protected void onItemClick(SpreadsheetEntry item)
            {
                _worksheets.setItems(_service.getWorksheetsFrom(item));
                _rows.clearItems();
            }

            @Override
            protected CharSequence getSectionNameFor(SpreadsheetEntry e)
            {
                return e.getTitle().getPlainText();
            }
        };
    }

    private KmListView<WorksheetEntry> newWorksheetList()
    {
        return new KmListView<WorksheetEntry>(ui())
        {
            @Override
            protected View getView(WorksheetEntry item, int index, View oldView)
            {
                KmOneLineView view;
                view = KmOneLineView.createOrCast(ui(), oldView);
                view.setText(item.getTitle().getPlainText());
                return view;
            }

            @Override
            protected void onItemClick(WorksheetEntry item)
            {
                _rows.setItems(_service.getListEntriesFrom(item));
            }

            @Override
            protected CharSequence getSectionNameFor(WorksheetEntry e)
            {
                return e.getTitle().getPlainText();
            }
        };
    }

    private KmListView<ListEntry> newRowList()
    {
        return new KmListView<ListEntry>(ui())
        {
            @Override
            protected View getView(ListEntry item, int index, View oldView)
            {
                KmOneLineView view;
                view = KmOneLineView.createOrCast(ui(), oldView);
                view.setText(getRowContent(item));
                return view;
            }

            @Override
            protected CharSequence getSectionNameFor(ListEntry e)
            {
                return e.getTitle().getPlainText();
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout top;
        top = ui().newColumn();
        top.addLabel("Spreadsheets");
        top.addViewFullWeight(_spreadsheets);

        KmColumnLayout middle;
        middle = ui().newColumn();
        middle.addLabel("Worksheets");
        middle.addViewFullWeight(_worksheets);

        KmColumnLayout bottom;
        bottom = ui().newColumn();
        bottom.addLabel("Rows");
        bottom.addViewFullWeight(_rows);

        KmColumnLayout root;
        root = ui().newColumn();
        root.setItemHeight(0);
        root.setItemWeightFull();
        root.addView(top);
        root.addView(middle);
        root.addView(bottom);
        return root;
    }

    //##################################################
    //# utility
    //##################################################

    private KmList<SpreadsheetEntry> getSpreadsheets()
    {
        return _service.getEntries();
    }

    private String getRowContent(ListEntry row)
    {
        String out = "";

        for ( String e : row.getCustomElements().getTags() )
            out = out + row.getCustomElements().getValue(e) + "  ";

        return out;
    }
}
