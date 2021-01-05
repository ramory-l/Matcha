import React, { useEffect, useState } from "react";
import SearchUsers from "../components/searchUsers";
import WithLoading from "../components/common/withLoading";
import "./styles/searchPage.scss";
import { getUserForm, saveForm } from "../services/formService";
import { getUserRates, searchForUsers } from "../services/userService";

const SearchUsersWithLoading = WithLoading(SearchUsers);

const SearchPage = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [users, setUsers] = useState([]);
  const [userForm, setUserForm] = useState({});

  useEffect(() => {
    const form = getUserForm();
    async function searchUsers(form) {
      const { data: foundedUsers } = await searchForUsers(form);
      const { data: likesDislikes } = await getUserRates("likesDislikes", true);
      console.log(likesDislikes);
      setUserForm(form);
      setUsers(foundedUsers);
      setIsLoading(false);
    }
    if (form) searchUsers(form);
    else setIsLoading(false);
  }, []);

  const handleSearchButtonClick = async (form) => {
    saveForm(form);
    setIsLoading(true);
    const { data: foundedUsers } = await searchForUsers(form);
    setUsers(foundedUsers);
    setIsLoading(false);
    console.log("submitted");
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
