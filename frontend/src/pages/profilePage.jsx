import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import ListGroup from "../components/common/listGroup";
import Loading from "../components/common/loading";
import ProfileForm from "../components/profileForm";
import auth from "../services/authService";
import { getUser } from "../services/userService";

const ProfilePage = (props) => {
  const [user, setUser] = useState(null);
  let username = props.match.params.username;
  let isMe = username === "me" ? true : false;

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
            {isMe ? null : (
              <Link
                to={{
                  pathname: `/messages/${user.username}`,
                  state: {
                    recipient: user,
                  },
                }}
              >
                <button type="button" className="btn btn-primary">
                  Send a message
                </button>
              </Link>
            )}
          </div>
          <div className="col-6">
            <ProfileForm {...props} user={user} isMe={isMe} />
          </div>
          <div className="col-2">
            <ListGroup
              items={["Who viewed my profile", "Who liked me", "My matches"]}
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
