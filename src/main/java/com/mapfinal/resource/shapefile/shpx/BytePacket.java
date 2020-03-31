package com.mapfinal.resource.shapefile.shpx;

public class BytePacket {
	/**
	 * buffer
	 */
	public byte[] buffer = null;
	/**
	 * 实际读取的长度,buffer为32K,读取的长度length大于32K时实际读取的为32K,否则即为要读取的长度值length
	 */
	public int actualLength = 0;
	/**
	 * 读取的长度,因为readPoint会改变这个值,所以每次读取完后将该值重新赋给外部length
	 */
	public int length = 0;
	/**
	 * eof
	 */
	public boolean isEof = false;
}
