import React from "react";

const Input = ({ readonly, value, name, label, error, ...rest }) => {
  return (
    <div className="form-group">
      {label ? <label htmlFor={name}>{label}</label> : null}
      {readonly ? (
        <input
          {...rest}
          name={name}
          value=""
          placeholder={value}
          id={name}
          className="form-control"
          disabled={readonly}
        />
      ) : (
        <input
          {...rest}
          value={value}
          name={name}
          id={name}
          className={error ? "form-control is-invalid" : "form-control"}
        />
      )}
      {error && <div className="text-danger">{error}</div>}
    </div>
  );
};

export default Input;
