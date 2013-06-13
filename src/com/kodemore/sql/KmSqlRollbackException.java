package com.kodemore.sql;

/**
 * I am used to conveniently unwind the call stack back to a
 * KmCommand, then rollback the transaction.  No error is throw
 * to the client. 
 */
public class KmSqlRollbackException
    extends RuntimeException
{
    // none
}
