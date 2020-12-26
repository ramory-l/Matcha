import React from "react";
import Modal from "./common/modal";
import ReportForm from "./reportForm";

const ReportModal = ({ modalTarget, modalTitle, userIdToReport }) => {
  return (
    <Modal
      modalTarget={modalTarget}
      modalTitle={modalTitle}
      removeFooter={true}
    >
      <ReportForm userIdToReport={userIdToReport} />
    </Modal>
  );
};

export default ReportModal;
