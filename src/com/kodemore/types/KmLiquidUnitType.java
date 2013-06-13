package com.kodemore.types;

import com.kodemore.collection.KmList;

/**
 * Contains the liquid unit types we recognize.
 * Will convert liquid unit types.
 * Has alternate values for parsing.
 * The first value is preferred for labeling.
 */
public enum KmLiquidUnitType
{
    //##################################################
    //# constants
    //##################################################

    OUNCE("OUNCE", 29.574, "OZ", "FL OZ", "OUNCE", "OUNCES"),
    GALLON("GALLON", 3785.410, "GAL", "GALLON", "GALLONS"),
    LITER("LITER", 1000.000, "L", "LITER", "LITRE", "LITERS", "LITRES"),
    MILLILITER("MILLILITER", 1.000, "ML", "MILLILITER", "MILLILITRE", "MILLILITERS", "MILLILITRES"),
    PINT("PINT", 473.176, "PT", "PINT", "PINTS"),
    QUART("QUART", 946.353, "QT", "QUART", "QRT", "QUARTS");

    //##################################################
    //# static
    //##################################################

    public static KmList<KmLiquidUnitType> getValues()
    {
        KmList<KmLiquidUnitType> v;
        v = new KmList<KmLiquidUnitType>();
        v.addAll(values());
        return v;
    }

    //##################################################
    //# variables
    //##################################################

    /**
     * This is the key for the retrieving type.
     */
    private final String         _key;

    /**
     * This contains the label values for all types.
     */
    private final KmList<String> _labels;

    /**
     * This is the base value for conversion calculations.
     */
    private final double         _inMilliliters;

    //##################################################
    //# constructors
    //##################################################

    private KmLiquidUnitType(String key, double ml, String... labels)
    {
        _key = key;

        _inMilliliters = ml;

        _labels = new KmList<String>();
        _labels.addAll(labels);
    }

    //##################################################
    //# accessing
    //##################################################

    public String getKey()
    {
        return _key;
    }

    public String getPrimaryLabel()
    {
        return _labels.getFirst();
    }

    public KmList<String> getLabels()
    {
        return _labels;
    }

    public KmLiquidUnitType getUnitTypeByKey(String key)
    {
        KmList<KmLiquidUnitType> values = getValues();
        for ( KmLiquidUnitType value : values )
            if ( value.hasLabel(key) )
                return value;

        return null;
    }

    public boolean hasLabel(String e)
    {
        return getLabels().contains(e.toUpperCase());
    }

    private double getInMilliliters()
    {
        return _inMilliliters;
    }

    //##################################################
    //# conversion
    //##################################################

    public double toMilliliters(double value)
    {
        return toUnits(value, MILLILITER);
    }

    public double toLiters(double value)
    {
        return toUnits(value, LITER);
    }

    public double toOunces(double value)
    {
        return toUnits(value, OUNCE);
    }

    public double toPints(double value)
    {
        return toUnits(value, PINT);
    }

    public double toQuarts(double value)
    {
        return toUnits(value, QUART);
    }

    public double toGallons(double value)
    {
        return toUnits(value, GALLON);
    }

    public Double toUnits(Double value, KmLiquidUnitType goalType)
    {
        if ( value == null )
            return null;

        double mlPerGoalType = goalType.getInMilliliters();

        // First convert value to mls.
        double valueInMls = value * getInMilliliters();

        // Then divide by target ml / unit to cancel out mls.
        return valueInMls / mlPerGoalType;
    }

}
