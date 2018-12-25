package com.example.hungryminion;

import java.io.IOException;
import java.io.InputStream;

import com.example.pacman.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetFileDescriptor;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity  {

	//private GestureDetector gd;

	MainScreen myview;
	// private ImageView img;
	InputStream is;
	AssetFileDescriptor descriptor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
		WindowManager wm = (WindowManager) this.getSystemService(this.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		@SuppressWarnings("deprecation")
		int width = display.getWidth();
		@SuppressWarnings("deprecation")
		int height = display.getHeight();
		AllResources.targetWidth = width;
		AllResources.targetHeight = height;
		AllResources.player = new MediaPlayer();
		AllResources.assetManager = this.getAssets();
		AllResources.prefer = getPreferences(MODE_PRIVATE);
		try {
			descriptor = AllResources.assetManager.openFd("back.mp3");
			AllResources.player.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset() , descriptor.getLength());
			is = AllResources.assetManager.open("back.png");
			AllResources.background = BitmapFactory.decodeStream(is);
			myview = new MainScreen(getApplicationContext());
			AllResources.player.prepare();
			AllResources.player.setLooping(true);
		}catch (Exception e) {
			e.printStackTrace();
		}
		setContentView(myview);
		//gd = new GestureDetector(getApplicationContext(), this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		boolean soundon = AllResources.prefer.getBoolean("soundOn", false);
		if(AllResources.player!=null || !AllResources.player.isPlaying())
			if(soundon)
				AllResources.player.start();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(AllResources.player!=null  || AllResources.player.isPlaying())
			AllResources.player.pause();
		
	}
/*	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gd.onTouchEvent(event);
	}
	@Override
	public void onBackPressed() {
		finish();
	}

	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		float sensitivity = 50;
		if (e1.getY() - e2.getY() > sensitivity) {
			Toast.makeText(getApplicationContext(), "Swipe up",
					Toast.LENGTH_LONG).show();
			return true;
		} else if (e2.getY() - e1.getY() > sensitivity) {
			Toast.makeText(getApplicationContext(), "Swipe Down",
					Toast.LENGTH_LONG).show();
			return true;
		} else if (e1.getX() - e2.getX() > sensitivity) {
			Toast.makeText(getApplicationContext(), "Swipe Left",
					Toast.LENGTH_LONG).show();
			return true;
		} else if (e2.getX() - e1.getX() > sensitivity) {
			Toast.makeText(getApplicationContext(), "Swipe Right",
					Toast.LENGTH_LONG).show();
			return true;
		} else {
			return true;
		}
	}

	public void onLongPress(MotionEvent arg0) {
	}

	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		return false;
	}



	public boolean onSingleTapUp(MotionEvent arg0) {
		return false;
	}

	public void onGesture(GestureOverlayView overlay, MotionEvent event) {

	}

	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

	}

	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {

	}

	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {

	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}*/

}
