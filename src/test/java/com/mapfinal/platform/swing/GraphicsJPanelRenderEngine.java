package com.mapfinal.platform.swing;


import javax.swing.JPanel;

import com.mapfinal.platform.bufferedimage.BufferedImageRenderEngine;

public class GraphicsJPanelRenderEngine extends BufferedImageRenderEngine {

	private JPanel panel;

	public GraphicsJPanelRenderEngine(JPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		panel.updateUI();
	}
}
