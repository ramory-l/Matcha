import React, { useEffect, useState } from "react";
import ChatBox from "../components/chatBox";
import ListGroup from "../components/common/listGroup";
import RecipientDescription from "../components/recipientDescription";
import { getUser, getUserMatches } from "../services/userService";

const MessagesPage = (props) => {
  const [recipient, setRecipient] = useState(null);
  const [users, setUsers] = useState([]);

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

  return (
    <div className="row">
      <div className="col-3">
        <span style={{ fontWeight: "bold" }}>Your Messages:</span>
        {users.length > 0 ? (
          <ListGroup items={users} />
        ) : (
          <p>No messages yet.</p>
        )}
      </div>
      {recipient ? (
        <>
          <div className="col-6">
            <ChatBox recipient={recipient} />
          </div>
          <div className="col">
            <RecipientDescription recipient={recipient} />
          </div>
        </>
      ) : null}
    </div>
  );
};

export default MessagesPage;
