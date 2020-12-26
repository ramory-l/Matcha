import React, { useState, useEffect } from "react";
import Pagination from "../components/common/pagination";
import Users from "../components/users";
import WithLoading from "../components/common/withLoading";
import queryString from "query-string";
import auth from "../services/authService";
import { paginate } from "../utils/paginate";
import * as userService from "../services/userService";
import { getUsersWithTag } from "../services/tagsService";

const UsersWithLoading = WithLoading(Users);

const HomePage = (props) => {
  const [isLoading, setIsLoading] = useState(true);
  const [users, setUsers] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [likesDislikes, setLikesDislikes] = useState([]);
  const pageSize = useState(10)[0];

  useEffect(() => {
    async function fetchUsers() {
      const { data: users } = await userService.getUsers();
      const { data: likesDislikes } = await userService.getUserRates(
        "likesDislikes",
        true
      );
      const { tag } = queryString.parse(props.location.search);
      setLikesDislikes(likesDislikes);
      if (tag) {
        const { data: usersWithTag } = await getUsersWithTag(tag);
        setUsers(usersWithTag);
      } else {
        setUsers(users);
      }
      setIsLoading(false);
    }
    fetchUsers();
  }, [props.location.search]);

  const filtered = users.filter((user) => {
    if (likesDislikes["likes"].filter((like) => like.id === user.id).length)
      user.isLiked = true;
    if (
      likesDislikes["dislikes"].filter((dislike) => dislike.id === user.id)
        .length
    )
      user.isDisliked = true;
    return user.id !== auth.getCurrentUser().id;
  });
  const paginatedUsers = paginate(filtered, currentPage, pageSize);

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  return (
    <>
      {paginatedUsers.length === 0 && !isLoading ? (
        <h1>No users found</h1>
      ) : (
        <>
          <UsersWithLoading isLoading={isLoading} users={paginatedUsers} />
          <Pagination
            itemsCount={users.length}
            pageSize={pageSize}
            currentPage={currentPage}
            onPageChange={handlePageChange}
          />
        </>
      )}
    </>
  );
};

export default HomePage;
