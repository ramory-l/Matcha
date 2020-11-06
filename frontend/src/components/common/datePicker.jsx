import React from "react";

const DatePicker = ({ name, label, value }) => {
  return (
    <div className="form-group">
      <label htmlFor="start">{label}</label>
      <input
        type="date"
        id={name}
        name={name}
        defaultValue={value}
        min="2001-01-01"
      />
    </div>
  );
};

export default DatePicker;
