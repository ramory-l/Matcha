import React, { Component } from "react";
import Input from "./input";
import CheckBox from "./checkBox";
import Select from "./select";
import TextArea from "./textArea";
import DatePicker from "./datePicker";
import Joi from "joi";
import InputWithButton from "./inputWithButton";

class Form extends Component {
  state = {
    data: {},
    errors: {},
  };

  validate = () => {
    const options = { abortEarly: false };
    const { error } = this.schema.validate(this.state.data, options);
    if (!error) return null;

    const errors = {};
    for (let item of error.details) errors[item.path[0]] = item.message;
    return errors;
  };

  inputNeedConfirm = ({ name }) => {
    if (name.startsWith("to_") || name.endsWith("_confirm")) {
      let isTo = name.startsWith("to_") ? true : false;
      let pureName = isTo ? name.slice(3) : name.slice(0, -8);
      return [pureName, isTo];
    }
    return null;
  };

  validateRefs = ([name, isTo], input) => {
    const { value } = input;
    const TO = `to_${name}`;
    const CONFIRM = `${name}_confirm`;
    const obj = isTo
      ? { [TO]: value, [CONFIRM]: this.state.data[CONFIRM] }
      : { [TO]: this.state.data[TO], [CONFIRM]: value };
    const schema = Joi.object({
      [TO]: this.schema.extract(TO),
      [CONFIRM]: this.schema.extract(CONFIRM),
    });
    const { error } = schema.validate(obj);
    return error ? error.details[0].message : null;
  };

  validateProperty = ({ name, value }) => {
    const obj = { [name]: value };
    const schema = Joi.object({ [name]: this.schema.extract(name) });
    const { error } = schema.validate(obj);
    return error ? error.details[0].message : null;
  };

  handleSubmit = (e) => {
    e.preventDefault();

    const errors = this.validate();
    this.setState({ errors: errors || {} });
    if (errors) return;

    this.doSubmit();
  };

  handleChange = ({ currentTarget: input }) => {
    const errors = { ...this.state.errors };
    const inputName = this.inputNeedConfirm(input);
    const errorMessage = inputName
      ? this.validateRefs(inputName, input)
      : this.validateProperty(input);

    if (errorMessage) errors[input.name] = errorMessage;
    else {
      if (inputName) {
        const to = `to_${inputName[0]}`;
        const confirm = `${inputName[0]}_confirm`;
        delete errors[to];
        delete errors[confirm];
      } else delete errors[input.name];
    }

    const data = { ...this.state.data };
    if (input.type === "checkbox") data[input.name] = input.checked;
    else data[input.name] = input.value;

    this.setState({ data, errors });
  };

  renderButton(label, className = "btn btn-primary") {
    return (
      <button disabled={this.validate()} className={className}>
        {label}
      </button>
    );
  }

  renderCheckbox(name, label, onChange) {
    const { data, errors } = this.state;
    return (
      <CheckBox
        label={label}
        name={name}
        checked={data[name]}
        onChange={onChange || this.handleChange}
        error={errors[name]}
      />
    );
  }

  renderInput(name, label, readonly = false, type = "text") {
    const { data, errors } = this.state;
    return (
      <Input
        readonly={readonly}
        type={type}
        name={name}
        value={data[name]}
        label={label}
        onChange={this.handleChange}
        error={errors[name]}
      />
    );
  }

  renderSelect(name, label, options) {
    const { data, errors } = this.state;
    return (
      <Select
        name={name}
        label={label}
        options={options}
        value={data[name]}
        onChange={this.handleChange}
        error={errors[name]}
      />
    );
  }

  renderTextArea(name, label, readonly) {
    const { data, errors } = this.state;
    return (
      <TextArea
        value={data[name]}
        name={name}
        label={label}
        readonly={readonly}
        onChange={this.handleChange}
        error={errors[name]}
      />
    );
  }

  renderDatePicker(name, label, readonly) {
    const { data, errors } = this.state;
    return (
      <DatePicker
        name={name}
        label={label}
        value={data[name]}
        readonly={readonly}
        onChange={this.handleChange}
        error={errors[name]}
      />
    );
  }

  renderInputWithButton(name, placeholder, buttonLabel, event, type = "text") {
    const { data, errors } = this.state;
    return (
      <InputWithButton
        name={name}
        value={data[name]}
        placeholder={placeholder}
        buttonLabel={buttonLabel}
        type={type}
        onChange={this.handleChange}
        onClick={event}
        error={errors[name]}
      />
    );
  }
}

export default Form;
