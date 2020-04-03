package com.mapfinal.cache.impl;

import java.util.List;

import com.mapfinal.MapfinalObject;
import com.mapfinal.cache.Cache;
import com.mapfinal.cache.LruCache;

public class LruCacheImpl<K, V extends MapfinalObject> implements Cache<K, V> {

	private LruCache<K, V> cache;
	
	public LruCacheImpl(int cacheSize) {
		this.cache = new LruCache<>(cacheSize);
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return cache.get(key);
	}

	@Override
	public void put(K key, V value) {
		// TODO Auto-generated method stub
		cache.put(key, value);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		cache.clear();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return cache.usedEntries();
	}

	@Override
	public boolean remove(K key) {
		// TODO Auto-generated method stub
		V val = cache.remove(key);
		return val==null ? false : true;
	}

	@Override
	public List<K> keys() {
		// TODO Auto-generated method stub
		return cache.getKeys();
	}

	public LruCache<K, V> getCache() {
		return cache;
	}

	public void setCache(LruCache<K, V> cache) {
		this.cache = cache;
	}
}
