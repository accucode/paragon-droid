package com.kodemore.acra;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import android.content.Context;

import com.kodemore.utility.Kmu;

public abstract class KmErrorReportSender
    implements ReportSender
{
    //##################################################
    //# enum
    //##################################################

    protected enum ErrorReportField
    {
        PACKAGE_NAME(ReportField.PACKAGE_NAME),
        APP_VERSION(ReportField.APP_VERSION_CODE),
        APP_VERSION_NAME(ReportField.APP_VERSION_NAME),
        ANDROID_VERSION(ReportField.ANDROID_VERSION),
        DEVICE_ID(ReportField.DEVICE_ID),
        PHONE_MODEL(ReportField.PHONE_MODEL),
        STACK_TRACE(ReportField.STACK_TRACE);

        private ReportField _field;

        ErrorReportField(ReportField f)
        {
            _field = f;
        }

        public String getFieldHeader()
        {
            switch ( this )
            {
                case PACKAGE_NAME:
                    return "Package Name : ";

                case APP_VERSION:
                    return "App Version Code : ";

                case APP_VERSION_NAME:
                    return "App Version Name : ";

                case ANDROID_VERSION:
                    return "Android Version : ";

                case DEVICE_ID:
                    return "Device ID : ";

                case PHONE_MODEL:
                    return "Phone Model : ";

                case STACK_TRACE:
                    return "Stack Trace : " + Kmu.CR_LF + Kmu.CR_LF;
            }

            return null;
        }

        public ReportField getField()
        {
            return _field;
        }
    }

    //##################################################
    //# variables
    //##################################################

    private Context _context;

    //##################################################
    //# constructor
    //##################################################

    public KmErrorReportSender(Context e)
    {
        _context = e;
    }

    //##################################################
    //# send
    //##################################################

    @Override
    public void send(CrashReportData report) throws ReportSenderException
    {
        try
        {
            handleErrorReport(report);
        }
        catch ( Exception ex )
        {
            // do nothing
        }
    }

    //##################################################
    //# abstract
    //##################################################

    protected abstract void handleErrorReport(CrashReportData report);

    //##################################################
    //# accessing
    //##################################################

    protected Context getContext()
    {
        return _context;
    }

    protected ErrorReportField[] getErrorReportFields()
    {
        return ErrorReportField.values();
    }

}
