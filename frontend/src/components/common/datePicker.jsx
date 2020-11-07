import React from "react";

const DatePicker = ({ name, label, value, readonly, onChange }) => {
  return (
    <div className="form-group">
      <label htmlFor="start">{label}</label>
      <input
        className="form-control"
        type="date"
        id={name}
        name={name}
        value={value}
        disabled={readonly}
        onChange={onChange}
      />
    </div>
  );
};

export default DatePicker;
