import React, { useState, useEffect } from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import UserAvatar from "./userAvatar";
import ProfileForm from "./profileForm";
import GuestsTable from "./guestsTable";
import ListGroup from "./common/listGroup";
import SettingForm from "./settingsForm";
import LikersTable from "./likersTable";
import UserContext from "../contexts/userContext";
import MatchesTable from "./matchesTable";
import { getUserImages } from "../services/imageService";
import { updateUser } from "../services/userService";
import { toast } from "react-toastify";
import "./styles/user.scss";

const User = (props) => {
  const [images, setImages] = useState([]);
  const { isMe, match, editMode, onEditModeChange, user } = props;
  const [userAvatar, setUserAvatar] = useState({});

  useEffect(() => {
    setUserAvatar(user.avatar);
  }, [user.avatar]);

  const handleUserAvatarUpdate = async (newAvatar) => {
    try {
      user.avatar = newAvatar;
      await updateUser(user);
      setUserAvatar(newAvatar);
      toast.success("Avatar has been changed!");
    } catch (ex) {
      toast.error("Error to update avatar!");
    }
  };

  const handleNewImages = async () => {
    const { data: images } = await getUserImages(user.id);
    setImages(images);
    onEditModeChange();
  };
  return (
    <UserContext.Provider
      value={{ userAvatar, handleUserAvatarUpdate, images, handleNewImages }}
    >
      <div className="row">
        <div className="col-3">
          <UserAvatar {...props} />
          {isMe ? (
            <ListGroup
              editMode={editMode}
              onEditModeChange={onEditModeChange}
              items={[
                { title: "My data", path: "/profile/me" },
                { title: "My guests", path: "/profile/me/guests" },
                { title: "My likers", path: "/profile/me/likers" },
                { title: "My matches", path: "/profile/me/matches" },
                { title: "Settings", path: "/profile/me/settings" },
              ]}
            />
          ) : null}
        </div>
        <div className="col">
          <div className="UserInfoBlock">
            <Switch>
              {isMe ? (
                <Route path={`${match.path}/guests`} component={GuestsTable} />
              ) : null}
              {isMe ? (
                <Route path={`${match.path}/likers`} component={LikersTable} />
              ) : null}
              {isMe ? (
                <Route
                  path={`${match.path}/matches`}
                  component={MatchesTable}
                />
              ) : null}
              {isMe ? (
                <Route
                  path={`${match.path}/settings`}
                  render={() => <SettingForm {...props} />}
                />
              ) : null}

              <Route
                exact
                path={`${match.path}`}
                render={() => <ProfileForm {...props} />}
              />
              <Redirect to="/not-found" />
            </Switch>
          </div>
        </div>
      </div>
    </UserContext.Provider>
  );
};

export default User;
