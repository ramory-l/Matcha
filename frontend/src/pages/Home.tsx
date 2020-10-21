import React from "react";
import MatchButton from "../components/MatchButton";
import "./styles/Home.css";

const HomePage = () => {
  return (
    <div className="Home">
      <figure className="Home-Figure">
        <img className="Figure-Avatar" src="/default-avatar.png" alt="User" />
        <figcaption className="Figure-Figcaption">
          <MatchButton backgroundColor="#4568dc" innerText="&#10004;" />
          Mihkail, 22
          <MatchButton backgroundColor="#b06ab3" innerText="&#10008;" />
        </figcaption>
      </figure>
      <div className="Home-MatchesBlock">
        <span className="MatchesBlock-Title">YOUR MATCHES:</span>
        <div className="MatchesBlock-Text">
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
          Артем Тулупов <br />
        </div>
      </div>
    </div>
  );
};

export default HomePage;
