import React, { useEffect, useState } from "react";
import AccessRestricted from "../components/accessRestricted";
import ChatBox from "../components/chatBox";
import ListGroup from "../components/common/listGroup";
import RecipientDescription from "../components/recipientDescription";
import { getUser, getUserMatches } from "../services/userService";
import "./styles/messagesPage.scss";

const MessagesPage = (props) => {
  const [recipient, setRecipient] = useState(null);
  const [users, setUsers] = useState([]);
  const [width, setWidth] = useState(window.innerWidth);
  const [cheater, setCheater] = useState(false);

  const handleWindowSizeChange = () => {
    setWidth(window.innerWidth);
  };

  useEffect(() => {
    async function getMatches() {
      const { data: matches } = await getUserMatches();
      if (
        props.match.params.username &&
        matches.filter(
          (match) => match.username === props.match.params.username
        ).length === 0
      ) {
        setCheater(true);
      }
    }
    getMatches();
  }, [props.match.params.username]);

  useEffect(() => {
    window.addEventListener("resize", handleWindowSizeChange);

    return () => {
      window.removeEventListener("resize", handleWindowSizeChange);
    };
  }, []);

  useEffect(() => {
    async function fetchRecipient() {
      const recipientUsername = props.match.params.username;
      const { data: users } = await getUserMatches();
      const modifiedUsers = users.map((user) => {
        const modifiedUser = {
          title: user.username,
          path: `/messages/${user.username}`,
        };
        return modifiedUser;
      });
      if (recipientUsername) {
        const { data: recipient } = await getUser(recipientUsername);
        setRecipient(recipient);
      }
      setUsers(modifiedUsers);
    }
    fetchRecipient();
  }, [props.match.params.username]);

  const isMobile = width <= 500;

  if (cheater) return <AccessRestricted />;

  return (
    <div className="row">
      <div className="col-3">
        <span style={{ fontWeight: "bold" }}>Your Messages:</span>
        {users.length > 0 ? (
          <div className="ChatList">
            <ListGroup items={users} />
          </div>
        ) : (
          <p>No messages yet.</p>
        )}
      </div>
      {recipient ? (
        <>
          {isMobile ? (
            <div className="col">
              <ChatBox recipient={recipient} />
              <RecipientDescription recipient={recipient} />
            </div>
          ) : (
            <>
              <div className="col-6">
                <ChatBox recipient={recipient} />
              </div>
              <div className="col">
                <RecipientDescription recipient={recipient} />
              </div>
            </>
          )}
        </>
      ) : null}
    </div>
  );
};

export default MessagesPage;
