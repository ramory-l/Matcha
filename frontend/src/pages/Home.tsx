import React from "react";
import MatchButton from "../components/MatchButton";
import "./styles/Home.css";

const HomePage = () => {
  return (
    <div className="Home">
      <figure className="Home-Figure">
        <img className="Figure-Avatar" src="/default-avatar.png" alt="User" />
        <figcaption className="Figure-Figcaption">
          <MatchButton backgroundColor="#4568dc" />
          Mihkail, 22
          <MatchButton backgroundColor="#b06ab3" />
        </figcaption>
      </figure>
    </div>
  );
};

export default HomePage;
