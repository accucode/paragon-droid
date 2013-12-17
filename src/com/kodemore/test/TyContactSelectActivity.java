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
import com.kodemore.contact.KmContactAddress;
import com.kodemore.contact.KmContactEmail;
import com.kodemore.contact.KmContactEvent;
import com.kodemore.contact.KmContactIm;
import com.kodemore.contact.KmContactManager;
import com.kodemore.contact.KmContactNickname;
import com.kodemore.contact.KmContactOrganization;
import com.kodemore.contact.KmContactPhone;
import com.kodemore.contact.KmContactRelation;
import com.kodemore.contact.KmContactStructuredName;
import com.kodemore.contact.KmContactWebsite;
import com.kodemore.contact.KmRawContact;
import com.kodemore.intent.KmSimpleIntentCallback;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmImageView;
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

    private KmImageView            _photo;

    private KmTwoLineListView      _list;
    private KmSimpleIntentCallback _pickCallback;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _photo = new KmImageView(ui());
        _photo.setAutoSave();

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
        root.addView(_photo);
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

        _photo.setImageBitmap(c.getPhoto());

        _list.clearItems();
        _list.addItem("Id", c.getId());
        _list.addItem("Display Name", c.getDisplayName());

        _list.addItem("Starred", c.getStarred());
        _list.addItem("Has Phone", c.getHasPhoneNumber());
        _list.addItem("Times Contacted", c.getTimesContacted());
        _list.addItem("Last Time Contacted", c.getLastTimeContacted().format_m_d_yyyy_h_mm());
        _list.addItem("Send to Voicemail", c.getSendToVoicemail());

        for ( KmRawContact e : c.getRawContacts() )
        {
            _list.addItem("Raw Contact " + e.getId(), "==============================");
            _list.addItem("Name", e.getAccountName());
            _list.addItem("Type", e.getAccountType());

            if ( e.hasStructuredName() )
            {
                KmContactStructuredName name = e.getStructuredName();
                _list.addItem("Display Name", name.getDisplayName());
                _list.addItem("Prefix", name.getPrefix());
                _list.addItem("First", name.getFirstName());
                _list.addItem("Middle", name.getMiddleName());
                _list.addItem("Last", name.getLastName());
                _list.addItem("Suffix", name.getSuffix());
                _list.addItem("Phonetic first", name.getPhoneticFirstName());
                _list.addItem("Phonetic middle", name.getPhoneticMiddleName());
                _list.addItem("Phonetic last", name.getPhoneticLastName());
            }

            if ( e.hasNickname() )
            {
                KmContactNickname nick = e.getNickname();
                _list.addItem("Nickname Type", nick.getTypeName());
                _list.addItem("Nickname", nick.getName());
            }

            _list.addItem("Notes", e.getNote());

            if ( e.hasOrganization() )
            {
                KmContactOrganization org = e.getOrganization();
                _list.addItem("Organization", "--------------------------------");
                _list.addItem("Type", org.getTypeName());
                _list.addItem("Company", org.getCompany());
                _list.addItem("Title", org.getTitle());
                _list.addItem("Department", org.getDepartment());
                _list.addItem("Job Description", org.getJobDescription());
            }

            _list.addItem("Emails", "--------------------------------");
            for ( KmContactEmail email : e.getEmails() )
            {
                String type = email.getTypeName();

                if ( email.isPrimary() )
                    type = type + " - PRIMARY";

                _list.addItem(type, email.getAddress());
            }

            _list.addItem("Phones", "--------------------------------");
            for ( KmContactPhone phone : e.getPhones() )
            {
                String type = phone.getTypeName();

                if ( phone.isPrimary() )
                    type = type + " - PRIMARY";

                _list.addItem(type, phone.getNumber());
            }

            _list.addItem("Addresses", "--------------------------------");
            for ( KmContactAddress address : e.getAddresses() )
            {
                String type = address.getTypeName();

                if ( address.isPrimary() )
                    type = type + " - PRIMARY";

                _list.addItem("Type", type);
                _list.addItem("Street", address.getStreet());
                _list.addItem("City", address.getCity());
                _list.addItem("State", address.getRegion());
                _list.addItem("Zip", address.getPostCode());
            }

            _list.addItem("IMs", "--------------------------------");
            for ( KmContactIm im : e.getIms() )
            {
                _list.addItem("Type", im.getTypeName());
                _list.addItem("Protocol", im.getProtocolName());
                _list.addItem("Data", im.getData());
            }

            _list.addItem("Websites", "--------------------------------");
            for ( KmContactWebsite web : e.getWebsites() )
                _list.addItem(web.getTypeName(), web.getUrl());

            _list.addItem("Relations", "--------------------------------");
            for ( KmContactRelation rel : e.getRelations() )
                _list.addItem(rel.getTypeName(), rel.getName());

            _list.addItem("Events", "--------------------------------");
            for ( KmContactEvent event : e.getEvents() )
                _list.addItem(event.getTypeName(), event.formatStartDate());

            _list.addItem("Custom Ringtone", "--------------------------------");
            _list.addItem("URI", e.getCustomRingtoneUri());
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
