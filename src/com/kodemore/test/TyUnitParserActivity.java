package com.kodemore.test;

import android.view.View;

import com.kodemore.utility.KmLiquidUnitParser;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextField;
import com.kodemore.view.KmTextView;

/**
 * This is a test class for the KmLiquidUnitParser.
 */
public class TyUnitParserActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField _inputTextField;
    private KmTextView  _unitTypeView;
    private KmTextView  _quantityLabelView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _inputTextField = newFieldWithLargeText();

        _unitTypeView = newViewWithLargeText();
        _quantityLabelView = newViewWithLargeText();
    }

    //##################################################
    //# layout
    //##################################################

    private KmTextField newFieldWithLargeText()
    {
        KmTextField e;
        e = new KmTextField(ui());
        e.setAutoSave();
        e.setTextSize(30);
        return e;
    }

    private KmTextView newViewWithLargeText()
    {
        KmTextView e;
        e = new KmTextView(ui());
        e.setAutoSave();
        e.getHelper().setFontSize(30);
        return e;
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.setPadding();
        root.addView(newInputFieldRow());
        root.addView(newTypeDisplayRow());
        root.addView(newQuantityDisplayRow());
        root.addButton("Parse", newParseAction()).setTextSize(30);
        return root;
    }

    private View newInputFieldRow()
    {
        KmTextView label;
        label = new KmTextView(ui());
        label.setValue("Enter string to parse");
        label.setTextSize(30);

        KmRowLayout row;
        row = ui().newEvenRow();
        row.addViewFullWeight(label);
        row.addViewFullWeight(_inputTextField);
        return row;
    }

    private View newTypeDisplayRow()
    {
        KmTextView label;
        label = new KmTextView(ui());
        label.setValue("KmLiquidUnitType: ");
        label.setTextSize(30);

        KmRowLayout row;
        row = ui().newEvenRow();
        row.addViewFullWeight(label);
        row.addViewFullWeight(_unitTypeView);
        return row;
    }

    private View newQuantityDisplayRow()
    {
        KmTextView label;
        label = new KmTextView(ui());
        label.setValue("Parsed Quantity: ");
        label.setTextSize(30);

        KmRowLayout row;
        row = ui().newEvenRow();
        row.addViewFullWeight(label);
        row.addViewFullWeight(_quantityLabelView);
        return row;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newParseAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleParse();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleParse()
    {
        if ( _inputTextField.isEmpty() )
        {
            clearValues();
            return;
        }

        KmLiquidUnitParser parser;
        parser = new KmLiquidUnitParser();
        parser.parse(_inputTextField.getValue());

        setFormattedValue(parser);
    }

    //##################################################
    //# convenience
    //##################################################

    private void clearValues()
    {
        _unitTypeView.clearValue();
        _quantityLabelView.clearValue();
    }

    private void setFormattedValue(KmLiquidUnitParser parser)
    {
        _unitTypeView.setValue(parser.getUnitLabel());
        _quantityLabelView.setValue(parser.getDisplayString());
    }

}
