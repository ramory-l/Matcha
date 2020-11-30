import React, { useState } from "react";
import { uploadImage } from "../../services/imageService";
import { toBase64 } from "../../utils/fileToBase64";

const FileInput = ({ name, label, userId }) => {
  const [image, setImage] = useState(null);

  const handleChange = async (e) => {
    const file = e.target.files[0];
    const filename = file.name.split(".")[0];
    try {
      const imageBase64 = await toBase64(file);
      const image = {
        name: filename,
        base64: imageBase64,
        userId: userId,
      };
      setImage(image);
      console.log(image);
    } catch (ex) {
      console.log(ex);
    }
  };

  const handleUploadButtonClick = async (e) => {
    try {
      await uploadImage(image);
    } catch (ex) {
      console.log(ex);
    }
  };

  return (
    <div className="form-group">
      <label htmlFor={name}>{label}</label>
      <input
        onChange={(e) => handleChange(e)}
        accept="image/*"
        type="file"
        className="form-control-file"
        id={name}
      />
      <button
        onClick={(e) => handleUploadButtonClick(e)}
        className="btn btn-secondary my-2"
      >
        Load
      </button>
    </div>
  );
};

export default FileInput;
