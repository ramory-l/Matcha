import React, { useEffect, useRef, useState } from "react";
import ReactTags from "react-tag-autocomplete";
import { getUserTags } from "../services/userService";
import "./styles/tagsInput.scss";

const TagsInput = ({ userId, editMode, isSearchForm }) => {
  const [tags, setTags] = useState([]);
  const [suggestions, setSuggestions] = useState([
    {
      id: 184,
      name: "Thailand",
    },
    {
      id: 86,
      name: "India",
    },
  ]);
  const tagInput = useRef(null);

  useEffect(() => {
    const fetchTags = async () => {
      const { data: tags } = await getUserTags(userId);
      const newTags = tags.map((tagName) => {
        return {
          name: tagName.tag,
        };
      });
      setTags(newTags);
    };
    fetchTags();
  }, [userId]);

  const handleDelete = (i) => {
    const newTags = tags.slice(0);
    newTags.splice(i, 1);
    setTags(newTags);
  };

  const handleAddition = (tag) => {
    const newTags = [].concat(tags, tag);
    setTags(newTags);
  };

  if (!editMode && !isSearchForm) {
    return (
      <>
        <label>Tags:</label>
        {tags.length === 0 ? (
          <p>No tags yet.</p>
        ) : (
          tags.map((tag) => (
            <a
              href={`/?tag=${tag.name}`}
              key={tag.id}
              onClick={(e) => this.handleTagClick(e, editMode)}
              className={`badge badge-info mx-1`}
            >
              {`#${tag.name}`}
            </a>
          ))
        )}
      </>
    );
  }

  return (
    <ReactTags
      ref={tagInput}
      tags={tags}
      suggestions={suggestions}
      onDelete={handleDelete}
      onAddition={handleAddition}
      autoresize={false}
    />
  );
};

export default TagsInput;
