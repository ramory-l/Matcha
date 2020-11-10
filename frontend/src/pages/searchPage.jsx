import React, { useEffect, useState, useRef } from "react";
import SearhForm from "../components/searchForm";
import Swiper from "react-id-swiper";
import Loading from "../components/common/loading";
import UserCard from "../components/userCard";
import * as userService from "../services/userService";
import "swiper/swiper.scss";
import "./styles/searchPage.scss";

const SearchPage = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [users, setUsers] = useState([]);
  const ref = useRef(null);

  useEffect(() => {
    async function fetchUsers() {
      const { data: users } = await userService.getUsers();
      setUsers(users);
      setIsLoading(false);
    }
    fetchUsers();
  }, []);

  const goNext = () => {
    if (ref.current !== null && ref.current.swiper !== null) {
      ref.current.swiper.slideNext();
    }
  };

  const goPrev = () => {
    if (ref.current !== null && ref.current.swiper !== null) {
      ref.current.swiper.slidePrev();
    }
  };

  return (
    <>
      <div className="row">
        {isLoading ? (
          <Loading />
        ) : (
          <Swiper ref={ref}>
            {users.map((user) => (
              <div key={user.id}>
                <UserCard user={user} />
              </div>
            ))}
          </Swiper>
        )}
        <button onClick={goPrev}>Prev</button>
        <button onClick={goNext}>Next</button>
      </div>
      <div className="row">
        <SearhForm />
      </div>
    </>
  );
};

export default SearchPage;
