import React, { useEffect, useState } from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import Logout from "../components/logout";
import NavBar from "../components/navBar";
import HomePage from "../pages/homePage";
import ProfilePage from "../pages/profilePage";
import NotFound from "../components/notFound";
import auth from "../services/authService";

const BasePage = () => {
  const [user, setUser] = useState({});

  useEffect(() => {
    const user = auth.getCurrentUser();
    setUser(user);
  }, []);

  return (
    <>
      <NavBar user={user} />
      <main className="container">
        <Switch>
          <Route path="/profile" component={ProfilePage} />
          <Route path="/logout" component={Logout} />
          <Route path="/not-found" component={NotFound} />
          <Route path="/" component={HomePage} />
          <Redirect to="/not-found" />
        </Switch>
      </main>
    </>
  );
};

export default BasePage;
