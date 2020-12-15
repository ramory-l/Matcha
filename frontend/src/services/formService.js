import http from "./httpService";
import { apiUrl } from "../config.json";
import auth from "./authService";

const apiEndpoint = apiUrl + "/forms";

export function getUserForm() {
  let userId = auth.getCurrentUser().id;
  return http.get(`${apiEndpoint}/${userId}`, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}

export function updateUserForm(userForm) {
  const tempForm = { ...userForm };
  delete tempForm.likesDislikes;
  return http.put(`${apiEndpoint}/`, tempForm, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}
