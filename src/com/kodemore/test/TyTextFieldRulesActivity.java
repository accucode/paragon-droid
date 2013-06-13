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

import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;

import com.kodemore.utility.Kmu;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmTextField;

/**
 * Examples of custom rules for text fields.
 */
public class TyTextFieldRulesActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField _upperCaseField;
    private KmTextField _ignoreVowelsField;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        int type = 0
            | InputType.TYPE_CLASS_TEXT
            | InputType.TYPE_TEXT_VARIATION_NORMAL
            | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
            | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;

        _upperCaseField = new KmTextField(ui());
        _upperCaseField.setInputType(type);

        _ignoreVowelsField = new KmTextField(ui());
        _ignoreVowelsField.addFilter(newIgnoreVowelsFilter());
    }

    private InputFilter newIgnoreVowelsFilter()
    {
        return new InputFilter()
        {
            @Override
            public CharSequence filter(
                CharSequence in,
                int inStart,
                int inEnd,
                Spanned out,
                int outStart,
                int outEnd)
            {
                SpannableStringBuilder b;
                b = new SpannableStringBuilder(in, inStart, inEnd);

                for ( int i = 0; i < b.length(); i++ )
                {
                    char c = b.charAt(i);

                    // Duplicate digits 0..9 when entered.
                    if ( Kmu.isDigit(c) )
                    {
                        b.insert(i, b.subSequence(i, i + 1));
                        i++;
                    }

                    // Convert vowels to stars.
                    if ( Kmu.isVowel(c) )
                        b.replace(i, i + 1, "*");

                    // Ignore the letter z completely.
                    if ( c == 'z' || c == 'Z' )
                        b.delete(i, i + 1);
                }

                return b;
            }
        };
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        KmColumnLayout root;
        root = ui().newColumn();

        installUpperCaseField(root);
        installIgnoreVowelsField(root);

        return root;
    }

    //##################################################
    //# utility
    //##################################################

    private void installUpperCaseField(KmColumnLayout root)
    {
        String msg = ""
            + "For simple rules, the field's inputType may be sufficient. "
            + "Here we force all characters to uppercase, and disable "
            + "the predictive dictionary suggestions.";

        root.addText(msg);
        root.addView(_upperCaseField);
        root.addSpace();
    }

    private void installIgnoreVowelsField(KmColumnLayout root)
    {
        String msg = ""
            + "For more complex rules we can install a filter. "
            + "Here we duplicate any digits (0..9), convert vowels "
            + "to stars, and ignore the letter z completely.";

        root.addText(msg);
        root.addView(_ignoreVowelsField);
        root.addSpace();
    }

}
