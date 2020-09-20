import React from "react";
import "./styles/Nav.css";
import { Link } from "react-router-dom";

interface INavProps {
  isAuthenticated: boolean;
}

const Nav: React.FC<INavProps> = ({ isAuthenticated }) => {
  return (
    <nav className="Nav">
      <span className="Nav-Title">Matcha</span>
      <ul className="Nav-Ul">
        <Link to="/">
          <li>Home</li>
        </Link>
        <Link to="/profile">
          <li>Profile</li>
        </Link>
      </ul>
    </nav>
  );
};

export default Nav;
