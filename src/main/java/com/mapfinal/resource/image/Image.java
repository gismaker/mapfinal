package com.mapfinal.resource.image;

import com.mapfinal.Mapfinal;
import com.mapfinal.event.Event;
import com.mapfinal.resource.Data;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.ResourceObject;

public class Image<M> extends ResourceObject<Image<M>> implements Data {
	
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 文件路径 或 网络地址，唯一键
	 */
	private String url;
	/**
	 * 存储类型
	 */
	private FileType fileType = FileType.cache;
	/**
	 * 图像类型
	 */
	private Resource.ImageType imageType = ImageType.png;
	/**
	 * 图像数据
	 */
	protected M data;
	
	public Image(String name, String url, M image) {
		this.setName(name);
		this.setUrl(url);
		this.data = image;
	}
	
	public ImageHandle<M> getHandle() {
		return Mapfinal.me().getPlatform().getImageHandle();
	}
	
	@Override
	public void prepare(Event event) {
		// TODO Auto-generated method stub
	}

	@Override
	public Image<M> read(Event event) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void writer(Event event, Image<M> image) {
		// TODO Auto-generated method stub
		if(image==null) {
			getHandle().writeFile(image.getUrl(), image.getData(event));
		}
	}
	
	public void writer(Event event) {
		if(this.data==null) {
			getHandle().writeFile(getUrl(), getData(event));
		}
	}
	
	/**
	 * 获取并读取图像
	 * @return
	 */
	public M getData(Event event) {
		read(event);
		return data;
	}
	
	public int getWidth() {
		return data==null ? 0 : Mapfinal.imageHandle().getWidth(data);
	}

	public int getHeight() {
		return data==null ? 0 : Mapfinal.imageHandle().getHeight(data);
	}
	
	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public void setData(M image) {
		this.data = image;
	}

	public Resource.ImageType getImageType() {
		return imageType;
	}

	public void setImageType(Resource.ImageType imageType) {
		this.imageType = imageType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		data = null;
	}

	
}
