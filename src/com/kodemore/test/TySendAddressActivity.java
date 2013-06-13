package com.kodemore.test;

import android.content.Intent;
import android.view.View;

import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTextField;

public class TySendAddressActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField _nameField;
    private KmTextField _addressField1;
    private KmTextField _addressField2;
    private KmTextField _phoneField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _nameField = ui().newTextField();
        _nameField.setAutoSave();

        _addressField1 = ui().newTextField();
        _addressField1.setAutoSave();

        _addressField2 = ui().newTextField();
        _addressField2.setAutoSave();

        _phoneField = ui().newTextField();
        _phoneField.setAutoSave();
        _phoneField.setInputTypeNumber();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout col;
        col = ui().newColumn();
        col.addLabel("Name ");
        col.addView(_nameField);

        col.addLabel("Address ");
        col.addView(_addressField1);
        col.addView(_addressField2);

        col.addLabel("Phone Number ");
        col.addView(_phoneField);

        col.addButton("Send", newSendToAction());

        return col;
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newSendToAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSendTo();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleSendTo()
    {
        Intent i = new Intent(TySendAddressActivity.this, TyGetAddressActivity.class);
        i.putExtra("name", _nameField.getValue());
        i.putExtra("address1", _addressField1.getValue());
        i.putExtra("address2", _addressField2.getValue());
        i.putExtra("phone", _phoneField.getValue());
        startActivity(i);
    }
}
