import React, { useState, useEffect } from "react";
import Pagination from "../components/common/pagination";
import * as userService from "../services/userService";
import { paginate } from "../utils/paginate";
import auth from "../services/authService";
import Users from "../components/users";
import WithLoading from "../components/common/withLoading";

const UsersWithLoading = WithLoading(Users);

const HomePage = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [users, setUsers] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [likes, setLikes] = useState([]);
  const [dislikes, setDislikes] = useState([]);
  const pageSize = useState(10)[0];

  useEffect(() => {
    async function fetchUsers() {
      const { data: users } = await userService.getUsers();
      const { data: likes } = await userService.getUserRates("likes", true);
      const { data: dislikes } = await userService.getUserRates(
        "dislikes",
        true
      );
      setLikes(likes);
      setDislikes(dislikes);
      setUsers(users);
      setIsLoading(false);
    }
    fetchUsers();
  }, []);

  const filtered = users.filter((user) => {
    if (likes.includes(user.id)) user.isLiked = true;
    if (dislikes.includes(user.id)) user.isDisliked = true;
    return user.id !== auth.getCurrentUser().id;
  });
  const paginatedUsers = paginate(filtered, currentPage, pageSize);

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  return (
    <>
      <UsersWithLoading isLoading={isLoading} users={paginatedUsers} />
      <Pagination
        itemsCount={users.length}
        pageSize={pageSize}
        currentPage={currentPage}
        onPageChange={handlePageChange}
      />
    </>
  );
};

export default HomePage;
