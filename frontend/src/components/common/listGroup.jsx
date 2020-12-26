import React from "react";
import { NavLink } from "react-router-dom";
import "./styles/listGroup.scss";

function ListGroup({ items, editMode, onEditModeChange }) {
  return (
    <ul className="list-group">
      {items.map((item, index) => (
        <NavLink
          style={{ color: "black" }}
          exact
          onClick={editMode ? onEditModeChange : null}
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
