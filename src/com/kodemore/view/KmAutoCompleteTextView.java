package com.kodemore.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;

import com.kodemore.collection.KmList;
import com.kodemore.drawable.KmDrawableBuilder;
import com.kodemore.utility.KmRunnable;
import com.kodemore.utility.Kmu;

public abstract class KmAutoCompleteTextView<T>
    extends AutoCompleteTextView
{
    //##################################################
    //# variables
    //##################################################

    protected KmUiManager                           _ui;

    private T                                       _selectedItem;

    private KmAdapterViewOnItemClickListenerWrapper _listenerWrapper;

    //##################################################
    //# constructor
    //##################################################

    public KmAutoCompleteTextView(KmUiManager ui)
    {
        super(ui.getContext());

        _ui = ui;

        setSingleLine();
        setThreshold(1);

        setAdapter(newAdapter());

        _listenerWrapper = newClickWrapper();
        super.setOnItemClickListener(_listenerWrapper);

        setDropDownBackgroundDrawable(getDefaultBackground());
    }

    public static Drawable getDefaultBackground()
    {
        KmDrawableBuilder b;
        b = new KmDrawableBuilder();
        b.addPad(2);
        b.addPaint(Color.LTGRAY, 1).setCornerRadius(3);
        b.addPaint(Color.DKGRAY, 1).setCornerRadius(2);
        return b.toDrawable();
    }

    //##################################################
    //# core
    //##################################################

    protected KmUiManager ui()
    {
        return _ui;
    }

    public KmTextViewHelper getHelper()
    {
        return new KmTextViewHelper(this);
    }

    public void setAutoSave()
    {
        setId();
        setSaveEnabled(true);
        setFreezesText(true);
    }

    //##################################################
    //# id
    //##################################################

    public void setId()
    {
        if ( !hasId() )
            setId(ui().nextId());
    }

    public boolean hasId()
    {
        return getId() != NO_ID;
    }

    //##################################################
    //# visibility
    //##################################################

    public void show()
    {
        getHelper().show();
    }

    public void hide()
    {
        getHelper().hide();
    }

    public void gone()
    {
        getHelper().gone();
    }

    //##################################################
    //# adapter
    //##################################################

    private KmArrayAdapter<T> newAdapter()
    {
        return new KmArrayAdapter<T>(_ui)
        {
            @Override
            protected View getView(T item, int index, View oldView)
            {
                return getLineView(item);
            }

            @Override
            public Filter getFilter()
            {
                return newFilter();
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public KmArrayAdapter<T> getAdapter()
    {
        return (KmArrayAdapter<T>)super.getAdapter();
    }

    protected View getLineView(T item)
    {
        return new KmOneLineView(_ui, item);
    }

    //##################################################
    //# filter
    //##################################################

    private Filter newFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                _selectedItem = null;

                if ( Kmu.isEmpty(constraint) )
                    return new FilterResults();

                String s = constraint.toString();
                KmList<T> v = KmAutoCompleteTextView.this.performFiltering(s);
                return toResults(v);
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                if ( results == null || results.count == 0 )
                {
                    getAdapter().clear();
                    return;
                }

                @SuppressWarnings("unchecked")
                KmList<T> items = (KmList<T>)results.values;

                getAdapter().setItems(items);
                getAdapter().notifyDataSetChanged();
            }

            private FilterResults toResults(KmList<T> v)
            {
                FilterResults r;
                r = new FilterResults();
                r.values = v;
                r.count = v.size();
                return r;
            }
        };
    }

    protected abstract KmList<T> performFiltering(String s);

    //##################################################
    //# on click
    //##################################################

    @Override
    public void setOnItemClickListener(OnItemClickListener e)
    {
        _listenerWrapper.setExtra(e);
    }

    private KmAdapterViewOnItemClickListenerWrapper newClickWrapper()
    {
        return new KmAdapterViewOnItemClickListenerWrapper(newMainClickListener());
    }

    private AdapterView.OnItemClickListener newMainClickListener()
    {
        return new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                _selectedItem = getAdapter().getItem(position);
            }
        };
    }

    //##################################################
    //# convenience
    //##################################################

    public String getTextString()
    {
        return getText().toString();
    }

    public void clearText()
    {
        setText("");
    }

    public void clearTextAsyncSafe()
    {
        new KmRunnable()
        {
            @Override
            public void run()
            {
                clearText();
            }
        }.runOnUiThread(ui());
    }

    //##################################################
    //# utility
    //##################################################

    @SuppressWarnings("unchecked")
    @Override
    protected CharSequence convertSelectionToString(Object selectedItem)
    {
        return formatSelectedItem((T)selectedItem);
    }

    protected String formatSelectedItem(T item)
    {
        return item.toString();
    }

    public boolean isEmpty()
    {
        return Kmu.isEmpty(getText());
    }

    public T getSelectedItem()
    {
        return _selectedItem;
    }

}
