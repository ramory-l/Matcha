import React, { useState } from "react";
import PropTypes from "prop-types";
import _ from "lodash";
import "./styles/pagination.scss";

const Pagination = ({
  itemsCount,
  pageSize,
  currentPage,
  onPageChange,
  startRangeProp = 1,
  endRangeProp = 4,
}) => {
  const [startRange, setStartRange] = useState(startRangeProp);
  const [endRange, setEndRange] = useState(endRangeProp);

  const pagesCount = Math.ceil(itemsCount / pageSize);

  if (pagesCount === 1) return null;

  const handleRangeChange = (direction) => {
    let directionValue = 1;

    if (direction === "prev") directionValue = -1;
    onPageChange(currentPage + directionValue);
    setStartRange((prev) => prev + directionValue);
    setEndRange((prev) => prev + directionValue);
  };

  const pages = _.range(startRange, endRange);
  return (
    <nav aria-label="Page navigation example">
      <ul className="pagination">
        <li className={startRange === 1 ? "page-item disabled" : "page-item"}>
          <span
            style={{ cursor: "pointer" }}
            className="page-link"
            aria-label="Previous"
            onClick={() => handleRangeChange("prev")}
          >
            <span aria-hidden="true">&laquo;</span>
          </span>
        </li>
        {pages.map((page) => (
          <li
            style={{ cursor: "pointer" }}
            key={page}
            className={page === currentPage ? "page-item active" : "page-item"}
          >
            <span
              style={{ cursor: "pointer", color: "black" }}
              className="page-link"
              onClick={() => onPageChange(page)}
            >
              {page}
            </span>
          </li>
        ))}
        <li
          className={
            endRange === pagesCount ? "page-item disabled" : "page-item"
          }
        >
          <span
            style={{ cursor: "pointer" }}
            className="page-link"
            aria-label="Next"
            onClick={() => handleRangeChange("next")}
          >
            <span aria-hidden="true">&raquo;</span>
          </span>
        </li>
      </ul>
    </nav>
  );
};

Pagination.propTypes = {
  itemsCount: PropTypes.number.isRequired,
  pageSize: PropTypes.number.isRequired,
  currentPage: PropTypes.number.isRequired,
  onPageChange: PropTypes.func.isRequired,
};

export default Pagination;
