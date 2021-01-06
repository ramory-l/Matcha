import React, { useContext, useEffect, useState } from "react";
import { rateUser } from "../services/userService";
import moment from "moment";
import _ from "lodash";
import SearchContext from "../contexts/searchContext";
import "./styles/usersCarousel.scss";

const UsersCarousel = ({ users, myData }) => {
  const [fetchedUsers, setFetchedUsers] = useState([]);
  const searchContext = useContext(SearchContext);

  useEffect(() => {
    const findDistance = (firstUser, secondUser) => {
      const x = secondUser.latitude - firstUser.latitude;
      const y = secondUser.longitude - firstUser.longitude;
      return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    };
    const usersWithDistance = users.map((user) => {
      user.distance = findDistance(myData, user);
      return user;
    });

    const userWithAge = usersWithDistance.map((user) => {
      const age = moment().diff(user.birthday, "years");
      user.age = age;
      return user;
    });

    const sorted = _.orderBy(
      userWithAge,
      [searchContext.sortBy.path],
      [searchContext.sortBy.order]
    );
    console.log(searchContext.sortBy);
    setFetchedUsers(sorted);
  }, [users, searchContext.sortBy, myData]);

  const findDistance = (firstUser, secondUser) => {
    const x = secondUser.latitude - firstUser.latitude;
    const y = secondUser.longitude - firstUser.longitude;
    return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
  };

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
              <p>Age: {moment().diff(user.birthday, "years")}</p>
              <p>Rate: {user.rate}</p>
              <p>Distance: {findDistance(myData, user).toFixed(2)} km</p>
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
