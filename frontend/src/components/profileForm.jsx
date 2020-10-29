import React from "react";
import Joi from "joi";
import Form from "./common/form";
import { getUser } from "../services/userService";
import auth from "../services/authService";

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

  async populateProfile() {
    try {
      let username = this.props.match.params.username;
      if (username === "me") {
        username = auth.getCurrentUser().sub;
      }
      const { data: user } = await getUser(username);
      if (this.mounted) {
        this.setState({ data: this.mapToViewModel(user) });
      }
    } catch (ex) {
      if (ex.response && ex.response.status === 404)
        this.props.history.replace("/not-found");
    }
  }

  async componentDidMount() {
    this.mounted = true;
    await this.populateProfile();
  }

  componentWillUnmount() {
    this.mounted = false;
  }

  async componentDidUpdate(prevProps) {
    if (this.props.match.params.username !== prevProps.match.params.username)
      await this.populateProfile();
  }

  mapToViewModel(user) {
    return {
      username: user.username,
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      password: "",
    };
  }

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
    const username = this.props.match.params.username;
    const readonly = username === "me" ? false : true;
    return (
      <form onSubmit={this.handleSubmit}>
        <h1>{username === "me" ? "My" : username} Profile</h1>
        {this.renderInput("username", "Username", readonly)}
        {this.renderInput("firstName", "First Name", readonly)}
        {this.renderInput("lastName", "Last Name", readonly)}
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
