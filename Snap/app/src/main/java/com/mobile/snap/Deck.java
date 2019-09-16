package com.mobile.snap;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> cards;
	
	public Deck(){
		dealCards();
		Collections.shuffle(cards);
	}
	
	private void dealCards(){
		String type;
		Card card;
		cards = new ArrayList<Card>();
		for (int i = 0; i < 4; i++){
			switch (i){
				case 0:
					type = "diamond";
					break;
				case 1:
					type = "heart";
					break;
				case 2:
					type = "club";
					break;
				case 3:
					type = "spade";
					break;
				default:
					type = "";
					break;
			}
			for (int j = 1; j <= 13; j++){
				card = new Card(type, j);
				cards.add(card);
			}
		}
	}
	
	public Hand dealHand(){
		Hand hand;
		hand = new Hand(cards.get(cards.size() - 1), cards.get(cards.size() - 2), cards.get(cards.size() - 3), cards.get(cards.size() - 4));
		for (int i = 0; i < 4; i++){
			cards.remove(cards.size() - 1);
		}
		return hand;
	}
	
	public void dealPile(Pile pile){
		for (int i = 0; i < 4; i++){
			pile.addCard(cards.get(cards.size() - 1));
			cards.remove(cards.size() - 1);
		}
	}

}
