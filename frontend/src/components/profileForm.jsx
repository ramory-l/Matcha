import React from "react";
import Joi from "joi";
import Form from "./common/form";
import * as userService from "../services/userService";

class ProfileForm extends Form {
  state = {
    data: {
      firstName: "",
      lastName: "",
      gender: "",
      birthday: "",
      description: "",
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
      firstName: user.firstName,
      lastName: user.lastName,
      gender: user.gender || "",
      birthday: user.birthday || "",
      description: user.description || "",
    };
  }

  schema = Joi.object({
    firstName: Joi.string().required().label("First Name"),
    lastName: Joi.string().required().label("Last Name"),
    gender: Joi.string().required().label("Gender"),
    birthday: Joi.any().optional(),
    description: Joi.any().optional(),
  });

  doSubmit = async () => {
    await userService.updateUser(this.state.data);
  };

  render() {
    const readonly = !this.props.isMe;
    const { firstName } = this.state.data;
    return (
      <form onSubmit={this.handleSubmit}>
        <h1>{this.props.isMe ? "My" : `${firstName}'s`} Profile</h1>
        {this.renderInput("firstName", "First Name", readonly)}
        {this.renderInput("lastName", "Last Name", readonly)}
        {this.renderDatePicker("birthday", "Birthday")}
        {readonly
          ? this.renderInput("gender", "Gender", readonly)
          : this.renderSelect("gender", "Gender", ["woman", "man"])}
        {this.renderTextarea("description", "Description", readonly)}
        {readonly ? null : this.renderButton("Save")}
      </form>
    );
  }
}

export default ProfileForm;
