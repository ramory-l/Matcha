import React, { useState } from "react";
import { Link } from "react-router-dom";
import RateButtons from "./rateButtons";
import "./styles/userCard.scss";

const UserCard = (props) => {
  const { user } = props;
  const [rate, setRate] = useState(user.rate);

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
        <RateButtons user={user} rateUpdateFunction={setRate} />
      </div>
    </div>
  );
};

export default UserCard;
