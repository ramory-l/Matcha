import React from "react";
import Joi from "joi";
import Form from "./common/form";
import * as userService from "../services/userService";

class ProfileForm extends Form {
  state = {
    data: {
      username: "",
      firstName: "",
      lastName: "",
      gender: "",
      email: "",
      birthday: "",
      description: "",
      password: "",
    },
    errors: {},
  };

  populateProfileForm() {
    this.setState({ data: this.mapToViewModel(this.props.user) });
  }

  componentDidMount() {
    this.populateProfileForm();
  }

  componentDidUpdate(prevProps) {
    if (this.props !== prevProps) this.populateProfileForm();
  }

  mapToViewModel(user) {
    return {
      username: user.username,
      firstName: user.firstName,
      lastName: user.lastName,
      gender: user.gender || "",
      email: user.email,
      birthday: user.birthday || "",
      description: user.description || "",
      password: "",
    };
  }

  schema = Joi.object({
    username: Joi.string().min(2).required().label("Username"),
    firstName: Joi.string().required().label("First Name"),
    lastName: Joi.string().required().label("Last Name"),
    gender: Joi.string().required().label("Gender"),
    email: Joi.string()
      .email({ tlds: false })
      .required()
      .label("Email address"),
    birthday: Joi.any().optional(),
    description: Joi.any().optional(),
    password: Joi.string().required().label("Password"),
  });

  doSubmit = async () => {
    await userService.updateUser(this.state.data);
  };

  render() {
    const username = this.props.match.params.username;
    const readonly = username === "me" ? false : true;
    return (
      <form onSubmit={this.handleSubmit}>
        <h1>{username === "me" ? "My" : username} Profile</h1>
        {this.renderInput("username", "Username", readonly)}
        {this.renderInput("firstName", "First Name", readonly)}
        {this.renderInput("lastName", "Last Name", readonly)}
        {readonly
          ? this.renderInput("gender", "Gender", readonly)
          : this.renderSelect("gender", "Gender", ["woman", "man"])}

        {this.renderInput("email", "Email address", readonly)}
        {readonly
          ? null
          : this.renderInput("password", "Password", readonly, "password")}
        {readonly ? null : this.renderButton("Save")}
      </form>
    );
  }
}

export default ProfileForm;
