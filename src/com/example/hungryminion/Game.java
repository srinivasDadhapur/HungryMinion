package com.example.hungryminion;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Random;

import com.example.pacman.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Game extends SurfaceView implements Runnable, OnGestureListener{

	
	public Object pauseLock;
	public boolean paused;
	volatile boolean running , updatebananas;
	
	GameScreen gameScreen;
	
	int bananaHorizontalCount = 90, bananaVerticalCount = 60;
	
	
	SurfaceHolder holder;
	Thread gameThread;
	private GestureDetector gd;
	Bitmap ob,spritesheet,life,enemyspritesheet;
	Bitmap banana,log,logMini,logVertical,logVerticalMini,bananaHorizontal;
	float x,y;
	Rect src,dst;
	private Context contet;
	float centerX,centerY,centerXb,centerYb;
	String direction="down";
	ArrayList<Bitmap> logs  , bananas , lifes;
	int imgx, imgy, currentFrame = 0 , width ,height , xSpeed =8  , ySpeed = 8;
	Rect logPosition,bananaPosition;
	List<Entry<Integer,Integer>> logPositions , bananaPositions, lifePositions;
	StringBuilder builder;
	long delataTime;
	private EnemyClass enemy1;
	private EnemyClass1 enemy2;
	private EnemyClass2 enemy3;
	private EnemyClass3 enemy4;
	private PlayerClass player;
	Random randomGenerator;
	public Game(Context context) throws IOException {
		super(context);
		this.contet = context;
		holder = getHolder();
		gd = new GestureDetector(context,this);
		updatebananas = true;
		logs = new ArrayList<Bitmap>();
		bananas = new ArrayList<Bitmap>();
		builder = new StringBuilder();
		//
		gameScreen = new GameScreen();
		paused = false;
		pauseLock = new Object();
		running = true;
		randomGenerator = new Random();
		logPositions = new ArrayList<Entry<Integer, Integer>>();
		bananaPositions = new ArrayList<Entry<Integer, Integer>>();
		
		lifePositions = new ArrayList<Entry<Integer,Integer>>();
		
		lifes = new ArrayList<Bitmap>();
		
		
		InputStream is = AllResources.assetManager.open("stuart.png");
		ob = BitmapFactory.decodeStream(is);
		is = AllResources.assetManager.open("banana.png");
		banana = BitmapFactory.decodeStream(is);
		is = AllResources.assetManager.open("log_mini.png");
		logMini = BitmapFactory.decodeStream(is);
		is = AllResources.assetManager.open("log_vertical.png");
		logVertical = BitmapFactory.decodeStream(is);
		is = AllResources.assetManager.open("log_vertical_mini.png");
		logVerticalMini = BitmapFactory.decodeStream(is);
		
		is = AllResources.assetManager.open("enenysprites.png");
		enemyspritesheet = BitmapFactory.decodeStream(is);
		
		
		is = AllResources.assetManager.open("lifes.png");
		life = BitmapFactory.decodeStream(is);
		
		is = AllResources.assetManager.open("banana_hor.png");
		bananaHorizontal = BitmapFactory.decodeStream(is);
		
		is = AllResources.assetManager.open("spritenew.png");
		spritesheet= BitmapFactory.decodeStream(is);
		
		is = AllResources.assetManager.open("log.png");
		log = BitmapFactory.decodeStream(is);
		
		for(int i=0;i<10;i++){
			logs.add(log);
		}
		for(int i=0;i<10;i++){
			logs.add(logMini);
		}
		for(int i=0;i<6;i++){
			logs.add(logVertical);
		}
		for(int i=0;i<12;i++){
			logs.add(logVerticalMini);
		}
		
		

		
		for(int i= 0 ;i<90; i++){
			bananas.add(banana);
		}
		for(int i= 0 ;i<60; i++){
			bananas.add(bananaHorizontal);
		}
		
		int lifeX=(int) (20*(AllResources.targetWidth/480.0f)),lifeY=0;
		for(int lifeat=0;lifeat<3;lifeat++){
			lifePositions.add(new SimpleEntry<Integer,Integer>(lifeX,lifeY));
			lifeX+=30*(AllResources.targetWidth/480.0f);
		}
		//Logs Horizontal
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (88*(AllResources.targetWidth/480.0f)),(int) (47*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (43*(AllResources.targetWidth/480.0f)),(int) (107*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (170*(AllResources.targetWidth/480.0f)),(int) (182*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (108*(AllResources.targetWidth/480.0f)),(int) (320*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (52*(AllResources.targetWidth/480.0f)),(int) (445*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (0*(AllResources.targetWidth/480.0f)),(int) (508*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (129*(AllResources.targetWidth/480.0f)),(int) (580*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (75*(AllResources.targetWidth/480.0f)),(int) (750*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (182*(AllResources.targetWidth/480.0f)),(int) (631*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (268*(AllResources.targetWidth/480.0f)),(int) (527*(AllResources.targetHeight/800.0f))));
		
		//Logs Horizontal mini
		
		//(205,96),(280,50),(361,110),(10,175),(10,714),(278,738),(343,678),(41,600),(333,308),(117,247)
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (234*(AllResources.targetWidth/480.0f)),(int) (102*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (296*(AllResources.targetWidth/480.0f)),(int) (45*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (361*(AllResources.targetWidth/480.0f)),(int) (110*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (0*(AllResources.targetWidth/480.0f)),(int) (175*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (140*(AllResources.targetWidth/480.0f)),(int) (689*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (278*(AllResources.targetWidth/480.0f)),(int) (738*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (415*(AllResources.targetWidth/480.0f)),(int) (678*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (415*(AllResources.targetWidth/480.0f)),(int) (605*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (345*(AllResources.targetWidth/480.0f)),(int) (250*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (185*(AllResources.targetWidth/480.0f)),(int) (255*(AllResources.targetHeight/800.0f))));
		
		//(110,105),(52,231),(270,633),(412,429),(410,182),(335,359),(268,410)
		
		//Logs Vertical
		
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (110*(AllResources.targetWidth/480.0f)),(int) (107*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (52*(AllResources.targetWidth/480.0f)),(int) (238*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (404*(AllResources.targetWidth/480.0f)),(int) (400*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (410*(AllResources.targetWidth/480.0f)),(int) (182*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (335*(AllResources.targetWidth/480.0f)),(int) (349*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (268*(AllResources.targetWidth/480.0f)),(int) (400*(AllResources.targetHeight/800.0f))));

		//(205,43),(412,10),(354,47),(163,110),(333,182),(171,252),(163,384),(194,515),(50,584),(119,584),(66,714),(424,680),
		
		//Logs Vertical mini
		
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (224*(AllResources.targetWidth/480.0f)),(int) (47*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (412*(AllResources.targetWidth/480.0f)),(int) (0*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (351*(AllResources.targetWidth/480.0f)),(int) (55*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (170*(AllResources.targetWidth/480.0f)),(int) (117*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (175*(AllResources.targetWidth/480.0f)),(int) (255*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (189*(AllResources.targetWidth/480.0f)),(int) (390*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (194*(AllResources.targetWidth/480.0f)),(int) (515*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (52*(AllResources.targetWidth/480.0f)),(int) (588*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (119*(AllResources.targetWidth/480.0f)),(int) (580*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (65*(AllResources.targetWidth/480.0f)),(int) (695*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (405*(AllResources.targetWidth/480.0f)),(int) (678*(AllResources.targetHeight/800.0f))));
		logPositions.add(new SimpleEntry<Integer, Integer>((int) (269*(AllResources.targetWidth/480.0f)),(int) (631*(AllResources.targetHeight/800.0f))));
		
		
		for(int lifevar=0;lifevar<=3;lifevar++){
			lifes.add(life);
		}
		int count = 0, bananaX = (int) (15*(AllResources.targetWidth/480.0f)),bananaY = (int) (36*(AllResources.targetHeight/800.0f));
		for(count = 0;count<3;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (15*(AllResources.targetWidth/480.0f));bananaY = (int) (192*(AllResources.targetHeight/800.0f));
		for(count=0;count<7;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (15*(AllResources.targetWidth/480.0f));bananaY = (int) (524*(AllResources.targetHeight/800.0f));
		for(count=0;count<6;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (77*(AllResources.targetWidth/480.0f));bananaY = (int) (154*(AllResources.targetHeight/800.0f));
		for(count=0;count<6;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		//
		bananaX = (int) (82*(AllResources.targetWidth/480.0f));bananaY = (int) (526*(AllResources.targetHeight/800.0f));
		for(count=0;count<5;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (137*(AllResources.targetWidth/480.0f));bananaY = (int) (124*(AllResources.targetHeight/800.0f));
		for(count=0;count<4;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (144*(AllResources.targetWidth/480.0f));bananaY = (int) (333*(AllResources.targetHeight/800.0f));
		for(count=0;count<2;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (218*(AllResources.targetWidth/480.0f));bananaY = (int) (335*(AllResources.targetHeight/800.0f));
		for(count=0;count<5;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (234*(AllResources.targetWidth/480.0f));bananaY = (int) (643*(AllResources.targetHeight/800.0f));
		for(count=0;count<3;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (367*(AllResources.targetWidth/480.0f));bananaY = (int) (543*(AllResources.targetHeight/800.0f));
		for(count=0;count<5;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		
		bananaX = (int) (292*(AllResources.targetWidth/480.0f));bananaY = (int) (197*(AllResources.targetHeight/800.0f));
		for(count=0;count<7;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		
		bananaX = (int) (361*(AllResources.targetWidth/480.0f));bananaY = (int) (266*(AllResources.targetHeight/800.0f));
		for(count=0;count<6;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		
		bananaX = (int) (435*(AllResources.targetWidth/480.0f));bananaY = (int) (3*(AllResources.targetHeight/800.0f));
		for(count=0;count<13;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (195*(AllResources.targetWidth/480.0f));bananaY = (int) (68*(AllResources.targetHeight/800.0f));
		for(count=0;count<2;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (321*(AllResources.targetWidth/480.0f));bananaY = (int) (58*(AllResources.targetHeight/800.0f));
		for(count=0;count<4;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (372*(AllResources.targetWidth/480.0f));bananaY = (int) (158*(AllResources.targetHeight/800.0f));
		for(count=0;count<2;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (441*(AllResources.targetWidth/480.0f));bananaY = (int) (687*(AllResources.targetHeight/800.0f));
		for(count=0;count<2;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (153*(AllResources.targetWidth/480.0f));bananaY = (int) (494*(AllResources.targetHeight/800.0f));
		for(count=0;count<2;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		bananaX = (int) (373*(AllResources.targetWidth/480.0f));bananaY = (int) (10*(AllResources.targetHeight/800.0f));
		for(count=0;count<2;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaY+=45*(AllResources.targetHeight/800.0f);
		}
		//86
		
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (290*(AllResources.targetWidth/480.0f)), (int) (646*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (330*(AllResources.targetWidth/480.0f)), (int) (646*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (147*(AllResources.targetWidth/480.0f)), (int) (625*(AllResources.targetHeight/800.0f))));	
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (326*(AllResources.targetWidth/480.0f)), (int) (283*(AllResources.targetHeight/800.0f))));
		
		
		
		//Horizontal Bananas
		bananaX = (int) (48*(AllResources.targetWidth/480.0f));bananaY = (int) (70*(AllResources.targetHeight/800.0f));
		for(count=0;count<3;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		
		bananaX = (int) (45*(AllResources.targetWidth/480.0f));bananaY = (int) (469*(AllResources.targetHeight/800.0f));
		for(count=0;count<4;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		
		bananaX = (int) (165*(AllResources.targetWidth/480.0f));bananaY = (int) (208*(AllResources.targetHeight/800.0f));
		for(count=0;count<3;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		
		bananaX = (int) (236*(AllResources.targetWidth/480.0f));bananaY = (int) (546*(AllResources.targetHeight/800.0f));
		for(count=0;count<3;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		
		bananaX = (int) (134*(AllResources.targetWidth/480.0f));bananaY = (int) (600*(AllResources.targetHeight/800.0f));
		for(count=0;count<5;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		
		bananaX = (int) (40*(AllResources.targetWidth/480.0f));bananaY = (int) (769*(AllResources.targetHeight/800.0f));
		for(count=0;count<10;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		
		bananaX = (int) (188*(AllResources.targetWidth/480.0f));bananaY = (int) (136*(AllResources.targetHeight/800.0f));
		for(count=0;count<5;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}


		bananaX = (int) (233*(AllResources.targetWidth/480.0f));bananaY = (int) (70*(AllResources.targetHeight/800.0f));
		for(count=0;count<2;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		
		bananaX = (int) (113*(AllResources.targetWidth/480.0f));bananaY = (int) (662*(AllResources.targetHeight/800.0f));
		for(count=0;count<3;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		bananaX = (int) (113*(AllResources.targetWidth/480.0f));bananaY = (int) (718*(AllResources.targetHeight/800.0f));
		for(count=0;count<3;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		
		bananaX = (int) (270*(AllResources.targetWidth/480.0f));bananaY = (int) (712*(AllResources.targetHeight/800.0f));
		for(count=0;count<2;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		
		bananaX = (int) (393*(AllResources.targetWidth/480.0f));bananaY = (int) (642*(AllResources.targetHeight/800.0f));
		for(count=0;count<3;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		bananaX = (int) (194*(AllResources.targetWidth/480.0f));bananaY = (int) (282*(AllResources.targetHeight/800.0f));
		for(count=0;count<2;count++){
			bananaPositions.add(new SimpleEntry<Integer,Integer>(bananaX, bananaY));
			bananaX+=45*(AllResources.targetWidth/480.0f);
		}
		//48
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (245*(AllResources.targetWidth/480.0f)), (int) (338*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (398*(AllResources.targetWidth/480.0f)), (int) (560*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (39*(AllResources.targetWidth/480.0f)), (int) (534*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (106*(AllResources.targetWidth/480.0f)), (int) (534*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (390*(AllResources.targetWidth/480.0f)), (int) (344*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (245*(AllResources.targetWidth/480.0f)), (int) (365*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (171*(AllResources.targetWidth/480.0f)), (int) (344*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (108*(AllResources.targetWidth/480.0f)), (int) (344*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (108*(AllResources.targetWidth/480.0f)), (int) (390*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (44*(AllResources.targetWidth/480.0f)), (int) (400*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (40*(AllResources.targetWidth/480.0f)), (int) (666*(AllResources.targetHeight/800.0f))));
		bananaPositions.add(new SimpleEntry<Integer,Integer>((int) (100*(AllResources.targetWidth/480.0f)), (int) (276*(AllResources.targetHeight/800.0f))));
		
		
		
		this.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				gd.onTouchEvent(arg1);
				return true;
			}
		});
		player = new PlayerClass(logPositions,spritesheet,ob);
		
		enemy1 = new EnemyClass(logPositions,enemyspritesheet,ob,0,0,"left",2,2,player);
		enemy2 = new EnemyClass1(logPositions,enemyspritesheet,ob,100,0,"down",2,2,player);
		enemy3 = new EnemyClass2(logPositions,enemyspritesheet,ob,100,0,"up",2,2,player);
		enemy4 = new EnemyClass3(logPositions,enemyspritesheet,ob,100,0,"right",2,2,player);


		gameThread = new Thread(this);
		
		gameThread.start();
	}

	long startTime = System.nanoTime();
	String lastDirection = "down";
	
	@Override
	public void run() {
		while(running){
			if(!holder.getSurface().isValid())
				continue;
			
		try {
			Thread.sleep(30);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		startTime = System.nanoTime();
		Canvas canvas = holder.lockCanvas();
		startTime = System.nanoTime();
		canvas.drawColor(Color.WHITE);
		Paint tex = new Paint();
		tex.setColor(Color.BLACK);
		tex.setTextSize(25);
		tex.setColor(Color.BLUE);
		tex.setTextAlign(Align.CENTER);
		drawLogs(canvas);
		drawLogsMini(canvas);
		drawLogsVertical(canvas);
		drawLogsVerticalMini(canvas);
		drawLifes(canvas);
		drawBananas(canvas);
		//canvas.drawText("Srinivas Dadhapur", canvas.getWidth()/2, 100, tex);
	//	canvas.drawBitmap(banana, 123,312, null);
		//Rect src = new Rect(0,0,30,32);
		//Rect dst = new Rect((int)x,(int)y,(int)x+ob.getWidth(),(int)y+ob.getHeight());
		//canvas.drawBitmap(spritesheet, src,dst, null);
		canvas.drawText("Score: "+GameScreen.score,202*(AllResources.targetWidth/480.0f),17*(AllResources.targetHeight/480.0f),tex);
	//	canvas.drawText("Banana X and x+center "+centerXb+" And "+(x+centerXb),canvas.getWidth()-120,200,tex);
		/*if( (((x+centerX)-(x+centerXb))<= (ob.getWidth()+banana.getWidth())/2f)  && ((y+centerY)-(y+centerYb))<=((ob.getHeight()+banana.getHeight())/2f) )
		{
			Toast.makeText(contet.getApplicationContext(), "Swipe up",
					Toast.LENGTH_LONG).show();
			//System.out.println("Kichaoke here");
		}*/
		/*int y = 0;
		while(y<30){
			for(Entry<Integer , Integer> kyes: positions){
				int xX = kyes.getKey();int  xY = kyes.getValue();
				canvas.drawBitmap(logs.get(0), xX,xY, null);
			}
			y++;
		}*/
	
		try{
		player.PlayerMovement(canvas);
		}catch(Exception e){
		}
		removeBananas();
		enemy1.enemyMovement(canvas);
		enemy2.enemyMovement(canvas);
		enemy3.enemyMovement(canvas);
		enemy4.enemyMovement(canvas);
		if(player.dst.intersect(enemy1.enemyPosition) || player.dst.intersect(enemy2.enemyPosition) || player.dst.intersect(enemy3.enemyPosition) || player.dst.intersect(enemy4.enemyPosition)){
			player.x = 0;
			player.y = 0;
			removeLifes();
		}
		/*Rect mid = new Rect();
		mid.set(0, 400, canvas.getWidth(), 550);
		Paint ourble = new Paint();
		ourble.setColor(Color.BLUE);
		canvas.drawRect(mid, ourble);*/
		long endTime = System.nanoTime();
		long delta = endTime-startTime;
		EnemyClass.timelapsed+=delta;
		EnemyClass1.timelapsed+=delta;
		EnemyClass2.timelapsed+=delta;
		EnemyClass3.timelapsed+=delta;
		holder.unlockCanvasAndPost(canvas);
		
		synchronized(pauseLock){
			while(paused){
				try {
					pauseLock.wait();
				} catch (InterruptedException e) {
				}
			}
			
		}
		}
		
	}
	
	
	private void removeLifes() {
			if(!lifePositions.isEmpty()){
				lifePositions.remove(lifePositions.size()-1);
				lifes.remove(lifes.size()-1);
			}
			else{
				gameOver();
			}
		return;
	}

	private void gameOver() {
		synchronized(pauseLock){
			paused = true;
			pauseLock.notifyAll();
		}
		Thread newThread = new Thread(){
			GameOver sd;
			Dialog dl;
			public void run() {
				gameScreen.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try{
						sd = new GameOver(contet);
						dl = new Dialog(contet);
						}catch(Exception sd){
							
						}
						dl.setTitle("High Scores");
						dl.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
						dl.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
						dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
						//dl.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
						dl.setContentView(sd);
						dl.show();
					}
				});
			}
		};
		newThread.start();
	}


	private void drawLifes(Canvas canvas) {
		Rect destinationRect;
		int i=0;
		while(i<lifePositions.size()){
			for(Entry<Integer , Integer> kyes: lifePositions){
				int xX = kyes.getKey();int  xY = kyes.getValue();
				destinationRect = new Rect(xX,xY,(int) (xX+20*(AllResources.targetWidth/480.0f)),(int) (xY+25*(AllResources.targetHeight/800.0f)));
				canvas.drawBitmap(lifes.get(i),null,destinationRect, null);
			}
			i++;
	}
	}
	
	private void removeBananas() {

		if(bananas.isEmpty()){
			gameOver();
		}
			boolean countFlag = true;
			int bananapos = 0;
//			banan = new Rect((Integer)me.getKey() , (Integer)me.getValue() , (Integer)me.getKey()+134 , (Integer)me.getValue()+35);
			for(Entry<Integer , Integer> kichak: bananaPositions){
				//log(kichak.getKey()+" , "+ kichak.getValue());
				int key = kichak.getKey();int val = kichak.getValue();
				if(bananapos<bananaHorizontalCount){
					bananaPosition = new Rect(key,val,(int) (key+(20*(AllResources.targetWidth/480.0f))),(int) (val+(40*(AllResources.targetHeight/800.0f))));
				}
				else if(bananapos>=bananaHorizontalCount){
					bananaPosition = new Rect(key,val,(int) (key+(40*(AllResources.targetWidth/480.0f))),(int) (val+(20*(AllResources.targetHeight/800.0f))));
					countFlag = false;
				}
				if(bananaPosition.intersect(player.dst)){
					bananas.remove(bananapos);
					bananaPositions.remove(bananapos);
					GameScreen.score+=10;
					if(countFlag){
						bananaHorizontalCount-=1;
					}
					//log("Banana Collision and Removed banana"+bananapos);
					return;
				}
//			banan = new Rect(0,0,200,200);
		/*	if(banan.intersect(dst)){
				canvas.drawText("Collisione Kichaoke", 200, 400, tex);
				ySpeed = 0;
			}else{
				ySpeed = 5;
			}*/		
			bananapos++;
		}
	}
	private void drawBananas(Canvas canvas) {
		int i = 0;
		Rect destinationRect = null;
		while(i<bananaPositions.size()){
			Entry<Integer,Integer> kyes =  bananaPositions.get(i);
			int xX = kyes.getKey();int  xY = kyes.getValue();
			if(i<bananaHorizontalCount){
			destinationRect = new Rect(xX,xY,xX+(int) (20*(AllResources.targetWidth/480.0f)),xY+(int) (40*(AllResources.targetHeight/800.0f)));
			canvas.drawBitmap(bananas.get(i),null,destinationRect, null);
			}
			else if(i>=bananaHorizontalCount){
				destinationRect = new Rect(xX,xY,xX+(int) (40*(AllResources.targetWidth/480.0f)),xY+(int) (20*(AllResources.targetHeight/800.0f)));
				canvas.drawBitmap(bananas.get(i),null,destinationRect, null);
			}
			i++;
	}
	}

	private void drawLogs(Canvas canvas) {
		Rect destinationRect;
		int i = 0;
		while(i<10){
			Entry<Integer,Integer> keys = logPositions.get(i);
			int xX = keys.getKey();int  xY = keys.getValue();
			destinationRect = new Rect(xX,xY,xX+(int)(137*(AllResources.targetWidth/480.0f)),xY+(int) (10*(AllResources.targetHeight/800.0f)));
			canvas.drawBitmap(logs.get(i),null,destinationRect, null);
			i++;
	}
}
	
	private void drawLogsMini(Canvas canvas) {
		Rect destinationRect;
		int i = 10;
		while(i<20){
			Entry<Integer,Integer> keys = logPositions.get(i);
			int xX = keys.getKey();int  xY = keys.getValue();
			destinationRect = new Rect(xX,xY,xX+(int)(65*(AllResources.targetWidth/480.0f)),xY+(int) (10*(AllResources.targetHeight/800.0f)));
			canvas.drawBitmap(logs.get(i),null,destinationRect, null);
			i++;
	}
}
	private void drawLogsVertical(Canvas canvas) {
		Rect destinationRect;
		int i = 20;
		while(i<26){
			Entry<Integer,Integer> keys = logPositions.get(i);
			int xX = keys.getKey();int  xY = keys.getValue();
			destinationRect = new Rect(xX,xY,xX+(int)(10*(AllResources.targetWidth/480.0f)),xY+(int) (137*(AllResources.targetHeight/800.0f)));
			canvas.drawBitmap(logs.get(i),null,destinationRect, null);
			i++;
	}
}
	
	private void drawLogsVerticalMini(Canvas canvas) {
		Rect destinationRect;
		int i = 26;
		while(i<38){
			Entry<Integer,Integer> keys = logPositions.get(i);
			int xX = keys.getKey();int  xY = keys.getValue();
			destinationRect = new Rect(xX,xY,xX+(int)(10*(AllResources.targetWidth/480.0f)),xY+(int) (65*(AllResources.targetHeight/800.0f)));
			canvas.drawBitmap(logs.get(i), null,destinationRect, null);
			i++;
	}
}
	

	private void log(String st){
		Log.d("Cutom Log",st);
		builder.append(st);
		builder.append("\n");
		
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		float sensitivity = 70;
		player.lastDirection = player.direction;
		if (e1.getY() - e2.getY() > sensitivity) {
			player.direction = "up";
		//	Toast.makeText(contet.getApplicationContext(), "Swipe up",
			//		Toast.LENGTH_LONG).show();
			return true;
		} else if (e2.getY() - e1.getY() > sensitivity) {
			player.direction = "down";
			//Toast.makeText(contet.getApplicationContext(), "Swipe Down",
				//	Toast.LENGTH_LONG).show();
			return true;
		} else if (e1.getX() - e2.getX() > sensitivity) {
			player.direction = "left";
			//Toast.makeText(contet.getApplicationContext(), "Swipe Left",
				//	Toast.LENGTH_LONG).show();
			return true;
		} else if (e2.getX() - e1.getX() > sensitivity) {
			player.direction = "right";
			//Toast.makeText(contet.getApplicationContext(), "Swipe Right",
				//	Toast.LENGTH_LONG).show();
			return true;
		} else {
			return true;
		}
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
