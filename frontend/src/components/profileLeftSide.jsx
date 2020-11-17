import React from "react";
import { Link } from "react-router-dom";

const ProfileLeftSide = ({ user, isMe, onEditModeChange }) => {
  return (
    <>
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
        <button
          onClick={onEditModeChange}
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
    </>
  );
};

export default ProfileLeftSide;
