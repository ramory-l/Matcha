import React from "react";
import ProfileInfo from "../components/ProfileInfo";
import ProfileSettings from "../components/ProfileSettings";
import "./styles/Profile.css";

const Profile = () => {
  return (
    <div className="Profile">
      <ProfileInfo />
      <ProfileSettings />
    </div>
  );
};

export default Profile;