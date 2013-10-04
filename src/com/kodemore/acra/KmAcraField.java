package com.kodemore.acra;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;

import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.Kmu;

public enum KmAcraField
{
    //##################################################
    //# values
    //##################################################

    PACKAGE_NAME(ReportField.PACKAGE_NAME),
    APP_VERSION(ReportField.APP_VERSION_CODE),
    APP_VERSION_NAME(ReportField.APP_VERSION_NAME),
    ANDROID_VERSION(ReportField.ANDROID_VERSION),
    DEVICE_ID(ReportField.DEVICE_ID),
    PHONE_MODEL(ReportField.PHONE_MODEL),
    STACK_TRACE(ReportField.STACK_TRACE);

    //##################################################
    //# variables
    //##################################################

    private ReportField _field;

    //##################################################
    //# constructor
    //##################################################

    private KmAcraField(ReportField e)
    {
        _field = e;
    }

    //##################################################
    //# accessing
    //##################################################

    public String getLabel()
    {
        switch ( this )
        {
            case PACKAGE_NAME:
                return "Package Name";

            case APP_VERSION:
                return "App Version Code";

            case APP_VERSION_NAME:
                return "App Version Name";

            case ANDROID_VERSION:
                return "Android Version";

            case DEVICE_ID:
                return "Device ID";

            case PHONE_MODEL:
                return "Phone Model";

            case STACK_TRACE:
                return "Stack Trace" + Kmu.CR_LF + Kmu.CR_LF;
        }

        return name();
    }

    public String getValueFor(CrashReportData data)
    {
        return data.getProperty(_field);
    }

    public String formatData(CrashReportData data)
    {
        KmStringBuilder out;
        out = new KmStringBuilder();
        out.print(getLabel());
        out.print(": ");
        out.println(getValueFor(data));
        return out.toString();
    }

}
