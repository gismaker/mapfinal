package com.mapfinal.resource.bundle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

import com.mapfinal.Mapfinal;
import com.mapfinal.converter.SpatialReference;
import com.mapfinal.map.GeoImage;
import com.mapfinal.map.Tile;
import com.mapfinal.resource.Data;

/**
 * 
 * https://www.cnblogs.com/yuantf/p/3320876.html
 * https://blog.csdn.net/abc553226713/article/details/8668839
 * 
 * @author yangyong
 *
 * @param <M>
 */
public class BundleFeature<M> implements GeoImage<M>, Data {

	// 名称
	private String name;
	// 文件路径 或 网络地址，唯一键
	private String url;
	private String collectionKey;
	// 被调用次数
	private int reference = 0;
	/**
	 * 切片数据
	 */
	protected M image;
	/**
	 * 切片信息
	 */
	protected Tile tile;

	public BundleFeature(String url, Tile tile) {
		this.name = tile.getId();
		this.url = url;
		this.tile = tile;
		read();
	}

	public BundleFeature(String url, Tile tile, M image) {
		this.name = tile.getId();
		this.url = url;
		this.tile = tile;
		this.image = image;
	}

	public SpatialReference getSpatialReference() {
		return tile != null ? tile.getSpatialReference() : null;
	}

	public int getReference() {
		// TODO Auto-generated method stub
		return reference;
	}

	public BundleFeature<M> reference() {
		// TODO Auto-generated method stub
		reference++;
		return this;
	}

	public int referenceRelease() {
		// TODO Auto-generated method stub
		this.reference--;
		if (this.reference == 0)
			destroy();
		return this.reference;
	}

	public void prepare() {
		// TODO Auto-generated method stub
		// undo
	}

	public void read() {
		// TODO Auto-generated method stub
		 byte[] img = readBundle(url, tile.getZ(), tile.getY(), tile.getX());
		//byte[] img = text(url, tile.getZ(), tile.getY(), tile.getX());
//		if(tile.getZ() > 12) {
//			if(img==null) System.out.println("bundle: null_" + tile.getImageName());
//			else {
//				System.out.println("bundle: img_" + tile.getImageName() + ", length: " + img.length);
//			}
//		}
		this.image = img != null ? (M) Mapfinal.me().getPlatform().getImageHandle().toImage(img) : this.image;
	}

	public void writer() {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Geometry getGeometry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGeometry(Geometry geometry) {
		// TODO Auto-generated method stub
	}

	@Override
	public M getImage() {
		// TODO Auto-generated method stub
		return image;
	}

	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return tile.getEnvelope();
	}

	/**
	 * 左上角
	 * 
	 * @return
	 */
	public Coordinate getTopLeft() {
		return new Coordinate(getEnvelope().getMinX(), getEnvelope().getMaxY());
	}

	/**
	 * 左下角
	 * 
	 * @return
	 */
	public Coordinate getBottomLeft() {
		return new Coordinate(getEnvelope().getMinX(), getEnvelope().getMinY());
	}

	/**
	 * 右上角
	 * 
	 * @return
	 */
	public Coordinate getTopRight() {
		return new Coordinate(getEnvelope().getMaxX(), getEnvelope().getMaxY());
	}

