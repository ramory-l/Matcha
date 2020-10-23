import { useEffect } from "react";
import authService from "../services/authService";

const Logout = () => {
  useEffect(() => {
    authService.logout();
    window.location = "/auth/login";
  });

  return null;
};

export default Logout;
