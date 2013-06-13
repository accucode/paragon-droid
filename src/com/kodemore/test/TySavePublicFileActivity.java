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

import com.kodemore.file.KmFilePath;
import com.kodemore.file.KmSharedFilePath;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;

/**
 * Test file access.
 */
public class TySavePublicFileActivity
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
        root.addButton("Read", newReadAction());
        root.addButton("Write", newWriteAction());
        root.addButton("Delete", newDeleteAction());
        root.addButton("Exists", newExistsAction());
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newReadAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleRead();
            }
        };
    }

    private KmAction newWriteAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleWrite();
            }
        };
    }

    private KmAction newDeleteAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleDelete();
            }
        };
    }

    private KmAction newExistsAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleExists();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    private void handleRead()
    {
        String s = getFile().readString();
        alert(s);
    }

    private void handleWrite()
    {
        getFile().writeString("something someplace");
    }

    private void handleDelete()
    {
        getFile().delete();
    }

    private void handleExists()
    {
        alert(getFile().exists() + "");
    }

    //##################################################
    //# utility
    //##################################################

    private KmFilePath getFile()
    {
        return new KmSharedFilePath("publicFile.txt");
    }

}
