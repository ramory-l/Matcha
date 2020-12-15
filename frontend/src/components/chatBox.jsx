import React, { useContext } from "react";
import BaseContext from "../contexts/baseContext";
import MessageInput from "./messageInput";
import "./styles/chatBox.scss";

const ChatBox = () => {
  const baseContext = useContext(BaseContext);

  const handleMessageSend = (message) => {
    baseContext.webSocket.send(message);
  };

  return (
    <div className="ChatBox">
      <div className="ChatBox-Messages"></div>
      <MessageInput onMessageSend={handleMessageSend} />
    </div>
  );
};

export default ChatBox;
