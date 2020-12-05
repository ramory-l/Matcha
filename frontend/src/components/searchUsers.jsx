import React, { useRef } from "react";
import SearhForm from "../components/searchForm";
import Swiper from "react-id-swiper";
import UserCard from "../components/userCard";
import { shallowEqual } from "../utils/equal";
import { useState } from "react";

const SearchUsers = ({ users, userForm }) => {
  const [filteredUsers, setFilteredUsers] = useState(users);
  const ref = useRef(null);

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

  const handleSearchButtonClick = () => {
    const filtered = users.filter((user) => {
      const tempUser1Form = { ...user.form };
      const tempUser2Form = { ...userForm };
      delete tempUser1Form.id;
      delete tempUser2Form.id;
      return shallowEqual(tempUser1Form, tempUser2Form);
    });
    setFilteredUsers(filtered);
  };

  return (
    <>
      <div className="row">
        <Swiper ref={ref}>
          {filteredUsers.map((user) => (
            <div key={user.id}>
              <UserCard user={user} />
            </div>
          ))}
        </Swiper>
        <button onClick={goPrev}>Prev</button>
        <button onClick={goNext}>Next</button>
      </div>
      <div className="row">
        <SearhForm
          userForm={userForm}
          onSearchButtonClick={handleSearchButtonClick}
        />
      </div>
    </>
  );
};

export default SearchUsers;
