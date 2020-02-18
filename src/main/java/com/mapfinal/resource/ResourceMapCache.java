package com.mapfinal.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.mapfinal.event.Callback;

public class ResourceMapCache<K, V> implements ResourceCache<K, V> {

	private ConcurrentHashMap<K, V> cache;
	
	public ResourceMapCache() {
		this.cache = new ConcurrentHashMap<>();
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return cache.get(key);
	}

	@Override
	public void getAsync(K key, Callback callback) {
		// TODO Auto-generated method stub
		
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
	public void refresh() {
		// TODO Auto-generated method stub
	}

	@Override
	public List<K> keys() {
		// TODO Auto-generated method stub
		return new ArrayList<>(cache.keySet());
	}

	@Override
	public V getValue(K key) {
		// TODO Auto-generated method stub
		return cache.get(key);
	}

	public ConcurrentHashMap<K, V> getCache() {
		return cache;
	}

	public void setCache(ConcurrentHashMap<K, V> cache) {
		this.cache = cache;
	}
}
