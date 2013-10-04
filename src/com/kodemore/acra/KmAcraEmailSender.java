package com.kodemore.acra;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;

import com.kodemore.smtp.KmSmtpClient;
import com.kodemore.smtp.KmSmtpSimpleMessage;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.stub.MyConstantsIF;
import com.kodemore.time.KmDate;
import com.kodemore.utility.KmLog;
import com.kodemore.utility.Kmu;

public class KmAcraEmailSender
    extends KmAcraReportSender
{
    //##################################################
    //# constants
    //##################################################

    private static final String TO_ADDRESS     = "wlove@accucode.com";
    private static final String FROM_ADDRESS   = "app@world.com";
    private static final String SUBJECT_PREFIX = "crash report";

    //##################################################
    //# constructor
    //##################################################

    public KmAcraEmailSender()
    {
        // none
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public void handleErrorReport(CrashReportData data)
    {
        if ( isEmailEnabled() )
            sendToEmail(data);
        else
            sendToLog(data);
    }

    private boolean isEmailEnabled()
    {
        return MyConstantsIF.SMTP_RELAY_ENABLED;
    }

    //##################################################
    //# handle report
    //##################################################

    private void sendToEmail(CrashReportData data)
    {
        try
        {
            KmSmtpClient c;
            c = new KmSmtpClient();
            c.setHost(MyConstantsIF.SMTP_RELAY_HOST);
            c.setPort(MyConstantsIF.SMTP_RELAY_PORT);
            c.setUser(MyConstantsIF.SMTP_RELAY_USER);
            c.setPassword(MyConstantsIF.SMTP_RELAY_PASSWORD);
            c.setMessage(createMessage(data));
        }
        catch ( Exception ex )
        {
            sendToLog(data);
        }
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

    private String getFromAddress()
    {
        return FROM_ADDRESS;
    }

    private String getToAddress()
    {
        return TO_ADDRESS;
    }

    private String getSubject()
    {
        return SUBJECT_PREFIX + ", " + formatDate();
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

    /**
     * todo_wyatt: attachment 
     */
    //    //==================================================
    //    //= utility (email attachments)
    //    //==================================================
    //
    //    private KmSmtpAttachment getAttachment(CrashReportData report)
    //    {
    //        return getLogcatAttachment(report);
    //    }
    //
    //    private KmSmtpAttachment getLogcatAttachment(CrashReportData report)
    //    {
    //        String contents = report.get(ReportField.LOGCAT);
    //        Uri uri = writeAttachment(contents);
    //
    //        KmSmtpAttachment attach;
    //        attach = new KmSmtpAttachment(uri, LOGCAT_FILE_NAME);
    //        attach.setContent(KmSmtpContentType.FILE);
    //
    //        return attach;
    //    }
    //
    //    private Uri writeAttachment(String s)
    //    {
    //        KmFilePath file;
    //        file = new KmApplicationFilePath(LOGCAT_FILE_NAME);
    //        file.writeBytes(s.getBytes());
    //        return file.toUri();
    //    }
}
