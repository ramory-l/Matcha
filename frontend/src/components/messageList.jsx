import React from "react";
import Message from "./message";
import "./styles/messageList.scss";

const MessageList = ({ messages, recipient }) => {
  if (messages.length === 0) return <p>No messages yet</p>;

  return (
    <div className="MessageList">
      {messages
        .slice(0)
        .reverse()
        .map((message, index) => (
          <Message key={index} recipient={recipient} messageData={message} />
        ))}
    </div>
  );
};

export default MessageList;
