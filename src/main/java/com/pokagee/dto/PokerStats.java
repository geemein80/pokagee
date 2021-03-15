package com.pokagee.dto;

public class PokerStats {

  private Double pot;

  private Double probWin;

  private Double probLoss;

  private Double evBigBlind;

  private Double evDoubleBigBlind;

  private Double evHalfPot;

  private Double evThreeQuarterPot;

  private Double evPot;

  private Double evAllIn;

  public Double getPot() {
    return pot;
  }

  public void setPot(Double pot) {
    this.pot = pot;
  }

  public Double getEvBigBlind() {
    return evBigBlind;
  }

  public void setEvBigBlind(Double evBigBlind) {
    this.evBigBlind = evBigBlind;
  }

  public Double getEvDoubleBigBlind() {
    return evDoubleBigBlind;
  }

  public void setEvDoubleBigBlind(Double evDoubleBigBlind) {
    this.evDoubleBigBlind = evDoubleBigBlind;
  }

  public Double getEvHalfPot() {
    return evHalfPot;
  }

  public void setEvHalfPot(Double evHalfPot) {
    this.evHalfPot = evHalfPot;
  }

  public Double getEvThreeQuarterPot() {
    return evThreeQuarterPot;
  }

  public void setEvThreeQuarterPot(Double evThreeQuarterPot) {
    this.evThreeQuarterPot = evThreeQuarterPot;
  }

  public Double getEvPot() {
    return evPot;
  }

  public void setEvPot(Double evPot) {
    this.evPot = evPot;
  }

  public Double getEvAllIn() {
    return evAllIn;
  }

  public void setEvAllIn(Double evAllIn) {
    this.evAllIn = evAllIn;
  }

  public Double getProbWin() {
    return probWin;
  }

  public void setProbWin(Double probWin) {
    this.probWin = probWin;
  }

  public Double getProbLoss() {
    return probLoss;
  }

  public void setProbLoss(Double probLoss) {
    this.probLoss = probLoss;
  }
}
