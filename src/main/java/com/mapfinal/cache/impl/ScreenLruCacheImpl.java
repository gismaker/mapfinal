package com.mapfinal.cache.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mapfinal.MapfinalObject;
import com.mapfinal.cache.Cache;
import com.mapfinal.cache.LRU;

public class ScreenLruCacheImpl<K, V extends MapfinalObject> implements Cache<K, V> {

	private LRU<K, V> mBitmapCache;
    protected int mCacheSize = 0;
    protected final Object lock = new Object();
	
	public ScreenLruCacheImpl(int size) {
		if(size==0) return;
		synchronized (lock) {
			mBitmapCache = new LRU(size);
        }
		mCacheSize = size;
	}
	
	public void resize(int size) {
		if(size==0) return;
		synchronized (lock) {
			mBitmapCache.resize(size);
        }
		mCacheSize = size;
	}
	
	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		if (mCacheSize == 0) {
            return null;
        }
        synchronized (lock) {
            if (mBitmapCache != null) {
                return mBitmapCache.get(key);
            }
        }
        return null;
	}

	@Override
	public void put(K key, V value) {
		// TODO Auto-generated method stub
		if (mCacheSize == 0) {
            return;
        }
        synchronized (lock) {
            if (mBitmapCache != null) {
                mBitmapCache.put(key, value);
            }
        }
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		if (mBitmapCache != null)
            mBitmapCache.clear();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		synchronized (lock) {
			if (mBitmapCache != null)
				return mBitmapCache.size();
			else 
				return 0;
        }
	}

	@Override
	public boolean remove(K key) {
		// TODO Auto-generated method stub
		synchronized (lock) {
			if (mBitmapCache != null) {
				V val = mBitmapCache.remove(key);
				return val==null ? false : true;
			} else {
				return false;
			}
		}
	}

	@Override
	public List<K> keys() {
		// TODO Auto-generated method stub
		if (mCacheSize == 0) {
            return null;
        }
        synchronized (lock) {
            if (mBitmapCache != null) {
            	return new ArrayList<>(mBitmapCache.keySet());
            }
        }
        return null;
	}
}
