const formKey = "form";

export function getUserForm() {
  try {
    const form = localStorage.getItem(formKey);
    return JSON.parse(form);
  } catch (ex) {
    return null;
  }
}

export function saveForm(form) {
  localStorage.setItem(formKey, JSON.stringify(form));
}
