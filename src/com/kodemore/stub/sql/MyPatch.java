package com.kodemore.stub.sql;

import com.kodemore.collection.KmList;
import com.kodemore.file.KmAssetFilePath;
import com.kodemore.file.KmFilePath;
import com.kodemore.sql.KmSqlDatabase;
import com.kodemore.sql.KmSqlParser;
import com.kodemore.utility.KmTaskIF;
import com.kodemore.utility.Kmu;

public abstract class MyPatch
{
    //##################################################
    //# variables
    //##################################################

    private KmTaskIF      _task;
    private KmSqlDatabase _database;

    //##################################################
    //# accessing
    //##################################################

    public abstract int getVersion();

    public boolean hasVersion(int i)
    {
        return getVersion() == i;
    }

    public abstract String getName();

    public KmTaskIF getTask()
    {
        return _task;
    }

    public boolean hasTask()
    {
        return getTask() != null;
    }

    public KmSqlDatabase getDatabase()
    {
        return _database;
    }

    //##################################################
    //# upgrade
    //##################################################

    public void upgradeInBackground(KmTaskIF task, KmSqlDatabase db)
    {
        _task = task;
        _database = db;

        setMessage(getName());
        setMax(1);
        publishProgress(0);

        upgradeInBackground();

        db.setVersion(getVersion());
    }

    protected abstract void upgradeInBackground();

    //##################################################
    //# convenience
    //##################################################

    protected void setMessage(String msg, Object... args)
    {
        if ( hasTask() )
            getTask().setMessage(msg, args);
    }

    protected void setMax(Integer i)
    {
        if ( hasTask() )
            getTask().setMax(i);
    }

    protected void publishProgress(Integer i)
    {
        if ( hasTask() )
            getTask().publishProgress(i);
    }

    //##################################################
    //# script files
    //##################################################

    protected void runScriptFile()
    {
        runScriptFile(getPatchFile());
    }

    protected void runScriptFile(String name)
    {
        runScriptFile(getPatchFile(name));
    }

    private void runScriptFile(KmFilePath file)
    {
        KmList<String> scripts = KmSqlParser.parseFile(file);
        for ( String s : scripts )
            getDatabase().run(s);
    }

    private KmFilePath getPatchFile(String name)
    {
        KmAssetFilePath folder = new KmAssetFilePath("patch");
        return folder.getChild(name);
    }

    /**
     * Get the default patch file.  This name is based on the 
     * suffix of my own class name starting with "patch", and
     * adding a .txt extension.  The first letter is changed to
     * lower case.
     * 
     * For example, if my class name is MyPatch_001
     * Then return, patch_001.txt
     */
    private KmFilePath getPatchFile()
    {
        String cName = getClass().getSimpleName();
        int i = cName.indexOf("Patch");
        if ( i < 0 )
            throw new RuntimeException("Cannot determine patch name.");

        String s;
        s = cName.substring(i);
        s = Kmu.lowercaseFirstLetter(s);
        s = s + ".txt";

        return getPatchFile(s);
    }

}
