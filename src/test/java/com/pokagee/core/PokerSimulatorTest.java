package com.pokagee.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class PokerSimulatorTest {

//  PokerSimulator pokerSimulator = new PokerSimulator();

  @Test
  public void testCardsFromStringParser() {
    Card[] cards = PokerSimulator.getCardsFromString("ac10s");
    assertNotNull(cards);
    Set<Card> cardSet = new HashSet<>(Arrays.asList(cards));
    assertEquals(2, cardSet.size());
    assertTrue(cardSet.contains(new Card("c", "a")));
    assertTrue(cardSet.contains(new Card("s", "10")));
  }

  @Test
  public void testAllCardsFromStringParser() {
    String[] Suit = { "d", "c", "h", "s"};
    String[] Rank = { "2", "3", "4",
        "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    Set<Card> allCards = new HashSet<>();
    StringBuffer sbAllCards = new StringBuffer();
    for(int i = 0;i< Suit.length;i++) {
      for(int j = 0;j< Rank.length;j++) {
        allCards.add(new Card(Suit[i], Rank[j]));
        sbAllCards.append(Rank[j]+Suit[i]);
      }
    }
    Card[] cards = PokerSimulator.getCardsFromString(sbAllCards.toString());
    assertNotNull(cards);
    Set<Card> cardSet = new HashSet<>(Arrays.asList(cards));
    assertEquals(allCards.size(), cardSet.size());

    for(Card curCard : allCards) {
      assertTrue(cardSet.contains(curCard));
    }

  }


//  @Test
//  public void testPocketAcesWinRate() {
//    PokerStatsRequest pokerStatsRequest = new PokerStatsRequest();
//    pokerStatsRequest.setHand("acAh");
//    pokerStatsRequest.setBigBlind(10);
//    pokerStatsRequest.setSmallBlind(5);
//    PokerStats pokerStats = PokerSimulator.calculatePokerStats(pokerStatsRequest);
//
//  }
}
