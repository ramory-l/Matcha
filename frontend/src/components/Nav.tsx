import React, { useState } from "react";
import "./styles/Nav.css";
import { Link } from "react-router-dom";

interface INavProps {
  isAuthenticated: boolean;
}

const Nav: React.FC<INavProps> = ({ isAuthenticated }) => {
  return (
    <nav className="Nav">
      <h3>Matcha</h3>
      <ul className="Nav-Ul">
        <Link to="/">
          <li>Home</li>
        </Link>
        {isAuthenticated ? (
          <Link to="/profile">
            <li>Profile</li>
          </Link>
        ) : (
          ""
        )}
      </ul>
    </nav>
  );
};

export default Nav;
