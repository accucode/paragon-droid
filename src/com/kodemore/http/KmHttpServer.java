/*
  Copyright (c) 2005-2011 www.kodemore.com

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

package com.kodemore.http;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.kodemore.utility.Kmu;

public abstract class KmHttpServer
{
    //##################################################
    //# constants
    //##################################################

    private static final int DEFAULT_PORT = 80;

    //##################################################
    //# variables
    //##################################################

    private ServerSocket     _serverSocket;
    private Socket           _socket;
    private String           _request;

    //##################################################
    //# run
    //##################################################

    public void run()
    {
        run(DEFAULT_PORT);
    }

    public void run(int port)
    {
        installServerSocket(port);
        while ( true )
            try
            {
                _socket = _serverSocket.accept();
                readRequest();
                writeResponse();
                _socket.close();
            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
            }
    }

    public void installServerSocket(int port)
    {
        try
        {
            _serverSocket = new ServerSocket(port);
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }

    //##################################################
    //# request
    //##################################################

    public void readRequest() throws Exception
    {
        StringBuilder sb = new StringBuilder();
        InputStream is = _socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader r = new BufferedReader(isr);
        while ( true )
        {
            String s = r.readLine();
            if ( s == null )
                break;
            if ( s.length() == 0 )
                break;
            sb.append(s);
            sb.append("\r\n");
        }
        _request = sb.toString();
    }

    public String getRequest()
    {
        return _request;
    }

    public String getRequestHtml()
    {
        String s;
        s = getRequest();
        s = Kmu.replaceAll(s, "\r\n", "<br>");
        s = Kmu.replaceAll(s, "\r", "<br>");
        s = Kmu.replaceAll(s, "\n", "<br>");
        return s;
    }

    //##################################################
    //# response
    //##################################################

    public void writeResponse() throws Exception
    {
        String s = getResponse();

        System.out.println("\n\nKmHttpServer.writeResponse");
        System.out.println(s);

        OutputStream os = _socket.getOutputStream();
        BufferedOutputStream out = new BufferedOutputStream(os);
        writeln(out, "HTTP/1.0 200 OK");
        writeln(out, "Content-Type: " + getContentType());
        writeln(out, "Content-Length: " + s.length());
        writeln(out, "");
        writeln(out, s);
        out.flush();
    }

    public abstract String getContentType();

    public abstract String getResponse() throws Exception;

    public void writeln(OutputStream os, String s) throws Exception
    {
        byte[] arr = s.getBytes();
        os.write(arr);
        os.write('\r');
        os.write('\n');
    }

    //##################################################
    //# main
    //##################################################

    public static void main(String args[])
    {
        KmHttpServer server = new KmHttpServer()
        {
            @Override
            public String getContentType()
            {
                return "text/plain";
            }

            @Override
            public String getResponse() throws Exception
            {
                return getRequest();
            }
        };
        server.run(8081);
    }

}
