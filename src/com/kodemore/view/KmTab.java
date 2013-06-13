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

package com.kodemore.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class KmTab
{
    //##################################################
    //# variables
    //##################################################

    private KmTabHost _host;
    private TabSpec   _inner;

    //##################################################
    //# constructor
    //##################################################

    public KmTab(KmTabHost host, String tag)
    {
        _host = host;
        _inner = _host.newTabSpec(tag);
    }

    //##################################################
    //# core
    //##################################################

    protected KmUiManager ui()
    {
        return _host.ui();
    }

    protected Context getContext()
    {
        return ui().getContext();
    }

    //##################################################
    //# accessing
    //##################################################

    public String getTag()
    {
        return _inner.getTag();
    }

    public void setIndicator(String title)
    {
        _inner.setIndicator(title);
    }

    public void setIndicator(int drawableId)
    {
        String title = "";
        setIndicator(title, drawableId);
    }

    public void setIndicator(String title, int drawableId)
    {
        Drawable drawable = _host.getResources().getDrawable(drawableId);
        _inner.setIndicator(title, drawable);
    }

    public void setContent(View view)
    {
        _inner.setContent(newFactory(view));
    }

    public void setContent(String text)
    {
        KmTextView e = ui().newTextView(text);
        setContent(e);
    }

    public void setContent(Class<? extends Activity> activity)
    {
        Intent intent = new Intent(getContext(), activity);
        setContent(intent);
    }

    public void setContent(Intent intent)
    {
        intent.putExtra(KmActivity.REQUEST_ID_OFFSET, _host.nextOffset());
        _inner.setContent(intent);
    }

    public void setup()
    {
        _host.addTab(_inner);
    }

    //##################################################
    //# support
    //##################################################

    private TabContentFactory newFactory(final View view)
    {
        return new TabContentFactory()
        {
            @Override
            public View createTabContent(String ignoredTag)
            {
                return view;
            }
        };
    }

}
