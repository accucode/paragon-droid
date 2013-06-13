package com.kodemore.sql;

/**
 * I am used to conveniently unwind the call stack back to a
 * KmCommand, then commit the transaction.  No error is throw
 * to the client. 
 */
public class KmSqlCommitException
    extends RuntimeException
{
    // none
}
