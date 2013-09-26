package com.kodemore.stub;

import com.kodemore.utility.Kmu;

public interface MyConstantsIF
{
    /**
     * Set to true when developing and debugging.
     * This enables various options like the test pages.
     */
    boolean debugging                 = true;

    //##################################################
    //# shared application folder
    //##################################################

    // review_steve: for external files - inserted this 
    /**
     * This constant is assuming that the package of this class
     * is in the root folder.  Feel free to change the settings
     * based on your particular needs.
     */
    String  SHARED_APPLICATION_FOLDER = Kmu.getPackageName(MyConstantsIF.class);
}
