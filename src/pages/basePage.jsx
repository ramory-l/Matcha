import React from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import NavBar from "../components/navBar";
import NotFound from "../components/notFound";
import HomePage from "../pages/homePage";
import ProfilePage from "../pages/profilePage";

const BasePage = () => {
  return (
    <>
      <NavBar />
      <main className="container">
        <Switch>
          <Route path="/profile" component={ProfilePage} />
          <Route path="/not-found" component={NotFound} />
          <Route path="/" exact component={HomePage} />
          <Redirect to="/not-found" />
        </Switch>
      </main>
    </>
  );
};

export default BasePage;
