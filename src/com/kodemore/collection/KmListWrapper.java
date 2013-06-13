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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A wrapper for the list interface.  This provides a way for us
 * to reliably adhere to the list interface, while providing reliable
 * hooks to monitor the addition and removal of elements.
 * 
 * Also, by implementing a delegating wrapper rather than a subclass,
 * we can effectively extend the behavior of any other list without
 * need to make a shallow copy.
 * 
 * In practice, this class provides only the minimal methods required
 * to implement the List interface.  The KmList class is used to 
 * provide the various extensions and convenience methods.
 */
public class KmListWrapper<E>
    implements List<E>
{
    //##################################################
    //# variables
    //##################################################

    /**
     * The inner list (aka delegate).
     * This is protected so that subclasses can implement
     * a more interesting version of getInner, e.g.: lazy
     * loading.
     */
    protected List<E> _inner;

    //##################################################
    //# constructor
    //##################################################

    public KmListWrapper()
    {
        _inner = getDefaultInner();
    }

    /**
     * Create a new list containing the contents of the parameter.
     * Changes to the new list do NOT affect the original. 
     */
    public KmListWrapper(List<E> v)
    {
        _inner = new ArrayList<E>(v);
    }

    //##################################################
    //# inner
    //##################################################

    /**
     * Get the initial value of the inner delegate, if a list
     * is not provide to the constructor.  I provide a hook for 
     * subclassing.
     */
    protected List<E> getDefaultInner()
    {
        return new ArrayList<E>();
    }

    /**
     * Get the inner list.  All access should delegate to this method.
     * This provides subclasses an opportunity to implement lazy loading
     * of the internal list.
     */
    protected List<E> getInner()
    {
        return _inner;
    }

    //##################################################
    //# add
    //##################################################

    @Override
    public void add(int index, E e)
    {
        handleAdd(e);
        getInner().add(index, e);
    }

    @Override
    public boolean add(E e)
    {
        handleAdd(e);
        return getInner().add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> v)
    {
        for ( E e : v )
            handleAdd(e);

        return getInner().addAll(v);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> v)
    {
        for ( E e : v )
            handleAdd(e);

        return getInner().addAll(index, v);
    }

    //##################################################
    //# remove
    //##################################################

    @Override
    public void clear()
    {
        for ( E e : this )
            handleRemove(e);

        getInner().clear();
    }

    @Override
    public E remove(int index)
    {
        handleRemove(get(index));

        return getInner().remove(index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object e)
    {
        handleRemove((E)e);

        return getInner().remove(e);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeAll(Collection<?> v)
    {
        for ( Object e : v )
            handleRemove((E)e);

        return getInner().removeAll(v);
    }

    @Override
    public boolean retainAll(Collection<?> v)
    {
        for ( E e : this )
            if ( !v.contains(e) )
                handleRemove(e);

        return getInner().retainAll(v);
    }

    //##################################################
    //# set
    //##################################################

    @Override
    public E set(int index, E e)
    {
        handleRemove(get(index));
        handleAdd(e);

        return getInner().set(index, e);
    }

    //##################################################
    //# testing
    //##################################################

    @Override
    public boolean contains(Object e)
    {
        return getInner().contains(e);
    }

    @Override
    public boolean containsAll(Collection<?> v)
    {
        return getInner().containsAll(v);
    }

    @Override
    public boolean isEmpty()
    {
        return getInner().isEmpty();
    }

    //##################################################
    //# comparing
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        return getInner().equals(e);
    }

    @Override
    public int hashCode()
    {
        return getInner().hashCode();
    }

    //##################################################
    //# accessing
    //##################################################

    @Override
    public E get(int index)
    {
        return getInner().get(index);
    }

    @Override
    public int indexOf(Object e)
    {
        return getInner().indexOf(e);
    }

    @Override
    public Iterator<E> iterator()
    {
        return getInner().iterator();
    }

    @Override
    public int lastIndexOf(Object e)
    {
        return getInner().lastIndexOf(e);
    }

    @Override
    public ListIterator<E> listIterator()
    {
        return getInner().listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index)
    {
        return getInner().listIterator(index);
    }

    @Override
    public int size()
    {
        return getInner().size();
    }

    @Override
    public List<E> subList(int start, int end)
    {
        return getInner().subList(start, end);
    }

    @Override
    public Object[] toArray()
    {
        return getInner().toArray();
    }

    @Override
    public <T> T[] toArray(T[] array)
    {
        return getInner().toArray(array);
    }

    //##################################################
    //# notification hooks
    //##################################################

    /**
     * Provide a hook for subclasses to perform an action
     * when an element is being added.  This method is called
     * before delegating to the inner list. 
     */
    protected void handleAdd(E e)
    {
        // subclass override
    }

    /**
     * Provide a hook for subclasses to take action when an element
     * is being removed. This method is called before delegating to 
     * the inner list.  This is called for elements that the client
     * is trying to remove, which means in some cases it may be called
     * on elements that are not actually contained in the list.
     */
    protected void handleRemove(E e)
    {
        // subclass override
    }

}
