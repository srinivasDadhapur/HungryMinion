package com.example.hungryminion;

import java.io.IOException;

import com.example.pacman.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

public class GameScreen extends Activity{
	static Game gameView;
	Thread newThread;
	View dialogView;
	static int score;
	static DialogView sd;
	Dialog dl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		try {
			gameView = new Game(getApplicationContext());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialogView = new View(this);
		setContentView(gameView);
		//
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		pauseMethod();
	}
	
	
	
	
	public void pauseMethod(){
		
		synchronized(gameView.pauseLock){
			gameView.paused = true;
			if(AllResources.highscore<score){
				AllResources.highscore = score;
			}
		}
		sd = new DialogView(GameScreen.this);
		dl = new Dialog(this.getApplicationContext());
		dl.setTitle("High Scores");
		dl.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dl.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dl.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
		dl.setContentView(sd);
		dl.setCancelable(false);
		dl.show();
		sd.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
				float x = event.getX();float y = event.getY();
				if(x>=167*(AllResources.targetWidth/480.0f) && x<=301*(AllResources.targetWidth/480.0f) && y>=293*(AllResources.targetHeight/800.0f) && y<=320*(AllResources.targetHeight/800.0f)){
					synchronized(GameScreen.gameView.pauseLock){
						GameScreen.gameView.paused = false;
						GameScreen.gameView.pauseLock.notifyAll();
					}
					dl.dismiss();
				}
				if(x>=117*(AllResources.targetWidth/480.0f) && x<=358*(AllResources.targetWidth/480.0f) && y>=373*(AllResources.targetHeight/800.0f) && y<=406*(AllResources.targetHeight/800.0f)){
					synchronized(GameScreen.gameView.pauseLock){
						GameScreen.gameView.paused = true;
						GameScreen.gameView.pauseLock.notifyAll();
					}
					dl.dismiss();
					finish();
				}
				}
				return true;
			}
			});
	}
/*
	public void finishGame() {
		synchronized(gameView.pauseLock){
			gameView.paused = true;
			if(AllResources.highscore<score){
				AllResources.highscore = score;
			}
		}
		finish();
	}
	*/
}

