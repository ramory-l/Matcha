import React from "react";
import { Route } from "react-router-dom";
import LoginForm from "../components/loginForm";
import RegisterForm from "../components/registerForm";
import "./styles/authPage.scss";

const AuthPage = () => {
  return (
    <div className="AuthPage">
      <Route path="/auth/login" component={LoginForm} />
      <Route path="/auth/register" component={RegisterForm} />
    </div>
  );
};

export default AuthPage;
