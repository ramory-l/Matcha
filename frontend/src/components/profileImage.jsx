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
      className="ProfileImage"
      data-target={dataTarget}
      data-slide-to={index}
    >
      {editMode ? (
        <CloseButton onClick={() => onImageDelete(image.id)} color="black" />
      ) : null}
      <img
        alt={`${modalTitle.slice(0, -9)} ${image.id}`}
        src={`${image.link}`}
      />
    </div>
  );
};

export default ProfileImage;
