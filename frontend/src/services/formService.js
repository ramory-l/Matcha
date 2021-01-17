import { tagsToArray, tagsToString } from "../utils/tagsUtils";

const formKey = "form";

export function getUserForm() {
  const formString = localStorage.getItem(formKey);
  const form = formString
    ? JSON.parse(formString)
    : {
        man: false,
        woman: false,
        to_rate: "",
        rate_confirm: "",
        to_age: 18,
        age_confirm: 80,
        radius: "",
        tags: "",
      };
  form.tags = tagsToArray(form.tags);
  return form;
}

export function saveForm(form) {
  const newForm = JSON.parse(JSON.stringify(form));
  newForm.tags = tagsToString(form.tags);
  localStorage.setItem(formKey, JSON.stringify(newForm));
}
