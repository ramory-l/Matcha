import React from "react";
import MessageInput from "./messageInput";
import "./styles/chatBox.scss";

const ChatBox = () => {
  return (
    <div className="ChatBox">
      <div className="ChatBox-Messages"></div>
      <MessageInput />
    </div>
  );
};

export default ChatBox;
