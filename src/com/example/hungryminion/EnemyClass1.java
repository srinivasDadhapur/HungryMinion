package com.example.hungryminion;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class EnemyClass1{
	float x,y;
	int xSpeed,ySpeed;
	String enemyDirection,lastDirection;
	Bitmap ob,spritesheet;
	int width,currentFrame,imgx;
	List<Entry<Integer,Integer>> logPositions;
	String[] directions = {"up","down","left","right"};
	private PlayerClass player;
	private String futureDirection;
	Random randomDirection;
	String elapsedDirection;
	public static  long timelapsed;
	public Rect enemyPosition;
	public EnemyClass1(List<Entry<Integer,Integer>>logpos , Bitmap sprite , Bitmap ob,float x,float y,String enemyDirection,int xSpeed,int ySpeed, PlayerClass player2){
		this.x = 100*(AllResources.targetWidth/480.0f);
		this.y = 354*(AllResources.targetHeight/800.0f);
		this.enemyDirection = enemyDirection;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		currentFrame = 0;
		this.ob = ob;
		this.spritesheet = sprite;
		this.logPositions = logpos;
		this.player = player2;
		randomDirection = new Random();
	}
	public void enemyMovement(Canvas canvas){
		Rect src;
		enemyPosition = new Rect((int)x,(int)y,(int) ((int)x+35*(AllResources.targetWidth/480.0f)),(int) ((int)y+37*(AllResources.targetHeight/800.0f)));

		String collision = enemyWalkCollisions(enemyPosition);
		if(collision!=null){
			futureDirection = enemyDirection;
			int ppos = randomDirection.nextInt(4);
			enemyDirection = directions[ppos];
			Log.d("CustomLog", enemyDirection);
		}
		else if((timelapsed%900000==0) && collision==null ){
			int ppos = randomDirection.nextInt(4);
			enemyDirection = directions[ppos];
			timelapsed = 0;

		}
		if(enemyDirection.equals("up")){
			width  = spritesheet.getWidth()/5;
			currentFrame = ++currentFrame % 5;
			imgx = currentFrame*width;
			src = new Rect(imgx,300,imgx+width,400);
			//enemyPosition = new Rect((int)x,(int)y,(int)x+width,(int)y+37);
			canvas.drawBitmap(spritesheet,src, enemyPosition, null);
			if(y>=8){
				y=y-ySpeed;
			}
			if(y<=8){
				enemyDirection = "down";
			}
		}
		else if(enemyDirection.equals("right")){
			
			width  = spritesheet.getWidth()/5;
			currentFrame = ++currentFrame % 5;
			imgx = currentFrame*width;
			src = new Rect(imgx,0,imgx+width,100);
			canvas.drawBitmap(spritesheet,src, enemyPosition, null);
			if(x<=(canvas.getWidth()-ob.getWidth())){
				x=x+xSpeed;
			}
			if(x>=(canvas.getWidth()-ob.getWidth())){
				enemyDirection = "left";
			}
		}
		else if(enemyDirection.equals("left")){
			width  = spritesheet.getWidth()/5;
			currentFrame = ++currentFrame % 5;
			imgx = currentFrame*width;
			src = new Rect(imgx,100,imgx+width,200);
			//enemyPosition = new Rect((int)x,(int)y,(int)x+width,(int)y+37);
			canvas.drawBitmap(spritesheet,src, enemyPosition, null);
			if(x>=8){
				x=x-xSpeed;
			}
			if(x<=8){
				enemyDirection = "right";
			}
		}
		else if(enemyDirection.equals("down")){
			width  = spritesheet.getWidth()/5;
			currentFrame = ++currentFrame % 5;
			imgx = currentFrame*width;
			src = new Rect(imgx,200,imgx+width,300);
			canvas.drawBitmap(spritesheet,src, enemyPosition, null);
		/*	if(checkCollisions()){
				ySpeed = 0;
			}
			else{
				ySpeed = 8;
			}*/
			if(y<=canvas.getHeight()-ob.getHeight()){
				y=y+ ySpeed;
			}
			if(y>=(canvas.getHeight()-ob.getHeight())){
				enemyDirection = "up";
			}
		}
	}
	private boolean inRange(int value,int min,int max){
		if(value>=min && value<=max){
			return true;
		}
		return false;
	}
	private boolean inRect(int x,int y, Rect rectangle){
		if(inRange(x,rectangle.left,rectangle.left+rectangle.right)  && inRange(y,rectangle.top,rectangle.top+rectangle.bottom)){
			return true;
		}
		return false;
	}
	public String enemyWalkCollisions(Rect enemyBox){
		int pos = 0;
		Rect logPosition = null;
		while(pos<logPositions.size()){
//			banan = new Rect((Integer)me.getKey() , (Integer)me.getValue() , (Integer)me.getKey()+134 , (Integer)me.getValue()+35);
			for(Entry<Integer , Integer> kichak: logPositions){
				//log(kichak.getKey()+" , "+ kichak.getValue());
				int key = kichak.getKey();int val = kichak.getValue();
				if(pos<10){
					logPosition = new Rect(key,val,(int) (key+(137*(AllResources.targetWidth/480.0f))),(int) (val+(10*(AllResources.targetHeight/800.0f))));
				}
				else if(pos>=10 && pos<20){
					logPosition = new Rect(key,val,(int) (key+(65*(AllResources.targetWidth/480.0f))),(int) (val+(10*(AllResources.targetHeight/800.0f))));
				}
				else if(pos>=20 && pos<26){
					logPosition = new Rect(key,val,(int) (key+(10*(AllResources.targetWidth/480.0f))),(int) (val+(137*(AllResources.targetHeight/800.0f))));
				}
				else if(pos>=26 && pos<38){
					logPosition = new Rect(key,val,(int) (key+(10*(AllResources.targetWidth/480.0f))),(int) (val+(65*(AllResources.targetHeight/800.0f))));
				}
				if(logPosition.intersect(enemyBox)){
				if(inRect(enemyBox.left,enemyBox.top,logPosition) && inRect(enemyBox.left+enemyBox.right,enemyBox.top,logPosition)){
					ySpeed = 0;
					y+=2;
					//top face
					return "up";
				}
				else if(inRect(enemyBox.left,enemyBox.top+enemyBox.bottom,logPosition) && inRect(enemyBox.left+enemyBox.right,enemyBox.top+enemyBox.bottom,logPosition) ){
					ySpeed = 0;
					y-=2;
					//bottom face
					return "down";
				}
				else if(inRect(enemyBox.left+enemyBox.right,enemyBox.top,logPosition) && inRect(enemyBox.left+enemyBox.right,enemyBox.top+enemyBox.bottom,logPosition) ){
					xSpeed = 0;
					x-=2;
					//right face
					return "right";
				}
				else if(inRect(enemyBox.left,enemyBox.top,logPosition) && inRect(enemyBox.left,enemyBox.top+enemyBox.bottom,logPosition)){
					xSpeed = 0;
					x+=2;
					//left face
					return "left";
				}
				else{
					//enemyDirection = lastDirection;
					if(inRect(enemyBox.left,enemyBox.top,logPosition)){
						y+=2;
						x+=2;
						//top face
						return null;
					}
					else if(inRect(enemyBox.left+enemyBox.right,enemyBox.top,logPosition)){
						y+=2;
						x-=2;
						return null;
					}
					else if(inRect(enemyBox.left,enemyBox.top+enemyBox.bottom,logPosition)){
						y-=2;
						x+=2;
						//bottom face
						return null;
					}
					else if(inRect(enemyBox.left+enemyBox.right,enemyBox.top+enemyBox.bottom,logPosition)){
						x-=2;
						y-=2;
						//right face
						return null;
					}
					return null;
				}
				}
				else{
					xSpeed = 2;
					ySpeed = 2;
				}
				
				
			}	
			pos++;
		}
		return null;
		
	}
}