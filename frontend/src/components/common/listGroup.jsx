import React from "react";
import { NavLink } from "react-router-dom";

function ListGroup({ items }) {
  return (
    <ul className="list-group">
      {items.map((item, index) => (
        <NavLink
          style={{ color: "black" }}
          to={item.path}
          key={index}
          className="list-group-item"
        >
          {item.title}
        </NavLink>
      ))}
    </ul>
  );
}

export default ListGroup;
