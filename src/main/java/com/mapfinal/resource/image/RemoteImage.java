package com.mapfinal.resource.image;

import java.io.File;

import com.mapfinal.Mapfinal;
import com.mapfinal.event.Callback;
import com.mapfinal.event.Event;
import com.mapfinal.kit.StringKit;

public class RemoteImage<M> extends Image<M> {
	private String fileExt = "png";
	private Thread downloadThread;
	private boolean isDestroy = false;
	
	public RemoteImage(String name, String url) {
		super(name, url, null);
		setFileType(FileType.http);
	}
	
	public ImageHandle getHandle() {
		return ImageManager.me().getHandle();
	}
	
	public String cachePath() {
		String objType = ImageManager.me().getCacheFolder();
		String objName = "default";
		if(this.collection!=null) {
			objType = this.collection.getType();
			objName = this.collection.getName();
		}
		String cpath = Mapfinal.me().getCacheFolder() + File.separator + objType + File.separator + objName;
		File f = new File(cpath);
		if(!f.exists()) {
			f.mkdirs();
		}
		return cpath;
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
				if(downloadThread!=null) {
					downloadThread.interrupt();
					downloadThread = null;
				}
				downloadThread = new Thread(new DownloadRunnable(getUrl(), callback));
				downloadThread.start();
			}
		}
		if(!bDownload) {
			callback.execute(new Event("imageCache:get").set("image", data));
		}
	}
	
	protected class DownloadRunnable implements Runnable {
		private String url;
		private Callback callback;
		
		public DownloadRunnable(String url, Callback callback) {
			// TODO Auto-generated constructor stub
			this.url = url;
			this.callback = callback;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//网络缓存
			getHandle().download(url, callback);
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
				if(downloadThread!=null) {
					downloadThread.interrupt();
					downloadThread = null;
				}
				downloadThread = new Thread(new DownloadRunnable(getUrl(), new Callback() {
					@Override
					public void execute(Event event) {
						M img = event.get("image");
						if(img!=null) {
							//从网络获取图片后,保存至本地缓存
							writeToLocal(img);
							//保存至内存中
							if(!isDestroy) setData(img);
						}
					}
					@Override
					public void error(Event event) {
					}
				}));
				downloadThread.start();
				/*
				getHandle().download(getUrl(), new Callback() {
					@Override
					public void execute(Event event) {
						if(releaseFlag) return;
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
				});*/
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
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		isDestroy = true;
		if(downloadThread!=null) {
			System.out.println("[RemoteImage] downloadThread stop");
			downloadThread.interrupt();
			downloadThread = null;
		}
	}
}
