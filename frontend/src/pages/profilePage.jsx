import React, { useState, useEffect } from "react";
import ListGroup from "../components/common/listGroup";
import Loading from "../components/common/loading";
import ProfileForm from "../components/profileForm";
import auth from "../services/authService";
import { getUser } from "../services/userService";

const ProfilePage = (props) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    async function fetchUser() {
      let username = props.match.params.username;
      if (username === "me") username = auth.getCurrentUser().sub;
      const { data: user } = await getUser(username);
      setUser(user);
    }
    fetchUser();
  }, [props]);

  return (
    <>
      {user ? (
        <div className="row">
          <div className="col-3">
            <figure className="figure">
              <img
                src="/default-avatar.png"
                className="figure-img img-fluid rounded"
                alt="avatar"
              />
              <figcaption className="figure-caption text-center">
                Fame rating: <strong>{user.rate}</strong>
              </figcaption>
            </figure>
          </div>
          <div className="col-6">
            <ProfileForm {...props} user={user} />
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
      ) : (
        <Loading />
      )}
    </>
  );
};

export default ProfilePage;
