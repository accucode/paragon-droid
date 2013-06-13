package com.kodemore.stub.sql;

import com.kodemore.collection.KmList;

public abstract class MyPatchRegistry
{
    //##################################################
    //# accessing
    //##################################################

    public static KmList<MyPatch> getPatches()
    {
        KmList<MyPatch> v = new KmList<MyPatch>();
        addPatchesTo(v);
        return v;
    }

    public static int getLatestVersion()
    {
        KmList<MyPatch> v = getPatches();
        return v.isEmpty()
            ? 0
            : v.getLast().getVersion();
    }

    public static MyPatch findPatch(int i)
    {
        for ( MyPatch e : getPatches() )
            if ( e.hasVersion(i) )
                return e;

        return null;
    }

    //##################################################
    //# utility
    //##################################################

    private static void addPatchesTo(KmList<MyPatch> v)
    {
        v.add(new MyPatch_001());
    }
}
