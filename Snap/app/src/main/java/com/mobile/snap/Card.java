package com.mobile.snap;

public class Card {
	private String type;
	private int value;
	
	public Card(String type, int value){
		this.type = type;
		this.value = value;
	}
	
	public String toString(){
		switch (value){
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				return type + "_" + value;
			case 1:
				return type + "_ace";
			case 11:
				return type + "_jack";
			case 12:
				return type + "_queen";
			case 13:
				return type + "_king";
		}
		return null;		
	}
	
	public int getValue(){
		return value;
	}
	
	public String getType(){
		return type;
	}
	
}
