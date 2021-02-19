package com.pokagee.web;

import com.pokagee.core.PokerGame;
import com.pokagee.dto.AddToPotRequest;
import com.pokagee.dto.PokerStats;
import com.pokagee.dto.PokerStatsRequest;
import com.pokagee.dto.Pot;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PokageeRestController {

  private PokerGame pokerGame = new PokerGame();
  private Pot pot = new Pot();

  @PostMapping(path = "/addToPot", consumes = "application/json", produces = "application/json")
  public ResponseEntity addToPot(@RequestBody AddToPotRequest addToPotRequest) {
    pot.setPot(addToPotRequest.getAddToPot()+pot.getPot());
    return new ResponseEntity(pot, HttpStatus.OK);
  }

  @GetMapping(path = "/resetPot", produces = "application/json")
  public ResponseEntity resetPot() {
    pot.setPot(0);
    return new ResponseEntity(pot, HttpStatus.OK);
  }

  @PostMapping(path = "/turnStats", consumes = "application/json", produces = "application/json")
  public ResponseEntity turnStats(@RequestBody PokerStatsRequest pokerStatsRequest) {
    PokerStats pokerStats = new PokerStats();
    Double potOdds = 4.0 * pokerStatsRequest.getOuts()/100;
    Double foldingFrequency = pokerStatsRequest.getBet().doubleValue() / (pokerStatsRequest.getBet().doubleValue() + pot.getPot().doubleValue());
    pokerStats.setPotOdds(potOdds);
    pokerStats.setFoldingFrequency(foldingFrequency);
    if(potOdds > foldingFrequency) {
      pokerStats.setAction("BET");
    } else {
      pokerStats.setAction("FOLD/CHECK");
    }
    return new ResponseEntity(pokerStats, HttpStatus.OK);
  }

  @PostMapping(path = "/riverStats", consumes = "application/json", produces = "application/json")
  public ResponseEntity riverStats(@RequestBody PokerStatsRequest pokerStatsRequest) {
    PokerStats pokerStats = new PokerStats();
    Double potOdds = 2.0 * pokerStatsRequest.getOuts()/100;
    Double foldingFrequency = pokerStatsRequest.getBet().doubleValue() / (pokerStatsRequest.getBet().doubleValue() + pot.getPot().doubleValue());
    pokerStats.setPotOdds(potOdds);
    pokerStats.setFoldingFrequency(foldingFrequency);
    if(potOdds > foldingFrequency) {
      pokerStats.setAction("BET");
    } else {
      pokerStats.setAction("FOLD/CHECK");
    }
    return new ResponseEntity(pokerStats, HttpStatus.OK);
  }
}