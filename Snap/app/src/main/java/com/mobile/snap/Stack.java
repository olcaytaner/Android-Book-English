package com.mobile.snap;

import java.util.ArrayList;

public class Stack {
	private ArrayList<Card> normalCards;
	private ArrayList<Card> snapCards;
	
	public Stack(){
		normalCards = new ArrayList<Card>();
		snapCards = new ArrayList<Card>();
	}
	
	public void addNormal(Card card){
		normalCards.add(card);
	}
	
	public void addSnap(Card card){
		snapCards.add(card);
	}
	
	public int numberOfCards(){
		return normalCards.size() + snapCards.size() * 2;
	}
	
	public int points(){
		int points = 0;
		for (Card card : snapCards){
			if (card.getValue() == 11){
				points += 20;
			} else {
				points += 10;
			}
		}
		for (Card card : normalCards){
			if (card.getValue() == 1 || card.getValue() == 11){
				points++;
			} else {
				if (card.getValue() == 2 && card.getType().equalsIgnoreCase("club")){
					points += 2;
				} else {
					if (card.getValue() == 2 && card.getType().equalsIgnoreCase("diamond")){
						points += 3;
					}
				}
			}
		}
		return points;
	}

}
