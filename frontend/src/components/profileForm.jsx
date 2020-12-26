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
      latitude: 0,
      longitude: 0,
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
      latitude: +user.latitude,
      longitude: +user.longitude,
    };
  }

  schema = Joi.object({
    firstName: Joi.string().required().label("First Name"),
    lastName: Joi.string().required().label("Last Name"),
    gender: Joi.string().optional(),
    birthday: Joi.any().optional(),
    description: Joi.any().optional(),
    latitude: Joi.number().required().label("Latitude"),
    longitude: Joi.number().required().label("Longitude"),
  });

  doSubmit = async () => {
    await userService.updateUser(this.mapToViewModel(this.state.data));
    toast.success("Changes successfully saved!");
    this.props.onEditModeChange();
    this.updateCurrentProfile();
  };

  handleGetCoordsButton = (e) => {
    e.preventDefault();
    navigator.geolocation.getCurrentPosition((position) => {
      const latitude = position.coords.latitude;
      const longitude = position.coords.longitude;
      const user = { ...this.state.data };
      user.latitude = latitude;
      user.longitude = longitude;
      this.setState({ data: user });
    });
  };

  render() {
    const { editMode, isMe, user } = this.props;
    const readonly = !(editMode && isMe);
    const { firstName, lastName } = this.state.data;
    return (
      <form className="ProfileForm" onSubmit={this.handleSubmit}>
        <h2>{editMode ? "Editing profile" : firstName + " " + lastName}</h2>
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
        {this.renderInput("latitude", "Latitude", readonly)}
        {this.renderInput("longitude", "Longitude", readonly)}
        <TagForm userId={user.id} editMode={editMode} />
        {readonly ? null : this.renderButton("Save")}
        {readonly ? null : (
          <button
            onClick={(e) => this.handleGetCoordsButton(e)}
            className="btn btn-warning"
          >
            Get My Coordinates
          </button>
        )}
      </form>
    );
  }
}

export default ProfileForm;
