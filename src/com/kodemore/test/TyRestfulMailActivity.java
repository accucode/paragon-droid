/*
  Copyright (c) 2005-2009 www.kodimport android.view.View;

import com.kodemore.utility.KmActivity;
import com.kodemore.utility.KmColumnLayout;
import com.kodemore.utility.KmRowLayout;
import com.kodemore.utility.KmTextField;
t limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */

package com.kodemore.test;

import org.apache.commons.net.util.Base64;

import android.view.View;

import com.kodemore.http.KmHttpPost;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Create and send an email using Mail Gun server.
 */
public class TyRestfulMailActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    private static final String DEFAULT_TO      = "aprofitt@accucode.com";
    private static final String DEFAULT_FROM    = "aprofitt@accucode.com";
    private static final String DEFAULT_SUBJECT = "TEST - This is the subject";
    private static final String DEFAULT_BODY    = "This is a test......";

    private static final String HTTP_HOST_NAME  = "api.mailgun.net";
    private static final String HTTP_PATH       = "/v2/aobar.mailgun.org/messages";
    private static final int    HTTP_PORT       = 80;

    private static final String HTTP_AUTH_KEY   = "api:key-0zgbqbsilizpnsvcp7lsgw1d8zei9ls2";

    //##################################################
    //# variables
    //##################################################

    private KmHttpPost          _sender;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        //none
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addButton("send", newSendAction());

        return root;
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newSendAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSend();
            }
        };
    }

    //##################################################
    //# handle 
    //##################################################

    private void handleSend()
    {
        try
        {
            test();
        }
        catch ( Exception e )
        {
            toast(e.toString());
        }
    }

    //##################################################
    //# utility
    //##################################################

    /*
     * Here is the problem section that has SSL issues.
     
     *  I tried running this on port 443 and we get a file not found exception.
     *  If we run this with port 80 we get a "SSLProtocolException : SSL Handshake aborted"
     *  Additional details : "failure in SSL library, usually a protocol error".
     *                       "ssl23_get_server_hello : unknown protocol (external/openssl/ssl/s23_clnt.c"
     *                       
     *  From within Git terminal we can run "openssl s_client -connect api.mailgun.net:443 " and get a response.
     *  I had to use Git because msdos CLI doesn't have this tool.
     */
    public void test() throws Exception
    {
        _sender = new KmHttpPost();

        _sender.setHost(HTTP_HOST_NAME);
        _sender.setPath(HTTP_PATH);
        _sender.setPort(HTTP_PORT);
        _sender.setContentType("application/x-www-form-urlencoded");

        /*
         * assigns the HTTP basic authorization key user:pass encoded in Base64
         */
        String key = Base64.encodeBase64String(HTTP_AUTH_KEY.getBytes()).toString();
        _sender.setHeader("Authorization", "Basic " + key);

        _sender.setHeader("to", DEFAULT_TO);
        _sender.setHeader("from", DEFAULT_FROM);
        _sender.setHeader("subject", DEFAULT_SUBJECT);
        _sender.setHeader("text", DEFAULT_BODY);
        _sender.setHttps();
        _sender.submit();

        alert(_sender.getException().toString());

        /*
         * I was testing apache here (adam)
         * When we run this we get a "No peer certificate" exception.
         *  
        URL location = _sender.getUrl(); //this is a hack

        try
        {
            HttpClient client = new DefaultHttpClient();

            client.getConnectionManager().getSchemeRegistry().register(
                new Scheme("SSLSocketFactory", SSLSocketFactory.getSocketFactory(), 80));

            HttpContext localContext = new BasicHttpContext();

            HttpPost post = new HttpPost(location.toURI());

            HttpResponse response = client.execute(post, localContext);
        }
        catch ( Exception e )
        {
            alert(e.toString());
        }
         */
    }

}
