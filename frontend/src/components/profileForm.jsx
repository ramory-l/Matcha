import React from "react";
import Joi from "joi";
import Form from "./common/form";
import * as userService from "../services/userService";
import moment from "moment";
import { toast } from "react-toastify";
import ProfileImages from "./profileImages";
import jquery from "jquery";
import httpService from "../services/httpService";
import "./styles/profileForm.scss";
import TagsInput from "./tagsInput";

class ProfileForm extends Form {
  state = {
    data: {
      id: "",
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
      id: user.id,
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
    id: Joi.number().required(),
    firstName: Joi.string().max(250).required().label("First Name"),
    lastName: Joi.string().max(250).required().label("Last Name"),
    gender: Joi.string().required(),
    birthday: Joi.date()
      .iso()
      .min("1920-01-01")
      .max("2002-12-31")
      .required()
      .label("Birthday"),
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
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;
        const user = { ...this.state.data };
        user.latitude = latitude;
        user.longitude = longitude;
        this.setState({ data: user });
      },
      (error) => {
        jquery.getJSON("https://api.ipify.org?format=json", (data) => {
          httpService.get(`http://ip-api.com/json/${data.ip}`).then((res) => {
            const user = { ...this.state.data };
            user.latitude = res.data.lat;
            user.longitude = res.data.lon;
            this.setState({ data: user });
          });
        });
      }
    );
  };

  render() {
    const { editMode, isMe, user } = this.props;
    const readonly = !(editMode && isMe);
    const { firstName, lastName } = this.state.data;
    return (
      <form className="ProfileForm" onSubmit={this.handleSubmit}>
        <h2 className="ProfileForm-FullName">
          {editMode ? "Editing profile" : firstName + " " + lastName}
        </h2>
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
        <TagsInput userId={user.id} editMode={editMode} />
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
