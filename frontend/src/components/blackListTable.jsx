import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Loading from "./common/loading";
import Table from "./common/table";
import { getUserBlacklist } from "../services/userService";
import _ from "lodash";
import moment from "moment";

const BlackListTable = (props) => {
  const [sortColumn, setSortColumn] = useState({
    path: "username",
    order: "asc",
  });
  const [blackList, setBlackList] = useState([]);

  useEffect(() => {
    async function fetchBlackList() {
      const { data: blackList } = await getUserBlacklist();
      const modifiedBlackList = blackList.map((blockedUser) => {
        const newBlockedUser = { ...blockedUser };
        const age = moment().diff(newBlockedUser.birthday, "years");
        newBlockedUser.gender = newBlockedUser.gender
          ? newBlockedUser.gender
          : "No gender";
        newBlockedUser.birthday = isNaN(age) ? "No age" : age;
        return newBlockedUser;
      });
      const sorted = _.orderBy(
        modifiedBlackList,
        [sortColumn.path],
        [sortColumn.order]
      );
      setBlackList(sorted);
    }
    fetchBlackList();
  }, [sortColumn]);

  const columns = [
    {
      path: "avatar",
      label: "Avatar",
      content: (user) => (
        <img
          alt={user.username}
          style={{ width: "50px" }}
          src={`${
            user.avatar?.link ? user.avatar.link : "/default-avatar.png"
          }`}
        />
      ),
      noSort: true,
    },
    {
      path: "username",
      label: "Username",
      content: (user) => {
        return (
          <Link style={{ color: "black" }} to={`/profile/${user.username}`}>
            {user.username}
          </Link>
        );
      },
    },
    { path: "gender", label: "Gender" },
    { path: "birthday", label: "Age" },
  ];

  const handleSort = (sortColumn) => {
    setSortColumn(sortColumn);
  };

  return blackList ? (
    <Table
      columns={columns}
      data={blackList}
      onSort={handleSort}
      sortColumn={sortColumn}
      style={{
        tableStyle: "table table-striped",
        tableHeaderStyle: "thead-dark",
      }}
    />
  ) : (
    <Loading />
  );
};

export default BlackListTable;
