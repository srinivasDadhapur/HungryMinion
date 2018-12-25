package com.example.hungryminion;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class GameOver extends View{
	Bitmap gameOver;
	InputStream imageStream;
	public GameOver(Context context) {
		super(context);
		
		try {
			imageStream = AllResources.assetManager.open("gameover.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gameOver = BitmapFactory.decodeStream(imageStream);
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Rect destination = new Rect(0,0,(int) (canvas.getWidth()-10*(AllResources.targetWidth/480.0f)),(int) (canvas.getHeight()-10*(AllResources.targetHeight/800.0f)));
		canvas.drawBitmap(gameOver, null, destination,null);
	}

}
