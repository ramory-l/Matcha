import React from "react";
import { Link } from "react-router-dom";
import FileInput from "./common/fileInput";

const ProfileLeftSide = ({ user, isMe, editMode, onEditModeChange }) => {
  return (
    <div className="ProfileLeftSide">
      <figure className="figure">
        <img
          src={user.avatar?.url ? user.avatar?.url : "/default-avatar.png"}
          className="figure-img img-fluid rounded"
          alt="avatar"
        />
        <figcaption className="figure-caption text-center">
          Fame rating: <strong>{user.rate}</strong>
        </figcaption>
      </figure>
      {isMe ? (
        <div className="ProfileLeftSide-Buttons">
          {editMode ? (
            <FileInput
              userId={user.id}
              name="imageLoader"
              label="Load images"
            />
          ) : null}
          <button
            onClick={onEditModeChange}
            type="button"
            className="btn btn-primary"
          >
            Edit profile
          </button>
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
