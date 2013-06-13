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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmRowLayout;

/**
 * Draw an a custom view with your finger. 
 */
public class TyDrawOnViewActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private View       _view;
    private LocalState _state;

    //##################################################
    //# state
    //##################################################

    @Override
    public Object onRetainNonConfigurationInstance()
    {
        return _state;
    }

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _view = newView();

        _state = hasLastNonConfigurationInstance()
            ? (LocalState)getLastNonConfigurationInstance()
            : createState();
    }

    private LocalState createState()
    {
        LocalState e;
        e = new LocalState();
        e.paths = new KmList<Path>();

        e.paint = new Paint();
        e.paint.setColor(Color.BLUE);
        e.paint.setAntiAlias(true);
        e.paint.setStyle(Paint.Style.STROKE);
        e.paint.setStrokeJoin(Paint.Join.ROUND);
        e.paint.setStrokeCap(Paint.Cap.ROUND);
        e.paint.setStrokeWidth(3);

        return e;
    }

    private View newView()
    {
        return new View(getContext())
        {
            @Override
            public void onDraw(Canvas c)
            {
                handleDraw(c);
            }

            @Override
            public boolean onTouchEvent(MotionEvent ev)
            {
                return handleTouch(ev);
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
        root.setItemWeightFull();
        root.addView(_view);

        installButtons(root);

        return root;
    }

    private void installButtons(KmColumnLayout root)
    {
        root.setItemWeightNone();

        KmRowLayout row;

        row = root.addEvenRow();
        row.addButton("Red", newRedAction());
        row.addButton("Green", newGreenAction());
        row.addButton("Blue", newBlueAction());

        row = root.addEvenRow();
        row.addButton("Clear", newClearAction());
        row.addButton("Undo", newUndoAction());
    }

    //##################################################
    //# actions
    //##################################################

    private KmAction newRedAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleRed();
            }
        };
    }

    private KmAction newGreenAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleGreen();
            }
        };
    }

    private KmAction newBlueAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleBlue();
            }
        };
    }

    private KmAction newClearAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleClear();
            }
        };
    }

    private KmAction newUndoAction()
    {
        return new KmAction()
        {
            @Override
            protected void handle()
            {
                handleUndo();
            }
        };
    }

    //##################################################
    //# handle
    //##################################################

    //==================================================
    //= handle :: buttons
    //==================================================

    private void handleRed()
    {
        _state.paint.setColor(Color.RED);
        _view.invalidate();
    }

    private void handleGreen()
    {
        _state.paint.setColor(Color.GREEN);
        _view.invalidate();
    }

    private void handleBlue()
    {
        _state.paint.setColor(Color.BLUE);
        _view.invalidate();
    }

    private void handleClear()
    {
        _state.paths.clear();
        _view.invalidate();
    }

    private void handleUndo()
    {
        _state.paths.removeLastSafe();
        _view.invalidate();
    }

    //==================================================
    //= handle :: view
    //==================================================

    private void handleDraw(Canvas c)
    {
        for ( Path e : _state.paths )
            c.drawPath(e, _state.paint);
    }

    private boolean handleTouch(MotionEvent ev)
    {
        int x = (int)ev.getX();
        int y = (int)ev.getY();

        Point p = new Point(x, y);

        int a = ev.getAction();

        if ( a == MotionEvent.ACTION_DOWN )
            handleTouchDown(p);

        if ( a == MotionEvent.ACTION_MOVE )
            handleTouchMove(p);

        if ( a == MotionEvent.ACTION_UP )
            handleTouchUp(p);

        return true;
    }

    private void handleTouchDown(Point p)
    {
        Path path;
        path = new Path();
        path.addCircle(p.x, p.y, 5, Direction.CW);

        _state.paths.add(path);
        _state.lastPoint = p;

        _view.invalidate();
    }

    private void handleTouchMove(Point p)
    {
        if ( Kmu.isEqual(_state.lastPoint, p) )
            return;

        _state.paths.getLast().lineTo(p.x, p.y);
        _state.lastPoint = p;

        _view.invalidate();
    }

    private void handleTouchUp(Point p)
    {
        Path path;
        path = _state.paths.getLast();
        path.addCircle(p.x, p.y, 5, Direction.CW);

        _view.invalidate();
    }

    //##################################################
    //# state
    //##################################################

    private class LocalState
    {
        private Paint        paint;
        private Point        lastPoint;
        private KmList<Path> paths;
    }
}
