import React from "react";
import Joi from "joi";
import Form from "./common/form";
import "./styles/settingsForm.scss";
import { resetPassword, updateUser } from "../services/userService";
import { toast } from "react-toastify";
import { getJwtFromEndpoint } from "../services/authService";

class SettingForm extends Form {
  state = {
    data: {
      email: "",
      to_new_password: "",
      new_password_confirm: "",
      current_password: "",
    },
    errors: {},
  };

  componentDidMount() {
    this.setState({ data: this.mapToViewModel(this.props.user) });
  }

  mapToViewModel(user) {
    return {
      email: user.email,
      to_new_password: "",
      new_password_confirm: "",
      current_password: "",
    };
  }

  schema = Joi.object({
    email: Joi.string()
      .email({ tlds: false })
      .required()
      .label("Email address"),
    to_new_password: Joi.string()
      .optional()
      .allow(null, "")
      .label("New Password"),
    new_password_confirm: Joi.any()
      .equal(Joi.ref("to_new_password"))
      .optional()
      .label("New Password Confirm")
      .options({
        messages: {
          "any.only": '"New Password" and {{#label}} does not match',
        },
      }),
    current_password: Joi.string().required().label("Current Password"),
  });

  doSubmit = async () => {
    try {
      const user = { ...this.state.data };
      await getJwtFromEndpoint(this.props.user.username, user.current_password);
      delete user.current_password;
      delete user.new_password_confirm;
      if (user.to_new_password) {
        await resetPassword(user);
        toast.info("Check your email to change your password");
      }
      delete user.to_new_password;
      if (user.email !== this.props.user.email) {
        user.id = this.props.user.id;
        await updateUser(user, true);
        toast.info("Your email is updated");
      }
    } catch (ex) {
      if (ex.response) {
        const errors = { ...this.state.errors };
        errors.current_password = ex.response.data;
        this.setState({ errors });
      }
    }
  };

  render() {
    return (
      <form className="SettingsForm" onSubmit={this.handleSubmit}>
        <h1>Settings</h1>
        {this.renderInput("email", "Email address")}
        {this.renderInput("to_new_password", "New Password", false, "password")}
        {this.renderInput(
          "new_password_confirm",
          "New Password Confirm",
          false,
          "password"
        )}
        {this.renderInput(
          "current_password",
          "Current Password",
          false,
          "password"
        )}
        {this.renderButton("Change Settings")}
      </form>
    );
  }
}

export default SettingForm;
