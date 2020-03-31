package com.mapfinal.example.converter;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 测试bundle文件那个tile有图片数据
 * @author yangyong
 *
 */
public class TestBundle {

	public static void main(String[] args) throws IOException {
		RandomAccessFile isBundle = null;
		RandomAccessFile isBundlx = null;
		String bundleBase = "D:\\lambkit-gis-earth\\data\\_alllayers\\L13\\R0c80C1900";
		String bundlxFileName = bundleBase + ".bundlx";
		String bundleFileName = bundleBase + ".bundle";
		// 行列号是整个范围内的，在某个文件中需要先减去前面文件所占有的行列号（都是128的整数）这样就得到在文件中的真是行列号
		isBundlx = new RandomAccessFile(bundlxFileName, "r");
		isBundle = new RandomAccessFile(bundleFileName, "r");
		int lens = 128 * 128;
		for(int index=0; index<lens; index++) {
			isBundlx.seek(16 + 5 * index);
			byte[] buffer = new byte[5];
			isBundlx.read(buffer, 0, 5);
			long offset = (long) (buffer[0] & 0xff) + (long) (buffer[1] & 0xff) * 256
					+ (long) (buffer[2] & 0xff) * 65536 + (long) (buffer[3] & 0xff) * 16777216
					+ (long) (buffer[4] & 0xff) * 4294967296L;
			
			isBundle.seek(offset);
			byte[] lengthBytes = new byte[4];
			isBundle.read(lengthBytes, 0, 4);
			int length = (int) (lengthBytes[0] & 0xff) + (int) (lengthBytes[1] & 0xff) * 256
					+ (int) (lengthBytes[2] & 0xff) * 65536 + (int) (lengthBytes[3] & 0xff) * 16777216;

			byte[] tileBytes = new byte[length];
			int bytesRead = 0;
			bytesRead = isBundle.read(tileBytes, 0, length);
			if(length > 100) {
				System.out.println("index:"+index+", offset:"+offset+", len:"+length+", readlen:"+bytesRead);
			}
		}
		isBundle.close();
		isBundlx.close();
		System.out.println("---------------over----------------");
	}
}
