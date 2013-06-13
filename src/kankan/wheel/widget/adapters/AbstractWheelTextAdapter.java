/*
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
package kankan.wheel.widget.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Abstract wheel adapter provides common functionality for adapters.
 */
public abstract class AbstractWheelTextAdapter
    extends AbstractWheelAdapter
{
    //##################################################
    //# constants
    //##################################################

    /** Text view resource. Used as a default view for adapter. */
    public static final int    TEXT_VIEW_ITEM_RESOURCE = -1;

    /** No resource constant. */
    protected static final int NO_RESOURCE             = 0;

    /** Default text color */
    public static final int    DEFAULT_TEXT_COLOR      = 0xFF101010;

    /** Default text color */
    public static final int    LABEL_COLOR             = 0xFF700070;

    /** Default text size */
    public static final int    DEFAULT_TEXT_SIZE       = 24;

    //##################################################
    //# variables
    //##################################################

    // Text settings
    private int                _textColor              = DEFAULT_TEXT_COLOR;
    private int                _textSize               = DEFAULT_TEXT_SIZE;

    // Current context
    protected Context          _context;
    // Layout inflater
    protected LayoutInflater   _inflater;

    // Items resources
    protected int              _itemResourceId;
    protected int              _itemTextResourceId;

    // Empty items resources
    protected int              _emptyItemResourceId;

    //##################################################
    //# constructors
    //##################################################

    /**
     * Constructor
     * @param context the current context
     */
    protected AbstractWheelTextAdapter(Context context)
    {
        this(context, TEXT_VIEW_ITEM_RESOURCE);
    }

    /**
     * Constructor
     * @param context the current context
     * @param itemResource the resource ID for a layout file containing a TextView to use when instantiating items views
     */
    protected AbstractWheelTextAdapter(Context context, int itemResource)
    {
        this(context, itemResource, NO_RESOURCE);
    }

    /**
     * Constructor
     * @param context the current context
     * @param itemResource the resource ID for a layout file containing a TextView to use when instantiating items views
     * @param itemTextResource the resource ID for a text view in the item layout
     */
    protected AbstractWheelTextAdapter(Context context, int itemResource, int itemTextResource)
    {
        _context = context;
        _itemResourceId = itemResource;
        _itemTextResourceId = itemTextResource;

        _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //##################################################
    //# accessing
    //##################################################

    /**
     * Gets text color
     * @return the text color
     */
    public int getTextColor()
    {
        return _textColor;
    }

    /**
     * Sets text color
     * @param textColor the text color to set
     */
    public void setTextColor(int textColor)
    {
        _textColor = textColor;
    }

    /**
     * Gets text size
     * @return the text size
     */
    public int getTextSize()
    {
        return _textSize;
    }

    /**
     * Sets text size
     * @param textSize the text size to set
     */
    public void setTextSize(int textSize)
    {
        _textSize = textSize;
    }

    /**
     * Gets resource Id for items views
     * @return the item resource Id
     */
    public int getItemResource()
    {
        return _itemResourceId;
    }

    /**
     * Sets resource Id for items views
     * @param itemResourceId the resource Id to set
     */
    public void setItemResource(int itemResourceId)
    {
        _itemResourceId = itemResourceId;
    }

    /**
     * Gets resource Id for text view in item layout 
     * @return the item text resource Id
     */
    public int getItemTextResource()
    {
        return _itemTextResourceId;
    }

    /**
     * Sets resource Id for text view in item layout 
     * @param itemTextResourceId the item text resource Id to set
     */
    public void setItemTextResource(int itemTextResourceId)
    {
        _itemTextResourceId = itemTextResourceId;
    }

    /**
     * Gets resource Id for empty items views
     * @return the empty item resource Id
     */
    public int getEmptyItemResource()
    {
        return _emptyItemResourceId;
    }

    /**
     * Sets resource Id for empty items views
     * @param emptyItemResourceId the empty item resource Id to set
     */
    public void setEmptyItemResource(int emptyItemResourceId)
    {
        _emptyItemResourceId = emptyItemResourceId;
    }

    /**
     * Returns text for specified item
     * @param index the item index
     * @return the text of specified items
     */
    protected abstract CharSequence getItemText(int index);

    @Override
    public View getItem(int index, View convertView, ViewGroup parent)
    {
        if ( index >= 0 && index < getItemsCount() )
        {
            if ( convertView == null )
                convertView = getView(_itemResourceId, parent);
            TextView textView = getTextView(convertView, _itemTextResourceId);
            if ( textView != null )
            {
                CharSequence text = getItemText(index);
                if ( text == null )
                    text = "";
                textView.setText(text);

                if ( _itemResourceId == TEXT_VIEW_ITEM_RESOURCE )
                    configureTextView(textView);
            }
            return convertView;
        }
        return null;
    }

    @Override
    public View getEmptyItem(View convertView, ViewGroup parent)
    {
        if ( convertView == null )
            convertView = getView(_emptyItemResourceId, parent);
        if ( _emptyItemResourceId == TEXT_VIEW_ITEM_RESOURCE && convertView instanceof TextView )
            configureTextView((TextView)convertView);

        return convertView;
    }

    /**
     * Configures text view. Is called for the TEXT_VIEW_ITEM_RESOURCE views.
     * @param view the text view to be configured
     */
    protected void configureTextView(TextView view)
    {
        view.setTextColor(_textColor);
        view.setGravity(Gravity.CENTER);
        view.setTextSize(_textSize);
        view.setLines(1);
        view.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
    }

    /**
     * Loads a text view from view
     * @param view the text view or layout containing it
     * @param textResource the text resource Id in layout
     * @return the loaded text view
     */
    private TextView getTextView(View view, int textResource)
    {
        TextView text = null;
        try
        {
            if ( textResource == NO_RESOURCE && view instanceof TextView )
                text = (TextView)view;
            else
                if ( textResource != NO_RESOURCE )
                    text = (TextView)view.findViewById(textResource);
        }
        catch ( ClassCastException e )
        {
            Log.e("AbstractWheelAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                "AbstractWheelAdapter requires the resource ID to be a TextView",
                e);
        }

        return text;
    }

    /**
     * Loads view from resources
     * @param resource the resource Id
     * @return the loaded view or null if resource is not set
     */
    private View getView(int resource, ViewGroup parent)
    {
        switch ( resource )
        {
            case NO_RESOURCE:
                return null;
            case TEXT_VIEW_ITEM_RESOURCE:
                return new TextView(_context);
            default:
                return _inflater.inflate(resource, parent, false);
        }
    }
}
