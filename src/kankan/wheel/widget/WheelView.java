/*
 *  Android Wheel Control.
 *  https://code.google.com/p/android-wheel/
 *  
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package kankan.wheel.widget;

import java.util.LinkedList;
import java.util.List;

import kankan.wheel.widget.WheelScroller.ScrollingListener;
import kankan.wheel.widget.adapters.WheelViewAdapter;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

/**
 * Numeric wheel view.
 * 
 * @author Yuri Kanivets
 */
public class WheelView
    extends View
{
    //##################################################
    //# constants
    //##################################################

    /** Top and bottom shadows colors */
    private static final int[]           SHADOWS_COLORS      = new int[]
                                                             {
        0xFF111111,
        0x00AAAAAA,
        0x00AAAAAA
                                                             };

    /** Top and bottom items offset (to hide that) */
    private static final int             ITEM_OFFSET_PERCENT = 10;

    /** Left and right padding value */
    private static final int             PADDING             = 10;

    /** Default count of visible items */
    private static final int             DEF_VISIBLE_ITEMS   = 5;

    //##################################################
    //# variables
    //##################################################

    // Wheel Values
    private int                          _currentItem        = 0;

    // Count of visible items
    private int                          _visibleItems       = DEF_VISIBLE_ITEMS;

    // Item height
    private int                          _itemHeight         = 0;

    // Center Line
    private Drawable                     _centerDrawable;

    // Shadows drawables
    private GradientDrawable             _topShadow;
    private GradientDrawable             _bottomShadow;

    // Scrolling
    private WheelScroller                _scroller;
    private boolean                      _isScrollingPerformed;
    private int                          _scrollingOffset;

    // Cyclic
    boolean                              _isCyclic           = false;

    // Items layout
    private LinearLayout                 _itemsLayout;

    // The number of first item in layout
    private int                          _firstItem;

    // View adapter
    private WheelViewAdapter             _viewAdapter;

    // Recycle
    private WheelRecycle                 _recycle            = new WheelRecycle(this);

    // Listeners
    private List<OnWheelChangedListener> _changingListeners  = new LinkedList<OnWheelChangedListener>();
    private List<OnWheelScrollListener>  _scrollingListeners = new LinkedList<OnWheelScrollListener>();
    private List<OnWheelClickedListener> _clickingListeners  = new LinkedList<OnWheelClickedListener>();

    //##################################################
    //# constructors
    //##################################################

    /**
     * Constructor
     */
    public WheelView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initData(context);
    }

    /**
     * Constructor
     */
    public WheelView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initData(context);
    }

    /**
     * Constructor
     */
    public WheelView(Context context)
    {
        super(context);
        initData(context);
    }

    //##################################################
    //# setup
    //##################################################

    /**
     * Initializes class data
     * @param context the context
     */
    private void initData(Context context)
    {
        _scroller = new WheelScroller(getContext(), scrollingListener);
    }

    // Scrolling listener
    WheelScroller.ScrollingListener scrollingListener = newScrollingListener();

    private ScrollingListener newScrollingListener()
    {
        return new WheelScroller.ScrollingListener()
        {
            @Override
            public void onStarted()
            {
                _isScrollingPerformed = true;
                notifyScrollingListenersAboutStart();
            }

            @Override
            public void onScroll(int distance)
            {
                doScroll(distance);

                int height = getHeight();
                if ( _scrollingOffset > height )
                {
                    _scrollingOffset = height;
                    _scroller.stopScrolling();
                }
                else
                    if ( _scrollingOffset < -height )
                    {
                        _scrollingOffset = -height;
                        _scroller.stopScrolling();
                    }
            }

            @Override
            public void onFinished()
            {
                if ( _isScrollingPerformed )
                {
                    notifyScrollingListenersAboutEnd();
                    _isScrollingPerformed = false;
                }

                _scrollingOffset = 0;
                invalidate();
            }

            @Override
            public void onJustify()
            {
                if ( Math.abs(_scrollingOffset) > WheelScroller.MIN_DELTA_FOR_SCROLLING )
                    _scroller.scroll(_scrollingOffset, 0);
            }
        };
    }

    /**
     * Set the the specified scrolling interpolator
     * @param interpolator the interpolator
     */
    public void setInterpolator(Interpolator interpolator)
    {
        _scroller.setInterpolator(interpolator);
    }

    /**
     * Gets count of visible items
     * 
     * @return the count of visible items
     */
    public int getVisibleItems()
    {
        return _visibleItems;
    }

    /**
     * Sets the desired count of visible items.
     * Actual amount of visible items depends on wheel layout parameters.
     * To apply changes and rebuild view call measure(). 
     * 
     * @param count the desired count for visible items
     */
    public void setVisibleItems(int count)
    {
        _visibleItems = count;
    }

    /**
     * Gets view adapter
     * @return the view adapter
     */
    public WheelViewAdapter getViewAdapter()
    {
        return _viewAdapter;
    }

    // Adapter listener
    private DataSetObserver dataObserver = newDataSetObserver();

    private DataSetObserver newDataSetObserver()
    {
        return new DataSetObserver()
        {
            @Override
            public void onChanged()
            {
                invalidateWheel(false);
            }

            @Override
            public void onInvalidated()
            {
                invalidateWheel(true);
            }
        };
    }

    /**
     * Sets view adapter. Usually new adapters contain different views, so
     * it needs to rebuild view by calling measure().
     *  
     * @param viewAdapter the view adapter
     */
    public void setViewAdapter(WheelViewAdapter viewAdapter)
    {
        if ( _viewAdapter != null )
            _viewAdapter.unregisterDataSetObserver(dataObserver);
        _viewAdapter = viewAdapter;
        if ( _viewAdapter != null )
            _viewAdapter.registerDataSetObserver(dataObserver);

        invalidateWheel(true);
    }

    /**
     * Adds wheel changing listener
     * @param listener the listener 
     */
    public void addChangingListener(OnWheelChangedListener listener)
    {
        _changingListeners.add(listener);
    }

    /**
     * Removes wheel changing listener
     * @param listener the listener
     */
    public void removeChangingListener(OnWheelChangedListener listener)
    {
        _changingListeners.remove(listener);
    }

    /**
     * Notifies changing listeners
     * @param oldValue the old wheel value
     * @param newValue the new wheel value
     */
    protected void notifyChangingListeners(int oldValue, int newValue)
    {
        for ( OnWheelChangedListener listener : _changingListeners )
            listener.onChanged(this, oldValue, newValue);
    }

    /**
     * Adds wheel scrolling listener
     * @param listener the listener 
     */
    public void addScrollingListener(OnWheelScrollListener listener)
    {
        _scrollingListeners.add(listener);
    }

    /**
     * Removes wheel scrolling listener
     * @param listener the listener
     */
    public void removeScrollingListener(OnWheelScrollListener listener)
    {
        _scrollingListeners.remove(listener);
    }

    /**
     * Notifies listeners about starting scrolling
     */
    protected void notifyScrollingListenersAboutStart()
    {
        for ( OnWheelScrollListener listener : _scrollingListeners )
            listener.onScrollingStarted(this);
    }

    /**
     * Notifies listeners about ending scrolling
     */
    protected void notifyScrollingListenersAboutEnd()
    {
        for ( OnWheelScrollListener listener : _scrollingListeners )
            listener.onScrollingFinished(this);
    }

    /**
     * Adds wheel clicking listener
     * @param listener the listener 
     */
    public void addClickingListener(OnWheelClickedListener listener)
    {
        _clickingListeners.add(listener);
    }

    /**
     * Removes wheel clicking listener
     * @param listener the listener
     */
    public void removeClickingListener(OnWheelClickedListener listener)
    {
        _clickingListeners.remove(listener);
    }

    /**
     * Notifies listeners about clicking
     */
    protected void notifyClickListenersAboutClick(int item)
    {
        for ( OnWheelClickedListener listener : _clickingListeners )
            listener.onItemClicked(this, item);
    }

    /**
     * Gets current value
     * 
     * @return the current value
     */
    public int getCurrentItem()
    {
        return _currentItem;
    }

    /**
     * Sets the current item. Does nothing when index is wrong.
     * 
     * @param index the item index
     * @param animated the animation flag
     */
    public void setCurrentItem(int index, boolean animated)
    {
        if ( _viewAdapter == null || _viewAdapter.getItemsCount() == 0 )
            return; // throw?

        int itemCount = _viewAdapter.getItemsCount();
        if ( index < 0 || index >= itemCount )
            if ( _isCyclic )
            {
                while ( index < 0 )
                    index += itemCount;
                index %= itemCount;
            }
            else
                return; // throw?
        if ( index != _currentItem )
            if ( animated )
            {
                int itemsToScroll = index - _currentItem;
                if ( _isCyclic )
                {
                    int scroll = itemCount
                        + Math.min(index, _currentItem)
                        - Math.max(index, _currentItem);
                    if ( scroll < Math.abs(itemsToScroll) )
                        itemsToScroll = itemsToScroll < 0
                            ? scroll
                            : -scroll;
                }
                scroll(itemsToScroll, 0);
            }
            else
            {
                _scrollingOffset = 0;

                int old = _currentItem;
                _currentItem = index;

                notifyChangingListeners(old, _currentItem);

                invalidate();
            }
    }

    /**
     * Sets the current item w/o animation. Does nothing when index is wrong.
     * 
     * @param index the item index
     */
    public void setCurrentItem(int index)
    {
        setCurrentItem(index, false);
    }

    /**
     * Tests if wheel is cyclic. That means before the 1st item there is shown the last one
     * @return true if wheel is cyclic
     */
    public boolean isCyclic()
    {
        return _isCyclic;
    }

    /**
     * Set wheel cyclic flag
     * @param isCyclic the flag to set
     */
    public void setCyclic(boolean isCyclic)
    {
        _isCyclic = isCyclic;
        invalidateWheel(false);
    }

    /**
     * Invalidates wheel
     * @param clearCaches if true then cached views will be clear
     */
    public void invalidateWheel(boolean clearCaches)
    {
        if ( clearCaches )
        {
            _recycle.clearAll();
            if ( _itemsLayout != null )
                _itemsLayout.removeAllViews();
            _scrollingOffset = 0;
        }
        else
            if ( _itemsLayout != null )
                // cache all items
                _recycle.recycleItems(_itemsLayout, _firstItem, new ItemsRange());

        invalidate();
    }

    /**
     * Initializes resources
     */
    private void initResourcesIfNecessary()
    {
        if ( _centerDrawable == null )
            _centerDrawable = newValueBackground();

        if ( _topShadow == null )
            _topShadow = new GradientDrawable(Orientation.TOP_BOTTOM, SHADOWS_COLORS);

        if ( _bottomShadow == null )
            _bottomShadow = new GradientDrawable(Orientation.BOTTOM_TOP, SHADOWS_COLORS);

        setBackgroundDrawable(newWheelBackground());
    }

    private Drawable newValueBackground()
    {
        GradientDrawable g;
        g = newGradient(0x70eeeeee, 0x70222222, 0x70222222);
        g.setStroke(1, 0xff333333);
        return g;
    }

    private Drawable newWheelBackground()
    {
        GradientDrawable outer;
        outer = newGradient(0xff333333, 0xffdddddd, 0xff333333);
        outer.setStroke(1, 0xff333333);

        GradientDrawable innerGrad = newGradient(0x70aaaaaa, 0x70ffffff, 0x70aaaaaa);
        InsetDrawable inner = new InsetDrawable(innerGrad, 4, 1, 4, 1);

        return newLayer(outer, inner);
    }

    private LayerDrawable newLayer(Drawable... arr)
    {
        return new LayerDrawable(arr);
    }

    private GradientDrawable newGradient(int... colors)
    {
        return new GradientDrawable(Orientation.TOP_BOTTOM, colors);
    }

    /**
     * Calculates desired height for layout
     * 
     * @param layout
     *            the source layout
     * @return the desired layout height
     */
    private int getDesiredHeight(LinearLayout layout)
    {
        if ( layout != null && layout.getChildAt(0) != null )
            _itemHeight = layout.getChildAt(0).getMeasuredHeight();

        int desired = _itemHeight * _visibleItems - _itemHeight * ITEM_OFFSET_PERCENT / 50;

        return Math.max(desired, getSuggestedMinimumHeight());
    }

    /**
     * Returns height of wheel item
     * @return the item height
     */
    private int getItemHeight()
    {
        if ( _itemHeight != 0 )
            return _itemHeight;

        if ( _itemsLayout != null && _itemsLayout.getChildAt(0) != null )
        {
            _itemHeight = _itemsLayout.getChildAt(0).getHeight();
            return _itemHeight;
        }

        return getHeight() / _visibleItems;
    }

    /**
     * Calculates control width and creates text layouts
     * @param widthSize the input layout width
     * @param mode the layout mode
     * @return the calculated control width
     */
    private int calculateLayoutWidth(int widthSize, int mode)
    {
        initResourcesIfNecessary();

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        _itemsLayout.setLayoutParams(params);
        _itemsLayout.measure(
            MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int width = _itemsLayout.getMeasuredWidth();

        if ( mode == MeasureSpec.EXACTLY )
            width = widthSize;
        else
        {
            width += 2 * PADDING;

            // Check against our minimum width
            width = Math.max(width, getSuggestedMinimumWidth());

            if ( mode == MeasureSpec.AT_MOST && widthSize < width )
                width = widthSize;
        }

        _itemsLayout.measure(
            MeasureSpec.makeMeasureSpec(width - 2 * PADDING, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        return width;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        buildViewForMeasuring();

        int width = calculateLayoutWidth(widthSize, widthMode);

        int height;
        if ( heightMode == MeasureSpec.EXACTLY )
            height = heightSize;
        else
        {
            height = getDesiredHeight(_itemsLayout);

            if ( heightMode == MeasureSpec.AT_MOST )
                height = Math.min(height, heightSize);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        layout(r - l, b - t);
    }

    /**
     * Sets layouts width and height
     * @param width the layout width
     * @param height the layout height
     */
    private void layout(int width, int height)
    {
        int itemsWidth = width - 2 * PADDING;

        _itemsLayout.layout(0, 0, itemsWidth, height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if ( _viewAdapter != null && _viewAdapter.getItemsCount() > 0 )
        {
            updateView();

            drawItems(canvas);
            drawCenterRect(canvas);
        }

        drawShadows(canvas);
    }

    /**
     * Draws shadows on top and bottom of control
     * @param canvas the canvas for drawing
     */
    private void drawShadows(Canvas canvas)
    {
        int height = (int)(1.5 * getItemHeight());
        _topShadow.setBounds(0, 0, getWidth(), height);
        _topShadow.draw(canvas);

        _bottomShadow.setBounds(0, getHeight() - height, getWidth(), getHeight());
        _bottomShadow.draw(canvas);
    }

    /**
     * Draws items
     * @param canvas the canvas for drawing
     */
    private void drawItems(Canvas canvas)
    {
        canvas.save();

        int top = (_currentItem - _firstItem)
            * getItemHeight()
            + (getItemHeight() - getHeight())
            / 2;
        canvas.translate(PADDING, -top + _scrollingOffset);

        _itemsLayout.draw(canvas);

        canvas.restore();
    }

    /**
     * Draws rect for current value
     * @param canvas the canvas for drawing
     */
    private void drawCenterRect(Canvas canvas)
    {
        int center = getHeight() / 2;
        int offset = (int)(getItemHeight() / 2 * 1.2);
        _centerDrawable.setBounds(0, center - offset, getWidth(), center + offset);
        _centerDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if ( !isEnabled() || getViewAdapter() == null )
            return true;

        switch ( event.getAction() )
        {
            case MotionEvent.ACTION_MOVE:
                if ( getParent() != null )
                    getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_UP:
                if ( !_isScrollingPerformed )
                {
                    int distance = (int)event.getY() - getHeight() / 2;
                    if ( distance > 0 )
                        distance += getItemHeight() / 2;
                    else
                        distance -= getItemHeight() / 2;
                    int items = distance / getItemHeight();
                    if ( items != 0 && isValidItemIndex(_currentItem + items) )
                        notifyClickListenersAboutClick(_currentItem + items);
                }
                break;
        }

        return _scroller.onTouchEvent(event);
    }

    /**
     * Scrolls the wheel
     * @param delta the scrolling value
     */
    private void doScroll(int delta)
    {
        _scrollingOffset += delta;

        int itemHeight = getItemHeight();
        int count = _scrollingOffset / itemHeight;

        int pos = _currentItem - count;
        int itemCount = _viewAdapter.getItemsCount();

        int fixPos = _scrollingOffset % itemHeight;
        if ( Math.abs(fixPos) <= itemHeight / 2 )
            fixPos = 0;
        if ( _isCyclic && itemCount > 0 )
        {
            if ( fixPos > 0 )
            {
                pos--;
                count++;
            }
            else
                if ( fixPos < 0 )
                {
                    pos++;
                    count--;
                }
            // fix position by rotating
            while ( pos < 0 )
                pos += itemCount;
            pos %= itemCount;
        }
        else
            // 
            if ( pos < 0 )
            {
                count = _currentItem;
                pos = 0;
            }
            else
                if ( pos >= itemCount )
                {
                    count = _currentItem - itemCount + 1;
                    pos = itemCount - 1;
                }
                else
                    if ( pos > 0 && fixPos > 0 )
                    {
                        pos--;
                        count++;
                    }
                    else
                        if ( pos < itemCount - 1 && fixPos < 0 )
                        {
                            pos++;
                            count--;
                        }

        int offset = _scrollingOffset;
        if ( pos != _currentItem )
            setCurrentItem(pos, false);
        else
            invalidate();

        // update offset
        _scrollingOffset = offset - count * itemHeight;
        if ( _scrollingOffset > getHeight() )
            _scrollingOffset = _scrollingOffset % getHeight() + getHeight();
    }

    /**
     * Scroll the wheel
     * @param itemsToSkip items to scroll
     * @param time scrolling duration
     */
    public void scroll(int itemsToScroll, int time)
    {
        int distance = itemsToScroll * getItemHeight() - _scrollingOffset;
        _scroller.scroll(distance, time);
    }

    /**
     * Calculates range for wheel items
     * @return the items range
     */
    private ItemsRange getItemsRange()
    {
        if ( getItemHeight() == 0 )
            return null;

        int first = _currentItem;
        int count = 1;

        while ( count * getItemHeight() < getHeight() )
        {
            first--;
            count += 2; // top + bottom items
        }

        if ( _scrollingOffset != 0 )
        {
            if ( _scrollingOffset > 0 )
                first--;
            count++;

            // process empty items above the first or below the second
            int emptyItems = _scrollingOffset / getItemHeight();
            first -= emptyItems;
            count += Math.asin(emptyItems);
        }
        return new ItemsRange(first, count);
    }

    /**
     * Rebuilds wheel items if necessary. Caches all unused items.
     * 
     * @return true if items are rebuilt
     */
    private boolean rebuildItems()
    {
        boolean updated = false;
        ItemsRange range = getItemsRange();
        if ( _itemsLayout != null )
        {
            int first = _recycle.recycleItems(_itemsLayout, _firstItem, range);
            updated = _firstItem != first;
            _firstItem = first;
        }
        else
        {
            createItemsLayout();
            updated = true;
        }

        if ( !updated )
            updated = _firstItem != range.getFirst()
                || _itemsLayout.getChildCount() != range.getCount();

        if ( _firstItem > range.getFirst() && _firstItem <= range.getLast() )
            for ( int i = _firstItem - 1; i >= range.getFirst(); i-- )
            {
                if ( !addViewItem(i, true) )
                    break;
                _firstItem = i;
            }
        else
            _firstItem = range.getFirst();

        int first = _firstItem;
        for ( int i = _itemsLayout.getChildCount(); i < range.getCount(); i++ )
            if ( !addViewItem(_firstItem + i, false) && _itemsLayout.getChildCount() == 0 )
                first++;
        _firstItem = first;

        return updated;
    }

    /**
     * Updates view. Rebuilds items and label if necessary, recalculate items sizes.
     */
    private void updateView()
    {
        if ( rebuildItems() )
        {
            calculateLayoutWidth(getWidth(), MeasureSpec.EXACTLY);
            layout(getWidth(), getHeight());
        }
    }

    /**
     * Creates item layouts if necessary
     */
    private void createItemsLayout()
    {
        if ( _itemsLayout == null )
        {
            _itemsLayout = new LinearLayout(getContext());
            _itemsLayout.setOrientation(LinearLayout.VERTICAL);
        }
    }

    /**
     * Builds view for measuring
     */
    private void buildViewForMeasuring()
    {
        // clear all items
        if ( _itemsLayout != null )
            _recycle.recycleItems(_itemsLayout, _firstItem, new ItemsRange());
        else
            createItemsLayout();

        // add views
        int addItems = _visibleItems / 2;
        for ( int i = _currentItem + addItems; i >= _currentItem - addItems; i-- )
            if ( addViewItem(i, true) )
                _firstItem = i;
    }

    /**
     * Adds view for item to items layout
     * @param index the item index
     * @param first the flag indicates if view should be first
     * @return true if corresponding item exists and is added
     */
    private boolean addViewItem(int index, boolean first)
    {
        View view = getItemView(index);
        if ( view != null )
        {
            if ( first )
                _itemsLayout.addView(view, 0);
            else
                _itemsLayout.addView(view);

            return true;
        }

        return false;
    }

    /**
     * Checks whether intem index is valid
     * @param index the item index
     * @return true if item index is not out of bounds or the wheel is cyclic
     */
    private boolean isValidItemIndex(int index)
    {
        return _viewAdapter != null
            && _viewAdapter.getItemsCount() > 0
            && (_isCyclic || index >= 0 && index < _viewAdapter.getItemsCount());
    }

    /**
     * Returns view for specified item
     * @param index the item index
     * @return item view or empty view if index is out of bounds
     */
    private View getItemView(int index)
    {
        if ( _viewAdapter == null || _viewAdapter.getItemsCount() == 0 )
            return null;
        int count = _viewAdapter.getItemsCount();
        if ( !isValidItemIndex(index) )
            return _viewAdapter.getEmptyItem(_recycle.getEmptyItem(), _itemsLayout);

        while ( index < 0 )
            index = count + index;

        index %= count;
        return _viewAdapter.getItem(index, _recycle.getItem(), _itemsLayout);
    }

    /**
     * Stops scrolling
     */
    public void stopScrolling()
    {
        _scroller.stopScrolling();
    }
}
