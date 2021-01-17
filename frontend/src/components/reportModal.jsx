import React from "react";
import Modal from "./common/modal";
import ReportForm from "./reportForm";

const ReportModal = ({ modalTarget, modalTitle, userIdToReport, onBlock }) => {
  return (
    <Modal
      modalTarget={modalTarget}
      modalTitle={modalTitle}
      removeFooter={true}
    >
      <ReportForm userIdToReport={userIdToReport} onBlock={onBlock} />
    </Modal>
  );
};

export default ReportModal;
