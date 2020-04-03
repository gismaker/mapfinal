package com.mapfinal.cache;

import java.util.List;

import com.mapfinal.MapfinalObject;

/**
 * 缓存接口
 * @author yangyong
 */
public interface Cache<K, V extends MapfinalObject>{
	/**
	 * 读取缓存类
	 * @param url  缓存的key值
	 * @return
	 */
    V get(K key);
    
    /**
     * 存储缓存类
     * @param key 缓存的key值
     * @param value 缓存值
     */
    void put(K key, V value);
    
    /**
     * 清空缓存
     */
    void clear();
    
    /**
     * 缓存大小
     * @return
     */
    int size();

    /**
     * 删除某个值
     *
     * @param key 需要删除的缓存值对应key
     * @return 返回true或者false表示删除是否成功
     */
    boolean remove(K key);

    /**
     * 所有主键，方便遍历
     * @return
     */
    List<K> keys();
    
}
