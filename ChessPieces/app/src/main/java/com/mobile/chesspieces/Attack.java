package com.mobile.chesspieces;

public class Attack extends Square {
	
	private int numberOfAttacks;
	
	public Attack(int x, int y, int numberOfAttacks){
		super(x, y);
		this.numberOfAttacks = numberOfAttacks;
	}
	
	public int getNumberOfAttacks(){
		return numberOfAttacks;
	}

}
