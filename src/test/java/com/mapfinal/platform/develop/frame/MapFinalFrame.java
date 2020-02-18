package com.mapfinal.platform.develop.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Java Swing 图形界面开发（目录）
 * https://blog.csdn.net/xietansheng/article/details/72814492
 * @author yangyong
 *
 */
public class MapFinalFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		/*
         * 在 AWT 的事件队列线程中创建窗口和组件, 确保线程安全,
         * 即 组件创建、绘制、事件响应 需要处于同一线程。
         */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 创建窗口对象
            	MapFinalFrame frame = new MapFinalFrame();
                // 显示窗口
                frame.setVisible(true);
            }
        });
    }
	
	public static final String TITLE = "Java图形绘制";

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    public MapFinalFrame() {
        super();
        initFrame();
    }

    private void initFrame() {
        // 设置 窗口标题 和 窗口大小
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);

        // 设置窗口关闭按钮的默认操作(点击关闭时退出进程)
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 把窗口位置设置到屏幕的中心
        setLocationRelativeTo(null);

        // 设置窗口的内容面板
        MapFinalPanel panel = new MapFinalPanel(this);
        setContentPane(panel);
    }

}
