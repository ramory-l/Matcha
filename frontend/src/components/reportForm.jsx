import React from "react";
import Form from "./common/form";
import Joi from "joi";
import { blockUser, reportUser } from "../services/userService";
import { toast } from "react-toastify";
import jQuery from "jquery";

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
      if (ex && ex.response) {
        toast.error(ex.response.data);
      }
    } finally {
      let $reportModal = jQuery("#reportModal");
      $reportModal.modal("hide");
      this.props.onBlock();
    }
  };

  blockUser = async (e) => {
    e.preventDefault();
    try {
      await blockUser(this.props.userIdToReport);
      this.props.onBlock();
      toast.success("You successfully blocked this user!");
    } catch (ex) {
      if (ex && ex.response) toast.error(ex.response.data);
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
