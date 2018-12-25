package com.example.hungryminion;

import com.example.pacman.R;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class IamSri extends SurfaceView implements android.view.GestureDetector.OnGestureListener,Runnable{

	SurfaceHolder holder;
	Thread ki;
	private GestureDetector gd;
	Bitmap ob;
	Bitmap banana;
	float x,y;
	private Context contet;
	float centerX,centerY,centerXb,centerYb;
	@SuppressWarnings("deprecation")
	public IamSri(Context context) {
		super(context);
		this.contet = context;
		ob = BitmapFactory.decodeResource(getResources(), R.drawable.stuart);
		banana = BitmapFactory.decodeResource(getResources(), R.drawable.banana);
		x=0;y=0;
		gd = new GestureDetector(context,this);
		holder = getHolder();
		centerY  = ob.getHeight()/2;
		centerX  = ob.getWidth()/2;
		centerXb = banana.getWidth()/2;
		centerYb = banana.getHeight()/2;
		ki = new Thread(this);
		this.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				gd.onTouchEvent(arg1);
				return true;
			}
		});
		ki.start();
	}

	String direction="down";
	@Override
	public void run() {
		while(true){
			if(!holder.getSurface().isValid())
				continue;
		Canvas canvas = holder.lockCanvas();
		canvas.drawColor(Color.CYAN);
		Paint tex = new Paint();
		tex.setColor(Color.BLACK);
		tex.setTextSize(15);
		tex.setTextAlign(Align.CENTER);
		
		//canvas.drawText("Srinivas Dadhapur", canvas.getWidth()/2, 100, tex);
	//	canvas.drawBitmap(banana, 123,312, null);
		canvas.drawBitmap(ob,x, y, null);
		canvas.drawText("Stuart X and Y "+x+" And "+y,canvas.getWidth()-120,100,tex);
	//	canvas.drawText("Banana X and x+center "+centerXb+" And "+(x+centerXb),canvas.getWidth()-120,200,tex);
		/*if( (((x+centerX)-(x+centerXb))<= (ob.getWidth()+banana.getWidth())/2f)  && ((y+centerY)-(y+centerYb))<=((ob.getHeight()+banana.getHeight())/2f) )
		{
			Toast.makeText(contet.getApplicationContext(), "Swipe up",
					Toast.LENGTH_LONG).show();
			//System.out.println("Kichaoke here");
		}*/
		if(direction.equals("up")){
			if(y>=0){
				y-=5;
			}
		}
		else if(direction.equals("right")){
			if(x<canvas.getWidth()-ob.getWidth()){
				x+=5;
			}
		}
		else if(direction.equals("left")){
			if(x>=0){
				x-=5;
			}
		}
		else if(direction.equals("down")){
			if(y<canvas.getHeight()-ob.getHeight()){
				y+=5;
			}
		}
		/*Rect mid = new Rect();
		mid.set(0, 400, canvas.getWidth(), 550);
		Paint ourble = new Paint();
		ourble.setColor(Color.BLUE);
		canvas.drawRect(mid, ourble);*/
		holder.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		float sensitivity = 50;
		if (e1.getY() - e2.getY() > sensitivity) {
			direction = "up";
		//	Toast.makeText(contet.getApplicationContext(), "Swipe up",
			//		Toast.LENGTH_LONG).show();
			return true;
		} else if (e2.getY() - e1.getY() > sensitivity) {
			direction = "down";
			//Toast.makeText(contet.getApplicationContext(), "Swipe Down",
				//	Toast.LENGTH_LONG).show();
			return true;
		} else if (e1.getX() - e2.getX() > sensitivity) {
			direction = "left";
			//Toast.makeText(contet.getApplicationContext(), "Swipe Left",
				//	Toast.LENGTH_LONG).show();
			return true;
		} else if (e2.getX() - e1.getX() > sensitivity) {
			direction = "right";
			//Toast.makeText(contet.getApplicationContext(), "Swipe Right",
				//	Toast.LENGTH_LONG).show();
			return true;
		} else {
			return true;
		}
	}


	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
