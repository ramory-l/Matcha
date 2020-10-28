import React from "react";
import ListGroup from "../components/common/listGroup";
import ProfileForm from "../components/profileForm";

const ProfilePage = (props) => {
  return (
    <div className="row">
      <div className="col-3">
        <figure className="figure">
          <img
            src="/default-avatar.png"
            className="figure-img img-fluid rounded"
            alt="avatar"
          />
          <figcaption className="figure-caption text-center">
            Here is your <strong>fame rating</strong>, for example...
          </figcaption>
        </figure>
      </div>
      <div className="col-6">
        <ProfileForm {...props} />
      </div>
      <div className="col-2">
        <ListGroup
          items={[
            { _id: "1", name: "Who viewed my profile" },
            { _id: "2", name: "Who liked me" },
            { _id: "3", name: "My matches" },
          ]}
        />
      </div>
    </div>
  );
};

export default ProfilePage;
