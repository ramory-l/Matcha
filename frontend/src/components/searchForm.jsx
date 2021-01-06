import React from "react";
import Form from "./common/form";
import Joi from "joi";
import TagsInput from "./tagsInput";
import { getUserForm, saveForm } from "../services/formService";
import SearchContext from "../contexts/searchContext";
import "./styles/searchForm.scss";

class SearchForm extends Form {
  static contextType = SearchContext;

  state = {
    data: {
      man: false,
      woman: false,
      to_rate: "",
      rate_confirm: "",
      to_age: 18,
      age_confirm: 80,
      radius: "",
      tags: [],
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
      .optional()
      .label("Age From"),
    age_confirm: Joi.number().optional().max(90).label("Age To"),
    radius: Joi.number().optional().min(0).empty("").label("Radius"),
    tags: Joi.array().optional().label("Tags"),
  });

  handleTagsForSearchForm = (tags) => {
    this.setState({
      data: {
        ...this.state.data,
        tags,
      },
    });
  };

  mapToViewModel(form) {
    return {
      man: form.man,
      woman: form.woman,
      to_rate: form.to_rate,
      rate_confirm: form.rate_confirm,
      to_age: form.to_age,
      age_confirm: form.age_confirm,
      radius: form.radius,
      tags: form.tags,
    };
  }

  componentDidUpdate() {
    if (Array.isArray(this.state.data.tags)) {
      saveForm(this.state.data);
    }
  }

  componentDidMount() {
    this.setState({ data: this.mapToViewModel(getUserForm()) });
  }

  doSubmit = () => {
    this.props.onSearchButtonClick(this.state.data);
  };

  handleSort = (sort) => {
    sort.order = sort.order === "asc" ? "desc" : "asc";
    this.context.setSortBy(sort);
  };

  render() {
    return (
      <SearchContext.Consumer>
        {(searchContext) => (
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
              <TagsInput
                isSearchForm={true}
                onTagsForSearchForm={this.handleTagsForSearchForm}
              />
              {this.renderButton("Search", "btn btn-primary mt-2")}
              <button
                onClick={() =>
                  this.handleSort({
                    path: "distance",
                    order: this.context.sortBy.order,
                  })
                }
                className="btn btn-dark mx-2 mt-2"
              >
                Sort by distance{" "}
                {this.context.sortBy.order === "asc" ? (
                  <i class="fa fa-sort-asc" aria-hidden="true"></i>
                ) : (
                  <i class="fa fa-sort-desc" aria-hidden="true"></i>
                )}
              </button>
              <button
                onClick={() =>
                  this.handleSort({
                    path: "rate",
                    order: this.context.sortBy.order,
                  })
                }
                className="btn btn-secondary mr-2 mt-2"
              >
                Sort by rate{" "}
                {this.context.sortBy.order === "asc" ? (
                  <i class="fa fa-sort-asc" aria-hidden="true"></i>
                ) : (
                  <i class="fa fa-sort-desc" aria-hidden="true"></i>
                )}
              </button>
              <button
                onClick={() =>
                  this.handleSort({
                    path: "age",
                    order: this.context.sortBy.order,
                  })
                }
                className="btn btn-success mt-2"
              >
                Sort by age{" "}
                {this.context.sortBy.order === "asc" ? (
                  <i class="fa fa-sort-asc" aria-hidden="true"></i>
                ) : (
                  <i class="fa fa-sort-desc" aria-hidden="true"></i>
                )}
              </button>
            </div>
          </form>
        )}
      </SearchContext.Consumer>
    );
  }
}

export default SearchForm;
