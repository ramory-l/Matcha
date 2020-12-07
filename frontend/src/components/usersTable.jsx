import React, { useEffect, useState } from "react";
import { getGuests } from "../services/guestService";
import Loading from "./common/loading";
import Table from "./common/table";
// import _ from "lodash";

const UsersTable = (props) => {
  const [sortColumn, setSortColumn] = useState({
    path: "username",
    order: "asc",
  });
  const [guests, setGuests] = useState([]);

  useEffect(() => {
    async function fetchGuests() {
      const { data: guests } = await getGuests();
      console.log(guests);
      // const sorted = _.orderBy(guests, [sortColumn.path], [sortColumn.order])
      setGuests(guests);
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
    { path: "gender", label: "Gender" },
    { path: "birthday", label: "Birthday" },
  ];

  const handleSort = (sortColumn) => {
    setSortColumn(sortColumn);
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
