import React from "react";
import { Link } from "react-router-dom";
import Form from "./common/form";
import Joi from "joi";
import "./styles/loginForm.scss";

class LoginForm extends Form {
  state = {
    data: {
      username: "",
      password: "",
    },
    errors: {},
  };

  schema = Joi.object({
    username: Joi.string().min(2).required().label("Username"),
    password: Joi.string().required().label("Password"),
  });

  doSubmit = () => {
    console.log("submitted");
  };

  render() {
    return (
      <form className="LoginForm" onSubmit={this.handleSubmit}>
        <h1>Login Form</h1>
        {this.renderInput("username", "Username")}
        {this.renderInput("password", "Password", "password")}
        {this.renderCheckbox("rememberme", "Remember me")}
        <div className="LoginForm-Buttons">
          {this.renderButton("Login")}
          <Link to="/auth/register">
            <button className="btn btn-warning">Not a member yet?</button>
          </Link>
        </div>
      </form>
    );
  }
}

export default LoginForm;
