/*
  Copyright (c) 2005-2009 www.kodimport android.view.View;

import com.kodemore.utility.KmActivity;
import com.kodemore.utility.KmColumnLayout;
import com.kodemore.utility.KmRowLayout;
import com.kodemore.utility.KmTextField;
t limitation the rights
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

import com.kodemore.utility.KmRunnable;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Test file this activity shows how to use the KmDownloadFromUrlTask to download a
 * file to the sd card.
 */

/**
 * review_lucas (steve) here is the test activity which downloads a file
 */

public class TyDownloadFileActivity
    extends KmActivity
{

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
        root.addFiller();
        root.addButton("Download", newDownloadAction());
        return root;
    }

    //##################################################
    //# lifecycle
    //##################################################

    /**
     * Used instead of onResumeAsync() due to no User Interface interaction.
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        getTask().onResume(getContext());
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        getTask().onPause();
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newDownloadAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                downloadFromUrlAsyncSafe();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleRun()
    {
        /**
         * note: these urls are hardcoded and may or may not be valid anymore...
         */
        String fileName = "cat.jpg";
        String urlString = "http://www.funsubstance.com/uploads/preview/104/104353.jpg";
        //        String fileName = "serenity.avi";
        //        String urlString = "https://www.dropbox.com/s/4h726ocb8udz7qx/01_Serenity.avi?dl=1";

        getTask().setFileName(fileName);
        getTask().setUrlString(urlString);
        getTask().setMessage("Downloading " + fileName);
        getTask().run();
    }

    //##################################################
    //# async
    //##################################################

    public void downloadFromUrlAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                handleRun();
            }
        }.runOnUiThread(ui());
    }

    //##################################################
    //# task
    //##################################################

    private KmDownloadFromUrlTask getTask()
    {
        return KmDownloadFromUrlTask.instance;
    }
}
