package com.mapfinal.map.handler;

import java.util.Timer;
import java.util.TimerTask;

import com.mapfinal.event.EventKit;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.map.MapContext;

/**
 * //距离间隔 var direction = this._lastPos.subtract(this._positions[0]), //时间间隔
 * duration = (this._lastTime - this._times[0]) / 1000,
 * //平移动画的曲率因子easeLinearity=0.25 ease = options.easeLinearity,
 * 
 * //速度向量 speedVector = direction.multiplyBy(ease / duration), //速度 speed =
 * speedVector.distanceTo([0, 0]), //限速，inertiaMaxSpeed=1500惯性移动的最大速度，单位是像素每秒.
 * limitedSpeed = Math.min(options.inertiaMaxSpeed, speed), //限速向量
 * limitedSpeedVector = speedVector.multiplyBy(limitedSpeed / speed),
 * //inertiaDeceleration=3000确定惯性移动减速的速率，单位是像素每秒的二次方2. decelerationDuration =
 * limitedSpeed / (options.inertiaDeceleration * ease), offset =
 * limitedSpeedVector.multiplyBy(-decelerationDuration / 2).round();
 * 
 * @author yangyong
 *
 */
public class MoveEase {

	private static final long INTERVAL = 10L;

	// 惯性移动的最大速度，单位是像素每秒.
	private float inertiaMaxSpeed = 1000;
	// 确定惯性移动减速的速率，单位是像素每秒的二次方2.
	private float inertiaDeceleration = 3000;
	// 平移动画的曲率因子
	private float easeLinearity = 0.25f;

	private Timer timer;
	private DragTask task;

	/**
	 * 惯性移动，像素
	 * @param context
	 * @param direction 距离间隔
	 * @param firstPos 初始位置
	 * @param duration 时间间隔
	 */
	public void move(MapContext context, ScreenPoint direction, ScreenPoint firstPos, long duration) {
		// 距离间隔
//		ScreenPoint direction = lastPos.subtract(this._positions.get(0));
//		//System.out.println("direction: " + direction.toString());
//		// 时间间隔
//		double  duration = (lastTime - _times.get(0)) / 1000.0f;
		if(duration > 0 && (Math.abs(direction.x()) > 0 || Math.abs(direction.y()) > 0)) {
			// 平移动画的曲率因子easeLinearity=0.25
			float ease = easeLinearity;
			//System.out.println("duration: " + duration);
			// 速度向量
			ScreenPoint speedVector = direction.multiplyBy(ease / duration);
			//System.out.println("speedVector: " + speedVector.toString());
			// 速度
			double speed = speedVector.distance(ScreenPoint.by(0, 0)) * 2;
			//System.out.println("speed: " + speed);
			// 限速，inertiaMaxSpeed=1500惯性移动的最大速度，单位是像素每秒.
			double limitedSpeed = Math.min(inertiaMaxSpeed, speed);
			//System.out.println("limitedSpeed: " + limitedSpeed);
			// 限速向量
			ScreenPoint limitedSpeedVector = speedVector.multiplyBy(limitedSpeed / speed);
			//System.out.println("limitedSpeedVector: " + limitedSpeedVector.toString());
			// inertiaDeceleration=3000确定惯性移动减速的速率，单位是像素每秒的二次方2.
			double decelerationDuration = limitedSpeed / (inertiaDeceleration * ease);
			//
			ScreenPoint offset = limitedSpeedVector.multiplyBy(-decelerationDuration / 2).round();
			
			//System.out.println("moveUp: " + decelerationDuration + ", " + offset.toString());
			// 开启惯性移动动画
			startMove(context, offset, firstPos, decelerationDuration, ease);
		}
	}

	// 开始惯性动画
	public void startMove(MapContext context, ScreenPoint offset, ScreenPoint firstPos, double decelerationDuration, float ease) {
		if (timer == null) {
			timer = new Timer();
		} else {
			task.cancel();
		}
		task = null;
		task = new DragTask(context, offset, firstPos, decelerationDuration, ease);
		timer.schedule(task, 0, INTERVAL);// 10毫秒一次
	}

	public void stopMove() {
		if (task != null) {
			task.complete();
		}
	}

	public class DragTask extends TimerTask {
		private MapContext context;
		private long _startTime = 0;
		private double _duration;
		private float _easeOutPower;
		//距离间隔
		private ScreenPoint _offset;

		public DragTask(MapContext context, ScreenPoint offset, ScreenPoint firstPos, Double duration, Float easeLinearity) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this._offset = offset;
			this._duration = duration == null ? 0.25 : duration;
			float ease = easeLinearity == null ? 0.5f : easeLinearity.floatValue();
			this._easeOutPower = 1 / Math.max(ease, 0.2f);
		}

		public void runFrame(double progress) {
			ScreenPoint pos = this._offset.clone().multiplyBy(progress);
			context.drag(-pos.getX(), -pos.getY());
			EventKit.sendEvent("redraw");
		}

		public void complete() {
			cancel();
			context.resetCenterForDrag();
			context.drag(0, 0);
			//System.out.println("----------------------------------");
			EventKit.sendEvent("redraw");
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
