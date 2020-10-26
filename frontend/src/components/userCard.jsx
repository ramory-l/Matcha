import React from "react";
import { Link } from "react-router-dom";
import "./styles/userCard.scss";

const UserCard = (props) => {
  const { user } = props;
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
        <span role="img" aria-label="like-button" className="RateButtons-Like">
          &#128077;
        </span>
        <span
          role="img"
          aria-label="dislike-button"
          className="RateButtons-Dislike"
        >
          &#128078;
        </span>
      </div>
      <span>Rating: {user.fameRate}</span>
    </div>
  );
};

export default UserCard;
