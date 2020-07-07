package com.mapfinal.platform.develop.graphics;


import javax.swing.JPanel;

public class GraphicsJPanelRenderEngine extends GraphicsRenderEngine {

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
