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

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.contact.KmContact;
import com.kodemore.contact.KmContactEmail;
import com.kodemore.contact.KmContactManager;
import com.kodemore.contact.KmContactPhone;
import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTwoLineListView;

/**
 * Select a contact and retrieve its information.
 * 
 * PERMISSIONS in AndroidManifest.xml
 *     <uses-permission android:name="android.permission.READ_CONTACTS" />
 */
public class TyContactSelectActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTwoLineListView      _list;
    private KmSimpleIntentCallback _pickCallback;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _list = new KmTwoLineListView(ui());
        _list.setAutoSave();

        _pickCallback = newPickContactCallback();
        _pickCallback.register(ui());
    }

    //==================================================
    //= init :: callback
    //==================================================

    private KmSimpleIntentCallback newPickContactCallback()
    {
        return new KmSimpleIntentCallback()
        {
            @Override
            public Intent getRequest()
            {
                return getPickContactIntent();
            }

            @Override
            public void handleOk(Intent data)
            {
                handlePickCallback(data);
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
        root.addButton("Pick", _pickCallback);
        root.addView(_list);
        return root;
    }

    //##################################################
    //# handle
    //##################################################

    private void handlePickCallback(Intent data)
    {
        KmContact c = getContactFor(data);
        if ( c == null )
            return;

        _list.clearItems();
        _list.addItem("Id", c.getId());
        _list.addItem("Name", c.getDisplayName());

        for ( KmContactPhone e : c.getPhones() )
        {
            String name = Kmu.format("Phone: " + e.getTypeName());
            String value = e.getNumber();
            _list.addItem(name, value);
        }

        for ( KmContactEmail e : c.getEmails() )
        {
            String name = Kmu.format("Email: " + e.getTypeName());
            String value = e.getData();
            _list.addItem(name, value);
        }
    }

    //##################################################
    //# utility
    //##################################################

    private KmContact getContactFor(Intent data)
    {
        Uri uri = data.getData();

        KmContactManager mgr;
        mgr = new KmContactManager(this);

        KmList<KmContact> v = mgr.getContacts(uri);

        if ( v.isEmpty() )
        {
            toast("No contact found.");
            return null;
        }

        if ( !v.isSingleton() )
        {
            toast("Multiple contacts found.");
            return null;
        }

        KmContact c = v.getFirst();
        mgr.fill(c);
        return c;
    }
}
