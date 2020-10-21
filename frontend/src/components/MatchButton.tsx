import React from "react";
import "./styles/MatchButton.css";

interface MatchButtonProps {
  backgroundColor: string;
  innerText: string;
}

function MatchButton(props: MatchButtonProps) {
  return (
    <div
      style={{ backgroundColor: props.backgroundColor }}
      className="MatchButton"
    >
      {props.innerText}
    </div>
  );
}

export default MatchButton;
