package com.pokagee.web;

import com.pokagee.core.PokerGame;
import com.pokagee.core.PokerSimulator;
import com.pokagee.dto.AddToPotRequest;
import com.pokagee.dto.PokerStats;
import com.pokagee.dto.PokerStatsRequest;
import com.pokagee.dto.Pot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PokageeRestController {

  private static final Logger log = LoggerFactory.getLogger(PokageeRestController.class);


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

  @PostMapping(path = "/stats", consumes = "application/json", produces = "application/json")
  public ResponseEntity turnStats(@RequestBody PokerStatsRequest pokerStatsRequest) {
    Long start = System.currentTimeMillis();
    PokerStats pokerStats = PokerSimulator.calculatePokerStats(pokerStatsRequest);
    log.info("Duration Seconds : "+((System.currentTimeMillis()-start)/1000l));
    return new ResponseEntity(pokerStats, HttpStatus.OK);
  }

}