import React, { useContext } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import BaseContext from "../contexts/baseContext";
import { getCurrentUser } from "../services/authService";
import * as userService from "../services/userService";
import RateButtons from "./rateButtons";
import "./styles/userCard.scss";

const UserCard = (props) => {
  const { user } = props;
  console.log("userCard", user);
  const [rate, setRate] = useState(user.rate);
  const baseContext = useContext(BaseContext);

  const handleRateChange = async (action) => {
    const likeNotification = {
      from: getCurrentUser().id,
      to: user.id,
      message: `${getCurrentUser().sub} ${action}d you!`,
      createTs: Date.now(),
      type: "notification",
    };

    const actionValue = action === "like" ? 1 : -1;

    if (!user.isLiked && !user.isDisliked) {
      await userService.rateUser(user.id, action);
      if (action === "like") user.isLiked = true;
      else user.isDisliked = true;
      setRate((prev) => prev + actionValue);
      baseContext.webSocket.send(JSON.stringify(likeNotification));
    } else if (user.isLiked) {
      user.isLiked = false;
      if (action === "like") {
        await userService.unrateUser(user.id, action);
        setRate((prev) => prev - 1);
      } else {
        await userService.unrateUser(user.id, "like");
        await userService.rateUser(user.id, action);
        user.isDisliked = true;
        setRate((prev) => prev - 2);
      }
    } else if (user.isDisliked) {
      user.isDisliked = false;
      if (action === "dislike") {
        await userService.unrateUser(user.id, action);
        setRate((prev) => prev + 1);
      } else {
        await userService.unrateUser(user.id, "dislike");
        await userService.rateUser(user.id, action);
        user.isLiked = true;
        setRate((prev) => prev + 2);
      }
    }
  };

  return (
    <div className="card my-2">
      <div className="card-image">
        <img
          src={user.avatar?.link ? user.avatar.link : "/default-avatar.png"}
          className="card-img-top"
          alt={user.username}
        />
      </div>
      <div className="card-body d-flex align-items-center flex-column">
        <Link to={`/profile/${user.username}`} style={{ color: "Indigo" }}>
          <h6 className="card-title">
            {user.firstName} {user.lastName}
          </h6>
        </Link>
        <p className="card-text">Rating: {rate}</p>
        <RateButtons
          userLiked={user.isLiked}
          userDisliked={user.isDisliked}
          onRateChange={handleRateChange}
        />
      </div>
    </div>
  );
};

export default UserCard;
