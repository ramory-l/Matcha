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
      this.props.onBlock();
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
      this.props.onBlock();
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
        <button disabled={this.validate()} className="btn btn-danger">
          Send Report
        </button>
        <button
          onClick={(e) => this.blockUser(e)}
          className="btn btn-danger ml-2"
          data-dismiss="modal"
        >
          Block User
        </button>
      </form>
    );
  }
}

export default ReportForm;
