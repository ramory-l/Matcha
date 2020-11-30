import React from "react";

const TextArea = ({ label, name, value, readonly, onChange }) => {
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
    </div>
  );
};

export default TextArea;
