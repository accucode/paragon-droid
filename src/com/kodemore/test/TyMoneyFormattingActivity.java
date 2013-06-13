package com.kodemore.test;

import java.math.BigDecimal;

import android.view.View;

import com.kodemore.types.KmMoney;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTextField;
import com.kodemore.view.KmTextView;

/**
 * Test class for formatting KmFixedDecimal classes.
 */
public class TyMoneyFormattingActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField _source;
    private KmTextView  _displayShortFormat;
    private KmTextView  _displayDisplayString;
    private KmTextView  _displayDisplayStringWithScaleZero;
    private KmTextView  _displayDisplayStringWithScale;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _source = newSourceField();

        _displayShortFormat = newDisplayField();
        _displayDisplayString = newDisplayField();
        _displayDisplayStringWithScaleZero = newDisplayField();
        _displayDisplayStringWithScale = newDisplayField();
    }

    private KmTextField newSourceField()
    {
        KmTextField f;
        f = ui().newTextField();
        f.setAutoSave();
        f.setTextSize(30);
        return f;
    }

    private KmTextView newDisplayField()
    {
        KmTextView f;
        f = ui().newTextView();
        f.setAutoSave();
        f.setTextSize(30);
        return f;
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        root.addLabel("Enter number:");
        root.addView(_source);

        root.addLabel("money.getDisplayStringShort() number:");
        root.addView(_displayShortFormat);

        root.addLabel("money.getDisplayString() number:");
        root.addView(_displayDisplayString);

        root.addLabel("money.getDisplayString(0) number:");
        root.addView(_displayDisplayStringWithScaleZero);

        root.addLabel("money.getDisplayString(10) number:");
        root.addView(_displayDisplayStringWithScale);

        root.addButton("Test", newFormatAction());
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newFormatAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleFormat();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleFormat()
    {
        if ( _source.getValue() == null )
        {
            _displayShortFormat.clearValue();
            _displayDisplayString.clearValue();
            _displayDisplayStringWithScaleZero.clearValue();
            _displayDisplayStringWithScale.clearValue();
            return;
        }

        KmMoney money;
        money = getMoneyFromSource();

        _displayShortFormat.setValue(money.getShortDisplayString());
        _displayDisplayString.setValue(money.getDisplayString());
        _displayDisplayStringWithScaleZero.setValue(money.getDisplayString(0));
        _displayDisplayStringWithScale.setValue(money.getDisplayString(10));
    }

    //##################################################
    //# convenience
    //##################################################

    private KmMoney getMoneyFromSource()
    {
        return new KmMoney(new BigDecimal(_source.getValue()));
    }

}
