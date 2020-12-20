import React from "react";
import "./styles/userMessageNotification.scss";

const UserMessageNotification = ({ avatarLink, username, message }) => {
  return (
    <div className="UserMessageNotification">
      <img
        className="mr-2"
        src={`${avatarLink ? avatarLink : "/default-avatar.png"}`}
        alt=""
      />
      <div className="UserMessageNotification-MessageBox">
        <span>Message From: {username} </span> <br />
        <span>{message}</span>
      </div>
    </div>
  );
};

export default UserMessageNotification;
