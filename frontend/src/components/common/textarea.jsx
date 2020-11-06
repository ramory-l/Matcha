import React from "react";

const Textarea = ({ label, name, value, readonly }) => {
  return (
    <div className="form-group">
      <label htmlFor={name}>{label}</label>
      <textarea
        className="form-control"
        id={name}
        rows="3"
        defaultValue={value}
        disabled={readonly}
      ></textarea>
    </div>
  );
};

export default Textarea;
