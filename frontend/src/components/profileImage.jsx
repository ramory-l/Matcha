import React from "react";
import CloseButton from "./common/closeButton";
import "./styles/profileImage.scss";

const ProfileImage = ({
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
      data-slide-to={image.id}
    >
      {editMode ? (
        <CloseButton onClick={() => onImageDelete(image.id)} color="black" />
      ) : null}
      <img
        alt={`${modalTitle.slice(0, -9)} ${image.id}`}
        className="w-100"
        src={`${image.link}`}
      />
    </div>
  );
};

export default ProfileImage;
