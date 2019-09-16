package com.mobile.snap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

public class Snap extends Activity {

    private ImageView[] cards = new ImageView[4];
    private ImageView[] computerCards = new ImageView[4];
    private ImageView[] closedCards = new ImageView[13];
    private ImageView middleCard;
    private Pile pile;
    private Deck deck;
    private Hand human, computer;
    private Stack humanStack, computerStack;
    private int numberOfDeals, moveCount;
    private ImageView humanAnimationCard, computerAnimationCard;
    private int humanAnimationCardX, humanAnimationCardY;
    private int computerAnimationCardX, computerAnimationCardY;

    private void showPile(){
        for (int i = 0; i < 13; i++){
            String tag = (String) closedCards[i].getTag();
            if (Integer.parseInt(tag) > pile.numberOfCards() + 7){
                closedCards[i].setVisibility(View.INVISIBLE);
            } else {
                closedCards[i].setVisibility(View.VISIBLE);
            }
        }
        middleCard.bringToFront();
    }

    private void play(Hand hand, Stack stack, int aKart){
        Card card = hand.play(aKart);
        pile.addCard(card);
        if (pile.winExists()){
            pile.sendToStack(stack);
            showPile();
            middleCard.setVisibility(View.INVISIBLE);
        } else {
            showPile();
            showCard(pile.topCard(), middleCard);
        }
    }

