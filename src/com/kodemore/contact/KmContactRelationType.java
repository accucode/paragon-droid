package com.kodemore.contact;

import android.provider.ContactsContract.CommonDataKinds.Relation;

public enum KmContactRelationType
{
    ASSISTANT(Relation.TYPE_ASSISTANT),
    BROTHER(Relation.TYPE_BROTHER),
    CHILD(Relation.TYPE_CHILD),
    DOMESTIC_PARTNER(Relation.TYPE_DOMESTIC_PARTNER),
    FATHER(Relation.TYPE_FATHER),
    FRIEND(Relation.TYPE_FRIEND),
    MANAGER(Relation.TYPE_MANAGER),
    MOTHER(Relation.TYPE_MOTHER),
    PARENT(Relation.TYPE_PARENT),
    PARTNER(Relation.TYPE_PARTNER),
    REFERRED_BY(Relation.TYPE_REFERRED_BY),
    RELATIVE(Relation.TYPE_RELATIVE),
    SISTER(Relation.TYPE_SISTER),
    SPOUSE(Relation.TYPE_SPOUSE),
    CUSTOM(Relation.TYPE_CUSTOM);

    //##################################################
    //# variables 
    //##################################################

    private int _code;

    //##################################################
    //# constructor
    //##################################################

    KmContactRelationType(int e)
    {
        _code = e;
    }

    //##################################################
    //# accessing
    //##################################################

    public int getCode()
    {
        return _code;
    }
}