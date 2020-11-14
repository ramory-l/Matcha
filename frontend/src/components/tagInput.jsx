import React from "react";
import "./styles/tagInput.scss";

const TagInput = (props) => {
  const { tags } = props;
  if (tags.length === 0) return <p>No tags yet.</p>;
  const isEdit = props.editMode ? "danger" : "info";
  return (
    <div className="form-group">
      <label>Tags:</label>
      {tags.map((tag, index) => (
        <a key={index} href="/#" class={`badge badge-${isEdit} mx-1`}>
          {tag}
        </a>
      ))}
    </div>
  );
};

export default TagInput;