    private void gameOver(){
        int computerPoints = computerStack.points();
        int humanPoints = humanStack.points();
        if (computerStack.numberOfCards() > humanStack.numberOfCards()){
            computerPoints += 3;
        } else {
            humanPoints += 3;
        }
        String message = "Computer:" + computerPoints + " Human:" + humanPoints;
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void continueGame(){
        moveCount++;
        if (moveCount == 4){
            moveCount = 0;
            numberOfDeals++;
            if (numberOfDeals < 6){
                dealCards();
                showPile();
            } else {
                gameOver();
            }
        }
    }

    private int sameCardExists(){
        for (int i = 0; i < 4; i++){
            if (!computer.isPlayed(i) && computer.getCard(i).getValue() == pile.topCard().getValue()){
                return i;
            }
        }
        return -1;
    }

    private int jackExists(){
        for (int i = 0; i < 4; i++){
            if (!computer.isPlayed(i) && computer.getCard(i).getValue() == 11){
                return i;
            }
        }
        return -1;
    }

    private int playRandom(){
        Random random = new Random();
        int card = random.nextInt(4);
        while (computer.isPlayed(card)){
            card = random.nextInt(4);
        }
        return card;
    }

    private void computersMove(){
        int selectedCard = -1;
        if (pile.topCard() != null){
            selectedCard = sameCardExists();
            if (selectedCard == -1){
                selectedCard = jackExists();
            }
        }
        if (selectedCard == -1){
            selectedCard = playRandom();
        }
        computerAnimationCard = computerCards[selectedCard];
        showCard(computer.getCard(selectedCard), computerAnimationCard);
        computerAnimation();
    }

    private void showCard(Card card, ImageView cardImage){
        AssetManager assets = getAssets();
        try{
            InputStream picture = assets.open(card.toString() + ".png");
            Drawable flag = Drawable.createFromStream(picture, card.toString());
            cardImage.setImageDrawable(flag);
        }
        catch (IOException e) {
        }
        cardImage.setVisibility(View.VISIBLE);
    }

    private void dealCards(){
        human = deck.dealHand();
        computer = deck.dealHand();
        for (int i = 0; i < 4; i++){
            showCard(human.getCard(i), cards[i]);
        }
        if (pile.topCard() != null){
            showCard(pile.topCard(), middleCard);
        } else {
            middleCard.setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < 4; i++){
            computerCards[i].setVisibility(View.VISIBLE);
        }
        moveCount = 0;
    }

    private void startGame(){
        numberOfDeals = 0;
        deck = new Deck();
        pile = new Pile();
        humanStack = new Stack();
        computerStack = new Stack();
        deck.dealPile(pile);
        dealCards();
        showPile();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        cards[0] = (ImageView) findViewById(R.id.card1);
        cards[1] = (ImageView) findViewById(R.id.card2);
        cards[2] = (ImageView) findViewById(R.id.card3);
        cards[3] = (ImageView) findViewById(R.id.card4);
        computerCards[0] = (ImageView) findViewById(R.id.computerCard1);
        computerCards[1] = (ImageView) findViewById(R.id.computerCard2);
        computerCards[2] = (ImageView) findViewById(R.id.computerCard3);
        computerCards[3] = (ImageView) findViewById(R.id.computerCard4);
        closedCards[0] = (ImageView) findViewById(R.id.closedCard1);
        closedCards[1] = (ImageView) findViewById(R.id.closedCard2);
        closedCards[2] = (ImageView) findViewById(R.id.closedCard3);
        closedCards[3] = (ImageView) findViewById(R.id.closedCard4);
        closedCards[4] = (ImageView) findViewById(R.id.closedCard5);
        closedCards[5] = (ImageView) findViewById(R.id.closedCard6);
        closedCards[6] = (ImageView) findViewById(R.id.closedCard7);
        closedCards[7] = (ImageView) findViewById(R.id.closedCard8);
        closedCards[8] = (ImageView) findViewById(R.id.closedCard9);
        closedCards[9] = (ImageView) findViewById(R.id.closedCard10);
        closedCards[10] = (ImageView) findViewById(R.id.closedCard11);
        closedCards[11] = (ImageView) findViewById(R.id.closedCard12);
        closedCards[12] = (ImageView) findViewById(R.id.closedCard13);
        for (int i = 0; i < 13; i++){
            closedCards[i].setVisibility(View.INVISIBLE);
        }
        middleCard = (ImageView) findViewById(R.id.middleCard);
        for (int i = 0; i < 4; i++){
            cards[i].setOnClickListener(cardClick);
        }
        startGame();
    }

    public OnClickListener cardClick = new OnClickListener() {
        public void onClick(View v){
            String tag = (String) v.getTag();
            int selectedCard = Integer.parseInt(tag);
            if (human.isPlayed(selectedCard)){
                return;
            }
            humanAnimationCard = cards[selectedCard];
            humanAnimation();
        }
    };

    private void humanAnimation(){
        humanAnimationCardX = humanAnimationCard.getLeft();
        humanAnimationCardY = humanAnimationCard.getTop();
        humanAnimationCard.animate().x(middleCard.getLeft()).y(middleCard.getTop()).setDuration(1000).setListener(humanAnimationOver);
    }

    private void computerAnimation(){
        computerAnimationCardX = computerAnimationCard.getLeft();
        computerAnimationCardY = computerAnimationCard.getTop();
        computerAnimationCard.animate().x(middleCard.getLeft()).y(middleCard.getTop()).setDuration(1000).setListener(computerAnimationOver);
    }

    public AnimatorListenerAdapter humanAnimationOver = new AnimatorListenerAdapter(){
        public void onAnimationStart(Animator animation){
        }
        public void onAnimationEnd(Animator animation){
            String tag = (String) humanAnimationCard.getTag();
            int cardTag = Integer.parseInt(tag);
            humanAnimationCard.setVisibility(View.INVISIBLE);
            play(human, humanStack, cardTag);
            computersMove();
            humanAnimationCard.animate().x(humanAnimationCardX).y(humanAnimationCardY).setDuration(1).setListener(null);
        }
    };

    public AnimatorListenerAdapter computerAnimationOver = new AnimatorListenerAdapter(){
        public void onAnimationStart(Animator animation){
        }
        public void onAnimationEnd(Animator animation){
            String tag = (String) computerAnimationCard.getTag();
            int cardTag = Integer.parseInt(tag);
            computerAnimationCard.setVisibility(View.INVISIBLE);
            computerAnimationCard.setImageResource(R.drawable.empty);
            play(computer, computerStack, cardTag - 4);
            continueGame();
            computerAnimationCard.animate().x(computerAnimationCardX).y(computerAnimationCardY).setDuration(1).setListener(null);
        }
    };

}
