package com.kodemore.ftp;

import org.apache.commons.net.ftp.FTP;

public enum KmFtpFileType
{
    ASCII(FTP.ASCII_FILE_TYPE),
    BINARY(FTP.BINARY_FILE_TYPE),
    LOCAL(FTP.LOCAL_FILE_TYPE),
    EBCDIC(FTP.EBCDIC_FILE_TYPE),
    DEFAULT(FTP.ASCII_FILE_TYPE);

    private int _type;

    private KmFtpFileType(int type)
    {
        _type = type;
    }

    public int getCode()
    {
        return _type;
    }
}
