import React, { Component } from "react";

var xhr;
var initialState = {
    pot:'',
    flop:'',
    turn:'',
    river:'',
    heroStack:'',
    hand:'',
    villainCount:'',
    smallBlind:'',
    bigBlind:'',
    pokerStats:{}

};
class PokerStatsForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = initialState;

        this.handleSmallBlindChange = this.handleSmallBlindChange.bind(this);
        this.handleBigBlindChange = this.handleBigBlindChange.bind(this);
        this.handlePotChange = this.handlePotChange.bind(this);
        this.handleHeroStackChange = this.handleHeroStackChange.bind(this);
        this.handleHandChange = this.handleHandChange.bind(this);
        this.handleVillainCountChange = this.handleVillainCountChange.bind(this);
        this.handleNewGame = this.handleNewGame.bind(this);

        this.handleFlopChange = this.handleFlopChange.bind(this);
        this.handleTurnChange = this.handleTurnChange.bind(this);
        this.handleRiverChange = this.handleRiverChange.bind(this);

        this.handleSubmitBetStatsRequest = this.handleSubmitBetStatsRequest.bind(this);
        this.handleSubmitBetStatsResponse = this.handleSubmitBetStatsResponse.bind(this);

    }
    handleHandChange(event) {
        this.setState({hand: event.target.value});
    }
    handleVillainCountChange(event) {
        this.setState({villainCount: event.target.value});
    }
    handleNewGame(event) {
        this.setState({pot:''});
        this.setState({flop:''});
        this.setState({turn:''});
        this.setState({hand:''});
        this.setState({river:''});
        this.setState({heroStack:''});
        this.setState({pokerStats:{}});

        event.preventDefault();

    }

    handleHeroStackChange(event) {
        this.setState({heroStack: event.target.value});
    }
    handleSmallBlindChange(event) {
        this.setState({smallBlind: event.target.value});
    }
    handleBigBlindChange(event) {
        this.setState({bigBlind: event.target.value});
    }
    handlePotChange(event) {
        this.setState({pot: event.target.value});
    }

    handleFlopChange(event) {
        this.setState({flop: event.target.value});
    }
    handleTurnChange(event) {
        this.setState({turn: event.target.value});
    }
    handleRiverChange(event) {
        this.setState({river: event.target.value});
    }

    handleSubmitBetStatsRequest(event) {
        xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8282/stats", true);


        xhr.setRequestHeader("Content-type", "application/json");
        xhr.setRequestHeader("Accept", "application/json");
        var request = {
            heroStack:this.state.heroStack,
            villainCount:this.state.villainCount,
            smallBlind:this.state.smallBlind,
            hand:this.state.hand,
            bigBlind:this.state.bigBlind,
            pot:this.state.pot,
            flop:this.state.flop,
            turn:this.state.turn,
            river:this.state.river

        };
        xhr.send(JSON.stringify(request));

        xhr.addEventListener("readystatechange", this.handleSubmitBetStatsResponse, false);
        event.preventDefault();

    }

    handleSubmitBetStatsResponse(event) {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
            this.setState({pokerStats: response});
        }
    }
    createStats() {
        var pokerStats = this.state.pokerStats;
        return <div>
            <div>Pot : {pokerStats.pot} </div>
            <br/>
            <div>Prob Win : {pokerStats.probWin} </div>
            <br/>
            <div>Prob Loss : {pokerStats.probLoss} </div>
            <br/>
            <div>EV BB : {pokerStats.evBigBlind} </div>
            <br/>
            <div>EV 2xBB : {pokerStats.evDoubleBigBlind} </div>
            <br/>
            <div>EV Half Pot : {pokerStats.evHalfPot} </div>
            <br/>
            <div>EV Three Quarter Pot : {pokerStats.evThreeQuarterPot} </div>
            <br/>
            <div>EV Pot : {pokerStats.evPot} </div>
            <br/>
            <div>EV All In : {pokerStats.evAllIn} </div>
            <br/>
        </div>

    }
    render() {
        var stats = this.createStats();
        return (<div>
                    <label>
                        Small Blind:
                        <input type="text" value={this.state.smallBlind} onChange={this.handleSmallBlindChange} />
                    </label>
                    <br/>
                    <label>
                        Big Blind:
                        <input type="text" value={this.state.bigBlind} onChange={this.handleBigBlindChange} />
                    </label>
                    <br/>
                    <br/>
                    <br/>
                    <label>
                        Villain Count:
                        <input type="text" value={this.state.villainCount} onChange={this.handleVillainCountChange} />
                    </label>
                    <br/>
                    <br/>
                    <br/>
                    <form onSubmit={this.handleNewGame}>

                        <input type="submit" value="New Game" />
                    </form>
                    <br/>
                    <br/>
                    <br/>
                    <label>
                        Hand:
                        <input type="text" value={this.state.hand} onChange={this.handleHandChange} />
                    </label>
                    <br/>
                    <br/>
                    <br/>
                    <label>
                        Hero Stack:
                        <input type="text" value={this.state.heroStack} onChange={this.handleHeroStackChange} />
                    </label>
                    <br/>
                    <br/>
                    <br/>
                    <label>
                        Pot:
                        <input type="text" value={this.state.pot} onChange={this.handlePotChange} />
                    </label>
                    <br/>
                    <br/>
                    <br/>

                    <label>
                        Flop:
                        <input type="text" value={this.state.flop} onChange={this.handleFlopChange} />
                    </label>
                    <br/>
                    <label>
                        Turn:
                        <input type="text" value={this.state.turn} onChange={this.handleTurnChange} />
                    </label>
                    <br/>
                    <label>
                        River:
                        <input type="text" value={this.state.river} onChange={this.handleRiverChange} />
                    </label>
                    <br/>
                    <br/>
                <form onSubmit={this.handleSubmitBetStatsRequest}>

                    <input type="submit" value="Bet Stats" />
                </form>
                <br/>
                <br/>

                {stats}
            </div>
        );
    }
}
export default PokerStatsForm;