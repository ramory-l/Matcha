import React from "react";

const FileInput = ({
  name,
  label,
  onChange,
  onUploadButtonClick,
  className = "form-control-file",
  divClassName = "form-group",
}) => {
  return (
    <div className={divClassName}>
      <input
        onChange={(e) => onChange(e)}
        accept="image/*"
        type="file"
        className={className}
        id={name}
        name={name}
      />
      <label htmlFor={name}>{label}</label>
      <button
        onClick={(e) => onUploadButtonClick(e)}
        className="btn btn-secondary my-2"
      >
        Load
      </button>
    </div>
  );
};

export default FileInput;
