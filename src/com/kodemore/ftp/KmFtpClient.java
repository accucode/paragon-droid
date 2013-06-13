package com.kodemore.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

import com.kodemore.collection.KmList;
import com.kodemore.file.KmExternalFilePath;
import com.kodemore.file.KmFilePath;
import com.kodemore.utility.Kmu;

public class KmFtpClient
{
    //##################################################
    //# variables
    //##################################################

    private FTPClient     _client;

    private Exception     _exception;
    private int           _response;

    private String        _host;
    private String        _user;
    private String        _password;
    private KmFtpFileType _fileType;

    //##################################################
    //# constructor
    //##################################################

    public KmFtpClient()
    {
        _exception = null;
        _response = -1;
        _fileType = KmFtpFileType.DEFAULT;
        _client = new FTPClient();
    }

    //##################################################
    //# connection
    //##################################################

    public void connect()
    {
        try
        {
            if ( _host == null )
                return;

            if ( _host.length() == 0 )
                return;

            _client.connect(_host);
            _client.login(_user, _password);
            _client.setFileType(_fileType.getCode());
            /*
             * This is best setting for clients, other settings
             * are for server-to-server transactions
             */
            _client.enterLocalPassiveMode();
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }
        finally
        {
            assignResponse();
        }
    }

    public void disconnect()
    {
        try
        {
            if ( _client.isConnected() )
            {
                _client.logout();
                _client.disconnect();
            }
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }
        finally
        {
            assignResponse();
        }
    }

    //##################################################
    //# accessing
    //##################################################

    public String getHost()
    {
        return _host;
    }

    public KmFtpFileType getFileType()
    {
        return _fileType;
    }

    public Exception getException()
    {
        return _exception;
    }

    public int getReponse()
    {
        return _response;
    }

    public boolean hasException()
    {
        return _exception != null;
    }

    public boolean hasResponse()
    {
        return _response != -1;
    }

    public boolean isConnected()
    {
        return _client.isConnected();
    }

    public void setFileType(KmFtpFileType type)
    {
        _fileType = type;
    }

    public void setHost(String host)
    {
        _host = host;
    }

    public void setLoginCredentials(String userName, String password)
    {
        _user = userName;
        _password = password;
    }

    //##################################################
    //# transfer
    //##################################################

    /**
     * Reads a string from a file on a ftp server
     */
    public String readFromFile(String what)
    {
        String returnString = null;
        ByteArrayOutputStream out = null;

        try
        {
            out = new ByteArrayOutputStream();

            if ( !_client.isConnected() )
                connect();

            _client.retrieveFile(what, out);

            returnString = out.toString();
        }
        catch ( IOException ex )
        {
            _exception = ex;
        }
        finally
        {
            Kmu.closeSafely(out);
            assignResponse();
        }

        return returnString;
    }

    /**
     * Gets a file from ftp server and writes to disk
     */
    public boolean getFile(String what, String where)
    {
        boolean returnFlag = false;
        OutputStream out = null;

        try
        {
            KmFilePath file = new KmExternalFilePath(where);

            out = file.openOutputStream();

            if ( !_client.isConnected() )
                connect();

            if ( file.isAvailable() )
                returnFlag = _client.retrieveFile(what, out);
        }
        catch ( IOException ex )
        {
            _exception = ex;
            returnFlag = false;
        }
        finally
        {
            Kmu.closeSafely(out);
            assignResponse();
        }

        return returnFlag;
    }

    /**
     * Writes a string to the file on the Ftp server.
     */
    public boolean writeToFile(String what, String where)
    {
        boolean returnFlag = false;
        ByteArrayInputStream in = null;

        try
        {
            in = new ByteArrayInputStream(what.getBytes());

            if ( !_client.isConnected() )
                connect();

            returnFlag = _client.storeFile(where, in);
        }
        catch ( IOException ex )
        {
            _exception = ex;
            returnFlag = false;
        }
        finally
        {
            Kmu.closeSafely(in);
            assignResponse();
        }

        return returnFlag;
    }

    /**
     * Appends a string to the file on the Ftp server.
     */
    public boolean appendToFile(String what, String where)
    {
        boolean returnFlag = false;
        ByteArrayInputStream in = null;

        try
        {
            in = new ByteArrayInputStream(what.getBytes());

            if ( !_client.isConnected() )
                connect();

            returnFlag = _client.appendFile(where, in);
        }
        catch ( IOException ex )
        {
            _exception = ex;
            returnFlag = false;
        }
        finally
        {
            Kmu.closeSafely(in);
            assignResponse();
        }

        return returnFlag;
    }

    /**
     * Gets a file from disk and writes it to ftp server
     */
    public boolean putFile(String what, String where)
    {
        boolean returnFlag = false;
        InputStream in = null;

        try
        {
            KmFilePath file = new KmExternalFilePath(what);

            in = file.openInputStream();

            if ( !_client.isConnected() )
                connect();

            if ( file.isAvailable() && file.exists() )
                returnFlag = _client.storeFile(where, in);
        }
        catch ( IOException ex )
        {
            _exception = ex;
            returnFlag = false;
        }
        finally
        {
            Kmu.closeSafely(in);
            assignResponse();
        }

        return returnFlag;
    }

    //##################################################
    //# file system
    //##################################################

    public String getCurrentDirectory()
    {
        String s = null;

        try
        {
            s = _client.printWorkingDirectory();
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }
        finally
        {
            assignResponse();
        }

        return s;
    }

    public boolean upOneDirectory()
    {
        try
        {
            _client.cdup();
            return true;
        }
        catch ( IOException ex )
        {
            _exception = ex;
            return false;
        }
        finally
        {
            assignResponse();
        }
    }

    public boolean changeDirectory(String directory)
    {
        try
        {
            _client.cwd(directory);
            return true;
        }
        catch ( IOException ex )
        {
            _exception = ex;
            return false;
        }
        finally
        {
            assignResponse();
        }
    }

    public KmList<String> list()
    {
        KmList<String> list = new KmList<String>();

        try
        {
            FTPFile[] files = _client.listFiles();

            int n = files.length;
            for ( int i = 0; i < n; i++ )
                list.add(files[i].getName());
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }
        finally
        {
            assignResponse();
        }

        return list;
    }

    public KmList<String> listDirectories()
    {
        KmList<String> list = new KmList<String>();

        try
        {
            FTPFile[] directories = _client.listDirectories();

            int n = directories.length;
            for ( int i = 0; i < n; i++ )
                list.add(directories[i].getName());
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }
        finally
        {
            assignResponse();
        }

        return list;
    }

    public KmList<String> listFiles()
    {
        KmList<String> list = new KmList<String>();

        try
        {
            FTPFileFilter filter;

            filter = new FTPFileFilter()
            {
                @Override
                public boolean accept(FTPFile file)
                {
                    return !file.isDirectory() && file.isFile();
                }
            };

            FTPFile[] files = _client.listFiles(null, filter);

            int n = files.length;
            for ( int i = 0; i < n; i++ )
                list.add(files[i].getName());
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }
        finally
        {
            assignResponse();
        }

        return list;
    }

    public boolean makeDirectory(String directory)
    {
        boolean flag = false;

        try
        {
            if ( _client.isConnected() )
                flag = _client.makeDirectory(directory);
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        return flag;
    }

    //##################################################
    //# utility
    //##################################################

    protected void assignResponse()
    {
        if ( _client == null )
            return;

        if ( _client.isConnected() )
            _response = _client.getReplyCode();
    }

}
