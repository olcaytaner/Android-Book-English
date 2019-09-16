package com.mobile.brickbreaker;

import android.graphics.Point;
import android.graphics.Rect;

public class DroppingBrick {

	private BrickType type;
	private Rect place;
	private Point speed;

	public DroppingBrick(BrickType type, Rect place, int height){
        this.type = type;
        this.place = place;
        speed = new Point(0, (int) (height / 500.0));
	}

	public void move(){
	    place.offset(speed.x, speed.y);
	}

	public boolean contactsWithPaddle(Paddle paddle){
	    if (Rect.intersects(place, paddle.place())){
	        return true;
	    } else {
	        return false;
	    }
	}
	
	public BrickType type(){
		return type;
	}
	
	public Rect place(){
		return place;
	}

}
