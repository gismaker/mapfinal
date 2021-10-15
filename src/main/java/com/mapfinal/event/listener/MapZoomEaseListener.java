package com.mapfinal.event.listener;

import java.util.Timer;
import java.util.TimerTask;

import com.mapfinal.event.Event;
import com.mapfinal.event.EventKit;
import com.mapfinal.event.EventListener;
import com.mapfinal.kit.StringKit;
import com.mapfinal.map.MapContext;

public class MapZoomEaseListener implements EventListener {

	private static final long INTERVAL = 10L;

	// 确定惯性移动减速的速率，单位是像素每秒的二次方2.
	private float inertiaDeceleration = 1;
	// 平移动画的曲率因子
	private float easeLinearity = 0.25f;

	private Timer timer;
	private ZoomTask task;
	
	public boolean onEvent(Event event) {
		if (StringKit.isBlank(event.getAction()))
			return false;
		MapContext context = event.get("map");
		if ("mouseWheel".equals(event.getAction())) {
			//stopMove();
			context.setZoomScale(true);
			if(event.get("rotation")!=null) {
				int rotation = event.get("rotation");
				if (rotation == 1) {
					//context.zoomOut();
					startMove(event, -1, inertiaDeceleration, easeLinearity);
				}
				if (rotation == -1) {
					//context.zoomIn();
					startMove(event, 1, inertiaDeceleration, easeLinearity);
				}
			}
			if(event.get("scaleFactor")!=null) {
				float scaleFactor = event.get("scaleFactor");
//				float hzoom = context.getZoom();
//				hzoom += scaleFactor - 1;
//				context.setZoom(hzoom);
				if(scaleFactor < 1.0) {
					scaleFactor *= -1;
				} else {
					scaleFactor = 2 - scaleFactor;
				}
				startMove(event, scaleFactor, inertiaDeceleration, easeLinearity);
			}
			//System.out.println("[GeoMap.onEvent] current zoom: " + context.getZoom());
			EventKit.sendEvent("redraw");
			//isZoomScale = false;
			return true;
		}
		return false;
	}
	
	// 开始惯性动画
		public void startMove(Event event, float offset, double decelerationDuration, float ease) {
			if (timer == null) {
				timer = new Timer();
			} else {
				task.setZoom(event, offset);
				return;
			}
			task = null;
			task = new ZoomTask(event, offset, decelerationDuration, ease);
			timer.schedule(task, 0, INTERVAL);// 10毫秒一次
		}

		public void stopMove() {
			if (task != null) {
				task.complete();
			}
			timer=null;
		}
	

	public class ZoomTask extends TimerTask {

		private MapContext context;
		
		private long _startTime = 0;
		//速度
		private double _duration;
		//惯性系数
		private float _easeOutPower;

		//初始放大系数
		private float _firstZoom;
		//间隔
		private float _offset;
		
		public ZoomTask(Event event, float offset, Double duration, Float easeLinearity) {
			// TODO Auto-generated constructor stub
			this.context = event.get("map");
			this._offset = offset;
			this._firstZoom = context.getZoom();
			this._duration = duration == null ? 0.25 : duration;
			float ease = easeLinearity == null ? 0.5f : easeLinearity.floatValue();
			this._easeOutPower = 1 / Math.max(ease, 0.2f);
		}
		
		public void setZoom(Event event, float offset) {
			//重设置当前起始zoom
			_firstZoom = context.getZoom();
			//重置缩放增量
			_offset = offset;
			//动画重新开始
			_startTime = 0;
		}

		public void runFrame(double progress) {
			float hzoom = (float) (_firstZoom + _offset * progress);
			context.setZoom(hzoom);
			EventKit.sendEvent("redraw");
		}

		public void complete() {
			cancel();
			float hzoom = (float) (_firstZoom + _offset);
			context.setZoom(hzoom);
			EventKit.sendEvent("redraw");
		}

		public double easeOut(double t) {
			return 1 - Math.pow(1 - t, this._easeOutPower);
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(_offset==0) return;
			
			if (_startTime == 0) {
				_startTime = System.currentTimeMillis();
			}
			long elapsed = System.currentTimeMillis() - this._startTime;
			double duration = this._duration * 1000;
			//System.out.println("time: " + elapsed + ", " + duration);
			
			if(duration==Double.NaN) {
				this.complete();
			} else if (elapsed < duration) {
				this.runFrame(this.easeOut(elapsed / duration));
			} else {
				this.runFrame(1);
			}
		}
	}
}
