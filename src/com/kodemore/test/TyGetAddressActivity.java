package com.kodemore.test;

import android.graphics.Color;
import android.view.View;

import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTextView;

public class TyGetAddressActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextView _line1;
    private KmTextView _line2;
    private KmTextView _line3;
    private KmTextView _line4;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _line1 = newTextView(intentField("name"));

        _line2 = newTextView(intentField("address1"));

        _line3 = newTextView(intentField("address2"));

        _line4 = newTextView(intentField("phone"));
    }

    public KmTextView newTextView(String s)
    {
        KmTextView e;
        e = ui().newTextView();
        e.setValue(s);
        e.setTextSize(20);
        return e;
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout col;
        col = ui().newColumn();
        addLabel(col, "Name ");
        col.addView(_line1);

        addLabel(col, "Address ");
        col.addView(_line2);
        col.addView(_line3);

        addLabel(col, "Phone Number ");
        col.addView(_line4);

        return col;
    }

    public KmTextView addLabel(KmColumnLayout col, String s)
    {
        KmTextView e = ui().newLabel(s);
        e.setTextColor(Color.BLUE);
        col.addView(e);
        return e;
    }

    //##################################################
    //# utility
    //##################################################

    public String intentField(String s)
    {
        return getIntent().getExtras().getString(s);
    }
}
