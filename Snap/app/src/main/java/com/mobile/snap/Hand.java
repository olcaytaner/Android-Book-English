package com.mobile.snap;

public class Hand {
	private Card[] cards = new Card[4];
	private boolean[] isPlayed = new boolean[4];
	
	public Hand(Card card1, Card card2, Card card3, Card card4){
		cards[0] = card1;
		cards[1] = card2;
		cards[2] = card3;
		cards[3] = card4;
	}
	
	public Card play(int position){
		isPlayed[position] = true;
		return cards[position];
	}
	
	public Card getCard(int position){
		return cards[position];
	}
	
	public boolean isPlayed(int position){
		return isPlayed[position];
	}

}
