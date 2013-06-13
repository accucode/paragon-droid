package com.kodemore.utility;

public abstract class KmAbstractFilter<K>
    implements KmFilterIF<K>
{
    //##################################################
    //# filter 
    //##################################################

    @Override
    public abstract boolean includes(K e);

    @Override
    public boolean excludes(K e)
    {
        return !includes(e);
    }

    @Override
    public KmFilterIF<K> reverse()
    {
        final KmFilterIF<K> f = this;
        return new KmAbstractFilter<K>()
        {
            @Override
            public boolean includes(K e)
            {
                return f.excludes(e);
            }
        };
    }

    //##################################################
    //# support
    //##################################################

    protected boolean matchesEqual(Object value, Object filter)
    {
        return Kmu.isEqual(value, filter);
    }

    protected boolean matchesString(String value, String filter)
    {
        return Kmu.isEqualIgnoreCase(value, filter);
    }

    protected boolean matchesSubstring(String value, String filter)
    {
        if ( value == null && filter == null )
            return true;

        if ( value == null )
            return false;

        return value.toLowerCase().contains(filter.toLowerCase());
    }
}
