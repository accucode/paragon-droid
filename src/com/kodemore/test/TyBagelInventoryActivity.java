package com.kodemore.test;

import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmStringListView;
import com.kodemore.view.KmTextField;

public class TyBagelInventoryActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField      _inputField;
    private KmList<String>   _list;
    private KmStringListView _listView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _inputField = ui().newTextField();
        _inputField.setAutoSave();

        _list = getBagelTypes();

        _listView = new KmStringListView(ui());
        _listView.setItems(_list);
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()

    {
        KmColumnLayout col;
        col = ui().newColumn();
        col.addSpace();
        col.addLabel("Input new bagel type:");

        KmRowLayout row;
        row = col.addRow();
        row.addViewFullWeight(_inputField);
        row.addButton("Add to menu", newAddAction());

        row = col.addEvenRow();
        row.addView(_listView);

        return col;
    }

    //##################################################
    //# accessing
    //##################################################

    public KmList<String> getBagelTypes()
    {
        KmList<String> v;
        v = new KmList<String>();

        v.add("plain");

        return v;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newAddAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleAdd();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleAdd()
    {
        String s = _inputField.getValue();
        _list.add(s);
        _list.sort();
        _listView.setItems(_list);
    }
}
