import React from "react";

const CheckBox = ({ label, name }) => {
  return (
    <div className="form-group form-check">
      <input
        type="checkbox"
        className="form-check-input"
        name={name}
        id={name}
      />
      <label className="form-check-label" htmlFor={name}>
        {label}
      </label>
    </div>
  );
};

export default CheckBox;
