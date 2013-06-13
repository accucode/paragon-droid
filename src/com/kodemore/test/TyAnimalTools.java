/*
  Copyright (c) 2005-2012 Wyatt Love

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

package com.kodemore.test;

import com.kodemore.collection.KmList;
import com.kodemore.comparator.KmComparator;
import com.kodemore.utility.KmFormatterIF;
import com.kodemore.utility.KmSamples;

/**
 * I provide a simple model for testing.
 */
public class TyAnimalTools
{
    //##################################################
    //# formatters
    //##################################################

    public KmFormatterIF<TyAnimal> getTypeFormatter()
    {
        return new KmFormatterIF<TyAnimal>()
        {
            @Override
            public String format(TyAnimal e)
            {
                return e.getType();
            }
        };
    }

    public KmFormatterIF<TyAnimal> getDisplayStringFormatter()
    {
        return new KmFormatterIF<TyAnimal>()
        {
            @Override
            public String format(TyAnimal e)
            {
                return e.getDisplayString();
            }
        };
    }

    //##################################################
    //# comparators
    //##################################################

    public KmComparator<TyAnimal> getTypeComparator()
    {
        return new KmComparator<TyAnimal>()
        {
            @Override
            public int compare(TyAnimal a, TyAnimal b)
            {
                return c(a.getType(), b.getType());
            }
        };
    }

    public KmComparator<TyAnimal> getDisplayStringComparator()
    {
        return new KmComparator<TyAnimal>()
        {
            @Override
            public int compare(TyAnimal a, TyAnimal b)
            {
                return c(a.getDisplayString(), b.getDisplayString());
            }
        };
    }

    public KmComparator<TyAnimal> getColorComparator()
    {
        return new KmComparator<TyAnimal>()
        {
            @Override
            public int compare(TyAnimal a, TyAnimal b)
            {
                return c(a.getColor(), b.getColor());
            }
        };
    }

    //##################################################
    //# samples 
    //##################################################

    public KmList<TyAnimal> getRandomAnimals()
    {
        KmList<String> types = KmSamples.getAnimalTypes();
        KmList<String> colors = KmSamples.getColorsNames();

        KmList<TyAnimal> v = new KmList<TyAnimal>();

        for ( String type : types )
        {
            TyAnimal e;
            e = new TyAnimal();
            e.setType(type);
            e.setColor(colors.getRandom());
            v.add(e);
        }

        return v;
    }

}
