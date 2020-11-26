import React from "react";

const CloseButton = ({ color, onClick = null, type = 0 }) => {
  const types = {
    0: (color, onClick) => (
      <button
        type="button"
        className="close"
        data-dismiss="modal"
        aria-label="Close"
        onClick={onClick}
      >
        <span className={`text-${color}`} aria-hidden="true">
          &times;
        </span>
      </button>
    ),
    1: (onClick) => (
      <button
        onClick={onClick}
        type="button"
        className="btn btn-secondary"
        data-dismiss="modal"
      >
        Close
      </button>
    ),
  };

  return types[type](color, onClick);
};

export default CloseButton;
