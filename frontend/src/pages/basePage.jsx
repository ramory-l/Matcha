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
import { toast } from "react-toastify";
import UserNotification from "../components/userNotification";
import BaseContext from "../contexts/baseContext";
import { getUserMatches } from "../services/userService";

const BasePage = (props) => {
  const [user, setUser] = useState({});
  const [webSocket, setWebSocket] = useState(null);
  const [matches, setMatches] = useState([]);
  const [avaliableChats, setAvaliableChats] = useState([]);

  useEffect(() => {
    async function getMatches() {
      const { data: matches } = await getUserMatches();
      setMatches(matches);
      const avaliableChats = matches.map(
        (match) => `/messages/${match.username}`
      );
      avaliableChats.push("/messages");
      setAvaliableChats(avaliableChats);
    }
    getMatches();
    const user = auth.getCurrentUser();
    setWebSocket(new WebSocket(`${ws}T_${getJwt()}`));
    setUser(user);
  }, []);

  useEffect(() => {
    if (webSocket) {
      webSocket.onopen = () => {
        console.log("connected");
      };

      webSocket.onclose = () => {
        console.log("closed");
      };

      webSocket.onmessage = (message) => {
        const data = JSON.parse(message.data);

        toast(
          <UserNotification
            dataType={data.type}
            avatarLink={data.avatar}
            username={data.username}
            message={data.message}
          />
        );
      };

      return () => {
        webSocket.close();
        console.log("closed");
      };
    }
  }, [webSocket]);

  return (
    <>
      <BaseContext.Provider value={{ webSocket, matches, avaliableChats }}>
        <NavBar user={user} />
        <main className="container">
          <Switch>
            <Route path="/profile/:username" component={ProfilePage} />
            {avaliableChats &&
            avaliableChats.includes(props.location.pathname) ? (
              <Route path="/messages/:username?" component={MessagesPage} />
            ) : null}
            <Route path="/search" component={SearchPage} />
            <Route path="/logout" component={Logout} />
            <Route path="/not-found" component={NotFound} />
            <Route path="/" exact component={HomePage} />
            <Redirect to="/not-found" />
          </Switch>
        </main>
      </BaseContext.Provider>
    </>
  );
};

export default BasePage;
