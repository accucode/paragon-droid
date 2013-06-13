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

public interface RR
{
    public interface drawable
    {
        //##################################################
        //# icons
        //##################################################

        /**
         * A small icon used to navigate to the About Us page.
         */
        int aboutIcon              = com.kodemore.stub.R.drawable.about_light;

        /**
         * A small plus icon used to open the AddItem page.
         */
        int addIcon                = com.kodemore.stub.R.drawable.add_light;

        /**
         * A small plus in from of present icon used to open a new Order or Inventory
         * 
         */
        int addIconGreen           = com.kodemore.stub.R.drawable.add_new_green;

        /**
         * A small tool icon used to navigate to the Admin page.
         */
        int adminIcon              = com.kodemore.stub.R.drawable.setup_light;

        /**
         * A small list icon used to navigate to an Advanced activity.
         */
        int advancedIcon           = com.kodemore.stub.R.drawable.advanced_search_light;

        /**
         * A small alert icon used to indicate an alert.
         */
        int alertIcon              = com.kodemore.stub.R.drawable.warning_light;

        /**
         * A small down arrow icon used to navigate to an Assign activity.
         */
        int assignIcon             = com.kodemore.stub.R.drawable.assign_rep_light;

        /**
         * A small red arrow icon used to navigate to the previous activity.
         */
        int backIcon               = com.kodemore.stub.R.drawable.back_red;

        /**
         * A small 'X' icon used to cancel an action.
         */
        int cancelIcon             = com.kodemore.stub.R.drawable.exit_cancel_light;

        /**
         * Small company logo for use on buttons.
         */
        int companyLogoIcon        = com.kodemore.stub.R.drawable.accucode_glow_2;

        /**
         * A small icon used to navigate to the database activity.
         */
        int databaseIcon           = com.kodemore.stub.R.drawable.database_light;

        /**
         * A small red icon used to delete the last character entered.
         */
        int deleteIcon             = com.kodemore.stub.R.drawable.ic_button_delete;

        /**
         * A small grey down arrow used to indicate moving an item down in the sequence.
         */
        int downArrowIcon          = com.kodemore.stub.R.drawable.down_light;

        /**
         * A small red down arrow used to indicate moving an item down in the sequence.
         */
        int downArrowRedIcon       = com.kodemore.stub.R.drawable.down_red;

        /**
         * A small red down arrow used to indicate moving an item down in the sequence.
         */
        int downBottomArrowRedIcon = com.kodemore.stub.R.drawable.to_bottom;

        /**
         * A small pencil icon used to edit an item.
         */
        int editIcon               = com.kodemore.stub.R.drawable.edit_light;

        /**
         * A small alert icon used to indicate an error.
         */
        int errorIcon              = com.kodemore.stub.R.drawable.warning_light;

        /**
         * A small 'X' icon used to exit the window or app.
         */
        int exitIcon               = com.kodemore.stub.R.drawable.exit_cancel_light;

        /**
         * A small box with an arrow icon used to indicate exporting.
         */
        int exportIcon             = com.kodemore.stub.R.drawable.export_light;

        /**
         * A small red 'X' icon typically used in the ternary checkbox view.
         */
        int falseIcon              = com.kodemore.stub.R.drawable.false_red;

        /**
         * A small star icon used to navigate to Favorites page.
         */
        int favoritesIcon          = com.kodemore.stub.R.drawable.favorite_light;

        /**
         * A small envelope with an arrow icon used to indicate mailing information.
         */
        int feedbackIcon           = com.kodemore.stub.R.drawable.send_light;

        /**
         * A small green arrow icon used to indicate finishing the activity.
         */
        int finishIcon             = com.kodemore.stub.R.drawable.forward_green;

        /**
         * A small flashlight icon used to indicate the flashlight turned on.
         */
        int flashlightOnIcon       = com.kodemore.stub.R.drawable.flashlight_on_light;

        /**
         * A small flashlight icon used to indicate the flashlight turned off.
         */
        int flashlightOffIcon      = com.kodemore.stub.R.drawable.flashlight_off_light;

        /**
         * A small green arrow icon used to indicate finishing the activity.
         */
        int forwardIcon            = com.kodemore.stub.R.drawable.forward_green;

        /**
         * A small question mark icon used to navigate to the Help page;
         */
        int helpIcon               = com.kodemore.stub.R.drawable.help_light;

        /**
         * A small clock icon used to indicate history;
         */
        int historyIcon            = com.kodemore.stub.R.drawable.history_light;

        /**
         * A small home icon used to navigate to the Home / Main Activity page;
         */
        int homeIcon               = com.kodemore.stub.R.drawable.home_orange;

        /**
         * A small 'i' icon used to navigate to the an information or details page;
         */
        int infoIcon               = com.kodemore.stub.R.drawable.info_light;

        /**
         * A small home icon used to navigate to the Business Manager page;
         */
        int managerIcon            = com.kodemore.stub.R.drawable.home_light;

        /**
         * A small icon with a triangle pointed down used to indicate a popup menu;
         */
        int menuIcon               = com.kodemore.stub.R.drawable.assign_rep_light;

        /**
         * A small icon of a orange circle with a line through it, typically used in the ternary 
         * checkbox view.
         */
        int nullIcon               = com.kodemore.stub.R.drawable.null_orange;

