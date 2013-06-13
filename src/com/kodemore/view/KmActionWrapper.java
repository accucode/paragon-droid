package com.kodemore.view;

public class KmActionWrapper
{
    //##################################################
    //# variables
    //##################################################

    private String   _name;
    private KmAction _action;

    //##################################################
    //# accessing
    //##################################################

    public String getName()
    {
        return _name;
    }

    public void setName(String e)
    {
        _name = e;
    }

    public KmAction getAction()
    {
        return _action;
    }

    public void setAction(KmAction e)
    {
        _action = e;
    }

    public boolean hasAction()
    {
        return _action != null;
    }

    //##################################################
    //# fire
    //##################################################

    public void fire()
    {
        if ( hasAction() )
            getAction().fire();
    }
}
