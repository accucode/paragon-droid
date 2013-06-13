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

package com.kodemore.stf.test;

import com.kodemore.stf.KmStfElement;
import com.kodemore.stf.KmStfParser;
import com.kodemore.utility.Kmu;

/**
 * I am used to test the parser.  
 */
public class KmStfTest
{
    //##################################################
    //# main
    //##################################################

    public static void main(String... args)
    {
        new KmStfTest().run();
    }

    //##################################################
    //# run
    //##################################################

    private void run()
    {
        String file = "sample.stf";
        String source = readSampleFile(file);

        System.out.println("Parsing...");

        KmStfParser p;
        p = new KmStfParser();
        p.addPrefixShortcut("id", "#");
        p.addDefaultShortcut("class").setAppendMode();

        p.parseSource(source);

        System.out.println("Parsing... ok.");
        System.out.println();

        KmStfElement r;
        r = p.getRoot();
        r.printTree();
    }

    private String readSampleFile(String file)
    {
        String pkg;
        pkg = Kmu.getPackageName(this);
        pkg = Kmu.replaceAll(pkg, ".", "/");

        String res = Kmu.format("%s/%s", pkg, file);

        return Kmu.readClassString(res);
    }

}
