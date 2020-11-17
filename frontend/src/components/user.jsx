import React from "react";
import { Route, Switch } from "react-router-dom";
import ProfileLeftSide from "./profileLeftSide";
import ProfileForm from "./profileForm";
import UsersTable from "./usersTable";
import ListGroup from "./common/listGroup";

const User = (props) => {
  return (
    <div className="row">
      <div className="col-3">
        <ProfileLeftSide {...props} />
      </div>
      <div className="col-6">
        <Switch>
          <Route path="/" render={() => <ProfileForm {...props} />} />
          <Route path="/wholikedme" component={UsersTable} />
        </Switch>
      </div>
      <div className="col-2">
        <ListGroup
          items={["Who viewed my profile", "Who liked me", "My matches"]}
        />
      </div>
    </div>
  );
};

export default User;
