package com.kodemore.acra;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.ReportingInteractionMode;
import org.acra.sender.ReportSender;

import android.app.Application;

import com.kodemore.utility.KmLog;

/**
 * I provide convenience methods to set up and access the ACRA
 * error reporting tool.
 * 
 * IMPORTANT... Although I provide most of the heavy lifting,
 * there are some special steps you must follow.
 * 
 *      1) Create a custom subclass of Application.  Mine is MyApplication.
 *      2) Register the MyApplication in your AndroidManifest.xml.
 *              See the <application android:name=".MyApplication" ...>
 *      3) Add the following annotation to your CLASS (not the constructor).
 *              @ReportsCrashes(formKey = "")
 *              
 */
public class KmAcra
{
    /**
     * Install ACRA using a default sender that reports errors to the Log.
     * This is a great place to start, even though it may not be especially useful.
     * 
     * Once you have this working, you can use the other installOn method below
     * to specify an alterate sender.  You may want to look at the KmAcraEmailSender,
     * or you can create your own custom sender.
     * 
     * I am typically called from the Application, and should only be called once 
     * when the application starts.
     * 
     * Everything must be in the order it is in.  This approach allows us to 
     * configure ACRA via functional code instead of declarative annotations.
     */
    public static void installOn(Application app)
    {
        installOn(app, new KmAcraLogSender());
    }

    /**
     * Install ACRA with a specific Sender.
     * For examples, see KmAcraLogSender and KmAcraEmailSender.
     */
    public static void installOn(Application app, ReportSender sender)
    {
        try
        {
            ACRAConfiguration config;
            config = ACRA.getNewDefaultConfig(app);
            config.setMode(ReportingInteractionMode.SILENT);

            ACRA.setConfig(config);
            ACRA.init(app);
            ACRA.getErrorReporter().setReportSender(sender);
        }
        catch ( Exception ex )
        {
            KmLog.error(ex);
        }
    }

    public static void handleSilentException(Exception ex)
    {
        try
        {
            ACRA.getErrorReporter().handleSilentException(ex);
        }
        catch ( IllegalStateException ex2 )
        {
            KmLog.error("ACRA not installed, cannot handle silent exception.");
            KmLog.error(ex);
        }
    }

}
