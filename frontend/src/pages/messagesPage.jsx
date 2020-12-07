import React, { useEffect, useState } from "react";
import ChatBox from "../components/chatBox";
import ListGroup from "../components/common/listGroup";
import RecipientDescription from "../components/recipientDescription";
import { getUser } from "../services/userService";

const MessagesPage = (props) => {
  const [recipient, setRecipient] = useState(null);

  useEffect(() => {
    async function fetchRecipient() {
      const recipientUsername = props.match.params.username;
      if (recipientUsername) {
        const { data: recipient } = await getUser(recipientUsername);
        setRecipient(recipient);
      }
    }
    fetchRecipient();
  }, [props]);

  return (
    <div className="row">
      <div className="col-3">
        <h2>Your Messages:</h2>
        <ListGroup
          items={[
            { title: "Putin", path: "#" },
            { title: "Putin", path: "me/guests" },
            { title: "Putin", path: "me/guests" },
          ]}
        />
      </div>
      <div className="col-6">
        <ChatBox />
      </div>
      {recipient ? (
        <div className="col">
          <RecipientDescription recipient={recipient} />
        </div>
      ) : null}
    </div>
  );
};

export default MessagesPage;
