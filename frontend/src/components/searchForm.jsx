import React from "react";
import { useState } from "react";
import { updateUserForm } from "../services/formService";
import CheckBox from "./common/checkBox";
import "./styles/searchForm.scss";

const SearhForm = ({ userForm, onSearchButtonClick }) => {
  const [form, setForm] = useState(userForm);

  const handleFormChange = (e) => {
    const newForm = { ...form };
    newForm[e.target.name] = e.target.checked;
    setForm(newForm);
  };

  const handleFormUpdate = async () => {
    await updateUserForm(form);
    onSearchButtonClick();
  };

  return (
    <div className="SearchForm">
      <div className="SearchForm-Gender">
        <h6>I want to find:</h6>
        <CheckBox
          name="man"
          checked={form.man}
          onChange={(e) => handleFormChange(e)}
          label="Man"
        />
        <CheckBox
          name="woman"
          checked={form.woman}
          onChange={(e) => handleFormChange(e)}
          label="Woman"
        />
      </div>
      <div className="SearchForm-Targets">
        <h6>For:</h6>
        <CheckBox
          name="friendship"
          checked={form.friendship}
          onChange={(e) => handleFormChange(e)}
          label="FriendShip"
        />
        <CheckBox
          name="love"
          checked={form.love}
          onChange={(e) => handleFormChange(e)}
          label="Love"
        />
        <CheckBox
          name="sex"
          checked={form.sex}
          onChange={(e) => handleFormChange(e)}
          label="Sex"
        />
        <CheckBox
          name="flirt"
          checked={form.flirt}
          onChange={(e) => handleFormChange(e)}
          label="Flirt"
        />
      </div>
      <div className="SearchForm-Options">
        <h6>Age:</h6>
        <input type="text" placeholder="From" size="4" /> <br /> <br />
        <input type="text" placeholder="To" size="4" /> <br /> <br />
        <button
          onClick={handleFormUpdate}
          type="button"
          className="btn btn-primary"
        >
          Search
        </button>
      </div>
    </div>
  );
};

export default SearhForm;
