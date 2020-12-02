import React, { useEffect, useState } from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import Logout from "../components/logout";
import NavBar from "../components/navBar";
import HomePage from "../pages/homePage";
import ProfilePage from "../pages/profilePage";
import MessagesPage from "./messagesPage";
import NotFound from "../components/notFound";
import SearchPage from "./searchPage";
import SettingsPage from "./settingsPage";
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
          <Route path="/profile/:username" component={ProfilePage} />
          <Route path="/messages/:username" component={MessagesPage} />
          <Route path="/search" component={SearchPage} />
          <Route path="/settings" component={SettingsPage} />
          <Route path="/logout" component={Logout} />
          <Route path="/not-found" component={NotFound} />
          <Route path="/" exact component={HomePage} />
          <Redirect to="/not-found" />
        </Switch>
      </main>
    </>
  );
};

export default BasePage;
