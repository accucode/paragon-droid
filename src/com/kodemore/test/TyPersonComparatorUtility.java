package com.kodemore.test;

import java.util.Comparator;

import com.kodemore.comparator.KmComparator;

public class TyPersonComparatorUtility
{
    //##################################################
    //# enum
    //##################################################

    public enum SortBy
    {
        FIRST,
        LAST;
    }

    //##################################################
    //# variables
    //##################################################

    private SortBy _sortBy;

    //##################################################
    //# accessing
    //##################################################

    public SortBy getSortBy()
    {
        return _sortBy;
    }

    public void setSortBy(SortBy sortBy)
    {
        _sortBy = sortBy;
    }

    //##################################################
    //# comparator
    //##################################################

    public static KmComparator<TyPersonObject> getFirstNameComparator()
    {
        return new KmComparator<TyPersonObject>()
        {
            @Override
            public int compare(TyPersonObject a, TyPersonObject b)
            {
                return c(a.getFirstName(), b.getFirstName());
            }
        };
    }

    public static KmComparator<TyPersonObject> getLastNameComparator()
    {
        return new KmComparator<TyPersonObject>()
        {
            @Override
            public int compare(TyPersonObject a, TyPersonObject b)
            {
                return c(a.getLastName(), b.getLastName());
            }
        };
    }

    public Comparator<TyPersonObject> myPersonComparator()
    {
        return new Comparator<TyPersonObject>()
        {
            @Override
            public int compare(TyPersonObject a, TyPersonObject b)
            {
                String s1 = null;
                String s2 = null;
                switch ( _sortBy )
                {
                    case FIRST:
                        s1 = a.getFirstName();
                        s2 = b.getFirstName();
                        if ( s1 == null && s2 == null )
                        {
                            s1 = a.getLastName();
                            s2 = b.getLastName();
                        }
                        if ( s1 != null && s2 != null && s1.compareToIgnoreCase(s2) == 0 )
                        {
                            s1 = a.getLastName();
                            s2 = b.getLastName();
                        }
                        break;
                    case LAST:
                        s1 = a.getLastName();
                        s2 = b.getLastName();
                        if ( s1 == null && s2 == null )
                        {
                            s1 = a.getLastName();
                            s2 = b.getLastName();
                        }
                        if ( s1 != null && s2 != null && s1.compareToIgnoreCase(s2) == 0 )
                        {
                            s1 = a.getLastName();
                            s2 = b.getLastName();
                        }
                        break;
                }
                if ( s1 == null && s2 == null )
                    return 0;
                if ( s1 == null )
                    return -1;
                if ( s2 == null )
                    return 1;
                return s1.compareToIgnoreCase(s2);
            }
        };
    }

}
