import React from "react";

const FileInput = () => {
  return (
    <div className="form-group">
      <label for="exampleFormControlFile1">Example file input</label>
      <input
        type="file"
        className="form-control-file"
        id="exampleFormControlFile1"
      />
    </div>
  );
};

export default FileInput;
