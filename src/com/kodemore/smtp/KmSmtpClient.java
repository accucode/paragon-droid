package com.kodemore.smtp;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.AuthenticatingSMTPClient.AUTH_METHOD;
import org.apache.commons.net.smtp.SMTPReply;

import com.kodemore.utility.KmLog;
import com.kodemore.utility.Kmu;

public class KmSmtpClient
{
    //##################################################
    //# constants
    //##################################################

    private static final int         SMTP_MESSAGE_SENT = 250;

    /**
     * The default port for smtp.
     */
    private static final int         PORT_DEFAULT      = 25;

    /**
     * A common alternative port for Message Submission Agent (MSA).
     */
    private static final int         PORT_MSA          = 587;

    //##################################################
    //# variables
    //##################################################

    /**
     * The host machine.
     */
    private String                   _host;

    /**
     * The host port. Usually 25 or 587.
     */
    private int                      _port;

    /**
     * The user name if required for login.
     */
    private String                   _user;

    /**
     * The password if required for login.
     */
    private String                   _password;

    /**
     * todo_wyatt: review all names
     */
    private AuthenticatingSMTPClient _client;
    private Integer                  _replyCode;
    private Exception                _exception;

    /**
     * The authentication method, if any.
     */
    private AUTH_METHOD              _authenticationMethod;

    /**
     * Transport Layer Security; enabled
     */
    private boolean                  _tls;

    /**
     * Returns whether a message was sent successfully or not.
     * Returns true if sent, false if not, and null if there was no
     * attempt to send the message.
     */
    private Boolean                  _sent;
    private KmSmtpAbstractMessage    _message;

    //##################################################
    //# constructors
    //##################################################

    public KmSmtpClient()
    {
        setPort(PORT_DEFAULT);
    }

    //##################################################
    //# host and port
    //##################################################

    public String getHost()
    {
        return _host;
    }

    public void setHost(String e)
    {
        _host = e;
    }

    public int getPort()
    {
        return _port;
    }

    public void setPort(int e)
    {
        _port = e;
    }

    public void setPortMsa()
    {
        setPort(PORT_MSA);
    }

    public void setPortDefault()
    {
        setPort(PORT_DEFAULT);
    }

    //##################################################
    //# user and password
    //##################################################

    public String getUser()
    {
        return _user;
    }

    public void setUser(String e)
    {
        _user = e;
    }

    public String getPassword()
    {
        return _password;
    }

    public void setPassword(String e)
    {
        _password = e;
    }

    //##################################################
    //# accessing (response)
    //##################################################

    /** 
     * Returns standard SMTP reply code, or null if there isn't one.
     * This will return responses for connection events only.
     */
    public int getReplyCode()
    {
        return _replyCode;
    }

    public boolean hasReplyCode()
    {
        return _replyCode != null;
    }

    //##################################################
    //# sent
    //##################################################

    public Boolean getSent()
    {
        return _sent;
    }

    public boolean isSent()
    {
        return Kmu.isTrue(getSent());
    }

    //##################################################
    //# exception
    //##################################################

    public Exception getException()
    {
        return _exception;
    }

    public boolean hasException()
    {
        return _exception != null;
    }

    public String formatException()
    {
        return Kmu.formatMessage(getException());
    }

    public void setMessage(KmSmtpAbstractMessage e)
    {
        _message = e;
        _sent = null;
    }

    //##################################################
    //# authentication method
    //##################################################

    public AUTH_METHOD getAuthenticationMethod()
    {
        return _authenticationMethod;
    }

    public void setAuthenticationMethod(AUTH_METHOD e)
    {
        _authenticationMethod = e;
    }

    public boolean hasAuthenticationMethod()
    {
        return _authenticationMethod != null;
    }

    public boolean isAuthenticating()
    {
        return hasAuthenticationMethod();
    }

    /**
     * This works with Sendgrid server-side authentication by default.
     */
    public void setAuthenticationLogin()
    {
        setAuthenticationMethod(AUTH_METHOD.LOGIN);
    }

    //##################################################
    //# transport layer security (TLS)
    //##################################################

    public void setTls(boolean e)
    {
        _tls = e;
    }

    public void setTls()
    {
        setTls(true);
    }

    public boolean getTls()
    {
        return _tls;
    }

    public boolean isTlsEnabled()
    {
        return _tls;
    }

    //##################################################
    //# send
    //##################################################

    public boolean send()
    {
        try
        {
            _send();
            _sent = true;
        }
        catch ( Exception ex )
        {
            _exception = ex;
            _sent = false;
        }
        return _sent;
    }

    //##################################################
    //# private
    //##################################################

    private boolean _send() throws Exception
    {
        try
        {
            _connect();
            _compose();
            return _execute();
        }
        finally
        {
            _disconnectSafely();
        }
    }

    /**
     * fixme_wyatt: wip
     */
    private void _connect() throws Exception
    {
        _client = new AuthenticatingSMTPClient();
        _client.connect(_host, _port);
        _client.login();

        if ( isTlsEnabled() )
            _client.execTLS();

        if ( hasAuthenticationMethod() )
            _client.auth(_authenticationMethod, _user, _password);

        _replyCode = _client.getReplyCode();

        if ( !SMTPReply.isPositiveCompletion(getReplyCode()) )
            throw new IOException("Connection cannot be resolved.");
    }

    private void _compose() throws IOException
    {
        String from = _message.getFrom();

        if ( from == null )
            return;

        if ( !_message.hasRecipients() )
            return;

        _client.setSender(from);

        for ( KmSmtpRecipient e : _message.getRecipients() )
            _client.addRecipient(e.getAddress());

        _writeMessage();
    }

    private void _writeMessage() throws IOException
    {
        Writer out = null;
        try
        {
            out = _client.sendMessageData();
            _message.composeOn(out);
        }
        finally
        {
            Kmu.closeSafely(out);
        }
    }

    private boolean _execute() throws IOException
    {
        _client.completePendingCommand();

        return _client.getReplyCode() == SMTP_MESSAGE_SENT;
    }

    private void _disconnectSafely()
    {
        try
        {
            if ( _client == null )
                return;

            _client.logout();
            _client.disconnect();
        }
        catch ( Exception ex )
        {
            KmLog.error(ex);
        }
    }
}
