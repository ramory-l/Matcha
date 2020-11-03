import React from "react";
import { Route, Switch } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import AuthPage from "./pages/authPage";
import BasePage from "./pages/basePage";
import ProtectedRoute from "./components/common/protectedRoute";
import auth from "./services/authService";
import "react-toastify/dist/ReactToastify.css";
import "./App.css";

function App() {
  let socket = new WebSocket(
    `ws://localhost:8080/socket?x-auth-token=T_${auth.getJwt()}`
  );

  console.log(socket);

  socket.onopen = function (e) {
    console.log("Socket ready");
    socket.send("HEEELLO");
  };

  socket.onmessage = function (e) {
    let message = e.data;

    console.log(message);
  };

  return (
    <>
      <ToastContainer />
      <Switch>
        <Route path="/auth" component={AuthPage} />
        <ProtectedRoute path="/" component={BasePage} />
      </Switch>
    </>
  );
}

export default App;
