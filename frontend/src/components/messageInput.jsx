import React, { useState } from "react";

const MessageInput = ({ onMessageSend }) => {
  const [message, setMessage] = useState("");

  return (
    <div className="input-group">
      <input
        type="text"
        className="form-control"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
      />
      <div className="input-group-append">
        <button
          className="btn btn-outline-secondary"
          type="button"
          onClick={() => {
            onMessageSend(message);
            setMessage("");
          }}
        >
          Send
        </button>
      </div>
    </div>
  );
};

export default MessageInput;
