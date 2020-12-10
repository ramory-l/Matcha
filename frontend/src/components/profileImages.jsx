import React, { useState, useEffect, useContext } from "react";
import ImageModal from "./imageModal";
import ProfileImage from "./profileImage";
import { deleteUserImage, getUserImages } from "../services/imageService";
import "./styles/profileImages.scss";
import UserContext from "../contexts/userContext";

const ProfileImages = ({ userId, modalTitle, editMode }) => {
  const [images, setImages] = useState([]);
  const userContext = useContext(UserContext);

  useEffect(() => {
    async function fetchUserImages() {
      const { data: images } = await getUserImages(userId);
      setImages(images);
    }
    fetchUserImages();
  }, [userId, userContext.images]);

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
        data-toggle={editMode ? null : "modal"}
        data-target={editMode ? null : `#${modalTarget}`}
      >
        {images.map((image, index) => (
          <ProfileImage
            index={index}
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
