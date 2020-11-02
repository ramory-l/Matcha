import React from "react";
import "./styles/recipientDescription.scss";

const RecipientDescription = (props) => {
  const recipient = props.recipient;
  return (
    <div className="RecipientDescription">
      <h5>Recipient:</h5>
      <img
        src={recipient.img ? recipient.img : "/default-avatar.png"}
        alt="Recipient avatar"
      />
      <span>{recipient.firstName}</span>
      <span>{recipient.lastName}</span>
    </div>
  );
};

export default RecipientDescription;
