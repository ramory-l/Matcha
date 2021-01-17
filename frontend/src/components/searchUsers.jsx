import React from "react";
import SearhForm from "../components/searchForm";
import UsersCarousel from "./usersCarousel";

const SearchUsers = ({ users, myData, onSearchButtonClick }) => {
  return (
    <>
      {users.length ? (
        <div className="row justify-content-center">
          <UsersCarousel myData={myData} users={users} />
        </div>
      ) : (
        <h1>No users found</h1>
      )}
      <div className="row justify-content-center">
        <SearhForm onSearchButtonClick={onSearchButtonClick} />
      </div>
    </>
  );
};

export default SearchUsers;
