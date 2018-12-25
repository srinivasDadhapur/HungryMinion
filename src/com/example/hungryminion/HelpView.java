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
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;

public class HelpView extends View {
	Bitmap help;
	InputStream imageStream;
	public HelpView(Context context){
		super(context);
		try {
			imageStream = AllResources.assetManager.open("help.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		help = BitmapFactory.decodeStream(imageStream);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Rect dsk = new Rect(0,0,(int) (canvas.getWidth()-10*(AllResources.targetWidth/480.0f)),(int) (canvas.getHeight()-10*(AllResources.targetHeight/800.0f)));
		canvas.drawBitmap(help, null,dsk,null);
	}
	

}
