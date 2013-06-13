package com.kodemore.time;

import com.kodemore.utility.KmCodedEnumIF;

public interface KmTimeZoneIF
    extends KmCodedEnumIF
{
    KmTimestamp toLocal(KmTimestamp utc);

    KmTimestamp toUtc(KmTimestamp local);
}
