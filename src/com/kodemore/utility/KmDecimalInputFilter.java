package com.kodemore.utility;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.EditText;

public class KmDecimalInputFilter
    implements InputFilter
{
    //##################################################
    //# constants
    //##################################################

    private static final String PRECISION_ERROR_MESSAGE = "Entering another digit would make the value to large to save.";
    private static final String SCALE_ERROR_MESSAGE     = "Only two decimal places allowed.";

    //##################################################
    //# variables
    //##################################################

    private EditText            _field;

    private Integer             _maxPrecision;
    private Integer             _scale;

    //##################################################
    //# accessing
    //##################################################

    public EditText getField()
    {
        return _field;
    }

    public void setField(EditText e)
    {
        _field = e;
    }

    public Integer getMaxPrecision()
    {
        return _maxPrecision;
    }

    public void setMaxPrecision(Integer e)
    {
        _maxPrecision = e;
    }

    public Integer getScale()
    {
        return _scale;
    }

    public void setScale(Integer e)
    {
        _scale = e;
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public CharSequence filter(
        CharSequence src,
        int srcStart,
        int srcEnd,
        Spanned dest,
        int destStart,
        int destEnd)
    {
        SpannableStringBuilder result;
        result = new SpannableStringBuilder(dest);
        result.insert(destStart, src);

        String s = result.toString();

        Integer count = Kmu.countOccurences(s, '.');

        /** 
         * allows one to enter decimal point if one does not
         * exist even if it would cause invalid scale
         */
        if ( src.toString().equals(".") && count <= 1 )
            return null;

        if ( count > 1 )
            return "";

        int countAllDigits = Kmu.countDigitsIn(s);

        if ( countAllDigits > getMaxPrecision() )
        {
            getField().setError(PRECISION_ERROR_MESSAGE);
            return "";
        }

        int decimalPlacesInSource = Kmu.countDigitsAfterDecimalIn(s);
        int decimalPlacesInDest = Kmu.countDigitsAfterDecimalIn(dest.toString());

        if ( decimalPlacesInSource > getScale() && decimalPlacesInSource > decimalPlacesInDest )
        {
            getField().setError(SCALE_ERROR_MESSAGE);
            return "";
        }

        return null;
    }

}
