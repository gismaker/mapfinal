package com.mapfinal.resource;

import java.util.List;

import com.mapfinal.event.Callback;

/**
 * 缓存接口
 * @author yangyong
 */
public interface ResourceCache<K, V>{
	/**
	 * 读取缓存类
	 * @param url  缓存的key值
	 * @return
	 */
    V get(K key);
    
    /**
     * 异步读取缓存类
     * @param url 缓存的key值
     * @param callback 处理类
     */
    void getAsync(K key, Callback callback);

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
     * 刷新缓存
     */
    void refresh();

    /**
     * 所有主键，方便遍历
     * @return
     */
    List<K> keys();
    /**
     * 获取当前内存对象
     * @param url
     * @return
     */
    V getValue(K key);
}
