import React from "react";
import { useState, useEffect } from "react";
import ImageModal from "./imageModal";
import ProfileImage from "./profileImage";
import "./styles/profileImages.scss";

const ProfileImages = ({ modalTitle, editMode }) => {
  const [images, setImages] = useState([]);

  useEffect(() => {
    const images = [
      "/default-avatar.png",
      "https://www.mirf.ru/wp-content/uploads/2018/05/Boku-no-Hero-Academia-1.jpg",
      "https://cdnimg.rg.ru/img/content/153/10/69/1_d_850.jpg",
      "https://lh3.googleusercontent.com/proxy/-wkNORYE6qZ-25xC99MOBqWdDflUh_3pPuXg31XNVzvher5IE-Vr5x4So2fG4054ckbWelfm8VaWGxJ4mUnPo3UVxsa5ufALBHMznX1fx81B9Se6L4x6aeSafvlpGKrNfXc-pN9ABeuHdo3YjUBzs0iGLT10vWw8Q90SaZfofapeBYNqKmnMrc4zzQ",
    ];
    setImages(images);
  }, []);

  const modalTarget = "profileImagesModal";
  const carouselTarget = "profileImagesCarousel";

  const handleImageDelete = (index) => {
    const newImages = images.filter((image, i) => i !== index);
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
            index={index}
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
