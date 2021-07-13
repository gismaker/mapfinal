package com.mapfinal.map.handler;

import java.util.Timer;
import java.util.TimerTask;

import com.mapfinal.event.EventKit;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.map.MapContext;

/**
 * 
 * @author yangyong
 *
 */
public class MoveFly {

	private static final long INTERVAL = 10L;

	// 确定惯性移动减速的速率，单位是像素每秒的二次方2.
	private float inertiaDeceleration = 1;
	// 平移动画的曲率因子
	private float easeLinearity = 0.25f;

	private Timer timer;
	private flyTask task;

	/**
	 * 飞行到目的地
	 * @param context
	 * @param latlng 目的地
	 * @param zoom	目标缩放
	 * @param duration 飞行时间
	 */
	public void flyTo(MapContext context, Latlng latlng, float zoom) {
		Latlng center = context.getCenter();
		Latlng offset = Latlng.by(latlng.lat() - center.lat(), latlng.lng() - center.lng());
		System.out.println("fly1: " + offset.toString());
		Latlng fistPos = Latlng.by(center);
		startMove(context, offset, fistPos, zoom, inertiaDeceleration, easeLinearity);
	}

	// 开始惯性动画
	public void startMove(MapContext context, Latlng offset, Latlng firstPos, float zoom, double decelerationDuration, float ease) {
		if (timer == null) {
			timer = new Timer();
		} else {
			task.cancel();
		}
		task = null;
		task = new flyTask(context, offset, firstPos, zoom, decelerationDuration, ease);
		timer.schedule(task, 0, INTERVAL);// 10毫秒一次
	}

	public void stopMove() {
		if (task != null) {
			task.complete();
		}
	}

	public class flyTask extends TimerTask {
		private MapContext context;
		private long _startTime = 0;
		private double _duration;
		private float _easeOutPower;
		//距离间隔
		private Latlng _offset;
		private Latlng _firstPos;
		// 初始放大系数
		private float _firstZoom;
		// 间隔
		private float _offsetZoom;

		public flyTask(MapContext context, Latlng offset, Latlng firstPos, float zoom, Double duration, Float easeLinearity) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this._offset = offset;
			this._firstPos = firstPos;
			this._firstZoom = context.getZoom();
			this._offsetZoom = zoom - this._firstZoom;
			this._duration = duration == null ? 0.25 : duration;
			float ease = easeLinearity == null ? 0.5f : easeLinearity.floatValue();
			this._easeOutPower = 1 / Math.max(ease, 0.2f);
		}

		public void runFrame(double progress) {
			double lat = this._firstPos.lat() + this._offset.lat() * progress;
			double lng = this._firstPos.lng() + this._offset.lng() * progress;
			context.setCenter(lat, lng);
			float hzoom = (float) (_firstZoom + _offsetZoom * progress);
			context.setZoom(hzoom);
			EventKit.sendEvent("redraw");
		}

		public void complete() {
			cancel();
		}

		public double easeOut(double t) {
			return 1 - Math.pow(1 - t, this._easeOutPower);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
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
				this.complete();
			}
		}
	}

}
