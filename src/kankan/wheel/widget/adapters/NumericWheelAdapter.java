/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package kankan.wheel.widget.adapters;

import android.content.Context;

/**
 * Numeric Wheel adapter.
 */
public class NumericWheelAdapter
    extends AbstractWheelTextAdapter
{
    //##################################################
    //# constants
    //##################################################

    /** The default min value */
    public static final int  DEFAULT_MAX_VALUE = 9;

    /** The default max value */
    private static final int DEFAULT_MIN_VALUE = 0;

    //##################################################
    //# variables 
    //##################################################

    private int              _minValue;
    private int              _maxValue;
    private String           _format;

    //##################################################
    //# constructor
    //##################################################

    /**
     * Constructor
     * @param context the current context
     */
    public NumericWheelAdapter(Context context)
    {
        this(context, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }

    /**
     * Constructor
     * @param context the current context
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public NumericWheelAdapter(Context context, int minValue, int maxValue)
    {
        this(context, minValue, maxValue, null);
    }

    /**
     * Constructor
     * @param context the current context
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     * @param format the format string
     */
    public NumericWheelAdapter(Context context, int minValue, int maxValue, String format)
    {
        super(context);

        _minValue = minValue;
        _maxValue = maxValue;
        _format = format;
    }

    //##################################################
    //# accessing
    //##################################################

    @Override
    public CharSequence getItemText(int index)
    {
        if ( index >= 0 && index < getItemsCount() )
        {
            int value = _minValue + index;
            return _format != null
                ? String.format(_format, value)
                : Integer.toString(value);
        }
        return null;
    }

    @Override
    public int getItemsCount()
    {
        return _maxValue - _minValue + 1;
    }
}
