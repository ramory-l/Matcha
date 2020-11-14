import http from "./httpService";
import { apiUrl } from "../config.json";
import auth from "./authService";
import moment from "moment";

const apiEndpoint = apiUrl + "/users";

export function register(user) {
  return http.post(apiEndpoint, {
    username: user.username,
    email: user.email,
    password: user.password,
  });
}

export function getUsers() {
  return http.get(`${apiEndpoint}/`, {
    headers: { "x-auth-token": "T_" + auth.getJwt() },
  });
}

export function getUser(username) {
  return http.get(`${apiEndpoint}/username/${username}`, {
    headers: { "x-auth-token": "T_" + auth.getJwt() },
  });
}

export function rateUser(to, action) {
  let from = auth.getCurrentUser().id;
  return http.post(`${apiEndpoint}/${action}s/from/${from}/to/${to}`, null, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}

export function unrateUser(to, action) {
  let from = auth.getCurrentUser().id;
  return http.delete(`${apiEndpoint}/${action}s/from/${from}/to/${to}`, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}

export function getUserRates(type, outgoing) {
  let userId = auth.getCurrentUser().id;
  return http.get(`${apiEndpoint}/${userId}/${type}?outgoing=${outgoing}`, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}

export function updateUser(user) {
  const tempUser = { ...user };
  tempUser.username = auth.getCurrentUser().sub;
  tempUser.birthday = moment(user.birthday).valueOf();
  return http.put(`${apiEndpoint}/`, tempUser, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}

export function getUserTags(userId) {
  return http.get(`${apiEndpoint}/${userId}/tags`, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}
