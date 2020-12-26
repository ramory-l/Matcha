import React, { useEffect, useState } from "react";
import Loading from "./common/loading";
import Table from "./common/table";
import _ from "lodash";
import moment from "moment";
import { getUserMatches } from "../services/userService";

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
      content: (avatar) => (
        <img
          alt={avatar.url}
          style={{ width: "5vw" }}
          src={`${avatar?.url ? avatar.url : "/default-avatar.png"}`}
        />
      ),
      noSort: true,
    },
    { path: "username", label: "Username" },
    { path: "gender", label: "Gender" },
    { path: "birthday", label: "Age" },
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
