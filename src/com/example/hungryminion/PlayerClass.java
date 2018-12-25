package com.example.hungryminion;

import java.util.List;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class PlayerClass {
	float x=0,y=0;
	int xSpeed = 4,ySpeed=4;
	String direction = null,lastDirection=null;
	Bitmap ob,spritesheet;
	int width,currentFrame,imgx;
	public Rect dst,logPosition,src;
	List<Entry<Integer,Integer>> logPositions;
	public PlayerClass(List<Entry<Integer, Integer>> logPositions, Bitmap spritesheet2, Bitmap ob2){
		this.logPositions = logPositions;
		this.spritesheet = spritesheet2;
		this.ob = ob2;
		this.direction = "down";
	}
	
	public void PlayerMovement(Canvas canvas){
		dst = new Rect((int)x,(int)y,(int) (x+32*(AllResources.targetWidth/480.0f)),(int) (y+38*(AllResources.targetHeight/800.0f)));
		checkCollisions(dst);
		if(direction.equals("up")){
			width  = spritesheet.getWidth()/5;
			currentFrame = ++currentFrame % 5;
			imgx = currentFrame*width;
			src = new Rect(imgx,300,imgx+width,400);
			//dst = new Rect((int)x,(int)y,(int)x+width,(int)y+37);
			canvas.drawBitmap(spritesheet,src, dst, null);
			if(y>=0){
				y=y-ySpeed;
			}
		}
		else if(direction.equals("right")){
			
			width  = spritesheet.getWidth()/5;
			currentFrame = ++currentFrame % 5;
			imgx = currentFrame*width;
			src = new Rect(imgx,0,imgx+width,100);
			canvas.drawBitmap(spritesheet,src, dst, null);
			if(x<(canvas.getWidth()-ob.getWidth())){
				x=x+xSpeed;
			}
		}
		else if(direction.equals("left")){
			width  = spritesheet.getWidth()/5;
			currentFrame = ++currentFrame % 5;
			imgx = currentFrame*width;
			src = new Rect(imgx,100,imgx+width,200);
			//dst = new Rect((int)x,(int)y,(int)x+width,(int)y+37);
			canvas.drawBitmap(spritesheet,src, dst, null);
			if(x>=0){
				x=x-xSpeed;
			}
		}
		else if(direction.equals("down")){
			width  = spritesheet.getWidth()/5;
			currentFrame = ++currentFrame % 5;
			imgx = currentFrame*width;
			src = new Rect(imgx,200,imgx+width,300);
			canvas.drawBitmap(spritesheet,src, dst, null);
			if(y<canvas.getHeight()-ob.getHeight()){
				y=y+ ySpeed;
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
	private void checkCollisions(Rect playerBox) {
		int pos = 0;
		while(pos<logPositions.size()){
				Entry<Integer, Integer> kichak = logPositions.get(pos);
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
				if(logPosition.intersect(playerBox)){
				if(inRect(playerBox.left,playerBox.top,logPosition) && inRect(playerBox.left+playerBox.right,playerBox.top,logPosition)){
					ySpeed = 0;
					y+=4;
					//top face
					break;
				}
				else if(inRect(playerBox.left,playerBox.top+playerBox.bottom,logPosition) && inRect(playerBox.left+playerBox.right,playerBox.top+playerBox.bottom,logPosition) ){
					ySpeed = 0;
					y-=4;
					//bottom face
					break;
				}
				else if(inRect(playerBox.left+playerBox.right,playerBox.top,logPosition) && inRect(playerBox.left+playerBox.right,playerBox.top+playerBox.bottom,logPosition) ){
					xSpeed = 0;
					x-=4;
					//right face
					break;
				}
				else if(inRect(playerBox.left,playerBox.top,logPosition) && inRect(playerBox.left,playerBox.top+playerBox.bottom,logPosition)){
					xSpeed = 0;
					x+=4;
					//left face
					break;
				}
				else{
					direction = lastDirection;
					if(inRect(playerBox.left,playerBox.top,logPosition)){
						y+=4;
						x+=4;
						//top face
						break;
					}
					else if(inRect(playerBox.left+playerBox.right,playerBox.top,logPosition)){
						y+=4;
						x-=4;
						break;
					}
					else if(inRect(playerBox.left,playerBox.top+playerBox.bottom,logPosition)){
						y-=4;
						x+=4;
						//bottom face
						break;
					}
					else if(inRect(playerBox.left+playerBox.right,playerBox.top+playerBox.bottom,logPosition)){
						x-=4;
						y-=4;
						//right face
						break;
					}
					break;
				}
				}
				else{
					xSpeed = 4;
					ySpeed = 4;
				}
			pos++;
		}
	}
}
