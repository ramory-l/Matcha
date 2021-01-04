import React, { useState } from "react";
import { Link } from "react-router-dom";
import StatusIndicator from "./common/statusIndicator";
import RateButtons from "./rateButtons";
import "./styles/userCard.scss";

const UserCard = (props) => {
  const { user } = props;
  const [rate, setRate] = useState(user.rate);

  return (
    <Link
      to={`/profile/${user.username}`}
      style={{ color: "Indigo", textDecoration: "none" }}
    >
      <div className="card my-2">
        <div className="card-image">
          <StatusIndicator isOnline={user.isOnline} />
          <img
            src={user.avatar?.link ? user.avatar.link : "/default-avatar.png"}
            className="card-img-top"
            alt={user.username}
          />
        </div>
        <div className="card-body d-flex align-items-center flex-column">
          <h6 className="card-title">
            {user.firstName} {user.lastName}
          </h6>
          <p className="card-text">Rating: {rate}</p>
          <RateButtons user={user} rateUpdateFunction={setRate} />
        </div>
      </div>
    </Link>
  );
};

export default UserCard;
