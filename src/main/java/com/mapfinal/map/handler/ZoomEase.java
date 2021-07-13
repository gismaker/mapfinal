package com.mapfinal.map.handler;

import java.util.Timer;
import java.util.TimerTask;

import com.mapfinal.event.EventKit;
import com.mapfinal.map.MapContext;

public class ZoomEase {

	private static final long INTERVAL = 10L;

	// 确定惯性移动减速的速率，单位是像素每秒的二次方2.
	private float inertiaDeceleration = 1;
	// 平移动画的曲率因子
	private float easeLinearity = 0.25f;

	private Timer timer;
	private ZoomTask task;

	public void zoom(MapContext context, float zoom) {
		float czoom = context.getZoom();
		zoomTo(context, zoom - czoom, inertiaDeceleration, easeLinearity);
	}

	public void zoomTo(MapContext context, float delta) {
		zoomTo(context, delta, inertiaDeceleration, easeLinearity);
	}

	// 开始惯性动画
	public void zoomTo(MapContext context, float delta, double decelerationDuration, float ease) {
		if (timer == null) {
			timer = new Timer();
		} else {
			task.setZoom(delta);
			return;
		}
		task = null;
		task = new ZoomTask(context, delta, decelerationDuration, ease);
		timer.schedule(task, 0, INTERVAL);// 10毫秒一次
	}
	
	public void stopWait() {
		if(task==null) return;
		if(timer==null) return;
		while(task.isRun) {
			try {
				Thread.currentThread().sleep(INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		stop();
	}

	public void stop() {
		if (task != null) {
			task.complete();
		}
		timer = null;
	}

	public class ZoomTask extends TimerTask {

		private MapContext context;

		private long _startTime = 0;
		// 速度
		private double _duration;
		// 惯性系数
		private float _easeOutPower;

		// 初始放大系数
		private float _firstZoom;
		// 间隔
		private float _offset;
		
		private boolean isRun = false;

		public ZoomTask(MapContext context, float offset, Double duration, Float easeLinearity) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this._offset = offset;
			this._firstZoom = context.getZoom();
			this._duration = duration == null ? 0.25 : duration;
			float ease = easeLinearity == null ? 0.5f : easeLinearity.floatValue();
			this._easeOutPower = 1 / Math.max(ease, 0.2f);
		}
		
		public boolean isRun() {
			return isRun;
		}

		public void setZoom(float offset) {
			// 重设置当前起始zoom
			_firstZoom = context.getZoom();
			// 重置缩放增量
			_offset = offset;
			// 动画重新开始
			_startTime = 0;
		}

		public void runFrame(double progress) {
			float hzoom = (float) (_firstZoom + _offset * progress);
			context.setZoom(hzoom);
			EventKit.sendEvent("redraw");
		}

		public void complete() {
			cancel();
			isRun = false;
		}

		public double easeOut(double t) {
			return 1 - Math.pow(1 - t, this._easeOutPower);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(!isRun) {
				isRun = true;
			}
			if (_offset == 0)
				return;

			if (_startTime == 0) {
				_startTime = System.currentTimeMillis();
			}
			long elapsed = System.currentTimeMillis() - this._startTime;
			double duration = this._duration * 1000;
			// System.out.println("time: " + elapsed + ", " + duration);

			if (duration == Double.NaN) {
				this.complete();
			} else if (elapsed < duration) {
				this.runFrame(this.easeOut(elapsed / duration));
			} else {
				this.runFrame(1);
				this.complete();
			}
		}
	}
}
