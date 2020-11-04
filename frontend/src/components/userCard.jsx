import React from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import * as userService from "../services/userService";
import RateButtons from "./rateButtons";
import "./styles/userCard.scss";

const UserCard = (props) => {
  const { user } = props;
  const [rate, setRate] = useState(user.rate);

  const handleRateChange = async (action) => {
    const actionValue = action === "like" ? 1 : -1;

    if (!user.isLiked && !user.isDisliked) {
      await userService.rateUser(user.id, action);
      if (action === "like") user.isLiked = true;
      else user.isDisliked = true;
      setRate((prev) => prev + actionValue);
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
    <div className="UserCard my-2">
      <div className="UserCard-Creds">
        <Link to={`/profile/${user.username}`}>
          <span className="UserCard-FirstName">{user.firstName}</span>
          <span className="UserCard-LastName">{user.lastName}</span>
        </Link>
      </div>
      <img
        className="UserCard-Avatar"
        src={user.img ? user.img : "/default-avatar.png"}
        alt="avatar"
      />
      <RateButtons onRateChange={handleRateChange} />
      <span>Rating: {rate}</span>
    </div>
  );
};

export default UserCard;
