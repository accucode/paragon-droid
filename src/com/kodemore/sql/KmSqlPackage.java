package com.kodemore.sql;

import com.kodemore.collection.KmList;
import com.kodemore.file.KmFilePath;
import com.kodemore.utility.KmBridge;
import com.kodemore.utility.KmClock;
import com.kodemore.utility.Kmu;

/**
 * I provide access to a logical groups of physical databases,
 * organized in a common way.  Each package consists of a single
 * logical database, but really contains multiple databases for
 * backups, and temporary usage.
 * 
 * The main structure is as follows:
 * 
 *      master.db   : The main database.
 *      temp.db     : A temporary database use for things like complex migrations.
 *      backup/*.db : A list of backups.
 *      
 * Backups are named using the current utc timestamp.
 * The format is: YYYYMMDD_HHMMSS.db.
 * For example: "20130131_145959.db"
 * 
 * Each package is stored in a separate subfolder.  The package's sole association
 * with its name is the folder under which it is organized.  This allows us to
 * easily clone an entire package by simply copying the entire folder.
 * 
 * The root folder for all sql packages is located under the standard application
 * folder on the external storage (sd card).  In principle, this path looks something
 * like the following.  See getPackagesPath() below.
 *      .../AppPackage/files/sql/packages
 */
public class KmSqlPackage
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The name of the package.  The name is used as part of the
     * file system path, and should be restricted to safe characters.
     * Some good examples include: default, bigTop, bobsDairy.
     */
    private String _name;

    //##################################################
    //# constructors
    //##################################################

    public KmSqlPackage(String name)
    {
        _name = name;
    }

    //##################################################
    //# name 
    //##################################################

    public String getName()
    {
        return _name;
    }

    public void setName(String e)
    {
        _name = e;
    }

    public boolean hasName()
    {
        return Kmu.hasValue(getName());
    }

    public boolean hasName(String e)
    {
        return Kmu.isEqual(getName(), e);
    }

    //##################################################
    //# testing
    //##################################################

    public boolean isActive()
    {
        String s = KmBridge.getInstance().getActiveSqlPackageName();
        return hasName(s);
    }

    //##################################################
    //# paths
    //##################################################

    /**
     * Return the path to this specific package.
     */
    public KmFilePath getPackageFolder()
    {
        return getRootFolder().getChild(getName());
    }

    /**
     * Get a path to a (non-backup) database in my package folder.
     * The parameter should include the file extention, typically ".db".
     */
    public KmFilePath getDatabaseFile(String file)
    {
        return getPackageFolder().getChild(file);
    }

    public KmFilePath getMasterFile()
    {
        return getDatabaseFile(KmSqlPackageConstantsIF.MASTER_FILE);
    }

    public KmFilePath getTempFile()
    {
        return getDatabaseFile(KmSqlPackageConstantsIF.TEMP_FILE);
    }

    /**
     * Return the folder where we store the backups databases.
     */
    public KmFilePath getBackupFolder()
    {
        return getPackageFolder().getChild(KmSqlPackageConstantsIF.BACKUP_FOLDER);
    }

    /**
     * Return the path for a specific backup database.
     * The parameter should include the file extention, typically ".db".
     */
    public KmFilePath getBackupFile(String file)
    {
        return getBackupFolder().getChild(file);
    }

    public KmFilePath getNewBackupFile()
    {
        String file = KmClock.getNowUtc().format_yyyymmdd_hhmmss() + ".db";
        return getBackupFile(file);
    }

    private KmFilePath getRootFolder()
    {
        return KmSqlPackageManager.getRootFolder();
    }

    //##################################################
    //# databases
    //##################################################

    public KmSqlDatabase openMasterDatabase()
    {
        return KmSqlDatabase.open(getMasterFile());
    }

    public KmSqlDatabase openTempDatabase()
    {
        return KmSqlDatabase.open(getTempFile());
    }

    //##################################################
    //# backups
    //##################################################

    public String createBackup()
    {
        KmFilePath backup = getNewBackupFile();
        KmFilePath master = getMasterFile();

        if ( backup.exists() )
            return null;

        if ( !master.copyTo(backup) )
            return null;

        return backup.getName();
    }

    public boolean restoreBackup(String name)
    {
        KmFilePath backup = getBackupFile(name);
        KmFilePath master = getMasterFile();

        if ( !backup.exists() )
            return false;

        return backup.copyTo(master);
    }

    public KmList<String> getBackupNames()
    {
        KmList<String> v = new KmList<String>();

        KmList<KmFilePath> files = getBackupFolder().getChildFiles();
        for ( KmFilePath e : files )
            v.add(e.getName());

        return v;
    }

    //##################################################
    //# misc
    //##################################################

    public void createFolders()
    {
        getPackageFolder().createFolder();
        getBackupFolder().createFolder();
    }

    public void delete()
    {
        getPackageFolder().deleteAll();
    }

    //##################################################
    //# equals
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        if ( e instanceof KmSqlPackage )
            return ((KmSqlPackage)e).hasName(getName());

        return false;
    }

    @Override
    public int hashCode()
    {
        return hasName()
            ? getName().hashCode()
            : 0;
    }

}
