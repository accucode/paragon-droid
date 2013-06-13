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

package com.kodemore.view;

import java.util.List;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.kodemore.alert.KmDialogBuilder;
import com.kodemore.utility.Kmu;

public abstract class KmListDialogManager<K>
    extends KmDialogManager
{
    //##################################################
    //# variables
    //##################################################

    private K        _selectedItem;

    private String   _title;
    private String   _cancelText;
    private String   _customButtonText;

    private KmAction _customButtonAction;

    private KmAction _onItemClickAction;

    //##################################################
    //# constructor 
    //##################################################

    public KmListDialogManager()
    {
        _title = "Select One";
        _cancelText = null;
        _customButtonText = null;

        _onItemClickAction = KmAction.NOOP;
    }

    //##################################################
    //# dialog
    //##################################################

    @Override
    public Dialog create()
    {
        return createDialog();
    }

    private Dialog createDialog()
    {
        _selectedItem = null;

        KmListView<K> listView;
        listView = newListView();
        listView.setItems(getItems());
        listView.setFastScrollEnabled();

        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle(_title);
        e.setView(listView);

        if ( hasCancelButton() )
            e.setNegativeButton(_cancelText);

        if ( hasCustomButton() )
            e.setPositiveButton(_customButtonText, _customButtonAction);

        return e.create();
    }

    //##################################################
    //# cancel button
    //##################################################

    public void addCancelButton()
    {
        _cancelText = "Cancel";
    }

    public void addCancelButton(String e)
    {
        _cancelText = e;
    }

    public void removeCancelButton()
    {
        _cancelText = null;
    }

    public boolean hasCancelButton()
    {
        return _cancelText != null;
    }

    //##################################################
    //# custom button
    //##################################################

    public void addCustomButton(String label, KmAction action)
    {
        _customButtonText = label;
        _customButtonAction = action;
    }

    public void removeCustomButton()
    {
        _customButtonText = null;
        _customButtonAction = null;
    }

    public boolean hasCustomButton()
    {
        return _customButtonText != null;
    }

    //##################################################
    //# on item selected listener
    //##################################################

    public void setOnItemSelectedListener(KmAction e)
    {
        _onItemClickAction = e;
    }

    //##################################################
    //# accessing
    //##################################################

    public K getSelectedItem()
    {
        return _selectedItem;
    }

    public void setSelectedItem(K e)
    {
        _selectedItem = e;
    }

    public String getTitle()
    {
        return _title;
    }

    public void setTitle(String e)
    {
        _title = e;
    }

    //##################################################
    //# listView
    //##################################################

    private KmListView<K> newListView()
    {
        return new KmListView<K>(ui())
        {
            @Override
            protected android.view.View getView(K item, int index, android.view.View oldView)
            {
                KmOneLineView view;
                view = KmOneLineView.createOrCast(ui(), oldView);
                view.setText(getDisplayName(item));
                view.setBackgroundColor(Color.DKGRAY);

                styleView(item, view);

                return view;
            }

            @Override
            protected void onItemClick(K item)
            {
                _selectedItem = item;
                _onItemClickAction.fire();
                dismissDialog();
            }

            @Override
            protected Drawable getDefaultBackground()
            {
                return null;
            }

            @Override
            protected CharSequence getSectionNameFor(K e)
            {
                return getDisplayName(e);
            }
        };
    }

    protected String getDisplayName(K e)
    {
        return Kmu.toStringSafe(e);
    }

    //##################################################
    //# abstract
    //##################################################

    protected abstract List<K> getItems();

    //##################################################
    //# styling
    //##################################################

    protected void styleSelected(KmOneLineView view)
    {
        KmTextViewHelper h;
        h = view.getTextView().getHelper();
        h.setBold();
        h.setTextColor(Color.YELLOW);
    }

    public void styleView(K item, KmOneLineView view)
    {
        if ( item == null )
            return;

        if ( isSpecialValue(item) )
            styleSpecialItem(view);
        else
            styleRegularItem(view);
    }

    private void styleSpecialItem(KmOneLineView view)
    {
        KmTextViewHelper h;
        h = view.getTextView().getHelper();
        h.setItalic();
        h.setTextColor(Color.CYAN);
    }

    private void styleRegularItem(KmOneLineView view)
    {
        KmTextViewHelper h;
        h = view.getTextView().getHelper();
        h.setNormal();
        h.setTextColor(Color.LTGRAY);
    }

    //##################################################
    //# abstract
    //##################################################

    protected boolean isSpecialValue(K e)
    {
        return false;
    }
}
