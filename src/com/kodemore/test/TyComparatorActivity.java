package com.kodemore.test;

import java.util.Collections;
import java.util.Comparator;

import android.view.View;

import com.kodemore.collection.KmList;
import com.kodemore.view.KmAction;
import com.kodemore.view.KmActivity;
import com.kodemore.view.KmColumnLayout;
import com.kodemore.view.KmListView;
import com.kodemore.view.KmOneLineView;
import com.kodemore.view.KmTableLayout;
import com.kodemore.view.KmTableRow;
import com.kodemore.view.KmTextField;

public class TyComparatorActivity
    extends KmActivity
{
    //##################################################
    //# variables
    //##################################################

    private KmTextField        _firstNameField;
    private KmTextField        _lastNameField;

    private KmListView<Person> _listView;
    private KmList<Person>     _list;

    //##################################################
    //# init
    //##################################################

    @Override
    protected void init()
    {
        _firstNameField = ui().newTextField();
        _firstNameField.setAutoSave();

        _lastNameField = ui().newTextField();
        _lastNameField.setAutoSave();

        createListView();
        createList();
    }

    private void createListView()
    {
        _listView = newNameListView();
        _listView.setAutoSave();
    }

    private void createList()
    {
        _list = getEmployeeList();
        Collections.sort(_list, new PersonComparator());

        _listView.addItems(_list);
    }

    private KmList<Person> getEmployeeList()
    {
        KmList<Person> v = new KmList<Person>();

        v.add(createPerson("Valerie", "Smith"));
        v.add(createPerson("Aaron", "Ledbetter"));
        v.add(createPerson("Steve", "Granado"));
        v.add(createPerson("Sean", "Jenson"));
        v.add(createPerson("Wyatt", "Love"));
        v.add(createPerson("Travis", "Plakke"));

        return v;
    }

    private Person createPerson(String firstName, String lastName)
    {
        Person e;
        e = new Person();
        e.setFirstName(firstName);
        e.setLastName(lastName);
        return e;
    }

    private String getFirstName()
    {
        return _firstNameField.getValue();
    }

    private String getLastName()
    {
        return _lastNameField.getValue();
    }

    private KmListView<Person> newNameListView()
    {
        return new KmListView<Person>(ui())
        {
            @Override
            protected View getView(Person e, int index, View oldView)
            {
                String s = e.getLastName() + ", " + e.getFirstName();

                KmOneLineView view;
                view = KmOneLineView.createOrCast(ui(), oldView);
                view.setText(s);
                return view;
            }

            @Override
            protected CharSequence getSectionNameFor(Person e)
            {
                return e.getLastName();
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
        root = new KmColumnLayout(ui());

        KmTableLayout table;
        table = root.addTable();
        table.setColumnStretchable(1);

        KmTableRow row3;
        row3 = table.addRow();
        row3.setItemWidthWrapContent();
        row3.addLabel("First Name: ");
        row3.addView(_firstNameField);

        KmTableRow row4;
        row4 = table.addRow();
        row4.setItemWidthWrapContent();
        row4.addLabel("Last Name: ");
        row4.addView(_lastNameField);

        root.addAddButton(newAddNameAction());
        root.addViewFullWeight(_listView);
        return root;
    }

    //##################################################
    //# action
    //##################################################

    private KmAction newAddNameAction()
    {
        return new KmAction()
        {
            @Override
            public void handle()
            {
                handleAddName();
            }
        };
    }

    private void handleAddName()
    {
        Person e;
        e = new Person();
        e.setLastName(getLastName());
        e.setFirstName(getFirstName());

        _list.add(e);
        Collections.sort(_list, new PersonComparator());
        _listView.setItems(_list);

        _firstNameField.clearValue();
        _lastNameField.clearValue();
    }

    //##################################################
    //# person class
    //##################################################

    public class Person
    {
        String firstName;
        String lastName;

        public boolean hasFirstName()
        {
            return getFirstName() != null;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String e)
        {
            firstName = e;
        }

        public boolean hasLastName()
        {
            return getLastName() != null;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String e)
        {
            lastName = e;
        }
    }

    //##################################################
    //# comparator class
    //##################################################

    /**
     * (valerie)
     *      Tada!  If only you were there to steer me straight every other time
     *      I get off course, which would probably be somewhere around 5 times
     *      a day.
     *      
     * (wyatt)
     *      Excellent.  That's a great start.
     *      
     *      Now, improve it to handle nulls.
     *      Although you ARE guaranteed that the parameters (a, b) will never be 
     *      null, you are NOT guaranteed (says me) that a person's first and last
     *      names will have non-null values.
     *      
     * (valerie)
     * Eh??
     * 
     * review_valerie (wyatt) broken
     *      I suspect that you never actually tested it.
     *      Try adding some null values to the names in getEmployeeList() and run it again.
     */
    public class PersonComparator
        implements Comparator<Person>
    {
        @Override
        public int compare(Person a, Person b)
        {
            String aFirst;
            String aLast;
            String bFirst;
            String bLast;

            if ( !a.hasFirstName() )
                aFirst = "";

            if ( !a.hasLastName() )
                aLast = "";

            if ( !b.hasFirstName() )
                bFirst = "";

            if ( !b.hasLastName() )
                bLast = "";

            aFirst = a.getFirstName();
            aLast = a.getLastName();
            bFirst = b.getFirstName();
            bLast = b.getLastName();

            if ( !aLast.equals(bLast) )
                return aLast.compareTo(bLast);

            return aFirst.compareTo(bFirst);
        }
    }
}
