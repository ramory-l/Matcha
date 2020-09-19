import React from "react";
import "./App.css";
import Nav from "./components/Nav";
import ProfilePage from "./pages/Profile";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
  RouteProps,
} from "react-router-dom";
import HomePage from "./pages/Home";
import LoginPage from "./pages/Login";

export const fakeAuth = {
  isAuthenticated: false,
  authenticate(cb: () => void) {
    this.isAuthenticated = true;
    setTimeout(cb, 100);
  },
  signout(cb: () => void) {
    this.isAuthenticated = false;
    setTimeout(cb, 100);
  },
};

const PrivateRoute = ({ children, ...rest }: RouteProps) => {
  return (
    <Route
      {...rest}
      render={({ location }) =>
        fakeAuth.isAuthenticated ? (
          children
        ) : (
          <Redirect
            to={{
              pathname: "/login",
              state: { from: location },
            }}
          />
        )
      }
    />
  );
};

const App = () => {
  return (
    <Router>
      <div className="App">
        <header className="App-header">
          <Nav isAuthenticated={fakeAuth.isAuthenticated} />
        </header>
        <Switch>
          <PrivateRoute path="/" exact>
            <HomePage />
          </PrivateRoute>
          <PrivateRoute path="/profile">
            <ProfilePage />
          </PrivateRoute>
          <Route path="/login" component={LoginPage} />
        </Switch>
      </div>
    </Router>
  );
};

export default App;
