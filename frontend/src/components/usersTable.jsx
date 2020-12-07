import React, { useEffect, useState } from "react";
import { getGuests } from "../services/guestService";
import Loading from "./common/loading";
import Table from "./common/table";
import _ from "lodash";

const UsersTable = (props) => {
  const [sortColumn, setSortColumn] = useState({
    path: "username",
    order: "asc",
  });
  const [guests, setGuests] = useState([]);

  useEffect(() => {
    async function fetchGuests() {
      const { data: guests } = await getGuests();
      const sorted = _.orderBy(guests, [sortColumn.path], [sortColumn.order]);
      setGuests(sorted);
    }
    fetchGuests();
  }, []);

  const columns = [
    {
      path: "avatar",
      label: "Avatar",
      content: (avatar) => (
        <img
          style={{ width: "5vw" }}
          src={`${avatar?.url ? avatar.url : "/default-avatar.png"}`}
        />
      ),
    },
    { path: "username", label: "Username" },
    { path: "date", label: "Date" },
  ];

  const handleSort = (sortColumn) => {
    setSortColumn(sortColumn);
    const sorted = _.orderBy(guests, [sortColumn.path], [sortColumn.order]);
    setGuests(sorted);
  };

  return guests ? (
    <Table
      columns={columns}
      data={guests}
      onSort={handleSort}
      sortColumn={sortColumn}
    />
  ) : (
    <Loading />
  );
};

export default UsersTable;
