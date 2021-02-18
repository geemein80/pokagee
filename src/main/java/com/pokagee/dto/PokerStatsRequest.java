package com.pokagee.dto;

public class PokerStatsRequest {
  private Integer bet;
  private Integer outs;

  public Integer getBet() {
    return bet;
  }

  public void setBet(Integer bet) {
    this.bet = bet;
  }

  public Integer getOuts() {
    return outs;
  }

  public void setOuts(Integer outs) {
    this.outs = outs;
  }
}
