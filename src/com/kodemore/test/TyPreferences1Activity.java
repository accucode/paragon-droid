/*
  Copyright (c) 2005-2009 www.kodimport android.view.View;

import com.kodemore.utility.KmAction;
import com.kodemore.utility.KmActivity;
import com.kodemore.utility.KmColumnLayout;
import com.kodemore.utility.KmIntegerField;
import com.kodemore.utility.KmRowLayout;
import com.kodemore.utility.KmTextField;
l
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

import com.kodemore.test.preferences.TyComplexPreferences;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmIntegerField;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextField;

/**
 * Create a custom screen to edit preferences manually.
 */
public class TyPreferences1Activity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmIntegerField _sizeField;
    private KmTextField    _colorField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _colorField = ui().newTextField();
        _colorField.setAutoSave();

        _sizeField = ui().newIntegerField();
        _sizeField.setAutoSave();
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        KmRowLayout row;
        row = root.addRow();
        row.addButton("Read", newReadAction());
        row.addButton("Write", newWriteAction());

        root.addText("Color");
        root.addView(_colorField);

        root.addText("Size");
        root.addView(_sizeField);
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

    //##################################################
    //# handle
    //##################################################

    private void handleRead()
    {
        TyComplexPreferences pp = getPreferences();

        _sizeField.setValue(pp.getSize());
        _colorField.setValue(pp.getColor());

        toast("read");
    }

    private void handleWrite()
    {
        TyComplexPreferences pp = getPreferences();

        pp.setSize(_sizeField.getValue());
        pp.setColor(_colorField.getValue());
        pp.commit();

        toast("write");
    }

    //##################################################
    //# utility
    //##################################################

    private TyComplexPreferences getPreferences()
    {
        return new TyComplexPreferences(getContext());
    }
}
