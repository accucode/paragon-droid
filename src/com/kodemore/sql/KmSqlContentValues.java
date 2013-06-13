package com.kodemore.sql;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import android.content.ContentValues;
import android.os.Parcel;

import com.kodemore.collection.KmList;
import com.kodemore.database.KmSqlValuesIF;
import com.kodemore.time.KmDate;
import com.kodemore.time.KmTimestamp;
import com.kodemore.types.KmMoney;
import com.kodemore.utility.Kmu;

/**
 * I provide an alternative to the default ContentValues
 * class in order to provide support for additional data types.
 */
public class KmSqlContentValues
    implements KmSqlValuesIF
{
    //##################################################
    //# variables
    //##################################################

    private ContentValues _inner;

    //##################################################
    //# constructor
    //##################################################

    public KmSqlContentValues()
    {
        _inner = new ContentValues();
    }

    //##################################################
    //# abstract accessing (delegates)
    //##################################################

    public void clear()
    {
        _inner.clear();
    }

    public boolean containsKey(String key)
    {
        return _inner.containsKey(key);
    }

    public int describeContents()
    {
        return _inner.describeContents();
    }

    public void remove(String key)
    {
        _inner.remove(key);
    }

    public int size()
    {
        return _inner.size();
    }

    @Override
    public KmList<String> getKeys()
    {
        KmList<String> v;
        v = new KmList<String>();
        v.addAll(keySet());
        return v;
    }

    public KmList<String> getSortedKeys()
    {
        KmList<String> v;
        v = getKeys();
        v.sort();
        return v;
    }

    public Set<String> keySet()
    {
        // Native keySet method is not available until API 11,
        // so we implement our own using the valueSet method.

        Set<String> keys = new HashSet<String>();

        Set<Entry<String,Object>> values = valueSet();
        for ( Entry<String,Object> e : values )
            keys.add(e.getKey());

        return keys;
    }

    public Set<Entry<String,Object>> valueSet()
    {
        return _inner.valueSet();
    }

    public void writeToParcel(Parcel parcel, int flags)
    {
        _inner.writeToParcel(parcel, flags);
    }

    //##################################################
    //# compare (delgates)
    //##################################################

    @Override
    public boolean equals(Object e)
    {
        return _inner.equals(e);
    }

    @Override
    public int hashCode()
    {
        return _inner.hashCode();
    }

    //##################################################
    //# testing
    //##################################################

    /**
     * Test is I am effectively identical to some other contentValues.
     * Return true if we both have the same keys and values.
     * 
     * Note that although do delegate to the _inner.equals() above, 
     * Android does not define the behavior of ContentValues.equals 
     * so we cannot rely on it for this purpose.
     */
    public boolean isSame(KmSqlContentValues b)
    {
        KmSqlContentValues a = this;
        KmList<String> aKeys = a.getKeys();

        if ( a.size() != b.size() )
            return false;

        for ( String key : aKeys )
        {
            if ( !b.containsKey(key) )
                return false;

            String aValue = a.getStringAt(key);
            String bValue = b.getStringAt(key);

            if ( Kmu.isNotEqual(aValue, bValue) )
                return false;
        }

        return true;
    }

    //##################################################
    //# null
    //##################################################

    public void putNull(String key)
    {
        _inner.putNull(key);
    }

    public void putNull(KmSqlColumnIF key)
    {
        putNull(key.getName());
    }

    public void putAll(ContentValues other)
    {
        _inner.putAll(other);
    }

    //##################################################
    //# string
    //##################################################

    @Override
    public String getStringAt(String key)
    {
        return _inner.getAsString(key);
    }

    @Override
    public String getStringAt(KmSqlColumnIF key)
    {
        return getStringAt(key.getName());
    }

    public void put(String key, String value)
    {
        _inner.put(key, value);
    }

    public void put(KmSqlColumnIF key, String value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# integer
    //##################################################

    @Override
    public Integer getIntegerAt(String key)
    {
        return _inner.getAsInteger(key);
    }

    @Override
    public Integer getIntegerAt(KmSqlColumnIF key)
    {
        return getIntegerAt(key.getName());
    }

    public void put(String key, Integer value)
    {
        _inner.put(key, value);
    }

    public void put(KmSqlColumnIF key, Integer value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# long
    //##################################################

    @Override
    public Long getLongAt(String key)
    {
        return _inner.getAsLong(key);
    }

    @Override
    public Long getLongAt(KmSqlColumnIF key)
    {
        return getLongAt(key.getName());
    }

    public void put(String key, Long value)
    {
        _inner.put(key, value);
    }

    public void put(KmSqlColumnIF key, Long value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# short
    //##################################################

    public Short getShortAt(String key)
    {
        return _inner.getAsShort(key);
    }

    public Short getShortAt(KmSqlColumnIF key)
    {
        return getShortAt(key.getName());
    }

    public void put(String key, Short value)
    {
        _inner.put(key, value);
    }

    public void put(KmSqlColumnIF key, Short value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# byte
    //##################################################

    public Byte getByteAt(String key)
    {
        return _inner.getAsByte(key);
    }

    public Byte getByteAt(KmSqlColumnIF key)
    {
        return getByteAt(key.getName());
    }

    public void put(String key, Byte value)
    {
        _inner.put(key, value);
    }

    public void put(KmSqlColumnIF key, Byte value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# byte array
    //##################################################

    public byte[] getByteArrayAt(String key)
    {
        return _inner.getAsByteArray(key);
    }

    public byte[] getByteArrayAt(KmSqlColumnIF key)
    {
        return getByteArrayAt(key.getName());
    }

    public void put(String key, byte[] value)
    {
        _inner.put(key, value);
    }

    public void put(KmSqlColumnIF key, byte[] value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# double
    //##################################################

    @Override
    public Double getDoubleAt(String key)
    {
        return _inner.getAsDouble(key);
    }

    @Override
    public Double getDoubleAt(KmSqlColumnIF key)
    {
        return getDoubleAt(key.getName());
    }

    public void put(String key, Double value)
    {
        _inner.put(key, value);
    }

    public void put(KmSqlColumnIF key, Double value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# float
    //##################################################

    public Float getFloatAt(String key)
    {
        return _inner.getAsFloat(key);
    }

    public Float getFloatAt(KmSqlColumnIF key)
    {
        return getFloatAt(key.getName());
    }

    public void put(String key, Float value)
    {
        _inner.put(key, value);
    }

    public void put(KmSqlColumnIF key, Float value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# boolean
    //##################################################

    @Override
    public Boolean getBooleanAt(String key)
    {
        return _inner.getAsBoolean(key);
    }

    @Override
    public Boolean getBooleanAt(KmSqlColumnIF key)
    {
        return getBooleanAt(key.getName());
    }

    public void put(String key, Boolean value)
    {
        _inner.put(key, value);
    }

    public void put(KmSqlColumnIF key, Boolean value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# date
    //##################################################

    @Override
    public KmDate getDateAt(String key)
    {
        Long ms = getLongAt(key);
        return KmSqlUtility.msToDate(ms);
    }

    @Override
    public KmDate getDateAt(KmSqlColumnIF key)
    {
        return getDateAt(key.getName());
    }

    public void put(String key, KmDate value)
    {
        Long ms = KmSqlUtility.dateToMs(value);
        put(key, ms);
    }

    public void put(KmSqlColumnIF key, KmDate value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# timestamp
    //##################################################

    @Override
    public KmTimestamp getTimestampAt(String key)
    {
        Long ms = getLongAt(key);
        return KmSqlUtility.msToTimestamp(ms);
    }

    @Override
    public KmTimestamp getTimestampAt(KmSqlColumnIF key)
    {
        return getTimestampAt(key.getName());
    }

    public void put(String key, KmTimestamp value)
    {
        Long ms = KmSqlUtility.timestampToMs(value);
        put(key, ms);
    }

    public void put(KmSqlColumnIF key, KmTimestamp value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# money
    //##################################################

    @Override
    public KmMoney getMoneyAt(String key)
    {
        Long cents = getLongAt(key);
        return KmMoney.fromCents(cents);
    }

    @Override
    public KmMoney getMoneyAt(KmSqlColumnIF key)
    {
        return getMoneyAt(key.getName());
    }

    public void put(String key, KmMoney value)
    {
        Long cents = KmMoney.toCents(value);
        put(key, cents);
    }

    public void put(KmSqlColumnIF key, KmMoney value)
    {
        put(key.getName(), value);
    }

    //##################################################
    //# accessing (extensions)
    //##################################################

    public ContentValues getInner()
    {
        return _inner;
    }

    //##################################################
    //# display (delegates)
    //##################################################

    @Override
    public String toString()
    {
        return _inner.toString();
    }

}
