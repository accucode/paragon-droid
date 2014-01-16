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

import java.util.Comparator;

import android.app.Activity;

import com.kodemore.collection.KmList;
import com.kodemore.comparator.KmComparator;
import com.kodemore.test.service.TyIntegerServiceActivity;

public class TyRegistry
{
    /**
     * If non-null, open this activity immediately.
     */
    public static Class<? extends Activity> getQuickTest()
    {
        return null;
    }

    /**
     * The list of all available tests.
     */
    public static KmList<Class<? extends Activity>> getTests()
    {
        KmList<Class<? extends Activity>> v;
        v = new KmList<Class<? extends Activity>>();

        v.add(TyAcraTestActivity.class);
        v.add(TyActivityNameActivity.class);
        v.add(TyAlertActivity.class);
        v.add(TyAndroidIdActivity.class);
        v.add(TyAnimalListActivity.class);
        v.add(TyAnimalPickerActivity.class);
        v.add(TyAnimalPickerClientActivity.class);
        v.add(TyAnimateListActivity.class);
        v.add(TyAnimationActivity.class);
        v.add(TyAppInfoActivity.class);
        v.add(TyAppListActivity.class);
        v.add(TyAssetActivity.class);
        v.add(TyAssetFileActivity.class);
        v.add(TyAutoCompleteActivity.class);
        v.add(TyBarCodeScan1Activity.class);
        v.add(TyBarCodeScan2Activity.class);
        v.add(TyBackActivity.class);
        v.add(TyBackground1Activity.class);
        v.add(TyBackground2Activity.class);
        v.add(TyBagelInventoryActivity.class);
        v.add(TyBluetoothScannerActivity.class);
        v.add(TyBuildAttributesActivity.class);
        v.add(TyButtonActivity.class);
        v.add(TyButtonDepthActivity.class);
        v.add(TyCalculatorActivity.class);
        v.add(TyCallLogsActivity.class);
        v.add(TyCheckList1Activity.class);
        v.add(TyCheckList2Activity.class);
        v.add(TyClockActivity.class);
        v.add(TyContactSelectActivity.class);
        v.add(TyContextMenuActivity.class);
        v.add(TyCustomButtonActivity.class);
        v.add(TyCustomMenuActivity.class);
        v.add(TyCustomToastActivity.class);
        v.add(TyDatePickActivity.class);
        v.add(TyDialogClientActivity.class);
        v.add(TyDialogEventsActivity.class);
        v.add(TyDialogManagerActivity.class);
        v.add(TyDialogSizePositionActivity.class);
        v.add(TyDialogWithMultilineTextFieldActivity.class);
        v.add(TyDisplayInfoActivity.class);
        v.add(TyDownloadFileActivity.class);
        v.add(TyDpActivity.class);
        v.add(TyDrawableActivity.class);
        v.add(TyDrawOnViewActivity.class);
        v.add(TyEmailActivity.class);
        v.add(TyFileExternalApplicationActivity.class);
        v.add(TyFileExternalSharedActivity.class);
        v.add(TyFilePrivateActivity.class);
        v.add(TyFocusActivity.class);
        v.add(TyFocusSoftKeyToggleActivity.class);
        v.add(TyFontFamilyActivity.class);
        v.add(TyFrameActivity.class);
        v.add(TyFtpActivity.class);
        v.add(TyFtpDirectoryActivity.class);
        v.add(TyGetAddressActivity.class);
        v.add(TyGoogleDocActivity.class);
        v.add(TyHelloActivity.class);
        v.add(TyHideSoftKeyboardActivity.class);
        v.add(TyHtmlActivity.class);
        v.add(TyHtmlZoomActivity.class);
        v.add(TyImageButtonActivity.class);
        v.add(TyInigmaScanActivity.class);
        v.add(TyIntegerServiceActivity.class);
        v.add(TyJsonActivity.class);
        v.add(TyLayout1Activity.class);
        v.add(TyLayout2Activity.class);
        v.add(TyLayout3Activity.class);
        v.add(TyLayout4Activity.class);
        v.add(TyLaunchGoogleStoreActivity.class);
        v.add(TyLayoutRotationActivity.class);
        v.add(TyLightActivity.class);
        v.add(TyLiquidUnitConversionActivity.class);
        v.add(TyListChangesActivity.class);
        v.add(TyListContextMenuActivity.class);
        v.add(TyListDialog1Activity.class);
        v.add(TyListDialog2Activity.class);
        v.add(TyListEnablementActivity.class);
        v.add(TyListFilterAnimalActivity.class);
        v.add(TyListFilterContactActivity.class);
        v.add(TyListRefreshActivity.class);
        v.add(TyListSectionsActivity.class);
        v.add(TyListSections2Activity.class);
        v.add(TyListStringsActivity.class);
        v.add(TyListSubclassActivity.class);
        v.add(TyLocationActivity.class);
        v.add(TyLogcatActivity.class);
        v.add(TyMeasureTextActivity.class);
        v.add(TyMenuActivity.class);
        v.add(TyMemoryActivity.class);
        v.add(TyMessageActivity.class);
        v.add(TyMoneyFormattingActivity.class);
        v.add(TyNotificationActivity.class);
        v.add(TyNullRootActivity.class);
        v.add(TyNumberPadActivity.class);
        v.add(TyOAuthActivity.class);
        v.add(TyOrientationActivity.class);
        v.add(TyPhoneStateActivity.class);
        v.add(TyPhotoActivity.class);
        v.add(TyPopupWindowActivity.class);
        v.add(TyPreferences1Activity.class);
        v.add(TyPreferencesSeparate1Activity.class);
        v.add(TyPreferencesSeparate2Activity.class);
        v.add(TyPreferencesShared1Activity.class);
        v.add(TyPreferencesShared2Activity.class);
        v.add(TyProgressDialogActivity.class);
        v.add(TyQrDroidScanActivity.class);
        v.add(TyQuickMarkScanActivity.class);
        v.add(TyQuantityFieldActivity.class);
        v.add(TyRadarActivity.class);
        v.add(TyResolveInfoActivity.class);
        v.add(TyRestartActivity.class);
        v.add(TyRestfulMailActivity.class);
        v.add(TyRequiredTextFieldActivity.class);
        v.add(TyRunningAppsActivity.class);
        v.add(TySavePublicFileActivity.class);
        v.add(TyScaledImageButtonActivity.class);
        v.add(TyScreenOrientationActivity.class);
        v.add(TySearchButtonHardwareActivity.class);
        v.add(TySendAddressActivity.class);
        v.add(TySerializeSpeedActivity.class);
        v.add(TySlideActivity.class);
        v.add(TySlideMenuActivity.class);
        v.add(TySmtpMailActivity.class);
        v.add(TySoftKeyboardToggleActivity.class);
        v.add(TySpinnerActivity.class);
        v.add(TyStringCompareActivity.class);
        v.add(TySystemPropertiesActivity.class);
        v.add(TyTabActivity.class);
        v.add(TyTableLayout1Activity.class);
        v.add(TyTableLayout2Activity.class);
        v.add(TyTableLayout3Activity.class);
        v.add(TyTabTestActivity.class);
        v.add(TyTabTestHostedActivity.class);
        v.add(TyTernaryCheckBoxActivity.class);
        v.add(TyTestActivity.class);
        v.add(TyTextFieldActivity.class);
        v.add(TyTextFieldRulesActivity.class);
        v.add(TyTextSwitcherActivity.class);
        v.add(TyTitleBarActivity.class);
        v.add(TyToastActivity.class);
        v.add(TyTreeActivity.class);
        v.add(TyTriStateCheckBoxActivity.class);
        v.add(TyTriStateCheckBox2Activity.class);
        v.add(TyUnregisteredActivity.class);
        v.add(TyUnitParserActivity.class);
        v.add(TyUriActivity.class);
        v.add(TyUserListActivity.class);
        v.add(TyWebActivity.class);
        v.add(TyWheelIntegerActivity.class);
        v.add(TyYesNoDialogActivity.class);
        v.add(TyYouTubeActivity.class);
        return v;
    }

    /**
     * The list of tests, sorted by their simple names.
     */
    public static KmList<Class<? extends Activity>> getSortedTests()
    {
        KmList<Class<? extends Activity>> v;
        v = getTests();
        v.sortOn(newComparator());
        return v;
    }

    public static Comparator<Class<? extends Activity>> newComparator()
    {
        return new KmComparator<Class<? extends Activity>>()
        {
            @Override
            public int compare(Class<? extends Activity> a, Class<? extends Activity> b)
            {
                return c(a.getSimpleName(), b.getSimpleName());
            }
        };
    }

}
