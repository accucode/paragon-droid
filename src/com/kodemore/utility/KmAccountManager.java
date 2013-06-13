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

package com.kodemore.utility;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.kodemore.collection.KmList;
import com.kodemore.comparator.KmComparator;
import com.kodemore.types.KmAccount;

/**
 * I provide a way to get user account information
 *  
 *  PERMISSIONS in AndroidManifest.xml
 *     <uses-permission android:name="android.permission.GET_ACCOUNTS" />
 */
public class KmAccountManager
{
    //##################################################
    //# formatters
    //##################################################

    public KmFormatterIF<KmAccount> getTypeFormatter()
    {
        return new KmFormatterIF<KmAccount>()
        {
            @Override
            public String format(KmAccount e)
            {
                return e.getType();
            }
        };
    }

    public KmFormatterIF<KmAccount> getNameFormatter()
    {
        return new KmFormatterIF<KmAccount>()
        {
            @Override
            public String format(KmAccount e)
            {
                return e.getName();
            }
        };
    }

    public KmFormatterIF<KmAccount> getDisplayStringFormatter()
    {
        return new KmFormatterIF<KmAccount>()
        {
            @Override
            public String format(KmAccount e)
            {
                return e.getDisplayString();
            }
        };
    }

    //##################################################
    //# comparators
    //##################################################

    public KmComparator<KmAccount> getTypeComparator()
    {
        return new KmComparator<KmAccount>()
        {
            @Override
            public int compare(KmAccount a, KmAccount b)
            {
                return c(a.getType(), b.getType());
            }
        };
    }

    public KmComparator<KmAccount> getNameComparator()
    {
        return new KmComparator<KmAccount>()
        {
            @Override
            public int compare(KmAccount a, KmAccount b)
            {
                return c(a.getName(), b.getName());
            }
        };
    }

    public KmComparator<KmAccount> getDisplayStringComparator()
    {
        return new KmComparator<KmAccount>()
        {
            @Override
            public int compare(KmAccount a, KmAccount b)
            {
                return c(a.getDisplayString(), b.getDisplayString());
            }
        };
    }

    //##################################################
    //# accounts 
    //##################################################

    public KmList<KmAccount> getAccounts()
    {
        Context context = getApplicationContext();
        Account[] accounts = AccountManager.get(context).getAccounts();

        KmList<KmAccount> v = new KmList<KmAccount>();

        for ( Account a : accounts )
        {
            KmAccount e;
            e = new KmAccount();
            e.setType(a.type);
            e.setName(a.name);
            v.add(e);
        }

        return v;
    }

    //##################################################
    //# support
    //##################################################

    private KmBridge getBridge()
    {
        return KmBridge.getInstance();
    }

    private Context getApplicationContext()
    {
        return getBridge().getApplicationContext();
    }
}
