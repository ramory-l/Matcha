import React from "react";

const DatePicker = ({ name, label, value, readonly, error, onChange }) => {
  return (
    <div className="form-group">
      <label htmlFor="start">{label}</label>
      <input
        className={error ? "form-control is-invalid" : "form-control"}
        type="date"
        id={name}
        name={name}
        value={value}
        disabled={readonly}
        onChange={onChange}
        min="1920-01-01"
        max="2002-12-31"
      />
      {error && <div className="text-danger">{error}</div>}
    </div>
  );
};

export default DatePicker;
