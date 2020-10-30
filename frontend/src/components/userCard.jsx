import React from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import * as userService from "../services/userService";
import "./styles/userCard.scss";

const UserCard = (props) => {
  const { user } = props;
  const [rate, setRate] = useState(user.rate);

  const handleRateChange = async (action) => {
    const originalRate = rate;
    let actionValue = action === "like" ? 1 : -1;

    try {
      await userService.rateUser(user.id, action);
      setRate((prev) => prev + actionValue);
    } catch (ex) {
      if (ex.response && ex.response.status === 400)
        toast.error(`You have already ${action}d this user!`);
      setRate(originalRate);
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
      <div className="UserCard-RateButtons">
        <span
          onClick={() => handleRateChange("like")}
          role="img"
          aria-label="like-button"
          className="RateButtons-Like"
        >
          &#128077;
        </span>
        <span
          onClick={() => handleRateChange("dislike")}
          role="img"
          aria-label="dislike-button"
          className="RateButtons-Dislike"
        >
          &#128078;
        </span>
      </div>
      <span>Rating: {rate}</span>
    </div>
  );
};

export default UserCard;
