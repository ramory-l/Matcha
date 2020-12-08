import React from "react";
import { Link } from "react-router-dom";
import ImageFileInput from "./imageFileInput";
import "./styles/profileLeftSide.scss";

const ProfileLeftSide = (props) => {
  const { user, isMe, editMode, onEditModeChange, location } = props;
  return (
    <div className="ProfileLeftSide">
      <figure className="figure">
        <img
          src={user.avatar?.url ? user.avatar?.url : "/default-avatar.png"}
          className="figure-img img-fluid rounded"
          alt="avatar"
        />
        <figcaption className="figure-caption text-center text-dark">
          Fame rating: <strong>{user.rate}</strong>
        </figcaption>
      </figure>
      {isMe ? (
        <div className="ProfileLeftSide-Buttons">
          {editMode ? (
            <ImageFileInput
              userId={user.id}
              name="imageLoader"
              label="Load image"
            />
          ) : null}
          {location.pathname === "/profile/me" ? (
            <button
              onClick={onEditModeChange}
              type="button"
              className="btn btn-info"
            >
              Edit profile
            </button>
          ) : null}
        </div>
      ) : (
        <Link to={`/messages/${user.username}`}>
          <button type="button" className="btn btn-primary">
            Send a message
          </button>
        </Link>
      )}
    </div>
  );
};

export default ProfileLeftSide;
