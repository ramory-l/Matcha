import React, { useState } from "react";

const MessageInput = ({ onMessageSend }) => {
  const [message, setMessage] = useState("");

  const handleSendButtonPress = () => {
    if (message) {
      onMessageSend(message);
      setMessage("");
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      handleSendButtonPress();
    }
  };

  return (
    <div className="input-group">
      <input
        type="text"
        className="form-control"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        onKeyPress={handleKeyPress}
      />
      <div className="input-group-append">
        <button
          className="btn btn-outline-secondary"
          type="button"
          onClick={handleSendButtonPress}
        >
          Send
        </button>
      </div>
    </div>
  );
};

export default MessageInput;
