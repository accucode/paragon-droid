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

package com.kodemore.view;

import java.util.List;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import com.kodemore.collection.KmList;
import com.kodemore.utility.Kmu;

public class KmArrayAdapter<K>
    extends ArrayAdapter<K>
    implements SectionIndexer
{
    //##################################################
    //# variables
    //##################################################

    private KmUiManager     _ui;

    private KmList<Section> _sections;
    private boolean         _sectionsDirty;

    //##################################################
    //# constructors
    //##################################################

    public KmArrayAdapter(KmUiManager ui)
    {
        super(ui.getContext(), 0);

        _ui = ui;
        registerDataSetObserver(newDataSetObserver());
    }

    private DataSetObserver newDataSetObserver()
    {
        return new DataSetObserver()
        {
            @Override
            public void onChanged()
            {
                invalidateSections();
            }

            @Override
            public void onInvalidated()
            {
                invalidateSections();
            }
        };
    }

    //##################################################
    //# ui
    //##################################################

    public KmUiManager ui()
    {
        return _ui;
    }

    //##################################################
    //# items
    //##################################################

    public void addItems(List<K> v)
    {
        for ( K e : v )
            add(e);
    }

    public void addItems(K... v)
    {
        for ( K e : v )
            add(e);
    }

    public void setItems(List<K> v)
    {
        clear();
        addItems(v);
    }

    public KmList<K> getItems()
    {
        KmList<K> v = new KmList<K>();

        int n = getCount();
        for ( int i = 0; i < n; i++ )
            v.add(getItem(i));

        return v;
    }

    public K getFirstItem()
    {
        return getItem(0);
    }

    public K getFirstItemSafe()
    {
        return isEmpty()
            ? null
            : getFirstItem();
    }

    //##################################################
    //# child view
    //##################################################

    @Override
    public final View getView(int index, View oldView, ViewGroup parent)
    {
        K e = getItem(index);
        return getView(e, index, oldView);
    }

    @Override
    public final View getDropDownView(int index, View oldView, ViewGroup parent)
    {
        K e = getItem(index);
        return getDropDownView(e, index, oldView);
    }

    //##################################################
    //# view
    //##################################################

    /**
     * Defaults to a safe but simplistic view.  This is the 
     * method that subclasses generally override.  This is 
     * the view used for simple text or fields; see all,
     * the getDropDownView.
     */
    protected View getView(K item, int index, View oldView)
    {
        return newOneLineView(item);
    }

    /**
     * Get the view used for items when the dropdown 
     * is opened. 
     */
    protected View getDropDownView(K item, int index, View oldView)
    {
        return newOneLineView(item);
    }

    //##################################################
    //# convenience
    //##################################################

    protected View newOneLineView(Object e)
    {
        KmOneLineView view;
        view = new KmOneLineView(ui());
        view.setText(e);
        return view;
    }

    protected View newOneLineBoldView(Object e)
    {
        KmOneLineView view;
        view = new KmOneLineView(ui());
        view.setTextBold(e);
        return view;
    }

    //##################################################
    //# sections
    //##################################################

    @Override
    public final int getPositionForSection(int section)
    {
        return getSectionList().get(section).position;
    }

    @Override
    public final int getSectionForPosition(int position)
    {
        K e = getItem(position);
        Character letter = getSectionLetterFor(e);

        KmList<Section> v = getSectionList();

        int n = v.size();
        for ( int i = 0; i < n; i++ )
        {
            Section section = v.get(i);
            if ( section.letter.compareTo(letter) < 0 )
                return i;
        }

        return -1;
    }

    @Override
    public final Object[] getSections()
    {
        KmList<Section> v = getSectionList();
        if ( v == null )
            return null;

        if ( v.isEmpty() )
            return null;

        return v.toArray();
    }

    //==================================================
    //= sections :: support
    //==================================================

    private Character getSectionLetterFor(K e)
    {
        CharSequence s = getSectionNameFor(e);

        return s.length() == 0
            ? '-'
            : Kmu.toUpperCase(s.charAt(0));
    }

    private void invalidateSections()
    {
        _sections = null;
        _sectionsDirty = true;
    }

    private KmList<Section> getSectionList()
    {
        if ( _sectionsDirty )
        {
            _sections = computeSections();
            _sectionsDirty = false;
        }

        return _sections;
    }

    private KmList<Section> computeSections()
    {
        if ( isEmpty() )
            return null;

        if ( !usesSections() )
            return null;

        KmList<Section> v = new KmList<Section>();
        K item;
        Character prevLetter = null;
        Character letter = null;

        int n = getCount();
        for ( int i = 0; i < n; i++ )
        {
            item = getItem(i);
            letter = getSectionLetterFor(item);

            if ( Kmu.isNotEqual(letter, prevLetter) )
            {
                Section section;
                section = new Section();
                section.letter = letter;
                section.position = i;

                v.add(section);
                prevLetter = letter;
            }
        }

        return v;
    }

    private boolean usesSections()
    {
        if ( isEmpty() )
            return false;

        K e = getFirstItem();
        return getSectionNameFor(e) != null;
    }

    private class Section
    {
        public Character letter;
        public int       position;

        @Override
        public String toString()
        {
            return letter.toString();
        }
    }

    //==================================================
    //= sections :: subclass
    //==================================================

    /**
     * This is the only the method that subclasses need to override
     * in order to implement the section indexer (rolodex).  The
     * default behavior is to return null, which means no sections
     * are to be displayed.  To implement sections, simply override
     * this method so that it is guaranteed to return a non-null
     * value (empty strings are ok).
     * 
     * The parameter is guaranteed to be non-null.
     * 
     * Note: For convenience you may simply return any non-null string.
     * However, in practice, only the first letter is used after being 
     * converted to uppercase.
     * 
     * Note: If you return an empty string, it will be indexed under
     * the entry for a single dash (-).
     * 
     * Note: Overriding this method does NOT automatically turn on
     * the thumb-dragging icon.  You must still call the listView's
     * setFastScrollAlwaysVisible(), or setFastScrollEnabled().
     */
    protected CharSequence getSectionNameFor(K e)
    {
        return null;
    }

}
