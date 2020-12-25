import React from "react";

const TextArea = ({ label, name, value, readonly, onChange, error }) => {
  return (
    <div className="form-group">
      <label htmlFor={name}>{label}</label>
      <textarea
        className="form-control"
        id={name}
        name={name}
        rows="3"
        value={value}
        disabled={readonly}
        onChange={onChange}
      ></textarea>
      {error && <div className="text-danger">{error}</div>}
    </div>
  );
};

export default TextArea;
