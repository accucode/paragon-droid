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

/**
 * A simple view, useful for custom lists. 
 */
public class KmListViewRow
    extends KmRowLayout
{
    //##################################################
    //# variables
    //##################################################

    private KmColumnLayout _menuButtonColumn;

    //##################################################
    //# constructor
    //##################################################

    public KmListViewRow(KmUiManager ui)
    {
        super(ui);
    }

    //##################################################
    //# menu button
    //##################################################

    public void addMenuButton()
    {
        addViewNoWeight(createMenuButton());
    }

    private KmColumnLayout createMenuButton()
    {
        _menuButtonColumn = ui().newColumn();
        _menuButtonColumn.setGravityCenter();
        _menuButtonColumn.addImageButton(RR.drawable.menuIcon, newButtonAction());
        return _menuButtonColumn;
    }

    //##################################################
    //# action
    //##################################################//

    private KmAction newButtonAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleButton();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################//

    private void handleButton()
    {
        showContextMenu();
    }

    //##################################################
    //# convenience
    //##################################################//

    public void showMenuButton()
    {
        _menuButtonColumn.show();
    }

    public void goneMenuButton()
    {
        _menuButtonColumn.gone();
    }

}
