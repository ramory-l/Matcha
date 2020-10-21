import React from "react";
import Joi from "joi";
import Form from "./common/form";

class ProfileForm extends Form {
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
      <form onSubmit={this.handleSubmit}>
        <h1>My Profile</h1>
        {this.renderInput("username", "Username")}
        {this.renderInput("firstName", "First Name")}
        {this.renderInput("lastName", "Last Name")}
        {this.renderInput("email", "Email address")}
        {this.renderInput("password", "Password", "password")}
        {this.renderButton("Save")}
      </form>
    );
  }
}

export default ProfileForm;
