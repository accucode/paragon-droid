package com.kodemore.smtp;

import java.io.IOException;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.AuthenticatingSMTPClient.AUTH_METHOD;
import org.apache.commons.net.smtp.SMTPReply;

import com.kodemore.utility.Kmu;

public class KmSmtpClient
{
    //##################################################
    //# constants
    //##################################################

    private static final int         SMTP_MESSAGE_SENT = 250;
    private static final int         SMTP_DEFAULT_PORT = 25;
    // private static final int         SMTP_SSL_PORT     = 465;
    private static final int         SMTP_MSA_PORT     = 587;

    //##################################################
    //# variables
    //##################################################

    private AuthenticatingSMTPClient _client;
    private int                      _response;
    private Exception                _exception;

    private boolean                  _authenticate;
    private boolean                  _tls;
    private AUTH_METHOD              _authMethod;

    private String                   _host;
    private int                      _port;
    private String                   _user;
    private String                   _password;

    /**
     * Returns whether a message was sent successfully or not.
     * Returns false if not, true if so, null if there was no
     * attempt to send the message.
     */
    private Boolean                  _sent;
    private KmSmtpAbstractMessage    _message;
    private Writer                   _writer;

    //##################################################
    //# constructors
    //##################################################

    public KmSmtpClient()
    {
        this(null, SMTP_DEFAULT_PORT);
    }

    public KmSmtpClient(String host, int port)
    {
        _host = host;
        _port = port;
        _tls = false;
        _authenticate = false;

        _sent = null;
        _exception = null;
        _response = -1;

        try
        {
            _client = new AuthenticatingSMTPClient();
        }
        catch ( NoSuchAlgorithmException ex )
        {
            _exception = ex;
            _client = null;
        }
    }

    //##################################################
    //# accessing
    //##################################################

    public int getPort()
    {
        return _port;
    }

    /** 
     * Returns standard SMTP response codes, or -1 if there aren't any.
     * This will return responses for connection events only.
     */

    public int getResponse()
    {
        return _response;
    }

    public Exception getException()
    {
        return _exception;
    }

    public String getHost()
    {
        return _host;
    }

    public boolean hasResponse()
    {
        return _response != -1;
    }

    public boolean hasException()
    {
        return _exception != null;
    }

    public void setHost(String e)
    {
        _host = e;
    }

    public void setPort(int e)
    {
        _port = e;
    }

    public void setMessage(KmSmtpAbstractMessage mail)
    {
        _message = mail;
        _sent = null;
    }

    public Boolean getSent()
    {
        return _sent;
    }

    public boolean isSent()
    {
        if ( getSent() == null )
            return false;

        return getSent();
    }

    //##################################################
    //# security
    //##################################################

    public void enableTls()
    {
        _tls = true;
    }

    public boolean isTlsEnabled()
    {
        return _tls;
    }

    public void setAuthentication(AUTH_METHOD method)
    {
        _authMethod = method;
        enableAuthentication();
    }

    public void enableAuthentication()
    {
        _authenticate = true;
    }

    public boolean isAuthenticationEnabled()
    {
        return _authenticate;
    }

    public void setLoginCredentials(String userName, String password)
    {
        _user = userName;
        _password = password;
    }

    public void setAuthentication(AUTH_METHOD method, String userName, String password)
    {
        setAuthentication(method);
        setLoginCredentials(userName, password);
        enableAuthentication();
    }

    //##################################################
    //# message
    //##################################################

    public boolean sendMessage()
    {
        if ( _message == null || _client == null )
            return false;

        boolean flag = false;

        try
        {
            _connect();
            _compose();
            flag = _execute();
            _disconnect();
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }

        _sent = flag;
        return flag;
    }

    //##################################################
    //# convenience
    //##################################################

    /**
     * This works with Sendgrid server-side authentication by default.
     */
    public void setCommonSecurity()
    {
        setAuthentication(AUTH_METHOD.LOGIN);
        enableTls();
    }

    /**
     * Sets the port 587, a commonly used port of service providers.
     * This port is recommended to be used by Sendgrid for their service.
     */
    public void setCommonPort()
    {
        _port = SMTP_MSA_PORT;
    }

    //##################################################
    //# utility
    //##################################################

    private void assignResponse()
    {
        if ( _client == null )
        {
            _response = -1;
            return;
        }

        _response = _client.getReplyCode();
    }

    private void _connect() throws Exception
    {
        if ( _client == null )
            return;

        _client.connect(_host, _port);
        _client.login();

        if ( isTlsEnabled() )
            _client.execTLS();

        if ( isAuthenticationEnabled() )
            _client.auth(_authMethod, _user, _password);

        assignResponse();

        if ( !SMTPReply.isPositiveCompletion(getResponse()) )
            throw new IOException("Connection cannot be resolved.");
    }

    private void _compose() throws IOException
    {
        if ( _message == null )
            return;

        String from = _message.getFrom();

        if ( from == null )
            return;

        if ( !_message.hasRecipients() )
            return;

        try
        {
            _client.setSender(from);

            for ( KmSmtpRecipient e : _message.getRecipients() )
                _client.addRecipient(e.getAddress());

            _writer = _client.sendMessageData();

            _message.composeOn(_writer);
        }
        finally
        {
            Kmu.closeSafely(_writer);
        }
    }

    private boolean _execute() throws IOException
    {
        if ( _client == null )
            return false;

        if ( _writer == null )
            return false;

        _client.completePendingCommand();

        if ( _client.getReplyCode() == SMTP_MESSAGE_SENT )
            return true;

        return false;
    }

    private void _disconnect() throws IOException
    {
        if ( _client == null )
            return;

        _client.logout();
        _client.disconnect();
    }

}
