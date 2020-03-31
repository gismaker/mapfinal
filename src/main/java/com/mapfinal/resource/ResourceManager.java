package com.mapfinal.resource;

/**
 * 资源管理，管理资源内存、资源调用、资源创建
 * @author yangyong
 *
 */
public interface ResourceManager<V extends Resource, K, C extends ResourceCollection<V,K>> {
	
	/**
	 * 资源类型
	 * @return
	 */
	public String getResourceType();
	
	/**
     * 内存占用
     * @return
     */
    long getMemorySize();
    
    /**
     * 加入资源
     * @param key
     * @param collection
     */
    public void addCollection(String key, C collection);
    
    /**
     * 获取资源
     * @param key
     * @return
     */
    public C getCollection(String key);
    
    /**
     * 获取资源的最小单元
     * @param collectionKey
     * @param resourceKey
     * @return
     */
    public V getResource(String collectionKey, K resourceKey);
    
}
