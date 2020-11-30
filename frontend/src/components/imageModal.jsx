import React from "react";
import Modal from "./common/modal";
import "./styles/imageModal.scss";

const ImageModal = ({ images, modalTitle, modalTarget, carouselTarget }) => {
  return (
    <Modal modalTarget={modalTarget} modalTitle={modalTitle}>
      <div id={carouselTarget} className="carousel slide" data-ride="carousel">
        <ol className="carousel-indicators">
          {images.map((image, index) => (
            <li
              key={index}
              data-target={`#${carouselTarget}`}
              data-slide-to={index}
              className={index === 0 ? "active" : ""}
            ></li>
          ))}
        </ol>
        <div className="carousel-inner">
          {images.map((image, index) => (
            <div
              key={index}
              className={index === 0 ? `carousel-item active` : `carousel-item`}
            >
              <img
                alt={`${modalTitle.slice(0, -9)} ${index}`}
                className="d-block w-100"
                src={`${image.link}`}
              />
            </div>
          ))}
        </div>
        <a
          className="carousel-control-prev"
          href={`#${carouselTarget}`}
          role="button"
          data-slide="prev"
        >
          <span
            className="carousel-control-prev-icon"
            aria-hidden="true"
          ></span>
          <span className="sr-only">Previous</span>
        </a>
        <a
          className="carousel-control-next"
          href={`#${carouselTarget}`}
          role="button"
          data-slide="next"
        >
          <span
            className="carousel-control-next-icon"
            aria-hidden="true"
          ></span>
          <span className="sr-only">Next</span>
        </a>
      </div>
    </Modal>
  );
};

export default ImageModal;
