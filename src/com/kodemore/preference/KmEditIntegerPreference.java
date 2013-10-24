package com.kodemore.preference;

import android.content.Context;
import android.preference.EditTextPreference;

import com.kodemore.utility.Kmu;

/**
 * I extend the default built-in EditTextPreference to provide better
 * editing for integer values.
 */
public class KmEditIntegerPreference
    extends EditTextPreference
{
    //##################################################
    //# variable
    //##################################################

    private Integer _defaultValue;

    //##################################################
    //# constructor
    //##################################################

    public KmEditIntegerPreference(Context context)
    {
        super(context);
    }

    //##################################################
    //# accessing
    //##################################################

    public Integer getDefaultValue()
    {
        return _defaultValue;
    }

    //##################################################
    //# override
    //##################################################

    @Override
    protected String getPersistedString(String defaultReturnValue)
    {
        Integer def = getDefaultValue();

        if ( def == null )
            return getPersistedInt(-1) + "";

        return getPersistedInt(def) + "";
    }

    @Override
    protected boolean persistString(String value)
    {
        Integer i = Kmu.parseInteger(value);

        if ( i == null )
            return false;

        return persistInt(i);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue)
    {
        if ( defaultValue instanceof Integer )
            defaultValue = defaultValue.toString();

        super.onSetInitialValue(restoreValue, defaultValue);
    }

    @Override
    public CharSequence getSummary()
    {
        return getPersistedString(getDefaultValue() + "");
    }

    /**
     * The default value must be an Integer.
     */
    @Override
    public void setDefaultValue(Object def)
    {
        _defaultValue = (Integer)def;

        if ( _defaultValue == null )
            super.setDefaultValue(null);
        else
            super.setDefaultValue(_defaultValue.toString());
    }

}
