package com.pokagee.dto;

public class PokerStats {
  private Double potOdds;

  private Double foldingFrequency;

  private String action;

  public Double getPotOdds() {
    return potOdds;
  }

  public void setPotOdds(Double potOdds) {
    this.potOdds = potOdds;
  }

  public Double getFoldingFrequency() {
    return foldingFrequency;
  }

  public void setFoldingFrequency(Double foldingFrequency) {
    this.foldingFrequency = foldingFrequency;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }
}
