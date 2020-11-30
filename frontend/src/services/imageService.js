import http from "./httpService";
import auth from "./authService";
import { apiUrl } from "../config.json";

const apiEndpoint = apiUrl + "/users";

export function uploadImage(image) {
  return http.post(`${apiEndpoint}/images/`, image, {
    headers: { "x-auth-token": "T_" + auth.getJwt() },
  });
}

export function getUserImages(userId) {
  return http.get(`${apiEndpoint}/${userId}/images`, {
    headers: { "x-auth-token": "T_" + auth.getJwt() },
  });
}

export function deleteUserImage(imageId) {
  return http.delete(`${apiEndpoint}/images/${imageId}`, {
    headers: { "x-auth-token": "T_" + auth.getJwt() },
  });
}
