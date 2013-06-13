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

package com.kodemore.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * I am a collection of various static methods that do not adhere
 * to the JDK 1.5 type checking typically introduced by generics.
 * The intent is to collect the "few" places that need to remain
 * checked into a single source file that other can delegate to.
 * This should allow the application to get a clean compile with
 * no warnings anywhere except here.
 */
@SuppressWarnings(
{
    "rawtypes",
    "unchecked"
})
public class KmUnchecked
{
    public static int compare(Object o1, Object o2)
    {
        Comparable c1 = (Comparable)o1;
        Comparable c2 = (Comparable)o2;

        if ( c1 == null && c2 == null )
            return 0;

        if ( c1 == null )
            return 1;

        if ( c2 == null )
            return -1;

        return c1.compareTo(c2);
    }

    public static int compare(Comparator c, Object o1, Object o2)
    {
        return c.compare(o1, o2);
    }

    public static void sort(List v)
    {
        Collections.sort(v);
    }

    public static void sort(List v, Comparator c)
    {
        Collections.sort(v, c);
    }

    public static void addAll(Collection target, Collection children)
    {
        target.addAll(children);
    }

    public static void addAll(Collection target, Iterator i)
    {
        while ( i.hasNext() )
            target.add(i.next());
    }

    public static void addAll(Collection target, Enumeration e)
    {
        while ( e.hasMoreElements() )
            target.add(e.nextElement());
    }

    public static void addAll(Collection target, Object[] arr)
    {
        int n = arr.length;
        for ( int i = 0; i < n; i++ )
            target.add(arr[i]);
    }

    public static <T extends Serializable> T getSerializedCopy(T e)
    {
        try
        {
            if ( e == null )
                return null;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(e);
            oos.flush();
            oos.close();
            byte[] ba = baos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(ba);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object x = ois.readObject();
            ois.close();
            return (T)x;
        }
        catch ( Exception ex )
        {
            throw Kmu.toRuntime(ex);
        }
    }
}
