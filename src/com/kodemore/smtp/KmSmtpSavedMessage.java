package com.kodemore.smtp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import android.net.Uri;

import com.kodemore.collection.KmList;
import com.kodemore.file.KmExternalFilePath;
import com.kodemore.file.KmFilePath;
import com.kodemore.utility.Kmu;

public class KmSmtpSavedMessage
    extends KmSmtpMessage
{
    //##################################################
    //# variables
    //##################################################

    private Uri _uri;

    //##################################################
    //# constructors
    //##################################################

    /**
     * KmSmtpSavedMessage should be used to send a message
     * you have previously saved to the SD card
     */
    public KmSmtpSavedMessage()
    {
        setBaseContent(BaseContent.FILE);
    }

    /**
     *  KmSmtpSavedMessage should be used to send a message
     *  you have previously saved to the SD card
     */

    public KmSmtpSavedMessage(Uri uri)
    {
        setBaseContent(BaseContent.FILE);
        load(uri);
    }

    //##################################################
    //# override
    //##################################################

    @Override
    public Uri saveToDisk(String path)
    {
        //do nothing
        return null;
    }

    //##################################################
    //# compose
    //##################################################

    @Override
    protected void composeMessage() throws IOException
    {
        if ( !isComposeReady() )
            throw new IOException("KmSmtpSavedMessage Invalid Uri or File.");

        if ( !composeFromFile() )
            throw new IOException("KmSmtpSavedMessage IO Exception.");
    }

    private boolean composeFromFile()
    {
        KmFilePath f = null;
        Reader r = null;
        BufferedReader b = null;

        try
        {
            if ( _uri == null )
                throw new Exception();

            f = new KmExternalFilePath(_uri.getPath());
            r = f.openFileReader();
            b = new BufferedReader(r);

            while ( true )
            {
                String s = b.readLine();

                if ( s == null )
                    break;

                if ( s.equals(END_RECIPIENTS) )
                    break;
            }

            while ( true )
            {
                int c = b.read();

                if ( c < 0 )
                    break;

                write(c);
            }

            return true;
        }
        catch ( Exception ex )
        {
            return false;
        }
        finally
        {
            Kmu.closeSafely(b);
            Kmu.closeSafely(r);
        }
    }

    //##################################################
    //# accessing
    //##################################################

    public Uri getUri()
    {
        return _uri;
    }

    public boolean loadUri(Uri uri)
    {
        return load(uri);
    }

    public boolean loadUri(KmFilePath file)
    {
        return loadUri(file.toUri());
    }

    public boolean isComposeReady()
    {
        return _uri != null;
    }

    //##################################################
    //# utility
    //##################################################

    private boolean load(Uri uri)
    {
        KmFilePath f = null;
        Reader r = null;
        BufferedReader b = null;

        _uri = uri;

        try
        {
            if ( _uri == null )
                throw new Exception();

            f = new KmExternalFilePath(_uri.getPath());
            r = f.openFileReader();
            b = new BufferedReader(r);

            loadReader(b);

            return true;
        }
        catch ( Exception ex )
        {
            _uri = null;
            return false;
        }
        finally
        {
            Kmu.closeSafely(b);
            Kmu.closeSafely(r);
        }
    }

    private void loadReader(BufferedReader b) throws Exception
    {
        String sender = null;
        KmList<String> recipients = new KmList<String>();

        int n = 2;
        for ( int i = 0; i < n; i++ )
        {
            String s = b.readLine();

            if ( s == null )
                break;

            if ( s.equals(END_SENDER) )
                break;

            if ( sender == null )
                sender = s;
        }

        while ( true )
        {
            String s = b.readLine();

            if ( s == null )
                break;

            if ( s.equals(END_RECIPIENTS) )
                break;

            recipients.add(s);
        }

        if ( Kmu.isEmpty(sender) )
            throw new Exception("Invalid format");

        setFrom(sender);

        if ( recipients.isEmpty() )
            return;

        for ( String s : recipients )
            addRecipient(s);
    }

    //##################################################
    //# convenience
    //##################################################

    public void delete()
    {
        if ( _uri == null )
            return;

        KmFilePath file = new KmExternalFilePath(_uri.getPath());
        file.delete();
    }
}
