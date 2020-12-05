import React, { useEffect, useState } from "react";
import * as userService from "../services/userService";
import { getUserForm } from "../services/formService";
import "swiper/swiper.scss";
import "./styles/searchPage.scss";
import SearchUsers from "../components/searchUsers";
import WithLoading from "../components/common/withLoading";
import { shallowEqual } from "../utils/equal";

const SearchUsersWithLoading = WithLoading(SearchUsers);

const SearchPage = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [users, setUsers] = useState([]);
  const [userForm, setUserForm] = useState({});

  useEffect(() => {
    async function fetchUsers() {
      const { data: users } = await userService.getUsers();
      const { data: userForm } = await getUserForm();
      const filteredUsers = users.filter((user) => {
        const tempUser1Form = { ...user.form };
        const tempUser2Form = { ...userForm };
        delete tempUser1Form.id;
        delete tempUser2Form.id;
        return shallowEqual(tempUser1Form, tempUser2Form);
      });
      setUserForm(userForm);
      setUsers(filteredUsers);
      setIsLoading(false);
    }
    fetchUsers();
  }, []);

  return (
    <SearchUsersWithLoading
      users={users}
      userForm={userForm}
      isLoading={isLoading}
    />
  );
};

export default SearchPage;
