package com.mobile.snap;

import java.util.ArrayList;

public class Pile {
	private ArrayList<Card> cards;
	
	public Pile(){
		cards = new ArrayList<Card>();
	}
	
	public Card topCard(){
		if (cards.isEmpty()){
			return null;
		}
		return cards.get(cards.size() - 1);
	}
	
	public void addCard(Card card){
		cards.add(card);
	}
	
	public int numberOfCards(){
		return cards.size();
	}
	
	public boolean winExists(){
		Card topCard;
		Card bottomCard;
		if (cards.size() >= 2){
			topCard = cards.get(cards.size() - 1);
			bottomCard = cards.get(cards.size() - 2);
			if (topCard.getValue() == bottomCard.getValue() || topCard.getValue() == 11){
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public void sendToStack(Stack stack){
        Card topCard;
        Card bottomCard;
        topCard = cards.get(cards.size() - 1);
        bottomCard = cards.get(cards.size() - 2);
		if (cards.size() == 2 && topCard.getValue() == bottomCard.getValue()){
			stack.addSnap(topCard());
		} else {
			for (Card card : cards){
				stack.addNormal(card);
			}
		}
		cards.clear();
	}

}
