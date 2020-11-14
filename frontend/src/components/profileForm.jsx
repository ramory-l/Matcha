import React from "react";
import Joi from "joi";
import Form from "./common/form";
import * as userService from "../services/userService";
import moment from "moment";
import { toast } from "react-toastify";
import TagInput from "./tagInput";

class ProfileForm extends Form {
  state = {
    data: {
      firstName: "",
      lastName: "",
      gender: "",
      birthday: "",
      description: "",
      tags: [],
    },
    errors: {},
  };

  populateProfileForm() {
    this.setState({
      data: this.mapToViewModel(this.props.user),
    });
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
      birthday: moment(user.birthday).format("YYYY-MM-DD") || "",
      description: user.description || "",
      tags: user.tags,
    };
  }

  schema = Joi.object({
    firstName: Joi.string().required().label("First Name"),
    lastName: Joi.string().required().label("Last Name"),
    gender: Joi.string().optional(),
    birthday: Joi.any().optional(),
    description: Joi.any().optional(),
    tags: Joi.array().optional(),
  });

  doSubmit = async () => {
    await userService.updateUser(this.state.data);
    toast.success("Changes successfully saved!");
    this.props.onEditModeChange();
  };

  render() {
    const { editMode, isMe } = this.props;
    const readonly = !(editMode && isMe);
    const { firstName, lastName } = this.state.data;
    return (
      <form onSubmit={this.handleSubmit}>
        <h1>{editMode ? "Editing profile" : firstName + " " + lastName}</h1>
        {editMode
          ? this.renderInput("firstName", "First Name", readonly)
          : null}
        {editMode ? this.renderInput("lastName", "Last Name", readonly) : null}
        {this.renderDatePicker("birthday", "Birthday", readonly)}
        {readonly
          ? this.renderInput("gender", "Gender", readonly)
          : this.renderSelect("gender", "Gender", ["woman", "man"])}
        {this.renderTextArea("description", "Description", readonly)}
        <TagInput
          tags={[
            "kek",
            "sex",
            "gay",
            "suck",
            "bitch",
            "big ass",
            "jojo",
            "anime",
            "nasvai",
            "gay porn",
            "erotic",
            "suka",
            "zaebalsya",
          ]}
          editMode={editMode}
        />
        {readonly ? null : this.renderButton("Save")}
      </form>
    );
  }
}

export default ProfileForm;
