import React from "react";
import { Redirect } from "react-router-dom";
import Form from "./common/form";
import LinkButton from "./common/linkButton";
import Joi from "joi";
import auth from "../services/authService";
import "./styles/authForm.scss";

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

  doSubmit = async () => {
    try {
      const { data } = this.state;
      await auth.login(data.username, data.password);
      const { state } = this.props.location;
      window.location = state ? state.from.pathname : "/";
    } catch (ex) {
      if (ex.response && ex.response.status === 404) {
        const errors = { ...this.state.errors };
        errors.username = ex.response.data;
        this.setState({ errors });
      }
    }
  };

  render() {
    if (auth.getCurrentUser()) return <Redirect to="/" />;
    return (
      <form className="LoginForm" onSubmit={this.handleSubmit}>
        <h1>Login Form</h1>
        {this.renderInput("username", "Username")}
        {this.renderInput("password", "Password", false, "password")}
        {this.renderButton("Login", "btn btn-dark")}
        <LinkButton
          onClick={(e) => e.preventDefault()}
          to="/auth/reset"
          className="btn btn-light"
        >
          Forgot password
        </LinkButton>
        <LinkButton
          onClick={(e) => e.preventDefault()}
          to="/auth/register"
          className="btn btn-warning ml-0"
        >
          Not a member yet?
        </LinkButton>
      </form>
    );
  }
}

export default LoginForm;
