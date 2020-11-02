import React from "react";
import ChatBox from "./chatBox";
import ListGroup from "./common/listGroup";
import RecipientDescription from "./recipientDescription";

const Messages = (props) => {
  const recipient = props.location.state?.recipient;

  return (
    <div className="row">
      <div className="col-3">
        <h2>Your Messages:</h2>
        <ListGroup
          items={[
            { _id: "1", name: "John Conor" },
            { _id: "2", name: "Putin" },
            { _id: "3", name: "Gaben" },
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

export default Messages;
