import React from "react";

const FileInput = ({ name, label, onChange, onUploadButtonClick }) => {
  return (
    <div className="form-group">
      <label htmlFor={name}>{label}</label>
      <input
        onChange={(e) => onChange(e)}
        accept="image/*"
        type="file"
        className="form-control-file"
        id={name}
      />
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
