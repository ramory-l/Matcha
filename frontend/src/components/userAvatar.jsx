import React, { useContext, useEffect, useState } from "react";
import UserContext from "../contexts/userContext";
import ImageFileInput from "./imageFileInput";
import RateButtons from "./rateButtons";
import LinkButton from "./common/linkButton";
import { getUserMatches } from "../services/userService";
import ReportModal from "./reportModal";
import moment from "moment";
import "./styles/userAvatar.scss";

const UserAvatar = (props) => {
  const { user, isMe, editMode, onEditModeChange, location } = props;
  const [rate, setRate] = useState(user.rate);
  const [matches, setMatches] = useState([]);
  const userContext = useContext(UserContext);

  console.log(user);

  useEffect(() => {
    async function getMatches() {
      const { data: matches } = await getUserMatches();
      setMatches(matches);
    }
    getMatches();
  }, []);

  return (
    <div className="UserAvatar">
      <figure className="figure">
        <div
          className={`UserAvatar-StatusIndicator ${
            user.isOnline ? "online" : "offline"
          }`}
        ></div>
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
          {user.isOnline ? null : (
            <span>
              Last Seen:{" "}
              <strong>
                {user.lastLoginDate
                  ? moment(user.lastLoginDate).format("YYYY-MM-DD")
                  : "Здох"}
              </strong>{" "}
              <br />
            </span>
          )}
          Fame rating: <strong>{rate}</strong>
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
      ) : matches.filter((match) => match.username === user.username).length ? (
        <LinkButton to={`/messages/${user.username}`} className="btn btn-info">
          Send Message
        </LinkButton>
      ) : (
        <>
          <RateButtons user={user} rateUpdateFunction={setRate} />
          <button
            data-toggle="modal"
            data-target="#reportModal"
            className="btn btn-danger"
          >
            Report/Block User
          </button>
          <ReportModal
            modalTarget="reportModal"
            modalTitle="Report User"
            userIdToReport={user.id}
          />
        </>
      )}
    </div>
  );
};

export default UserAvatar;
