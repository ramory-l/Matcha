import React from "react";
import "./styles/userNotification.scss";

const UserNotification = ({ dataType, avatarLink, username, message }) => {
  return (
    <div className="UserNotification">
      <img
        className="mr-2"
        src={`${avatarLink ? avatarLink : "/default-avatar.png"}`}
        alt={`${username}`}
      />
      <div className="UserNotification-MessageBox">
        {dataType === "message" ? (
          <>
            <span>Message From: {username} </span> <br />
          </>
        ) : null}
        <span>{message}</span>
      </div>
    </div>
  );
};

export default UserNotification;
