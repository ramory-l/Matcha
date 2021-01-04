import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Loading from "./common/loading";
import Table from "./common/table";
import _ from "lodash";
import moment from "moment";
import { getUserMatches } from "../services/userService";
import HelpBox from "./common/helpBox";

const MatchesTable = (props) => {
  const [sortColumn, setSortColumn] = useState({
    path: "username",
    order: "asc",
  });
  const [matches, setmatches] = useState([]);

  useEffect(() => {
    async function fetchMatches() {
      const { data: matches } = await getUserMatches();
      const modifiedMatches = matches.map((match) => {
        const newMatch = { ...match };
        const age = moment().diff(newMatch.birthday, "years");
        newMatch.gender = newMatch.gender ? newMatch.gender : "No gender";
        newMatch.birthday = isNaN(age) ? "No age" : age;
        return newMatch;
      });
      const sorted = _.orderBy(
        modifiedMatches,
        [sortColumn.path],
        [sortColumn.order]
      );
      setmatches(sorted);
    }
    fetchMatches();
  }, [sortColumn]);

  const columns = [
    {
      path: "avatar",
      label: "Avatar",
      content: (user) => (
        <img
          alt={user.username}
          style={{ width: "5vw" }}
          src={`${
            user.avatar?.link ? user.avatar.link : "/default-avatar.png"
          }`}
        />
      ),
      noSort: true,
    },
    {
      path: "username",
      label: "Username",
      content: (user) => {
        return (
          <Link style={{ color: "black" }} to={`/profile/${user.username}`}>
            {user.username}
          </Link>
        );
      },
    },
    { path: "gender", label: "Gender" },
    { path: "birthday", label: "Age" },
    {
      path: "tags",
      label: "Common tags",
      content: (user) => {
        const tagsStr = user.tags
          .map((tag) => {
            return `#${tag.name}`;
          })
          .join(", ");
        return <HelpBox textInBox={tagsStr} text={user.tags.length} />;
      },
    },
  ];

  const handleSort = (sortColumn) => {
    setSortColumn(sortColumn);
  };

  return matches ? (
    <Table
      columns={columns}
      data={matches}
      onSort={handleSort}
      sortColumn={sortColumn}
      style={{
        tableStyle: "table table-striped",
        tableHeaderStyle: "thead-dark",
      }}
    />
  ) : (
    <Loading />
  );
};

export default MatchesTable;
