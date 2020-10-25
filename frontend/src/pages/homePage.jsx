import React, { useState, useEffect } from "react";
import Pagination from "../components/common/pagination";
import UserCard from "../components/userCard";
import * as userService from "../services/userService";
import { paginate } from "../utils/paginate";

const HomePage = () => {
  const [users, setUsers] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(15);

  useEffect(() => {
    async function fetchUsers() {
      const { data } = await userService.getUsers();
      setUsers(data);
    }
    fetchUsers();
  }, []);
  const paginatedUsers = paginate(users, currentPage, pageSize);

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  return (
    <>
      <div className="row justify-content-center">
        {paginatedUsers.map((user, index) => (
          <div key={index} className="col-xs-1 mx-2">
            <UserCard user={user} />
          </div>
        ))}
      </div>
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
