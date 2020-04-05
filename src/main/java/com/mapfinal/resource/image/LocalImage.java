package com.mapfinal.resource.image;

public class LocalImage<M> extends Image<M> {

	public LocalImage(String name, String url) {
		super(name, url, null);
		setFileType(FileType.file);
	}

	public LocalImage<M> read() {
		// TODO Auto-generated method stub
		if(this.data==null) {
			this.data = (M) getHandle().readFile(getUrl());
		}
		return this;
	}
}
