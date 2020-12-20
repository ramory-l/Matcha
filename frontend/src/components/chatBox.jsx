import React, { useContext } from "react";
import BaseContext from "../contexts/baseContext";
import { getCurrentUser } from "../services/authService";
import MessageInput from "./messageInput";
import "./styles/chatBox.scss";

const ChatBox = ({ recipient }) => {
  const baseContext = useContext(BaseContext);

  const handleMessageSend = (messageString) => {
    const message = {
      from: getCurrentUser().id,
      to: recipient.id,
      message: messageString,
      createTS: Date.now(),
      type: "message",
    };
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
