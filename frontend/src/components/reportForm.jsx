import React from "react";
import Form from "./common/form";
import Joi from "joi";
import { blockUser, reportUser } from "../services/userService";
import { toast } from "react-toastify";

class ReportForm extends Form {
  state = {
    data: {
      reportText: "",
    },
    errors: {},
  };

  schema = Joi.object({
    reportText: Joi.string().required().label("Report Text"),
  });

  doSubmit = async () => {
    try {
      await reportUser(this.props.userIdToReport, this.state.data.reportText);
      toast.success("You successfully reported this user!");
    } catch (ex) {
      toast.error("Error to send a report!");
      console.log(ex);
    }
  };

  blockUser = async (e) => {
    e.preventDefault();
    try {
      await blockUser(this.props.userIdToReport);
      toast.success("You successfully blocked this user!");
    } catch (ex) {
      toast.error("Error to block user!");
      console.log(ex);
    }
  };

  render() {
    return (
      <form className="reportForm" onSubmit={this.handleSubmit}>
        {this.renderTextArea(
          "reportText",
          "Please describe your report below:"
        )}
        {this.renderButton("Send Report", "btn btn-danger")}
        <button
          onClick={(e) => this.blockUser(e)}
          className="btn btn-danger ml-2"
        >
          Block User
        </button>
      </form>
    );
  }
}

export default ReportForm;
