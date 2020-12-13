import React from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import LoginForm from "../components/loginForm";
import RegisterForm from "../components/registerForm";
import ParticlesBg from "particles-bg";
import icon from "./files/icon";
import "./styles/authPage.scss";

const AuthPage = () => {
  let config = {
    num: [4, 7],
    rps: 0.1,
    radius: [5, 40],
    life: [1.5, 3],
    v: [2, 3],
    tha: [-50, 50],
    alpha: [0.6, 0],
    scale: [0.1, 0.9],
    body: icon,
    position: "all",
    //color: ["random", "#ff0000"],
    cross: "dead",
    random: 10,
  };
  return (
    <div className="AuthPage">
      <Switch>
        <Route path="/auth/login" component={LoginForm} />
        <Route path="/auth/register" component={RegisterForm} />
        <Redirect to="/not-found" />
      </Switch>
      <ParticlesBg type="custom" config={config} bg={true} />
    </div>
  );
};

export default AuthPage;
