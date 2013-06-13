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

import android.view.View;

import com.kodemore.file.KmAssetFilePath;
import com.kodemore.file.KmFilePath;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextField;
import com.kodemore.view.KmTwoLineListView;

/**
 * Test acess to asset files.
 */
public class TyAssetFileActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField       _pathField;
    private KmTwoLineListView _listView;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _pathField = ui().newTextField();
        _pathField.setAutoSave();

        _listView = new KmTwoLineListView(ui());
        _listView.setAutoSave();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        root.addBoldLabel("Asset Path");

        KmRowLayout row;
        row = root.addRow();
        row.addViewFullWeight(_pathField);
        row.addButton("Test", newTestAction());

        root.addView(_listView);

        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newTestAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleTest();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleTest()
    {
        String path = _pathField.getText().toString();

        KmAssetFilePath f = new KmAssetFilePath(path);

        _listView.clearItems();

        _listView.addItem("name", f.getName());
        _listView.addItem("path", f.getPath());
        _listView.addItem("realPath", f.getRealPath());
        _listView.addNull();

        _listView.addItem("exists", f.exists());
        _listView.addItem("root", f.isRoot());
        _listView.addItem("file", f.isFile());
        _listView.addItem("folder", f.isFolder());
        _listView.addNull();

        _listView.addItem("parent", f.getParent());
        _listView.addItem("children...", "");

        for ( KmFilePath e : f.getChildren() )
            _listView.addItem("child", e);
    }
}
