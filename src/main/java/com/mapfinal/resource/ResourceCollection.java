package com.mapfinal.resource;

import java.util.List;

/**
 * 资源管理，管理资源内存、资源调用、资源创建
 * @author yangyong
 *
 */
public interface ResourceCollection<V extends Resource, K> {
	
	/**
	 * 资源名称
	 */
	public String getName();
	/**
	 * 资源地址，文件路径、网络地址、内存数据主键
	 * @return
	 */
	public String getUrl();
	/**
	 * 资源类型
	 * @return
	 */
	public String getType();
	
	/**
     * 内存占用
     * @return
     */
    long getMemorySize();
    
	/**
	 * 读取并加载
	 * @param url  缓存的key值
	 * @return
	 */
	public V get(K key);
	
	/**
	 * 直接读取缓存内容
	 * @param key
	 * @return
	 */
	//public V getValue(K key);
    
    /**
     * 异步读取
     * @param url 缓存的key值
     * @param callback 处理类
     */
    //void getAsync(K key, Callback callback);

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
    //void refresh();

    /**
     * 所有主键，方便遍历
     * @return
     */
    List<K> keys();
}
