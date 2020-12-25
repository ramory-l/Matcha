import React from "react";
import TableBody from "./tableBody";
import TableHeader from "./tableHeader";

const Table = ({ columns, sortColumn, onSort, data, style }) => {
  const { tableStyle = "table", tableBodyStyle, tableHeaderStyle } = style;

  if (data.length === 0) return <h5>No data yet</h5>;

  return (
    <>
      <table className={tableStyle}>
        <TableHeader
          className={tableHeaderStyle}
          columns={columns}
          sortColumn={sortColumn}
          onSort={onSort}
        />
        <TableBody className={tableBodyStyle} data={data} columns={columns} />
      </table>
    </>
  );
};

export default Table;
