import React, { useEffect, useState } from "react";
import SearchUsers from "../components/searchUsers";
import WithLoading from "../components/common/withLoading";
import * as userService from "../services/userService";
import { findSimilarityInForms } from "../utils/equal";
import { getCurrentUser } from "../services/authService";
import "./styles/searchPage.scss";

const SearchUsersWithLoading = WithLoading(SearchUsers);

const SearchPage = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [users, setUsers] = useState([]);
  const [userForm, setUserForm] = useState({});

  useEffect(() => {
    async function fetchUsers() {
      const { data: users } = await userService.getUsers();
      const { data: user } = await userService.getUser(getCurrentUser().sub);
      const userForm = user.form;
      const { data: likesDislikes } = await userService.getUserRates(
        "likesDislikes",
        true
      );
      userForm.likesDislikes = likesDislikes;
      const filteredUsers = users.filter((user) => {
        return findSimilarityInForms(userForm, user);
      });
      setUserForm(userForm);
      setUsers(filteredUsers);
      setIsLoading(false);
    }
    fetchUsers();
  }, []);

  const handleSearchButtonClick = async () => {
    const { data: users } = await userService.getUsers();
    const { data: user } = await userService.getUser(getCurrentUser().sub);
    const userForm = user.form;
    const { data: likesDislikes } = await userService.getUserRates(
      "likesDislikes",
      true
    );
    userForm.likesDislikes = likesDislikes;
    const filteredUsers = users.filter((user) => {
      return findSimilarityInForms(userForm, user);
    });
    setUsers(filteredUsers);
  };

  return (
    <SearchUsersWithLoading
      users={users}
      userForm={userForm}
      isLoading={isLoading}
      onSearchButtonClick={handleSearchButtonClick}
    />
  );
};

export default SearchPage;
