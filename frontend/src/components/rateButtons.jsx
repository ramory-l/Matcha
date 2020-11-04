import React from "react";
import "./styles/rateButtons.scss";

const RateButtons = ({ userLiked, userDisliked, onRateChange }) => {
  const userLikeValue = userLiked ? " RateButtons-Like_active" : "";
  const userDislikeValue = userDisliked ? " RateButtons-Dislike_active" : "";
  return (
    <div className="RateButtons">
      <span
        onClick={() => onRateChange("like")}
        role="img"
        aria-label="like-button"
        className={`RateButtons-Like${userLikeValue}`}
      >
        &#128077;
      </span>
      <span
        onClick={() => onRateChange("dislike")}
        role="img"
        aria-label="dislike-button"
        className={`RateButtons-Dislike${userDislikeValue}`}
      >
        &#128078;
      </span>
    </div>
  );
};

export default RateButtons;
