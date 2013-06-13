/*
  Copyright (c) 2005-2012 Wyatt Love

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
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

import android.graphics.Color;
import android.view.View;

import com.kodemore.ftp.KmFtpClient;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextField;
import com.kodemore.view.value.KmStringValue;

/**
 * I test the ability to read and write files from a 
 * remote ftp server.  
 * 
 * I rely on the Apache Commons Net library.
 */
public class TyFtpActivity
    extends KmActivity
{
    /** 
     * This is designed to work with FTP service DriveHQ.
     */

    //##################################################
    //# constants
    //##################################################

    private static final String HOST      = "";
    private static final String USER      = "";
    private static final String PASSWORD  = "";
    private static final String FILE      = "";
    private static final String DIRECTORY = "";

    //test

    //##################################################
    //# variables
    //##################################################

    private KmTextField         _textField;
    private KmStringValue       _returnString;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _textField = ui().newTextField();
        _textField.setTextColor(Color.RED);
        /**
         * (steve) well i put this one line of code in here to 
         * try and fix this. then i started getting null pointers, maybe connectivity
         * maybe not, so then i commented out the line and it is still
         * not working...dumb.
         */
        _textField.setMultiLine();
        _textField.setAutoSave();

        _returnString = ui().newStringValue();
        _returnString.setAutoSave();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        root.addViewFullWeight(_textField);

        KmRowLayout buttons;
        buttons = root.addEvenRow();
        buttons.addButton("Read", newReadAction());
        buttons.addButton("Write", newWriteAction());

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newReadAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleRead();
            }
        };
    }

    private KmAction newWriteAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleWrite();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleRead()
    {
        /**
         * (steve) it would seem that the set text 
         * takes issue with carriage returns, when there are none in
         * the file to be read the _textField populates. When I add
         * one return to the text in the same file and
         * re upload it to the ftp site, the _textField fails
         * to show any text at all.
         * 
         *      (wyatt)
         *      I'm not sure I understand, but this would seem like 
         *      expected behavior.  I see you added a toast below; what
         *      does it show?  Does the ftp client return the correct 
         *      value? Or is the problem with with the text field?  
         *      Text fields are likely configured for single line editing
         *      by default unless you changed it.
         *      
         *      (steve) ah, i was un aware of that. the toast
         *      message does display properly, only nothing appears in the 
         *      text field.
         */
        readFile();
        _textField.setText(_returnString.getValue());
        toast(_returnString.getValue());
    }

    private void handleWrite()
    {
        writeFile(_textField.getValue());
    }

    //##################################################
    //# utility
    //##################################################

    private void readFile()
    {
        KmFtpClient client = new KmFtpClient();
        toast("working...");
        try
        {
            client.setHost(HOST);
            client.setLoginCredentials(USER, PASSWORD);
            client.connect();

            client.changeDirectory(DIRECTORY);

            _returnString.setValue(client.readFromFile(FILE));
        }
        catch ( Exception ex )
        {
            alert(ex.toString());
            _returnString = null;
        }
        finally
        {
            client.disconnect();

            if ( client.hasException() )
                alert("EXCEPTION : \n" + client.getException().toString());

            if ( client.hasResponse() )
                alert("RESPONSE : \n" + client.getReponse());
        }
    }

    private void writeFile(String value)
    {
        KmFtpClient client = new KmFtpClient();

        try
        {
            client.setHost(HOST);
            client.setLoginCredentials(USER, PASSWORD);
            client.connect();

            client.changeDirectory(DIRECTORY);

            client.writeToFile(value, FILE);
        }
        catch ( Exception ex )
        {
            alert(ex.toString());
        }
        finally
        {
            client.disconnect();

            if ( client.hasException() )
                alert("EXCEPTION : \n" + client.getException().toString());

            if ( client.hasResponse() )
                alert("RESPONSE : \n" + client.getReponse());
        }
    }
}