        /**
         * A small icon of gears used to indicate adding options;
         */
        int optionsIcon            = com.kodemore.stub.R.drawable.options_light;

        /**
         * A small box icon used to navigate to the Ordered page;
         */
        int orderedIcon            = com.kodemore.stub.R.drawable.check_light;

        /**
         * A small checklist icon used to navigate to the Preferences page;
         */
        int preferencesIcon        = com.kodemore.stub.R.drawable.to_do_light;

        /**
         * A small triangle icon used to indicate going to the previous item;
         */
        int previousIcon           = com.kodemore.stub.R.drawable.previous_light;

        /**
         * A small icon of two arrows pointed out used to add a rating.
         */
        int rateIcon               = com.kodemore.stub.R.drawable.share_light;

        /**
         * A small clock icon used to indicate a recent activity;
         */
        int recentIcon             = com.kodemore.stub.R.drawable.history_light;

        /**
         * A small 'i' icon used to navigate to the a results page;
         */
        int resultsIcon            = com.kodemore.stub.R.drawable.info_light;

        /**
         * A small checklist icon used to navigate to a review page;
         */
        int reviewIcon             = com.kodemore.stub.R.drawable.review_light;

        /**
         * A small play icon used to signify running of an activity;
         */
        int runIcon                = com.kodemore.stub.R.drawable.run_light;

        /**
         * A small disk icon used to navigate to the import/export tools page.
         */
        int saveIcon               = com.kodemore.stub.R.drawable.save_light;

        /**
         * A small envelope icon used to indicate sending information.
         */
        int sendIcon               = com.kodemore.stub.R.drawable.send_light;

        /**
         * A small magnifying glass icon used to navigate to a search page.
         */
        int searchIcon             = com.kodemore.stub.R.drawable.search_light;

        /**
         * A small tools icon used to navigate to a setup page.
         */
        int setupIcon              = com.kodemore.stub.R.drawable.setup_light;

        /**
         * A small icon of two arrows pointed out used to indicate sharing items.
         */
        int shareIcon              = com.kodemore.stub.R.drawable.share_light;

        /**
         * A small 'X' icon used to skip an action.
         */
        int skipIcon               = com.kodemore.stub.R.drawable.skip_light;

        /**
         * A small circular arrow icon used to signify syncing.
         */
        int syncIcon               = com.kodemore.stub.R.drawable.update_light;

        /**
         * A small icon of gears used to navigate to the Tests page.
         */
        int testsIcon              = com.kodemore.stub.R.drawable.tests_light;

        /**
         * A small checklist icon used to navigate to the To Do page.
         */
        int todoIcon               = com.kodemore.stub.R.drawable.to_do_light;

        /**
         * A small tools icon used to navigate to the TestTools page.
         */
        int toolsIcon              = com.kodemore.stub.R.drawable.setup_light;

        /**
         * A small trashcan icon used to signify deletion.
         */
        int trashIcon              = com.kodemore.stub.R.drawable.delete_light;

        /**
         * A small green checkmark icon typically used in the ternary checkbox view.
         */
        int trueIcon               = com.kodemore.stub.R.drawable.true_green;

        /**
         * A small grey up arrow used to indicate moving an item up in the sequence.
         */
        int upArrowIcon            = com.kodemore.stub.R.drawable.up_arrow_grey;

        /**
         * A small green up arrow used to indicate moving an item up in the sequence.
         */
        int upArrowGreenIcon       = com.kodemore.stub.R.drawable.up_green_light;
        /**
         * A small green up arrow used to indicate moving an item up in the sequence.
         */
        int upTopArrowGreenIcon    = com.kodemore.stub.R.drawable.to_top;

        /**
         * A small paper with an arrow icon used to indicate uploading or upgrading.
         */
        int upgradeIcon            = com.kodemore.stub.R.drawable.update_light;

        /**
         * A small paper with an arrow icon used to indicate uploading or upgrading.
         */
        int uploadIcon             = com.kodemore.stub.R.drawable.update_light;

        /**
         * A small person holding a flag icon used to navigate to the User Info page.
         */
        int userInfoIcon           = com.kodemore.stub.R.drawable.user_info_light;

        /**
         * A small eye icon used to indicate viewing information.
         */
        int viewIcon               = com.kodemore.stub.R.drawable.view_light;

        /**
         * A small alert icon used to indicate a warning.
         */
        int warningIcon            = com.kodemore.stub.R.drawable.warning_light;

        //##################################################
        //# banner images
        //##################################################//

        /**
         * A larger image of a gold bubble banner.  This image is
         * not suitable for use on a button.
         */
        int titleWallBanner        = com.kodemore.stub.R.drawable.title_wall;

        //##################################################
        //# logos
        //##################################################//

        /**
         * A larger image of the company logo.  This image is 
         * not suitable for use on a button.
         */
        int companyLogo            = com.kodemore.stub.R.drawable.accucode_glow;

        //##################################################
        //# background wallpaper
        //##################################################//

        /**
         * A larger image of the main background.  This image is 
         * not suitable for use on a button.
         */
        int mainWallpaper          = com.kodemore.stub.R.drawable.main_wallpaper;
    }
}
