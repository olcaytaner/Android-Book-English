package com.mobile.brickbreaker;

import android.graphics.Rect;

public class Brick {

	private BrickType type;
	private Rect place;
	private boolean isBroken;
	private int numberOfHits;
	
	public Brick(BrickType type, Rect place){
        this.type = type;
        isBroken = false;
        this.place = place;
        this.numberOfHits = 0;
	}

	public void hit(){
	    numberOfHits++;
	    if (type == BrickType.HARD){
	        if (numberOfHits == 2){
	            isBroken = true;
	        }
	    } else {
	        isBroken = true;
	    }
	}
	
	public boolean isBroken(){
		return isBroken;
	}
	
	public Rect place(){
		return place;
	}
	
	public BrickType type(){
		return type;
	}

}
