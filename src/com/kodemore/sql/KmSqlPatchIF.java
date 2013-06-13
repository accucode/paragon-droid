package com.kodemore.sql;

/**
 * I am used to upgrade the database to a specific version.
 */
public interface KmSqlPatchIF
{
    /**
     * The database version corresponding to this patch.  Specifically
     * the database version created as a result of applying this patch.
     */
    int getVersion();

    /**
     * A brief name that summarizes the changes being made. 
     */
    String getName();

    /**
     * Upgrades the database.  This assumes that the database has already
     * been upgraded to the previous version. 
     */
    void upgrade(KmSqlDatabase db);
}
