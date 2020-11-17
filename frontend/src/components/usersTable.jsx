import React from "react";
import Table from "./common/table";

const UsersTable = (props) => {
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
