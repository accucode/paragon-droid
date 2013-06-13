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
import android.widget.LinearLayout;

/**
 * Custom Activity with optional sliding menus on both sides.
 */
public abstract class KmSlideMenuActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private TheScrollView               _scrollView;
    private KmRowLayout                 _scrollRow;

    private KmListView<KmActionWrapper> _leftMenu;
    private KmListView<KmActionWrapper> _rightMenu;

    private boolean                     _showsMain;
    private KmColumnLayout              _mainColumn;
    private View                        _mainView;
    private View                        _mainHeader;
    private KmButton                    _leftButton;
    private KmButton                    _rightButton;

    //##################################################
    //# create
    //##################################################

    @Override
    protected void init()
    {
        _mainView = new View(getContext());
        _mainHeader = new View(getContext());

        _leftMenu = newMenuList();
        _leftButton = ui().newButton("Left Menu");
        _leftButton.getHelper().gone();

        _rightMenu = newMenuList();
        _rightButton = ui().newButton("Right Menu");
        _rightButton.getHelper().gone();
    }

    @Override
    protected View createLayout()
    {
        setup();

        createMain();
        createScrollView();

        return _scrollView;
    }

    private void createMain()
    {
        KmRowLayout row;
        row = ui().newRow();

        row.addView(_leftButton);
        row.addViewFullWeight(_mainHeader);
        row.addView(_rightButton);

        _mainColumn = ui().newColumn();
        _mainColumn.setItemWidth(getScreenWidth());
        _mainColumn.addView(row);
        _mainColumn.addViewFullWeight(_mainView);
    }

    private void createScrollView()
    {
        _scrollRow = ui().newRow();
        _scrollRow.addView(_leftMenu);
        _scrollRow.addView(_mainColumn);
        _scrollRow.addView(_rightMenu);

        _scrollView = new TheScrollView(ui());
        _scrollView.disableFadingEdge();
        _scrollView.disableTouchEvents();
        _scrollView.addView(_scrollRow);
    }

    //##################################################
    //# set default views
    //##################################################

    public void setMainHeader(View v)
    {
        _mainHeader = v;
    }

    public void setMainView(View v)
    {
        _mainView = v;
    }

    //##################################################
    //# side menu methods
    //##################################################

    public void addLeftMenuItem(String s, KmAction a)
    {
        KmActionWrapper item;
        item = new KmActionWrapper();
        item.setName(s);
        item.setAction(a);

        _leftButton.getHelper().show();
        _leftMenu.addItem(item);
    }

    public void addRightMenuItem(String s, KmAction a)
    {
        KmActionWrapper item;
        item = new KmActionWrapper();
        item.setName(s);
        item.setAction(a);

        _rightButton.getHelper().show();
        _rightMenu.addItem(item);
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newSlideLeftAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleShowLeft();
            }
        };
    }

    private KmAction newSlideRightAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleShowRight();
            }
        };
    }

    private KmAction newSlideMainAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleShowMain();
            }
        };
    }

    //##################################################
    //# create menu
    //##################################################

    private KmListView<KmActionWrapper> newMenuList()
    {
        return new KmListView<KmActionWrapper>(ui())
        {
            @Override
            protected View getView(KmActionWrapper item, int index, View oldView)
            {
                KmOneLineView view;
                view = KmOneLineView.createOrCast(ui(), oldView);
                view.setText(item.getName());
                return view;
            }

            @Override
            protected void onItemClick(KmActionWrapper item)
            {
                item.fire();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleShowLeft()
    {
        _showsMain = false;
        _leftButton.setOnClickListener(newSlideMainAction());
        _scrollView.smoothScrollToStart();
    }

    private void handleShowRight()
    {
        _showsMain = false;
        _rightButton.setOnClickListener(newSlideMainAction());
        _scrollView.smoothScrollToEnd();
    }

    private void handleShowMain()
    {
        showMain(true);
    }

    private void showMain(boolean smooth)
    {
        _showsMain = true;

        _leftButton.setOnClickListener(newSlideLeftAction());
        _rightButton.setOnClickListener(newSlideRightAction());

        _scrollView.scrollTo(_mainColumn, smooth);
    }

    //##################################################
    //# back button 
    //##################################################

    @Override
    public void onBackPressed()
    {
        if ( _showsMain )
            super.onBackPressed();
        else
            handleShowMain();
    }

    //##################################################
    //# abstact
    //##################################################

    public abstract void setup();

    //##################################################
    //# custom view
    //##################################################

    /**
     * I horizontal scroll view in order to override the layout widths
     * of the left, right, and main sections to custom values.  Also,
     * I scroll to the main view after my layout is complete. 
     */
    private class TheScrollView
        extends KmHorizontalScrollView
    {
        public TheScrollView(KmUiManager ui)
        {
            super(ui);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
        {
            int w = getWidth();

            getRowParamsFor(_leftMenu).width = w - _leftButton.getWidth();
            getRowParamsFor(_rightMenu).width = w - _rightButton.getWidth();
            getRowParamsFor(_mainColumn).width = w;

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        public void onLayout(boolean changed, int l, int t, int r, int b)
        {
            super.onLayout(changed, l, t, r, b);

            showMain(false);
        }

        private LinearLayout.LayoutParams getRowParamsFor(View e)
        {
            return _scrollRow.getLayoutParamsFor(e);
        }
    }
}
