import http from "./httpService";
import { apiUrl } from "../config.json";
import auth from "./authService";
import moment from "moment";

const apiEndpoint = apiUrl + "/users";

export function register(user) {
  return http.post(apiEndpoint, {
    username: user.username,
    firstName: user.firstName,
    lastName: user.lastName,
    email: user.email,
    password: user.to_password,
  });
}

export function resetPassword(user) {
  return http.put(`${apiEndpoint}/password/reset`, {
    email: user.email,
    newPass: user.to_new_password,
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

export function getUserMatches() {
  let userId = auth.getCurrentUser().id;
  return http.get(`${apiEndpoint}/matcha/${userId}`, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}

export function getMessagesWithUser(secondUserId, limit, offset) {
  let firstUserId = auth.getCurrentUser().id;
  return http.get(
    `${apiEndpoint}/messages/limit/${limit}/offset/${offset}/first/${firstUserId}/second/${secondUserId}`,
    {
      headers: { "x-auth-token": `T_${auth.getJwt()}` },
    }
  );
}

export function updateUser(user, email = false) {
  const tempUser = { ...user };
  delete tempUser.username;
  if (!email) delete tempUser.email;
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

export function reportUser(userToId, reportText) {
  const userFromId = auth.getCurrentUser().id;
  return http.post(
    `${apiEndpoint}/report/from/${userFromId}/to/${userToId}`,
    reportText,
    {
      headers: { "x-auth-token": `T_${auth.getJwt()}` },
    }
  );
}

export function blockUser(userToId) {
  const userFromId = auth.getCurrentUser().id;
  return http.post(
    `${apiEndpoint}/blacklist/from/${userFromId}/to/${userToId}`,
    null,
    {
      headers: { "x-auth-token": `T_${auth.getJwt()}` },
    }
  );
}

export function unblockUser(userToId) {
  const userFromId = auth.getCurrentUser().id;
  return http.delete(
    `${apiEndpoint}/blacklist/from/${userFromId}/to/${userToId}`,
    {
      headers: { "x-auth-token": `T_${auth.getJwt()}` },
    }
  );
}

export function getUserBlacklist() {
  const userId = auth.getCurrentUser().id;
  return http.get(`${apiEndpoint}/blacklist/${userId}`, {
    headers: { "x-auth-token": `T_${auth.getJwt()}` },
  });
}

export function searchForUsers(params) {
  const {
    man,
    woman,
    to_age: ageFrom,
    age_confirm: ageTo,
    to_rate: rateFrom,
    rate_confirm: rateTo,
    radius,
    tags,
  } = params;
  const userId = auth.getCurrentUser().id;
  return http.get(
    `${apiEndpoint}/search/${userId}?man=${man}&woman=${woman}&agefrom=${ageFrom}&ageto=${ageTo}${
      rateFrom ? `&ratefrom=${rateFrom}` : ""
    }${rateTo ? `&rateto=${rateTo}` : ""}${
      radius ? `&radius=${radius}` : `&radius=${0}`
    }${tags ? `&tags=${tags}` : ""}`,
    {
      headers: { "x-auth-token": `T_${auth.getJwt()}` },
    }
  );
}
