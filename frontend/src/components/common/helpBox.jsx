import React from "react";
import ReactTooltip from "react-tooltip";
import "./styles/helpBox.scss";

const HelpBox = ({ textInBox, text }) => {
  return (
    <>
      <p className="HelpBox-TextInBox" data-tip={textInBox}>
        {text}
      </p>
      <ReactTooltip className="HelpBox" effect="solid" />
    </>
  );
};

export default HelpBox;
