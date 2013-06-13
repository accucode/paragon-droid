package com.kodemore.test;

import org.apache.commons.net.util.Base64;

import android.view.View;

import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthRsaSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthSigner;

import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * This is currently not working correctly. I was attempting to use
 * the OAuthPlayground to get a client ID and client secret setup to
 * access the docs list from AndroidDemo@google.com. I used a "Recommended"
 * way of using OAuth and I wasn't able to get it to work yet.
 * 
 * See : google docs AOA: OAuth Playground
 */
public class TyOAuthActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################//

    private static final String CLIENT_ID     = "";
    private static final String CLIENT_SECRET = "";

    private static final String SCOPE         = "%2F%2Fdocs.google.com%2Ffeeds%2F+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fdrive+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fdrive.file+https%3A%2F%2Fspreadsheets.google.com%2Ffeeds%2F&access_type=offline";

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        // none
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("Run Google Doc Test", newTestAction());
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newTestAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                authenticate();
            }
        };
    }

    //##################################################
    //# handle 
    //##################################################

    private void authenticate()
    {
        try
        {
            GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
            oauthParameters.setOAuthConsumerKey(CLIENT_ID);

            byte[] clientSecret = Base64.encodeBase64(CLIENT_SECRET.getBytes());
            OAuthSigner signer = new OAuthRsaSha1Signer(new String(clientSecret));

            GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(signer);

            oauthParameters.setScope(SCOPE);

            oauthHelper.getUnauthorizedRequestToken(oauthParameters);

            String requestUrl = oauthHelper.createUserAuthorizationUrl(oauthParameters);
            String token = oauthHelper.getAccessToken(oauthParameters);

            alert(requestUrl + "\n\n" + token);
        }
        catch ( Exception ex )
        {
            alert(ex);
        }

    }
}
