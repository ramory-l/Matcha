import React, { useContext, useState } from "react";
import FileInput from "./common/fileInput";
import { toBase64 } from "../utils/fileToBase64";
import { uploadImage } from "../services/imageService";
import UserContext from "../contexts/userContext";
import "./styles/imageFileInput.scss";
import { toast } from "react-toastify";

const ImageFileInput = ({ name, label, userId }) => {
  const [newLabel, setNewLabel] = useState(label);
  const [image, setImage] = useState(null);
  const userContext = useContext(UserContext);

  const handleChange = async (e) => {
    const file = e.target.files[0];
    if (file) {
      file.name.length > 13
        ? setNewLabel(file.name.slice(0, 7) + "...")
        : setNewLabel(file.name);
      const filename = file.name.split(".")[0];
      const imageBase64 = await toBase64(file);
      const image = {
        name: filename,
        base64: imageBase64,
        userId: userId,
      };
      setImage(image);
    }
  };

  const handleUploadButtonClick = async () => {
    if (image) {
      try {
        await uploadImage(image);
        userContext.handleNewImages();
        toast.success("Successfully added new image!");
      } catch (ex) {}
    }
  };

  return (
    <FileInput
      name={name}
      label={newLabel}
      divClassName="DivFileInput"
      className="ImageFileInput"
      onChange={handleChange}
      onUploadButtonClick={handleUploadButtonClick}
    />
  );
};

export default ImageFileInput;
