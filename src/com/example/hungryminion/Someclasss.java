package com.example.hungryminion;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Someclasss extends SurfaceView implements Runnable {
	
	private SurfaceHolder holder;
	Random rand;
	Thread ki;
	public Someclasss(Context context) {
		super(context);
		holder = getHolder();
		rand  = new Random();
		ki = new Thread(this);
		ki.start();
		
	}
	@Override
	public void run() {
		while(true){
			 if(!holder.getSurface().isValid()) 
                 continue; 
			Canvas canvas = holder.lockCanvas();
			canvas.drawRGB(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
			holder.unlockCanvasAndPost(canvas);
		}
		
	}
}
