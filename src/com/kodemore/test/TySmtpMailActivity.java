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
import com.kodemore.smtp.types.KmSmtpRecipientType;
import com.kodemore.string.KmStringBuilder;
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

    private static final String DEFAULT_TO      = "user@acme.com";
    private static final String DEFAULT_FROM    = "user@acme.com";
    private static final String DEFAULT_SUBJECT = "TEST - This is the subject";
    private static final String DEFAULT_BODY    = "TEST - This is the body";

    private static final String FILE_NAME       = "test.csv";

    private static final String SMTP_HOST_NAME  = "smtp.sendgrid.net";
    private static final int    SMTP_PORT       = 587;
    private static final String SMTP_USER       = "user";
    private static final String SMTP_PASSWORD   = "password";

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
        KmSmtpRecipient to = new KmSmtpRecipient(KmSmtpRecipientType.TO, DEFAULT_TO);

        Uri fileUri = writeCsvFile(FILE_NAME);

        KmSmtpAttachment attach;
        attach = new KmSmtpAttachment();
        attach.setUri(fileUri);
        attach.setFileName(FILE_NAME);

        KmSmtpSimpleMessage msg;
        msg = new KmSmtpSimpleMessage();
        msg.addRecipient(to);
        msg.setSubject(DEFAULT_SUBJECT);
        msg.setFrom(DEFAULT_FROM);
        msg.setBody(DEFAULT_BODY);
        msg.setContentMultipart();
        msg.addAttachment(attach);

        KmSmtpClient client;
        client = new KmSmtpClient();
        client.setHost(SMTP_HOST_NAME);
        client.setPort(SMTP_PORT);
        client.setUser(SMTP_USER);
        client.setPassword(SMTP_PASSWORD);
        client.setAuthenticationLogin();
        client.setTls();
        client.setMessage(msg);
        client.send();

        showAlert(client);
    }

    private Uri writeCsvFile(String fileName)
    {
        KmCsvBuilder csv;
        csv = new KmCsvBuilder();
        csv.printField("this is");
        csv.printField("a test");
        csv.printField("to see");
        csv.printField("if attachments");
        csv.printField("work");
        csv.endRecord();

        KmFilePath file;
        file = new KmApplicationFilePath(fileName);
        file.writeString(csv.toString());

        return file.toUri();
    }

    private void showAlert(KmSmtpClient client)
    {
        KmStringBuilder out;
        out = new KmStringBuilder();
        out.printfln("Message Sent: %s", client.isSent());
        out.printfln("Response Code: %s", client.getReplyCode());
        out.printfln("Exception: %s", client.formatException());

        alert(out);
    }
}
