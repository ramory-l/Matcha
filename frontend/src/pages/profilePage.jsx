import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import ListGroup from "../components/common/listGroup";
import Loading from "../components/common/loading";
import ProfileForm from "../components/profileForm";
import auth from "../services/authService";
import { getUser, getUserTags } from "../services/userService";

const ProfilePage = (props) => {
  const [user, setUser] = useState(null);
  let username = props.match.params.username;
  let isMe = !username ? true : false;
  const [editMode, setEditMode] = useState(false);

  const handleEditModeChange = () => {
    setEditMode((prev) => !prev);
  };

  useEffect(() => {
    async function fetchUser() {
      const username = props.match.params.username
        ? props.match.params.username
        : auth.getCurrentUser().sub;
      const { data: user } = await getUser(username);
      const { data: tags } = await getUserTags(user.id);
      user.tags = tags;
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
                src={
                  user.avatar?.url ? user.avatar?.url : "/default-avatar.png"
                }
                className="figure-img img-fluid rounded"
                alt="avatar"
              />
              <figcaption className="figure-caption text-center">
                Fame rating: <strong>{user.rate}</strong>
              </figcaption>
            </figure>
            {isMe ? (
              <button
                onClick={handleEditModeChange}
                type="button"
                className="btn btn-primary"
              >
                Edit profile
              </button>
            ) : (
              <Link to={`/messages/${user.username}`}>
                <button type="button" className="btn btn-primary">
                  Send a message
                </button>
              </Link>
            )}
          </div>
          <div className="col-6">
            <ProfileForm
              {...props}
              user={user}
              isMe={isMe}
              editMode={editMode}
              onEditModeChange={handleEditModeChange}
            />
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
