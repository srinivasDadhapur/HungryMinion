package com.example.hungryminion;

import java.io.IOException;
import java.io.InputStream;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class HelpScreen extends Activity{
	View dialogView;
	Bitmap helpenemy,helpminion;
	InputStream imageStream;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		dialogView = new DialogView(getApplicationContext());
		setContentView(dialogView);
		
		try {
			imageStream = AllResources.assetManager.open("enemy.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		helpenemy = BitmapFactory.decodeStream(imageStream);
		try {
			imageStream = AllResources.assetManager.open("minion.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		helpminion = BitmapFactory.decodeStream(imageStream);
		
	}
	/*@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
//	@Override
	//public void onBackPressed() {
		//gameView.running = false;
/*		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	    alertDialogBuilder.setMessage("Kichaoke");
	    AlertDialog alertDialog = alertDialogBuilder.create();
	    alertDialog.show();
		View ki = new DialogView(this);
		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setContentView(ki);
		dialog.show();
		//dialog.setOnKeyListener(this);
	}*/
/*	public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		if(arg1==KeyEvent.KEYCODE_BACK){
			gameView.running = true;
			return false;
		}
		return true;
	} */
//}
}

