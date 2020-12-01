import http from "./httpService";
import { apiUrl } from "../config.json";
import auth from "./authService";

const apiEndpoint = apiUrl + "/users";

export function createGuest(to) {
  let userId = auth.getCurrentUser().id;
  return http.post(`${apiEndpoint}/guests/from/${userId}/to/${to}`, null, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}

export function getGuests() {
  let userId = auth.getCurrentUser().id;
  return http.get(`${apiEndpoint}/${userId}/guests`, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}
