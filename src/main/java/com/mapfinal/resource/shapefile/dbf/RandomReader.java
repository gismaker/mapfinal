package com.mapfinal.resource.shapefile.dbf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RandomReader {

	private byte[] buf;
	private int length = 0;
	private int currPointer = 0;

	public RandomReader(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		length = fis.available();
		buf = new byte[length];
		fis.read(buf);
		fis.close();
	}

	public RandomReader(String file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		length = fis.available();
		buf = new byte[length];
		fis.read(buf);
		fis.close();
	}
	
	public void init() {
		currPointer = 0;
	}

	public int read() {
		if (currPointer > length) {
			return -1;
		}
		return buf[currPointer++];
	}

	public int read(byte[] buffer, int offset, int count) {
		if (this.currPointer > length) {
			return -1;
		}
		int available = length - this.currPointer;
		if (count > available) {
			// System.arraycopy(buf, currPointer, buffer, offset, available);
			return -1;
		}
		System.arraycopy(buf, currPointer, buffer, offset, count);
		this.currPointer += count;
		return count;
	}

	public void seek(int pos) throws IOException {
		this.currPointer = pos;
	}
}
