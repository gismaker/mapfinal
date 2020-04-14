package com.mapfinal.resource.image;

import java.io.File;

import com.mapfinal.Mapfinal;
import com.mapfinal.event.Callback;

import com.mapfinal.event.Event;
import com.mapfinal.event.EventKit;
import com.mapfinal.kit.StringKit;
import com.mapfinal.task.ThreadPool;

public class RemoteImage<M> extends Image<M> {
	private boolean isDestroy = false;
	
	public RemoteImage(String name, String url) {
		super(name, url, null);
		setFileType(FileType.http);
	}
	
	public String cachePath() {
		String cpath = Mapfinal.me().getCacheFolder() + File.separator + "image";
		File f = new File(cpath);
		if(!f.exists()) {
			f.mkdirs();
		}
		return cpath;
	}
	
	public String getFileName() {
		String cachePath = cachePath();
		String name = StringKit.encodeName(getUrl());
		return cachePath + File.separator + name + "." + getImageType().toString();
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
		//System.out.println("[RemoteImage] read: " + filename);
		return (M) getHandle().readFile(filename);
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
			if(!isDestroy()) {
				getHandle().download(url, callback);
			}
		}
	}

	public boolean isDestroy() {
		return isDestroy;
	}
	
	@Override
	public RemoteImage<M> read() {
		// TODO Auto-generated method stub
		isDestroy = false;
		if(this.data==null) {
			//本地缓存
			this.data = readFromLocal();
			if(this.data==null) {
 				ThreadPool.getInstance().submit(new DownloadRunnable(getUrl(), new Callback() {
					@Override
					public void execute(Event event) {
						M img = event.get("image");
						if(img!=null) {
							//从网络获取图片后,保存至本地缓存
							writeToLocal(img);
							EventKit.sendEvent("redraw", "msg", "remote image download");
							//保存至内存中
							if(!isDestroy) setData(img);
						}
					}
					@Override
					public void error(Event event) {
					}
				}));
			}
		}
		return this;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		isDestroy = true;
		//System.out.println("[RemoteImage] downloadThread stop ： " +  getName());
	}
}
