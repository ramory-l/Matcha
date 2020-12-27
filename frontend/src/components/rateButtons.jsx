import React, { useContext } from "react";
import BaseContext from "../contexts/baseContext";
import { getCurrentUser } from "../services/authService";
import { rateUser, unrateUser } from "../services/userService";
import "./styles/rateButtons.scss";

const RateButtons = ({ user, rateUpdateFunction }) => {
  const userLikeValue = user.isLiked ? "active" : "";
  const userDislikeValue = user.isDisliked ? "active" : "";
  const baseContext = useContext(BaseContext);

  const handleRateChange = async (action) => {
    const likeDislikeNotification = {
      from: getCurrentUser().id,
      to: user.id,
      message: `${getCurrentUser().sub} ${action}d you!`,
      createTs: Date.now(),
      type: "notification",
    };

    const actionValue = action === "like" ? 1 : -1;

    if (!user.isLiked && !user.isDisliked) {
      await rateUser(user.id, action);
      if (action === "like") user.isLiked = true;
      else user.isDisliked = true;
      rateUpdateFunction((prev) => prev + actionValue);
      baseContext.webSocket.send(JSON.stringify(likeDislikeNotification));
    } else if (user.isLiked) {
      user.isLiked = false;
      if (action === "like") {
        await unrateUser(user.id, action);
        rateUpdateFunction((prev) => prev - 1);
      } else {
        await unrateUser(user.id, "like");
        await rateUser(user.id, action);
        user.isDisliked = true;
        rateUpdateFunction((prev) => prev - 2);
        baseContext.webSocket.send(JSON.stringify(likeDislikeNotification));
      }
    } else if (user.isDisliked) {
      user.isDisliked = false;
      if (action === "dislike") {
        await unrateUser(user.id, action);
        rateUpdateFunction((prev) => prev + 1);
      } else {
        await unrateUser(user.id, "dislike");
        await rateUser(user.id, action);
        user.isLiked = true;
        rateUpdateFunction((prev) => prev + 2);
        baseContext.webSocket.send(JSON.stringify(likeDislikeNotification));
      }
    }
  };

  return (
    <div className="RateButtons">
      <button
        disabled={!user.avatar}
        onClick={() => handleRateChange("like")}
        type="button"
        className={`btn btn-sm btn-outline-success ${userLikeValue}`}
      >
        <i className="fa fa-thumbs-up" aria-hidden="true"></i>
      </button>
      <button
        disabled={!user.avatar}
        onClick={() => handleRateChange("dislike")}
        type="button"
        className={`btn btn-sm btn-outline-danger ${userDislikeValue}`}
      >
        <i className="fa fa-thumbs-down" aria-hidden="true"></i>
      </button>
    </div>
  );
};

export default RateButtons;
