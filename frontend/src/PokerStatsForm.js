import React, { Component } from "react";

var xhr;
var initialState = {
    addToPot: '',
    pot:'0',
    selectedStreet:"TURN",
    betSize:'',
    outs:'',
    pokerStats:{}

};
class PokerStatsForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = initialState;

        this.handleResetPot = this.handleResetPot.bind(this);
        this.handleResetPotResponse = this.handleResetPotResponse.bind(this);

        this.handleAddToPotValue = this.handleAddToPotValue.bind(this);
        this.handleAddToPot = this.handleAddToPot.bind(this);
        this.handleAddToPotResponse = this.handleAddToPotResponse.bind(this);

        this.handleStreetChange = this.handleStreetChange.bind(this);
        this.handleBetSizeChange = this.handleBetSizeChange.bind(this);
        this.handleOutsChange = this.handleOutsChange.bind(this);

        this.handleSubmitBetStatsRequest = this.handleSubmitBetStatsRequest.bind(this);
        this.handleSubmitBetStatsResponse = this.handleSubmitBetStatsResponse.bind(this);

    }


    handleResetPot(event) {
        xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8282/resetPot", true);
        xhr.setRequestHeader("Accept", "application/json");
        xhr.send();
        xhr.addEventListener("readystatechange", this.handleResetPotResponse, false);

    }
    handleResetPotResponse() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
            this.setState(initialState);
        }
    }
    handleAddToPotValue(event) {
        this.setState({addToPot: event.target.value});
    }
    handleStreetChange(event) {
        this.setState({selectedStreet: event.target.value});
    }
    handleBetSizeChange(event) {
        this.setState({betSize: event.target.value});
    }
    handleOutsChange(event) {
        this.setState({outs: event.target.value});
    }

    handleAddToPot(event) {

        xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8282/addToPot", true);
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.setRequestHeader("Accept", "application/json");
        var request = {
            addToPot:this.state.addToPot
        };
        xhr.send(JSON.stringify(request));

        xhr.addEventListener("readystatechange", this.handleAddToPotResponse, false);

    }
    handleAddToPotResponse() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
            this.setState({pot: response.pot});

        }
    }

    handleSubmitBetStatsRequest(event) {
        xhr = new XMLHttpRequest();
        if(this.state.selectedStreet === 'TURN') {
            xhr.open("POST", "http://localhost:8282/turnStats", true);

        } else {
            xhr.open("POST", "http://localhost:8282/riverStats", true);

        }
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.setRequestHeader("Accept", "application/json");
        var request = {
            bet:this.state.betSize,
            outs:this.state.outs
        };
        xhr.send(JSON.stringify(request));

        xhr.addEventListener("readystatechange", this.handleSubmitBetStatsResponse, false);
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
                    <form onSubmit={this.handleResetPot}>
                        <input type="submit" text="test" value="Reset Pot" />
                    </form>
                    <form onSubmit={this.handleAddToPot}>
                        <br/>
                        <br/>
                        <label>
                            <input type="text" value={this.state.addToPot} onChange={this.handleAddToPotValue} />
                        </label>
                        <input type="submit" value="Add To Pot" />
                    </form>
                <form onSubmit={this.handleSubmitBetStatsRequest}>
                    <br/>
                    <div>
                        Turn:
                        <input type="radio" value="TURN" checked={this.state.selectedStreet ==='TURN'} onChange={this.handleStreetChange} />

                    </div>
                    <div>
                        River:
                        <input type="radio" value="RIVER" checked={this.state.selectedStreet ==='RIVER'} onChange={this.handleStreetChange} />
                    </div>

                    <br/>
                    <label>
                        Bet Size:
                        <input type="text" value={this.state.betSize} onChange={this.handleBetSizeChange} />
                    </label>
                    <br/>
                    <label>
                        Outs:
                        <input type="text" value={this.state.outs} onChange={this.handleOutsChange} />
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