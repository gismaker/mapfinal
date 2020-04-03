package com.mapfinal.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.mapfinal.MapfinalObject;
import com.mapfinal.cache.Cache;

public class MapCacheImpl<K, V extends MapfinalObject> implements Cache<K, V> {

	private ConcurrentHashMap<K, V> cache;
	
	public MapCacheImpl() {
		this.cache = new ConcurrentHashMap<>();
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
		return cache.size();
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
		return new ArrayList<>(cache.keySet());
	}

	public ConcurrentHashMap<K, V> getCache() {
		return cache;
	}

	public void setCache(ConcurrentHashMap<K, V> cache) {
		this.cache = cache;
	}
}
