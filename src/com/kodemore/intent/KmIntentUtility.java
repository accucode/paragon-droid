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

package com.kodemore.intent;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.webkit.MimeTypeMap;

import com.kodemore.utility.KmPackageManager;
import com.kodemore.utility.Kmu;

/**
 * I provice convience methods for starting activities.
 */
public abstract class KmIntentUtility
{
    //##################################################
    //# misc
    //##################################################

    public static void addAndroidUserAccount(Context context)
    {
        context.startActivity(new Intent(Settings.ACTION_ADD_ACCOUNT));
    }

    //##################################################
    //# market
    //##################################################

    public static void viewMarketDetails(Context context, String app)
    {
        String uri = "market://details?id=" + app;
        viewUri(context, uri);
    }

    public static void searchMarketForPackage(Context context, String pkg)
    {
        String uri = "market://search?q=pname:" + pkg;
        viewUri(context, uri);
    }

    //##################################################
    //# app search
    //##################################################

    public static void searchForApp(Context context, String app)
    {
        try
        {
            searchMarketForApp(context, app);
        }
        catch ( ActivityNotFoundException ex1 )
        {
            try
            {
                searchInternetForApp(context, app);
            }
            catch ( ActivityNotFoundException ex2 )
            {
                Kmu.alert(context, "No browser installed, cannot search for app.");
            }
        }
    }

    private static void searchMarketForApp(Context context, String app)
    {
        String uri = "market://" + formatAppSearch(app);
        viewUri(context, uri);
    }

    private static void searchInternetForApp(Context context, String app)
    {
        String uri = "http://play.google.com/store/" + formatAppSearch(app);
        viewUri(context, uri);
    }

    //##################################################
    //# open file
    //##################################################

    public static void openTextFile(Context context, String path)
    {
        File folder = Environment.getExternalStorageDirectory();
        File file = new File(folder, path);
        Uri uri = Uri.fromFile(file);

        viewUriForExtension(context, uri, "txt");
    }

    public static void openUrl(Context context, String url)
    {
        String urlLower = url.toLowerCase();
        if ( !urlLower.startsWith("http") )
            url = "http://" + url;

        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(i);
    }

    public static void openYouTube(Context context, String id)
    {
        Uri uri = Uri.parse("vnd.youtube:" + id);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);

        if ( !supports(context, i) )
        {
            alert(context, "YouTube Not Available");
            return;
        }

        context.startActivity(i);
    }

    //##################################################
    //# send
    //##################################################

    public static void sendText(Context context, String text)
    {
        String mimeType = getMimeTypeForExtension("txt");

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        context.startActivity(intent);
    }

    //##################################################
    //# view uri
    //##################################################

    public static void viewUri(Context context, String uri)
    {
        viewUri(context, Uri.parse(uri));
    }

    public static void viewUri(Context context, Uri uri)
    {
        viewUriForMimeType(context, uri, null);
    }

    public static void viewUriForExtension(Context context, String uri, String ext)
    {
        viewUriForExtension(context, Uri.parse(uri), ext);
    }

    public static void viewUriForExtension(Context context, Uri uri, String ext)
    {
        String mime = getMimeTypeForExtension(ext);
        viewUriForMimeType(context, uri, mime);
    }

    public static void viewUriForMimeType(Context context, String uri, String type)
    {
        viewUriForMimeType(context, Uri.parse(uri), type);
    }

    public static void viewUriForMimeType(Context context, Uri uri, String type)
    {
        Intent intent;
        intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        if ( type == null )
            intent.setData(uri);
        else
            intent.setDataAndType(uri, type);

        context.startActivity(intent);
    }

    //##################################################
    //# support
    //##################################################

    private static String formatAppSearch(String app)
    {
        return "search?q=" + app + "&c=apps";
    }

    private static void alert(Context context, String msg)
    {
        Kmu.alert(context, msg);
    }

    private static boolean supports(Context context, Intent i)
    {
        return new KmPackageManager().supports(i);
    }

    private static String getMimeTypeForExtension(String ext)
    {
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getMimeTypeFromExtension(ext);
    }
}
