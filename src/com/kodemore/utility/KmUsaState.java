package com.kodemore.utility;

import com.kodemore.collection.KmList;

public enum KmUsaState
{
    //##################################################
    //# enum 
    //##################################################

    AL("AL", "Alabama"),
    AK("AK", "Alaska"),
    AZ("AZ", "Arizona"),
    AR("AR", "Arkansas"),
    CA("CA", "California"),
    CO("CO", "Colorado"),
    CT("CT", "Connecticut"),
    DE("DE", "Delaware"),
    FL("FL", "Florida"),
    GA("GA", "Georgia"),
    HI("HI", "Hawaii"),
    ID("ID", "Idaho"),
    IL("IL", "Illinois"),
    IN("IN", "Indiana"),
    IA("IA", "Iowa"),
    KS("KS", "Kansas"),
    KY("KY", "Kentucky"),
    LA("LA", "Louisiana"),
    ME("ME", "Maine"),
    MD("MD", "Maryland"),
    MA("MA", "Massachusetts"),
    MI("MI", "Michigan"),
    MN("MN", "Minnesota"),
    MS("MS", "Mississippi"),
    MO("MO", "Missouri"),
    MT("MT", "Montana"),
    NE("NE", "Nebraska"),
    NV("NV", "Nevada"),
    NH("NH", "New Hampshire"),
    NJ("NJ", "New Jersey"),
    NM("NM", "New Mexico"),
    NY("NY", "New York"),
    NC("NC", "North Carolina"),
    ND("ND", "North Dakota"),
    OH("OH", "Ohio"),
    OK("OK", "Oklahoma"),
    OR("OR", "Oregon"),
    PA("PA", "Pennsylvania"),
    RI("RI", "Rhode Island"),
    SC("SC", "South Carolina"),
    SD("SD", "South Dakota"),
    TN("TN", "Tennessee"),
    TX("TX", "Texas"),
    UT("UT", "Utah"),
    VT("VT", "Vermont"),
    VA("VA", "Virginia"),
    WA("WA", "Washington"),
    WV("WV", "West Virginia"),
    WI("WI", "Wisconsin"),
    WY("WY", "Wyoming"),
    DC("DC", "Washington D.C.");

    //##################################################
    //# variables
    //##################################################

    private String _code;
    private String _label;

    //##################################################
    //# constructor
    //##################################################

    private KmUsaState(String abbreviation, String name)
    {
        _code = abbreviation;
        _label = name;
    }

    //##################################################
    //# accessing
    //##################################################

    public String getCode()
    {
        return _code;
    }

    private boolean hasCode(String s)
    {
        return getCode().equals(s);
    }

    public String getLabel()
    {
        return _label;
    }

    private boolean hasLabel(String s)
    {
        return getLabel().equals(s);
    }

    public String getName()
    {
        return name();
    }

    //##################################################
    //# convenience
    //##################################################

    public static KmList<KmUsaState> getStates()
    {
        KmList<KmUsaState> v;
        v = new KmList<KmUsaState>();
        v.addAll(values());
        return v;
    }

    public static KmUsaState findLabel(String s)
    {
        for ( KmUsaState e : getStates() )
            if ( e.hasLabel(s) )
                return e;

        return null;
    }

    public static KmUsaState findCode(String s)
    {
        for ( KmUsaState e : getStates() )
            if ( e.hasCode(s) )
                return e;

        return null;
    }
}
