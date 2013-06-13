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

import android.text.InputType;
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.ftp.KmFtpClient;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextField;

/**
 * I test the ability to read and write files from a 
 * remote ftp server.  
 * 
 * I rely on the Apache Commons Net library.
 */
public class TyFtpDirectoryActivity
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
    private static final String DIRECTORY = "";

    //##################################################
    //# variables
    //##################################################

    private KmTextField         _textField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _textField = ui().newTextField();
        _textField.setSingleLine(false);
        _textField.setClickable(false);
        _textField.setFocusable(false);
        _textField.addInputType(InputType.TYPE_NULL);
        _textField.addInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        _textField.setAutoSave();
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
        buttons.addButton("List Directories / Files", newReadAction());

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

    //##################################################
    //# handle
    //##################################################

    private void handleRead()
    {
        _textField.setValue(readFile());
    }

    //##################################################
    //# utility
    //##################################################

    private String readFile()
    {
        KmFtpClient client = new KmFtpClient();
        String returnString = null;

        try
        {
            client.setHost(HOST);
            client.setLoginCredentials(USER, PASSWORD);
            client.connect();

            client.changeDirectory(DIRECTORY);

            KmList<String> directories = client.listDirectories();
            KmList<String> files = client.listFiles();

            KmStringBuilder builder = new KmStringBuilder();

            builder.append("Directories in " + DIRECTORY + " :  \n \n");
            for ( String s : directories )
                builder.append(s + "\n");

            builder.append("\n \n");

            builder.append("Files in " + DIRECTORY + " :  \n \n");
            for ( String s : files )
                builder.append(s + "\n");

            returnString = builder.toString();
        }
        catch ( Exception ex )
        {
            alert(ex.toString());
            returnString = null;
        }
        finally
        {
            client.disconnect();

            if ( client.hasException() )
                alert("EXCEPTION : \n" + client.getException().toString());

            if ( client.hasResponse() )
                alert("RESPONSE : \n" + client.getReponse());
        }

        return returnString;
    }

}
