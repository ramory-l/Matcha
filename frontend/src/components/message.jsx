import React from "react";
import { getCurrentUser } from "../services/authService";
import moment from "moment";
import "./styles/message.scss";

const Message = ({ messageData, recipient }) => {
  const isMe = messageData.from === getCurrentUser().id;

  return (
    <div className={isMe ? "Message" : "Message Message-Me"}>
      <span className="Message-Title">
        {isMe ? getCurrentUser().sub : recipient.username}:
      </span>
      <span className={isMe ? "Message-Text_me" : "Message-Text"}>
        {messageData.message}
      </span>
      <span className="Message-Date">
        {moment(messageData.createTs).format("YYYY-MM-DD, h:mm:ss a")}
      </span>
    </div>
  );
};

export default Message;
