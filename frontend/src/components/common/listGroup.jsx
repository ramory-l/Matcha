import React from "react";

function ListGroup({ items, selectedItem, onItemSelect }) {
  return (
    <ul className="list-group">
      {items.map((item, index) => (
        <li
          onClick={() => onItemSelect(item)}
          key={index}
          className={
            item === selectedItem ? "list-group-item active" : "list-group-item"
          }
        >
          {item}
        </li>
      ))}
    </ul>
  );
}

export default ListGroup;
