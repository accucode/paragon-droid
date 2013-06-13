package com.kodemore.sql;

import com.kodemore.database.KmSqlValuesIF;

public interface KmDaoDomainIF
{
    /**
     * Return the name of the database table.
     */
    String getTableName();

    void applyFromValues(KmSqlValuesIF values);

    /**
     * Read a single column from the cursor's current row
     * and apply the value to this domain model.
     */
    void applyFromValues(KmSqlValuesIF values, String col);

    /**
     * Compose a map containing all of the values to update
     * stored in the database.
     */
    KmSqlContentValues getValues();

    void wherePrimaryKey(KmSqlCompositeCondition where);

    void save();

    void delete();
}
