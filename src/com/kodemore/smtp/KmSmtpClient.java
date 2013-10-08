package com.kodemore.smtp;

import java.io.Writer;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.AuthenticatingSMTPClient.AUTH_METHOD;
import org.apache.commons.net.smtp.SMTPReply;

import com.kodemore.collection.KmList;
import com.kodemore.utility.KmLog;
import com.kodemore.utility.Kmu;

public class KmSmtpClient
{
    //##################################################
    //# constants
    //##################################################

    /**
     * The default port for smtp.
     */
    private static final int         PORT_DEFAULT = 25;

    /**
     * A common alternative port for Message Submission Agent (MSA).
     */
    private static final int         PORT_MSA     = 587;

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
     * The inner client.  This is the bridge to the Apache commons functionality.
     */
    private AuthenticatingSMTPClient _client;

    /**
     * The exception that was caught during send().
     */
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
     * Returns true if the message was successfully sent.
     */
    private boolean                  _sent;

    /**
     * The message to be sent.
     */
    private KmSmtpMessage            _message;

    //##################################################
    //# constructors
    //##################################################

    public KmSmtpClient()
    {
        setPortDefault();
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
    //# message
    //##################################################

    public void setMessage(KmSmtpMessage e)
    {
        _message = e;
    }

    //##################################################
    //# reply code
    //##################################################

    public int getReplyCode()
    {
        return _client.getReplyCode();
    }

    private boolean hasPositiveCompletionReplyCode()
    {
        return SMTPReply.isPositiveCompletion(getReplyCode());
    }

    //##################################################
    //# sent
    //##################################################

    public boolean getSent()
    {
        return _sent;
    }

    public boolean isSent()
    {
        return getSent();
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

    public void setAuthenticationLogin()
    {
        setAuthenticationMethod(AUTH_METHOD.LOGIN);
    }

    public void setAuthenticationPlain()
    {
        setAuthenticationMethod(AUTH_METHOD.PLAIN);
    }

    public void setAuthenticationCramMd5()
    {
        setAuthenticationMethod(AUTH_METHOD.CRAM_MD5);
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
            _sent = false;
            _exception = null;
            _send();
            _sent = true;
        }
        catch ( Exception ex )
        {
            _exception = ex;
        }
        return _sent;
    }

    //##################################################
    //# private
    //##################################################

    private void _send() throws Exception
    {
        try
        {
            _connect();
            _login();
            _tls();
            _authenticate();
            _setSender();
            _addRecipients();
            _composeMessage();
            _complete();
        }
        finally
        {
            _logout();
            _disconnect();
        }
    }

    private void _connect() throws Exception
    {
        _client = new AuthenticatingSMTPClient();
        _client.connect(_host, _port);
    }

    private void _login() throws Exception
    {
        boolean ok = _client.login();

        if ( !ok )
            fatal("Unable to login, reply code (%s).", getReplyCode());
    }

    private void _tls() throws Exception
    {
        if ( !isTlsEnabled() )
            return;

        boolean ok = _client.execTLS();
        if ( !ok )
            fatal("Unable to execute TLS, reply code (%s).", getReplyCode());
    }

    private void _authenticate() throws Exception
    {
        if ( hasAuthenticationMethod() )
            _client.auth(_authenticationMethod, _user, _password);

        if ( !hasPositiveCompletionReplyCode() )
            fatal("Unable to authenticate, reply code (%s).", getReplyCode());
    }

    private void _setSender() throws Exception
    {
        String e = _message.getFrom();

        if ( e == null )
            fatal("Missing sender.");

        boolean ok = _client.setSender(e);
        if ( !ok )
            fatal("Unable to set sender, reply code (%s).", getReplyCode());
    }

    private void _addRecipients() throws Exception
    {
        KmList<KmSmtpRecipient> v = _message.getRecipients();

        if ( v.isEmpty() )
            fatal("Missing recipients.");

        for ( KmSmtpRecipient e : v )
        {
            boolean ok = _client.addRecipient(e.getAddress());
            if ( !ok )
                fatal("Unable to add recipient, reply code (%s).", getReplyCode());
        }
    }

    private void _composeMessage() throws Exception
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

    private void _complete() throws Exception
    {
        boolean ok = _client.completePendingCommand();
        if ( !ok )
            fatal("Unable to complete pending command, reply code (%s).", getReplyCode());
    }

    private void _logout()
    {
        try
        {
            if ( _client != null )
                _client.logout();
        }
        catch ( Exception ex )
        {
            KmLog.error(ex);
        }
    }

    private void _disconnect()
    {
        try
        {
            if ( _client != null )
                _client.disconnect();
        }
        catch ( Exception ex )
        {
            KmLog.error(ex);
        }
    }

    //##################################################
    //# utility
    //##################################################

    private void fatal(String msg, Object... args)
    {
        Kmu.fatal(msg, args);
    }
}
