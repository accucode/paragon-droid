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

import android.content.Intent;
import android.view.View;

import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.KmEmail;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextField;
import com.kodemore.view.KmTextView;

/**
 * Create and send an email.
 */
public class TyEmailActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmEmail                _email;

    private KmTextView             _summaryText;
    private KmTextField            _toField;
    private KmTextField            _ccField;
    private KmTextField            _bccField;
    private KmTextField            _subjectField;
    private KmTextField            _bodyField;

    private KmSimpleIntentCallback _addAttachmentCallback;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _email = new KmEmail(getContext());

        _summaryText = ui().newTextView();
        _summaryText.setAutoSave();

        _toField = ui().newTextField();
        _toField.setAutoSave();

        _ccField = ui().newTextField();
        _ccField.setAutoSave();

        _bccField = ui().newTextField();
        _bccField.setAutoSave();

        _subjectField = ui().newTextField();
        _subjectField.setAutoSave();

        _bodyField = ui().newTextField();
        _bodyField.setAutoSave();

        _addAttachmentCallback = newAddAttachmentCallback();
        _addAttachmentCallback.register(ui());
    }

    private KmSimpleIntentCallback newAddAttachmentCallback()
    {
        return new KmSimpleIntentCallback()
        {
            @Override
            public Intent getRequest()
            {
                Intent i;
                i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("file/*");
                return Intent.createChooser(i, "Choose File");
            }

            @Override
            public void handleOk(Intent data)
            {
                _email.addAttachmentUri(data.getData());
                updateSummaryView();
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addViewFullWeight(createInputView());
        root.addView(createSummaryView());
        root.addButton("send", newSendAction());

        updateSummaryView();

        return root;
    }

    private KmTextView createSummaryView()
    {
        return _summaryText;
    }

    private View createInputView()
    {
        KmColumnLayout col;
        col = ui().newColumn();

        col.addLabel("To:");
        col.addView(createToRow());

        col.addLabel("CC:");
        col.addView(createCcRow());

        col.addLabel("BCC:");
        col.addView(createBccRow());

        col.addLabel("Subject:");
        col.addView(createSubjectRow());

        col.addLabel("Body:");
        col.addView(createBodyRow());

        col.addButton("Add Attachment", _addAttachmentCallback);

        return col.inScrollView();
    }

    private KmRowLayout createToRow()
    {
        KmRowLayout row;
        row = ui().newRow();
        row.addViewFullWeight(_toField);
        row.addButton("Add", newAddToAction());
        return row;
    }

    private KmRowLayout createCcRow()
    {
        KmRowLayout row;
        row = ui().newRow();
        row.addViewFullWeight(_ccField);
        row.addButton("Add", newAddCcAction());
        return row;
    }

    private KmRowLayout createBccRow()
    {
        KmRowLayout row;
        row = ui().newRow();
        row.addViewFullWeight(_bccField);
        row.addButton("Add", newAddBccAction());
        return row;
    }

    private KmRowLayout createSubjectRow()
    {
        KmRowLayout row;
        row = ui().newRow();
        row.addViewFullWeight(_subjectField);
        row.addButton("Set", newSetSubjectAction());
        return row;
    }

    private KmRowLayout createBodyRow()
    {
        KmRowLayout row;
        row = ui().newRow();
        row.addViewFullWeight(_bodyField);
        row.addButton("Set", newSetBodyAction());
        return row;
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newAddToAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleAddTo();
            }
        };
    }

    private KmAction newAddCcAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleAddCc();
            }
        };
    }

    private KmAction newAddBccAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleAddBcc();
            }
        };
    }

    private KmAction newSetSubjectAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSetSubject();
            }
        };
    }

    private KmAction newSetBodyAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleSetBody();
            }
        };
    }

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

    private void handleAddTo()
    {
        String s = _toField.getValue();
        _email.addTo(s);
        _toField.clearText();
        updateSummaryView();
    }

    private void handleAddCc()
    {
        String s = _ccField.getValue();
        _email.addCc(s);
        _ccField.clearText();
        updateSummaryView();
    }

    private void handleAddBcc()
    {
        String s = _bccField.getValue();
        _email.addBcc(s);
        _bccField.clearText();
        updateSummaryView();
    }

    private void handleSetSubject()
    {
        String s = _subjectField.getValue();
        _email.setSubject(s);
        _subjectField.clearText();
        updateSummaryView();
    }

    private void handleSetBody()
    {
        String s = _bodyField.getValue();
        _email.setBody(s);
        _bodyField.clearText();
        updateSummaryView();
    }

    private void handleSend()
    {
        _email.send();
    }

    //##################################################
    //# utility
    //##################################################

    private void updateSummaryView()
    {
        _summaryText.setText(formatSummary());
    }

    private String formatSummary()
    {
        KmStringBuilder out;
        out = new KmStringBuilder();

        addToSummary(out, "To: ", _email.getTo().format());
        addToSummary(out, "CC: ", _email.getCc().format());
        addToSummary(out, "BCC: ", _email.getBcc().format());
        addToSummary(out, "Subject: ", _email.getSubject());
        addToSummary(out, "Body: ", _email.getBody());
        addToSummary(out, "Attachments: ", _email.getAttachmentUris().format());

        return out.toString();
    }

    private void addToSummary(KmStringBuilder out, String label, String value)
    {
        if ( value == null )
            value = "[none]";

        out.printfln("%s %s", label, value);
    }

}
