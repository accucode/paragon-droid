package com.kodemore.stub.sql;

import com.kodemore.collection.KmList;
import com.kodemore.file.KmFilePath;
import com.kodemore.sql.KmSqlDatabase;
import com.kodemore.sql.KmSqlPackage;
import com.kodemore.sql.KmSqlPackageManager;
import com.kodemore.stub.MyPreferenceWrapper;
import com.kodemore.utility.KmTaskIF;
import com.kodemore.utility.Kmu;

public class MyDatabaseManager
{
    //##################################################
    //# instance
    //##################################################

    private static MyDatabaseManager _instance;

    public static MyDatabaseManager getInstance()
    {
        if ( _instance == null )
            _instance = new MyDatabaseManager();

        return _instance;
    }

    //##################################################
    //# variables
    //##################################################

    private KmSqlDatabase _database;

    //##################################################
    //# constructor
    //##################################################

    private MyDatabaseManager()
    {
        // private
    }

    //##################################################
    //# database
    //##################################################

    public KmSqlDatabase getDatabase()
    {
        if ( _database == null || !_database.isOpen() )
            _database = open();

        return _database;
    }

    public boolean hasDatabase()
    {
        return _database != null;
    }

    //##################################################
    //# open
    //##################################################

    public KmSqlDatabase open()
    {
        return open(true);
    }

    public KmSqlDatabase open(boolean autoUpgrade)
    {
        KmSqlDatabase db;
        db = KmSqlDatabase.open(getDatabasePath());

        if ( autoUpgrade )
            upgradeAll(db);

        return db;
    }

    public KmSqlDatabase reopen()
    {
        closeSafely();
        return open();
    }

    public void closeSafely()
    {
        if ( hasDatabase() )
            getDatabase().closeSafely();

        _database = null;
    }

    public void drop()
    {
        closeSafely();
        getActiveSqlPackage().getPackageFolder().deleteAllFiles();
    }

    //##################################################
    //# testing
    //##################################################

    public boolean exists()
    {
        return getDatabasePath().exists();
    }

    public boolean isCurrent()
    {
        KmSqlDatabase db = null;
        try
        {
            if ( !exists() )
                return false;

            db = open(false);
            return isCurrent(db);
        }
        catch ( Exception ex )
        {
            return false;
        }
        finally
        {
            if ( db != null )
                db.closeSafely();
        }
    }

    //##################################################
    //# path
    //##################################################

    public KmFilePath getDatabasePath()
    {
        return getActiveSqlPackage().getMasterFile();
    }

    public KmFilePath getTempDatabasePath()
    {
        return getActiveSqlPackage().getTempFile();
    }

    //##################################################
    //# package
    //##################################################

    public KmSqlPackage getActiveSqlPackage()
    {
        KmSqlPackage pkg;
        pkg = new KmSqlPackage(getActivePackageName());
        pkg.createFolders();
        return pkg;
    }

    public KmList<String> getAvailablePackageNames()
    {
        return KmSqlPackageManager.getAllNames();
    }

    public String getActivePackageName()
    {
        return getPreferences().getSqlPackageName();
    }

    public void setActivePackageName(String e)
    {
        closeSafely();
        getPreferences().setSqlPackageName(e);
    }

    //##################################################
    //# upgrade
    //##################################################

    public void upgradeAll(KmSqlDatabase db)
    {
        KmTaskIF task = null;
        upgradeAll(task, db);
    }

    public void upgradeAll(KmTaskIF task, KmSqlDatabase db)
    {
        while ( true )
        {
            if ( isCurrent(db) )
                return;

            int next = db.getNextVersion();
            MyPatch patch = MyPatchRegistry.findPatch(next);

            if ( patch == null )
                fatal("Cannot find patch (%s).", next);

            patch.upgradeInBackground(task, db);

            if ( !db.hasVersion(next) )
                fatal("Upgrade failed (%s)", next);
        }
    }

    private boolean isCurrent(KmSqlDatabase db)
    {
        return db.hasVersion(getLatestVersion());
    }

    private int getLatestVersion()
    {
        return MyPatchRegistry.getLatestVersion();
    }

    //##################################################
    //# support
    //##################################################

    private MyPreferenceWrapper getPreferences()
    {
        return new MyPreferenceWrapper();
    }

    private void fatal(String msg, Object... args)
    {
        Kmu.fatal(msg, args);
    }
}
