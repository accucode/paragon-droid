package com.kodemore.utility;

import com.kodemore.types.KmLiquidUnitType;
import com.kodemore.types.KmQuantity;

/**
 * This tool converts a string to it's quantity 
 * and it's type.  For example 750ML would be 
 * converted to a quantity of 750 and a type of ML.
 */
public class KmLiquidUnitParser
{
    //##################################################
    //# variables
    //##################################################

    private String           _source;
    private KmLiquidUnitType _unitType;
    private KmQuantity       _quantity;

    //##################################################
    //# accessing
    //##################################################

    public String getSource()
    {
        return _source;
    }

    public void setSource(String source)
    {
        _source = source;
    }

    public KmLiquidUnitType getUnitType()
    {
        return _unitType;
    }

    private void setUnitType(KmLiquidUnitType e)
    {
        _unitType = e;
    }

    public KmQuantity getQuantity()
    {
        return _quantity;
    }

    private void setQuantity(KmQuantity e)
    {
        _quantity = e;
    }

    public String getDisplayString()
    {
        if ( _quantity == null )
            return null;

        return _quantity.getDisplayString();
    }

    public String getUnitLabel()
    {
        if ( _unitType == null )
            return null;

        return _unitType.getPrimaryLabel();
    }

    //##################################################
    //# utility
    //##################################################

    public void parse(String s)
    {
        s = cleanSource(s);
        setSource(s);
        parse();
    }

    private void parse()
    {
        try
        {
            parseTry();
        }
        catch ( Exception ex )
        {
            clearValues();
        }
    }

    private void parseTry()
    {
        clearValues();
        if ( sourceHasNoValue() )
            return;

        if ( sourceSeemsValid() )
        {
            setUnitType(getUnitTypeFromSource());
            setQuantity(getQuantityFromSource());
        }

        if ( getUnitType() == null || getQuantity() == null )
            clearValues();
    }

    //##################################################
    //# convenience
    //##################################################

    private String cleanSource(String s)
    {
        s = trimAndRemoveCommasFrom(s);
        return s;
    }

    private void clearValues()
    {
        setUnitType(null);
        setQuantity(null);
    }

    private boolean sourceHasNoValue()
    {
        return Kmu.isEmpty(_source);
    }

    private boolean sourceSeemsValid()
    {
        return sourceEndsWithUnitLabel() && sourceStartsWithValue();
    }

    private boolean sourceEndsWithUnitLabel()
    {
        for ( KmLiquidUnitType type : KmLiquidUnitType.getValues() )
            for ( String label : type.getLabels() )
                if ( _source.toUpperCase().endsWith(label.toUpperCase()) )
                    return true;

        return false;
    }

    private boolean sourceStartsWithValue()
    {
        return getNumericPartOfString().length() > 0;
    }

    private KmLiquidUnitType getUnitTypeFromSource()
    {
        KmLiquidUnitType result = null;
        int len = 0;

        for ( KmLiquidUnitType t : KmLiquidUnitType.getValues() )
            for ( String label : t.getLabels() )
                if ( sourceContainsTypeUnitLabel(label) )
                    if ( label.length() > len )
                    {
                        result = t;
                        len = label.length();
                    }

        return result;
    }

    private boolean sourceContainsTypeUnitLabel(String e)
    {
        String s = _source.toUpperCase();
        String t = e.toUpperCase();

        return s.contains(t);
    }

    private KmQuantity getQuantityFromSource()
    {
        return KmQuantity.parse(getNumericPartOfString());
    }

    private String getNumericPartOfString()
    {
        String s = null;

        int n = _source.length();
        for ( int i = 0; i < n; i++ )
        {
            s = _source.substring(0, i);

            boolean isDecimalPoint = hasDecimalPointAt(i);
            boolean isDigit = hasDigitAt(i);

            if ( !isDecimalPoint && !isDigit )
                break;
        }
        return s;
    }

    private boolean hasDecimalPointAt(int i)
    {
        return _source.charAt(i) == '.';
    }

    private boolean hasDigitAt(int i)
    {
        return Kmu.isDigit(_source.charAt(i));
    }

    private String trimAndRemoveCommasFrom(String s)
    {
        s = Kmu.trim(s);
        s = Kmu.replaceAll(s, ',', "");
        return s;
    }

}
