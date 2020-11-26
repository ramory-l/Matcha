import React from "react";
import CloseButton from "./common/closeButton";
import "./styles/profileImage.scss";

const ProfileImage = ({
  index,
  dataTarget,
  image,
  modalTitle,
  editMode,
  onImageDelete,
}) => {
  return (
    <div
      className="col-12 col-sm-6 col-lg-3"
      data-target={dataTarget}
      data-slide-to={index}
    >
      {editMode ? (
        <CloseButton onClick={() => onImageDelete(index)} color="black" />
      ) : null}
      <img
        alt={`${modalTitle.slice(0, -9)} ${index}`}
        className="w-100"
        src={`${image}`}
      />
    </div>
  );
};

export default ProfileImage;
