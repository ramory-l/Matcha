import React, { useEffect, useState } from "react";
import Loading from "./common/loading";
import Table from "./common/table";
import _ from "lodash";
import { getUserRates } from "../services/userService";

const LikersTable = (props) => {
  const [sortColumn, setSortColumn] = useState({
    path: "username",
    order: "asc",
  });
  const [likers, setLikers] = useState([]);

  useEffect(() => {
    async function fetchLikers() {
      const { data: likers } = await getUserRates("likes", false);
      const sorted = _.orderBy(likers, [sortColumn.path], [sortColumn.order]);
      setLikers(sorted);
    }
    fetchLikers();
  }, [sortColumn]);

  const columns = [
    {
      path: "avatar",
      label: "Avatar",
      content: (avatar) => (
        <img
          alt={avatar.url}
          style={{ width: "5vw" }}
          src={`${avatar?.url ? avatar.url : "/default-avatar.png"}`}
        />
      ),
      noSort: true,
    },
    { path: "username", label: "Username" },
  ];

  const handleSort = (sortColumn) => {
    setSortColumn(sortColumn);
  };

  return likers ? (
    <Table
      columns={columns}
      data={likers}
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

export default LikersTable;
