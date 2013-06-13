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

import android.view.KeyEvent;
import android.view.View;

import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmUiManager;
import com.kodemore.view.RR;

/**
 * Any view as the menu.
 */
public class TyCustomMenuActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmRowLayout _row;

    //##################################################
    //# config
    //##################################################

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent ev)
    {
        if ( keyCode == KeyEvent.KEYCODE_MENU )
        {
            _row.getHelper().toggleGone();

            return true;
        }

        return super.onKeyDown(keyCode, ev);
    }

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        KmUiManager ui = ui();
        _row = ui.newRow();
        _row.setItemWeightFull();
        _row.addImageButtonScaled(RR.drawable.helpIcon, newToastAction("1"), 10);
        _row.addImageButtonScaled(RR.drawable.preferencesIcon, newToastAction("2"), 10);
        _row.addImageButtonScaled(RR.drawable.editIcon, newToastAction("3"), 10);
        _row.addImageButtonScaled(RR.drawable.toolsIcon, newToastAction("4"), 10);
        _row.getHelper().gone();

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
        root.addView(_row);
        return root;
    }
}
