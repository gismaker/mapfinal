package com.mapfinal.resource.image;

import com.mapfinal.resource.Resource;
import com.mapfinal.resource.ResourceCollection;
import com.mapfinal.resource.ResourceObject;

public class Image<M> extends ResourceObject {
	private FileType fileType = FileType.cache;
	private Resource.ImageType imageType = ImageType.png;
	protected M data;
	protected ResourceCollection collection;
	
	public Image(String name, String url) {
		super(name, url);
		prepare();
	}

	public Image(String name, String url, M image) {
		super(name, url);
		this.data = image;
		prepare();
	}
	
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
	}

	@Override
	public void read() {
		// TODO Auto-generated method stub
	}

	@Override
	public void writer() {
		// TODO Auto-generated method stub
		if(this.data==null) {
			ImageManager.me().getHandle().writeFile(getUrl(), this.data);
		}
	}
	
	/**
	 * 获取并读取图像
	 * @return
	 */
	public M getData() {
		read();
		return data;
	}
	
	public int getWidth() {
		return 0;
	}

	public int getHeight() {
		return 0;
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

	public ResourceCollection getCollection() {
		return collection;
	}

	public void setCollection(ResourceCollection collection) {
		this.collection = collection;
	}
}
