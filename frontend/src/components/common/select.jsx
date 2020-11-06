import React from "react";

const Select = ({ name, label, error, options, ...rest }) => {
  return (
    <div className="form-group">
      <label htmlFor={name}>{label}</label>
      <select
        {...rest}
        name={name}
        className={error ? "form-control is-invalid" : "form-control"}
        id={name}
      >
        <option value=""></option>
        {options.map((option, index) => (
          <option key={index}>{option}</option>
        ))}
      </select>
      {error && <div className="text-danger">{error}</div>}
    </div>
  );
};

export default Select;
