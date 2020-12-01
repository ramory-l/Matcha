import React, { useEffect } from "react";
import { getGuests } from "../services/guestService";
import Table from "./common/table";

const UsersTable = (props) => {
  useEffect(() => {
    async function fetchGuests() {
      const { data: guests } = await getGuests();
      console.log(guests);
    }
    fetchGuests();
  }, []);

  const columns = [];
  const { users, onSort, sortColumn } = props;
  return (
    <Table
      columns={columns}
      data={users}
      onSort={onSort}
      sortColumn={sortColumn}
    />
  );
};

export default UsersTable;
