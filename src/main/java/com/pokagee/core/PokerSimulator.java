package com.pokagee.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PokerSimulator {
  public static void main(String[] args) {
    double player1Wins = 0.0d;
    double player2Wins = 0.0d;
    double numberOfGames = 1000;
    for( int sessions = 0; sessions<=numberOfGames; sessions++) {
      Set<Card> existingCards = new HashSet<Card>();
      Card player1Card1 = new Card(1, 14);
      Card player1Card2 = new Card(2, 14);

      Card player2Card1 = new Card(3, 13);
      Card player2Card2 = new Card(4, 13);

      existingCards.add(player1Card1);
      existingCards.add(player1Card2);
      existingCards.add(player2Card1);
      existingCards.add(player2Card2);

      Card[] player1 = new Card[7];
      player1[0] = player1Card1;
      player1[1] = player1Card2;
      Card[] player2 = new Card[7];
      player2[0] = player2Card1;
      player2[1] = player2Card2;

      List<Card> cards = createShuffledDeck();

      Card[] communityCards = getCommunityCards(cards, existingCards);
      for(int i=0;i<communityCards.length;i++) {
        player1[i+2] = communityCards[i];
        player2[i+2] = communityCards[i];
      }

//      System.out.println("========COMMUNITY CARDS=======");
//      for(int i=0;i<communityCards.length;i++) {
//        System.out.print(communityCards[i] +" - ");
//      }
//
//      System.out.println("\n========PLAYER 1=======");
//      for(int i=0;i<2;i++) {
//        System.out.print(player1[i] +" - ");
//      }
//      System.out.println("\n========PLAYER 2=======");
//      for(int i=0;i<2;i++) {
//        System.out.print(player2[i] +" - ");
//      }
//
      int cardScore1 = getCardScore(player1);
      int cardScore2 = getCardScore(player2);
//      System.out.println();
      if(cardScore1>cardScore2) {
//        System.out.println("Player1 Wins!");
        player1Wins++;
      } else if(cardScore1<cardScore2 ){
//        System.out.println("Player2 Wins!");
        player2Wins++;

      } else {
//        System.out.println("Tie!");
      }
    }
    System.out.println("player1 ratio = "+ (player1Wins/numberOfGames ));
    System.out.println("player2 ratio = "+ (player2Wins/numberOfGames ));


    System.out.println("DONE");
  }

  public static Card[] getCommunityCards(List<Card> cards, Set<Card> existingCards) {
    Card[] comCards = new Card[5];
    int comCardIdx = 0;
    int currentCardIdx = 0;
    boolean isFlop = true;
    while(comCards[4]==null) {
      if (isFlop) {
        while(comCardIdx<3) {
          Card currentCard = cards.get(currentCardIdx);

          if (!existingCards.contains(currentCard)) {
            comCards[comCardIdx] = currentCard;
            comCardIdx++;
          }
          currentCardIdx++;

        }
        isFlop = false;
      } else {
        currentCardIdx++;
        Card currentCard = cards.get(currentCardIdx);

        if (!existingCards.contains(currentCard)) {
          comCards[comCardIdx] = currentCard;
          comCardIdx++;
        }
        currentCardIdx++;
      }

    }
    return comCards;
  }

  public static int getCardScore(Card[] cards) {
    PokerEvaluator pokerEvaluator = new PokerEvaluator();
    int highestValue = -1;
    for(int i=0;i<6;i++) {
      for(int j=i+1;j<7;j++) {
        Card[] fiveCards = new Card[5];
        int fiveCardIdx = 0;
        for(int k = 0;k<cards.length;k++) {
          if(k==i || k==j) {
            continue;
          } else {
            fiveCards[fiveCardIdx] = cards[k];
            fiveCardIdx++;
          }
        }

        int value = PokerEvaluator.valueHand(fiveCards);
        if(value>highestValue) {
          highestValue = value;
        }
      }
    }
    return highestValue;
  }

  public static List<Card> createShuffledDeck() {
    List<Card> deck = new ArrayList<Card>();

    for(int i=1;i<=4;i++) {
      for(int j=1;j<=13;j++) {
        Card card = new Card(i, j);
        deck.add(card);
      }
    }
    Collections.shuffle(deck);
    return deck;
  }
}
