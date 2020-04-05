package com.mapfinal.resource.bundle;

import java.util.Map;

import com.mapfinal.common.SyncWriteMap;
import com.mapfinal.resource.ResourceManager;

public class BundleManager implements ResourceManager<String, BundleFeature, BundleCollection> {

	private Map<String, BundleCollection> collection = null;
	
	public BundleManager() {
		collection = new SyncWriteMap<String, BundleCollection>(32, 0.25F);
	}
	
	private static final BundleManager me = new BundleManager();
	
	public static BundleManager me() {
		return me;
	}
	
	public BundleCollection create(String name, String url) {
		BundleCollection c = getCollection(url);
		if(c==null) {
			c = new BundleCollection(name, url);
		}
		return c;
	}
	
	@Override
	public String getResourceType() {
		// TODO Auto-generated method stub
		return "bundle";
	}

	@Override
	public long getMemorySize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addCollection(String key, BundleCollection collection) {
		// TODO Auto-generated method stub
		this.collection.put(key, collection);
	}

	@Override
	public BundleCollection getCollection(String key) {
		// TODO Auto-generated method stub
		return collection.get(key);
	}

	@Override
	public BundleFeature getResource(String collectionKey, String resourceKey) {
		// TODO Auto-generated method stub
		BundleCollection c = getCollection(collectionKey);
		return c!=null ? c.get((String)resourceKey) : null;
	}
	
	/// <summary>
	/// 查找切片对应的文件路径
	/// </summary>
	/// <param name="root">路径</param>
	/// <param name="level">切片等级</param>
	/// <param name="rGroup"></param>
	/// <param name="cGroup"></param>
	/// <returns></returns>
	public String getBundlePath(String root, int level, int rGroup , int cGroup ) {
	     String bundlesDir = root;
	     String l = String.valueOf(level);
	     int lLength = l.length();
	     if (lLength < 2)
	     {
	         for (int i = 0; i < 2 - lLength; i++)
	         {
	             l = "0" + l;
	         }
	     }
	     l = "L" + l;

	     String r = Integer.toHexString(rGroup);
	     int rLength = r.length();
	     if (rLength < 4)
	     {
	         for (int i = 0; i < 4 - rLength; i++)
	         {
	             r = "0" + r;
	         }
	     }
	     r = "R" + r;

	     String c = Integer.toHexString(cGroup);
	     int cLength = c.length();
	     if (cLength < 4)
	     {
	         for (int i = 0; i < 4 - cLength; i++)
	         {
	             c = "0" + c;
	         }
	     }
	     c = "C" + c;
	     String bundlePath=String.format("%s\\_alllayers\\%s\\%s%s", bundlesDir, l, r, c);
	     return bundlePath;
	}

	@Override
	public void remove(String collectionKey) {
		// TODO Auto-generated method stub
		this.collection.remove(collectionKey);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.collection.clear();
	}

}
