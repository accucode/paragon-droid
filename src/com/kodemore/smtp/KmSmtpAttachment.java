package com.kodemore.smtp;

import java.io.InputStream;
import java.io.OutputStream;

import android.net.Uri;
import android.util.Base64;
import android.util.Base64OutputStream;

import com.kodemore.file.KmApplicationFilePath;
import com.kodemore.file.KmExternalFilePath;
import com.kodemore.file.KmFilePath;
import com.kodemore.smtp.types.KmSmtpContentType;
import com.kodemore.smtp.types.KmSmtpEncoding;
import com.kodemore.utility.Kmu;

public class KmSmtpAttachment
{
    //##################################################
    //# variables
    //##################################################

    private String            _uid;
    private String            _fileName;
    private Uri               _fileUri;
    private KmSmtpContentType _fileType;
    private Uri               _tempUri;

    //##################################################
    //# constructors
    //##################################################

    public KmSmtpAttachment()
    {
        _uid = Kmu.newUid();
        _fileType = KmSmtpContentType.FILE;
    }

    //##################################################
    //# file uri 
    //##################################################

    public Uri getFileUri()
    {
        return _fileUri;
    }

    public void setFileUri(Uri e)
    {
        _fileUri = e;
    }

    public boolean hasFileUri()
    {
        return getFileUri() != null;
    }

    //##################################################
    //# file name
    //##################################################

    public String getFileName()
    {
        return _fileName;
    }

    public void setFileName(String e)
    {
        _fileName = e;
    }

    public boolean hasFileName()
    {
        return getFileName() != null;
    }

    //##################################################
    //# file type
    //##################################################

    public KmSmtpContentType getFileType()
    {
        return _fileType;
    }

    public void setFileType(KmSmtpContentType e)
    {
        _fileType = e;
    }

    public String getFileTypeName()
    {
        return getFileType().getContentTypeName();
    }

    public boolean hasFileType()
    {
        return getFileType() != null;
    }

    //##################################################
    //# encoding
    //##################################################

    public KmSmtpEncoding getEncoding()
    {
        return _fileType.getEncoding();
    }

    public String getEncodingName()
    {
        return getEncoding().getType();
    }

    //##################################################
    //# validation
    //##################################################

    public boolean isValidAttachment()
    {
        return hasFileType() && hasFileUri() && hasFileName();
    }

    //##################################################
    //# encode
    //##################################################

    /**
     *  Note this isn't wired up for encoding Utf8 or Ascii yet
     *  due to most attachments being either Base64 or ISO encoded.
     */
    public Uri createEncodedAttachment()
    {
        if ( _fileUri == null )
            return null;

        KmFilePath e = new KmExternalFilePath(_fileUri.getPath());

        if ( e.exists() && e.isAvailable() )
            switch ( _fileType.getEncoding() )
            {
                case BASE64:
                    return newBase64File(e);

                case ISO8859:
                    return newIsoFile(e);

                default:
                    return null;
            }

        return null;
    }

    private Uri newIsoFile(KmFilePath f)
    {
        String contents = f.readString();
        contents = Kmu.encodeIso8869(contents);

        KmFilePath out;
        out = new KmApplicationFilePath(getTempFileNamePath());
        out.writeBytes(contents.getBytes());

        _tempUri = out.toUri();

        return _tempUri;
    }

    private Uri newBase64File(KmFilePath fileIn)
    {
        InputStream in = null;

        KmFilePath fileOut = null;
        OutputStream out = null;
        Base64OutputStream b64 = null;

        try
        {
            in = fileIn.openInputStream();

            fileOut = new KmApplicationFilePath(getTempFileNamePath());
            out = fileOut.openOutputStream();
            b64 = new Base64OutputStream(out, Base64.CRLF);

            int c;
            while ( true )
            {
                c = in.read();

                if ( c < 0 )
                    break;

                b64.write(c);
            }

            _tempUri = fileOut.toUri();

            return _tempUri;
        }
        catch ( Exception e )
        {
            //do nothing
        }
        finally
        {
            Kmu.closeSafely(in);
            Kmu.closeSafely(b64);
            Kmu.closeSafely(out);
        }

        return null;
    }

    //##################################################
    //# support
    //##################################################

    private String getTempFileNamePath()
    {
        String path = Kmu.getSimpleNameFor(this) + getUid();
        String ext = ".unk";

        switch ( _fileType )
        {
            case PDF:
                ext = ".bin";
                break;

            case FILE:
                ext = ".bin";
                break;

            case HTML:
                ext = ".html";
                break;

            case TEXT:
                ext = ".txt";
                break;
        }

        path = Kmu.formatSafeFileName(path, ext);

        return path;
    }

    //##################################################
    //# clean up
    //##################################################

    /** 
     *  Deletes the temporary attachment file made by
     *  createEncodedAttachment
     */
    public boolean cleanUp()
    {
        if ( _tempUri == null )
            return false;

        KmFilePath f = new KmExternalFilePath(_tempUri.getPath());

        if ( f.exists() && f.isAvailable() )
        {
            f.delete();
            return true;
        }

        return false;
    }

    //##################################################
    //# uid
    //##################################################

    public String getUid()
    {
        return _uid;
    }

}
