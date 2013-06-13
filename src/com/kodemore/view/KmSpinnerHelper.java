package com.kodemore.view;

import android.widget.Spinner;

public class KmSpinnerHelper
    extends KmGroupHelper
{
    //##################################################
    //# constructor
    //##################################################

    public KmSpinnerHelper(Spinner e, KmUiManager ui)
    {
        super(e, ui);
    }

    @Override
    protected Spinner getView()
    {
        return (Spinner)super.getView();
    }

}
