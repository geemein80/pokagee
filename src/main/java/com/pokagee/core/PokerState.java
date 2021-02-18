package com.pokagee.core;

public interface PokerState {
  void setPreflopState();

  void setFlopState();

  void setTurnState();

  void setRiverState();

  void setFinishedState();
}
