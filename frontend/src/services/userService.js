import http from "./httpService";
import { apiUrl } from "../config.json";
import auth from "./authService";

const apiEndpoint = apiUrl + "/user/";

export function register(user) {
  return http.post(apiEndpoint, {
    username: user.username,
    email: user.email,
    password: user.password,
  });
}

export function getUsers() {
  return http.get(apiEndpoint + "all", {
    headers: { "x-auth-token": "T_" + auth.getJwt() },
  });
}

export function getUser(username) {
  return http.get(apiEndpoint + "username/" + username);
}
