package com.kodemore.stub;

import com.kodemore.utility.Kmu;

/**
 * I define a number of application specific constants.
 * All values should be carefully reviewed each time a new project is started.
 */
public interface MyConstantsIF
{
    //##################################################
    //# debugging
    //##################################################

    /**
     * Set to true when developing and debugging.
     * This enables various options like the test pages.
     */
    boolean debugging                 = true;

    //##################################################
    //# paths
    //##################################################

    /**
     * The application's package name.
     * E.g.: "com.kodemore.stub".
     * Defaults to the package of THIS class.
     * Should match the package declared in AndroidManifest.xml.
     */
    String  APPLICATION_PACKAGE       = Kmu.getPackageName(MyConstantsIF.class);

    /**
     * This constant is assuming that the package of this class
     * is in the root folder.  Feel free to change the settings
     * based on your particular needs.
     */
    String  SHARED_APPLICATION_FOLDER = APPLICATION_PACKAGE;

    //##################################################
    //# smtp relay
    //##################################################

    /**
     * Some tools use an smtp relay to send emails, rather than relying on the normal 
     * email apps.  This allows us to send emails in batch, and/or without interrupting
     * the user.  The smtp relay is limited to sending email, and cannot be used
     * to pop/fetch email messages.
     */
    boolean SMTP_RELAY_ENABLED        = false;
    String  SMTP_RELAY_HOST           = "smtp.sendgrid.net";
    int     SMTP_RELAY_PORT           = 587;
    String  SMTP_RELAY_USER           = "user";
    String  SMTP_RELAY_PASSWORD       = "password";

}
