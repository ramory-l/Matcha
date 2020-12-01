import React, { useState, useEffect } from "react";
import WithLoading from "../components/common/withLoading";
import User from "../components/user";
import auth from "../services/authService";
import { createGuest } from "../services/guestService";
import { getUser } from "../services/userService";

const UserWithLoading = WithLoading(User);

const ProfilePage = (props) => {
  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState(null);
  let isMe = props.match.params.username === "me" ? true : false;
  const [editMode, setEditMode] = useState(false);

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
        console.log("Guest created!");
      }
      setUser(user);
      setIsLoading(false);
    }
    fetchUser();
  }, [props]);

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
