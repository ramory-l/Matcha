import React from "react";
import { Route, Switch } from "react-router-dom";
import AuthPage from "./pages/authPage";
import BasePage from "./pages/basePage";
import ProtectedRoute from "./components/common/protectedRoute";
import "./App.css";

function App() {
  return (
    <Switch>
      <Route path="/auth" component={AuthPage} />
      <ProtectedRoute path="/" component={BasePage} />
    </Switch>
  );
}

export default App;
