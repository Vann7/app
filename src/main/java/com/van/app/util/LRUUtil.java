package com.van.app.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 类说明：利用LinkedHashMap实现简单的缓存， 必须实现removeEldestEntry方法，具体参见JDK文档
 * @param <K>
 * @param <V>
 */
public class LRUUtil<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    private static final float DEAFULT_LOAD_FACTOR = 0.75f;

    private final Lock lock = new ReentrantLock();

    public LRUUtil(int capacity) {
        super(capacity, DEAFULT_LOAD_FACTOR, true);
        this.capacity = capacity;
    }

    @Override
    public int size() {
        try {
            lock.lock();
            return super.size();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public boolean containsKey(Object key) {
        try {
            lock.lock();
            return super.containsKey(key);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public V put(K key, V value) {
        try {
            lock.lock();
            return super.put(key, value);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public V get(Object key) {
        try {
            lock.lock();
            return super.get(key);
        } finally {
            lock.unlock();
        }

    }

    /**
     * 通过覆盖这个方法，加入一定的条件，满足条件返回true。当put进新的值方法返回true时，便移除该map中最老的键和值。
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
