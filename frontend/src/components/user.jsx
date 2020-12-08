import React, { useState } from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import ProfileLeftSide from "./profileLeftSide";
import ProfileForm from "./profileForm";
import UsersTable from "./usersTable";
import ListGroup from "./common/listGroup";
import SettingForm from "./settingsForm";
import UserContext from "../contexts/userContext";
import { getUserImages } from "../services/imageService";

const User = (props) => {
  const [images, setImages] = useState([]);
  const { isMe, match, editMode, onEditModeChange, user } = props;

  const handleNewImages = async () => {
    const { data: images } = await getUserImages(user.id);
    setImages(images);
    onEditModeChange();
  };

  return (
    <UserContext.Provider value={{ images, handleNewImages }}>
      <div className="row">
        <div className="col-3">
          <ProfileLeftSide {...props} />
        </div>
        <div className="col-6">
          <Switch>
            {isMe ? (
              <Route path={`${match.path}/guests`} component={UsersTable} />
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
        {isMe ? (
          <div className="col-2">
            <ListGroup
              editMode={editMode}
              onEditModeChange={onEditModeChange}
              items={[
                { title: "My data", path: "/profile/me" },
                { title: "My guests", path: "/profile/me/guests" },
                { title: "My likes", path: "/profile/me/likes" },
                { title: "My matches", path: "/profile/me/matches" },
                { title: "Settings", path: "/profile/me/settings" },
              ]}
            />
          </div>
        ) : null}
      </div>
    </UserContext.Provider>
  );
};

export default User;
