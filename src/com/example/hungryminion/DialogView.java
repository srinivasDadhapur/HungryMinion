package com.example.hungryminion;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DialogView extends View {
	Context context;
	Bitmap resume;
	InputStream imageStream;
	public DialogView(Context context) {
		super(context);
		this.context = context;
		try {
			imageStream = AllResources.assetManager.open("resume.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resume = BitmapFactory.decodeStream(imageStream);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Rect dsk = new Rect(0,0,canvas.getWidth(),canvas.getHeight());
		Paint color = new Paint();
		color.setColor(Color.BLUE);
		color.setTextSize(30);
		canvas.drawBitmap(resume, null,dsk, null);
	}

}
