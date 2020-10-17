import React from "react";
import { Link } from "react-router-dom";
import Form from "./common/form";
import Joi from "joi";
import "./styles/authForm.scss";

class RegisterForm extends Form {
  state = {
    data: {
      username: "",
      firstName: "",
      lastName: "",
      email: "",
      password: "",
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
    password: Joi.string().required().label("Password"),
  });

  doSubmit = () => {
    console.log("submitted");
  };

  render() {
    return (
      <form className="RegisterForm" onSubmit={this.handleSubmit}>
        <h1>Register Form</h1>
        {this.renderInput("username", "Username")}
        {this.renderInput("firstName", "First Name")}
        {this.renderInput("lastName", "Last Name")}
        {this.renderInput("email", "Email address")}
        {this.renderInput("password", "Password", "password")}
        <div className="RegisterForm-Buttons">
          {this.renderButton("Register")}
          <Link to="/auth/login">
            <button className="btn btn-warning">Already a member?</button>
          </Link>
        </div>
      </form>
    );
  }
}

export default RegisterForm;
