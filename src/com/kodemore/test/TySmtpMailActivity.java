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

import android.net.Uri;
import android.view.View;

import com.kodemore.csv.KmCsvBuilder;
import com.kodemore.file.KmApplicationFilePath;
import com.kodemore.file.KmFilePath;
import com.kodemore.smtp.KmSmtpAttachment;
import com.kodemore.smtp.KmSmtpClient;
import com.kodemore.smtp.KmSmtpRecipient;
import com.kodemore.smtp.KmSmtpSimpleMessage;
import com.kodemore.smtp.types.KmSmtpContentType;
import com.kodemore.smtp.types.KmSmtpRecipientType;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Create and send an email using Sendgrid server.
 */
public class TySmtpMailActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    private static final String DEFAULT_TO      = "Adam Profitt <aprofitt@accucode.com>";
    private static final String DEFAULT_FROM    = "aprofitt@accucode.com";
    private static final String DEFAULT_SUBJECT = "TEST - This is the subject";
    private static final String DEFAULT_BODY    = "TEST - This is the body";

    private static final String FILE_NAME       = "test.csv";

    private static final String SMTP_HOST_NAME  = "smtp.sendgrid.net";
    private static final int    SMTP_PORT       = 587;
    private static final String SMTP_AUTH_USER  = "accucodetest";
    private static final String SMTP_AUTH_PWD   = "temp123!";

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

    private void test()
    {
        KmSmtpClient client = new KmSmtpClient(SMTP_HOST_NAME, SMTP_PORT);

        /*
         * Sets common security measures, works with Sendgrid
         */
        client.setCommonSecurity();
        client.setLoginCredentials(SMTP_AUTH_USER, SMTP_AUTH_PWD);

        KmSmtpSimpleMessage msg = new KmSmtpSimpleMessage();

        KmSmtpRecipient to = new KmSmtpRecipient(KmSmtpRecipientType.TO, DEFAULT_TO);

        KmSmtpAttachment attach = new KmSmtpAttachment(
            createCsv(FILE_NAME),
            FILE_NAME,
            KmSmtpContentType.FILE);

        msg.addRecipient(to);
        msg.setContentMultipart();
        msg.addAttachment(attach);
        msg.setSubject(DEFAULT_SUBJECT);
        msg.setFrom(DEFAULT_FROM);
        msg.setBody(DEFAULT_BODY);

        client.setMessage(msg);
        client.sendMessage();

        feedbackAlert(client);
    }

    private Uri createCsv(String fileName)
    {
        KmCsvBuilder csv = new KmCsvBuilder();
        csv.printField("this is");
        csv.printField("a test");
        csv.printField("to see");
        csv.printField("if attachments");
        csv.printField("work");
        csv.endRecord();

        KmFilePath file = new KmApplicationFilePath(fileName);

        file.writeString(csv.toString());

        return file.toUri();
    }

    private void feedbackAlert(KmSmtpClient client)
    {
        boolean isSent = client.isSent();
        String ex = "none";

        if ( client.getException() != null )
            ex = client.getException().toString();

        int resp = client.getResponse();

        alert(" MESSAGE SENT : " + isSent + "\n RESPONSE CODE : " + resp + " \n EXCEPTION : " + ex);
    }
}
