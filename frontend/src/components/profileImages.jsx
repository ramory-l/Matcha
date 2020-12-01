import React from "react";
import { useState, useEffect } from "react";
import { deleteUserImage, getUserImages } from "../services/imageService";
import ImageModal from "./imageModal";
import ProfileImage from "./profileImage";
import "./styles/profileImages.scss";

const ProfileImages = ({ userId, modalTitle, editMode }) => {
  const [images, setImages] = useState([]);

  useEffect(() => {
    async function fetchUserImages() {
      const { data: images } = await getUserImages(userId);
      setImages(images);
    }
    fetchUserImages();
  }, [userId]);

  const modalTarget = "profileImagesModal";
  const carouselTarget = "profileImagesCarousel";

  const handleImageDelete = async (id) => {
    const newImages = images.filter((image) => image.id !== id);
    await deleteUserImage(id);
    setImages(newImages);
  };

  return (
    <>
      <div
        id="gallery"
        className="row"
        data-toggle={editMode ? null : "modal"}
        data-target={editMode ? null : `#${modalTarget}`}
      >
        {images.map((image, index) => (
          <ProfileImage
            key={index}
            image={image}
            onImageDelete={handleImageDelete}
            dataTarget={`#${carouselTarget}`}
            modalTitle={modalTitle}
            editMode={editMode}
          />
        ))}
      </div>
      <ImageModal
        images={images}
        modalTitle={modalTitle}
        modalTarget={modalTarget}
        carouselTarget={carouselTarget}
      />
    </>
  );
};

export default ProfileImages;
