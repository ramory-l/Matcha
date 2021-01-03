import React from "react";

const CheckBox = ({ label, name, onChange = null, value }) => {
  console.log(value);
  return (
    <div className="form-group form-check">
      <input
        onChange={onChange}
        type="checkbox"
        className="form-check-input"
        name={name}
        id={name}
        value={value}
      />
      <label className="form-check-label" htmlFor={name}>
        {label}
      </label>
    </div>
  );
};

export default CheckBox;
