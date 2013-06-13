/*
  Copyright (c) 2005-2011 www.kodemore.com

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

package com.kodemore.types;

import java.io.Serializable;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;

public abstract class KmWeightUnit
    implements Comparable<KmWeightUnit>, Serializable
{
    //##################################################
    //# constants
    //##################################################

    public static final KmWeightUnit          KILOGRAM = newKilogram();
    public static final KmWeightUnit          GRAM     = newGram();
    public static final KmWeightUnit          POUND    = newPound();

    //##################################################
    //# static
    //##################################################

    private static final KmList<KmWeightUnit> UNITS    = newUnitList();

    protected static KmWeightUnit getUnit(String key)
    {
        for ( KmWeightUnit e : UNITS )
            if ( e.hasKey(key) )
                return e;
        return null;
    }

    //##################################################
    //# accessing
    //##################################################

    public abstract String getKey();

    public abstract String getAbbreviation();

    public abstract String getName();

    public abstract double getUnitsPerKilogram();

    //##################################################
    //# convenience
    //##################################################

    public boolean hasKey()
    {
        return getKey() != null;
    }

    public boolean hasKey(String e)
    {
        return getKey().equals(e);
    }

    //##################################################
    //# convert
    //##################################################

    /**
     * Convert a value in my units into kilograms.
     */
    public double toKilograms(double localValue)
    {
        return localValue / getUnitsPerKilogram();
    }

    /**
     * Convert kilogram into local units.
     */
    public double toLocal(double kgValue)
    {
        return kgValue * getUnitsPerKilogram();
    }

    public double toLocal(KmWeight e)
    {
        if ( equals(e.getUnit()) )
            return e.getValue();
        return toLocal(e.getUnit().toKilograms(e.getValue()));
    }

    //##################################################
    //# compare
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        return e instanceof KmWeightUnit && ((KmWeightUnit)e).hasKey(getKey());
    }

    @Override
    public int hashCode()
    {
        return getKey().hashCode();
    }

    @Override
    public int compareTo(KmWeightUnit e)
    {
        return e.getKey().compareTo(getKey());
    }

    //##################################################
    //# display
    //##################################################

    @Override
    public String toString()
    {
        return Kmu.format("WeightUnit(%s)", getAbbreviation());
    }

    //##################################################
    //# instance creation (private)
    //##################################################

    private static KmList<KmWeightUnit> newUnitList()
    {
        KmList<KmWeightUnit> v;
        v = new KmList<KmWeightUnit>();
        v.add(KILOGRAM);
        v.add(GRAM);
        v.add(POUND);
        return v;
    }

    private static KmWeightUnit newKilogram()
    {
        return new KmWeightUnit()
        {
            @Override
            public String getKey()
            {
                return "kg";
            }

            @Override
            public String getAbbreviation()
            {
                return "kg";
            }

            @Override
            public String getName()
            {
                return "kilogram";
            }

            @Override
            public double getUnitsPerKilogram()
            {
                return 1;
            }
        };
    }

    private static KmWeightUnit newGram()
    {
        return new KmWeightUnit()
        {
            @Override
            public String getKey()
            {
                return "g";
            }

            @Override
            public String getAbbreviation()
            {
                return "g";
            }

            @Override
            public String getName()
            {
                return "gram";
            }

            @Override
            public double getUnitsPerKilogram()
            {
                return 1000;
            }
        };
    }

    private static KmWeightUnit newPound()
    {
        return new KmWeightUnit()
        {
            @Override
            public String getKey()
            {
                return "lb";
            }

            @Override
            public String getAbbreviation()
            {
                return "lb";
            }

            @Override
            public String getName()
            {
                return "pound";
            }

            @Override
            public double getUnitsPerKilogram()
            {
                return 2.20462;
            }
        };
    }

}
