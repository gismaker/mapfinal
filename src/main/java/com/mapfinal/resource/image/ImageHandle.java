package com.mapfinal.resource.image;

import com.mapfinal.event.Callback;

public abstract class ImageHandle<M> {

	public abstract int getWidth(M image);
	
	public abstract int getHeight(M image);
	/**
	 * 读取图像
	 * @param fileName
	 * @return
	 */
	public abstract M readFile(String fileName);
	
	public abstract byte[] read(String fileName);
	
	/**
	 * 写图像到文件
	 * @param fileName
	 * @param image
	 */
	public abstract void writeFile(String fileName, M image);
	
	public abstract void write(String fileName, byte[] image);
	/**
	 * 下载图像
	 * @param url
	 * @param callback
	 */
	public abstract void download(String url, Callback callback);
	
	/**
	 * 上传图像，默认post方法
	 * @param url
	 * @param image
	 */
	public abstract void upload(String url, M image);
	
	/**
	 * byte转image
	 * @param imageBuf
	 * @return
	 */
	public abstract M toImage(byte[] imageBuf);
	
	/**
     * Encode Image to Base64 String
     * @param image
     * @param type
     * @return
     */
    public abstract String encodeToString(M image, String type);
	
    /***
     * Decode Base64 String to Image
     * @param imageString
     * @return
     */
    public abstract M decodeToImage(String imageString);
	
	/**
     * 缩放图像（按比例缩放）
     * @param image 源图像文件地址
     * @param scale 缩放比例
     */
    public abstract M scale(M image, float scale);
    
    /**
     * 缩放图像（按高度和宽度缩放）
     * @param image 源图像文件地址
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
     */
    public abstract M scale2(M image, int width, int height, boolean bb);
    
    /**
     * 图像切割(按指定起点坐标和宽高切割)
     * @param image 源图像
     * @param x 目标切片起点坐标X
     * @param y 目标切片起点坐标Y
     * @param width 目标切片宽度
     * @param height 目标切片高度
     */
    public abstract M cut(M image, int x, int y, int width, int height);
    
    /**
     * 图像切割（指定切片的行数和列数）
     * @param image 源图像
     * @param rows 目标切片行数。默认2，必须是范围 [1, 20] 之内
     * @param cols 目标切片列数。默认2，必须是范围 [1, 20] 之内
     */
    //public abstract M cut2(M image, int rows, int cols);
    
    /**
     * 图像切割（指定切片的宽度和高度）
     * @param image 源图像
     * @param destWidth 目标切片宽度。默认200
     * @param destHeight 目标切片高度。默认150
     */
    //public abstract M cut3(M image, int destWidth, int destHeight);
    
    /**
     * 彩色转为黑白 
     * @param image 源图像
     */
    public abstract M gray(M image);
    
    /**
     * 给图片添加文字水印
     * @param image 源图像
     * @param pressText 水印文字
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public abstract M pressText(M image, String pressText, String fontName,
            int fontStyle, int color, int fontSize,int x, int y, float alpha);
    
    /**
     * 给图片添加文字水印
     * @param image 源图像
     * @param pressText 水印文字
     * @param fontName 字体名称
     * @param fontStyle 字体样式
     * @param color 字体颜色
     * @param fontSize 字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public abstract M pressText2(M image, String pressText, String fontName, 
    		int fontStyle, int color, int fontSize, int x, int y, float alpha);
    
    /**
     * 给图片添加图片水印
     * @param inputImage 源图像
     * * @param markImage 水印图片
     * @param x 修正值。 默认在中间
     * @param y 修正值。 默认在中间
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public abstract M pressImage(M inputImage, M pressImage, int x, int y, float alpha);
    
    /**
     * 以指定角度旋转图片：使用正角度 theta 进行旋转，可将正 x 轴上的点转向正 y 轴。 
     * @param inputImage
     * @param degree 角度:以度数为单位
     * @return
     */
    public abstract M rotateImage(final M inputImage, final float degree);
    
    /**
     * 水平翻转图像
     * 
     * @param M 目标图像
     * @return
     */
    public abstract M flipHorizontalImage(final M inputImage);
    
    /**
     * 竖直翻转图像
     * 
     * @param M 目标图像
     * @return
     */
    public abstract M flipVerticalImage(final M inputImage);
    
    /**
     * 不带logo的二维码
     *
     * @param text 二维码内容
     * @param width 二维码宽度
     * @param height 长度
     * @param whiteSize 白边大小
     * @return
     */
    public abstract M createQRImage(String text,int width,int height,int whiteSize);
    
    /**
     *   5、 生成带logo的二维码
     *
     *
     * @param text 二维码内容
     * @param text 二维码内容
     * @param width 二维码宽度
     * @param height 长度
     * @param whiteSize 白边大小
     * @param logo LOGO图片
     * @return
     * @throws Exception
     */
    public abstract M createLogoQRImage(String text,int width,int height, int whiteSize, M logo);
    
    /**
     *  读取二维码的文件里面的信息
     */
    public abstract String readQRImage(M image);
    
    /**
     * 指定图片宽度和高度和压缩比例对图片进行压缩
     * @param widthdist 压缩后图片的宽度
     * @param heightdist 压缩后图片的高度
     * @param rate 压缩的比例 ,可以设置为null
     */
    public abstract M reduceImg(M image, int widthdist, int heightdist, Float rate);
    
}
