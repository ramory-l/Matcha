import React, { useContext, useEffect, useState } from "react";
import UserContext from "../contexts/userContext";
import ImageFileInput from "./imageFileInput";
import RateButtons from "./rateButtons";
import LinkButton from "./common/linkButton";
import { getUserBlacklist, getUserMatches } from "../services/userService";
import ReportModal from "./reportModal";
import moment from "moment";
import "./styles/userAvatar.scss";

const UserAvatar = (props) => {
  const { user, isMe, editMode, onEditModeChange } = props;
  const [rate, setRate] = useState(user.rate);
  const [matches, setMatches] = useState([]);
  const [blackList, setBlackList] = useState([]);
  const userContext = useContext(UserContext);

  useEffect(() => {
    async function getMatches() {
      const { data: matches } = await getUserMatches();
      const { data: blackList } = await getUserBlacklist();
      setBlackList(blackList);
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
          <RateButtons user={user} rateUpdateFunction={setRate} />
          {matches.filter((match) => match.username === user.username)
            .length ? (
            <LinkButton
              to={`/messages/${user.username}`}
              className="btn btn-info"
            >
              Send Message
            </LinkButton>
          ) : null}
          {blackList.filter((blocked) => blocked.id === user.id).length ? (
            <button className="btn btn-success">Unblock</button>
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
              />
            </>
          )}
        </div>
      )}
    </div>
  );
};

export default UserAvatar;
