import React, { useState, useEffect, useContext } from "react";
import WithLoading from "../components/common/withLoading";
import User from "../components/user";
import BaseContext from "../contexts/baseContext";
import auth, { getCurrentUser } from "../services/authService";
import { createGuest } from "../services/guestService";
import { getUser, getUserRates } from "../services/userService";

const UserWithLoading = WithLoading(User);

const ProfilePage = (props) => {
  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState(null);
  let isMe = props.match.params.username === "me" ? true : false;
  const [editMode, setEditMode] = useState(false);
  const baseContext = useContext(BaseContext);

  const handleEditModeChange = () => {
    setEditMode((prev) => !prev);
  };

  useEffect(() => {
    async function fetchUser() {
      const username =
        props.match.params.username === "me"
          ? auth.getCurrentUser().sub
          : props.match.params.username;
      const { data: user } = await getUser(username);
      if (user.username !== auth.getCurrentUser().sub) {
        await createGuest(user.id);
        const guestNotification = {
          from: getCurrentUser().id,
          to: user.id,
          message: `${getCurrentUser().sub} visited your profile!`,
          createTs: Date.now(),
          type: "notification",
        };
        baseContext.webSocket.send(JSON.stringify(guestNotification));
      }
      const { data: likesDislikes } = await getUserRates("likesDislikes", true);
      if (likesDislikes["likes"].filter((like) => like.id === user.id).length)
        user.isLiked = true;
      if (
        likesDislikes["dislikes"].filter((dislike) => dislike.id === user.id)
          .length
      )
        user.isDisliked = true;
      setUser(user);
      setIsLoading(false);
    }
    fetchUser();
  }, [props.match.params.username, baseContext.webSocket]);

  return (
    <UserWithLoading
      {...props}
      isLoading={isLoading}
      user={user}
      isMe={isMe}
      editMode={editMode}
      onEditModeChange={handleEditModeChange}
    />
  );
};

export default ProfilePage;
