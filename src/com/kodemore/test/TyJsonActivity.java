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

import android.graphics.Color;
import android.view.View;

import com.kodemore.collection.KmJsonList;
import com.kodemore.collection.KmJsonMap;
import com.kodemore.collection.KmUntypedTuple;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmTwoLineListView;
import com.kodemore.view.KmTwoLineView;

public class TyJsonActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    private static final String OK = "ok";

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
        KmTwoLineListView list;
        list = newList();
        list.addItem("Pass", testPass());
        list.addItem("Fail", testFail());
        list.addItem("Test test 01", test01());
        list.addItem("Test test 02", test02());
        list.addItem("Test test 03", test03());
        list.addItem("Test test 04", test04());
        return list;
    }

    private KmTwoLineListView newList()
    {
        return new KmTwoLineListView(ui())
        {
            @Override
            protected View getView(KmUntypedTuple item, int index, View oldView)
            {
                Object key = item.getKey();
                Object value = item.getValue();

                int color = OK.equals(value)
                    ? Color.GREEN
                    : Color.RED;

                KmTwoLineView view;
                view = KmTwoLineView.createOrCast(ui(), oldView);
                view.setLine1(key);
                view.setLine2(value);
                view.getTextView2().setTextColor(color);
                return view;
            }
        };
    }

    //##################################################
    //# tests
    //##################################################

    private String testPass()
    {
        return OK;
    }

    private String testFail()
    {
        return "This should fail.";
    }

    private String test01()
    {
        try
        {
            KmJsonMap out = new KmJsonMap();
            String json = out.toString();
            KmJsonMap in = KmJsonMap.parse(json);

            if ( !in.isEmpty() )
                return "Not Empty";

            return OK;
        }
        catch ( RuntimeException ex )
        {
            return ex.getMessage();
        }
    }

    private String test02()
    {
        try
        {
            KmJsonMap out;
            out = new KmJsonMap();
            out.setString("a", "animal");
            out.setInteger("b", 2);

            String json = out.toString();
            KmJsonMap in = KmJsonMap.parse(json);

            assertEquals(in.size(), 2);
            assertEquals(in.getString("a"), "animal");
            assertEquals(in.getInteger("b"), 2);

            return OK;
        }
        catch ( RuntimeException ex )
        {
            return ex.getMessage();
        }
    }

    private String test03()
    {
        try
        {
            KmJsonMap m;
            m = new KmJsonMap();
            m.setString("a", "animal");

            KmJsonList v;
            v = m.setList("v");
            v.addInteger(8);
            v.addBoolean(true);
            v.setDouble(3, 3.87);

            String json = m.toString();
            KmJsonMap in = KmJsonMap.parse(json);

            assertEquals(in.size(), 2);
            assertEquals(in.getString("a"), "animal");

            v = in.getList("v");
            assertEquals(v.size(), 4);
            assertEquals(v.getInteger(0), 8);
            assertEquals(v.getBoolean(1), true);
            assertEquals(v.isNull(2), true);
            assertEquals(v.getString(2), null);
            assertEquals(v.getDouble(3), 3.87);

            return OK;
        }
        catch ( RuntimeException ex )
        {
            return ex.getMessage();
        }
    }

    private String test04()
    {
        try
        {
            String s = "\n\r\t,{}[]\007";

            KmJsonMap m;
            m = new KmJsonMap();
            m.setString("a", s);

            String json = m.toString();
            KmJsonMap in = KmJsonMap.parse(json);

            assertEquals(in.size(), 1);
            assertEquals(in.getString("a"), s);

            return OK;
        }
        catch ( RuntimeException ex )
        {
            return ex.getMessage();
        }
    }

    private void assertEquals(Object a, Object b)
    {
        if ( Kmu.isNotEqual(a, b) )
            error("%s != %s", a, b);
    }

    private void error(String msg, Object... args)
    {
        String s = Kmu.format(msg, args);
        throw new RuntimeException(s);
    }

}
