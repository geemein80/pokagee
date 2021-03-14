import React, { Component } from "react";

var xhr;
var initialState = {
    pot:'0',
    flop:'',
    turn:'',
    river:'',
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

        this.handleFlopChange = this.handleFlopChange.bind(this);
        this.handleTurnChange = this.handleTurnChange.bind(this);
        this.handleRiverChange = this.handleRiverChange.bind(this);

        this.handleSubmitBetStatsRequest = this.handleSubmitBetStatsRequest.bind(this);
        this.handleSubmitBetStatsResponse = this.handleSubmitBetStatsResponse.bind(this);

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
            bet:this.state.flop,
            turn:this.state.turn
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
            <div>Pot : {this.state.pot} </div>
            <br/>
            <div>Pot Odds : {pokerStats.potOdds} </div>
            <br/>
            <div>Folding Frequency : {pokerStats.foldingFrequency} </div>
            <br/>
            <div>Action : {pokerStats.action} </div>
            <br/>
        </div>
    }
    render() {
        var stats = this.createStats();
        return (<div>
                <form onSubmit={this.handleSubmitBetStatsRequest}>
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