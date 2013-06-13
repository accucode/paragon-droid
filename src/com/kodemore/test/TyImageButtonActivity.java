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

import com.kodemore.collection.KmList;
import com.kodemore.collection.KmOrderedMap;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmButton;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.RR;

/**
 * A simple layout of built-in android icons.
 */
public class TyImageButtonActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmButton _changeLayoutButton;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        // none
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        addRowTo(root, getRowInfo1());
        addRowTo(root, getRowInfo2());
        addRow3(root);

        root.addFiller();

        addRowTo(root, getRowInfo4());
        addRowTo(root, getRowInfo5());
        addRowTo(root, getRowInfo6());
        addRowTo(root, getRowInfo7());
        addRowTo(root, getRowInfo8());
        addRowTo(root, getRowInfo9());
        addRow10(root);

        return root.inScrollView();
    }

    /**
     * My icons are currently in use in AO Bar
     */
    private KmOrderedMap<Integer,String> getRowInfo1()
    {
        KmOrderedMap<Integer,String> m;
        m = new KmOrderedMap<Integer,String>();
        m.put(RR.drawable.runIcon, "runIcon");
        m.put(RR.drawable.alertIcon, "alertIcon");
        m.put(RR.drawable.aboutIcon, "aboutIcon");
        m.put(RR.drawable.trashIcon, "trashIcon");
        m.put(RR.drawable.editIcon, "editIcon");
        m.put(RR.drawable.infoIcon, "infoIcon");
        m.put(RR.drawable.menuIcon, "menuIcon");
        m.put(RR.drawable.userInfoIcon, "userInfoIcon");
        m.put(RR.drawable.historyIcon, "historyIcon");
        m.put(RR.drawable.saveIcon, "saveIcon");
        return m;
    }

    private KmOrderedMap<Integer,String> getRowInfo2()
    {
        KmOrderedMap<Integer,String> m;
        m = new KmOrderedMap<Integer,String>();
        m.put(RR.drawable.feedbackIcon, "feedbackIcon");
        m.put(RR.drawable.shareIcon, "shareIcon");
        m.put(RR.drawable.uploadIcon, "uploadIcon");
        m.put(RR.drawable.advancedIcon, "advancedIcon");
        m.put(RR.drawable.viewIcon, "viewIcon");
        return m;
    }

    private void addRow3(KmColumnLayout root)
    {
        KmRowLayout row;
        row = root.addRow();

        addMailButton(row);
        addBackButton(row);
        addAlertButton(row);
        addWarningButton(row);
    }

    /**
     * My icons are not currently in use in AO Bar
     */
    private KmOrderedMap<Integer,String> getRowInfo4()
    {
        KmOrderedMap<Integer,String> m;
        m = new KmOrderedMap<Integer,String>();
        m.put(android.R.drawable.ic_lock_idle_lock, "ic_lock_idle_lock");
        m.put(android.R.drawable.ic_lock_idle_low_battery, "ic_lock_idle_low_battery");
        m.put(android.R.drawable.ic_lock_power_off, "ic_lock_power_off");
        m.put(android.R.drawable.ic_lock_silent_mode_off, "ic_lock_silent_mode_off");
        m.put(android.R.drawable.ic_media_ff, "ic_media_ff");
        m.put(android.R.drawable.ic_media_next, "ic_media_next");
        m.put(android.R.drawable.ic_media_pause, "ic_media_pause");
        m.put(android.R.drawable.ic_media_previous, "ic_media_previous");
        m.put(android.R.drawable.ic_media_rew, "ic_media_rew");
        return m;
    }

    private KmOrderedMap<Integer,String> getRowInfo5()
    {
        KmOrderedMap<Integer,String> m;
        m = new KmOrderedMap<Integer,String>();
        m.put(android.R.drawable.ic_input_add, "ic_input_add");
        m.put(android.R.drawable.ic_delete, "ic_delete");
        m.put(android.R.drawable.ic_dialog_dialer, "ic_dialog_dialer");
        m.put(android.R.drawable.ic_dialog_email, "ic_dialog_email");
        m.put(android.R.drawable.ic_dialog_map, "ic_dialog_map");
        m.put(android.R.drawable.ic_input_delete, "ic_input_delete");
        m.put(android.R.drawable.ic_input_get, "ic_input_get");
        m.put(android.R.drawable.ic_btn_speak_now, "ic_btn_speak_now");
        m.put(android.R.drawable.ic_lock_lock, "ic_lock_lock");
        m.put(android.R.drawable.ic_menu_add, "ic_menu_add");
        return m;
    }

    private KmOrderedMap<Integer,String> getRowInfo6()
    {
        KmOrderedMap<Integer,String> m;
        m = new KmOrderedMap<Integer,String>();
        m.put(android.R.drawable.ic_menu_agenda, "ic_menu_agenda");
        m.put(android.R.drawable.ic_menu_call, "ic_menu_call");
        m.put(android.R.drawable.ic_menu_camera, "ic_menu_camera");
        m.put(android.R.drawable.ic_lock_silent_mode, "ic_lock_silent_mode");
        m.put(android.R.drawable.ic_menu_close_clear_cancel, "ic_menu_close_clear_cancel");
        m.put(android.R.drawable.ic_menu_compass, "ic_menu_compass");
        m.put(android.R.drawable.ic_menu_crop, "ic_menu_crop");
        m.put(android.R.drawable.ic_menu_day, "ic_menu_day");
        m.put(
            android.R.drawable.ic_menu_always_landscape_portrait,
            "ic_menu_always_landscape_portrait");
        return m;
    }

    private KmOrderedMap<Integer,String> getRowInfo7()
    {
        KmOrderedMap<Integer,String> m;
        m = new KmOrderedMap<Integer,String>();
        m.put(android.R.drawable.ic_menu_gallery, "ic_menu_gallery");
        m.put(android.R.drawable.ic_menu_help, "ic_menu_help");
        m.put(android.R.drawable.ic_menu_manage, "ic_menu_manage");
        m.put(android.R.drawable.ic_menu_directions, "ic_menu_directions");
        m.put(android.R.drawable.ic_menu_mapmode, "ic_menu_mapmode");
        m.put(android.R.drawable.ic_menu_month, "ic_menu_month");
        m.put(android.R.drawable.ic_menu_revert, "ic_menu_revert");
        m.put(android.R.drawable.ic_menu_rotate, "ic_menu_rotate");
        m.put(android.R.drawable.ic_menu_my_calendar, "ic_menu_my_calendar");
        m.put(android.R.drawable.ic_menu_mylocation, "ic_menu_mylocation");
        return m;
    }

    private KmOrderedMap<Integer,String> getRowInfo8()
    {
        KmOrderedMap<Integer,String> m;
        m = new KmOrderedMap<Integer,String>();
        m.put(android.R.drawable.ic_menu_preferences, "ic_menu_preferences");
        m.put(android.R.drawable.ic_menu_search, "ic_menu_search");
        m.put(android.R.drawable.ic_menu_set_as, "ic_menu_set_as");
        m.put(android.R.drawable.ic_menu_today, "ic_menu_today");
        m.put(android.R.drawable.ic_menu_slideshow, "ic_menu_slideshow");
        m.put(android.R.drawable.ic_menu_sort_alphabetically, "ic_menu_sort_alphabetically");
        return m;
    }

    private KmOrderedMap<Integer,String> getRowInfo9()
    {
        KmOrderedMap<Integer,String> m;
        m = new KmOrderedMap<Integer,String>();
        m.put(android.R.drawable.ic_menu_week, "ic_menu_week");
        m.put(android.R.drawable.ic_menu_zoom, "ic_menu_zoom");
        m.put(android.R.drawable.btn_star, "btn_star");
        m.put(android.R.drawable.btn_star_big_off, "btn_star_big_off");
        m.put(android.R.drawable.btn_star_big_on, "btn_star_big_on");
        m.put(android.R.drawable.ic_menu_upload_you_tube, "ic_menu_upload_you_tube");
        m.put(android.R.drawable.ic_lock_idle_alarm, "ic_lock_idle_alarm");
        m.put(android.R.drawable.ic_lock_idle_charging, "ic_lock_idle_charging");
        return m;
    }

    private void addRow10(KmColumnLayout root)
    {
        KmRowLayout row;
        row = root.addRow();

        addMenuAdditionButton(row);
        addInputAdditionButton(row);
        addSearchButton(row);
        addChangeLayoutButton(row);
        addMenuPreferencesButton(row);
    }

    private void addRowTo(KmColumnLayout root, KmOrderedMap<Integer,String> info)
    {
        KmRowLayout row = root.addRow();

        KmList<Integer> resIds = info.getKeys();
        for ( Integer resId : resIds )
        {
            String msg = info.get(resId);
            KmAction action = newToastAction(msg);

            row.addImageButton(resId, action);
        }
    }

    private void addMailButton(KmRowLayout row)
    {
        KmButton b;
        b = row.addImageToastButton(RR.drawable.feedbackIcon, "feedbackIcon");
        b.setStyleGradient();
    }

    private void addWarningButton(KmRowLayout row)
    {
        KmButton b;
        b = row.addImageToastButton(RR.drawable.warningIcon, "warningIcon");
        b.setStyleWarning();
    }

    private void addAlertButton(KmRowLayout row)
    {
        KmButton b;
        b = row.addImageToastButton(RR.drawable.errorIcon, "errorIcon");
        b.setStyleError();
    }

    private void addChangeLayoutButton(KmRowLayout row)
    {
        _changeLayoutButton = row.addImageButton(
            android.R.drawable.btn_star_big_on,
            "Push",
            newChangeLayoutAction());
    }

    private void addSearchButton(KmRowLayout row)
    {
        KmButton b;
        b = row.addImageToastButton(android.R.drawable.ic_menu_search, "ic_menu_search");
        b.setStyleGradient();
    }

    private void addInputAdditionButton(KmRowLayout row)
    {
        KmButton b;
        b = row.addImageToastButton(android.R.drawable.ic_input_add, "ic_input_add");
        b.setStyleGradient();
    }

    private void addMenuAdditionButton(KmRowLayout row)
    {
        KmButton b;
        b = row.addImageToastButton(android.R.drawable.ic_menu_add, "ic_menu_add");
        b.setStyleGradient();
    }

    private void addBackButton(KmRowLayout row)
    {
        KmButton b;
        b = row.addImageToastButton(android.R.drawable.ic_input_delete, "ic_input_delete");
        b.setStyleGradient();
    }

    private void addMenuPreferencesButton(KmRowLayout row)
    {
        KmButton b;
        b = row.addImageToastButton(android.R.drawable.ic_menu_preferences, "ic_menu_preferences");
        b.setStyleGradient();
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newChangeLayoutAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleChangeLayout();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleChangeLayout()
    {
        _changeLayoutButton.toggleOrientation();
    }

}
