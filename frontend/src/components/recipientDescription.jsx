import React from "react";
import { Link } from "react-router-dom";
import "./styles/recipientDescription.scss";

const RecipientDescription = (props) => {
  const recipient = props.recipient;
  return (
    <div className="RecipientDescription">
      <h5 className="RecipientDescription-Title">Recipient:</h5>
      <Link to={`/profile/${recipient.username}`}>
        <img
          src={
            recipient.avatar?.link
              ? recipient.avatar.link
              : "/default-avatar.png"
          }
          alt="Recipient avatar"
        />
      </Link>
      <div className="RecipientDescription-FullName">
        <span>{recipient.firstName}</span>
        <span>{recipient.lastName}</span>
      </div>
    </div>
  );
};

export default RecipientDescription;
