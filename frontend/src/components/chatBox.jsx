import React, { useContext, useEffect, useState } from "react";
import BaseContext from "../contexts/baseContext";
import { getCurrentUser } from "../services/authService";
import { getMessagesWithUser } from "../services/userService";
import MessageList from "./messageList";
import MessageInput from "./messageInput";
import "./styles/chatBox.scss";

const ChatBox = (props) => {
  const { recipient } = props;
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    async function fetchMessages() {
      const { data: messages } = await getMessagesWithUser(
        recipient.id,
        120,
        0
      );
      setMessages(messages.data);
    }
    fetchMessages();
  }, [recipient.id]);

  const baseContext = useContext(BaseContext);

  useEffect(() => {
    const handleMessage = (message) => {
      const data = JSON.parse(message.data);
      if (data.type === "message") {
        const newMessages = [...messages];
        newMessages.unshift(data);
        setMessages(newMessages);
      }
    };
    if (baseContext.webSocket) {
      baseContext.webSocket.addEventListener("message", handleMessage);
    }
    return () => {
      baseContext.webSocket.removeEventListener("message", handleMessage);
      console.log("closed");
    };
  }, [baseContext.webSocket, messages]);

  const handleMessageSend = (messageString) => {
    const newMessages = [...messages];
    const message = {
      from: getCurrentUser().id,
      to: recipient.id,
      message: messageString,
      createTs: Date.now(),
      type: "message",
    };
    newMessages.unshift(message);
    setMessages(newMessages);
    baseContext.webSocket.send(JSON.stringify(message));
  };

  return (
    <div className="ChatBox">
      {messages ? (
        <MessageList recipient={recipient} messages={messages} />
      ) : null}
      <MessageInput onMessageSend={handleMessageSend} />
    </div>
  );
};

export default ChatBox;
