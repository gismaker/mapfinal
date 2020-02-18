package com.mapfinal.resource;

import java.util.List;

import com.mapfinal.event.Callback;

public class ResourceLruCache<K, V> implements ResourceCache<K, V> {

	private LruCache<K, V> cache;
	
	public ResourceLruCache(int cacheSize) {
		this.cache = new LruCache<>(cacheSize);
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
		return cache.usedEntries();
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
		return cache.getKeys();
	}

	@Override
	public V getValue(K key) {
		// TODO Auto-generated method stub
		return cache.get(key);
	}

	public LruCache<K, V> getCache() {
		return cache;
	}

	public void setCache(LruCache<K, V> cache) {
		this.cache = cache;
	}
}
