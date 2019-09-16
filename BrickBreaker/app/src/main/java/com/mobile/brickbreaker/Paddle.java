package com.mobile.brickbreaker;

import android.graphics.Rect;

public class Paddle {
	private Rect place;
	
	public Paddle(int screenWidth, int screenHeight){
		place = new Rect((int) (0.425 * screenWidth), (int) (screenHeight - 0.175 * screenWidth), (int) (0.575 * screenWidth), (int) (screenHeight - 0.125 * screenWidth));
	}

	public void grow(){
	    place.inset(-(int)(place.width() * 0.625), 0);
	}

	public void shrink(){
	    place.inset((int)(place.width() * 0.1), 0);
	}

	public void setNewPosition(int x){
	    place.offsetTo(x, place.top);
	}
	
	public Rect place(){
		return place;
	}

}
