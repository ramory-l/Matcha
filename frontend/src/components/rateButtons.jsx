import React from "react";
import "./styles/rateButtons.scss";

const RateButtons = ({ onRateChange }) => {
  return (
    <div className="RateButtons">
      <span
        onClick={() => onRateChange("like")}
        role="img"
        aria-label="like-button"
        className="RateButtons-Like"
      >
        &#128077;
      </span>
      <span
        onClick={() => onRateChange("dislike")}
        role="img"
        aria-label="dislike-button"
        className="RateButtons-Dislike"
      >
        &#128078;
      </span>
    </div>
  );
};

export default RateButtons;
