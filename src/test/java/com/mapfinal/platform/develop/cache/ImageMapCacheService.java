package com.mapfinal.platform.develop.cache;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import com.mapfinal.event.Callback;
import com.mapfinal.event.Event;
import com.mapfinal.kit.FileKit;
import com.mapfinal.resource.ImageCacheService;
import com.mapfinal.resource.Resource;

public class ImageMapCacheService implements ImageCacheService {

	ConcurrentHashMap<String, Object> imageCache = new ConcurrentHashMap<String, Object>();
	
	public ImageMapCacheService() {
	}
	
	@Override
	public String encodeName(String url) {
		// TODO Auto-generated method stub
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					url.getBytes("UTF-8"));
			StringBuilder hex = new StringBuilder(hash.length * 2);
	        for (byte b : hash) {
	            if ((b & 0xFF) < 0x10) {
	                hex.append("0");
	            }
	            hex.append(Integer.toHexString(b & 0xFF));
	        }
	        return hex.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return url;
	}

	@Override
	public Object readFromCache(String url) {
		// TODO Auto-generated method stub
		return imageCache.get(url);
	}

	@Override
	public void writerToCache(String url, Object image) {
		// TODO Auto-generated method stub
		imageCache.put(url, image);
	}
	
	@Override
	public boolean remove(String url) {
		// TODO Auto-generated method stub
		Object image = imageCache.remove(url);
		return image == null ? false : true;
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		imageCache.clear();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return imageCache.size();
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getKeys() {
		// TODO Auto-generated method stub
		return new ArrayList<String>(imageCache.keySet());
	}
	
	@Override
	public Object readFromLocal(String url, String cachePath, Resource.ImageType type) {
		// TODO Auto-generated method stub
		if(url.startsWith("http")) {
			String filename = getFileName(url, cachePath, type);
			return Toolkit.getDefaultToolkit().getImage(filename);
		} else {
			return Toolkit.getDefaultToolkit().getImage(url);
		}
	}
	
	public String getFileName(String url, String cachePath, Resource.ImageType type) {
		String name = encodeName(url);
		return cachePath + File.separator + name + "." + type.toString();
	}

	@Override
	public void writerToLocal(String fileName, Object image) {
		// TODO Auto-generated method stub
		if(image instanceof BufferedImage) {
			BufferedImage bufimg = (BufferedImage) image;
			try {
				ImageIO.write(bufimg, FileKit.getExtensionName(fileName), new File(fileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void downloadImage(String url, Callback callback) {
		// TODO Auto-generated method stub
		HttpURLConnection httpCon = null;
        URLConnection  con = null;
        URL urlObj=null;
        InputStream in =null;
        try {
        	//System.out.println("[GraphicsImageIO] httpImage: " + url);
        	urlObj = new URL(url);
			con = urlObj.openConnection();
			httpCon =(HttpURLConnection) con;
	        in = httpCon.getInputStream();
	        BufferedImage image = javax.imageio.ImageIO.read(in);
	        if(callback!=null) callback.execute(new Event("imageCache:get").set("image", image));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(callback!=null) callback.error(new Event("imageCache:get").set("image", null).set("error", e));
		}
	}

	@Override
	public void uploadImage(String url) {
		// TODO Auto-generated method stub
		
	}
}
