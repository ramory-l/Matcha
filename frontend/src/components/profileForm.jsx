import React from "react";
import Joi from "joi";
import Form from "./common/form";
import * as userService from "../services/userService";
import moment from "moment";
import { toast } from "react-toastify";
import TagForm from "./tagForm";
import ProfileImages from "./profileImages";
import "./styles/profileForm.scss";

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

  updateToNextProfile() {
    this.setState({ data: this.mapToViewModel(this.props.user) });
  }

  updateCurrentProfile() {
    this.setState({ data: this.mapToViewModel(this.state.data) });
  }

  componentDidMount() {
    this.updateToNextProfile();
  }

  componentDidUpdate(prevProps) {
    if (this.props.user.username !== prevProps.user.username)
      this.updateToNextProfile();
  }

  mapToViewModel(user) {
    return {
      firstName: user.firstName,
      lastName: user.lastName,
      gender: user.gender || "",
      birthday:
        (user.birthday && moment(user.birthday).format("YYYY-MM-DD")) || "",
      description: user.description || "",
    };
  }

  schema = Joi.object({
    firstName: Joi.string().required().label("First Name"),
    lastName: Joi.string().required().label("Last Name"),
    gender: Joi.string().optional(),
    birthday: Joi.any().optional(),
    description: Joi.any().optional(),
  });

  doSubmit = async () => {
    await userService.updateUser(this.state.data);
    toast.success("Changes successfully saved!");
    this.props.onEditModeChange();
    this.updateCurrentProfile();
  };

  render() {
    const { editMode, isMe, user } = this.props;
    const readonly = !(editMode && isMe);
    const { firstName, lastName } = this.state.data;
    return (
      <form className="ProfileForm" onSubmit={this.handleSubmit}>
        <h1>{editMode ? "Editing profile" : firstName + " " + lastName}</h1>
        <ProfileImages
          userId={user.id}
          modalTitle={`${firstName}'s photos`}
          editMode={editMode}
        />
        {editMode
          ? this.renderInput("firstName", "First Name", readonly)
          : null}
        {editMode ? this.renderInput("lastName", "Last Name", readonly) : null}
        {this.renderDatePicker("birthday", "Birthday", readonly)}
        {readonly
          ? this.renderInput("gender", "Gender", readonly)
          : this.renderSelect("gender", "Gender", ["woman", "man"])}
        {this.renderTextArea("description", "Description", readonly)}
        <TagForm userId={user.id} editMode={editMode} />
        {readonly ? null : this.renderButton("Save")}
      </form>
    );
  }
}

export default ProfileForm;
