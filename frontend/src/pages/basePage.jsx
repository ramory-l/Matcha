import React, { useEffect, useState } from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import Logout from "../components/logout";
import NavBar from "../components/navBar";
import HomePage from "../pages/homePage";
import ProfilePage from "../pages/profilePage";
import MessagesPage from "./messagesPage";
import NotFound from "../components/notFound";
import SearchPage from "./searchPage";
import auth, { getJwt } from "../services/authService";
import { ws } from "../config.json";

const BasePage = () => {
  const [user, setUser] = useState({});
  const [webSocket, setWebSocket] = useState(null);

  useEffect(() => {
    const user = auth.getCurrentUser();
    setWebSocket(new WebSocket(`${ws}${getJwt()}`));
    setUser(user);
  }, []);

  return (
    <>
      <NavBar user={user} />
      <main className="container">
        <Switch>
          <Route path="/profile/:username" component={ProfilePage} />
          <Route path="/messages/:username?" component={MessagesPage} />
          <Route path="/search" component={SearchPage} />
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
