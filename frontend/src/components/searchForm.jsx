import React from "react";
import Form from "./common/form";
import Joi from "joi";
import TagsInput from "./tagsInput";
import "./styles/searchForm.scss";

class SearchForm extends Form {
  state = {
    data: {
      man: false,
      woman: false,
      to_rate: "",
      rate_confirm: "",
      to_age: 18,
      age_confirm: 80,
      radius: "",
      tags: "",
    },
    errors: {},
  };

  schema = Joi.object({
    man: Joi.boolean().required().label("Man"),
    woman: Joi.boolean().required().label("Woman"),
    to_rate: Joi.number()
      .less(Joi.ref("rate_confirm"))
      .optional()
      .empty("")
      .label("Rate From"),
    rate_confirm: Joi.number().optional().empty("").label("Rate To"),
    to_age: Joi.number()
      .less(Joi.ref("age_confirm"))
      .min(18)
      .required()
      .label("Age From"),
    age_confirm: Joi.number().required().max(90).label("Age To"),
    radius: Joi.number().optional().min(0).empty("").label("Radius"),
    tags: Joi.string().optional().label("Tags"),
  });

  handleTags = (tags) => {
    this.setState({ data: { ...this.state.data, tags } });
  };

  doSubmit = () => {
    this.props.onSearchButtonClick(this.state.data);
  };
  render() {
    return (
      <form className="SearchForm" onSubmit={this.handleSubmit}>
        <div className="SearchForm-Gender">
          <div className="Gender-Title">Gender:</div>
          {this.renderCheckbox("man", "Man")}
          {this.renderCheckbox("woman", "Woman")}
        </div>
        <div className="SearchForm-Options">
          <div className="Options-Title">Options:</div>
          {this.renderInput("to_rate", "Rate From")}
          {this.renderInput("rate_confirm", "Rate To")}
          {this.renderInput("to_age", "Age From")}
          {this.renderInput("age_confirm", "Age To")}
        </div>
        <div className="SearchForm-Location">
          <div className="Location-Title">Location:</div>
          {this.renderInput("radius", "Radius")}
        </div>
        <div className="SearchForm-Tags">
          <div className="Tags-Title">Tags:</div>
          <TagsInput isSearchForm={true} onHandleTags={this.handleTags} />
          {this.renderButton("Search")}
        </div>
      </form>
    );
  }
}

export default SearchForm;
