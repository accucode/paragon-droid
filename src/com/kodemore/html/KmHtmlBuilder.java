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

package com.kodemore.html;

import java.util.List;

import com.kodemore.collection.KmList;
import com.kodemore.string.KmStringBuilder;
import com.kodemore.utility.KmConstantsIF;
import com.kodemore.utility.Kmu;

/**
 * I supply some convenience methods for building simple html.
 */
public class KmHtmlBuilder
    implements CharSequence
{
    //##################################################
    //# static
    //##################################################

    private static final char   CHAR_CR = '\r';
    private static final char   CHAR_LF = '\n';

    private static final String CR      = "" + CHAR_CR;
    private static final String LF      = "" + CHAR_LF;
    private static final String CRLF    = CR + LF;

    //##################################################
    //# variables
    //##################################################

    /**
     * The buffer onto which the content is rendered.
     */
    private KmStringBuilder     _buffer;

    //##################################################
    //# constructor
    //##################################################

    public KmHtmlBuilder()
    {
        _buffer = new KmStringBuilder();
    }

    //##################################################
    //# accessing
    //##################################################

    /**
     * Get the contents of the buffer.
     */
    @Override
    public String toString()
    {
        return formatHtml();
    }

    public String formatHtml()
    {
        return _buffer.toString();
    }

    public int getLength()
    {
        return _buffer.length();
    }

    public void clear()
    {
        _buffer.setLength(0);
    }

    //##################################################
    //# doc type
    //##################################################

    //    public void printDocType401Transitional()
    //    {
    //        printLiteralLine("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"");
    //        printLiteralLine("\"http://www.w3.org/TR/html4/loose.dtd\">");
    //    }

    public void printDocType401Strict()
    {
        printLiteralLine("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"");
        printLiteralLine("\"http://www.w3.org/TR/html4/strict.dtd\">");
    }

    //##################################################
    //# begin & end
    //##################################################

    public void begin(String tag)
    {
        open(tag);
        close();
    }

    public void beginCss(String tag, String css)
    {
        open(tag);
        printAttribute("class", css);
        close();
    }

    public void beginStyle(String tag, String style)
    {
        open(tag);
        printAttribute("style", style);
        close();
    }

    public void open(String tag)
    {
        _buffer.append("<");
        _buffer.append(tag);
    }

    public void close()
    {
        _buffer.append(">");
    }

    public void end(String tag)
    {
        _buffer.append("</");
        _buffer.append(tag);
        _buffer.append(">");
    }

    //##################################################
    //# attributes
    //##################################################

    public void printAttribute(String key)
    {
        _buffer.append(" ");
        _buffer.append(key);
    }

    public void printAttribute(String key, String value)
    {
        if ( value == null )
            return;

        _buffer.append(" ");
        _buffer.append(key);
        _buffer.append("=");
        _buffer.append(KmConstantsIF.QUOTE);
        _buffer.append(escapeAttribute(value));
        _buffer.append(KmConstantsIF.QUOTE);
    }

    private String escapeAttribute(String value)
    {
        return Kmu.escapeHtml(value);
    }

    public void printAttribute(String key, Integer i)
    {
        if ( i == null )
            return;

        printAttribute(key, i + "");
    }

    public void printAttributes(KmStyleBuilder out)
    {
        if ( out == null )
            return;

        printAttribute("style", out.getValue());
    }

    public void printAttributes(KmCssBuilder out)
    {
        if ( out == null )
            return;

        printAttribute("class", out.getValue());
    }

    //##################################################
    //# div
    //##################################################

    public void openDiv()
    {
        open("div");
    }

    public void beginDiv()
    {
        openDiv();
        close();
    }

    public void beginDivCss(String css)
    {
        openDiv();
        printAttribute("class", css);
        close();
    }

    public void beginDiv(KmCssBuilder css)
    {
        openDiv();
        printAttributes(css);
        close();
    }

    public void beginDivId(String id)
    {
        openDiv();
        printAttribute("id", id);
        close();
    }

    public void printDivId(String id)
    {
        beginDivId(id);
        endDiv();
    }

    public void endDiv()
    {
        end("div");
    }

    //##################################################
    //# span
    //##################################################

    public void openSpan()
    {
        open("span");
    }

    public void beginSpanId(String htmlId)
    {
        open("span");
        printAttribute("id", htmlId);
        close();
    }

    public void beginSpanCss(String css)
    {
        beginCss("span", css);
    }

    public void endSpan()
    {
        end("span");
    }

    //##################################################
    //# html
    //##################################################

    public void beginHtml()
    {
        begin("html");
    }

    public void endHtml()
    {
        end("html");
    }

    //##################################################
    //# head
    //##################################################

    public void beginHead()
    {
        begin("head");
    }

    public void endHead()
    {
        end("head");
    }

    public void printTitle(String e)
    {
        beginTitle();
        print(e);
        endTitle();

        printLiteralLine();
    }

    public void beginTitle()
    {
        begin("title");
    }

    public void endTitle()
    {
        end("title");
    }

    /**
     * Print the meta refresh tag.
     * This will cause the page to refresh evern n seconds.
     * This tag should be used inside the <head>.
     */
    public void printMetaRefresh(int seconds)
    {
        printMetaTag("refresh", seconds);
    }

    public void printMetaNoCache()
    {
        printMetaTag("cache-control", "no-cache");
        printMetaTag("pragma", "no-cache");
        printMetaTag("expires", -1);
    }

    public void printMetaTag(String httpEquiv, int content)
    {
        printMetaTag(httpEquiv, content + "");
    }

    public void printMetaTag(String httpEquiv, String content)
    {
        open("meta");
        printAttribute("http-equiv", httpEquiv);
        printAttribute("content", content);
        close();

        printLiteralLine();
    }

    public void printMetaCharset(String charset)
    {
        String content = "text/html;charset=" + charset;

        open("meta");
        printAttribute("http-equiv", "content-type");
        printAttribute("content", content);
        close();

        printLiteralLine();
    }

    public void printMetaCharsetUtf8()
    {
        printMetaCharset("UTF-8");
    }

    //##################################################
    //# body
    //##################################################

    public void beginBody()
    {
        begin("body");
    }

    public void openBody()
    {
        open("body");
    }

    public void endBody()
    {
        end("body");
    }

    //##################################################
    //# form
    //##################################################

    public void beginForm()
    {
        begin("form");
    }

    public void openForm()
    {
        open("form");
    }

    public void endForm()
    {
        end("form");
    }

    //##################################################
    //# header
    //##################################################

    public void printHeader(int level, String s)
    {
        String tag = "h" + level;
        begin(tag);
        print(s);
        end(tag);
    }

    public void printHeader1(String s)
    {
        printHeader(1, s);
    }

    public void printHeader2(String s)
    {
        printHeader(2, s);
    }

    public void printHeader3(String s)
    {
        printHeader(3, s);
    }

    //##################################################
    //# tables
    //##################################################

    public void beginTable()
    {
        begin("table");
    }

    public void openTable()
    {
        open("table");
    }

    public void endTable()
    {
        end("table");
    }

    public void beginTableRow()
    {
        begin("tr");
    }

    public void openTableRow()
    {
        open("tr");
    }

    public void endTableRow()
    {
        end("tr");
    }

    public void beginTableHeader()
    {
        begin("th");
    }

    public void openTableHeader()
    {
        open("th");
    }

    public void endTableHeader()
    {
        end("th");
    }

    public void printTableHeader(Object e)
    {
        beginTableHeader();
        print(e);
        endTableHeader();
    }

    public void beginTableData()
    {
        begin("td");
    }

    public void beginTableDataCss(String css)
    {
        css = "sqlResultBlock " + css;

        openTableData();
        printAttribute("class", css);
        close();
    }

    public void openTableData()
    {
        open("td");
    }

    public void endTableData()
    {
        end("td");
    }

    public void printTableData(Object e)
    {
        beginTableData();
        print(e);
        endTableData();
    }

    //##################################################
    //# print
    //##################################################

    public void print(Object e)
    {
        String s = format(e);
        _buffer.append(s);
    }

    public void println()
    {
        printBreak();
    }

    public void println(Object e)
    {
        print(e);
        printBreak();
    }

    public void printf(String format, Object... args)
    {
        String s = Kmu.format(format, args);
        print(s);
    }

    public void printfln()
    {
        println();
    }

    public void printfln(String format, Object... args)
    {
        String s = Kmu.format(format, args);
        println(s);
    }

    public void printNonBreaking(Object e)
    {
        String s = format(e);
        s = Kmu.replaceAll(s, " ", "&nbsp;");
        _buffer.append(s);
    }

    public void printBold(Object e)
    {
        begin("b");
        print(e);
        end("b");
    }
    
    public void printlnBold(Object e)
    {
        begin("b");
        print(e);
        end("b");
        println();
    }

    public void printItalic(Object e)
    {
        begin("i");
        print(e);
        end("i");
    }

    public void printTextCss(Object e, String css)
    {
        beginSpanCss(css);
        print(e);
        endSpan();
    }

    public void printWithoutBreaks(Object e)
    {
        if ( e == null )
            return;

        String s;
        s = e.toString();
        s = Kmu.replaceAll(s, "&", "&amp;");
        s = Kmu.replaceAll(s, "<", "&lt;");
        s = Kmu.replaceAll(s, ">", "&gt;");
        s = Kmu.replaceAll(s, "\"", "&quot;");

        _buffer.append(s);
    }

    public void printTagValue(String tag, String value)
    {
        begin(tag);
        print(value);
        end(tag);
    }

    public void printNonBreakingSpaces(int n)
    {
        for ( int i = 0; i < n; i++ )
            printNonBreakingSpace();
    }

    public void printNonBreakingSpace()
    {
        printLiteral("&nbsp;");
    }

    public void printCopyright()
    {
        printLiteral("&copy;");
    }

    public void printBreak()
    {
        begin("br");
    }

    public void printBreaks(int n)
    {
        for ( int i = 0; i < n; i++ )
            printBreak();
    }

    public void printPageBreak()
    {
        openDiv();
        printAttribute("style", "page-break-after:always;");
        close();
        endDiv();
    }

    public void printSpace()
    {
        printLiteral(" ");
    }

    public void printRule()
    {
        begin("hr");
    }

    public void printPreformatted(String s)
    {
        begin("pre");
        print(s);
        end("pre");
    }

    //##################################################
    //# convenience
    //##################################################

    public void render(KmHtmlBuilder out)
    {
        printLiteral(out.formatHtml());
    }

    //##################################################
    //# hidden field
    //##################################################

    public void printHiddenField(String name, String value)
    {
        open("input");
        printAttribute("type", "hidden");
        printAttribute("name", name);
        printAttribute("value", value);
        close();
    }

    //##################################################
    //# image
    //##################################################

    public void printImage(String url)
    {
        printImage(url, null, null);
    }

    public void printImage(String url, Integer width, Integer height)
    {
        open("img");
        printAttribute("src", url);
        printAttribute("border", "0");
        printAttribute("width", width);
        printAttribute("height", height);
        printAttribute("alt", "");
        close();
    }

    //##################################################
    //# link
    //##################################################

    public void printLink(String text, String href)
    {
        open("a");
        printAttribute("href", href);
        close();
        _buffer.append(text);
        end("a");
    }

    //##################################################
    //# cdata
    //##################################################

    public void printCdata(String s)
    {
        beginCdata();
        printLiteral(s);
        endCdata();
    }

    public void beginCdata()
    {
        printLiteral("<![CDATA[");
    }

    public void endCdata()
    {
        printLiteral("]]>");
    }

    //##################################################
    //# literals
    //##################################################

    public void printLiteral(char c)
    {
        _buffer.append(c);
    }

    public void printLiteral(String s)
    {
        if ( s != null )
            _buffer.append(s);
    }

    public void printLiteral(StringBuilder e)
    {
        printLiteral(e.toString());
    }

    public void printLiteral(KmHtmlBuilder body)
    {
        printLiteral(body.toString());
    }

    public void printLiteralLine(String msg, Object... args)
    {
        String s = Kmu.format(msg, args);
        printLiteral(s);
        printLiteralLine();
    }

    public void printLiteralLine()
    {
        printLiteral(CRLF);
    }

    public boolean isAtLiteralLineStart()
    {
        int n = _buffer.length();
        if ( n == 0 )
            return true;

        return _buffer.charAt(n - 1) == CHAR_LF;
    }

    public void ensureLiteralLine()
    {
        if ( !isAtLiteralLineStart() )
            printLiteralLine();
    }

    //##################################################
    //# comment
    //##################################################

    public void beginComment()
    {
        _buffer.append("<!--");
    }

    public void endComment()
    {
        _buffer.append("-->");
    }

    public void printComment(String s)
    {
        beginComment();
        printLiteral(s);
        endComment();
    }

    //##################################################
    //# java script
    //##################################################

    public void printScriptLink(String path)
    {
        printScriptLink(path, null);
    }

    public void printScriptLink(String path, String comment)
    {
        open("script");
        printAttribute("type", "text/javascript");
        printAttribute("src", path);
        close();

        if ( Kmu.hasValue(comment) )
        {
            printLiteralLine();
            printLiteralLine("/*");
            KmList<String> v = Kmu.getLines(comment);
            for ( String e : v )
                printLiteralLine(" *" + e);
            printLiteralLine(" */");
        }

        end("script");
        printLiteralLine();
    }

    public void printScriptInline(String e)
    {
        if ( e == null )
            return;

        KmList<String> v;
        v = new KmList<String>();
        v.add(e);

        printScriptsInline(v);
    }

    public void printScriptsInline(List<String> v)
    {
        if ( v == null )
            return;

        Kmu.trimValues(v);
        Kmu.removeEmptyValues(v);

        if ( v.isEmpty() )
            return;

        printLiteralLine();
        open("script");
        printAttribute("type", "text/javascript");
        close();

        printLiteralLine();

        for ( String e : v )
            printLiteralLine(e);

        end("script");
        printLiteralLine();
    }

    //##################################################
    //# style sheet
    //##################################################

    public void printStyleLink(String path)
    {
        printStyleLink(path, null);
    }

    public void printStyleLink(String path, String media)
    {
        open("link");
        printAttribute("rel", "stylesheet");
        printAttribute("type", "text/css");
        printAttribute("href", path);
        printAttribute("media", media);
        close();
        printLiteralLine();
    }

    public void printScreenStyleLink(String path)
    {
        printStyleLink(path, "screen");
    }

    public void printPrintStyleLink(String path)
    {
        printStyleLink(path, "print");
    }

    //##################################################
    //# support
    //##################################################

    private String format(Object e)
    {
        String s = e == null
            ? ""
            : e.toString();

        s = Kmu.replaceAll(s, "&", "&amp;");
        s = Kmu.replaceAll(s, "<", "&lt;");
        s = Kmu.replaceAll(s, ">", "&gt;");
        s = Kmu.replaceAll(s, "\"", "&quot;");

        s = Kmu.replaceAll(s, CRLF, "<br>");
        s = Kmu.replaceAll(s, CR, "<br>");
        s = Kmu.replaceAll(s, LF, "<br>");

        return s;
    }

    //##################################################
    //# char sequence
    //##################################################

    @Override
    public int length()
    {
        return toString().length();
    }

    @Override
    public char charAt(int index)
    {
        return toString().charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end)
    {
        return toString().subSequence(start, end);
    }
}
