package com.kodemore.test;

import android.content.res.Configuration;
import android.graphics.Color;
import android.view.View;

import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmButton;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextField;
import com.kodemore.view.KmTextView;
import com.kodemore.view.value.KmStringValue;

public class TyCalculatorActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmStringValue _operation;

    private KmTextField   _inputField1;
    private KmTextField   _inputField2;
    private KmTextField   _outputField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _operation = new KmStringValue(ui());
        _operation.setAutoSave();

        _inputField1 = addTextField(null);
        _inputField1.setAutoSave();

        _inputField2 = addTextField(null);
        _inputField2.setAutoSave();

        _outputField = addTextField(null);
        _outputField.setAutoSave();
        _outputField.setEnabled(false);
    }

    private KmTextField addTextField(CharSequence text)
    {
        KmTextField e;
        e = ui().newTextField();
        e.setText(text);
        e.setRawInputType(Configuration.KEYBOARD_QWERTY);
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
        root.addSpace();

        KmRowLayout row;
        row = root.addEvenRow();
        row.addView(newText("Input first number:"));
        row.addView(_inputField1);

        row = root.addEvenRow();
        row.addView(newText("Input second number:"));
        row.addView(_inputField2);

        row = root.addEvenRow();
        row.addView(newText(""));
        KmRowLayout row2;
        row2 = row.addEvenRow();

        KmStringValue op = new KmStringValue(ui());
        op.setValue("+");

        addButton("+", newOperaterAction(op), row2);

        op = new KmStringValue(ui());
        op.setValue("-");

        addButton("-", newOperaterAction(op), row2);

        row = root.addEvenRow();
        row.addView(newText("What would you like me to do?"));

        row2 = row.addEvenRow();

        op = new KmStringValue(ui());
        op.setValue("*");

        addButton("*", newOperaterAction(op), row2);

        op = new KmStringValue(ui());
        op.setValue("/");

        addButton("/", newOperaterAction(op), row2);

        row = root.addEvenRow();
        row.addView(newText("Go forward or change your mind:"));

        row2 = row.addEvenRow();
        addButton("=", newGoAction(), row2);
        addButton("C", newNullAction(), row2);

        row = root.addEvenRow();
        row.addView(newText("Your answer is here and in a toast:"));
        row.addView(_outputField);

        return root;
    }

    private View newText(String s)
    {
        KmTextView e;
        e = ui().newLabel(s);
        e.setTextColor(Color.BLUE);
        e.setPadding(50, 0, 0, 0);
        return e;
    }

    private void addButton(String s, KmAction action, KmRowLayout row)
    {
        KmButton e;
        e = row.addButton();
        e.setHeight(75);
        e.setTextSize(27);
        e.setText(s);
        e.setOnClickListener(action);
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newOperaterAction(final KmStringValue operator)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                _operation = operator;
            }
        };
    }

    private KmAction newGoAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                if ( !areEntriesValid() )
                {
                    handleNullString();
                    return;
                }

                if ( _operation == null )
                {
                    alert("No operation selected");
                    return;
                }

                int outputValue = 0;

                if ( _operation.getValue().equals("+") )
                    outputValue = handleAddition();

                if ( _operation.getValue().equals("-") )
                    outputValue = handleSubtraction();

                if ( _operation.getValue().equals("*") )
                    outputValue = handleMultiplication();

                if ( _operation.getValue().equals("/") )
                    outputValue = handleDivision();

                handleGetValue(outputValue);
                _operation = null;
            }
        };
    }

    private KmAction newNullAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSetNull();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleNullString()
    {
        String string1 = _inputField1.getValue();
        String string2 = _inputField2.getValue();

        if ( string1 == null && string2 == null )
        {
            alert("Please input numbers");
            return;
        }

        if ( string1 == null )
        {
            alert("Please input first number");
            return;
        }

        if ( string2 == null )
        {
            alert("Please input second number");
            return;
        }

        alert("Please only input numbers");
    }

    private boolean areEntriesValid()
    {
        if ( _inputField1.isEmpty() )
            return false;

        if ( _inputField2.isEmpty() )
            return false;

        if ( !Kmu.isAllDigits(_inputField1.getValue()) )
            return false;

        if ( !Kmu.isAllDigits(_inputField2.getValue()) )
            return false;

        return true;
    }

    private int getInt1()
    {
        String s = _inputField1.getValue();
        return Integer.parseInt(s);
    }

    private int getInt2()
    {
        String s = _inputField2.getValue();
        return Integer.parseInt(s);
    }

    private int handleAddition()
    {
        return getInt1() + getInt2();
    }

    private int handleSubtraction()
    {
        return getInt1() - getInt2();
    }

    private int handleMultiplication()
    {
        return getInt1() * getInt2();
    }

    private int handleDivision()
    {
        return getInt1() / getInt2();
    }

    private void handleGetValue(int output)
    {
        String s = output + "";

        _outputField.setText(s);

        toast("Your answer is " + s);
    }

    private void handleSetNull()
    {
        _inputField1.clearValue();
        _inputField2.clearValue();
        _outputField.clearValue();
        _operation = null;
    }
}
