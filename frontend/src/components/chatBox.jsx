import React, { useContext, useEffect, useState, useRef } from "react";
import BaseContext from "../contexts/baseContext";
import { getCurrentUser } from "../services/authService";
import { getMessagesWithUser } from "../services/userService";
import MessageList from "./messageList";
import MessageInput from "./messageInput";
import "./styles/chatBox.scss";

const ChatBox = ({ recipient }) => {
  const [messages, setMessages] = useState([]);
  const messageEl = useRef(null);

  useEffect(() => {
    async function fetchMessages() {
      const { data: messages } = await getMessagesWithUser(recipient.id, 15, 0);
      setMessages(messages.data);
    }
    fetchMessages();
  }, [recipient.id]);

  const baseContext = useContext(BaseContext);

  useEffect(() => {
    if (baseContext.webSocket) {
      baseContext.webSocket.onmessage = (message) => {
        const data = JSON.parse(message.data);
        const newMessages = [...messages];
        newMessages.unshift(data);
        setMessages(newMessages);
      };
    }
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
    console.log(messageEl.current);
    baseContext.webSocket.send(JSON.stringify(message));
  };

  return (
    <div className="ChatBox">
      {messages ? (
        <MessageList
          ref={messageEl}
          recipient={recipient}
          messages={messages}
        />
      ) : null}
      <MessageInput onMessageSend={handleMessageSend} />
    </div>
  );
};

export default ChatBox;
