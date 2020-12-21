import React, { useContext } from "react";
import UserContext from "../contexts/userContext";
import ImageFileInput from "./imageFileInput";
import RateButtons from "./rateButtons";
import "./styles/userAvatar.scss";

const UserAvatar = (props) => {
  const { user, isMe, editMode, onEditModeChange, location } = props;
  const userContext = useContext(UserContext);
  return (
    <div className="UserAvatar">
      <figure className="figure">
        <img
          src={
            userContext.userAvatar?.link
              ? userContext.userAvatar.link
              : "/default-avatar.png"
          }
          className="figure-img img-fluid rounded"
          alt="avatar"
        />
        <figcaption className="figure-caption text-center text-dark">
          Fame rating: <strong>{user.rate}</strong>
        </figcaption>
      </figure>
      {isMe ? (
        <div className="UserAvatar-Buttons">
          {editMode ? (
            <ImageFileInput
              userId={user.id}
              name="imageLoader"
              label={
                <i className="fa fa-upload" aria-hidden="true">
                  Choose a file
                </i>
              }
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
        <RateButtons />
      )}
    </div>
  );
};

export default UserAvatar;
