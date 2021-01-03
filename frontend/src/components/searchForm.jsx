import React from "react";
import { updateUserForm } from "../services/formService";
import Form from "./common/form";
import Joi from "joi";
import "./styles/searchForm.scss";

class SearchForm extends Form {
  state = {
    data: {
      man: false,
      woman: false,
      rateFrom: 0,
      rateTo: 0,
      ageFrom: 0,
      ageTo: 0,
      lat: 0,
      lon: 0,
    },
    errors: {},
  };

  schema = Joi.object({
    man: Joi.boolean().required().label("Man"),
    woman: Joi.boolean().required().label("Woman"),
    rateFrom: Joi.number().required().label("Rate From"),
    rateTo: Joi.number().required().label("Rate To"),
    ageFrom: Joi.number().required().label("Age From"),
    ageTo: Joi.number().required().label("Age To"),
    lat: Joi.number().required().label("Latitude"),
    lon: Joi.number().required().label("Longitude"),
  });

  doSubmit = () => {};
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
          {this.renderInput("lat", "Latitude")}
          {this.renderInput("lon", "Longitude")}
        </div>
        <div className="SearchForm-Tags">
          <div className="Tags-Title">Tags:</div>
          {this.renderInput("lat", "Latitude")}
          {this.renderInput("lon", "Longitude")}
        </div>
      </form>
    );
  }
}

export default SearchForm;

// const SearhForm = ({ userForm, onSearchButtonClick }) => {
//   const [form, setForm] = useState(userForm);

//   const handleFormChange = (e) => {
//     const { target } = e;
//     const newForm = { ...form };
//     if (target.type === "checkbox") newForm[target.name] = target.checked;
//     else newForm[target.name] = target.value;
//     setForm(newForm);
//   };

//   const handleFormUpdate = async () => {
//     await updateUserForm(form);
//     onSearchButtonClick();
//   };
//   return (
//     <div className="SearchForm">
//       <div className="SearchForm-Gender">
//         <h6>I want to find:</h6>
//         <CheckBox
//           name="man"
//           checked={form.man}
//           onChange={(e) => handleFormChange(e)}
//           label="Man"
//         />
//         <CheckBox
//           name="woman"
//           checked={form.woman}
//           onChange={(e) => handleFormChange(e)}
//           label="Woman"
//         />
//       </div>
//       <div className="SearchForm-Targets">
//         <h6>For:</h6>
//         <CheckBox
//           name="friendship"
//           checked={form.friendship}
//           onChange={(e) => handleFormChange(e)}
//           label="FriendShip"
//         />
//         <CheckBox
//           name="love"
//           checked={form.love}
//           onChange={(e) => handleFormChange(e)}
//           label="Love"
//         />
//         <CheckBox
//           name="sex"
//           checked={form.sex}
//           onChange={(e) => handleFormChange(e)}
//           label="Sex"
//         />
//         <CheckBox
//           name="flirt"
//           checked={form.flirt}
//           onChange={(e) => handleFormChange(e)}
//           label="Flirt"
//         />
//       </div>
//       <div className="SearchForm-Options">
//         <h6>Age:</h6>
//         <input
//           name="ageFrom"
//           value={form.ageFrom}
//           onChange={handleFormChange}
//           type="text"
//           placeholder="From"
//           size="4"
//         />{" "}
//         <br /> <br />
//         <input
//           name="ageTo"
//           value={form.ageTo}
//           onChange={handleFormChange}
//           type="text"
//           placeholder="To"
//           size="4"
//         />{" "}
//         <br /> <br />
//         <button
//           onClick={handleFormUpdate}
//           type="button"
//           className="btn btn-primary"
//         >
//           Search
//         </button>
//       </div>
//     </div>
//   );
// };

// export default SearhForm;
