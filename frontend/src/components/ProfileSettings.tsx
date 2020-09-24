import React from "react";
import "./styles/ProfileSettings.css";

function ProfileSettings() {
  return (
    <form className="ProfileSettings">
      <fieldset>
        <legend>Я ищу:</legend>
        <input type="checkbox" /> девушку
        <input type="checkbox" /> парня <br />
        от <input type="text" size={1} /> до <input type="text" size={1} />{" "}
        <br />
        <input type="checkbox" /> для дружбы и общения <br />
        <input type="checkbox" /> для любви и отношений <br />
        <input type="checkbox" /> для секса и свободных отношений <br />
        <input type="checkbox" /> для флирта и виртуального секса <br />
      </fieldset>
    </form>
  );
}

export default ProfileSettings;
