import React from "react";
import CheckBox from "./common/checkBox";
import "./styles/searchForm.scss";

const SearhForm = () => {
  return (
    <div className="SearchForm">
      <div className="SearchForm-Gender">
        <h6>I want to find:</h6>
        <CheckBox label="Man" />
        <CheckBox label="Woman" />
      </div>
      <div className="SearchForm-Targets">
        <h6>For:</h6>
        <CheckBox label="FriendShip" />
        <CheckBox label="Love" />
        <CheckBox label="Sex" />
        <CheckBox label="Flirt" />
      </div>
      <div className="SearchForm-Options">
        <h6>Age:</h6>
        <input type="text" placeholder="From" size="3" /> <br /> <br />
        <input type="text" placeholder="To" size="3" />
      </div>
    </div>
  );
};

export default SearhForm;
