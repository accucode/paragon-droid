package com.kodemore.acra;

import org.acra.collector.CrashReportData;

import com.kodemore.utility.KmLog;

/**
 * I am used to direct ACRA error reports to the log file.
 * I am suitable as a default and do not require any special permissions,
 * credentials, or passwords.
 */
public class KmAcraLogSender
    extends KmAcraReportSender
{
    //##################################################
    //# constructor
    //##################################################

    public KmAcraLogSender()
    {
        // none
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public void handleErrorReport(CrashReportData data)
    {
        for ( KmAcraField e : getErrorReportFields() )
            KmLog.error(e.formatData(data));
    }

}
