package com.kodemore.test;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;

import com.kodemore.alert.KmDialogBuilder;
import com.kodemore.collection.KmList;
import com.kodemore.utility.KmLog;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmDialogManager;
import com.kodemore.view.KmFrameLayout;
import com.kodemore.view.KmListDialogManager;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmOneLineView;
import com.kodemore.view.KmRowLayout;
import com.kodemore.view.KmTextField;

public class TyDialogSizePositionActivity
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

        KmRowLayout row;
        row = root.addRow();
        row.setItemWeightFull();

        row.addButton("T1", newT1DialogManager());
        row.addButton("T2", newT2DialogManager());
        row.addButton("T3", newT3ListDialogManager());

        row = root.addRow();
        row.setItemWeightFull();

        row.addButton("T4", newT4ListDialogManager());
        row.addButton("T5", newT5ListDialogManager());
        row.addButton("T6", newT6ListDialogManager());

        row = root.addRow();
        row.setItemWeightFull();

        row.addButton("T7", newT7ListDialogManager());
        row.addButton("T8", newT8ListDialogManager());
        row.addButton("T9", newT9ListDialogManager());

        row = root.addRow();
        row.setItemWeightFull();

        row.addButton("T10", newT10ListDialogManager());
        row.addButton("T11", newT11ListDialogManager());

        return root.inScrollView();
    }

    //##################################################
    //# dialog
    //##################################################

    private KmDialogManager newT1DialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return newT1Dialog();
            }
        };
    }

    private KmDialogManager newT2DialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return newT2Dialog();
            }
        };
    }

    private KmDialogManager newT3ListDialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return newT3ListDialog();
            }
        };
    }

    private KmDialogManager newT4ListDialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return newT4ListDialog();
            }
        };
    }

    private KmDialogManager newT5ListDialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return newT5ListDialog();
            }
        };
    }

    private KmDialogManager newT6ListDialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return newT6ListDialog();
            }
        };
    }

    private KmDialogManager newT7ListDialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return newT7ListDialog();
            }
        };
    }

    private KmDialogManager newT8ListDialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return newT8ListDialog();
            }
        };
    }

    private KmDialogManager newT9ListDialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return newT9ListDialog();
            }
        };
    }

    private KmDialogManager newT10ListDialogManager()
    {
        return new KmDialogManager()
        {
            @Override
            public Dialog create()
            {
                return newT10ListDialog();
            }
        };
    }

    private KmDialogManager newT11ListDialogManager()
    {
        return newT11ListDialog();
    }

    /**
     * Test: T1
     * Simple dialog box
     * Works with no issues
     */
    protected Dialog newT1Dialog()
    {
        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("T1:Confirm Dialog box");
        e.setMessage("Text should display in full");
        e.setPositiveButton("Ok");
        e.setNegativeButton("Cancel");

        return e.create();
    }

    /**
     * Test: T2
     * Simple dialog box with text entry
     * Works with no issues
     */
    protected Dialog newT2Dialog()
    {
        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("T2:Enter Dialog box");
        e.setMessage("Should be able to enter text in field.");
        e.setView(new KmTextField(ui()));
        e.setPositiveButton("Ok");
        e.setNegativeButton("Cancel");
        return e.create();
    }

    /**
     * Test: T3
     * Simple dialog with KmListView
     * A long list hides the dialog message
     * On small screens the bottom edge goes offscreen
     */
    protected Dialog newT3ListDialog()
    {
        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("T3:List display in Dialog");
        e.setMessage("This message should be visible.");
        e.setView(createList());
        e.setPositiveButton("Ok");
        e.setNegativeButton("Cancel");
        return e.create();
    }

    /**
     * Test: T4
     * Dialog box with a row above and below the
     * KmListView entry along with a empty column
     * on either side.  On short screens it pushes the 
     * dialog box off the bottom and forces the scroll to
     * go past the bottom of the screen.  
     * Uncomment the
     * lines that set the background color to see the
     * upper and lower limits of the displayable area.
     */
    protected Dialog newT4ListDialog()
    {
        KmColumnLayout root;
        root = ui().newColumn();
        //        root.addRow().setBackgroundColor(Color.RED);

        // row left intentionally empty. 
        root.setItemHeight(10);
        root.addRow();

        root.setItemHeight(550);

        KmRowLayout row;
        row = root.addRow();
        row.setItemWidth(20);
        row.addColumn();
        row.setItemWidth(800);
        row.addView(createList());
        row.setItemWidth(5);
        row.addColumn();

        //        root.addRow().setBackgroundColor(Color.BLUE);
        root.setItemHeight(10);
        root.addRow();

        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("T4:List constrained by surrounding items");
        e.setMessage("This message should be visible.");
        e.setView(root);
        e.setPositiveButton("Ok");
        e.setNegativeButton("Cancel");
        return e.create();
    }

    /**
     * Test: T5
     * Dialog box with an added KmListView that gets its settings
     * for item height and weight based on the screen size.  
     * Does not properly size horizontally
     * leaving a gap between the item and the right side of the dialog
     * box.  
     */
    private AlertDialog newT5ListDialog()
    {
        KmColumnLayout root = ui().newColumn();
        double screenWidth = getScreenWidth();
        double screenHeight = getScreenHeight();

        int h = (int)(screenHeight * .3);
        root.setItemHeight(h);
        KmRowLayout row;
        row = root.addRow();

        int w = (int)(screenWidth * .6);
        row.setItemWidth(w);
        row.addView(createList());

        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("T5:Should be centered on screen.  Buttons should show properly");
        e.setMessage("Looking for proper spacing all around");
        e.setView(root);
        e.setPositiveButton("Ok");
        e.setNegativeButton("Cancel");
        return e.create();
    }

    /**
     * Test: T6
     * Dialog box with KmListView.  Uses screen dimensions to 
     * determine height and weight.  Leaves gap on right in landscape.
     * Leaves a gap on the right, items scroll behind the 
     * edge of the buttons and fast scroll unusable in portrait.
     */
    private Dialog newT6ListDialog()
    {
        double sw = getScreenWidth();
        double sh = getScreenHeight();
        double percentOfScreen = 1;
        double min = Math.min(sw, sh);
        double dw = min * percentOfScreen;
        double percentVisible = .3;
        double area = sh * sw * percentVisible;
        double dh = area / dw;

        KmColumnLayout root;
        root = ui().newColumn();
        root.setItemHeight((int)dh);

        KmRowLayout row;
        row = root.addRow();
        row.setItemWidth((int)dw);
        row.addView(createList());

        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("T6:Dynamically rendered based on size of screen");
        e.setMessage("Looking for proper spacing all around");
        e.setView(root);
        e.setPositiveButton("Ok");
        e.setNegativeButton("Cancel");
        return e.create();
    }

    /**
     * Test: T7
     * Dialog box with KmColumnLayout.  Uses different
     * percentages to determine size of list in dialog.
     * Slight gap between fast scroll control
     * and edge of dialog box in landscape.
     */
    private Dialog newT7ListDialog()
    {
        double sW = getScreenWidth();
        double visibleWidthPercentage = .95;
        double dW = sW * visibleWidthPercentage;

        double sH = getScreenHeight();
        double visibleHeightPercentage = .35;
        double dH = sH * visibleHeightPercentage;

        KmLog.info("***************** WIDTH is: %s and HEIGHT is: %s", sW, sH);
        KmLog.info("***************** wD is:    %s and hD is:     %s", dW, dH);

        KmColumnLayout root;
        root = ui().newColumn();
        root.setItemHeight((int)dH);

        KmRowLayout row;
        row = root.addRow();
        row.setItemWidth((int)dW);
        row.addView(createList());

        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("T7:Dynamically rendered");
        e.setMessage("Looking for proper spacing");
        e.setView(root);
        e.setPositiveButton("Ok");
        e.setNegativeButton("Cancel");
        return e.create();
    }

    /** 
     * Test: T8
     * Dialog box with list inside of frame.
     * Using the frame gives us control over
     * sizing when we add the list.
     * Slight gap between fast scroll control
     * and edge of dialog box in landscape.
     */
    private Dialog newT8ListDialog()
    {
        double sW = getScreenWidth();
        double visibleWidthPercentage = .95;
        double dW = sW * visibleWidthPercentage;

        double sH = getScreenHeight();
        double visibleHeightPercentage = .35;
        double dH = sH * visibleHeightPercentage;

        KmLog.info("***************** WIDTH is: %s and HEIGHT is: %s", sW, sH);
        KmLog.info("***************** wD is: %s and hD is: %s", dW, dH);

        KmFrameLayout frame;
        frame = ui().newFrameLayout();
        frame.addView(createList(), (int)dW, (int)dH);

        KmColumnLayout root;
        root = ui().newColumn();

        KmRowLayout row;
        row = root.addRow();
        row.addView(frame);

        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("T8:Dynamically rendered frame on screensize");
        e.setMessage("Looking for proper spacing");
        e.setView(root);
        e.setPositiveButton("Ok");
        e.setNegativeButton("Cancel");
        return e.create();
    }

    /**
     * Test: T9
     * Dialog list with list in frame
     * and screen dimensions are modified based on 
     * orientation
     */
    private Dialog newT9ListDialog()
    {
        double sw = getScreenWidth();
        double visibleWidthPercentage = isPortrait()
            ? .95
            : .97;
        double dw = sw * visibleWidthPercentage;

        double sh = getScreenHeight();
        double visibleHeightPercentage = isPortrait()
            ? .6
            : .35;
        double dh = sh * visibleHeightPercentage;

        KmLog.info("* WIDTH is: %s and HEIGHT is: %s", sw, sh);
        KmLog.info("* wD is:    %s and hD is:     %s", dw, dh);
        if ( isPortrait() )
            KmLog.info("* PORTRAIT");
        else
            KmLog.info("* LANDSCAPE");

        KmFrameLayout frame;
        frame = ui().newFrameLayout();
        frame.addView(createList(), (int)dw, (int)dh);

        KmColumnLayout root;
        root = ui().newColumn();

        KmRowLayout row;
        row = root.addRow();
        row.addView(frame);

        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("T9:Dynamic on orientation");
        e.setMessage("Looking for proper spacing");
        e.setView(root);
        e.setPositiveButton("Ok");
        e.setNegativeButton("Cancel");
        return e.create();
    }

    /**
     * Test: T10
     * Dialog with list and message removed.
     * The list appears in the dialog's 
     * content area. The dialog cannot show both 
     * a message and a list and you should set a 
     * title for the dialog and add content but 
     * replace the message with content.
     */
    private Dialog newT10ListDialog()
    {
        KmDialogBuilder e;
        e = new KmDialogBuilder(ui());
        e.setTitle("T10: Removing message to allow keep list from pushing to the top");
        e.setView(createList());
        e.setPositiveButton("Ok");
        e.setNegativeButton("Cancel");
        return e.create();
    }

    /**
     * Test: T11
     * Dialog with list dialog manager
     * uses onClickAction to select item
     * and return to where it was called
     */
    private KmListDialogManager<String> newT11ListDialog()
    {
        KmListDialogManager<String> e;
        e = new KmListDialogManager<String>()
        {
            @Override
            protected List<String> getItems()
            {
                return getList();
            }

        };
        e.setTitle("T11: Using KmListDialogManager");
        e.setOnItemSelectedListener(newT11DialogAction());
        e.register(ui());
        e.addCancelButton();
        return e;
    }

    //##################################################
    //# utility
    //##################################################

    private View createList()
    {
        KmList<String> v;
        v = getList();
        if ( v.isEmpty() )
            return new KmListView<String>(ui());

        KmListView<String> view;
        view = newDetailList();
        view.setItems(v);
        view.setFastScrollEnabled();
        return view;
    }

    private KmList<String> getList()
    {
        KmList<String> v;
        v = new KmList<String>();

        KmList<TyAnimal> animals;
        animals = TyAnimal.tools.getRandomAnimals();
        for ( TyAnimal e : animals )
            v.add(e.getDisplayString());
        v.sort();
        return v;
    }

    private KmListView<String> newDetailList()
    {
        return new KmListView<String>(ui())
        {

            @Override
            protected View getView(String item, int index, View oldView)
            {
                KmOneLineView view;
                view = KmOneLineView.createOrCast(ui(), oldView);
                view.setText(item);
                return view;
            }

            @Override
            protected CharSequence getSectionNameFor(String e)
            {
                return e;
            }
        };
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newT11DialogAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                toast("selected item ");
            }
        };
    }

}
