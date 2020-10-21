import React from "react";
import { useHistory, useLocation } from "react-router-dom";
import { fakeAuth } from "../App";
import "./styles/Login.css";

function LoginPage() {
  let history = useHistory();
  let location = useLocation();

  let { from } = (location.state as any) || { from: { pathname: "/" } };
  let login = () => {
    fakeAuth.authenticate(() => {
      history.replace(from);
    });
  };

  return (
    <div className="Login">
      <span className="Login-Title">Find Your Match. On Matcha.</span>
      <form className="Login-Form" action="">
        <label htmlFor="username">Username:</label>
        <br />
        <input type="text" name="username" />
        <br />
        <br />
        <label htmlFor="password">Password:</label>
        <br />
        <input type="text" name="password" />
        <br />
        <br />
        <input
          className="Form-LoginButton"
          onClick={login}
          type="button"
          value="Log in"
        />
        <input className="Form-RegisterButton" type="button" value="Register" />
      </form>
    </div>
  );
}

export default LoginPage;
