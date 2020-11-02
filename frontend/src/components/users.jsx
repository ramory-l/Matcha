import React from "react";
import UserCard from "../components/userCard";

const Users = (props) => {
  const users = props.users;
  return (
    <div className="row justify-content-center">
      {users.map((user) => (
        <div key={user.id} className="col-xs-1 mx-2">
          <UserCard user={user} />
        </div>
      ))}
    </div>
  );
};

export default Users;