	/**
	 * 右下角
	 * 
	 * @return
	 */
	public Coordinate getBottomRight() {
		return new Coordinate(getEnvelope().getMaxX(), getEnvelope().getMinY());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public byte[] text(String bundlesDir, Integer mLevel, Integer mRow, Integer mColumn) {
		System.out.println("level:" + mLevel + ", row:" + mRow + ", col:" + mColumn);
		String level = Integer.toString(mLevel);
		int levelLength = level.length();
		if (levelLength == 1) {
			level = "0" + level;
		}
		level = "L" + level;

		int rowGroup = 128 * (mRow / 128);
		String row = Integer.toHexString(rowGroup);
		int rowLength = row.length();
		if (rowLength < 4) {
			for (int i = 0; i < 4 - rowLength; i++) {
				row = "0" + row;
			}
		}
		row = "R" + row;

		int columnGroup = 128 * (mColumn / 128);
		String column = Integer.toHexString(columnGroup);
		int columnLength = column.length();
		if (columnLength < 4) {
			for (int i = 0; i < 4 - columnLength; i++) {
				column = "0" + column;
			}
		}
		column = "C" + column;

		String bundleName = String.format("%s/%s/%s%s", bundlesDir, level, row, column) + ".bundle";
		int index = 128 * (mRow - rowGroup) + (mColumn - columnGroup);
		
		RandomAccessFile isBundle = null;
		try {
			isBundle = new RandomAccessFile(bundleName, "r");
			isBundle.skipBytes(64 + 8 * index);

			// 获取位置索引并计算切片位置偏移量
			byte[] indexBytes = new byte[4];
			isBundle.read(indexBytes, 0, 4);
			long offset = (long) (indexBytes[0] & 0xff) + (long) (indexBytes[1] & 0xff) * 256
					+ (long) (indexBytes[2] & 0xff) * 65536 + (long) (indexBytes[3] & 0xff) * 16777216;

			// 获取切片长度索引并计算切片长度
			long startOffset = offset - 4;
			isBundle.seek(startOffset);
			byte[] lengthBytes = new byte[4];
			isBundle.read(lengthBytes, 0, 4);
			int length = (int) (lengthBytes[0] & 0xff) + (int) (lengthBytes[1] & 0xff) * 256
					+ (int) (lengthBytes[2] & 0xff) * 65536 + (int) (lengthBytes[3] & 0xff) * 16777216;

			// 根据切片位置和切片长度获取切片
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			byte[] tileBytes = new byte[length];
			int bytesRead = 0;
			if (length > 0) {
				bytesRead = isBundle.read(tileBytes, 0, tileBytes.length);
				if (bytesRead > 0) {
					bos.write(tileBytes, 0, bytesRead);
				}
			}
			isBundle.close();
			byte[] tile = bos.toByteArray();
			return tile;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(isBundle!=null) {
				try {
					isBundle.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println(bundleName + " not find.");
		}
		return null;
	}

	public byte[] readBundle(String bundlesDir, int level, int row, int col) {
		//System.out.println("level:" + level + ", row:" + row + ", col:" + col);
		int size = 128;
		byte[] result = null;
		RandomAccessFile isBundle = null;
		RandomAccessFile isBundlx = null;
		try {
			String l = String.valueOf(level);
			int lLength = l.length();
			if (lLength < 2) {
				l = "0" + l;
				l = l.substring(l.length() - 2);
			}
			l = "L" + l;
			int rGroup = size * (row / size);
			String r = Integer.toHexString(rGroup);
			// System.out.println("rGroup:" + rGroup + ", row:" + row + ", r:" + r);
			int rLength = r.length();
			if (rLength < 4) {
				r = "000" + r;
				r = r.substring(r.length() - 4);
			}

			r = "R" + r;
			int cGroup = size * (col / size);
			String c = Integer.toHexString(cGroup);
			int cLength = c.length();
			if (cLength < 4) {
				c = "000" + c;
				c = c.substring(c.length() - 4);
			}
			c = "C" + c;
			String bundleBase = bundlesDir + File.separator + l + File.separator + r + c;
			String bundlxFileName = bundleBase + ".bundlx";
			String bundleFileName = bundleBase + ".bundle";
			//System.out.println("col:" + col + ", cg:" + cGroup + ", row:" + row + ", rg:" + rGroup + ", file:" + bundleBase);
			//System.out.println("file:" + bundleFileName);
			// 行列号是整个范围内的，在某个文件中需要先减去前面文件所占有的行列号（都是128的整数）这样就得到在文件中的真是行列号
			isBundlx = new RandomAccessFile(bundlxFileName, "r");
			int index = size * (col - cGroup) + (row - rGroup);// 4896;//
			isBundlx.seek(16 + 5 * index);
			byte[] buffer = new byte[5];
			isBundlx.read(buffer, 0, 5);
			long offset = (long) (buffer[0] & 0xff) + (long) (buffer[1] & 0xff) * 256
					+ (long) (buffer[2] & 0xff) * 65536 + (long) (buffer[3] & 0xff) * 16777216
					+ (long) (buffer[4] & 0xff) * 4294967296L;
			isBundle = new RandomAccessFile(bundleFileName, "r");
			isBundle.seek(offset);
			byte[] lengthBytes = new byte[4];
			isBundle.read(lengthBytes, 0, 4);
			int length = (int) (lengthBytes[0] & 0xff) + (int) (lengthBytes[1] & 0xff) * 256
					+ (int) (lengthBytes[2] & 0xff) * 65536 + (int) (lengthBytes[3] & 0xff) * 16777216;
			result = new byte[length];
			int bytesRead = 0;
			bytesRead = isBundle.read(result, 0, length);
			// System.out.println("Bundle: " + bundleFileName + ", index:"+index+",
			// offset:"+offset+", len:"+length+", readlen:"+bytesRead);
			if (isBundle != null) {
				isBundle.close();
				isBundlx.close();
			}
		} catch (Exception ex) {
			try {
				if (isBundle != null)
					isBundle.close();
				if (isBundlx != null)
					isBundlx.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// ex.printStackTrace();
			return null;
		}
		return result;
	}

	public void writeBundle(Byte[] image, String bundlesDir, int level, int row, int col) {
		// undo
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		image = null;
	}

	public String getCollectionKey() {
		return collectionKey;
	}

	public void setCollectionKey(String collectionKey) {
		this.collectionKey = collectionKey;
	}

	@Override
	public boolean isRectImage() {
		// TODO Auto-generated method stub
		return true;
	}
}
