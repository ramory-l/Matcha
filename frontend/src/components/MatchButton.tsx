import React from "react";
import "./styles/MatchButton.css";

interface MatchButtonProps {
  backgroundColor: string;
}

function MatchButton(props: MatchButtonProps) {
  return (
    <div
      style={{ backgroundColor: props.backgroundColor }}
      className="MatchButton"
    ></div>
  );
}

export default MatchButton;
