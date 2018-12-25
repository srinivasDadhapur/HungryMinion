package com.example.hungryminion;

import java.io.IOException;
import java.io.InputStream;

import com.example.pacman.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainScreen extends View{
	Context conte;
	StringBuilder builder = new StringBuilder();
	Editor editor;
	Bitmap music_on,music_off,helpminion,helpenemy;
	InputStream imageStream;
	Canvas canvas;
	public MainScreen(Context context) throws IOException {
		super(context);
		this.conte = context;
		imageStream = AllResources.assetManager.open("musicOn.png");
		music_on = BitmapFactory.decodeStream(imageStream);
		imageStream = AllResources.assetManager.open("musicOff.png");
		music_off = BitmapFactory.decodeStream(imageStream);
	}
	
	
	
	//y70 y 98 x100 x170 
	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas){
		this.canvas = canvas;
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(DRAWING_CACHE_QUALITY_HIGH);
		paint.setTextSize(40);
		Rect dsk = new Rect(0,0,canvas.getWidth(),canvas.getHeight());
		this.canvas.drawBitmap(AllResources.background,null,dsk,null);
		if(AllResources.prefer.getBoolean("soundOn", false)){
			this.canvas.drawBitmap(music_on,324*(AllResources.targetWidth/480.0f),515*(AllResources.targetHeight/800.0f),null);
		}
		else{
			this.canvas.drawBitmap(music_off,324*(AllResources.targetWidth/480.0f),515*(AllResources.targetHeight/800.0f),null);
		}
	}
	
	private void log(String st){
		Log.d("Sound Test",st);
		builder.append(st);
		builder.append("\n");
		
	}

	
	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
				float x = event.getX();float y = event.getY();
				if(x>=184*(AllResources.targetWidth/480.0f) && x<=273*(AllResources.targetWidth/480.0f) && y>=267*(AllResources.targetHeight/800.0f) && y<=315*(AllResources.targetHeight/800.0f)){
					Intent intent = new Intent(conte,GameScreen.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					conte.startActivity(intent);
				}
				if(x>=185*(AllResources.targetWidth/480.0f) && x<=270*(AllResources.targetWidth/480.0f) && y>=487*(AllResources.targetHeight/800.0f) && y<=536*(AllResources.targetHeight/800.0f)){
					HelpView ss = null;
					ss = new HelpView(conte);
					Dialog dl = new Dialog(conte);
					dl.setTitle("How To Play");
					//dl.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
					dl.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
					dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dl.setContentView(ss);
					dl.show();
				}
				if(x>=123*(AllResources.targetWidth/480.0f) && x<=345*(AllResources.targetWidth/480.0f) && y>=377*(AllResources.targetHeight/800.0f) && y<=423*(AllResources.targetHeight/800.0f)){
					HighScoreView ss = new HighScoreView(conte);
					Dialog dl = new Dialog(conte);
					dl.setTitle("High Scores");
					dl.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
					dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
					//dl.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
					dl.setContentView(ss);
					dl.show();
				}
				if(x>=324*(AllResources.targetWidth/480.0f) && x<=384*(AllResources.targetWidth/480.0f) && y>=515*(AllResources.targetHeight/800.0f) && y<=595*(AllResources.targetHeight/800.0f)){
					boolean sound = AllResources.prefer.getBoolean("soundOn", false);//false
					if(!sound){
					editor = AllResources.prefer.edit();
					editor.putBoolean("soundOn", true);
					editor.commit();
					log("Sound is On");
					if(AllResources.player!=null && !AllResources.player.isPlaying() && AllResources.prefer.getBoolean("soundOn", false)){
						AllResources.player.start();
						canvas.drawBitmap(music_on,324*(AllResources.targetWidth/480.0f),515*(AllResources.targetHeight/800.0f), null);
						invalidate();
						}
					}
					else{
						editor = AllResources.prefer.edit();
						editor.putBoolean("soundOn", false);
						editor.commit();
						log("Sound is OFF");
						if(AllResources.player!=null && AllResources.player.isPlaying() && !AllResources.prefer.getBoolean("soundOn", false)){
							AllResources.player.pause();
							canvas.drawBitmap(music_off,324*(AllResources.targetWidth/480.0f),515*(AllResources.targetHeight/800.0f), null);
							invalidate();
						}
		
					}
				}
				
			}
				
				//log("Events "+(event.getX())+"  "+(event.getY()));
				return true;
			}
		});
		}
}
