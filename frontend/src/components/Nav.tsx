import React from "react";
import "./styles/Nav.css";
import { Link } from "react-router-dom";

function Nav() {
  return (
    <nav className="Nav">
      <h3>Logo</h3>
      <ul className="Nav-Ul">
        <Link to="/profile">
          <li>Profile</li>
        </Link>
      </ul>
    </nav>
  );
}

export default Nav;
