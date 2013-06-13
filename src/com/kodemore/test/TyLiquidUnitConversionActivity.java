package com.kodemore.test;

import android.view.View;

import com.kodemore.types.KmLiquidUnitType;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmQuantityField;
import com.kodemore.view.KmSpinner;
import com.kodemore.view.KmTextView;

/**
 * This is a test class for the KmLiquidUnitConverter.
 */
public class TyLiquidUnitConversionActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmQuantityField             _source;
    private KmTextView                  _converted;
    private KmSpinner<KmLiquidUnitType> _fromType;
    private KmSpinner<KmLiquidUnitType> _toType;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _source = newSourceField();
        _converted = newConvertedView();
        _fromType = newTypeSpinner();
        _toType = newTypeSpinner();
    }

    private KmQuantityField newSourceField()
    {
        KmQuantityField f;
        f = ui().newQuantityField();
        f.setAutoSave();
        return f;
    }

    private KmTextView newConvertedView()
    {
        KmTextView v;
        v = ui().newTextView();
        v.setAutoSave();
        v.getHelper().setFontSize(30);
        return v;
    }

    private KmSpinner<KmLiquidUnitType> newTypeSpinner()
    {
        KmSpinner<KmLiquidUnitType> s;
        s = ui().newSpinner();
        s.setAutoSave();
        return s;
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        root.addLabel("Enter value:");
        root.addView(_source);

        root.addLabel("From");
        root.addView(_fromType);

        root.addLabel("To");
        root.addView(_toType);

        root.addLabel("Converted Value:");
        root.addView(_converted);

        root.addButton("CONVERT!", newConvertAction());

        return root.inScrollView();
    }

    //##################################################
    //# lifecycle
    //##################################################    

    @Override
    protected void onResumeAsync()
    {
        _fromType.addItems(KmLiquidUnitType.getValues());
        _toType.addItems(KmLiquidUnitType.getValues());
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newConvertAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleConvert();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleConvert()
    {
        String label = formatConvertedLabel();
        _converted.setValue(label);
    }

    private String formatConvertedLabel()
    {
        if ( _source.isEmpty() )
            return "NO VALUE TO BE CONVERTED";

        Double toValue = getToValue();
        if ( toValue == null )
            return _source.getValue() + "NOT CONVERTED";

        return toValue + " " + getTypeToConvertFrom().getPrimaryLabel();
    }

    private Double getToValue()
    {
        return getTypeToConvertFrom().toUnits(getSourceValue(), getTypeToConvertTo());
    }

    //##################################################
    //# convenience
    //##################################################

    private KmLiquidUnitType getTypeToConvertFrom()
    {
        return _fromType.getCurrentSelection();
    }

    private KmLiquidUnitType getTypeToConvertTo()
    {
        return _toType.getCurrentSelection();
    }

    private double getSourceValue()
    {
        return _source.getValue().toDoubleValue();
    }

}
