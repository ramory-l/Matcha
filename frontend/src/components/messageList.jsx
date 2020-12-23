import React, { useEffect, useRef } from "react";
import Message from "./message";
import "./styles/messageList.scss";

const MessageList = (props) => {
  const { messages, recipient } = props;

  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(scrollToBottom, [messages]);

  return (
    <div className="MessageList">
      {messages.length === 0 ? (
        <span className="MessageList-Empty">No messages yet</span>
      ) : null}
      {messages
        .slice(0)
        .reverse()
        .map((message, index) => (
          <Message key={index} recipient={recipient} messageData={message} />
        ))}
      <div ref={messagesEndRef} />
    </div>
  );
};

export default MessageList;
