import React, { useEffect, useState } from "react";
import { getGuests } from "../services/guestService";
import Loading from "./common/loading";
import Table from "./common/table";
import _ from "lodash";
import moment from "moment";

const UsersTable = (props) => {
  const [sortColumn, setSortColumn] = useState({
    path: "username",
    order: "asc",
  });
  const [guests, setGuests] = useState([]);

  useEffect(() => {
    async function fetchGuests() {
      const { data: guests } = await getGuests();
      const modifiedGuests = guests.map((guest) => {
        const newGuest = { ...guest };
        newGuest.date = moment(newGuest.date).format("YYYY-MM-DD, h:mm:ss a");
        return newGuest;
      });
      const sorted = _.orderBy(
        modifiedGuests,
        [sortColumn.path],
        [sortColumn.order]
      );
      setGuests(sorted);
    }
    fetchGuests();
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
    { path: "date", label: "Date" },
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
      style={{
        tableStyle: "table table-striped",
        tableHeaderStyle: "thead-dark",
      }}
    />
  ) : (
    <Loading />
  );
};

export default UsersTable;
