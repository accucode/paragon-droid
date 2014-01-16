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

import com.kodemore.utility.KmProgressDialogTask;
import com.kodemore.utility.Kmu;

public class KmDownloadFromUrlTask
    extends KmProgressDialogTask
{
    /**
     *     review_lucas (steve) this is the task that is used when 
     *     downloading a file it shows the spinner 
     */
    //##################################################
    //# instance
    //##################################################

    public static final KmDownloadFromUrlTask instance = new KmDownloadFromUrlTask(null, null);

    //##################################################
    //# varialbles
    //##################################################//

    public String                             _urlString;
    public String                             _fileName;

    //##################################################
    //# constructors
    //##################################################

    private KmDownloadFromUrlTask(String urlString, String fileName)
    {
        setUrlString(urlString);
        setFileName(fileName);

        setDialogStyleSpinner();
    }

    //##################################################
    //# handle
    //##################################################

    @Override
    public void handleBackground()
    {
        publishProgress(null);

        Kmu.downloadFileFromUrl(getUrlString(), getFileName());
    }

    //##################################################
    //# access
    //##################################################//

    public String getUrlString()
    {
        return _urlString;
    }

    public void setUrlString(String urlString)
    {
        _urlString = urlString;
    }

    public String getFileName()
    {
        return _fileName;
    }

    public void setFileName(String fileName)
    {
        _fileName = fileName;
    }

}
