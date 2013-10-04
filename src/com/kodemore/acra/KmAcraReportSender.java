package com.kodemore.acra;

import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import android.content.Context;

import com.kodemore.collection.KmList;
import com.kodemore.utility.KmBridge;

public abstract class KmAcraReportSender
    implements ReportSender
{
    //##################################################
    //# constructor
    //##################################################

    public KmAcraReportSender()
    {
        // none
    }

    //##################################################
    //# send
    //##################################################

    @Override
    public void send(CrashReportData data) throws ReportSenderException
    {
        try
        {
            handleErrorReport(data);
        }
        catch ( Exception ex )
        {
            // do nothing
        }
    }

    //##################################################
    //# abstract
    //##################################################

    protected abstract void handleErrorReport(CrashReportData data);

    //##################################################
    //# accessing
    //##################################################

    protected Context getContext()
    {
        return KmBridge.getInstance().getApplicationContext();
    }

    protected KmList<KmAcraField> getErrorReportFields()
    {
        KmList<KmAcraField> v;
        v = new KmList<KmAcraField>();
        v.addAll(KmAcraField.values());
        return v;
    }
}
