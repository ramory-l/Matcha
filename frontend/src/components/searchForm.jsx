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
      rateFrom: "",
      rateTo: "",
      ageFrom: 18,
      ageTo: 80,
      radius: "",
      tags: "",
    },
    errors: {},
  };

  schema = Joi.object({
    man: Joi.boolean().required().label("Man"),
    woman: Joi.boolean().required().label("Woman"),
    rateFrom: Joi.number().optional().empty("").label("Rate From"),
    rateTo: Joi.number().optional().empty("").label("Rate To"),
    ageFrom: Joi.number().min(18).required().label("Age From"),
    ageTo: Joi.number().min(18).max(90).required().label("Age To"),
    radius: Joi.number().optional().empty("").label("Radius"),
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
          {this.renderInput("rateFrom", "Rate From")}
          {this.renderInput("rateTo", "Rate To")}
          {this.renderInput("ageFrom", "Age From")}
          {this.renderInput("ageTo", "Age To")}
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
