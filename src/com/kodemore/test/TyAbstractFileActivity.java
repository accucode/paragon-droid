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

import java.util.Random;

import android.view.View;

import com.kodemore.file.KmFilePath;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.KmTimer;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextField;

/**
 * Test access to the local flat-file system.
 * Subclasses provide alternate implementations for various access.
 */
public abstract class TyAbstractFileActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField _textField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _textField = ui().newTextField();
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

        KmRowLayout row;
        row = root.addFullRow();
        row.addButton("R", newReadAction());
        row.addButton("W", newWriteAction());
        row.addButton("Add", newAppendAction());
        row.addButton("Avail", newAvailableAction());
        row.addButton("Exists", newExistsAction());
        row.addButton("Del", newDeleteAction());

        root.setItemHeightWrapContent();
        row = root.addFullRow();
        row.addButton("1,000", newRandomAction(1000));
        row.addButton("10,000", newRandomAction(10000));
        row.addButton("100,000", newRandomAction(100000));
        row.addButton("Clear", newClearAction());

        root.setItemHeightMatchParent();
        root.addView(_textField);

        return root;
    }

    //##################################################
    //# abstract 
    //##################################################

    protected abstract KmFilePath getFile();

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

    private KmAction newAppendAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleAppend();
            }
        };
    }

    private KmAction newExistsAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleExists();
            }
        };
    }

    private KmAction newAvailableAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleAvailable();
            }
        };
    }

    private KmAction newDeleteAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleDelete();
            }
        };
    }

    private KmAction newClearAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleClear();
            }
        };
    }

    private KmAction newRandomAction(final int n)
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleRandom(n);
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleRead()
    {
        KmTimer t = KmTimer.run();

        KmFilePath f = getFile();
        if ( !f.exists() )
        {
            toast("Missing");
            return;
        }

        String s = f.readString();
        _textField.setText(s);

        toast("Read: " + t.formatSeconds());
    }

    private void handleWrite()
    {
        KmTimer t = KmTimer.run();
        CharSequence s = _textField.getText();
        getFile().writeString(s);
        toast("Written: " + t.formatSeconds());
    }

    private void handleAppend()
    {
        KmTimer t = KmTimer.run();
        CharSequence s = _textField.getText();
        getFile().appendString(s);
        toast("Appended: " + t.formatSeconds());
    }

    private void handleAvailable()
    {
        toast("Available: " + getFile().isAvailable());
    }

    private void handleExists()
    {
        toast("Exists: " + getFile().exists());
    }

    private void handleDelete()
    {
        getFile().delete();
        toast("Deleted");
    }

    private void handleClear()
    {
        _textField.clearText();
        toast("Cleared");
    }

    private void handleRandom(int n)
    {
        String s = _textField.getText() + getRandomText(n);
        _textField.setText(s);
        toast("Appended");
    }

    //##################################################
    //# utility
    //##################################################

    private String getRandomText(int n)
    {
        Random r = new Random();
        KmStringBuilder out = new KmStringBuilder();

        while ( true )
        {
            int i = r.nextInt(100000);

            out.print(i);
            out.space();

            if ( out.length() >= n )
                break;
        }

        return out.toString();
    }
}
