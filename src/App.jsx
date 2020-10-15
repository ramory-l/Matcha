import React from "react";
import { Route, Switch } from "react-router-dom";
import AuthPage from "./pages/authPage";
import "./App.css";

function App() {
  return (
    <Switch>
      <Route path="/auth" component={AuthPage} />
    </Switch>
  );
}

export default App;
