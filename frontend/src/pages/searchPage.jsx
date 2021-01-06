import React, { useEffect, useState } from "react";
import SearchUsers from "../components/searchUsers";
import WithLoading from "../components/common/withLoading";
import { getUserForm, saveForm } from "../services/formService";
import { getUser, getUserRates, searchForUsers } from "../services/userService";
import { getCurrentUser } from "../services/authService";
import SearchContext from "../contexts/searchContext";
import "./styles/searchPage.scss";

const SearchUsersWithLoading = WithLoading(SearchUsers);

const SearchPage = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [users, setUsers] = useState([]);
  const [myData, setMyData] = useState({});
  const [sortBy, setSortBy] = useState({
    path: "distance",
    order: "asc",
  });

  useEffect(() => {
    const searchUsers = async () => {
      const { data: myData } = await getUser(getCurrentUser().sub);
      setMyData(myData);
      const form = getUserForm();
      const { data: foundedUsers } = await searchForUsers(form);
      const { data: likesDislikes } = await getUserRates("likesDislikes", true);
      const filtered = foundedUsers.filter((user) => {
        if (likesDislikes.likes.filter((like) => user.id === like.id).length)
          return false;
        if (
          likesDislikes.dislikes.filter((dislike) => user.id === dislike.id)
            .length
        )
          return false;
        return true;
      });
      setUsers(filtered);
      setIsLoading(false);
    };
    searchUsers();
  }, []);

  const handleSearchButtonClick = async (form) => {
    saveForm(form);
    setIsLoading(true);
    const { data: foundedUsers } = await searchForUsers(getUserForm());
    const { data: likesDislikes } = await getUserRates("likesDislikes", true);
    const filtered = foundedUsers.filter((user) => {
      if (likesDislikes.likes.filter((like) => user.id === like.id).length)
        return false;
      if (
        likesDislikes.dislikes.filter((dislike) => user.id === dislike.id)
          .length
      )
        return false;
      return true;
    });
    setUsers(filtered);
    setIsLoading(false);
    console.log("submitted");
  };

  return (
    <SearchContext.Provider value={{ sortBy, setSortBy }}>
      <SearchUsersWithLoading
        users={users}
        myData={myData}
        isLoading={isLoading}
        onSearchButtonClick={handleSearchButtonClick}
      />
    </SearchContext.Provider>
  );
};

export default SearchPage;
