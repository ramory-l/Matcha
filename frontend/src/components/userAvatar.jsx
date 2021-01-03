import React, { useContext, useEffect, useState } from "react";
import UserContext from "../contexts/userContext";
import ImageFileInput from "./imageFileInput";
import RateButtons from "./rateButtons";
import LinkButton from "./common/linkButton";
import {
  getUserBlacklist,
  getUserMatches,
  unblockUser,
} from "../services/userService";
import ReportModal from "./reportModal";
import moment from "moment";
import "./styles/userAvatar.scss";

const UserAvatar = (props) => {
  const { user, isMe, editMode, onEditModeChange } = props;
  const [rate, setRate] = useState(user.rate);
  const [isBlocked, setIsBlocked] = useState(false);
  const [isMatch, setIsMatch] = useState(false);
  const userContext = useContext(UserContext);

  useEffect(() => {
    async function getMatches() {
      const { data: matches } = await getUserMatches();
      const { data: blackList } = await getUserBlacklist();
      const isBlocked = !!blackList.filter((blocked) => blocked.id === user.id)
        .length;
      const isMatch = !!matches.filter(
        (match) => match.username === user.username
      ).length;
      if (!isMe && isBlocked) {
        setIsBlocked(isBlocked);
      } else {
        setIsMatch(isMatch);
      }
    }
    getMatches();
  }, [isMe, user]);

  const handleUserBlock = () => {
    setIsBlocked(true);
  };

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
                  ? moment(user.lastLoginDate).format("DD-MM-YYYY, h:mm:ss a")
                  : "RIP"}
              </strong>{" "}
              <br />
            </span>
          )}
          Fame rating: <strong>{rate}</strong>
        </figcaption>
      </figure>
      {isMe ? (
        <div className="UserAvatar-MyButtons">
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
          <button
            onClick={onEditModeChange}
            type="button"
            className="btn btn-info"
          >
            Edit profile
          </button>
        </div>
      ) : (
        <div className="UserAvatar-Buttons">
          <RateButtons
            user={user}
            rateUpdateFunction={setRate}
            isBlocked={isBlocked}
          />
          {isMatch ? (
            <LinkButton
              to={`/messages/${user.username}`}
              className="btn btn-info"
            >
              Send Message
            </LinkButton>
          ) : null}
          {isBlocked ? (
            <button
              onClick={() => {
                unblockUser(user.id);
                setIsBlocked(false);
              }}
              className="btn btn-success"
            >
              Unblock
            </button>
          ) : (
            <>
              <button
                data-toggle="modal"
                data-target="#reportModal"
                className="btn btn-danger mt-2"
              >
                Report/Block User
              </button>
              <ReportModal
                modalTarget="reportModal"
                modalTitle="Report User"
                userIdToReport={user.id}
                onBlock={handleUserBlock}
              />
            </>
          )}
        </div>
      )}
    </div>
  );
};

export default UserAvatar;
