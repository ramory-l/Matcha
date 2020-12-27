import React, { useEffect, useState } from "react";
import { rateUser } from "../services/userService";
import "./styles/usersCarousel.scss";

const UsersCarousel = ({ users }) => {
  const [fetchedUsers, setFetchedUsers] = useState([]);

  useEffect(() => {
    setFetchedUsers(users);
  }, [users]);

  const handleLikeDislike = async (action) => {
    const newUsers = [...fetchedUsers];
    const activeUser = document.querySelector(".carousel-item.active");
    const activeUserId = activeUser.firstChild.alt;
    newUsers.shift();
    setTimeout(() => setFetchedUsers(newUsers), 500);
    await rateUser(activeUserId, action);
  };

  return (
    <div
      id="carouselExampleControls"
      className="carousel modified slide"
      data-ride="carousel"
      data-interval="false"
      data-keyboard="false"
    >
      <div className="carousel-inner">
        {fetchedUsers.map((user, index) => (
          <div
            key={user.id}
            className={index === 0 ? `carousel-item active` : `carousel-item`}
          >
            <img
              src={user.avatar?.link ? user.avatar.link : "/default-avatar.png"}
              className="d-block w-100"
              alt={user.id}
            />
            <div className="carousel-caption text-dark">
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
        data-slide="next"
        onClick={() => handleLikeDislike("like")}
      >
        <i className="fa fa-thumbs-up text-success" aria-hidden="true"></i>
      </a>
      <a
        className="carousel-control-next"
        href="#carouselExampleControls"
        role="button"
        data-slide="next"
        onClick={() => handleLikeDislike("dislike")}
      >
        <i className="fa fa-thumbs-down text-danger" aria-hidden="true"></i>
      </a>
    </div>
  );
};

export default UsersCarousel;
