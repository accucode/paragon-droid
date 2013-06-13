package com.kodemore.test;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView.ScaleType;

import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmImageView;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextView;
import com.kodemore.view.KmWallpaperFrame;
import com.kodemore.view.RR;

public class TyBackground2Activity
    extends KmActivity
{
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

        KmWallpaperFrame bg;
        bg = ui().newBackgroundImageLayout();
        bg.setBackgroundImage(image);
        bg.setContentView(root);

        return bg;
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
