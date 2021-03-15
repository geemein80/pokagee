package com.pokagee.core;

import com.pokagee.dto.PokerStats;
import com.pokagee.dto.PokerStatsRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PokerSimulator {

  public static Card[] getCardsFromString(String cardsStr) {
    if(cardsStr==null || cardsStr.trim().equals("")) {
      return new Card[]{};
    }
    Pattern pattern = Pattern.compile("[cshd]");
    Matcher matcher = pattern.matcher(cardsStr);
    Integer prevEndIndex = 0;
    List<Card> cardList = new ArrayList<>();
    while (matcher.find()) {

      Card card = new Card(matcher.group(), cardsStr.substring(prevEndIndex, matcher.end()-1) );
      cardList.add(card);

      prevEndIndex = matcher.end();
    }
    return cardList.toArray(new Card[]{});
  }

  public static PokerStats calculatePokerStats(PokerStatsRequest pokerStatsRequest) {
    double heroWinCount = 0.0d;
    //double numberOfGames = 10000000.0d;  //10 minute calculation
    double numberOfGames = 100000.0d;



    Card[] heroCards = getCardsFromString(pokerStatsRequest.getHand());
    Card[] flop = getCardsFromString(pokerStatsRequest.getFlop());
    Card[] turnArr = getCardsFromString(pokerStatsRequest.getTurn());
    Card[] riverArr = getCardsFromString(pokerStatsRequest.getRiver());

    Card turn = null;
    Card river = null;

    if(turnArr.length>0) {
      turn = turnArr[0];
    }
    if(riverArr.length>0) {
      river = riverArr[0];
    }

    for( int sessions = 0; sessions<=numberOfGames; sessions++) {
      Set<Card> existingCards = new HashSet<Card>();

      existingCards.addAll(Arrays.asList(heroCards));
      existingCards.addAll(Arrays.asList(flop));
      existingCards.addAll(Arrays.asList(turn));
      existingCards.addAll(Arrays.asList(river));

      Card[] hero = new Card[7];
      hero[0] = heroCards[0];
      hero[1] = heroCards[1];

      List<Card> cards = createShuffledDeck(existingCards);
      Integer villainCount = pokerStatsRequest.getVillainCount();

      int cardIndex = 0;

      List<Card[]> villains = new ArrayList<>();
      for(int i=0;i<villainCount;i++) {
        Card[] villain = new Card[7];
        villain[0] = cards.get(i+0);
        villain[1] = cards.get(i+1);
        villains.add(villain);
        cardIndex+=2;
      }

      Card[] communityCards = getRemainingCommunityCards(cards, cardIndex);
      int communityCardIndex = 0;
      if(flop.length!=3) {
        flop = new  Card[3];
        flop[0] = communityCards[communityCardIndex++];
        flop[1] = communityCards[communityCardIndex++];
        flop[2] = communityCards[communityCardIndex++];
      }
      if(turn==null) {
        turn = communityCards[communityCardIndex++];
      }
      if(river==null) {
        river = communityCards[communityCardIndex++];
      }

      assignCommunityCards(hero, flop, turn, river);

      int heroScore = getCardScore(hero);
      boolean heroWins = true;
      for(int i=0;i<villainCount;i++) {
        Card[] villain = villains.get(i);
        assignCommunityCards(villain, flop, turn, river);
        int villainScore = getCardScore(villain);
        if(villainScore>heroScore) {
          heroWins = false;
          break;
        }
      }
      if(heroWins) {
        heroWinCount++;
      }

    }
    System.out.println("heroWinCount ratio = "+ (heroWinCount/numberOfGames ));
    System.out.println("heroLossCount ratio = "+ ((numberOfGames-heroWinCount)/numberOfGames));

    System.out.println("DONE");
    Double probWin = (heroWinCount/numberOfGames);
    Double probLoss = ((numberOfGames-heroWinCount)/numberOfGames);
    Double pot = pokerStatsRequest.getPot();
    Double probWinPot = probWin*pot;

    PokerStats pokerStats = new PokerStats();
    pokerStats.setProbWin(probWin);
    pokerStats.setProbLoss(probLoss);
    pokerStats.setPot(pot);
    pokerStats.setEvAllIn(probWinPot - probLoss * pokerStatsRequest.getHeroStack());
    pokerStats.setEvBigBlind(probWinPot - probLoss * pokerStatsRequest.getBigBlind());
    pokerStats.setEvDoubleBigBlind(probWinPot - 2 * probLoss * pokerStatsRequest.getBigBlind());
    pokerStats.setEvHalfPot(probWinPot - .5*pot*probLoss);
    pokerStats.setEvThreeQuarterPot(probWinPot - .75*pot*probLoss);
    pokerStats.setEvPot(probWinPot - pot * probLoss);

    return pokerStats;
  }

  public static void assignCommunityCards(Card[] playerCards, Card[] flop, Card turn, Card river) {
    if(flop.length==3) {
      playerCards[2] = flop[0];
      playerCards[3] = flop[1];
      playerCards[4] = flop[2];
      if(turn !=null) {
        playerCards[5] = turn;
        if(river!=null) {
          playerCards[6] = river;
        }
      }
    }
  }

  public static Card[] getRemainingCommunityCards(List<Card> cards, Integer cardIndex/*, Set<Card> existingCards*/) {
    Card[] comCards = new Card[5];
    int comCardIdx = 0;
    int currentCardIdx = cardIndex;
    boolean isFlop = true;
    while(comCards[4]==null) {
      if (isFlop) {
        while(comCardIdx<3) {
          Card currentCard = cards.get(currentCardIdx);
          comCards[comCardIdx] = currentCard;
          comCardIdx++;
          currentCardIdx++;

        }
        isFlop = false;
      } else {
        currentCardIdx++;
        Card currentCard = cards.get(currentCardIdx);
        comCards[comCardIdx] = currentCard;
        comCardIdx++;

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

  public static List<Card> createShuffledDeck(Set<Card> existingCards) {
    List<Card> deck = new ArrayList<Card>();

    for(int i=1;i<=4;i++) {
      for(int j=1;j<=13;j++) {
        Card card = new Card(i, j);
        if(!existingCards.contains(card)) {
          deck.add(card);
        }
      }
    }
    Collections.shuffle(deck);
    return deck;
  }
}
