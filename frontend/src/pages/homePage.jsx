import React from "react";
import UserCard from "../components/userCard";

const HomePage = () => {
  const users = [
    {
      firstName: "Mikhail",
      lastName: "Strizhenov",
      fameRate: 100,
      img:
        "https://avatars.mds.yandex.net/get-kinopoisk-post-img/1101693/06b70fd3739a21ca81ed80af2bd9ea0f/960x540",
    },
    { firstName: "Artem", lastName: "Tulupov", fameRate: 101 },
    { firstName: "Elina", lastName: "Ivanova", fameRate: 200 },
    { firstName: "Dmitriy", lastName: "Zimov", fameRate: 1 },
    { firstName: "Roman", lastName: "Ivanov", fameRate: 100 },
  ];
  return (
    <div className="row justify-content-center">
      {users.map((user, index) => (
        <div key={index} className="col-xs-1 mx-2">
          <UserCard user={user} />
        </div>
      ))}
    </div>
  );
};

export default HomePage;
