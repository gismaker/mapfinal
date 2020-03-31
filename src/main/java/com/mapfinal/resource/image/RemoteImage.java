package com.mapfinal.resource.image;

import java.io.File;

import com.mapfinal.Mapfinal;
import com.mapfinal.event.Callback;
import com.mapfinal.event.Event;
import com.mapfinal.kit.StringKit;

public class RemoteImage<M> extends Image<M> {
	private String fileExt = ".png";
	
	public RemoteImage(String name, String url) {
		super(name, url, null);
		setFileType(FileType.http);
	}
	
	public ImageHandle getHandle() {
		return ImageManager.me().getHandle();
	}
	
	public String cachePath() {
		String im = ImageManager.me().getCacheFolder();
		String cf = this.getCollection()!=null ? this.getCollection().getName() : "defalut";
		return Mapfinal.me().getCacheFolder() + File.separator + im + File.separator + cf;
	}
	
	public String getFileName() {
		String cachePath = cachePath();
		String name = StringKit.encodeName(getUrl());
		return cachePath + File.separator + name + "." + fileExt.toString();
	}
	
	
	public void writeToLocal(M image) {
		String fileName = getFileName();
		File f = new File(fileName);
		if(!f.exists()) {
			getHandle().writeFile(fileName, image);
		}
	}
	
	public M readFromLocal() {
		// TODO Auto-generated method stub
		String filename = getFileName();
		return (M) getHandle().readFile(filename);
	}
	
	public void getAsync(Callback callback) {
		boolean bDownload = false;
		if(this.data==null) {
			//本地缓存
			this.data = readFromLocal();
			if(data==null) {
				bDownload = true;
				//网络缓存
				getHandle().download(getUrl(), new Callback() {
					@Override
					public void execute(Event event) {
						M img = event.get("image");
						if(img!=null) {
							//从网络获取图片后,保存至本地缓存
							writeToLocal(img);
							//保存至内存中
							setData(img);
						}
						callback.execute(new Event("imageCache:get").set("image", data));
					}
					@Override
					public void error(Event event) {
						callback.error(event);
					}
				});
			}
		}
		if(!bDownload) {
			callback.execute(new Event("imageCache:get").set("image", data));
		}
	}
	
	@Override
	public void read() {
		// TODO Auto-generated method stub
		if(this.data==null) {
			//本地缓存
			this.data = readFromLocal();
			if(data==null) {
				//网络缓存
				getHandle().download(getUrl(), new Callback() {
					@Override
					public void execute(Event event) {
						M img = event.get("image");
						if(img!=null) {
							//从网络获取图片后,保存至本地缓存
							writeToLocal(img);
							//保存至内存中
							setData(img);
						}
					}
					@Override
					public void error(Event event) {
					}
				});
			}
		}
	}
	
	public void download(Callback callback) {
		if(this.data!=null) {
			writeToLocal(this.data);
			callback.execute(new Event("imageCache:download").set("image", data));
		} else {
			//本地缓存
			this.data = readFromLocal();
			if(data==null) {
				//网络缓存
				getHandle().download(getUrl(), new Callback() {
					@Override
					public void execute(Event event) {
						M img = event.get("image");
						if(img!=null) {
							//从网络获取图片后,保存至本地缓存
							writeToLocal(img);
						}
						callback.execute(new Event("imageCache:download").set("image", data));
					}
					@Override
					public void error(Event event) {
						callback.error(event);
					}
				});
			}
		}
	}
	
	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
}
