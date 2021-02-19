import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import PokerStatsForm from "./PokerStatsForm";
import {
    Route,
    HashRouter
} from "react-router-dom";

var destination = document.querySelector("#container");

ReactDOM.render(
    <HashRouter>
        <div>
            <div className="content">
                <Route exact path="/" component={PokerStatsForm}/>
            </div>
        </div>
    </HashRouter>
    ,
    destination
);