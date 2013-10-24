package com.kodemore.acra;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;

import com.kodemore.smtp.KmSmtpClient;
import com.kodemore.smtp.KmSmtpSimpleMessage;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.time.KmDate;
import com.kodemore.utility.KmLog;
import com.kodemore.utility.Kmu;

public class KmAcraEmailSender
    extends KmAcraReportSender
{
    //##################################################
    //# variables
    //##################################################

    private String _host;
    private int    _port;
    private String _user;
    private String _password;

    private String _toAddress;
    private String _fromAddress;
    private String _subjectPrefix;

    //##################################################
    //# constructor
    //##################################################

    public KmAcraEmailSender()
    {
        // none
    }

    //##################################################
    //# accessing
    //##################################################

    public String getHost()
    {
        return _host;
    }

    public void setHost(String e)
    {
        _host = e;
    }

    public int getPort()
    {
        return _port;
    }

    public void setPort(int e)
    {
        _port = e;
    }

    public String getUser()
    {
        return _user;
    }

    public void setUser(String e)
    {
        _user = e;
    }

    public String getPassword()
    {
        return _password;
    }

    public void setPassword(String e)
    {
        _password = e;
    }

    public String getToAddress()
    {
        return _toAddress;
    }

    public void setToAddress(String e)
    {
        _toAddress = e;
    }

    public String getFromAddress()
    {
        return _fromAddress;
    }

    public void setFromAddress(String e)
    {
        _fromAddress = e;
    }

    public String getSubjectPrefix()
    {
        return _subjectPrefix;
    }

    public void setSubjectPrefix(String e)
    {
        _subjectPrefix = e;
    }

    private String getSubject()
    {
        return getSubjectPrefix() + ", " + formatDate();
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public void handleErrorReport(CrashReportData data)
    {
        try
        {
            sendToEmail(data);
        }
        catch ( Exception ex )
        {
            sendToLog(data);
        }
    }

    //##################################################
    //# handle report
    //##################################################

    private void sendToEmail(CrashReportData data)
    {
        KmSmtpClient c;
        c = new KmSmtpClient();
        c.setHost(getHost());
        c.setPort(getPort());
        c.setUser(getUser());
        c.setPassword(getPassword());
        c.setMessage(createMessage(data));
        c.send();
    }

    private void sendToLog(CrashReportData data)
    {
        try
        {
            if ( data == null )
                return;

            String trace = data.get(ReportField.STACK_TRACE);
            if ( Kmu.hasValue(trace) )
                KmLog.error(trace);
        }
        catch ( Exception ex )
        {
            try
            {
                KmLog.error(ex);
            }
            catch ( Exception ignored )
            {
                // ignored
            }
        }
    }

    //##################################################
    //# utility
    //##################################################

    private KmSmtpSimpleMessage createMessage(CrashReportData data)
    {
        KmSmtpSimpleMessage e;
        e = new KmSmtpSimpleMessage();
        e.addTo(getToAddress());
        e.setFrom(getFromAddress());
        e.setSubject(getSubject());
        e.setBody(formatBody(data));
        e.setContentAuto();
        return e;
    }

    //##################################################
    //# support
    //##################################################

    private String formatDate()
    {
        KmDate date = KmDate.createTodayUtc();
        return date.format_mm_dd_yyyy();
    }

    private String formatBody(CrashReportData data)
    {
        KmStringBuilder out = new KmStringBuilder();

        for ( KmAcraField e : getErrorReportFields() )
            out.println(e.formatData(data));

        return out.toString();
    }

}
