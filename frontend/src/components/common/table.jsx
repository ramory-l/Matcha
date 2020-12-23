import React, { useState } from "react";
import { paginate } from "../../utils/paginate";
import Pagination from "./pagination";
import TableBody from "./tableBody";
import TableHeader from "./tableHeader";

const Table = ({ columns, sortColumn, onSort, data, style }) => {
  const { tableStyle = "table", tableBodyStyle, tableHeaderStyle } = style;
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = useState(5)[0];

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  if (data.length === 0) return <h5>No data yet</h5>;

  const paginatedData = paginate(data, currentPage, pageSize);

  return (
    <>
      <table className={tableStyle}>
        <TableHeader
          className={tableHeaderStyle}
          columns={columns}
          sortColumn={sortColumn}
          onSort={onSort}
        />
        <TableBody
          className={tableBodyStyle}
          data={paginatedData}
          columns={columns}
        />
      </table>
      <Pagination
        itemsCount={data.length}
        pageSize={pageSize}
        currentPage={currentPage}
        onPageChange={handlePageChange}
        endRangeProp={3}
      />
    </>
  );
};

export default Table;
