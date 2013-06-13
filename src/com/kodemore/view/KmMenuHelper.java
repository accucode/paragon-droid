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

import android.view.Menu;
import android.view.MenuItem;

public class KmMenuHelper
{
    //##################################################
    //# variables
    //##################################################

    private Menu _menu;

    //##################################################
    //# constructor
    //##################################################

    public KmMenuHelper(Menu menu)
    {
        _menu = menu;
    }

    //##################################################
    //# accessing
    //##################################################

    public Menu getMenu()
    {
        return _menu;
    }

    //##################################################
    //# add elements
    //##################################################

    public void addOptions(KmAction action)
    {
        add("Options", RR.drawable.optionsIcon, action);
    }

    public void addShare(KmAction action)
    {
        add("Share", RR.drawable.shareIcon, action);
    }

    public void addFeedback(KmAction action)
    {
        add("Feedback", RR.drawable.feedbackIcon, action);
    }

    public void addUpgrade(KmAction action)
    {
        add("Upgrade", RR.drawable.upgradeIcon, action);
    }

    public void addSync(KmAction action)
    {
        add("Sync", RR.drawable.syncIcon, action);
    }

    public void addEdit(KmAction action)
    {
        add("Edit", RR.drawable.editIcon, action);
    }

    public void addSetup(KmAction action)
    {
        add("Setup", RR.drawable.setupIcon, action);
    }

    public void addHelp(KmAction action)
    {
        add("Help", RR.drawable.helpIcon, action);
    }

    public void addAbout(KmAction action)
    {
        add("About", RR.drawable.aboutIcon, action);
    }

    public void addInfo(KmAction action)
    {
        add("Info", RR.drawable.infoIcon, action);
    }

    public void addRating(KmAction action)
    {
        add("Rate Me", RR.drawable.rateIcon, action);
    }

    public void addAction(String title, KmAction action)
    {
        add(title, null, action);
    }

    public void addOther(String title, int resource, KmAction action)
    {
        add(title, resource, action);
    }

    public void addToastAction(String title, String toast)
    {
        KmToastAction action = new KmToastAction(toast);
        addAction(title, action);
    }

    //##################################################
    //# support
    //##################################################

    private void add(String title, Integer icon, KmAction action)
    {
        // http://blogfreakz.com/mobile-development/android-gui-icons-and-fonts/

        MenuItem e;
        e = getMenu().add(title);
        e.setOnMenuItemClickListener(action);

        if ( icon != null )
            e.setIcon(icon);
    }
}
