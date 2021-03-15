package com.pokagee.dto;

public class PokerStatsRequest {
  private Double heroStack;
  private Double pot;
  private Double smallBlind;
  private Double bigBlind;
  private Integer villainCount;

  private String hand;
  private String turn;
  private String flop;
  private String river;

  public Double getHeroStack() {
    return heroStack;
  }

  public void setHeroStack(Double heroStack) {
    this.heroStack = heroStack;
  }

  public Double getPot() {
    return pot;
  }

  public void setPot(Double pot) {
    this.pot = pot;
  }

  public Double getSmallBlind() {
    return smallBlind;
  }

  public void setSmallBlind(Double smallBlind) {
    this.smallBlind = smallBlind;
  }

  public Double getBigBlind() {
    return bigBlind;
  }

  public void setBigBlind(Double bigBlind) {
    this.bigBlind = bigBlind;
  }

  public String getTurn() {
    return turn;
  }

  public void setTurn(String turn) {
    this.turn = turn;
  }

  public String getFlop() {
    return flop;
  }

  public void setFlop(String flop) {
    this.flop = flop;
  }

  public String getRiver() {
    return river;
  }

  public void setRiver(String river) {
    this.river = river;
  }

  public String getHand() {
    return hand;
  }

  public void setHand(String hand) {
    this.hand = hand;
  }

  public Integer getVillainCount() {
    return villainCount;
  }

  public void setVillainCount(Integer villainCount) {
    this.villainCount = villainCount;
  }
}
