package com.mapfinal.event.listener;

import java.util.Timer;
import java.util.TimerTask;

import com.mapfinal.event.Event;
import com.mapfinal.event.EventKit;
import com.mapfinal.event.EventListener;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.kit.StringKit;
import com.mapfinal.map.MapContext;

/**
 * 未完成
 * @author yangyong
 *
 */
public class MapMoveAnimationListener implements EventListener {

	private static final long INTERVAL = 10L;
	
	private float x0 = 0, y0 = 0;
	private boolean bMove = false;
	private Timer timer;
	private DragTask task;
	//速度
	private float velocity = 0;
	//上一次的时间
	private long lastTime;
	//上一次的位置
	private float lx, ly;
	private int vi = 1;
	
	@Override
	public boolean onEvent(Event event) {
		// TODO Auto-generated method stub
		if (StringKit.isBlank(event.getAction()))
			return false;
		MapContext context = event.get("map");
		switch (event.getAction()) {
		case "mouseDown":
			context.setZoomScale(false);
			ScreenPoint sp = event.get("screenPoint");
			x0 = sp.getX();
			y0 = sp.getY();
			velocity = 0;
			lx = x0;
			ly = y0;
			bMove = true;
			lastTime = System.currentTimeMillis();
			stopMove();
			return true;
		case "mouseUp":
			if (bMove) {
				bMove = false;
				if(!context.isZoomScale()) {
					//开启惯性移动动画
					ScreenPoint spm = event.get("screenPoint");
					double radians = GetAzimuth(x0, y0, spm.getX(), spm.getY());
					startMove(event, x0, y0, radians);
					//context.resetCenterForDrag();
					//System.out.println("[GeoMap.onEvent] mouseUp: " + dx + ", " + dy);
				}
//				context.setZoomScale(false);
//				context.drag(0, 0);
//				// System.out.println("[GeoMap.onEvent] mouseUp");
//				EventKit.sendEvent("redraw");
			}
			return true;
		case "mouseMove":
			if (bMove && !context.isZoomScale()) {
				ScreenPoint spm = event.get("screenPoint");
				context.drag(spm.getX() - x0, spm.getY() - y0);
				// System.out.println("[GeoMap.onEvent] move x: " + x + ", y: "
				// + y + ", x0: " + x0 + ", y0: " + y0);
				// System.out.println("[GeoMap.onEvent] move dx: " + dx + ", dy:
				// " + dy);
				long currentTime = System.currentTimeMillis();
				//计算速度
				computeVelocity(spm.getX() - lx, spm.getY() - lx, currentTime - lastTime);
				lx = spm.getX();
				ly = spm.getY();
				lastTime = currentTime;
				EventKit.sendEvent("redraw");
			}
			return true;
		default:
			break;
		}
		return false;
	}
	
	//计算速度,像素/毫秒
	public void computeVelocity(float dx, float dy, long dt) {
		vi++;
		float dv = (float) (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) / dt);
		dv /= vi;
		velocity += dv;
//		System.out.println("velocity: " + velocity);
	}
	//开始惯性动画
	public void startMove(Event event, float x0, float y0, double radians) {
		if(timer==null) {
			timer = new Timer();
		} else {
			task.cancel();
		}
		task = null;
		task = new DragTask(event, x0, y0, radians, velocity);
		timer.schedule(task, 0, INTERVAL * 10);//100毫秒一次
	}
	
	public void stopMove() {
		if(task!=null) {
			task.cancel();
		}
	}
	
	public double GetAzimuth(double cx, double cy, double px, double py) {
        double dAzimuth = 0;
        dAzimuth = Math.atan2(py - cy, px - cx);
        double angle = dAzimuth  * 180 / Math.PI;
        if (angle < 0)
        	angle += 360;
        if(angle > 360)
        	angle -= 360;
//        System.out.println("angle: " + angle);
        return dAzimuth;
    }
	
	public class DragTask extends TimerTask {
		
		private float x0, y0;
		private float cx, cy;
		private float velocity = 0;
		private MapContext context;
		private double radians;
		private boolean isRun = true;
//		private int i = 0;
		private float mFriction = -4.2F;
		private float dist;
		private double cosa = 1;
		private double sina = 1;
		
		public DragTask(Event event, float x0, float y0, double radians, float velocity) {
			// TODO Auto-generated constructor stub
			this.x0 = x0;
			this.y0 = y0;
			this.context = event.get("map");
			this.velocity = velocity;
			ScreenPoint spm = event.get("screenPoint");
			this.cx = spm.getX();
			this.cy = spm.getY();
			dist = (float) (Math.sqrt(Math.pow((cy-y0), 2) + Math.pow((cx-x0), 2)));
			this.radians = radians;//angle * Math.PI / 180;
			this.cosa = Math.cos(radians);
			this.sina = Math.sin(radians);
//			System.out.println("radians: " + this.radians);
			System.out.println("velocity: " + velocity);
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
//			System.out.println("task index: " + i);
//			i++;
			if(!isRun) {
				return;
			}
			
			velocity = (float) (velocity * Math.exp((INTERVAL / 1000.0F * 30F * -4.2F)));
			
			dist = (float) ((dist - velocity / this.mFriction)
					+ (velocity / this.mFriction) * Math.exp((this.mFriction * (float) INTERVAL / 1000.0F)));
			
//			System.out.println("v: " + velocity);
//			System.out.println("vcx: " + vcx + ", cx: " + cx);
//			System.out.println("vcy: " + vcy + ", cy: " + cy);
			
			if(Math.abs(velocity) < 0.1) {
				cancel();
				isRun = false;
				context.resetCenterForDrag();
				context.setZoomScale(false);
				context.drag(0, 0);
				// System.out.println("[GeoMap.onEvent] mouseUp");
				EventKit.sendEvent("redraw");
				return;
			}
			
//          double vx = Math.cos(radians) * velocity;
//          double vy = Math.sin(radians) * velocity;
//          
//			float dx = (float) (vx * INTERVAL * 0.4);
//			float dy = (float) (vy * INTERVAL * 0.4);
//			
//			System.out.println("dx: " + dx + ", dy: " + dy);
			
//			if(Math.abs(dx) < 1 && Math.abs(dy) < 1) {
//				cancel();
//				isRun = false;
//				return;
//			}
			
			double vx = cosa * dist;
			double vy = sina * dist;
	        cx += vx;
			cy += vy;
			context.drag(cx - x0, cy - y0);
			EventKit.sendEvent("redraw");
		}
	}

}
