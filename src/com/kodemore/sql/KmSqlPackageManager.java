package com.kodemore.sql;

import com.kodemore.collection.KmList;
import com.kodemore.file.KmFilePath;
import com.kodemore.file.KmSharedFilePath;

/**
 * See KmSqlPackage
 */
public abstract class KmSqlPackageManager
{
    public static KmList<KmSqlPackage> getAll()
    {
        KmList<KmSqlPackage> v = new KmList<KmSqlPackage>();

        for ( String name : getAllNames() )
            v.add(new KmSqlPackage(name));

        return v;
    }

    public static KmList<String> getAllNames()
    {
        KmList<String> v = new KmList<String>();
        KmList<KmFilePath> folders = getRootFolder().getChildFolders();
        for ( KmFilePath folder : folders )
            v.add(folder.getName());

        return v;
    }

    public static KmSqlPackage addPackage(String name)
    {
        KmSqlPackage e;
        e = new KmSqlPackage(name);
        e.createFolders();
        e.openMasterDatabase().closeSafely();
        return e;
    }

    public static boolean hasPackage(String name)
    {
        return getAllNames().contains(name);
    }

    /**
     * Return the root path where all of the packages are stored.
     */
    public static KmFilePath getRootFolder()
    {
        return new KmSharedFilePath(KmSqlPackageConstantsIF.ROOT_FOLDER);
    }

}
