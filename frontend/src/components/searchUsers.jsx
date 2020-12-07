import React from "react";
import SearhForm from "../components/searchForm";
import UsersCarousel from "./usersCarousel";

const SearchUsers = ({ users, userForm, onSearchButtonClick }) => {
  return (
    <>
      <div className="row justify-content-center">
        <UsersCarousel users={users} />
      </div>
      <div className="row">
        <SearhForm
          userForm={userForm}
          onSearchButtonClick={onSearchButtonClick}
        />
      </div>
    </>
  );
};

export default SearchUsers;
