import React from "react";
import ImageModal from "./imageModal";
import "./styles/profileImages.scss";

const ProfileImages = ({ modalTitle }) => {
  const images = [
    "https://www.mirf.ru/wp-content/uploads/2020/01/Deca-Dense.jpg",
    "https://www.mirf.ru/wp-content/uploads/2018/05/Boku-no-Hero-Academia-1.jpg",
    "https://cdnimg.rg.ru/img/content/153/10/69/1_d_850.jpg",
    "https://lh3.googleusercontent.com/proxy/-wkNORYE6qZ-25xC99MOBqWdDflUh_3pPuXg31XNVzvher5IE-Vr5x4So2fG4054ckbWelfm8VaWGxJ4mUnPo3UVxsa5ufALBHMznX1fx81B9Se6L4x6aeSafvlpGKrNfXc-pN9ABeuHdo3YjUBzs0iGLT10vWw8Q90SaZfofapeBYNqKmnMrc4zzQ",
  ];
  const modalTarget = "profilePhotosModal";
  const carouselTarget = "profilePhotosCarousel";

  return (
    <>
      <div
        id="gallery"
        className="row"
        data-toggle="modal"
        data-target={`#${modalTarget}`}
      >
        {images.map((image, index) => (
          <div
            key={index}
            className="col-12 col-sm-6 col-lg-3"
            data-target={`#${carouselTarget}`}
            data-slide-to={index}
          >
            <img alt={`user ${index}`} className="w-100" src={`${image}`} />
          </div>
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
