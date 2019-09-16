package com.mobile.brickbreaker;

import android.graphics.Point;

public class Ball {
	
	private Point center;
	private Point speed;
	private int radius;

	public Ball(int paddleX, int paddleY, int screenWidth){
	    speed = new Point((int)(-screenWidth / 300.0), (int)(-screenWidth / 300.0));
	    radius = screenWidth / 50;
	    center = new Point(paddleX + radius, paddleY - radius);
	}

	public void move(){
	    center.offset(speed.x, speed.y);
	}
	
	public Point center(){
		return center;
	}
	
	public int radius(){
		return radius;
	}

	public boolean contactsWithScreenBounds(int screenWidth, int screenHeight){
	    if (center.x < 0 || center.x > screenWidth){
	        speed.x *= -1;
	    }
	    if (center.y < 0){
	        speed.y *= -1;
	    }
	    if (center.y > screenHeight){
	        return true;
	    } else {
	        return false;
	    }
	}

	public boolean contactsWithBrick(Brick brick){
	    if (center.x + radius > brick.place().left && center.x - radius < brick.place().right
	        && center.y + radius > brick.place().top && center.y - radius < brick.place().bottom){
	        speed.y *= -1;
	        return true;
	    } else {
	        return false;
	    }
	}

	public boolean contactsWithPaddle(Paddle paddle){
	    if (center.x + radius > paddle.place().left && center.x - radius < paddle.place().right
		        && center.y + radius > paddle.place().top && center.y - radius < paddle.place().bottom){
		        speed.y *= -1;
	        return true;
	    } else {
	        return false;
	    }
	}

	public void speedUp(){
	    speed.x *= 1.25;
	    speed.y *= 1.25;
	}

	public void slowDown(){
	    speed.x *= 0.8;
	    speed.y *= 0.8;
	}

}
