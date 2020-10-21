import React from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import LoginForm from "../components/loginForm";
import RegisterForm from "../components/registerForm";
import "./styles/authPage.scss";

const AuthPage = () => {
  return (
    <div className="AuthPage">
      <Switch>
        <Route path="/auth/login" component={LoginForm} />
        <Route path="/auth/register" component={RegisterForm} />
        <Redirect to="/not-found" />
      </Switch>
    </div>
  );
};

export default AuthPage;