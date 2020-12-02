import React from "react";
import "./styles/rateButtons.scss";

const RateButtons = ({ userLiked, userDisliked, onRateChange }) => {
  const userLikeValue = userLiked ? "active" : "";
  const userDislikeValue = userDisliked ? "active" : "";
  return (
    <div className="RateButtons">
      <button
        onClick={() => onRateChange("like")}
        type="button"
        className={`btn btn-outline-success ${userLikeValue}`}
      >
        <span role="img" aria-label="like">
          &#128077;
        </span>
      </button>
      <button
        onClick={() => onRateChange("dislike")}
        type="button"
        className={`btn btn-outline-danger ${userDislikeValue}`}
      >
        <span role="img" aria-label="dislike">
          &#128078;
        </span>
      </button>
    </div>
  );
};

export default RateButtons;
