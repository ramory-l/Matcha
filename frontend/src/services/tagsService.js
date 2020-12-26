import auth from "./authService";
import http from "./httpService";
import { apiUrl } from "../config.json";

const apiEndpoint = apiUrl + "/users";

export function createTag(tagName) {
  return http.post(
    `${apiEndpoint}/${auth.getCurrentUser().id}/tags/${tagName}`,
    null,
    {
      headers: { "x-auth-token": `T_${auth.getJwt()}` },
    }
  );
}

export function deleteTag(tagName) {
  return http.delete(
    `${apiEndpoint}/${auth.getCurrentUser().id}/tags/${tagName}`,
    {
      headers: { "x-auth-token": `T_${auth.getJwt()}` },
    }
  );
}

export function getUsersWithTag(tagName) {
  return http.get(`${apiEndpoint}/tags/${tagName}`, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}
