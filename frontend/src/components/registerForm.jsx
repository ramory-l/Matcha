import React from "react";
import Form from "./common/form";
import LinkButton from "./common/linkButton";
import Joi from "joi";
import auth from "../services/authService";
import * as userService from "../services/userService";
import "./styles/authForm.scss";

class RegisterForm extends Form {
  state = {
    data: {
      username: "",
      firstName: "",
      lastName: "",
      email: "",
      to_password: "",
      password_confirm: "",
    },
    errors: {},
  };

  schema = Joi.object({
    username: Joi.string().min(2).required().label("Username"),
    firstName: Joi.string().required().label("First Name"),
    lastName: Joi.string().required().label("Last Name"),
    email: Joi.string()
      .email({ tlds: false })
      .required()
      .label("Email address"),
    to_password: Joi.string().required().label("Password"),
    password_confirm: Joi.any()
      .equal(Joi.ref("to_password"))
      .required()
      .label("Confirm password")
      .options({
        messages: { "any.only": '"Password" and {{#label}} does not match' },
      }),
  });

  doSubmit = async () => {
    try {
      const response = await userService.register(this.state.data);
      auth.loginWithJwt(response.headers["x-auth-token"]);
      window.location = "/";
    } catch (ex) {
      if (ex.response && ex.response.status === 400) {
        const errors = { ...this.state.errors };
        errors.username = ex.response.data;
        this.setState({ errors });
      }
    }
  };

  render() {
    return (
      <form className="RegisterForm" onSubmit={this.handleSubmit}>
        <span className="RegisterForm-Title">Register Form</span>
        {this.renderInput("username", "Username")}
        {this.renderInput("firstName", "First Name")}
        {this.renderInput("lastName", "Last Name")}
        {this.renderInput("email", "Email address")}
        {this.renderInput("to_password", "Password", false, "password")}
        {this.renderInput(
          "password_confirm",
          "Password Confirm",
          false,
          "password"
        )}
        {this.renderButton("Register", "btn btn-dark")}
        <LinkButton
          onClick={(e) => e.preventDefault()}
          to="/auth/login"
          className="btn btn-warning ml-0"
        >
          Already a member?
        </LinkButton>
      </form>
    );
  }
}

export default RegisterForm;
