/*
  Copyright (c) 2005-2012 Wyatt Love

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

package com.kodemore.collection;

import java.util.LinkedHashMap;

public class KmOrderedMap<K, V>
    extends LinkedHashMap<K,V>
{
    //##################################################
    //# testing
    //##################################################

    public boolean isNotEmpty()
    {
        return !isEmpty();
    }

    //##################################################
    //# accessing
    //##################################################

    public KmList<K> getKeys()
    {
        KmList<K> v;
        v = new KmList<K>();
        v.addAll(keySet());
        return v;
    }

    public KmList<V> getValues()
    {
        KmList<V> v;
        v = new KmList<V>();
        v.addAll(values());
        return v;
    }

    public K getKeyAt(int index)
    {
        return getKeys().get(index);
    }

    public V getValueAt(int index)
    {
        return getValues().get(index);
    }

    //##################################################
    //# conversion
    //##################################################

    public KmList<KmTuple<K,V>> toList()
    {
        KmList<KmTuple<K,V>> v = new KmList<KmTuple<K,V>>();

        for ( Entry<K,V> e : entrySet() )
            v.add(KmTuple.create(e));

        return v;
    }

    public KmList<KmUntypedTuple> toUntypedList()
    {
        KmList<KmUntypedTuple> v = new KmList<KmUntypedTuple>();

        for ( Entry<K,V> e : entrySet() )
            v.add(new KmUntypedTuple(e.getKey(), e.getValue()));

        return v;
    }
}
