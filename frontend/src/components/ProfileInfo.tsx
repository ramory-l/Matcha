import React from "react";
import "./styles/ProfileInfo.css";

function ProfileInfo() {
  return (
    <div className="ProfileInfo">
      <img
        className="ProfileInfo-Image"
        src="/default-avatar.png"
        alt="Avatar"
      />
      <div className="ProfileInfo-Description">
        <span>Имя: Default</span>
        <br />
        <span>Фамилия: Default</span>
        <br />
        <span>Дата рождения: Default</span>
        <br />
        <span>Пол: Default</span>
        <br />
        <span>О себе: Default</span>
      </div>
    </div>
  );
}

export default ProfileInfo;
