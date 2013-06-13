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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kodemore.utility.KmFilterIF;
import com.kodemore.utility.KmRandom;
import com.kodemore.utility.Kmu;

/**
 * Extend the list wrapper, providing a variety of convenience methods.
 */
public class KmList<E>
    extends KmListWrapper<E>
    implements Serializable
{
    //##################################################
    //# constructor
    //##################################################

    public KmList()
    {
        super();
    }

    public KmList(List<E> v)
    {
        super(v);
    }

    //##################################################
    //# add
    //##################################################

    public boolean addAll(E... arr)
    {
        boolean changed = false;

        for ( E e : arr )
            if ( add(e) )
                changed = true;

        return changed;
    }

    public boolean addAll(Iterator<E> i)
    {
        boolean changed = false;

        while ( i.hasNext() )
            if ( add(i.next()) )
                changed = true;

        return changed;
    }

    public boolean addNonNull(E e)
    {
        if ( e == null )
            return false;

        return add(e);
    }

    public boolean addAllNonNull(List<E> v)
    {
        boolean changed = false;
        for ( E e : v )
            if ( addNonNull(e) )
                changed = true;

        return changed;
    }

    public boolean addDistinct(E e)
    {
        if ( contains(e) )
            return false;

        return add(e);
    }

    public boolean addAllDistinct(List<E> v)
    {
        boolean changed = false;
        for ( E e : v )
            if ( addDistinct(e) )
                changed = true;
        return changed;
    }

    public boolean addNonNullDistinct(E e)
    {
        if ( e == null )
            return false;

        if ( contains(e) )
            return false;

        return add(e);
    }

    public void addFirst(E object)
    {
        add(0, object);
    }

    //##################################################
    //# remove
    //##################################################

    public E removeFirst()
    {
        return remove(0);
    }

    public E removeFirstSafe()
    {
        if ( isEmpty() )
            return null;

        return removeFirst();
    }

    public E removeLast()
    {
        return remove(size() - 1);
    }

    public E removeLastSafe()
    {
        if ( isEmpty() )
            return null;

        return removeLast();
    }

    public void removeDuplicates()
    {
        KmList<E> v = getShallowCopy();
        Set<E> set = new HashSet<E>();

        clear();

        for ( E e : v )
            if ( set.add(e) )
                add(e);
    }

    public void removeNulls()
    {
        KmList<E> v = getShallowCopy();

        clear();
        addAllNonNull(v);
    }

    //##################################################
    //# replace
    //##################################################

    public boolean replace(E e)
    {
        int i = indexOf(e);
        if ( i < 0 )
            return false;

        set(i, e);
        return true;
    }

    public void replaceAll(Collection<E> v)
    {
        clear();
        addAll(v);
    }

    //##################################################
    //# testing
    //##################################################

    public boolean isNotEmpty()
    {
        return !isEmpty();
    }

    public boolean isSingleton()
    {
        return size() == 1;
    }

    public boolean hasMultiple()
    {
        return size() > 1;
    }

    public boolean isValidIndex(int i)
    {
        return i >= 0 && i < size();
    }

    public boolean isOutOfBounds(int i)
    {
        return !isValidIndex(i);
    }

    //##################################################
    //# sort
    //##################################################

    @SuppressWarnings(
    {
        "rawtypes",
        "unchecked"
    })
    public void sort()
    {
        Collections.sort((List)this);
    }

    public void sortOn(Comparator<E> e)
    {
        Collections.sort(this, e);
    }

    public void randomize()
    {
        int n = size();
        for ( int i = 0; i < n; i++ )
        {
            int j = KmRandom.getInteger(size());
            swap(i, j);
        }
    }

    //##################################################
    //# format
    //##################################################

    /**
     * Concatenate the elements, separated by a single comma.
     */
    public String format()
    {
        return Kmu.format(this);
    }

    /**
     * Concatenate the elements, separated by a new line character.
     */
    public String formatLines()
    {
        return Kmu.formatLines(this);
    }

    /**
     * Concatenate the elements, separated by a delimiter.
     */
    public String format(String delimiter)
    {
        return Kmu.format(this, delimiter);
    }

    //##################################################
    //# copy
    //##################################################

    public KmList<E> getShallowCopy()
    {
        KmList<E> v;
        v = new KmList<E>();
        v.addAll(this);
        return v;
    }

    public KmList<E> getSerializedCopy()
    {
        return Kmu.getSerializedCopy(this);
    }

    public KmList<E> copyAndRetain(KmFilterIF<E> f)
    {
        KmList<E> v;
        v = getShallowCopy();
        v.retain(f);
        return v;
    }

    public KmList<E> copyAndReject(KmFilterIF<E> f)
    {
        KmList<E> v;
        v = getShallowCopy();
        v.reject(f);
        return v;
    }

    public KmList<E> getReverse()
    {
        KmList<E> v;
        v = getShallowCopy();
        v.reverse();
        return v;
    }

    //##################################################
    //# get
    //##################################################

    public E getSafe(int i)
    {
        if ( i < 0 )
            return null;

        if ( i >= size() )
            return null;

        return get(i);
    }

    //##################################################
    //# get random
    //##################################################

    public KmList<E> getRandomized()
    {
        KmList<E> v;
        v = getShallowCopy();
        v.randomize();
        return v;
    }

    public E getRandom()
    {
        int i = KmRandom.getInteger(size());
        return get(i);
    }

    public E getRandomSafe()
    {
        int i = KmRandom.getInteger(size());
        return getSafe(i);
    }

    //##################################################
    //# get first
    //##################################################

    public E getFirst()
    {
        return get(0);
    }

    public E getFirstSafe()
    {
        return getSafe(0);
    }

    public KmList<E> getFirst(int n)
    {
        KmList<E> v;
        v = new KmList<E>();
        v.addAll(subList(0, n));
        return v;
    }

    public KmList<E> getFirstSafe(int n)
    {
        if ( n >= size() )
            return getShallowCopy();

        return getFirst(n);
    }

    //##################################################
    //# get last
    //##################################################

    public E getLast()
    {
        return get(size() - 1);
    }

    public E getLastSafe()
    {
        return getSafe(size() - 1);
    }

    public KmList<E> getLast(int n)
    {
        int size = size();

        KmList<E> v;
        v = new KmList<E>();
        v.addAll(subList(size - n, size));
        return v;
    }

    public KmList<E> getLastSafe(int n)
    {
        if ( n >= size() )
            return getShallowCopy();

        return getLast(n);
    }

    //##################################################
    //# filter
    //##################################################

    public void retain(KmFilterIF<E> f)
    {
        if ( f == null )
            return;

        Iterator<E> i = iterator();
        while ( i.hasNext() )
            if ( f.excludes(i.next()) )
                i.remove();
    }

    public void reject(KmFilterIF<E> f)
    {
        if ( f == null )
            return;

        Iterator<E> i = iterator();
        while ( i.hasNext() )
            if ( f.includes(i.next()) )
                i.remove();
    }

    //##################################################
    //# array
    //##################################################

    public String[] toStringArray()
    {
        int n = size();
        String[] arr = new String[n];

        for ( int i = 0; i < n; i++ )
            arr[i] = Kmu.toStringSafe(get(i));

        return arr;
    }

    public ArrayList<E> toArrayList()
    {
        ArrayList<E> v;
        v = new ArrayList<E>();
        v.addAll(this);
        return v;
    }

    public KmList<Object> toObjectList()
    {
        KmList<Object> v;
        v = new KmList<Object>();
        v.addAll(this);
        return v;
    }

    //##################################################
    //# mutate
    //##################################################

    public void reverse()
    {
        Collections.reverse(this);
    }

    public void moveToEnd(E e)
    {
        if ( remove(e) )
            add(e);
    }

    public void moveToTop(E e)
    {
        if ( remove(e) )
            addFirst(e);
    }

    public void swap(int i, int j)
    {
        if ( i == j )
            return;

        E e = get(i);
        set(i, get(j));
        set(j, e);
    }
}
