import React from "react";

const Input = ({ readonly, value, name, label, error, ...rest }) => {
  return (
    <div className="form-group">
      <label htmlFor={name}>{label}</label>
      {readonly ? (
        <input
          {...rest}
          name={name}
          placeholder={value}
          id={name}
          className="form-control"
          disabled="true"
        />
      ) : (
        <input
          {...rest}
          value={value}
          name={name}
          id={name}
          className="form-control"
        />
      )}
      {error && <div className="alert alert-danger">{error}</div>}
    </div>
  );
};

export default Input;
