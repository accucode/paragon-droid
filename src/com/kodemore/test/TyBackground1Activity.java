package com.kodemore.test;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.kodemore.utility.KmConstantsIF;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmImageView;
import com.kodemore.view.KmResourceBanner;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextView;
import com.kodemore.view.RR;

public class TyBackground1Activity
    extends KmActivity
{
    //##################################################
    //# config
    //##################################################

    @Override
    protected View createTitleView()
    {
        KmResourceBanner banner = new KmResourceBanner(getContext());
        banner.setResId(RR.drawable.titleWallBanner);

        KmRowLayout row;
        row = ui().newRow();
        row.setBackgroundDrawable(banner.getDrawable());

        if ( showsTitleHomeButton() )
        {
            row.setItemHeightAsPercentageOfScreen(KmConstantsIF.TITLE_BAR_HEIGHT);
            row.addImageButtonScaled(
                RR.drawable.saveIcon,
                newHomePressedAction(),
                KmConstantsIF.TITLE_TEXT_HEIGHT);

            row.setPadding(3);
        }

        row.setItemWeightFull();
        row.addTitleLabel(getTitle());
        return row;
    }

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        //none
    }

    //##################################################
    //# layout
    //##################################################

    @Override
    protected View createLayout()
    {
        Drawable image;
        image = getResources().getDrawable(RR.drawable.mainWallpaper);

        KmImageView backgroundImage;
        backgroundImage = ui().newImageView();
        backgroundImage.setImageDrawable(image);
        backgroundImage.setScaleType(ScaleType.CENTER_CROP);

        KmColumnLayout root;
        root = ui().newColumn();
        root.addSpace();
        root.addView(whiteText("Here is a Title"));
        root.addSpace();
        root.addView(whiteText("Here is some text"));
        root.addSpace();
        root.addView(whiteText("Here is some more text"));
        root.addSpace();
        root.addButton("Toast", newToastAction("Toast!"));
        root.addFiller();

        KmRowLayout row;
        row = root.addEvenRow();
        row.addButton("Bottom 1", newToastAction("1"));
        row.addButton("Bottom 2", newToastAction("2"));
        row.addButton("Bottom 3", newToastAction("3"));

        RelativeLayout background;
        background = new RelativeLayout(getContext());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);

        background.addView(backgroundImage, params);
        background.addView(root, params);

        return background;
    }

    //##################################################
    //# utility
    //##################################################

    private View whiteText(String s)
    {
        KmTextView e = ui().newLabel(s);
        e.setTextColor(Color.WHITE);
        return e;
    }
}
