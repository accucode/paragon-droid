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

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.kodemore.utility.KmConstantsIF;

public class KmTabHost
    extends TabHost
{
    //##################################################
    //# constants
    //##################################################

    private static final int DEFAULT_OFFSET = 1000;

    //##################################################
    //# variables
    //##################################################

    private int              _nextTag;
    private int              _idOffset;

    private KmRowLayout      _titleView;

    //##################################################
    //# constructor
    //##################################################

    public KmTabHost(KmTabActivity activity)
    {
        /**
         *  super(activity,null); is required here for forward compatibility from android 2.1
         *  
         *  http://stackoverflow.com/questions/5408452/honeycomb-and-tabhost-specs
         */

        super(activity, null);

        _idOffset = DEFAULT_OFFSET;

        TabWidget tab;
        tab = new TabWidget(activity);
        tab.setId(android.R.id.tabs);

        FrameLayout frame;
        frame = new FrameLayout(activity);
        frame.setId(android.R.id.tabcontent);

        KmColumnLayout col;
        col = ui().newColumn();
        col.setItemHeightAsPercentageOfScreen(KmConstantsIF.TITLE_BAR_HEIGHT);
        col.addView(createTitleView());
        col.setItemHeightWrapContent();
        col.addView(tab);
        col.setItemHeightMatchParent();
        col.addView(frame);

        TabHost host;
        host = this;
        host.setId(android.R.id.tabhost);
        host.addView(col);
        host.setup(activity.getLocalActivityManager());
    }

    //##################################################
    //# accessing
    //##################################################

    /**
     * Get my activity.  See my constructor.
     */
    public KmTabActivity getActivity()
    {
        return (KmTabActivity)getContext();
    }

    public KmUiManager ui()
    {
        return getActivity().ui();
    }

    public void setTitleView(KmTitleView view)
    {
        _titleView.addViewFullWeight(view);
    }

    //##################################################
    //# tabs
    //##################################################

    public KmTab addTab()
    {
        return addTab(getNextTag());
    }

    public KmTab addTab(String tag)
    {
        return new KmTab(this, tag);
    }

    //##################################################
    //# support
    //##################################################

    private String getNextTag()
    {
        return "" + _nextTag++;
    }

    //##################################################
    //# id offset stuff
    //##################################################

    public int nextOffset()
    {
        int i = _idOffset;
        _idOffset = _idOffset + DEFAULT_OFFSET;
        return i;
    }

    //##################################################
    //# title bar
    //##################################################

    protected View createTitleView()
    {

        return _titleView = new KmRowLayout(ui());
    }
}
