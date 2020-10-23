import React, { useState, useEffect } from "react";
import UserCard from "../components/userCard";
import * as userService from "../services/userService";

const HomePage = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    async function fetchUsers() {
      const users = await userService.getUsers();
      setUsers(users);
    }
    fetchUsers();
  }, []);

  return (
    // <div className="row justify-content-center">
    //   {users.map((user, index) => (
    //     <div key={index} className="col-xs-1 mx-2">
    //       <UserCard user={user} />
    //     </div>
    //   ))}
    // </div>
    null
  );
};

export default HomePage;
