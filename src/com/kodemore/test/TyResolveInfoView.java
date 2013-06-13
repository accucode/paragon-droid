package com.kodemore.test;

import android.view.Gravity;
import android.widget.ImageView.ScaleType;

import com.kodemore.utility.KmResolveInfo;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmImageView;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextView;
import com.kodemore.view.KmUiManager;

public class TyResolveInfoView
    extends KmRowLayout
{
    //##################################################
    //# static 
    //##################################################

    public static TyResolveInfoView createOrCast(KmUiManager ui, Object e)
    {
        if ( e instanceof TyResolveInfoView )
            return (TyResolveInfoView)e;

        return new TyResolveInfoView(ui);
    }

    //##################################################
    //# variables
    //##################################################

    private KmImageView _iconView;
    private KmTextView  _labelView;
    private KmTextView  _activityPackageView;
    private KmTextView  _activityNameView;

    //##################################################
    //# constructor
    //##################################################

    public TyResolveInfoView(KmUiManager ui)
    {
        super(ui);

        setVerticalGravity(Gravity.CENTER);

        _iconView = ui.newImageView();
        _iconView.setAdjustViewBounds(true);
        _iconView.setMaxHeight(60);
        _iconView.setMaxWidth(60);
        _iconView.setScaleType(ScaleType.CENTER_INSIDE);

        _labelView = ui.newTextView();
        _labelView.getHelper().setFontSize(18);
        _labelView.getHelper().setPadding(4);

        _activityPackageView = ui().newTextView();
        _activityPackageView.getHelper().setFontSize(12);
        _activityPackageView.getHelper().setPadding(4);

        _activityNameView = ui.newTextView();
        _activityNameView.getHelper().setFontSize(12);
        _activityNameView.getHelper().setPadding(4);

        setItemWeightNone();
        addView(_iconView);

        setItemWeightFull();

        KmColumnLayout col;
        col = addColumn();
        col.addView(_labelView);
        col.addView(_activityPackageView);
        col.addView(_activityNameView);
    }

    //##################################################
    //# accessing
    //##################################################

    public void apply(KmResolveInfo e)
    {
        _iconView.setImageDrawable(e.getApplicationIcon());
        _labelView.setText(e.getApplicationLabel());
        _activityPackageView.setText(e.getActivityPackage());
        _activityNameView.setText(e.getActivityName());
    }
}
