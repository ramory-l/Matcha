import React from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import ProfileLeftSide from "./profileLeftSide";
import ProfileForm from "./profileForm";
import UsersTable from "./usersTable";
import ListGroup from "./common/listGroup";

const User = (props) => {
  const { isMe, match } = props;
  return (
    <div className="row">
      <div className="col-3">
        <ProfileLeftSide {...props} />
      </div>
      <div className="col-6">
        <Switch>
          {isMe ? (
            <Route path={`${match.path}/guests`} component={UsersTable} />
          ) : null}
          <Route
            exact
            path={`${match.path}`}
            render={() => <ProfileForm {...props} />}
          />
          <Redirect to="/not-found" />
        </Switch>
      </div>
      <div className="col-2">
        <ListGroup
          items={[
            { title: "My guests", path: "me/guests" },
            { title: "My likes", path: "me/likes" },
            { title: "My matches", path: "me/matches" },
          ]}
        />
      </div>
    </div>
  );
};

export default User;
