package com.kodemore.database;

import com.kodemore.collection.KmList;
import com.kodemore.sql.KmSqlColumnIF;
import com.kodemore.time.KmDate;
import com.kodemore.time.KmTimestamp;
import com.kodemore.types.KmMoney;

public interface KmSqlValuesIF
{
    KmList<String> getKeys();

    String getStringAt(KmSqlColumnIF key);

    String getStringAt(String key);

    Integer getIntegerAt(KmSqlColumnIF key);

    Integer getIntegerAt(String key);

    Long getLongAt(KmSqlColumnIF key);

    Long getLongAt(String key);

    Double getDoubleAt(KmSqlColumnIF key);

    Double getDoubleAt(String key);

    Boolean getBooleanAt(KmSqlColumnIF key);

    Boolean getBooleanAt(String key);

    KmDate getDateAt(KmSqlColumnIF key);

    KmDate getDateAt(String key);

    KmTimestamp getTimestampAt(KmSqlColumnIF key);

    KmTimestamp getTimestampAt(String key);

    KmMoney getMoneyAt(KmSqlColumnIF key);

    KmMoney getMoneyAt(String key);

}
