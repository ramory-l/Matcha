import React, { useState } from "react";
import PropTypes from "prop-types";
import _ from "lodash";
import { Link } from "react-router-dom";

const Pagination = ({ itemsCount, pageSize, currentPage, onPageChange }) => {
  const pagesCount = Math.ceil(itemsCount / pageSize);
  if (pagesCount === 1) return null;
  const [startRange, setStartRange] = useState(1);
  const [endRange, setEndRange] = useState(6);

  const handleNext = () => {
    setStartRange((prev) => prev + 1);
    setEndRange((prev) => prev + 1);
  };

  const handlePrev = () => {
    setStartRange((prev) => prev - 1);
    setEndRange((prev) => prev - 1);
  };

  const pages = _.range(startRange, endRange);
  return (
    <nav aria-label="Page navigation example">
      <ul className="pagination">
        <li class="page-item">
          <Link
            to="#"
            class="page-link"
            aria-label="Previous"
            onClick={() => handlePrev()}
          >
            <span aria-hidden="true">&laquo;</span>
          </Link>
        </li>
        {pages.map((page) => (
          <li
            key={page}
            className={page === currentPage ? "page-item active" : "page-item"}
          >
            <Link
              to="#"
              className="page-link"
              onClick={() => onPageChange(page)}
            >
              {page}
            </Link>
          </li>
        ))}
        <li class="page-item">
          <Link
            to="#"
            class="page-link"
            aria-label="Next"
            onClick={() => handleNext()}
          >
            <span aria-hidden="true">&raquo;</span>
          </Link>
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
