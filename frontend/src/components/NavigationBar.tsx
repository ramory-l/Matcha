import React, { FC, useState } from "react";
import "./styles/NavigationBar.css";

interface NavigationBarProps {
  title: string;
}

const NavigationBar: FC<NavigationBarProps> = ({ title }) => {
  const [navBarState, setNavBarState] = useState("mnavbar");

  const toogleNavBarState = () => {
    if (navBarState === "mnavbar") {
      setNavBarState("mnavbar responsive");
    } else {
      setNavBarState("mnavbar");
    }
  };

  return (
    <div className={navBarState} id="matcha navbar">
      <a href="#home" className="active">
        Home
      </a>
      <a href="#news">News</a>
      <a href="#contact">Contact</a>
      <a href="#about">About</a>
      <a className="icon" onClick={toogleNavBarState}>
        <i className="fa fa-bars"></i>
      </a>
    </div>
  );
};

export default NavigationBar;
