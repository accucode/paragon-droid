package com.kodemore.alert;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;

import com.kodemore.utility.KmLog;
import com.kodemore.view.KmUiManager;

public class KmDialogBuilder
{
    //##################################################
    //# variables
    //##################################################

    private Builder _builder;

    private boolean _hasMessageContent;
    private boolean _hasViewContent;

    //##################################################
    //# constructor
    //##################################################

    public KmDialogBuilder(KmUiManager ui)
    {
        _builder = new Builder(ui.getContext());
    }

    //##################################################
    //# delegates
    //##################################################

    public AlertDialog create()
    {
        return _builder.create();
    }

    @Override
    public boolean equals(Object o)
    {
        return _builder.equals(o);
    }

    @Override
    public int hashCode()
    {
        return _builder.hashCode();
    }

    public Builder setAdapter(ListAdapter adapter, OnClickListener listener)
    {
        return _builder.setAdapter(adapter, listener);
    }

    public Builder setCancelable(boolean cancelable)
    {
        return _builder.setCancelable(cancelable);
    }

    public Builder setCursor(Cursor cursor, OnClickListener listener, String labelColumn)
    {
        return _builder.setCursor(cursor, listener, labelColumn);
    }

    public Builder setCustomTitle(View customTitleView)
    {
        return _builder.setCustomTitle(customTitleView);
    }

    public Builder setIcon(Drawable icon)
    {
        return _builder.setIcon(icon);
    }

    public Builder setIcon(int iconId)
    {
        return _builder.setIcon(iconId);
    }

    public Builder setInverseBackgroundForced(boolean useInverseBackground)
    {
        return _builder.setInverseBackgroundForced(useInverseBackground);
    }

    public Builder setItems(CharSequence[] items, OnClickListener listener)
    {
        return _builder.setItems(items, listener);
    }

    public Builder setItems(int itemsId, OnClickListener listener)
    {
        return _builder.setItems(itemsId, listener);
    }

    public Builder setMultiChoiceItems(
        CharSequence[] items,
        boolean[] checkedItems,
        OnMultiChoiceClickListener listener)
    {
        return _builder.setMultiChoiceItems(items, checkedItems, listener);
    }

    public Builder setMultiChoiceItems(
        Cursor cursor,
        String isCheckedColumn,
        String labelColumn,
        OnMultiChoiceClickListener listener)
    {
        return _builder.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
    }

    public Builder setMultiChoiceItems(
        int itemsId,
        boolean[] checkedItems,
        OnMultiChoiceClickListener listener)
    {
        return _builder.setMultiChoiceItems(itemsId, checkedItems, listener);
    }

    public Builder setNegativeButton(CharSequence text, OnClickListener listener)
    {
        return _builder.setNegativeButton(text, listener);
    }

    public Builder setNegativeButton(int textId, OnClickListener listener)
    {
        return _builder.setNegativeButton(textId, listener);
    }

    public void setNegativeButton(CharSequence text)
    {
        setNegativeButton(text, null);
    }

    public Builder setNeutralButton(CharSequence text, OnClickListener listener)
    {
        return _builder.setNeutralButton(text, listener);
    }

    public Builder setNeutralButton(int textId, OnClickListener listener)
    {
        return _builder.setNeutralButton(textId, listener);
    }

    public Builder setOnCancelListener(OnCancelListener onCancelListener)
    {
        return _builder.setOnCancelListener(onCancelListener);
    }

    public Builder setOnItemSelectedListener(OnItemSelectedListener listener)
    {
        return _builder.setOnItemSelectedListener(listener);
    }

    public Builder setOnKeyListener(OnKeyListener onKeyListener)
    {
        return _builder.setOnKeyListener(onKeyListener);
    }

    public Builder setPositiveButton(CharSequence text, OnClickListener listener)
    {
        return _builder.setPositiveButton(text, listener);
    }

    public Builder setPositiveButton(int textId, OnClickListener listener)
    {
        return _builder.setPositiveButton(textId, listener);
    }

    public void setPositiveButton(CharSequence text)
    {
        setPositiveButton(text, null);
    }

    public Builder setSingleChoiceItems(
        CharSequence[] items,
        int checkedItem,
        OnClickListener listener)
    {
        return _builder.setSingleChoiceItems(items, checkedItem, listener);
    }

    public Builder setSingleChoiceItems(
        Cursor cursor,
        int checkedItem,
        String labelColumn,
        OnClickListener listener)
    {
        return _builder.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
    }

    public Builder setSingleChoiceItems(int itemsId, int checkedItem, OnClickListener listener)
    {
        return _builder.setSingleChoiceItems(itemsId, checkedItem, listener);
    }

    public Builder setSingleChoiceItems(
        ListAdapter adapter,
        int checkedItem,
        OnClickListener listener)
    {
        return _builder.setSingleChoiceItems(adapter, checkedItem, listener);
    }

    public Builder setTitle(CharSequence title)
    {
        return _builder.setTitle(title);
    }

    public Builder setTitle(int titleId)
    {
        return _builder.setTitle(titleId);
    }

    public AlertDialog show()
    {
        return _builder.show();
    }

    @Override
    public String toString()
    {
        return _builder.toString();
    }

    //==================================================
    //= delegates (content)
    //==================================================

    public Builder setMessage(CharSequence msg)
    {
        if ( msg == null )
            _hasMessageContent = false;
        else
            _hasMessageContent = true;
        checkContent();
        return _builder.setMessage(msg);
    }

    public Builder setMessage(int messageId)
    {
        Builder e;
        e = _builder.setMessage(messageId);

        if ( e == null )
        {
            _hasMessageContent = false;
            return null;
        }

        _hasMessageContent = true;
        checkContent();
        return e;
    }

    public Builder setView(View view)
    {
        if ( view == null )
            _hasMessageContent = false;
        else
            _hasMessageContent = true;
        checkContent();
        return _builder.setView(view);
    }

    //##################################################
    //# utility
    //##################################################

    private void checkContent()
    {
        if ( _hasMessageContent && _hasViewContent )
            KmLog.warnTrace("Alert dialog not intended to have both message and view.");
    }

}
