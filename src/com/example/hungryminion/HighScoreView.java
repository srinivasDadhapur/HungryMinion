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
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;

public class HighScoreView extends View {
	Bitmap highscore;
	InputStream imageStream;
	public HighScoreView(Context context) {
		super(context);
		try {
			imageStream = AllResources.assetManager.open("highscore.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		highscore = BitmapFactory.decodeStream(imageStream);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setTextSize(30);
		Rect dsk = new Rect(0,0,(int) (canvas.getWidth()-10*(AllResources.targetWidth/480.0f)),(int) (canvas.getHeight()-10*(AllResources.targetHeight/800.0f)));
		canvas.drawBitmap(highscore, null,dsk,null);
		canvas.drawText(GameScreen.score+"", 203*(AllResources.targetWidth/480.0f), 298*(AllResources.targetHeight/800.0f), paint);
		
	}
	

}
