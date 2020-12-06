import React from "react";

const UsersCarousel = ({ users }) => {
  return (
    <div
      id="carouselExampleControls"
      className="carousel slide"
      data-ride="carousel"
    >
      <div className="carousel-inner">
        {users.map((user, index) => (
          <div
            key={user.id}
            className={index === 0 ? `carousel-item active` : `carousel-item`}
          >
            <img
              src="/default-avatar.png"
              className="d-block w-100"
              alt={user.id}
            />
            <div className="carousel-caption d-none d-md-block text-dark">
              <h5>
                {user.firstName} {user.lastName}
              </h5>
              <p>{user.description}</p>
            </div>
          </div>
        ))}
      </div>
      <a
        className="carousel-control-prev"
        href="#carouselExampleControls"
        role="button"
        data-slide="prev"
      >
        <span className="carousel-control-prev-icon" aria-hidden="true"></span>
        <span className="sr-only">Previous</span>
      </a>
      <a
        className="carousel-control-next"
        href="#carouselExampleControls"
        role="button"
        data-slide="next"
      >
        <span className="carousel-control-next-icon" aria-hidden="true"></span>
        <span className="sr-only">Next</span>
      </a>
    </div>
  );
};

export default UsersCarousel;
