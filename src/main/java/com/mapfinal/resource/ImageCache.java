package com.mapfinal.resource;

import java.io.File;
import java.util.List;

import com.mapfinal.Mapfinal;
import com.mapfinal.event.Callback;
import com.mapfinal.event.Event;

/**
 * 图片缓存策略  - 三级缓存
 * <br>网络缓存, 不优先加载, 速度慢,浪费流量
 * <br>本地缓存, 次优先加载, 速度快
 * <br>内存缓存, 优先加载, 速度最快（包括强引用（LruCache）和软引用（SoftReference））
 * <br>https://blog.csdn.net/u011916937/article/details/84955151
 * @author yangyong
 */
public class ImageCache implements ResourceCache<String, Object> {
	
	private ImageCacheService service;
	private String cacheFolder;
	private Resource.ImageType type;
	
	public ImageCache(ImageCacheService service, String cacheFolder, Resource.ImageType type) {
		// TODO Auto-generated constructor stub
		this.service = service;
		this.cacheFolder = cacheFolder;
		this.type = type;
	}
	
	public String cachePath() {
		return Mapfinal.me().getCacheFolder() + File.separator + cacheFolder;
	}
	
	public String getFileName(String url) {
		String cachePath = cachePath();
		String name = service.encodeName(url);
		return cachePath + File.separator + name + "." + type.toString();
	}
	
	/**
	 * 
	 * @param url=ResourceObject.url
	 * @return
	 */
	@Override
	public Object get(String url) {
		// TODO Auto-generated method stub
		Object obj = null;
		//内存缓存
		obj = service.readFromCache(url);
		if(obj==null) {
			//本地缓存
			obj = service.readFromLocal(url, cachePath(), type);
			if(obj!=null) {
				//从本地获取图片后,保存至内存中
				service.writerToCache(url, obj);
			} else if(obj==null && url.startsWith("http")) {
				//网络缓存
				service.downloadImage(url, new Callback() {
					@Override
					public void execute(Event event) {
						// TODO Auto-generated method stub
						Object img = event.get("image");
						if(img!=null) {
							//从网络获取图片后,保存至本地缓存
							service.writerToLocal(getFileName(url), img);
							//保存至内存中
							service.writerToCache(url, img);
						}
					}
					@Override
					public void error(Event event) {
						// TODO Auto-generated method stub
					}
				});
			}
		}
		return obj;
	}

	/*
	public void getSync(String imagefile, Callback callback) {
		// TODO Auto-generated method stub
		Object image = null;
		//内存缓存
		image = readFromCache(imagefile);
		if(image==null) {
			//本地缓存
			image = readFromLocal(imagefile);
			if(image!=null) {
				//从本地获取图片后,保存至内存中
				writerToCache(imagefile, image);
			} else if(image==null && imagefile.startsWith("http")) {
				//网络缓存
				downloadImage(imagefile, new Callback() {
					@Override
					public void execute(Event event) {
						// TODO Auto-generated method stub
						Object img = event.get("image");
						if(img!=null) {
							//从网络获取图片后,保存至本地缓存
							writerToLocal(imagefile, img);
							//保存至内存中
							writerToCache(imagefile, img);
						}
					}
					@Override
					public void error(Event event) {
						// TODO Auto-generated method stub
					}
				});
			}
		}
		callback.execute(new Event("imageCache:get").set("image", image));
	}*/

	@Override
	public void getAsync(String url, Callback callback) {
		// TODO Auto-generated method stub
		Object image = null;
		boolean bDownload = false;
		//内存缓存
		image = service.readFromCache(url);
		if(image==null) {
			//本地缓存
			image = service.readFromLocal(url, cachePath(), type);
			if(image!=null) {
				//从本地获取图片后,保存至内存中
				service.writerToCache(url, image);
			} else if(image==null && url.startsWith("http")) {
				bDownload = true;
				//网络缓存
				service.downloadImage(url, new Callback() {
					@Override
					public void execute(Event event) {
						Object img = event.get("image");
						if(img!=null) {
							//从网络获取图片后,保存至本地缓存
							service.writerToLocal(getFileName(url), img);
							//保存至内存中
							service.writerToCache(url, img);
						}
						callback.execute(new Event("imageCache:get").set("image", img));
					}
					@Override
					public void error(Event event) {
						callback.error(event);
					}
				});
			}
		}
		if(!bDownload) {
			callback.execute(new Event("imageCache:get").set("image", image));
		}
	}

	@Override
	public void put(String key, Object value) {
		// TODO Auto-generated method stub
		//保存至内存中
		service.writerToCache(key, value);
		//保存至本地缓存
		service.writerToLocal(key, value);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		service.clear();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return service.size();
	}

	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		return service.remove(key);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		service.refresh();
	}
	
	public ImageCacheService getService() {
		return service;
	}

	public String getCacheFolder() {
		return cacheFolder;
	}

	public void setCacheFolder(String cacheFolder) {
		this.cacheFolder = cacheFolder;
	}

	public Resource.ImageType getType() {
		return type;
	}

	public void setType(Resource.ImageType type) {
		this.type = type;
	}

	@Override
	public List<String> keys() {
		// TODO Auto-generated method stub
		return service.getKeys();
	}

	@Override
	public Object getValue(String url) {
		// TODO Auto-generated method stub
		return service.readFromCache(url);
	}
}
