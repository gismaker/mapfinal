package com.mapfinal.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = -7398651751321552254L;

	private int cacheSize;
	
	public LRU(int cacheSize) {
		super(cacheSize * 4 / 3, 0.75f, true);
		this.cacheSize = cacheSize;
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		boolean flag = size() > cacheSize;
		if(flag) {
			//K key = eldest.getKey();
			//System.out.println("well to remove key : " + key);
			return true;
		} else {
			return false;
		}
	}

	public void resize(int cacheSize) {
		this.cacheSize = cacheSize;
	}
	
	public static void main(String[] args) {
		LRU<Character, Integer> lru = new LRU<Character, Integer>(10);
        String s = "abcdefghijkl";
        for (int i = 0; i < s.length(); i++) {
            lru.put(s.charAt(i), i);
        }
        System.out.println("LRU中key为h的Entry的值为： " + lru.get('h'));
        System.out.println("LRU的大小 ：" + lru.size());
        System.out.println("LRU ：" + lru);
	}
	
}
