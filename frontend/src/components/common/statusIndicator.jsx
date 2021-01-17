import React from "react";
import "./styles/statusIndicator.scss";

const StatusIndicator = ({ isOnline }) => {
  return (
    <div className={`StatusIndicator ${isOnline ? "online" : "offline"}`}></div>
  );
};

export default StatusIndicator;
