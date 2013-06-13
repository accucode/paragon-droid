package com.kodemore.utility;

/**
 * I define a basic interface for common enums.  This creates
 * a convenient contract that applies to "99%" of the enums
 * used in simple business apps.
 */
public interface KmCodedEnumIF
{
    String getCode();

    String getName();

}
