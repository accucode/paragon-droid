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

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmOneLineView;

/**
 * Pick an animal.  Used to test passing parameters and
 * return values between activities.
 */
public class TyAnimalPickerActivity
    extends KmActivity
{
    //##################################################
    //# constants
    //##################################################

    private static final Class<?> CLASS        = TyAnimalPickerActivity.class;

    private static final String   KEY_REQUEST  = CLASS + ".request";
    private static final String   KEY_RESPONSE = CLASS + ".response";

    //##################################################
    //# request (static)
    //##################################################

    public static Intent createRequest(Context context, TyAnimalFilter f)
    {
        Intent i;
        i = new Intent();
        i.setClass(context, CLASS);
        i.putExtra(KEY_REQUEST, f);
        return i;
    }

    private TyAnimalFilter getRequest()
    {
        return (TyAnimalFilter)getIntent().getSerializableExtra(KEY_REQUEST);
    }

    //##################################################
    //# response (static)
    //##################################################

    private static Intent createResponse(TyAnimal e)
    {
        return Kmu.createIntent(KEY_RESPONSE, e);
    }

    public static TyAnimal getResponse(Intent i)
    {
        return (TyAnimal)i.getSerializableExtra(KEY_RESPONSE);
    }

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
        KmListView<TyAnimal> list;
        list = newList();
        list.setItems(getAnimals());
        list.setFastScrollEnabled();
        return list;
    }

    //##################################################
    //# list
    //##################################################

    private KmListView<TyAnimal> newList()
    {
        return new KmListView<TyAnimal>(ui())
        {
            @Override
            public View getView(TyAnimal item, int index, View oldView)
            {
                KmOneLineView view;
                view = KmOneLineView.createOrCast(ui(), oldView);
                view.setText(item.getDisplayString());
                return view;
            }

            @Override
            public void onItemClick(TyAnimal e)
            {
                handleSelect(e);
            }

            @Override
            protected CharSequence getSectionNameFor(TyAnimal e)
            {
                return e.getDisplayString();
            }
        };
    }

    private KmList<TyAnimal> getAnimals()
    {
        TyAnimalTools x = TyAnimal.tools;

        KmList<TyAnimal> v;
        v = x.getRandomAnimals();
        v.sortOn(x.getDisplayStringComparator());
        v.retain(getRequest());
        return v;
    }

    //##################################################
    //# handle
    //##################################################

    private void handleSelect(TyAnimal item)
    {
        Intent intent = createResponse(item);
        finishOk(intent);
    }
}
