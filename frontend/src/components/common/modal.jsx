import React from "react";
import CloseButton from "./closeButton";

const Modal = ({ children, modalTarget, modalTitle }) => {
  return (
    <div
      className="modal fade mw-100"
      id={modalTarget}
      tabIndex="-1"
      role="dialog"
      aria-hidden="true"
    >
      <div className="modal-dialog modal-lg" role="document">
        <div className="modal-content bg-dark text-light">
          <div className="modal-header">
            <h5 className="modal-title">{modalTitle}</h5>
            <CloseButton color="light" />
          </div>
          <div className="modal-body">{children}</div>
          <div className="modal-footer">
            <CloseButton type={1} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Modal;
