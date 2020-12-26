import React from "react";

const InputWithButton = ({
  placeholder,
  readonly,
  value,
  name,
  error,
  onClick,
  buttonLabel,
  ...rest
}) => {
  return (
    <>
      <div className="input-group my-3">
        <input
          {...rest}
          id={name}
          name={name}
          value={value}
          className="form-control"
          placeholder={placeholder}
        />
        <div className="input-group-append">
          <button
            disabled={error}
            onClick={onClick}
            className="btn btn-outline-secondary"
            type="button"
          >
            {buttonLabel}
          </button>
        </div>
      </div>
      {error && <div className="text-danger">{error}</div>}
    </>
  );
};

export default InputWithButton;
